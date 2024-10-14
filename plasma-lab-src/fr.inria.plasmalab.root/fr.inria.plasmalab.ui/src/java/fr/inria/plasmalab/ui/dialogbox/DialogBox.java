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
package fr.inria.plasmalab.ui.dialogbox;


import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.filters.EmptyDirFilter;
import fr.inria.plasmalab.ui.dialogbox.filters.PlasmaFilter;

@SuppressWarnings("serial")
public class DialogBox extends JFileChooser {
	PlasmaLabGUI owner = null; 

	public DialogBox(PlasmaLabGUI owner) {
		super();
		this.owner = owner;
		//TODO: Windows?
		try {
			this.setCurrentDirectory(PlasmaLabGUI.getWorkingDir().getCanonicalFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public File openModel() {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(PlasmaFilter.forModelFiles());
		int val = this.showOpenDialog(this.owner);

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (!file.canWrite())
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Info", JOptionPane.WARNING_MESSAGE);
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		} else
			return null;
	}
	
	public File exportModel(String suggestion) {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(null);
		this.setSelectedFile(new File(suggestion));
		int val = this.showDialog(this.owner, "Export");
		

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (file.exists()) {
				val = JOptionPane.showConfirmDialog(this, "File " + file.getName() + " is already existing. Do you want to replace it?",
						"Plasma Info", JOptionPane.OK_CANCEL_OPTION);
				if (val != JOptionPane.OK_OPTION)
					return null;
			} else try {
				if (!file.createNewFile())
					JOptionPane.showMessageDialog(this, "File " + file.getName() + " cannot be created", "Plasma Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) { e.printStackTrace(); }

			if (!file.canWrite()) {
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			//JOptionPane.showMessageDialog(this, "File " + file.getName() + " is ready", "Plasma Info", JOptionPane.INFORMATION_MESSAGE);
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		}
		return null;
	}


	public File openProject () {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(PlasmaFilter.forProjectFiles());
		int val = this.showOpenDialog(this.owner);

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (!file.canWrite())
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Info", JOptionPane.WARNING_MESSAGE);
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		} else
			return null;
	}
	
	public File openFile () {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(null);
		int val = this.showOpenDialog(this.owner);

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (!file.canWrite())
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Info", JOptionPane.WARNING_MESSAGE);
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		} else
			return null;
	}


	public File createDirectoryProject() {
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		EmptyDirFilter EDFilter = new EmptyDirFilter();
		File selected = null;
		do {
			int val = this.showOpenDialog(this.owner);

			if (val == JFileChooser.APPROVE_OPTION) {
				selected = getSelectedFile();
				assert (!selected.isDirectory()) : "PlasmaDialogBox: " + selected.getName() + "should be a directory";
			} else //JFileChooser.CANCEL_OPTION;
				return null;
		} while (EDFilter.accept(selected));
		PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
		if (selected.canWrite())
			return selected;
		else {
			JOptionPane.showMessageDialog(this, "Directory " + selected.getName() + " is read only", "Plasma Info", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public File newProject(){
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(PlasmaFilter.forProjectFiles());
		int val = this.showOpenDialog(this.owner);

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (!file.canWrite()) {
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		}
		return null;
	}


	public File saveProject(String name) {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setFileFilter(PlasmaFilter.forProjectFiles());
		String suffix = PlasmaFilter.projectExtension[0];
		name += (name.endsWith(suffix)) ? "" : "." + suffix; 
		this.setSelectedFile(new File(name));
		int val = this.showSaveDialog(this.owner);

		if (val == JFileChooser.APPROVE_OPTION) {
			File file = getSelectedFile();
			if (file.exists()) {
				val = JOptionPane.showConfirmDialog(this, "File " + file.getName() + " already exists. Do you want to replace it?",
						"Plasma Info", JOptionPane.OK_CANCEL_OPTION);
				if (val != JOptionPane.OK_OPTION)
					return null;
			} else try {
				if (!file.createNewFile())
					JOptionPane.showMessageDialog(this, "File " + file.getName() + " cannot be created", "Plasma Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) { e.printStackTrace(); }

			if (!file.canWrite()) {
				JOptionPane.showMessageDialog(this, "File " + file.getName() + " is read only", "Plasma Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			PlasmaLabGUI.setWorkingDirPath(this.getCurrentDirectory().getAbsolutePath());
			return file;
		}
		return null;
	}
	
	public int closeUnsavedProject(ProjectControler pc){
		int choice = JOptionPane.showConfirmDialog(owner, pc.getName()+" has unsaved content, would you want to save it?", "Project modified", JOptionPane.YES_NO_CANCEL_OPTION);
		return choice;
		
	}
	
	public String newEdit(String message, String title, String suggestion){
		return (String) JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE, null, null, suggestion);
	}
}
