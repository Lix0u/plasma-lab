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
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.workflow.data.AbstractData;

public class AlgorithmSelectionPanel extends JPanel implements ActionListener, ItemListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8260195835521952892L;
	
	//Graphical Resources
	public static final String AlgoLabel = "Algorithm: ";
	private JPanel cards;

	//Logical Resources
	protected ArrayList<InterfaceAlgorithmFactory> algos;

	//Logical & Graphical resources
	protected JComboBox algoSelector;
	protected HashMap<String, ParametersPanel> parametersPanelMap;
	
	
	public AlgorithmSelectionPanel() {
		super();
		algos = new ArrayList<InterfaceAlgorithmFactory>();
		parametersPanelMap = new HashMap<String, ParametersPanel>();
		doGraphics();
	}


	protected void doGraphics() {
		this.setLayout(new BorderLayout());
		this.add(createSelectors(),BorderLayout.NORTH);
		cards = new JPanel(new CardLayout());
		this.add(cards,BorderLayout.CENTER);
		

		this.setBorder(Common.createBorder(AlgoLabel));
		
	}
	
	public void addAlgorithmFactory(InterfaceAlgorithmFactory factory){
		algos.add(factory);
		ParametersPanel pp = new ParametersPanel(factory.getDescription(), factory.getParametersList());
		JPanel external = new JPanel(new BorderLayout());
		external.add(pp,BorderLayout.NORTH);
		cards.add(external, factory.getName());
		parametersPanelMap.put(factory.getName(), pp);
		updateProjects();
	}
	
	private JPanel createSelectors() {
		algoSelector = new JComboBox();
		Dimension dim = algoSelector.getPreferredSize();
		algoSelector.setPreferredSize(new Dimension(dim.width+130,dim.height));
		algoSelector.setEditable(false);
		algoSelector.addItemListener(this);
		algoSelector.addActionListener(this);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weightx=0.5;
		c.anchor=GridBagConstraints.BASELINE_TRAILING;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(new JLabel(AlgoLabel), c);


		c.anchor=GridBagConstraints.BASELINE_LEADING;
		//c.weightx=0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		panel.add(algoSelector, c);

		// A JPanel with a GridBagLayout have the expected behavior 
		// if it is contained by a component with LeftRightLayout (a new FlowLayout(FolwLayout.LEFT)) 
		//return Common.wrapInLeftRightLayout(panel);
		return panel;
	}	
	
	public void updateProjects(){
		algoSelector.removeAllItems();
		for(InterfaceAlgorithmFactory factory:algos){
			algoSelector.addItem(factory);
		}
	}
	
	public InterfaceAlgorithmFactory getSelectedAlgorithm(){
		return (InterfaceAlgorithmFactory) algoSelector.getSelectedItem();
	}
	
	public HashMap<String, Object> getParametersMap(){
		return parametersPanelMap.get(getSelectedAlgorithm().getName()).getParametersValues();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	    CardLayout cl = (CardLayout)(cards.getLayout());
	    cl.show(cards, (String)e.getItem().toString());
	}
	
	public void updateModelList(List<AbstractData> list){
		for(ParametersPanel asp:parametersPanelMap.values()){
			asp.updateModelList(list);
		}
	}

}
