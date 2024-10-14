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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.controler.ExperimentationManager;
import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.tabs.experimentation.components.AlgorithmSelectionPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.DataSelectionPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.ExperimentResultPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.components.GraphicPanel;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

@SuppressWarnings("serial")
public class ExperimentPanel extends JPanel implements ActionListener, ChangeListener, ItemListener, ExperimentationListener{
	
	/** Default value for the  beat frequency, in ms */
	protected static final int BEAT_FREQUENCY = 3000;
	
	final static Logger logger = LoggerFactory.getLogger(ExperimentPanel.class);

	private final static ImageIcon initIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/initIcon16.png"));
	private final static ImageIcon startIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/startIcon16.png"));
	private final static ImageIcon stopIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/stopIcon16.png"));
	
	public static final String methodSettingsTitle = "Analysis: ", onlineTitle = "Execution: ";
	public static final String title = "Experimentation";
	public static final String tooltip = "Define parameters to create a new experiment...";
	public static final String local = "local", distributed = "distributed";
	private static final String START = "Start", STOP = "Cancel", INIT="Initialize";


	//relevant components for interactions	
	protected DataSelectionPanel dataSelPanel;
	protected AlgorithmSelectionPanel algoSelPanel;
	private ExperimentResultPanel resultPanel;
	private GraphicPanel graphicPanel;
	private ExperimentationManager expManager;
	private InterfaceIdentifier[] headers;
	
	//Start panel
	private JButton startButton;
	private JProgressBar progress;
	private static Color PROGRESSCOLOR;
	private static final Color PROGRESSERROR = new Color(171, 40, 40);
	private static final Color PROGRESSDONE = new Color(95, 170, 40);
	private static final Color PROGRESSSTOPPED = new Color(0,0,0);
	private JLabel infoMessage;
	
	//Online panel
	private JTextField portArea, threadArea, batchArea;
	private int nbThread, batch;
	private JComboBox hostArea;
	private JRadioButton onlineButton, offlineButton;
	private boolean online;
	
	private int nbConnectedService;
	private String nodeURI;
	
	private long timeInMS;
	
	private boolean firstResults;

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	public ExperimentPanel(PlasmaLabGUI owner, ArrayList<String> hostAdresses) {
		expManager = ExperimentationManager.createExperimentationManager(Controler.getControler());
		hostArea = new JComboBox();
		for(String add:hostAdresses)
			hostArea.addItem(add);
		setLayout(new BorderLayout(0, 0));
		//add(createTopPanel(), BorderLayout.NORTH);
		add(createWestPanel(), BorderLayout.WEST);
		//add(createStartPanel(), BorderLayout.SOUTH);
		setToolTipText(tooltip);
		
		
		JTabbedPane tabP = new JTabbedPane(JTabbedPane.TOP);
		//tabP.setBorder(Common.createBorder(""));
		resultPanel = new ExperimentResultPanel();
		//resultPanel.setModelListener(new ModelListener(this));
		tabP.addTab(resultPanel.getName(),resultPanel);
		tabP.setSelectedComponent(resultPanel);
		add(tabP,BorderLayout.CENTER);
		
		graphicPanel = new GraphicPanel(owner);
		tabP.addTab("plot", graphicPanel);
	}
	
	protected Component createWestPanel() {
		JPanel westPanel = new JPanel();

		westPanel.setLayout(new BorderLayout());
		//westPanel.add(createMethodPanel(), BorderLayout.NORTH);
		dataSelPanel = new DataSelectionPanel(this) ;
		westPanel.add(dataSelPanel, BorderLayout.NORTH);
		algoSelPanel = new AlgorithmSelectionPanel();
		westPanel.add(new JScrollPane(algoSelPanel), BorderLayout.CENTER);
		westPanel.add(createOnlinePanel(), BorderLayout.SOUTH);
		
		return westPanel;
	}
	
