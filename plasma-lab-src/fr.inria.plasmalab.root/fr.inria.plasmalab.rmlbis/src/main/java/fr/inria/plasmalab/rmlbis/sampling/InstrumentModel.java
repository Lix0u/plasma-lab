/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.sampling;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.BinOp;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.factory.IdentFactory;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.ast.models.ModelType;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class InstrumentModel extends AbstractFunction {

	private int nbParameters;
	private Model model;
	private IdentFactory factory;
	

	public InstrumentModel() {
		this.name = "Add sampling parameters";
		this.description = "Instrument a RML model with sampling parameters.";
	}
	
	public void execute(AbstractData d) throws PlasmaDataException {
		if(d.checkForErrors()){
			String errorMsg = "Error in "+d.getName();
			for(PlasmaDataException err:d.getErrors()){
				errorMsg += "\n"+err.getMessage();
			}
			throw new PlasmaDataException(errorMsg);
		}
		if (d instanceof RMLModel) {
			model = ((RMLModel) d).getModel();
			factory =  model.getDefaultSystem().factory;

			if (model.getType() == ModelType.dtmcsampling 
					|| model.getType() == ModelType.ctmcsampling
					|| model.getType() == ModelType.mdpsampling
					|| model.getType() == ModelType.mdpshdsampling) {
				countParameters();
				addSamplingParameters();
				addCountVariables();
				addRateLabels();
				System.out.println(model.toString());
			}
		}
	}
	
	/** Count the number of parameters by counting total number of actions */ 
	private void countParameters() {
		nbParameters = 0;
		for (Module m : model.getModules().values()) {
			for (Command c : m.getCommands()) {
				nbParameters += c.getActions().size();
			}
		}
		
	}
	
	/** Add constants for each sampling parameters.
	 *  Their name is lambdai.
	 * @throws DuplicateIdentifier 
	 */
	private void addSamplingParameters() throws DuplicateIdentifier {
		for (int i = 0; i < nbParameters; i++) {
			Constant c = factory.makeConstant("lambda"+i, Type.Double, factory.makeValue(1.0));
			model.addConstant(c);
		}
	}
	
	/** Add variables to count the number of occurences of each command.
	 *  Their name is nb_lambdai.
	 * @throws DuplicateIdentifier 
	 */
	private void addCountVariables() throws DuplicateIdentifier {
		int i = 0;
		for (Module m : model.getModules().values()) {
			for (Command c : m.getCommands()) {
				for (Action a : c.getActions()) {
					Variable v = factory.makeVariable("nb_lambda" + i, Type.Integer);
					m.addLocalVariable(v);
					i++;
				}
			}
		}
	}
	
	/** Add labels for the rate of each action:
	 *  - in CTMC the rate is the rate of the action
	 *  - in DTMC the rate is the rate of the action divided by the sampled rate of the command
	 *  Name is "rate_lambdai"
	 * @throws DuplicateIdentifier 
	 */
	private void addRateLabels() throws DuplicateIdentifier {
		Expr body;
		int i = 0;
		for (Module m : model.getModules().values()) {
			for (Command c : m.getCommands()) {
				for (Action a : c.getActions()) {
					switch (model.getType()) {
					case ctmcsampling:
						body = a.getRateExpr();
						break;
					default:
						body = factory.makeBinExpr(a.getRateExpr(), BinOp.Div, c.getTotalRateExpr());
						break;
					}
					Label l = factory.makeLabel("\"rate_lambda" + i + "\"", body);
					model.addLabel(l);
					i++;
				}
			}
		}
	}
}
