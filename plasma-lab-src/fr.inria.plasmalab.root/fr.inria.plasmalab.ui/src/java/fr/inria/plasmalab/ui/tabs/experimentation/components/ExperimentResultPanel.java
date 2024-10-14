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

import javax.swing.table.DefaultTableModel;

import fr.inria.plasmalab.ui.tabs.experimentation.components.jtable.LightCellRenderer;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class ExperimentResultPanel extends ResultPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5442013558228750687L;
	
	public ExperimentResultPanel(){
		super();
		setName("Experimentation results");
	}
	
	@Override
	public void setTableHeader(InterfaceIdentifier[] column){
		super.setTableHeader(column);
		table.getColumnModel().getColumn(0).setCellRenderer(new LightCellRenderer());
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.repaint();
		this.revalidate();
	}
	
	@Override
	public void addLine(Object[] line){
		((DefaultTableModel)tm).addRow(line);
		this.repaint();
		this.revalidate();
	}
}
