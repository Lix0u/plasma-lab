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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class PlotPanel extends JPanel implements ActionListener, ItemListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530615673467462862L;
	private static final Icon CLOSE_0 = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/close0.png"));
	private static final Icon CLOSE_1 = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/close1.png"));
	private static final String xStr="X: ",
								yStr="Y: ",
								zStr="Z: ",
								nameStr="Name: ",
								colorStr="Color: ",
								closeStr="close",
								plotStr="Plot";
	
	//Graphic
	private JComboBox resultsCBox;
	private JComboBox xCBox, yCBox, zCBox;
	private JTextField nameField;
	private JButton colorChooser;
	
	//Logic
	private PlotWizard wizard;
	private List<ResultInterface> results;
	private Color color;
	
	

	public PlotPanel(List<ResultInterface> results, PlotWizard owner) {
		super(new GridBagLayout());
		this.wizard = owner;
		this.results = results;
		
		this.setBorder(Common.createBorder("Plot"));
		this.setMaximumSize(new Dimension(600, 150));
		
		//First line
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=2;
		gbc.gridwidth=3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.weightx=0.6;
		resultsCBox = new JComboBox();
		this.add(resultsCBox, gbc);
		gbc.gridx=3;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.weightx=0;
		this.add(new JLabel(xStr),gbc);
		gbc.gridx=4;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.weightx=0.1;
		xCBox = new JComboBox();
		this.add(xCBox,gbc);
		gbc.gridx=6;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.weightx=0;
		this.add(new JLabel(yStr),gbc);
		gbc.gridx=7;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.weightx=0.1;
		yCBox = new JComboBox();
		this.add(yCBox,gbc);
		gbc.gridx=9;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.weightx=0;
		this.add(new JLabel(zStr),gbc);
		gbc.gridx=10;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.weightx=0.1;
		zCBox = new JComboBox();
		this.add(zCBox,gbc);
		
		gbc.gridx=12;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.weightx=0;
		// Create a JButton for the close tab button
		JButton btnClose = new JButton();
		
		// Configure icon and rollover icon for button
		btnClose.setRolloverIcon(CLOSE_1);
		btnClose.setIcon(CLOSE_0);
		btnClose.setActionCommand(closeStr);
		btnClose.setRolloverEnabled(true);
		// Set border null so the button doesn't make the tab too big
		btnClose.setBorder(null);
		btnClose.setBorderPainted(false);
		btnClose.setOpaque(false);
		// Make sure the button can't get focus, otherwise it looks funny
		btnClose.setFocusable(false);
		btnClose.setContentAreaFilled(false);
		// Put the panel together
		btnClose.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnClose.addActionListener(this);
		this.add(btnClose,gbc);
		
		
		//Second line
		gbc.gridy=2;
		gbc.gridx=1;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.weightx=0;
		this.add(new JLabel(nameStr),gbc);
		gbc.gridx=2;
		gbc.gridwidth=4;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		nameField = new JTextField();
		this.add(nameField, gbc);

		gbc.gridx=7;
		gbc.gridwidth=4;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		this.add(new JLabel(colorStr),gbc);
		gbc.gridx=11;
		gbc.gridwidth=1;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		colorChooser = new JButton();
		colorChooser.setActionCommand(colorStr);
		colorChooser.addActionListener(this);
		colorChooser.setPreferredSize(new Dimension(15, 15));
		color = Color.BLACK;
		colorChooser.setBackground(color);
		this.add(colorChooser,gbc);
		
		
		resultsCBox.addItemListener(this);
		xCBox.addItemListener(this);
		yCBox.addItemListener(this);
		zCBox.addItemListener(this);
		resultsCBox.addItem("-");
		
		Set<String> catSet = new HashSet<String>();
		for(ResultInterface ri:results){
			if(catSet.add(ri.getCategory()))
				resultsCBox.addItem(ri.getCategory());
		}
		resultsCBox.setSelectedIndex(0);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(closeStr)){
			wizard.remPlotPanel(this);
		}
		else if (e.getActionCommand().equals(colorStr)){
			color = JColorChooser.showDialog(
                    this,
                    "Choose plot Color",
					color);
			colorChooser.setBackground(color);
		}
	}



	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(resultsCBox)){
			xCBox.removeAllItems();
			yCBox.removeAllItems();
			zCBox.removeAllItems();
			xCBox.addItem("-");
			yCBox.addItem("-");
			zCBox.addItem("-");
			
			try{
				for(ResultInterface ri:results){
					if(ri.getCategory().equals(resultsCBox.getSelectedItem().toString())){
						for(InterfaceIdentifier var:ri.getHeaders()){
							xCBox.addItem(var);
							yCBox.addItem(var);
							zCBox.addItem(var);
						}
						break;
					}
				}
				
			}catch(NullPointerException ex){
			}
		}
	}

	public String getXPlot(){
		return xCBox.getSelectedItem().toString();
	}
	public String getYPlot(){
		return yCBox.getSelectedItem().toString();
	}
	public String getZPlot(){
		return zCBox.getSelectedItem().toString();
	}
	public String getCategory(){
		return resultsCBox.getSelectedItem().toString();
	}
	public String getNamePlot(){
		return nameField.getText();
	}
	public Color getColorPlot(){
		return color;
	}


}
