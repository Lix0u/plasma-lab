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
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.DialogBox;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class ImportWizard extends AbstractWizard {

	final static Logger logger = LoggerFactory.getLogger(ImportWizard.class);
	
	private static final long serialVersionUID = -8689170804401561312L;
	private final static ImageIcon impIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/importIcon32.png"));
	private static ImportWizard wizard;
	
	private JButton cancelButton, finishButton, browseButton;
	private JComboBox projectBox, typeBox;
	private JCheckBox useFileName;
	private JTextField nameField, fileField;
	private File file;
	private static int dataCount=0;
	private static final String cancelStr="Cancel", finishStr="Finish", 
			nameStr="Name", typeStr="Type", projectStr="Project", browseStr="Browse",
			useFileNameStr="Use file name", fileStr="File";
	
	public ImportWizard(PlasmaLabGUI window) {
		super("Import...", "Import a model or a requirement", impIcon, window);
	}
	
	public void reinit(){
		nameField.setText("data_"+dataCount);
		nameField.setEnabled(false);
		useFileName.setSelected(true);
		fileField.setText("");
		file = null;
		projectBox.removeAllItems();
		for(ProjectControler pc:window.getPCList())
			projectBox.addItem(pc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//cancel
		if(e.getActionCommand().equals(cancelStr)){
			this.setVisible(false);
		}
		//Data
		else if(e.getActionCommand().equals(finishStr)){
			file = new File(fileField.getText());
			try {
				this.setVisible(false);
				Project project = ((ProjectControler)projectBox.getSelectedItem()).getProject();
				String name;
				if(!useFileName.isSelected())
					name = nameField.getText();
				else
					name = file.getName();
				
				//Create data using parameters
				if(typeBox.getSelectedItem() instanceof AbstractModelFactory){
					AbstractModelFactory amf = (AbstractModelFactory)typeBox.getSelectedItem();
					if(name!=null) {
						if(name.equals(""))
							name = "model_"+dataCount;
							dataCount++;
					}
					AbstractModel am = amf.createAbstractModel(name, file);
					am.checkForErrors();
					project.addModel(am);
					wizard.setVisible(false);
				}
				else if(typeBox.getSelectedItem() instanceof AbstractRequirementFactory){
					AbstractRequirementFactory arf = (AbstractRequirementFactory)typeBox.getSelectedItem();
					if(name!=null){
						if(name.equals(""))
							name = "requirement_"+dataCount;
							dataCount++;
						}
					AbstractRequirement ar = arf.createAbstractRequirement(name, file);
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
					AbstractData ad = adf.createAbstractData(name,file);
					ad.checkForErrors();
					project.addAbstractData(ad);
					wizard.setVisible(false);
				}
			}
			catch(PlasmaDataException error) {
				logger.error(error.getMessage());
				JOptionPane.showMessageDialog(window, 
						"Import file could not be read.\n"
						+ error.getMessage(),
						"Import error", JOptionPane.ERROR_MESSAGE);
			}
		}
		//Browse
		else if(e.getActionCommand().equals(browseStr)){
			DialogBox db = new DialogBox(window);
			file = db.openFile();
			if(file!=null){
				fileField.setText(file.getAbsolutePath());
				nameField.setText(file.getName());
			}
			this.toFront();
			this.repaint();
			
		}
		//CheckBox
		else if(e.getActionCommand().equals(useFileNameStr)){
			nameField.setEnabled(!useFileName.isSelected());
		}
	}

	@Override
	public JPanel createUtilityPanel() {
		JPanel utilityPanel = new JPanel(new GridBagLayout());
		JLabel nameLabel = new JLabel(nameStr);
		JLabel typeLabel = new JLabel(typeStr);
		JLabel fileLabel = new JLabel(fileStr);
		JLabel projectLabel = new JLabel(projectStr);
		browseButton = new JButton(browseStr);
		browseButton.addActionListener(this);
		nameField = new JTextField();
		fileField = new JTextField();
		//fileField.setEditable(false);
		projectBox = new JComboBox();
		useFileName = new JCheckBox(useFileNameStr);
		useFileName.addActionListener(this);
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
		//TYPE
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		utilityPanel.add(typeLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=2;
		gbc.gridwidth = 3;
		utilityPanel.add(typeBox,gbc);
		//Project		
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		utilityPanel.add(projectLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=2;
		gbc.gridwidth = 3;
		utilityPanel.add(projectBox,gbc);
		//FILE
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		utilityPanel.add(fileLabel, gbc);
		
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx=2;
		gbc.gridwidth = 2;
		utilityPanel.add(fileField,gbc);
		
		gbc.weightx = 0;
		gbc.fill =GridBagConstraints.NONE;
		gbc.gridx=4;
		gbc.gridwidth = 1;
		utilityPanel.add(browseButton,gbc);
		//Use File Name
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 5;
		utilityPanel.add(useFileName, gbc);
		//File Name
		gbc.weightx = 1;
		gbc.fill =GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		utilityPanel.add(nameField, gbc);
		
		return utilityPanel;
	}


	@Override
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelButton = new JButton(cancelStr);
		cancelButton.setPreferredSize(new Dimension(85, 25));
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
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
			wizard = new ImportWizard(window);
		}
		int x= window.getX()+(window.getSize().width)/2-200;
		int y= window.getY()+50;
		wizard.setBounds(x, y, 400, 300);
		wizard.reinit();
		wizard.setVisible(true);
	}

}
