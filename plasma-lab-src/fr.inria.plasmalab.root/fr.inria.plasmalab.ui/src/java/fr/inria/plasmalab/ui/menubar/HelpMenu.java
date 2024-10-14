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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.dialogbox.AboutDialog;

public class HelpMenu extends JMenu implements ActionListener {

	private static final long serialVersionUID = 4691404901168847063L;
	final static Logger logger = LoggerFactory.getLogger(HelpMenu.class);
	private PlasmaLabGUI main;
	private AboutDialog about;

	public HelpMenu (PlasmaLabGUI main) {
		super("Help");
		this.setMnemonic('H');
		this.main = main;
		
		JMenuItem about = new JMenuItem("About Plasma...");
		about.addActionListener(this);
		about.setActionCommand("about");
		about.setMnemonic('A');
		this.add(about);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("about")){
			if(about==null){
				about = new AboutDialog(main);
			}
			about.setVisible(true);
		}
		else
			logger.debug("Command (" + command + ") is not yet implemented");
	}
}
