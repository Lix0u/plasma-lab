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
package fr.inria.plasmalab.ui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

import fr.inria.plasmalab.ui.PlasmaLabGUI;


public class PlasmaMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = -1336863491391384491L;
	
	private PlasmaLabGUI main;
	
	public PlasmaMenuBar (PlasmaLabGUI main) {
		this.main = main;
		this.add(new FileMenu(main));
		this.add(new EditMenu(main));
		this.add(new ExperimentMenu(main));
		this.add(new HelpMenu(main));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}
