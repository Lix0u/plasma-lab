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

import fr.inria.plasmalab.ui.PlasmaLabGUI;

public class NewWizard extends AbstractWizard {

	private final static ImageIcon newIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/newIcon32.png"));
	private static NewWizard wizard;
	
	private JButton cancelButton, nextButton;
	private JComboBox comboBox;
	private static final String cancelStr="Cancel", nextStr="Next", 
			wizardStr="Wizard", projectStr="Project", dataStr="Data";
	
	public NewWizard(PlasmaLabGUI window) {
		super("New...", "Select New wizard", newIcon, window);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 760645196734669089L;

	@Override
	public void actionPerformed(ActionEvent e) {
		//Cancel
		if(e.getActionCommand().equals(cancelStr)){
			this.setVisible(false);
		}
		//Project
		else if(e.getActionCommand().equals(nextStr)&&comboBox.getSelectedItem().equals(projectStr)){
			this.setVisible(false);
			NewProjectWizard.showWizard(window);
		}
		//Project
		else if(e.getActionCommand().equals(nextStr)&&comboBox.getSelectedItem().equals(dataStr)){
			this.setVisible(false);
			NewDataWizard.showWizard(window);
		}
	}

	@Override
	public JPanel createUtilityPanel() {
		JPanel utilityPanel = new JPanel(new GridBagLayout());
		JLabel label = new JLabel(wizardStr);
		comboBox = new JComboBox();
		comboBox.addItem(dataStr);
		comboBox.addItem(projectStr);
		
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
		utilityPanel.add(comboBox,gbc);
		
		return utilityPanel;
	}

	@Override
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelButton = new JButton(cancelStr);
		cancelButton.setPreferredSize(new Dimension(85, 25));
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		nextButton = new JButton(nextStr);
		nextButton.setPreferredSize(new Dimension(85, 25));
		nextButton.setText(nextStr);
		nextButton.setActionCommand(nextStr);
		nextButton.addActionListener(this);
		buttonPanel.add(nextButton);		
		return buttonPanel;
	}

	public static void showWizard(PlasmaLabGUI window) {
		if(wizard==null){
			wizard = new NewWizard(window);
		}
		int x= window.getX()+(window.getSize().width)/2-200;
		int y= window.getY()+50;
		wizard.setBounds(x, y, 400, 200);
		wizard.setVisible(true);
		
	}

}
