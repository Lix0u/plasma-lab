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
package fr.inria.plasmalab.ui.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.dialogbox.DialogBox;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.ui.tabs.edition.EditionPanel;
import fr.inria.plasmalab.ui.tabs.edition.ProjectPanel;
import fr.inria.plasmalab.ui.tabs.edition.observer.DataObserver;
import fr.inria.plasmalab.ui.tabs.edition.observer.ProjectObserver;
import fr.inria.plasmalab.ui.viewer.ProjectViewer;
import fr.inria.plasmalab.workflow.data.AbstractData;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 * The ProjectControler class is responsible for interaction between the Plasma Lab GUI and a Project (and its sub-components).
 */
public class ProjectControler implements ProjectObserver, DataObserver, ActionListener, CaretListener{
	final static Logger logger = LoggerFactory.getLogger(ProjectControler.class);
	private PlasmaLabGUI owner;
	private ProjectViewer pViewer;
	private ProjectPanel pPanel;
	
	private Project project;

	private List<EditionPanel> editionPanels;
	
	/**
	 * @param owner
	 */
	public ProjectControler(PlasmaLabGUI owner){
		editionPanels = new ArrayList<EditionPanel>();
		this.owner = owner;
		pViewer = owner.getProjectViewer();
	}
	
	/**
	 * Initializa all GUI to handle this new Project.
	 */
	public void init(){
		//Observer
		project.addObserver(pViewer);
		project.addObserver(this);
		project.addObserver(owner.getSimulationPanel().getDataSelPanel());
		project.addObserver(owner.getExperimentPane().getDataSelPanel());
		project.addObserver(owner.getOptimizationPane().getDataSelPanel());
		project.addObserver(pPanel);
		//Register
		project.register();
	}
	/********************************** PROJECT OPERATIONS ********************************************/
	
	public ProjectPanel getpPanel() {
		return pPanel;
	}

