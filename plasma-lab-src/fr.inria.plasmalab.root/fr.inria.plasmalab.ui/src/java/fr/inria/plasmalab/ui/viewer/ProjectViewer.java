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
package fr.inria.plasmalab.ui.viewer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.ui.tabs.edition.observer.DataObserver;
import fr.inria.plasmalab.ui.tabs.edition.observer.ProjectObserver;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;


@SuppressWarnings("serial")
public class ProjectViewer extends JScrollPane implements DataObserver, ProjectObserver, MouseListener {

	final static Logger logger = LoggerFactory.getLogger(ProjectViewer.class);
	
	public final static String	projectWieverTitle = "Plasma Explorer";
	public final static int 		projectWieverWidth = 50;
	private final static ImageIcon fileOk = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/fileOk_20.png"));
		private final static ImageIcon fileError = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/fileError_20.png"));
		private final static ImageIcon fileEmptyError = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/icons/fileEmptyError_20.png"));
		private final static ImageIcon fileEmpty = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/icons/fileEmpty_20.png"));
		private final static ImageIcon projectOk = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/projectOk_20.png"));
		private final static ImageIcon projectError = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/projectError_20.png"));
		private final static ImageIcon variable = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/icons/variable_20.png"));
	
	public static String workspaceNodeTitle, projectsNodeTitle, experimentsNodeTitle,
		modelsNodeTitle, contractsNodeTitle, resourcesNodeTitle;
	
	private JTree jtree;
	
	DefaultMutableTreeNode workspaceNode;
	DefaultMutableTreeNode projectsNode; 
	DefaultMutableTreeNode experimentsNode;
	DefaultTreeModel treeModel;
	
	private PlasmaLabGUI main;
	private ViewerPopupMenu menu;

