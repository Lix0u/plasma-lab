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

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.ui.tabs.experimentation.components.jtable.LightCellRenderer;

public class SimulationPropertyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2232717232089866984L;
	

	protected JTable table;
	protected TableModel tm;


	public SimulationPropertyPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		table = new JTable();
		table.setEnabled(false);
		
		String[] column = new String[]{"Property","Value"};
		tm = new DefaultTableModel(column, 0);
		table.setModel(tm);
		table.getColumnModel().getColumn(1).setCellRenderer(new LightCellRenderer());
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.setRowHeight(30);
		this.setBorder(Common.createBorder("Properties: "));
		
		JScrollPane tableSP = new JScrollPane(table);
        tableSP.setPreferredSize(new Dimension(100, 200));
		
		this.add(tableSP);
	}
	
	public void clear(){
		((DefaultTableModel)tm).setRowCount(0);
	}

	public void addLine(String name, Object value){
		((DefaultTableModel)tm).addRow(new Object[]{name, value});
		this.repaint();
		this.revalidate();
	}

}