	protected JPanel createOnlinePanel(){
		JPanel onlinePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		onlinePanel.setBorder(Common.createBorder(onlineTitle));

		c.anchor = GridBagConstraints.BASELINE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=2;
		c.gridheight=1;
		offlineButton = new JRadioButton(local);
		onlineButton = new JRadioButton(distributed);
		offlineButton.addActionListener(this);
		onlineButton.addActionListener(this);
		offlineButton.setActionCommand(local);
		onlineButton.setActionCommand(distributed);
		online = false;
		onlinePanel.add(onlineButton,c);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.gridheight=1;
		onlinePanel.add(offlineButton,c);

		/*c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.gridheight=1;
		hostArea.setEnabled(false);
		onlinePanel.add(new JLabel("Host: "),c);
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth=3;
		c.gridheight=1;
		onlinePanel.add(hostArea,c);*/

		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.gridheight=1;
		portArea = new JTextField(5);
		portArea.setText("8111");
		onlinePanel.add(new JLabel("Port: "),c);
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth=3;
		c.gridheight=1;
		onlinePanel.add(portArea,c);
		
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth=1;
		c.gridheight=1;
		threadArea = new JTextField(5);
		threadArea.setText("1");
		threadArea.setEnabled(true);
		onlinePanel.add(new JLabel("Threads: "),c);
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth=3;
		c.gridheight=1;
		onlinePanel.add(threadArea,c);
		
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth=1;
		c.gridheight=1;
		batchArea = new JTextField(5);
		batchArea.setText("500");
		batchArea.setEnabled(true);
		onlinePanel.add(new JLabel("Batch: "),c);
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth=3;
		c.gridheight=1;
		onlinePanel.add(batchArea,c);

		//Change to offline
		online = false;
		onlineButton.setSelected(false);
		offlineButton.setSelected(true);
		
		
		//Add Start Panel

		c.anchor = GridBagConstraints.BASELINE;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth=2;
		c.gridheight=1;
		startButton	= new JButton();
		setToStart();
		startButton.addActionListener(this);
		Dimension dim = startButton.getPreferredSize();
		startButton.setPreferredSize(new Dimension(dim.width+25,dim.height));
		onlinePanel.add(startButton,c);
		
		c.anchor = GridBagConstraints.BASELINE;
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth=2;
		c.gridheight=1;
		c.insets = new Insets(0, 5, 0, 0);
		progress = new JProgressBar();
		progress.setMaximum(100);
		progress.setMinimum(0);
		progress.setStringPainted(true);
		progress.setString("");
		PROGRESSCOLOR = progress.getForeground();
		onlinePanel.add(progress,c);
		
		c.anchor = GridBagConstraints.BASELINE;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth=4;
		c.gridheight=1;
		infoMessage = new JLabel("-");
		onlinePanel.add(infoMessage,c);
		
		return onlinePanel;
	}
	
