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
import java.util.Properties;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.wizard.GenericWizards;
import fr.inria.plasmalab.ui.dialogbox.wizard.ImportWizard;
import fr.inria.plasmalab.ui.dialogbox.wizard.NewDataWizard;
import fr.inria.plasmalab.ui.dialogbox.wizard.NewProjectWizard;


public class FileMenu extends JMenu implements ActionListener {

	private static final long serialVersionUID = 639664359670478540L;
	final static Logger logger = LoggerFactory.getLogger(FileMenu.class);
	
	private static String newStr, openStr, project, content, saveStr, saveAsStr, exportStr, importStr, exitStr;
	private String[] newSubCommand;

	public PlasmaLabGUI main;

	public FileMenu(PlasmaLabGUI main) {
		super("File");
		this.main = main;
		this.setMnemonic('F'); // Create shortcut
		
		Properties stringValues = main.getStringValues();
		content = stringValues.getProperty("content");
		project = stringValues.getProperty("project");
		newStr = stringValues.getProperty("new");
		openStr = stringValues.getProperty("open");
		saveStr = stringValues.getProperty("save");
		saveAsStr = stringValues.getProperty("saveAs");
		importStr = stringValues.getProperty("import");
		exportStr = stringValues.getProperty("export");
		exitStr = stringValues.getProperty("exit");
		newSubCommand = new String[]{ project, content};
		
		JMenuItem item;
		this.add(new SubMenu(newStr, newSubCommand, this));
		item  = new JMenuItem(openStr+" "+project);
		item.addActionListener(this);
		item.setMnemonic('O');
		item.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, 
		        java.awt.Event.CTRL_MASK));
		this.add(item);
		this.add(new JSeparator());
		item  = new JMenuItem(saveStr);
		item.addActionListener(this);
		item.setMnemonic('S');
		item.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 
		        java.awt.Event.CTRL_MASK));
		this.add(item);
		item  = new JMenuItem(saveAsStr);
		item.addActionListener(this);
		item.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 
		        java.awt.Event.CTRL_MASK+java.awt.Event.SHIFT_MASK));
		this.add(item);
		this.add(new JSeparator());
		item  = new JMenuItem(importStr);
		item.addActionListener(this);
		item.setMnemonic('I');
		item.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, 
		        java.awt.Event.CTRL_MASK));
		this.add(item);
		item  = new JMenuItem(exportStr);
		item.addActionListener(this);
		item.setMnemonic('E');
		item.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 
		        java.awt.Event.CTRL_MASK));
		this.add(item);
		this.add(new JSeparator());
		item = new JMenuItem(exitStr);
		item.addActionListener(this);
		item.setMnemonic('X');
		this.add(item);		
	}


	public static class SubMenu extends JMenu {
		private static final long serialVersionUID = 819167235362528911L;

		public SubMenu(String prefix, String[] suffixes, ActionListener al) {
			super(prefix.toString());
			JMenuItem item;

			for (int i = 0; i < suffixes.length; i++) {
				item = new JMenuItem(suffixes[i].toString());
				item.setActionCommand(prefix.toString() + suffixes[i].toString());
				item.addActionListener(al);
				item.setMnemonic(suffixes[i].toString().charAt(0));
				this.add(item);
			}
			this.setMnemonic(prefix.charAt(0));
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.startsWith(newStr))
			newActionPerfomed(command);
		else if (command.startsWith(openStr))
			openActionPerfomed(command);
		else if (command.startsWith(saveAsStr))  // saveAs must be checked before Save
			saveAsActionPerfomed();
		else if (command.startsWith(saveStr))  // saveAs must be checked before Save
			saveActionPerfomed();
		else if (command.startsWith(exportStr))  // saveAs must be checked before Save
			exportActionPerfomed();
		else if (command.startsWith(importStr))
			importActionPerfomed();
		else if (command.startsWith(exitStr))
			exitActionPerfomed();
		else
			logger.debug("Command (" + command + ") is not yet implemented");
	}

	private void exportActionPerfomed() {
		ProjectControler pc = main.getActiveProjectControler();
		if(pc!= null)
			pc.exportData();
	}
	private void importActionPerfomed() {
		if(!main.getPCList().isEmpty())
			ImportWizard.showWizard(main);
	}

	private void openActionPerfomed(String command) {
		if (command.endsWith(project))
			GenericWizards.openProject(main);
	}

	private void newActionPerfomed(String command) {
		if(command.endsWith(project)){
			NewProjectWizard.showWizard(main);
		}else if(command.endsWith(content)){
			NewDataWizard.showWizard(main);
		}
	}

	private void saveActionPerfomed() {
		ProjectControler pc = main.getActiveProjectControler();
		if(pc!= null)
			pc.saveProject();
	}

	private void saveAsActionPerfomed() {
		ProjectControler pc = main.getActiveProjectControler();
		if(pc!= null)
			pc.saveAsProject();
	}
	
	private void exitActionPerfomed() {
		while(!main.getPCList().isEmpty()) {
			ProjectControler pc = main.getPCList().get(0);
			if(pc != null) {
				if (! pc.closeProject())	// cancel exit
					return; 
			}
			else {
				main.getPCList().remove(0);
			}
		}
		System.exit(0);
	}
}
