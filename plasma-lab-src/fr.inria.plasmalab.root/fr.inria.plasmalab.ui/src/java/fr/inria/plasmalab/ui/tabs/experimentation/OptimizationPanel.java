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
package fr.inria.plasmalab.ui.tabs.experimentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.tabs.experimentation.components.AlgorithmSelectionPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.DataSelectionPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.OptimizationComponent;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;

public class OptimizationPanel extends ExperimentPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8680200404082879664L;
	
	private OptimizationComponent optimComponent;
	public static final String title = "Optimization";
	public static final String tooltip = "Define parameters to create a new optimization experiment...";

	public OptimizationPanel(PlasmaLabGUI owner, ArrayList<String> hostAdresses) {
		super(owner, hostAdresses);
		
		//OptimizationComponent listen to data selectionPanel
		dataSelPanel.setOptimizationComponent(optimComponent);
	}

	
	protected Component createWestPanel() {
		JPanel westPanel = new JPanel();

		westPanel.setLayout(new BorderLayout());
		//westPanel.add(createMethodPanel(), BorderLayout.NORTH);
		dataSelPanel = new DataSelectionPanel(this) ;
		optimComponent = new OptimizationComponent();
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(dataSelPanel, BorderLayout.NORTH);
		northPanel.add(optimComponent, BorderLayout.SOUTH);
		westPanel.add(northPanel, BorderLayout.NORTH);
		algoSelPanel = new AlgorithmSelectionPanel();
		westPanel.add(new JScrollPane(algoSelPanel), BorderLayout.CENTER);
		westPanel.add(createOnlinePanel(), BorderLayout.SOUTH);
		
		return westPanel;
	}
	
	@Override
	public OptimVariables getOptimVariables(){
		return optimComponent.getOptimVariables();
	}

}
