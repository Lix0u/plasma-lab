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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.inria.plasmalab.ui.PlasmaLabGUI;

public abstract class AbstractWizard extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1705095351676985007L;
	protected PlasmaLabGUI window;
	
	public AbstractWizard(String title, String subTitle, Icon icon, PlasmaLabGUI window){
		this.window = window;
		
		init();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createTitlePanel(title, subTitle, icon),BorderLayout.NORTH);
		getContentPane().add(createUtilityPanel(),BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(),BorderLayout.SOUTH);
	}
	
	protected void init() {
	}

	public JPanel createTitlePanel(String title, String subTitle, Icon icon){
		JPanel titlePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel jTitle = new JLabel(title);
		Font f = new Font("Dialog", Font.BOLD, 18);
		jTitle.setFont(f);
		gbc.weightx = 0.8;
		gbc.insets = new Insets(5, 15, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		titlePanel.add(jTitle, gbc);
		JLabel jSubTitle = new JLabel("\t"+subTitle);
		gbc.weightx = 0.8;
		gbc.insets = new Insets(5, 30, 0, 5);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		titlePanel.add(jSubTitle, gbc);
		JLabel jIcon = new JLabel(icon);
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0.2;
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.fill = GridBagConstraints.BOTH;
		titlePanel.add(jIcon, gbc);
		return titlePanel;
	}
	
	public abstract JPanel createUtilityPanel();
	
	public abstract JPanel createButtonPanel();
}
