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
package fr.inria.plasmalab.ui.dialogbox.wizard;

import java.io.File;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.DialogBox;

/**
 * 
 * @author kevin.corre@inria.fr
 * 
 * This wizard task is to ask user to select a factory and a project and then create/import and set 
 * a requirement or a model.
 *
 */
public class GenericWizards{
	public static void openProject(PlasmaLabGUI main) {
		DialogBox db = new DialogBox(main);
		File file = db.openProject();
		if(file!=null){
			if(main.getActiveProjectControler() == null){
				ProjectControler newPC = new ProjectControler(main);
				main.getPCList().add(newPC);
				newPC.createProject(file);
			}
			else if(main.getActiveProjectControler().isEmpty()&&main.getActiveProjectControler().isSaved()){
				main.getActiveProjectControler().replaceProject(file);
			}else{
				ProjectControler newPC = new ProjectControler(main);
				main.getPCList().add(newPC);
				newPC.createProject(file);
			}
		}
	}
}
