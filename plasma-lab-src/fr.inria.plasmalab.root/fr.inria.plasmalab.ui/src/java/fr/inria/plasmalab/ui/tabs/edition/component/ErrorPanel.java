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
package fr.inria.plasmalab.ui.tabs.edition.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class ErrorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2136875317291677407L;
	private JTextArea textArea;
	
	
	public ErrorPanel(){
		textArea = new JTextArea();
		textArea.setEditable(false);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=1;
		JScrollPane scrollArea = new JScrollPane(textArea);
		scrollArea.setLayout(new ScrollPaneLayout());
		scrollArea.setBackground(Color.WHITE);
		this.add(scrollArea,c);
	}
	
	
	public void clearErrors(){
		textArea.setText("");
	}
	
	public void setForData(AbstractData data){
		if(!textArea.getText().equals(""))
			textArea.setText(textArea.getText()+"\n\n");
		textArea.setText(textArea.getText()+
				"Error in "+data.getName()+":");
	}
	
	public void addErrors(PlasmaDataException error){
		textArea.setText(textArea.getText()+
				"\n"+error.getMessage());
	}

}