	/**
	 * Open a project from a Plasma Lab Project file.
	 * @param target
	 */
	public void createProject(File target) {
		try{
			project = new Project(target, this);
			pPanel = new ProjectPanel(this, project);
			init();
			//Add project edition panel
			owner.addProjectPanel(pPanel);
			
			for(AbstractData data:project.getAMList()){
				openData(data, project);
			}
			for(AbstractData data:project.getARList()){
				openData(data, project);
			}
			for(AbstractData data:project.getADList()){
				openData(data, project);
			}
			setSaved(true);
		} catch(Exception e){
			logger.error(e.getMessage());
			closeProject();
			JOptionPane.showMessageDialog(this.owner, 
					"Project file could not be read.\n"
					+ e.getMessage(),
					"Project creation error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Create an empty Plasma Lab project.
	 * @param name
	 */
	public void createProject(String name){
		project = new Project(name, this);
		pPanel = new ProjectPanel(this, project);
		init();
		//Add project edition panel
		owner.addProjectPanel(pPanel);
		setSaved(true);
	}

	/**
	 * Modify GUI component so that they reflect the new Project.
	 * @param root
	 * @param file
	 */
	public void replaceProject(File file){
		//Reference to old Project
		try{
			Project oldProject = project;
			oldProject.close();
			editionPanels.clear();

			project = new Project(file, this);
			pPanel.replace(project);
			init();
			owner.updateTabName(pPanel, project.getName());
			
			for(AbstractData data:project.getAMList()){
				openData(data, project);
			}
			for(AbstractData data:project.getARList()){
				openData(data, project);
			}
			for(AbstractData data:project.getADList()){
				openData(data, project);
			}
			setSaved(true);
		} catch(Exception e){
			logger.error(e.getMessage());
			closeProject();
			JOptionPane.showMessageDialog(this.owner, 
					"Project file could not be read.\n"
					+ e.getMessage(),
					"Project creation error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void remProject(Project project) {
		//does nothing: the project controler is linked to a single project 
	}
	public boolean closeProject() {
		//Confirmation
		boolean confirm = true;
		if(!isSaved()){
			DialogBox db = new DialogBox(owner);
			int choice = db.closeUnsavedProject(this);
			if(choice == 2)
				confirm = false;
			else if (choice== 0){
				confirm = saveProject();
			}
		}
		if(confirm){
			if (project != null)
				project.close();
			owner.remProjectControler(this);
		}
		return confirm;
	}
	
	@Override
	public void addProject(Project project) {
		//does nothing: the project controler is linked to a single project 
	}
	public boolean saveProject(){
		boolean confirm = true;
		if(project.getFile()==null)
			confirm = saveAsProject();
		else
			project.save();
		
		setSaved(confirm);
		return confirm;
	}
	public boolean saveAsProject() {
		boolean confirm = false;
		DialogBox db = new DialogBox(owner);
		File target = db.saveProject(project.getName());
		if(target!=null){
			project.setFile(target);
			confirm = saveProject();
		}

		setSaved(confirm);
		return confirm;
	}
	@Override
	public void renamed(String oldName, Project project) {
		owner.updateTabName(pPanel, project.getName());
		setSaved(false);
	}


	
	/****************************************** GENERAL PURPOSE *****************************************/

	public boolean isEmpty(){
		return project.isEmpty();
	}
	
	public boolean isSaved(){
		if (project != null)
			return project.isSaved();
		else
			return true;
	}
	
	public void setSaved(boolean saved){
		project.setSaved(saved);
		owner.setTabSaved(pPanel, project.getName(), saved);
		if(saved){
			for(EditionPanel ep:editionPanels){
				pPanel.setTabSaved(ep, ep.getData().getName(), true);
			}
		}
	}

	/**
	 * Do undo and redo action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("undo")){
			logger.info("Rollback not implemented");
		}else if(e.getActionCommand().equals("redo")){
			logger.info("Redo not implemented");
		}
	}
	
	/**
	 * Set focus to an edition panel.
	 * 
	 * If project is the project of this controler, and leaf is an AbstractData of this project,
	 * this method will set focus to the corresponding editionPanel or create such a Panel if the
	 * AbstractData wasn't open.
	 * 
	 * @param project
	 * @param leaf
	 * @return
	 */
	public boolean setFocusTo(Project project, Object leaf) {
		if(!this.project.equals(project)){
			return false;
		}else{
			Boolean exist = false;
			if(leaf instanceof AbstractData){
				for(EditionPanel ep:editionPanels){
					if(leaf.equals(ep.getData())){
						pPanel.focusData(ep);
						exist = true;
						break;
					}
				}
				if(!exist){
					EditionPanel ep = new EditionPanel(this, (AbstractData)leaf);
					editionPanels.add(ep);
					pPanel.showModel(ep);
					updateError();
				}
			}
			return true;
		}
	}
	


	@Override
	public void caretUpdate(CaretEvent arg0) {
		pPanel.updateCaretPos((JTextArea) arg0.getSource());
	}
	
	public void updateError(){
		pPanel.clearErrors();
		for(AbstractData data:project.getAMList()){
			if(data.getErrors().size()>0)
				pPanel.updateError(data.getErrors(), data);
		}
		for(AbstractData data:project.getARList()){
			if(data.getErrors().size()>0)
				pPanel.updateError(data.getErrors(), data);
		}
	}
	
	public PlasmaLabGUI getOwner() {
		return owner;
	}
	
	public String getName(){
		return project.getName();
	}
	
	public Project getProject() {
		return project;
	}
	
	public String toString(){
		return project.toString();
	}
	
	/******************************* DATA OPERATIONS **********************************************/

	@Override
	public void addData(AbstractData data, Project project) {
		openData(data, project);
		setSaved(false);
		//TODO data.addObserver(this);
	}
	
	@Override
	public void openData(AbstractData data, Project project){
		EditionPanel ep = new EditionPanel(this, data);
		ep.updateDoc();
		editionPanels.add(ep);
		pPanel.showModel(ep);
		updateError();
	}

	@Override
	public void remData(AbstractData data, Project project) {
		for(EditionPanel ep:editionPanels){
			if(ep.getData().equals(data)){
				editionPanels.remove(ep);
				break;
			}
		}
		setSaved(false);
		updateError();
	}

	@Override
	public void closeData(AbstractData data, Project project) {
		for(EditionPanel ep:editionPanels){
			if(ep.getData().equals(data)){
				editionPanels.remove(ep);
				break;
			}
		}
		pPanel.closeData(data,project);
		pViewer.closeData(data, project);
		updateError();
	}
	

	public void exportData() {
		EditionPanel ep = pPanel.getActiveDataPanel();
		AbstractData data = null;
		if(ep != null)
			data = ep.getData();
		if(data!=null){
			File file = new DialogBox(owner).exportModel(data.getName());
			if(file!=null) {
				try {
					data.exportTo(file);
				}
				catch(IOException e){
					logger.error(e.getMessage());
					JOptionPane.showMessageDialog(this.owner, 
							"Cannot export data to a file.\n"
							+ e.getMessage(),
							"Export error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void updateDataSignal(AbstractData data) {
		logger.info("updateData not implemented");
		
	}

	@Override
	public void renamed(String old, AbstractData data) {
		for(EditionPanel ep:editionPanels){
			if(ep.getData().equals(data)){
				pPanel.updateDataTabName(ep, data.getName());
				pPanel.setTabSaved(ep, data.getName(), false);
				break;
			}
		}
	}
	@Override
	public void updateData(AbstractData Data, Project project) {
		// TODO Auto-generated method stub
		
	}	
	public void updateData(String text, AbstractData data){
		data.updateContent(text);
		data.checkForErrors();
		updateError();
		project.sendUpdateDataSignal(data);
		for(EditionPanel ep:editionPanels){
			if(ep.getData().equals(data)){
				pPanel.setTabSaved(ep, data.getName(), false);
				break;
			}
		}
		setSaved(false);
	}
	
	public void rename(Project project, Object leaf) {
		String newName = null;
		if(leaf instanceof AbstractData){
			DialogBox db = new DialogBox(owner);
			newName = db.newEdit("New name:", "Rename data", ((AbstractData)leaf).getName());
			if(newName!=null)
				if(newName.length()>0){
					AbstractData data = (AbstractData)leaf;
					String oldName = data.getName();
					data.rename(newName);
					renamed(oldName, data);
					setSaved(false);
					updateError();
				}
		} else if(leaf.equals(project)){
			DialogBox db = new DialogBox(owner);
			newName = db.newEdit("New name:", "Rename project ", project.getName());
			if(newName!=null)
				if(newName.length()>0) {
					project.rename(newName);
				}
		}
		/* TODO Bug: 
		 * This is a workaround (which doesn't work perfectly)
		 * When clicking ok on the dialogBox the Focus is lost for the main Window
		 * thus the ProjectViewer is not correctly updated as long as the main Window
		 * isn't focused.
		 * This solution seems to partially solve the problem.
		 */
		owner.requestFocus();
	}
}
