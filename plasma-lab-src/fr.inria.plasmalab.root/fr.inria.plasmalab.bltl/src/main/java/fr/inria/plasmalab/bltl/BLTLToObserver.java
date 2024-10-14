/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.sedsoft.ispltl.ISPLTL;

public class BLTLToObserver extends AbstractFunction {

	public BLTLToObserver() {
		this.name = "Convert to observer";
		this.description = "Convert a BLTL property to an observer.";
	}

	@Override
	public void execute(AbstractData d) {
		BLTLRequirement req;
		if (d instanceof BLTLRequirement) {
			req = (BLTLRequirement)d;
			ISPLTL ispltl = new ISPLTL();
			ispltl.createAutomataFile(req.getName()+"_obs.txt",req.getContent());
			
//			if (parameters.length>=2 && parameters[1] instanceof Project) {
//				Project p = (Project)parameters[1];
//				String observer = ispltl.createAutomataString(req.getContent());
//				
//				for(AbstractDataFactory arf: Controler.getControler().getADFList()){
//					if (arf.getId().equals("fr.inria.plasmalab.rmlbis.observer") && arf instanceof AbstractRequirementFactory){
//						try{
//							AbstractData obs = arf.createAbstractData(req.getName()+"_obs",observer);
//							p.addRequirement(obs);
//						} catch(Exception e){
//						  	JOptionPane.showMessageDialog(p.getpControler().getOwner(),
//									"An error occured while loading requirement\n"
//									+ e.getMessage()
//									, "Project creation error", JOptionPane.ERROR_MESSAGE);
//						}
//						return;
//					}
//				}
//			}
		}
	}
}
