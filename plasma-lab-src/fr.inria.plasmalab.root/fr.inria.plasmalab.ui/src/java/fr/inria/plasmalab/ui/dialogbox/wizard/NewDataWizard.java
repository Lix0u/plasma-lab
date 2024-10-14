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
package fr.inria.plasmalab.ui.dialogbox.wizard;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class NewDataWizard extends AbstractWizard {
	final static Logger logger = LoggerFactory.getLogger(NewDataWizard.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1468421100497371939L;
	private final static ImageIcon newIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/newIcon32.png"));
	private static NewDataWizard wizard;
	
	private JButton cancelButton, backButton, finishButton;
	private JComboBox projectBox, typeBox;
	private JTextField nameField;
	private static int dataCount=0;
	private static final String cancelStr="Cancel", backStr="Back", finishStr="Finish", 
			nameStr="Name", typeStr="Type", projectStr="Project";
	
	public NewDataWizard(PlasmaLabGUI window) {
		super("New...", "Create a new model or requirement", newIcon, window);
	}
	
	public void reinit(){
		nameField.setText("data_"+dataCount);
		projectBox.removeAllItems();
		for(ProjectControler pc:window.getPCList())
			projectBox.addItem(pc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Back
		if(e.getActionCommand().equals(backStr)){
			this.setVisible(false);
			NewWizard.showWizard(window);
		}
		//Data
		else if(e.getActionCommand().equals(finishStr)){
			this.setVisible(false);
			Project project = ((ProjectControler)projectBox.getSelectedItem()).getProject();
			String name = nameField.getText();
			
			//Create data using parameters
			if(typeBox.getSelectedItem() instanceof AbstractModelFactory){
				AbstractModelFactory amf = (AbstractModelFactory)typeBox.getSelectedItem();
				if(name!=null){
					if(name.equals(""))
						name = "model_"+dataCount;
						dataCount++;
					}
				try {
					AbstractModel am;
					am = amf.createAbstractModel(name);
					am.checkForErrors();
					project.addModel(am);
					wizard.setVisible(false);
				} catch (PlasmaDataException err) {
					logger.error("Error while creating new data: ", err);
				}
			}
			else if(typeBox.getSelectedItem() instanceof AbstractRequirementFactory){
				AbstractRequirementFactory arf = (AbstractRequirementFactory)typeBox.getSelectedItem();
				if(name!=null){
					if(name.equals(""))
						name = "requirement_"+dataCount;
						dataCount++;
					}
				AbstractRequirement ar = arf.createAbstractRequirement(name);
				ar.checkForErrors();
				project.addRequirement(ar);
				wizard.setVisible(false);
			}
			else{
				AbstractDataFactory adf = (AbstractDataFactory) typeBox.getSelectedItem();
				if(name!=null){
					if(name.equals(""))
						name = "requirement_"+dataCount;
						dataCount++;
					}
				try {
					AbstractData ad = adf.createAbstractData(name);
					ad.checkForErrors();
					project.addAbstractData(ad);
					wizard.setVisible(false);
				} catch (PlasmaDataException err) {
					logger.error("Error while creating new data: ", err);
				}
				
			}
		}
		//Cancel
		else if(e.getActionCommand().equals(cancelStr)){
			this.setVisible(false);
		}
	}

	@Override
	public JPanel createUtilityPanel() {
		JPanel utilityPanel = new JPanel(new GridBagLayout());
		JLabel nameLabel = new JLabel(nameStr);
		JLabel typeLabel = new JLabel(typeStr);
		JLabel projectLabel = new JLabel(projectStr);
		nameField = new JTextField();
		projectBox = new JComboBox();
		//Moved to init
		//for(ProjectControler pc:window.getPCList())
		//	projectBox.addItem(pc);
		typeBox = new JComboBox();
		for(AbstractDataFactory adf:Controler.getControler().getADFList())
			typeBox.addItem(adf);
//		for(AbstractRequirementFactory arf:Controler.getControler().getARFList())
//			typeBox.addItem(arf);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		utilityPanel.add(typeLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=1;
		gbc.gridwidth = 2;
		utilityPanel.add(typeBox,gbc);

		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		utilityPanel.add(projectLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=1;
		gbc.gridwidth = 2;
		utilityPanel.add(projectBox,gbc);

		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		utilityPanel.add(nameLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=1;
		gbc.gridwidth = 2;
		utilityPanel.add(nameField,gbc);
		
		return utilityPanel;
	}


	@Override
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelButton = new JButton(cancelStr);
		cancelButton.setPreferredSize(new Dimension(85, 25));
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		backButton = new JButton(backStr);
		backButton.setPreferredSize(new Dimension(85, 25));
		backButton.addActionListener(this);
		buttonPanel.add(backButton);
		finishButton = new JButton(finishStr);
		finishButton.setPreferredSize(new Dimension(85, 25));
		finishButton.setText(finishStr);
		finishButton.setActionCommand(finishStr);
		finishButton.addActionListener(this);
		buttonPanel.add(finishButton);		
		return buttonPanel;
	}

	public static void showWizard(PlasmaLabGUI window) {
		if(wizard==null){
			wizard = new NewDataWizard(window);
		}
		int x= window.getX()+(window.getSize().width)/2-200;
		int y= window.getY()+50;
		wizard.setBounds(x, y, 400, 300);
		wizard.reinit();
		wizard.setVisible(true);
	}

}
