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
package fr.inria.plasmalab.ui.dialogbox;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class AboutDialog extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = -554152449049055395L;
	private static String title = "About PLASMA Lab";
	private final static ImageIcon icon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/PLASMAsplash.png"));
	private final static String version = "Version "+Controler.getVersion(),
								buildId = "Build id: "+Controler.getBuildID(),
			copyright = "Copyright INRIA \n";
	private static URI link = null;
	
	public AboutDialog(PlasmaLabGUI main){
		//Frame size
		int x= main.getX()+(main.getSize().width)/2-200;
		int y= main.getY()+50;
		this.setBounds(x, y, 500, 400);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle(title);
		this.setIconImages(main.getFrameIcons());
		
		try {
			link = new URI("https://project.inria.fr/plasma-lab/");
		} catch (URISyntaxException e) {}
		
		//TabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();
		this.add(tabbedPane);
		
		//PLASMA Tab
		JPanel plasmaPanel = new JPanel(new GridBagLayout());
		plasmaPanel.setBackground(Color.white);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=4;
		gbc.gridwidth=3;
		gbc.weightx=1;
		gbc.weighty=0.66;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.fill = GridBagConstraints.BOTH;
		plasmaPanel.add(new JLabel(icon), gbc);
		gbc.gridx=1;
		gbc.gridy=4;
		gbc.gridheight=1;
		gbc.gridwidth=2;
		gbc.weightx=0;
		gbc.weighty=0.15;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(5, 0, 0, 10);
		plasmaPanel.add(new JLabel(version),gbc);
		gbc.gridy=5;
		gbc.insets = new Insets(5, 0, 5, 10);
		plasmaPanel.add(new JLabel(buildId),gbc);
		gbc.gridx=0;
		gbc.gridwidth=1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 10, 0, 5);
		JButton linkButton = new JButton(link.toString());
		linkButton.setText("<HTML><FONT color=\"#000099\"><U>"+link+"</U></FONT></HTML>");
		linkButton.setBorderPainted(false);
		linkButton.setOpaque(false);
		linkButton.setBackground(Color.WHITE);
		linkButton.setToolTipText(link.toString());
		linkButton.addActionListener(this);
		plasmaPanel.add(linkButton,gbc);
		
		
		
		//Plugins Tab
		JTable table = new JTable() {
			private static final long serialVersionUID = -984446150285910301L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //Disallow the editing of any cell
			  }
		};
		TableModel tm = new DefaultTableModel(new String[]{"Name", "Id", "Type", "Distributed"}, 0);
		for(AbstractDataFactory adf: Controler.getControler().getADFList()){
			if(adf instanceof AbstractModelFactory)
				((DefaultTableModel)tm).addRow(new String[]{adf.getName(),adf.getId(),"Model","NA"});
			else if(adf instanceof AbstractRequirementFactory)
				((DefaultTableModel)tm).addRow(new String[]{adf.getName(),adf.getId(),"Requirement","NA"});
			else
				((DefaultTableModel)tm).addRow(new String[]{adf.getName(),adf.getId(),"NA","NA"});
		}
		for(InterfaceAlgorithmFactory iaf: Controler.getControler().getAAFList()){
			((DefaultTableModel)tm).addRow(new String[]{iaf.getName(),iaf.getId(),"Algorithm",Boolean.toString(iaf.isDistributed())});
		}
		table.setModel(tm);
		JScrollPane pluginPanel = new JScrollPane(table);
		pluginPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pluginPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tabbedPane.addTab("Plugins", pluginPanel);
		

		
		
		//License Tab
		JPanel licensePanel = new JPanel();
		licensePanel.add(new JTextArea(copyright));
		
		
		
		//Add to tabbedPane
		tabbedPane.addTab("PLASMA Lab", plasmaPanel);
		tabbedPane.addTab("Plugins", pluginPanel);
		tabbedPane.addTab("License", licensePanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (Desktop.isDesktopSupported()) {
	      try {
	        Desktop.getDesktop().browse(link);
	      } catch (IOException err) { /* TODO: error handling */ }
	    } else { /* TODO: error handling */ }
	}
}