	public void initTreeModel() {
		workspaceNode = new DefaultMutableTreeNode(workspaceNodeTitle);
		projectsNode = workspaceNode;
		treeModel = new DefaultTreeModel(workspaceNode);
		jtree.setModel(treeModel);
		
		
		jtree.setCellRenderer(new DefaultTreeCellRenderer() {	
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, isLeaf, row, focused);
                
                if(value instanceof DefaultMutableTreeNode){
                	DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                	Object userObject = node.getUserObject();
                	if(userObject == null)
                		setIcon(projectOk);
                	else if(userObject instanceof AbstractData){
                		AbstractData data = (AbstractData)userObject;
                		if(data.getErrors().size()>0){
                    		if(data.isEmpty())
                    			setIcon(fileEmptyError);
                    		else
                    			setIcon(fileError);
                		}
                		else{
                			if(data.isEmpty())
                				setIcon(fileEmpty);
                			else
                    			setIcon(fileOk);
                		}
                	} else if(userObject instanceof Project){
                		Project project = (Project)userObject;
                		if(project.hasError())
                			setIcon(projectError);
                		else
                			setIcon(projectOk);
                	} else if(userObject instanceof InterfaceIdentifier)
                		setIcon(variable);
                	else
                		setIcon(projectOk);
                }
                return c;
            }
        });
		
	}

	public ProjectViewer(PlasmaLabGUI main) {

		Properties stringValues = main.getStringValues();
		workspaceNodeTitle = stringValues.getProperty("workspace");
		projectsNodeTitle = stringValues.getProperty("projects");
		experimentsNodeTitle = stringValues.getProperty("experiments");
		modelsNodeTitle = stringValues.getProperty("models");
		contractsNodeTitle = stringValues.getProperty("requirements");
		resourcesNodeTitle = stringValues.getProperty("resources");
		
		jtree = new JTree();
		jtree.setBorder(Common.createBorder("Project Explorer"));
		jtree.setVisibleRowCount(projectWieverWidth);
		jtree.setRootVisible(false);
		jtree.setShowsRootHandles(true);
		this.setViewportView(jtree);
		initTreeModel();
		this.main = main;
		
		jtree.addMouseListener(this);
		menu = new ViewerPopupMenu();
	}
	
	public void addProject(Project project) {
		// Building the new node
		DefaultMutableTreeNode projectNode = new DefaultMutableTreeNode(project);
		DefaultMutableTreeNode modelsNode = new DefaultMutableTreeNode(modelsNodeTitle);
		DefaultMutableTreeNode contractsNode = new DefaultMutableTreeNode(contractsNodeTitle);
		DefaultMutableTreeNode resourcesNode = new DefaultMutableTreeNode(resourcesNodeTitle);
		projectNode.add(modelsNode);
		projectNode.add(contractsNode);
		projectNode.add(resourcesNode);
		for (AbstractData model: project.getAMList()){
			if(model instanceof AbstractModel)
				modelsNode.add(new DefaultMutableTreeNode(model));
			else
				resourcesNode.add(new DefaultMutableTreeNode(model));
		}
		for (AbstractData requirement: project.getARList()){
			if(requirement instanceof AbstractRequirement)
				contractsNode.add(new DefaultMutableTreeNode(requirement));
			else
				resourcesNode.add(new DefaultMutableTreeNode(requirement));
		}
		for (AbstractData data:project.getADList())
			resourcesNode.add(new DefaultMutableTreeNode(data));
		// Thus, we insert it into the existing TreeModel
	    treeModel.insertNodeInto(projectNode, projectsNode, projectsNode.getChildCount());
	    //Unfold the paths of the new project node:
	    if (modelsNode.getChildCount() > 0) {
	    	DefaultMutableTreeNode fstChild = (DefaultMutableTreeNode) modelsNode.getFirstChild();
	    	jtree.scrollPathToVisible(new TreePath(fstChild.getPath()));
	    	if (contractsNode.getChildCount() > 0) {
		    	DefaultMutableTreeNode sndChild = (DefaultMutableTreeNode) contractsNode.getFirstChild();
		    	jtree.scrollPathToVisible(new TreePath(sndChild.getPath()));
	    	}
	    } else
	    	jtree.scrollPathToVisible(new TreePath(modelsNode.getPath()));
	}
	
	private DefaultMutableTreeNode getChildNode(DefaultMutableTreeNode parent, Object target) throws NoSuchElementException {
		DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getFirstChild(); 
		
		while (child != null) {
			if (child.getUserObject() == target) 
				return child; 
			child = (DefaultMutableTreeNode) parent.getChildAfter(child);
		}
		throw new NoSuchElementException();
	}
	
	
	private DefaultMutableTreeNode locateProject(Project project) {
		return getChildNode(projectsNode, project);
	}
	
	
	private void addData(Project project, String category, Object data) {

		DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(data);
		dataNode.setUserObject(data);
		DefaultMutableTreeNode categoryNode = getChildNode(locateProject(project), category);
		if(data instanceof AbstractModel){
			AbstractModel model = (AbstractModel) data;
			
			// If model has no errors, display its identifiers
			if(model.getErrors().size()<=0)
				for(InterfaceIdentifier id:model.getIdentifiers().values()){
					DefaultMutableTreeNode idNode = new DefaultMutableTreeNode(id.getName());
					idNode.setUserObject(id);
					dataNode.add(idNode);
				}
		}
		treeModel.insertNodeInto(dataNode, categoryNode, categoryNode.getChildCount());
		jtree.scrollPathToVisible(new TreePath(dataNode.getPath()));
	}
	
	private void updateData(Project project, String category, Object data) {
		
		DefaultMutableTreeNode categoryNode = getChildNode(locateProject(project), category);
		DefaultMutableTreeNode oldNode = getChildNode(categoryNode, data);
		oldNode.removeAllChildren();
		int index = categoryNode.getIndex(oldNode);
		treeModel.removeNodeFromParent(oldNode);
		DefaultMutableTreeNode replacingNode = new DefaultMutableTreeNode(data);
		replacingNode.setUserObject(data);
		if(data instanceof AbstractModel){
			AbstractModel model = (AbstractModel) data;
			
			// If model has no errors, display its identifiers
			if(model.getErrors().size()<=0)
				for(InterfaceIdentifier id:model.getIdentifiers().values()){
					DefaultMutableTreeNode idNode = new DefaultMutableTreeNode(id.getName());
					idNode.setUserObject(id);
					replacingNode.add(idNode);
				}
		}
		treeModel.insertNodeInto(replacingNode, categoryNode, index);
		jtree.scrollPathToVisible(new TreePath(replacingNode.getPath()));
	}
	
	private void removeData(Project project, String category, Object data) {
		DefaultMutableTreeNode categoryNode = getChildNode(locateProject(project), category);
		DefaultMutableTreeNode toRemoveNode = getChildNode(categoryNode, data);
		treeModel.removeNodeFromParent(toRemoveNode);
		jtree.scrollPathToVisible(new TreePath(categoryNode.getPath()));
	}
	// Project
	@Override
	public void remProject(Project project) {
		try {
			DefaultMutableTreeNode toRemoveNode = getChildNode(projectsNode, project);
			treeModel.removeNodeFromParent(toRemoveNode);
			jtree.scrollPathToVisible(new TreePath(projectsNode.getPath()));
		}
		catch(NoSuchElementException e) {
			
		}
	}
	
	// DATA
	@Override
	public void addData(AbstractData data, Project project) {
		if(data instanceof AbstractModel)
			addData(project, modelsNodeTitle, data);
		else if (data instanceof AbstractRequirement)
			addData(project, contractsNodeTitle, data);
		else
			addData(project, resourcesNodeTitle, data);
	}
	@Override
	public void openData(AbstractData Data, Project project) {
		// Nothing to do
	}
	@Override
	public void updateData(AbstractData Data, Project project) {
		if(Data instanceof AbstractModel)
			updateData(project, modelsNodeTitle, Data);
		else if (Data instanceof AbstractRequirement)
			updateData(project, contractsNodeTitle, Data);
		else
			updateData(project, resourcesNodeTitle, Data);
	}
	
	@Override
	public void remData(AbstractData data, Project project) {
		if(data instanceof AbstractModel)
			removeData(project, modelsNodeTitle, data);
		else if (data instanceof AbstractRequirement)
			removeData(project, contractsNodeTitle, data);
		else
			removeData(project, resourcesNodeTitle, data);
	}
	@Override
	public void updateDataSignal(AbstractData data) {
		//TODO 
		// Nothing to do?
		//updateData(project, modelsNodeTitle, model);
	}
	@Override
	public void closeData(AbstractData data, Project project) {
		// TODO change icon
	}

	@Override
	public void renamed(String oldName, Project project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renamed(String old, AbstractData Data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try{
			if (e.getButton()==MouseEvent.BUTTON3){
				int row = jtree.getClosestRowForLocation(e.getX(), e.getY());
				jtree.setSelectionRow(row);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
				if(node.getLevel()==3|| node.getLevel() ==1){
					menu.setTarget(node);		
					
					menu.show(e.getComponent(), e.getX(), e.getY());
					menu.delete.setEnabled(true);
				}
			}
			else if (e.getClickCount() == 2){
				TreePath path = jtree.getLeadSelectionPath();
				Object leaf = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
				Object project = ((DefaultMutableTreeNode)path.getPathComponent(1)).getUserObject();
				main.setFocusTo(project, leaf);			
			}
		}catch(NullPointerException exc){
			//A null pointer exception can occur here if there is no nodes (empty project)
			logger.debug(exc.getMessage(), exc);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	class ViewerPopupMenu extends JPopupMenu implements ActionListener{
		private static final String renameItem = "Rename", deleteItem ="Delete", closeItem="Close";
		
		public JMenuItem rename, delete, close;
		
		public ViewerPopupMenu() {
			super();
			
			close = new JMenuItem(closeItem);
			close.setActionCommand(closeItem);
			close.addActionListener(this);
			delete = new JMenuItem(deleteItem);
			delete.setActionCommand(deleteItem);
			delete.addActionListener(this);
		}
		
		public void setTarget(DefaultMutableTreeNode node){
			this.removeAll();
			rename = new JMenuItem(renameItem);
			rename.setActionCommand(renameItem);
			rename.addActionListener(this);
			this.add(rename);
			
			if(node.getLevel()==1){ // PROJECT
				this.add(close);
			}
			if(node.getLevel()==3){ // DATA
				this.add(delete);
				AbstractData data = (AbstractData) node.getUserObject();
				for (AbstractFunction f : data.getFunctions()) {
					JMenuItem fmenu = new JMenuItem(f.getName());
					this.add(fmenu);
					fmenu.setActionCommand(f.getName());
					fmenu.addActionListener(this);
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			TreePath path = jtree.getLeadSelectionPath();
			Object leaf = ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
			Project project = (Project)((DefaultMutableTreeNode)path.getPathComponent(1)).getUserObject();

			if(command.equals(renameItem)){
				project.getpControler().rename(project, leaf);
			}else if(command.equals(deleteItem)){
				if(leaf instanceof AbstractData){
					project.remData((AbstractData) leaf);
				}
			}else if(command.equals(closeItem)){
				if(leaf instanceof Project){
					project.getpControler().closeProject();
				}
			}
			else if(leaf instanceof AbstractData){
				AbstractData data = (AbstractData) leaf;
				for (AbstractFunction f : data.getFunctions()) {
					if (f.getName() == command) {
//						Object[] parameters =  new Object[2];
//						parameters[0]=data;
//						parameters[1]=project;
						try {
							f.execute(data);
						} catch (PlasmaDataException e) {
							logger.error("Error while executing function " + f.getName() + " : " + e.getMessage());
						}
					}
				}
			}
		}
		
	}
}