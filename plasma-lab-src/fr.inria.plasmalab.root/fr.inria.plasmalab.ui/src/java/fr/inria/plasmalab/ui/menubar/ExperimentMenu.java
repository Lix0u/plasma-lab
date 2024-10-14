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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fr.inria.plasmalab.ui.PlasmaLabGUI;

public class ExperimentMenu extends JMenu implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4255104409183205536L;
	private final static String simu = "Simulation", expe = "Experiment", opti = "Optimization"; 
	
	public PlasmaLabGUI main;
	
	public ExperimentMenu(PlasmaLabGUI main) {
		super("Experiment");
		this.main = main;
		this.setMnemonic('X'); // Create shortcut

		JMenuItem item = new JMenuItem(simu);
		item.setActionCommand(simu);
		item.addActionListener(this);
		item.setMnemonic('S');
		this.add(item);
		item = new JMenuItem(expe);
		item.setActionCommand(expe);
		item.addActionListener(this);
		item.setMnemonic('E');
		this.add(item);
		item = new JMenuItem(opti);
		item.setActionCommand(opti);
		item.addActionListener(this);
		item.setMnemonic('O');
		this.add(item);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(expe)){
			main.setFocusToExperiment();
		}else if(e.getActionCommand().equals(simu)){
			main.setFocusToSimulation();
		}else if(e.getActionCommand().equals(opti)){
			main.setFocusToExperiment();
		}
	}
}
