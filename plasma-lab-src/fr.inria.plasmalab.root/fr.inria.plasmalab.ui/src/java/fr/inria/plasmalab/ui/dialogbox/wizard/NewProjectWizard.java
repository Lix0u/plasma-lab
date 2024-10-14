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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;

public class NewProjectWizard extends AbstractWizard {

	private final static ImageIcon newIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/newIcon32.png"));
	private static NewProjectWizard wizard;
	
	private JButton cancelButton, backButton, finishButton;
	private JTextField nameField;
	private static int projectCount=0;
	private static final String cancelStr="Cancel", backStr="Back", finishStr="Finish", 
			nameStr="Name";
	
	public NewProjectWizard(PlasmaLabGUI window) {
		super("New...", "Create a new project", newIcon, window);
	}
	
	public void reinit(){
		nameField.setText("project_"+projectCount);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 807978705725247654L;

	@Override
	public void actionPerformed(ActionEvent e) {
		//back
		if(e.getActionCommand().equals(backStr)){
			this.setVisible(false);
			NewWizard.showWizard(window);
		}
		//Project
		else if(e.getActionCommand().equals(finishStr)){
			this.setVisible(false);
			newProject(window);
		}
		//Cancel
		else if(e.getActionCommand().equals(cancelStr)){
			this.setVisible(false);
		}
	}


	@Override
	public JPanel createUtilityPanel() {
		JPanel utilityPanel = new JPanel(new GridBagLayout());
		JLabel label = new JLabel(nameStr);
		nameField = new JTextField();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.insets = new Insets(5, 15, 5, 15);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		utilityPanel.add(label, gbc);
		
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
			wizard = new NewProjectWizard(window);
		}
		int x= window.getX()+(window.getSize().width)/2-200;
		int y= window.getY()+50;
		wizard.setBounds(x, y, 400, 200);
		wizard.reinit();
		wizard.setVisible(true);
	}
	


	private void newProject(PlasmaLabGUI main){
		String name = nameField.getText();
		ProjectControler newPC = new ProjectControler(main);
		main.getPCList().add(newPC);
		if(name!=null){
			if(!name.equals(""))
			    newPC.createProject(name);
			else{
				newPC.createProject("project_"+projectCount);
				projectCount++;
			}
		}
		
	}

}
