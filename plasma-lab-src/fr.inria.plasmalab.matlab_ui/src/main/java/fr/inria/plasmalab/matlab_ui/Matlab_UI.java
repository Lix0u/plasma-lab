/**
 * This file is part of fr.inria.plasmalab.matlab_ui.
 *
 * fr.inria.plasmalab.matlab_ui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab_ui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab_ui.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab_ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.bltl.BLTLFactory;
import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.controler.ExperimentationManager;
import fr.inria.plasmalab.matlab.MatLabSessionFactory;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaPluginNotFound;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;

public class Matlab_UI implements ExperimentationListener{
	private static MatLabSessionFactory MATLABSESSIONFACTORY;
	private static BLTLFactory BLTLFACTORY;
	private static MatlabProxy MATLABPROXY;
	private static boolean firstPass = true;
	protected static final int BEAT_FREQUENCY = 3000;
	
	private List<ResultInterface> results;
	
	public Matlab_UI(){
		try{
			if(firstPass){
				Controler controler = Controler.createControler(null);
				
				MATLABSESSIONFACTORY = new MatLabSessionFactory();
				controler.getADFList().add(MATLABSESSIONFACTORY);
				
				BLTLFACTORY = new BLTLFactory();
				controler.getADFList().add(BLTLFACTORY);
				firstPass = false;
			}
			MATLABPROXY = MATLABSESSIONFACTORY.getProxy();
			MATLABPROXY.eval("p2s_displayMsg_handle = getappdata(0,'p2s_displayMsg');");
			MATLABPROXY.eval("p2s_start_handle = getappdata(0,'p2s_start');");
			MATLABPROXY.eval("p2s_completed_handle = getappdata(0,'p2s_completed');");
			MATLABPROXY.eval("p2s_error_handle = getappdata(0,'p2s_error');");
			
		}catch(MatlabConnectionException e){
			throw new PlasmaRuntimeException(e);
		} catch (MatlabInvocationException e) {
			throw new PlasmaRuntimeException(e);
		}
	}
	
	/**
	 * This method is used where the requirement in GUIDE(MATLAB) is a String array.
	 * This is what we get with a multiline edit box. We transform this array into a single String 
	 * with whitespace separator.
	 * @param simulinkModel
	 * @param requirementsLine
	 * @param algo
	 * @param algoParameters
	 */
	public void InitExperiment(String simulinkModel, String[] requirementsLine, String algo, String algoParameters){
		String requirement = "";
		for(int i=0; i<requirementsLine.length; i++){
			requirement += requirementsLine[i]+" ";
		}
		InitExperiment(simulinkModel, requirement, algo, algoParameters);
	}
	
	public void InitExperiment(String simulinkModel, String requirement,
			String algo, String algoParameters){

		try {
			//Model
			AbstractModel msm = MATLABSESSIONFACTORY.createAbstractModel("Simulink model", simulinkModel);
			//Requirement
			AbstractRequirement bltlr = BLTLFACTORY.createAbstractRequirement(requirement, requirement);
			List<AbstractRequirement> requirements = new ArrayList<AbstractRequirement>();
			requirements.add(bltlr);
			//Port & NbThread
			int port = 8100;
			int nbThread = 1;
			int batch = 500;
			//Optim variables: empty, no optimization
			// TODO: optimization in MATLAB Session
			OptimVariables optimVariables = new OptimVariables();
			
			//Algorithm
			InterfaceAlgorithmFactory algoFactory = null;
			List<InterfaceAlgorithmFactory> algoFactories = Controler.getControler().getAAFList();
			for(InterfaceAlgorithmFactory iaf: algoFactories){
				if(iaf.getId().endsWith(algo)){
					algoFactory = iaf;
					break;
				}
			}
			if(algoFactory == null)
				throw new PlasmaPluginNotFound(algo);
	
			//Algorithm parameters;
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("distributed", nbThread!=1);
			algoFactory.fillParametersMap(parameters, algoParameters.split(" "));

			System.out.println("Experiment: \n"
					+ algoFactory.getId()+"\n"
					+ parameters.toString()+"\n"
					+ "Model: "+msm.getContent()+"\n"
					+ "Requirements: "+requirements.toString());
			
			//Run the experiment
			ExperimentationManager expManager =  ExperimentationManager.createExperimentationManager(Controler.getControler());
			expManager.addExperimentationListener(this);
			expManager.setupAnExperiment(algoFactory , msm, requirements, port, optimVariables , nbThread, batch, BEAT_FREQUENCY, parameters);
			expManager.startExperimentAsync();
			
		} catch (PlasmaDataException e){
			notifyAlgorithmError(this.toString(), e.getMessage());
		} catch (PlasmaParameterException e){
			notifyAlgorithmError(this.toString(), e.getMessage());
		}
		
	}
	
	/** EXPERIMENTATION LISTNER **/
	public void notifyAlgorithmStarted(String nodeURI) {
		try {
			MATLABPROXY.eval("p2s_start_handle();");
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			throw new PlasmaRuntimeException("MATLABPROXY disconnected", e);
		}
	}

	public void notifyAlgorithmCompleted(String nodeURI) {
		try {
			for(ResultInterface ri:this.results){
				MATLABPROXY.eval("p2s_displayMsg_handle('"+printResult(ri).replaceAll("'", "''")+"');");
			}
			MATLABPROXY.eval("p2s_displayMsg_handle('Experiment completed.');");
			MATLABPROXY.eval("p2s_completed_handle();");
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			throw new PlasmaRuntimeException("MATLABPROXY disconnected", e);
		}
	}

	public void notifyAlgorithmStopped(String nodeURI) {
		try {
			MATLABPROXY.eval("p2s_completed_handle('Stopped before completing experiment.');");
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			throw new PlasmaRuntimeException("MATLABPROXY disconnected", e);
		}
	}

	public void notifyAlgorithmError(String nodeURI, String errorMessage) {
		try {
			MATLABPROXY.eval("p2s_error_handle('"+errorMessage.replaceAll("'", "''")+"');");
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			throw new PlasmaRuntimeException("MATLABPROXY disconnected", e);
		}
	}

	public void notifyNewServiceConnected(String nodeURI) {
		// TODO Stub de la méthode généré automatiquement
		
	}

	public void notifyServiceDisconnected(String nodeURI) {
		// TODO Stub de la méthode généré automatiquement
		
	}

	public void publishResults(String nodeURI, List<ResultInterface> results) {
		this.results = results;		
	}

	public void notifyProgress(int percent) {
		// TODO Stub de la méthode généré automatiquement
		
	}

	public void notifyTimeRemaining(long milliseconds) {
		// TODO Stub de la méthode généré automatiquement
		
	}
	
	public String printResult(ResultInterface ri){
		String toPrint = ri.getCategory()+" [";
		for(InterfaceIdentifier ii:ri.getHeaders()){
			try {
				toPrint += ii.toString()+": "+ri.getValueOf(ii)+", ";
			} catch (PlasmaExperimentException e) {
				toPrint += ii.toString()+": NaN, ";
			}
		}
		toPrint = toPrint.subSequence(0, toPrint.length()-2).toString()+"]";
		return toPrint;
	}
	
	public String getPlasmaVersionNumber(){
		return Controler.getVersion()+"-"+Controler.getBuildID();
	}
}