	public DataSelectionPanel getDataSelPanel() {
		return dataSelPanel;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals(START)){
			try{
				setToInit();
				AbstractModel model = dataSelPanel.getSelectedModel();
				if(model==null)
					throw new PlasmaParameterException("No model selected.");
				List<AbstractRequirement> requirements = dataSelPanel.getSelectedRequirement();
				if(requirements.size()<=0)
					throw new PlasmaParameterException("No requirement selected.");
				OptimVariables optimVariables = getOptimVariables();
				
				nodeURI = "";
				expManager.addExperimentationListener(this);
				if(!online){
					timeInMS = System.currentTimeMillis();
					nbThread = Integer.parseInt(threadArea.getText());
					/*for(int i=0; i<nbThread;i++){
						main.getControler().startService("localhost",portArea.getText(),"/",nodeURI);
					}*/
				}else{
					timeInMS = 0;
					nbThread = 0;
				}
				batch = Integer.parseInt(batchArea.getText());
				//Get algorithm parameters
				Map<String, Object> parametersMap = algoSelPanel.getParametersMap();
				parametersMap.put("distributed", nbThread!=1);
				expManager.setupAnExperiment(algoSelPanel.getSelectedAlgorithm(),
														model,	requirements, 
														Integer.parseInt(portArea.getText()),
														optimVariables, nbThread, batch, BEAT_FREQUENCY,
														parametersMap);
				nodeURI = expManager.startExperimentAsync();
			}catch(PlasmaParameterException e){
				logger.error("Parameter error: " + e.getMessage());
				JOptionPane.showMessageDialog(this, e.getMessage(), "Parameter error", JOptionPane.ERROR_MESSAGE);
				expManager.removeExperimentationListener(this);
				setToStart();
			}catch(PlasmaDataException e){
				logger.error("Data error: " + e.getMessage());
				JOptionPane.showMessageDialog(this, e.getMessage(), "Data error", JOptionPane.ERROR_MESSAGE);
				expManager.removeExperimentationListener(this);
				setToStart();
			}
		}else if(arg0.getActionCommand().equals(STOP)){
			expManager.stopExperiment();
		}
			else if(arg0.getSource().equals(offlineButton)){
			onlineButton.setSelected(false);
			online = false;
			offlineButton.setSelected(true);
			//portArea.setEnabled(false);
			hostArea.setEnabled(false);
			threadArea.setEnabled(true);
		}else if(arg0.getSource().equals(onlineButton)){
			onlineButton.setSelected(true);
			online = true;
			offlineButton.setSelected(false);
			//portArea.setEnabled(true);
			hostArea.setEnabled(true);
			threadArea.setEnabled(false);
			
		}
			
		
		
	}

	@Override
	public void notifyAlgorithmStarted(String nodeURI) {
		if(this.nodeURI.equals(nodeURI)){
			firstResults = true;
			resultPanel.clear();
			setInfoMessage("Algorithm running");
			progress.setForeground(PROGRESSCOLOR);
			progress.setIndeterminate(true);
			setToStop();
		}
	}

	@Override
	public void notifyAlgorithmCompleted(String nodeURI) {
		if(this.nodeURI.equals(nodeURI)){
			long sec = (System.currentTimeMillis() - timeInMS)/1000;
			long msec = (System.currentTimeMillis() - timeInMS)%1000;
			setInfoMessage("Algorithm completed - "+sec+"."+msec+"s");
			progress.setIndeterminate(false);
			progress.setForeground(PROGRESSDONE);
			progress.setValue(100);
			progress.setString("");
			nbConnectedService = 0;
			setToStart();
		}
	}

	@Override
	public void notifyAlgorithmStopped(String nodeURI) {
		if(this.nodeURI.equals(nodeURI)){
			long sec = (System.currentTimeMillis() - timeInMS)/1000;
			long msec = (System.currentTimeMillis() - timeInMS)%1000;
			if (timeInMS > 0)
				setInfoMessage("Algorithm stopped - "+sec+"."+msec+"s");
			else
				setInfoMessage("Algorithm stopped");
			progress.setIndeterminate(false);
			progress.setForeground(PROGRESSSTOPPED);
			progress.setValue(100);
			progress.setString("");
			nbConnectedService = 0;
			setToStart();
		}
	}

	@Override
	public void notifyAlgorithmError(String nodeURI, String errorMessage) {
		if(this.nodeURI.equals(nodeURI)){
			progress.setForeground(PROGRESSERROR);
			setInfoMessage("Algorithm stopped with an error.");
			JOptionPane.showMessageDialog(this, errorMessage, "Experimentation error", JOptionPane.ERROR_MESSAGE);
			progress.setIndeterminate(false);
			progress.setValue(100);
			progress.setString("");
			nbConnectedService = 0;
			setToStart();
		}
	}

	@Override
	public void notifyNewServiceConnected(String nodeURI) {
		if(this.nodeURI.equals(nodeURI)){
			nbConnectedService++;
			if (timeInMS==0)
				timeInMS = System.currentTimeMillis();
			setInfoMessage("New service connected, "+nbConnectedService+" working.");
		}
	}

	@Override
	public void notifyServiceDisconnected(String nodeURI) {
		if(this.nodeURI.equals(nodeURI)){
			nbConnectedService--;
			setInfoMessage("Service disconnected, "+nbConnectedService+" working.");
		}
	}

	@Override
	public void notifyProgress(int percent) {
		progress.setIndeterminate(false);
		progress.setValue(percent);		
	}

	@Override
	public void notifyTimeRemaining(long milliseconds) {
		progress.setString(milliseconds/1000+" seconds");
	}

	@Override
	public void publishResults(String nodeURI, List<ResultInterface> results) {
		if (results.isEmpty())
			return;
		if(firstResults){
			firstResults = false;
			//init headers with first result
			initResultTable(results.get(0));
		}
		
		//resultPanel.clearTable();
		//graphicPanel.clearResult();
		graphicPanel.setResult(results);		
		resultPanel.clear();
		
		for(ResultInterface result:results){
			Object[] values = new Object[headers.length];
			SMCResult resultSMC = (SMCResult)result;
			values[0] = resultSMC.getPr();
			values[1] = resultSMC.getCategory();
//			values[2] = resultSMC.getPr();
//			values[3] = resultSMC.getTotalCount();
//			values[4] = resultSMC.getPositiveCount();
			
			for(int i=2; i<values.length; i++){
				try {
					values[i] = result.getValueOf(headers[i].getName());
				} catch (PlasmaExperimentException e) {
					logger.warn("Unknown header name: " + headers[i].getName());
					values[i] = Double.NaN;
				}
			}
			resultPanel.addLine(values);
		}
	}
	
	public void setInfoMessage(String msg){
		infoMessage.setText(msg);
		logger.debug(msg);
	}
	

	public AlgorithmSelectionPanel getAlgoSelPanel() {
		return algoSelPanel;
	}
	
	public void setToStart(){
		startButton.setIcon(startIcon);
		startButton.setText(START);
		startButton.setActionCommand(START);
	}
	public void setToStop(){
		startButton.setIcon(stopIcon);
		startButton.setText(STOP);
		startButton.setActionCommand(STOP);
	}
	public void setToInit(){
		startButton.setIcon(initIcon);
		startButton.setText(INIT);
		startButton.setActionCommand(INIT);
	}
	
	public OptimVariables getOptimVariables(){
		return new OptimVariables();
	}
	
	private void initResultTable(ResultInterface result){
		InterfaceIdentifier[] resultHeaders = result.getHeaders();
		//InterfaceIdentifier[] otherHeaders = result.
		
		headers = new InterfaceIdentifier[resultHeaders.length+2];
		//Set in right order
		headers[0] = new ResultIdentifier("", false);
		headers[1] = new ResultIdentifier("Name", false);
		for(int i=0; i<resultHeaders.length; i++){
			headers[i+2] = resultHeaders[i];
		}
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				resultPanel.setTableHeader(headers);
			}});
	}
}
