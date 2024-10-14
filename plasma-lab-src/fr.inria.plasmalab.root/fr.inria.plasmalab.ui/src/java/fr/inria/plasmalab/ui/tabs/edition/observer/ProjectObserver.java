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
package fr.inria.plasmalab.ui.tabs.edition.observer;

import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.workflow.data.AbstractData;


public interface ProjectObserver {
	public void remProject(Project project);
	public void addProject(Project project);

	public void addData(AbstractData Data, Project project);
	public void openData(AbstractData Data, Project project);
	public void remData(AbstractData Data, Project project);
	public void updateData(AbstractData Data, Project project);
	public void closeData(AbstractData Data, Project project);

	public void renamed(String oldName, Project project);


}
