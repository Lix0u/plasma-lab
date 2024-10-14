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
package fr.inria.plasmalab.ui.tabs.experimentation.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.ui.tabs.edition.observer.ProjectObserver;
import fr.inria.plasmalab.ui.tabs.experimentation.ExperimentPanel;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;

public class DataSelectionPanel extends JPanel implements ActionListener, ProjectObserver, ListSelectionListener {
	private static final long serialVersionUID = -8780848288393557440L;

	//Graphical Resources
	public static final String ProjectLabel = "Project: ";
	public static final String ModelLabel = "Model: ";
	public static final String ContractsLabel = "Properties: ";
	public static final String kParamTitle = "Contract time: ";
	public static final String ProjectsLabel ="File selection:";
	public static final String RequirementLabel ="Requirements: ";
	public static final int contractsSelectorDefaultwidth = 200;
	
	public static final String TIME_LABEL="time", STEP_LABEL="step";

	//Logical Resources
	protected ArrayList<Project> projects;

	//Logical & Graphical resources
	protected JComboBox projectSelector;
	protected JComboBox modelSelector;
	protected JList requirementSelector;
	//protected JList contractsSelector;
	protected JTextField kMax, kMin, kInc;
	protected JRadioButton kTime, kStep;
	
	private ExperimentPanel owner;
	
	//Data listener
	protected OptimizationComponent optimizationComponent;

	public DataSelectionPanel(ExperimentPanel owner) {
		super();
		this.owner = owner;
		projects = new ArrayList<Project>();
		doGraphics();
		//setEnabledStepTime(false);
	}
	
	public void setOptimizationComponent(OptimizationComponent optimizationComponent){
		this.optimizationComponent=optimizationComponent;
	}


	protected void doGraphics() {
		this.setLayout(new BorderLayout());
		this.add(createSelectors(), BorderLayout.NORTH);
		//this.add(createContractSelector(), BorderLayout.CENTER);
		
	}
	
	public void addProject(Project project){
		projects.add(project);
		updateProjects();
	}
	
	private JPanel createSelectors() {
		projectSelector = new JComboBox();
		Dimension dim = projectSelector.getPreferredSize();
		projectSelector.setPreferredSize(new Dimension(dim.width+130,dim.height));
		projectSelector.setEditable(false);
		projectSelector.addActionListener(this);
		modelSelector = new JComboBox();
		dim = modelSelector.getPreferredSize();
		modelSelector.setPreferredSize(new Dimension(dim.width+130,dim.height));
		modelSelector.setEditable(false);
		modelSelector.addActionListener(this);
		requirementSelector = new JList();
		requirementSelector.addListSelectionListener(this);
		requirementSelector.setAutoscrolls(true);
		requirementSelector.setFixedCellWidth(modelSelector.getPreferredSize().width);
		requirementSelector.setModel(new DefaultListModel());
		requirementSelector.setVisibleRowCount(4);
		//requirementSelector.setEditable(false);
		//requirementSelector.addActionListener(this);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		panel.setBorder(Common.createBorder(ProjectsLabel));
		c.weightx=0.5;
		c.anchor=GridBagConstraints.BASELINE_TRAILING;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(new JLabel(ProjectLabel), c);


		c.anchor=GridBagConstraints.BASELINE_LEADING;
		//c.weightx=0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(projectSelector, c);


		c.anchor=GridBagConstraints.BASELINE_TRAILING;
		//c.weightx=0.3;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(new JLabel(ModelLabel), c);


		c.anchor=GridBagConstraints.BASELINE_LEADING;
		//c.weightx=0.6;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(modelSelector, c);
		
		c.anchor=GridBagConstraints.BASELINE_TRAILING;
		//c.weightx=0.3;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(new JLabel(RequirementLabel), c);


		c.anchor=GridBagConstraints.BASELINE_LEADING;
		//c.weightx=0.6;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth=1;
		c.gridheight=1;
		JScrollPane propPanel = new JScrollPane(requirementSelector);
		panel.add(propPanel, c);
		// A JPanel with a GridBagLayout have the expected behavior 
		// if it is contained by a component with LeftRightLayout (a new FlowLayout(FolwLayout.LEFT)) 
		//return Common.wrapInLeftRightLayout(panel);
		return panel;
	}	
	
	public void updateProjects(){
		projectSelector.removeAllItems();
		for(Project project:projects)
			projectSelector.addItem(project);
		updateModels();
		updateContracts();
	}

	public void updateModels () {
		modelSelector.removeAllItems();
		Project project = (Project) projectSelector.getSelectedItem();
		if(project!=null){
			for(AbstractData model:project.getAMList())
				modelSelector.addItem(model);
		
			if(owner != null){
				AlgorithmSelectionPanel asp = owner.getAlgoSelPanel();
				if(asp != null)
					asp.updateModelList(project.getAMList());
			}
		}
	}

	public void updateContracts() {
		Project project = (Project) projectSelector.getSelectedItem();
		DefaultListModel listModel = (DefaultListModel) requirementSelector.getModel();
		listModel.clear();
		if(project!=null)
			for(AbstractData requirement:project.getARList()){
				listModel.addElement(requirement);
			}
		//TODO Add model's requirement
		requirementSelector.setModel(listModel);
	}
	

	public List<AbstractRequirement> getSelectedRequirement(){
		List<AbstractRequirement> list = new ArrayList<AbstractRequirement>();
		for(Object pi:requirementSelector.getSelectedValues()){
			list.add((AbstractRequirement) pi);
		}
		return list;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(optimizationComponent!=null){
			optimizationComponent.setOptimizationReq(getSelectedModel(),getSelectedRequirement());
		}
	}
	
	public AbstractModel getSelectedModel(){
		return (AbstractModel) modelSelector.getSelectedItem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (TIME_LABEL.equals(e.getActionCommand())) {
			kTime.setSelected(true);
			kStep.setSelected(false);
		} else if (STEP_LABEL.equals(e.getActionCommand())){
			kTime.setSelected(false);
			kStep.setSelected(true);		
		} else if (e.getSource().equals(projectSelector)){
			try{
				updateContracts();
				updateModels();
			}catch (NullPointerException ex){}
		} else if (e.getSource().equals(modelSelector)){
			try{
				updateContracts();
			}catch (NullPointerException ex){}
		}
	}
	public Boolean kIsTime(){
		return kTime.isSelected();
	}
	public Float getKMin(){
		return Float.valueOf(kMin.getText());
	}
	public Float getKMax(){
		return Float.valueOf(kMax.getText());
	}
	public Float getKInc(){
		return Float.valueOf(kInc.getText());
	}

	@Override
	public void remProject(Project project) {
		projects.remove(project);
		updateProjects();
	}

	@Override
	public void renamed(String oldName, Project project) {
		this.repaint();
	}
	
	public void setEnabledStepTime(boolean bool){
		kInc.setEnabled(bool);
		kMin.setEnabled(bool);
		kMax.setEnabled(bool);
		kTime.setEnabled(bool);
		kStep.setEnabled(bool);
	}
	
	public Project getSelectedProject(){
		return (Project)projectSelector.getSelectedItem();
	}

	@Override
	public void addData(AbstractData Data, Project project) {
		updateModels();
		updateContracts();
	}

	@Override
	public void openData(AbstractData Data, Project project) {
		// Nothing to do
	}
	@Override
	public void updateData(AbstractData Data, Project project) {
		// Nothing to do
	}


	@Override
	public void remData(AbstractData Data, Project project) {
		updateModels();
		updateContracts();
	}


	@Override
	public void closeData(AbstractData Data, Project project) {
		// Nothing to do
	}
}
