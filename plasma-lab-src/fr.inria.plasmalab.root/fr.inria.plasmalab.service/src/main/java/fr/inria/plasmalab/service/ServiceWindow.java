/**
 * This file is part of fr.inria.plasmalab.service.
 *
 * fr.inria.plasmalab.service is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.service is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.service.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.service;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.distributed.Service;


public class ServiceWindow extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3063403325135551121L;
	private static final String STATUS_OFFLINE = "Disconnected", STATUS_CONNECTED = "Connected",
			STATUS_WORKING = "Working", STATUS_DONE = "Disconnected. Done in ", STATUS_WAITING = "Waiting";
	private static final String CONNECT = "Connect", DISCONNECT = "Disconnect";
	private JTextField host, port, thread;
	private JButton connect, details;
	private JLabel status;
	private boolean onlyOnce;
	
	private Controler controler;
	
	private ArrayList<Integer> idList;
	
	public static String version;

	public ServiceWindow(Controler controler){
		this.setIconImages(getFrameIcons());
		
		this.controler = controler;
		host = new JTextField(10);
		host.setText("localhost");
		port = new JTextField(5);
		port.setText("8111");
		thread = new JTextField(5);
		thread.setText("1");
		
		connect = new JButton(CONNECT);
		connect.setActionCommand(CONNECT);
		connect.addActionListener(this);
		

		details = new JButton("details");
		details.setActionCommand("details");
		details.addActionListener(this);
		details.setEnabled(false);
		
		status = new JLabel(STATUS_OFFLINE);
		onlyOnce = true;
		idList = new ArrayList<Integer>();
		
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		

		
		Properties mavenProperties = new Properties();
		try {
			mavenProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("version.properties"));
			version = (String) mavenProperties.get("version");
		} catch (IOException e1) {
			version = "";
			e1.printStackTrace();
		}
		
		this.setTitle("Plasma Service "+version);
		this.setBounds(100, 100, 400, 175);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel(new GridBagLayout());
		this.add(panel);
		//Host
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.NONE;
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		panel.add(new JLabel("Host: "),gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth=5;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		panel.add(host,gbc);
		//Port
		gbc.gridx = 9;
		gbc.gridy = 0;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		panel.add(new JLabel("Port: "),gbc);

		gbc.gridx = 11;
		gbc.gridy = 0;
		gbc.gridwidth=2;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.SOUTHWEST;
		panel.add(port,gbc);
		//Thread
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth=2;
		gbc.gridheight=3;
		gbc.anchor=GridBagConstraints.EAST;
		panel.add(new JLabel("Thread: "),gbc);

		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth=9;
		gbc.gridheight=3;
		gbc.anchor=GridBagConstraints.WEST;
		panel.add(thread,gbc);
		
		//Button
		gbc.gridx = 13;
		gbc.gridy = 3;
		gbc.gridwidth=6;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.BASELINE;
		panel.add(connect,gbc);

		gbc.gridx = 13;
		gbc.gridy = 4;
		gbc.gridwidth=6;
		gbc.gridheight=2;
		gbc.anchor=GridBagConstraints.CENTER;
		panel.add(details,gbc);
		
		//Icon
		gbc.gridx = 13;
		gbc.gridy = 0;
		gbc.gridwidth=6;
		gbc.gridheight=3;
		gbc.anchor=GridBagConstraints.BASELINE;
		panel.add(new JLabel(new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/PLASMA64.png"))),gbc);
		
		//Status
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth=2;
		gbc.gridheight=3;
		gbc.anchor=GridBagConstraints.EAST;
		panel.add(new JLabel("Status: "),gbc);
		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridwidth=9;
		gbc.gridheight=3;
		gbc.anchor=GridBagConstraints.WEST;
		panel.add(status,gbc);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(CONNECT)){
			int nbThread = Integer.parseInt(thread.getText());
			for(int i=0; i< nbThread; i++){
				Service.startService(host.getText(), port.getText(), "/", controler.getADFList(), controler.getAAFList());
			}
		} else if(e.getActionCommand().equals(DISCONNECT)){
			//Thread.stop is deprecated so we don't stop threads
			//But we tell the controler to send them the stop message.
			/*try {
				Registry registry = LocateRegistry.getRegistry(rHost, rPort);
				PlasmaInterface plasmaInterface = (PlasmaInterface) registry.lookup("plasma");
				for(Integer i:idList)
					plasmaInterface.remResource(i);
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
	}

	public void setConnected(){
		onlyOnce = true;
		status.setText(STATUS_CONNECTED);
		connect.setText(DISCONNECT);
		connect.setActionCommand(DISCONNECT);
	}
	
	public void setWorking(){
		status.setText(STATUS_WORKING);
	}
	
	public void setDone(long maxMS){
		//Only get the time of the first thread to complete
		if(onlyOnce){
			status.setText(STATUS_DONE+maxMS+"ms");
			onlyOnce = false;
		}
	}
	
	public void setDisconnected(int serviceId){
		idList.remove(new Integer(serviceId));
		if(idList.size()==0){
			connect.setText(CONNECT);
			connect.setActionCommand(CONNECT);
		}
	}

	public void setWaiting() {
		status.setText(STATUS_WAITING);
	}

	public void addId(int id) {
		idList.add(id);
	}
	public void clearIds(int id) {
		idList.clear();
	}
	
	private List<Image> getFrameIcons() {
		List<Image> icons = new ArrayList<Image>();
		icons.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread()
				.getContextClassLoader().getResource("images/PLASMA16.png")));
		icons.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread()
				.getContextClassLoader().getResource("images/PLASMA32.png")));
		icons.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread()
				.getContextClassLoader().getResource("images/PLASMA64.png")));
		icons.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread()
				.getContextClassLoader().getResource("images/PLASMA128.png")));
		return icons;
	}
}
