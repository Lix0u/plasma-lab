/**
 * This file is part of fr.inria.plasmalab.ui.
 *
 * fr.inria.plasmalab.ui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.ui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.ui.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.ui.tabs.experimentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.tabs.experimentation.components.DataSelectionPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.GraphicPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.ResultPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.SimulationPropertyPanel;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class SimulationPanel extends JPanel implements ChangeListener, ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 7987934886196400969L;
	final static Logger logger = LoggerFactory.getLogger(SimulationPanel.class);

	private PlasmaLabGUI owner;

	private static String plotStr, simuStr, backStr, newPathStr, stepsStr, upToStepStr;
	private final static ImageIcon pathIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/pathIcon16.png"));
	private final static ImageIcon forwardIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/forwardIcon16.png"));
	private final static ImageIcon backwardIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/backwardIcon16.png"));

	// Panels
	private ResultPanel simResultPanel;
	private GraphicPanel graphicPanel;
	private DataSelectionPanel dataSelPanel;
	private SimulationPropertyPanel simPropertyPanel;


	// Simulation commands
	private JButton simuButton, backButton, newPathButton;
	private JComboBox simuModeCombo, backModeCombo;
	private JTextField simuStepNb, backStepNb;

	//Logical
	private List<ResultInterface> resultTrace;
	private InterfaceState path;
	private AbstractModel model;	
	private int stepNb;
	private List<InterfaceIdentifier> stateProperties;

	private InterfaceIdentifier[] headers;

	private boolean simulatingLock;
	private boolean checkingLock;

	public SimulationPanel(PlasmaLabGUI owner){
		simulatingLock = true;
		checkingLock = true;
		this.owner = owner;
		Properties stringValues = owner.getStringValues();
		plotStr = stringValues.getProperty("plot");
		simuStr = stringValues.getProperty("simulate");
		backStr = stringValues.getProperty("backtrack");
		newPathStr = stringValues.getProperty("new_path");
		stepsStr = stringValues.getProperty("steps");
		upToStepStr = stringValues.getProperty("up_to_step");

		setLayout(new BorderLayout(0, 0));
		add(createWestPanel(), BorderLayout.WEST);

		JTabbedPane tabP = new JTabbedPane(JTabbedPane.TOP);
		//tabP.setBorder(Common.createBorder(""));
		simResultPanel = new ResultPanel();
		simResultPanel.addListSelectionListener(this);
		//resultPanel.setModelListener(new ModelListener(this));
		tabP.addTab(simResultPanel.getName(), simResultPanel);
		tabP.setSelectedComponent(simResultPanel);
		add(tabP,BorderLayout.CENTER);

		graphicPanel = new GraphicPanel(owner);
		tabP.addTab(plotStr, graphicPanel);
	}


	private Component createWestPanel() {
		JPanel westPanel = new JPanel(new BorderLayout());
		dataSelPanel = new DataSelectionPanel(null);
		simPropertyPanel = new SimulationPropertyPanel();
		westPanel.add(dataSelPanel, BorderLayout.NORTH);
		westPanel.add(createSimuCmdPanel(), BorderLayout.CENTER);
		westPanel.add(simPropertyPanel, BorderLayout.SOUTH);

		return westPanel;
	}

	private Component createSimuCmdPanel(){
		simuButton = new JButton(simuStr);
		simuButton.setIcon(forwardIcon);
		simuButton.setEnabled(false);
		simuButton.addActionListener(this);
		simuButton.setActionCommand(simuStr);
		backButton = new JButton(backStr);
		backButton.setIcon(backwardIcon);
		backButton.setEnabled(false);
		backButton.addActionListener(this);
		backButton.setActionCommand(backStr);
		simuModeCombo = new JComboBox();
		simuModeCombo.setEnabled(false);
		simuModeCombo.addItem(stepsStr);
		simuModeCombo.addItem(upToStepStr);
		backModeCombo = new JComboBox();
		backModeCombo.setEnabled(false);
		backModeCombo.addItem(stepsStr);
		backModeCombo.addItem(upToStepStr);
		simuStepNb = new JTextField("1");
		simuStepNb.setColumns(5);
		simuStepNb.setEnabled(false);
		backStepNb = new JTextField("1");
		backStepNb.setColumns(5);
		backStepNb.setEnabled(false);
		newPathButton = new JButton(newPathStr);
		newPathButton.setIcon(pathIcon);
		newPathButton.addActionListener(this);
		newPathButton.setActionCommand(newPathStr);

		JPanel cP = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor=gbc.BASELINE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//New Path
		gbc.insets = new Insets(3, 50, 3, 50);
		gbc.weightx = 1;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=1;
		gbc.gridwidth=4;
		cP.add(newPathButton,gbc);

		//Simulate
		gbc.insets = new Insets(3, 50, 3, 50);
		gbc.weightx = 1;
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridheight=1;
		gbc.gridwidth=4;
		cP.add(simuButton,gbc);
		gbc.insets = new Insets(3,50,3,3);
		gbc.weightx = 0.75;
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridheight=1;
		gbc.gridwidth=3;
		cP.add(simuModeCombo,gbc);
		gbc.insets = new Insets(3,3,3,50);
		gbc.weightx = 0.25;
		gbc.gridx=3;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		cP.add(simuStepNb,gbc);

		//Backtrack
		gbc.insets = new Insets(3, 50, 3, 50);
		gbc.weightx = 1;
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridheight=1;
		gbc.gridwidth=4;
		cP.add(backButton,gbc);
		gbc.insets = new Insets(3,50,3,3);
		gbc.weightx = 0.75;
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridheight=1;
		gbc.gridwidth=3;
		cP.add(backModeCombo,gbc);
		gbc.insets = new Insets(3,3,3,50);
		gbc.weightx = 0.25;
		gbc.gridx=3;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		cP.add(backStepNb,gbc);	

		JPanel cmdPanel = new JPanel(new BorderLayout());
		cmdPanel.add(cP, BorderLayout.NORTH);
		cmdPanel.setBorder(Common.createBorder("Exploration"));
		return new JScrollPane(cmdPanel);
	}	

	/**
	 * 
	 * @return
	 */
	public DataSelectionPanel getDataSelPanel() {
		return dataSelPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane jtp = (JTabbedPane) e.getSource();

		if (jtp.getSelectedComponent() instanceof SimulationPanel) {
			//ExperimentPanel newExp = (ExperimentPanel) jtp.getSelectedComponent(); 	
			//newExp.projectFiles.updateModels(main.editorPanel.getModels());
			//newExp.projectFiles.updateContracts(main.editorPanel.getContracts());
		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(newPathStr)){
			simulatingLock = false;
			try{
				newPath();
				drawPropertiesValues(stepNb);
				simResultPanel.unselectRow();
			}catch(PlasmaDataException exp){
				//Plasma error, desactivate simulate
				simuButton.setEnabled(false);
				simuModeCombo.setEnabled(false);
				simuStepNb.setEnabled(false);
				JOptionPane.showMessageDialog(owner, exp.getMessage(), 
						"Exception occured", JOptionPane.WARNING_MESSAGE);
			}catch(PlasmaSimulatorException exp){
				//Plasma error, desactivate simulate
				simuButton.setEnabled(false);
				simuModeCombo.setEnabled(false);
				simuStepNb.setEnabled(false);
				JOptionPane.showMessageDialog(owner, exp.getMessage(), 
						"Exception occured", JOptionPane.WARNING_MESSAGE);
			}finally{
				simulatingLock = true;
			}
		}else if(e.getActionCommand().equals(simuStr)){
			simulatingLock = false;
			try{
				if(simuModeCombo.getSelectedItem().equals(stepsStr)){
					for(int i = 0; i<Integer.parseInt(simuStepNb.getText()); i++){
						simulate();
					}
				} else if(simuModeCombo.getSelectedItem().equals(upToStepStr)){
					while(stepNb<Integer.parseInt(simuStepNb.getText())-1){
						simulate();
					}
				}
				if(simResultPanel.getSelectedRow()<0){
					drawPropertiesValues(stepNb);
				}
			}catch(PlasmaDeadlockException exp){
				//Dead-lock, desactivate simulate
				simuButton.setEnabled(false);
				simuModeCombo.setEnabled(false);
				simuStepNb.setEnabled(false);
				JOptionPane.showMessageDialog(owner, exp.getMessage(), 
						"Deadlock", JOptionPane.WARNING_MESSAGE);
			}catch(PlasmaSimulatorException exp){
				//Plasma error, desactivate simulate
				simuButton.setEnabled(false);
				simuModeCombo.setEnabled(false);
				simuStepNb.setEnabled(false);
				JOptionPane.showMessageDialog(owner, exp.getMessage(), 
						"Exception occured", JOptionPane.WARNING_MESSAGE);
			}catch(NumberFormatException exp){
				JOptionPane.showMessageDialog(this, "Steps should be a positive integer.", "Parameter error", JOptionPane.ERROR_MESSAGE);
			} finally {
				simulatingLock = true;
			}
		}else if(e.getActionCommand().equals(backStr)){
			simulatingLock = false;
			try{
				if(backModeCombo.getSelectedItem().equals(stepsStr)){
					for(int i = 0; i<Integer.parseInt(backStepNb.getText()); i++){
						if(!backtrack())
							break;
					}
				} else if(backModeCombo.getSelectedItem().equals(upToStepStr)){
					while(stepNb>Integer.parseInt(backStepNb.getText())-1){
						if(!backtrack())
							break;
					}
				}
				if(simResultPanel.getSelectedRow()<0){
					drawPropertiesValues(stepNb);
				}
			}catch(PlasmaSimulatorException exp){
				//Plasma error, desactivate simulate
				simuButton.setEnabled(false);
				simuModeCombo.setEnabled(false);
				simuStepNb.setEnabled(false);
				JOptionPane.showMessageDialog(owner, exp.getMessage(), 
						"Exception occured", JOptionPane.WARNING_MESSAGE);
			}catch(NumberFormatException exp){
				JOptionPane.showMessageDialog(this, "Steps should be a positive integer.", "Parameter error", JOptionPane.ERROR_MESSAGE);
			} finally {
				simulatingLock = true;
			}
		}
	}

	/** Backtrack from one step.
	 * 
	 *  @return true is backtrack is still enabled (trace's length is greater than 1.
	 * @throws PlasmaSimulatorException 
	 */
	private boolean backtrack() throws PlasmaSimulatorException {
		//Activate simulate
		simuButton.setEnabled(true);
		simuModeCombo.setEnabled(true);
		simuStepNb.setEnabled(true);
		model.backtrack();
		if(model.getTraceLength()==1){
			//Initial state, desactivate backtrack
			backButton.setEnabled(false);
			backModeCombo.setEnabled(false);
			backStepNb.setEnabled(false);
		}
		stepNb --;
		// Display previous state
		simResultPanel.remLine(stepNb+1);
		return model.getTraceLength()>1;
	}


	private void simulate() throws PlasmaSimulatorException {
		model.simulate();
		stepNb++;
		// TODO logical?
		// Check property and label

		// TODO Display new state

		// Activate backtrack
		backButton.setEnabled(true);
		backModeCombo.setEnabled(true);
		backStepNb.setEnabled(true);
		path = model.getStateAtPos(stepNb);
		drawLine(path);

		// Draw graphics
		// graphicPanel.clearResult();
		resultTrace.clear();
		for (InterfaceState state : model.getTrace()) {
			resultTrace.add(state);
		}
		graphicPanel.setResult(resultTrace);
	}


	private void newPath() throws PlasmaDataException, PlasmaSimulatorException {
		stepNb=0;
		model = dataSelPanel.getSelectedModel();

		if(model == null){
			JOptionPane.showMessageDialog(this, "No model selected.", "Data error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		//TODO check if not null
		simuButton.setEnabled(true);
		backButton.setEnabled(false);
		simuModeCombo.setEnabled(true);
		backModeCombo.setEnabled(false);
		simuStepNb.setEnabled(true);
		backStepNb.setEnabled(false);

		if(!model.getErrors().isEmpty()){
			String errorMsg = "Error in "+model.getName();
			for(PlasmaDataException err:model.getErrors()){
				errorMsg += "\n"+err.getMessage();
			}
			throw new PlasmaDataException(errorMsg);
		}
		path = model.newPath();

		//headers = model.getIdentifiers().values().toArray(new InterfaceIdentifier[model.getIdentifiers().size()]);
		headers = model.getHeaders();
		simResultPanel.setTableHeader(headers);
		//Draw graphics
		//graphicPanel.clearResult();
		resultTrace = new ArrayList<ResultInterface>();
		for(InterfaceState state:model.getTrace()){
			resultTrace.add(state);
			drawLine(state);
		}
		//Step Nb = number of state in trace starting from 0
		stepNb = resultTrace.size()-1;

		graphicPanel.setResult(resultTrace);
		stateProperties = model.getStateProperties();
	}

	public void drawPropertiesValues(int untilStep){
		checkingLock = false;
		if(untilStep>stepNb){
			untilStep = stepNb;
			simResultPanel.unselectRow();
		}
		List<AbstractRequirement> requirements = dataSelPanel.getSelectedRequirement();
		simPropertyPanel.clear();
		for(AbstractRequirement req:requirements){
			req.setModel(model);
			double value = Double.NaN;
			if(!req.checkForErrors()){
				//Check from init state to untilStep
				try {
					value = req.check(untilStep,model.getStateAtPos(0));
				} catch (PlasmaCheckerException e) {
					value = Double.NaN;
					logger.error(e.getMessage());
				}
			}else{
				value = Double.NaN;
				for(PlasmaDataException e:req.getErrors())
					logger.error(e.getMessage());
			}
			simPropertyPanel.addLine(req.getName(), value);
		}
		if(stateProperties != null)
			for(InterfaceIdentifier id:stateProperties){
				double value;
				try {
					value = model.getStateAtPos(untilStep).getValueOf(id);
				} catch (PlasmaSimulatorException e) {
					logger.warn("Unknown id: " + id.getName());
					value = Double.NaN;
				}
				simPropertyPanel.addLine(id.getName(), value);
		}
		checkingLock = true;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(simulatingLock){
			if(checkingLock){
				int untilStep = simResultPanel.getSelectedRow();
				if(untilStep<0)
					untilStep = stepNb;
				drawPropertiesValues(untilStep);
			}
		}
	}

	public void drawLine(InterfaceState newState){
		String[] line = new String[headers.length];
		for(int i=0; i<line.length; i++){
			try {
				Double val = newState.getValueOf(headers[i]);
				int intVal = val.intValue();
				if (val == intVal)
					line[i]= Integer.toString(intVal);
				else
					line[i]= val.toString();
			} catch (PlasmaSimulatorException e) {
				logger.warn("Unknown header name: " + headers[i]);
				line[i] = "NaN";
			}
		}
		simResultPanel.addLine(line);
	}
}
