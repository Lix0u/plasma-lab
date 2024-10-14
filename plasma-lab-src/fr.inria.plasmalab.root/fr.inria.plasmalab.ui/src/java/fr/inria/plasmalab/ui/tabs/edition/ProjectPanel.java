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
package fr.inria.plasmalab.ui.tabs.edition;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.ui.tabs.edition.component.ErrorPanel;
import fr.inria.plasmalab.ui.tabs.edition.observer.ProjectObserver;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class ProjectPanel extends JPanel implements ProjectObserver, ChangeListener, ActionListener{
	private static final long serialVersionUID = 6327663522168962858L;
	public static int counter = 1;

	/* Graphical resources of the class */
	private static double sizeWeightVertical = 0.8;
	private JSplitPane splitPane;
	private ErrorPanel errorPane;
	private JTabbedPane tabDataPane;
	//TODO Add ErrorPanel JTabbedPane
	
	/* Controler and logical*/
	private ProjectControler pControler;
	private Project project;

	/** Constructors: Build a project */
	public ProjectPanel(ProjectControler pControler, Project project){
		//Keeped for history
		//Properties sv = pControler.getOwner().getStringValues();
		//contractEditorTitle = sv.getProperty("requireEdit");
		
		//Logical
		this.project = project;
		//TODO Add Observer		
		
		//Graphical construction
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(sizeWeightVertical);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane,BorderLayout.CENTER);
		//Model edition area
		tabDataPane = new JTabbedPane(JTabbedPane.TOP);
		tabDataPane.addChangeListener(this);
		
		//Error display panel
		errorPane = new ErrorPanel();
		
		//Set Split Pane
		splitPane.setLeftComponent(tabDataPane);
		splitPane.setRightComponent(errorPane);
		
		//Controler
		this.pControler = pControler;
		
		init();
	}
	
	
	private void init(){
		//Todo show data that were previously opened
		/*for(Model m:project.getModels())
			pControler.addModel(m, project);
		for(PropertyInterface c:project.getContracts())
			pControler.addContract(c, project);*/
	}
	
	public void close(){
		tabDataPane.removeAll();
		//TODO clean error panel;
		//TODO Remove Observer;
	}
	
	public void replace(Project project){
		tabDataPane.removeAll();
		this.project = project;
		//TODO clear();
		init();
	}

	public void focusData(EditionPanel mp){
		tabDataPane.setSelectedComponent(mp);
	}
	public void showModel(EditionPanel mp) {
		tabDataPane.insertTab(mp.getName(), null, mp, mp.getData().getId(), tabDataPane.getTabCount());
		focusData(mp);
	}
		
	@Override
	public String getName(){
		try{
		return project.getName();
		}catch(NullPointerException e){
			return "Unnamed project!";
		}
	}
	
	public Project getProject(){
		return project;
	}
	public ProjectControler getPControler(){
		return pControler;
	}
	
	public EditionPanel getActiveDataPanel(){
		return (EditionPanel)tabDataPane.getSelectedComponent();
	}
	
	public void updateDataTabName(Component tabbed, String name){
		for(int i=0; i<tabDataPane.getTabCount(); i++){
			if(tabDataPane.getComponentAt(i).equals(tabbed)){
				tabDataPane.setTitleAt(i,name);
				break;
			}
		}
	}
	
	public void setTabSaved(Component tabbed, String name, boolean saved){
		for(int i=0; i<tabDataPane.getTabCount(); i++){
			if(tabDataPane.getComponentAt(i).equals(tabbed)){
				if(saved)
					tabDataPane.setTitleAt(i,name);
				else
					tabDataPane.setTitleAt(i, "*"+name);
				break;
			}
		}
	}
	
	public void updateCaretPos(JTextArea source){
		int x=0;
		int y=0;
		int caretpos = source.getCaretPosition();
		try {
			x = source.getLineOfOffset(caretpos);
			y = caretpos - source.getLineStartOffset(x);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pControler.getOwner().setCaretPos(x+1, y+1);
		File file = getActiveDataPanel().getData().getOrigin();
		if (file == null)
			file = project.getFile();
		String type = getActiveDataPanel().getData().getId();
		if (file != null)
			pControler.getOwner().setTypeLabel(file.getAbsolutePath() + " : " + type);
		else
			pControler.getOwner().setTypeLabel(type);
	}
	
	public void clearErrors(){
		errorPane.clearErrors();
	}
	
	public void updateError(List<PlasmaDataException> errors, AbstractData data){
		errorPane.setForData(data);
		for(PlasmaDataException error:errors){
			errorPane.addErrors(error);
		}
	}


	@Override
	public void remProject(Project project) {
		// Nothing to do?
		// TODO ?
	}

	@Override
	public void addProject(Project project) {
		// Nothing to do	
	}

	@Override
	public void addData(AbstractData Data, Project project) {
		// handled by ProjectControler		
	}
	@Override
	public void openData(AbstractData Data, Project project) {
		// handled by ProjectControler		
	}
	@Override
	public void updateData(AbstractData Data, Project project) {
		// handled by ProjectControler		
	}
	@Override
	public void remData(AbstractData Data, Project project) {
		// handled by ProjectControler
		closeData(Data, project);
	}
	@Override
	public void closeData(AbstractData Data, Project project) {
		for(int i=0; i<tabDataPane.getTabCount(); i++){
			if(((EditionPanel)tabDataPane.getComponentAt(i)).getData().equals(Data)){
				tabDataPane.removeTabAt(i);
				break;
			}
		}
	}


	@Override
	public void renamed(String oldName, Project project) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		EditionPanel editPanel = (EditionPanel) tabDataPane.getSelectedComponent();
		if(editPanel==null){
			pControler.getOwner().setCaretPos(0,0);
			pControler.getOwner().setTypeLabel("");
		}else
			updateCaretPos(editPanel.getTextArea());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
        int X = (int) tabDataPane.getMousePosition().getX();
        int Y = (int) tabDataPane.getMousePosition().getY();

        int i = tabDataPane.getUI().tabForCoordinate(tabDataPane, X, Y);
        AbstractData data = ((EditionPanel)tabDataPane.getComponentAt(i)).getData();
        pControler.closeData(data, project);
	}
}
