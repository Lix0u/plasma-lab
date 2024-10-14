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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.tabs.experimentation.components.jtable.RowNumberTable;
import fr.inria.plasmalab.ui.tabs.experimentation.components.jtable.SelectableCellRendererComponent;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class ResultPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -2889178345073418340L;
	final static Logger logger = LoggerFactory.getLogger(ResultPanel.class);
	//graphical Resources
	public final static String prefixIdent = "Experiment ";
	public static int counter = 1;
	
	protected JTable table;
	//private ArrayList<TableModel> tmS;
	protected TableModel tm;
	protected static final String tooltip = "";
	protected ResultPopupMenu menu;
	private int selection;

	

	public ResultPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		menu = new ResultPopupMenu(this);
		setToolTipText(tooltip);
		setName("Simulation results");
		//tmS = new ArrayList<TableModel>();
		table = new JTable(){
			  /**
			 * 
			 */
			private static final long serialVersionUID = -5267701455104927812L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //Disallow the editing of any cell
			}
			//Scrollable if not fitting (not resized)
			public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
		};
		table.setEnabled(true);
		table.addMouseListener(this);
		table.setDefaultRenderer(Object.class, new SelectableCellRendererComponent());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		addLPColumn(table, scroll);
		
		this.add(scroll);
	}
	
	private void addLPColumn(JTable table, JScrollPane scp){
        JTable rowTable = new RowNumberTable(table);
        scp.setRowHeaderView(rowTable);
        scp.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
        scp.repaint();
    }
	
	public void setTableHeader(InterfaceIdentifier[] headers){
		String[] col = new String[headers.length];
		for(int i = 0; i<headers.length; i++){
			col[i] = headers[i].getName();
		}
		tm = new DefaultTableModel(col, 0);
		table.setModel(tm);
		for(int i = 0; i<headers.length; i++){
			table.getColumnModel().getColumn(i).setPreferredWidth(100);
		}
		this.repaint();
		this.revalidate();
	}
	
	public void addLine(Object[] line){
		((DefaultTableModel)tm).addRow(line);
		this.repaint();
		this.revalidate();
	}
	
	public void remLine(int index){
		((DefaultTableModel)tm).removeRow(index);
		this.repaint();
		this.revalidate();
	}
	
	public void clear(){
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		while(dm.getRowCount() > 0)
		{
		    dm.removeRow(0);
		}
		this.repaint();
		this.revalidate();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON3){
			menu.show(this, e.getX(), e.getY());	
		}else{
	        selection= table.getSelectedRow();
	        table.clearSelection();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
        table.getSelectionModel().setSelectionInterval(selection, selection);
        //now you need to select the row here check below link
		
	}
	
	public void addListSelectionListener(ListSelectionListener lse){
		table.getSelectionModel().addListSelectionListener(lse);
	}
	
	public int getSelectedRow(){
		return selection;
	}
	
	public void unselectRow(){
		selection=-1;
		table.getSelectionModel().clearSelection();
	}
	
	class ResultPopupMenu extends JPopupMenu implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8616677859442051054L;
		private static final String exportItem = "export";
		private Component owner;
		
		public JMenuItem export;
		
		public ResultPopupMenu(Component owner){
			super();
			this.owner = owner;
			
			export = new JMenuItem(exportItem);
			export.setActionCommand(exportItem);
			export.addActionListener(this);
			this.add(export);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getActionCommand().equals(exportItem)){
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(PlasmaLabGUI.getWorkingDir());
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				String suggestion = "results.tsv";
				jfc.setSelectedFile(new File(suggestion));
				if(jfc.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION){
					File target = jfc.getSelectedFile();
					int val;
					if (target.exists()) {
						val = JOptionPane.showConfirmDialog(this, "File " + target.getName() + " is already existing. Do you want to replace it?",
								"Plasma Info", JOptionPane.OK_CANCEL_OPTION);
					} else {
						val = JOptionPane.OK_OPTION;
					}
					if (val == JOptionPane.OK_OPTION)
						try {
							BufferedWriter bw = new BufferedWriter(new FileWriter(target));
							//for(TableModel tm:tmS){
							for(int i =0; i<tm.getColumnCount(); i++){
								bw.write(tm.getColumnName(i)+"\t");
							}
							bw.newLine();
							for(int j = 0; j<tm.getRowCount(); j++){
								for(int i=0; i<tm.getColumnCount(); i++){
									bw.write(tm.getValueAt(j, i)+"\t");
								}
								bw.newLine();
							}
							bw.close();
						} catch (IOException e) {
							logger.warn(e.getMessage(), e);
							JOptionPane.showMessageDialog(this, "An error occured while exporting your results.\n"+
							e.getMessage(), "Results not saved", JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		}
	}
}
