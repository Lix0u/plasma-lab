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
package fr.inria.plasmalab.ui.tabs.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ConsolePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -1404532918642971911L;
	final static Logger logger = LoggerFactory.getLogger(ConsolePanel.class);

	public final static String title = "Console";
	public final static String tooltip =
			"Display all the command-line informations outputed by Plasma";

			
	public JTextArea console;

	public ConsolePanel() {
		setLayout(new BorderLayout(0, 0));

		add(this.createConsole(), BorderLayout.CENTER);
		add(this.createButtonPanel(), BorderLayout.SOUTH);
		this.loadFile("/etc/samba/smb.conf");

	}

	public JComponent createConsole() {
		console = new JTextArea();
		console.setEditable(false);
		console.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		console.setForeground(Color.WHITE);
		console.setBackground(Color.BLACK);
		
		JScrollPane scrollP = new JScrollPane(console);
		scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		return scrollP;
	}
	
	
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton clearButton = new JButton("Clear Log");
		clearButton.addActionListener(this);
		clearButton.setActionCommand(clearAction);
		buttonPanel.add(clearButton);
		
		JButton saveButton = new JButton("Save As...");
		saveButton.addActionListener(this);
		saveButton.setActionCommand(saveAction);
		buttonPanel.add(saveButton);
		return buttonPanel;
	}
	
	private final static String clearAction = "Clear";
	private final static String saveAction = "Save";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (clearAction.equals(e.getActionCommand())) {
			console.setText("");
		} else if (saveAction.equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(this, "SelectAll button is pressed", "Plasma Info", JOptionPane.INFORMATION_MESSAGE);
		}
	} 
		
	
	
	
	public void loadFile(String filename) {
		try {
			File f = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				console.append(strLine + "\n");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}


