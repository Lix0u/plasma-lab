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
package fr.inria.plasmalab.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.wizard.GenericWizards;
import fr.inria.plasmalab.ui.menubar.IconBar;
import fr.inria.plasmalab.ui.menubar.PlasmaMenuBar;
import fr.inria.plasmalab.ui.structure.Project;
import fr.inria.plasmalab.ui.tabs.edition.ProjectPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.ExperimentPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.OptimizationPanel;
import fr.inria.plasmalab.ui.tabs.experimentation.SimulationPanel;
import fr.inria.plasmalab.ui.viewer.ProjectViewer;


/**
 * 
 * @author kevin.corre@inria.fr
 *
 * Plasma Lab main window.
 */
public class PlasmaLabGUI extends JFrame {

	private static File workingDir;
	
	private static final long serialVersionUID = -4091681729836853576L;
	
	final static Logger log_ = LoggerFactory.getLogger(PlasmaLabGUI.class);

	private final static String title="Plasma Lab";
	private final static String PROJECT_TOOLTIP = "Edit your models and properties";

	public JTabbedPane activitiesPane;
	private ExperimentPanel experimentPane;
	private OptimizationPanel optimizationPane;
	private SimulationPanel simulationPanel;
	private ProjectViewer projectViewer;

	/* MVC controler */
	private ArrayList<ProjectControler> pCList;

	/* Host adresses */
	private ArrayList<String> hostAdresses;

	private Properties stringValues;

	private GenericWizards adWizard;
	private JLabel caretPos;
	private JLabel typeLabel;

	public Properties getStringValues() {
		return stringValues;
	}

	/**
	 * Create the application.
	 */
	public PlasmaLabGUI(Controler controler) {
		super(title);
		log_.debug("starting plasmaGUI");
		stringValues = new Properties();
		try {
			stringValues.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("strings.values"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		hostAdresses = new ArrayList<String>();		
		try {
			Enumeration<NetworkInterface> e=NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n=(NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i= (InetAddress) ee.nextElement();
					if(!(i instanceof Inet6Address))
						hostAdresses.add(i.getHostAddress());
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setWorkingDirPath(controler.getWorkingDirPath());
		pCList = new ArrayList<ProjectControler>();
		
		setIconImages(getFrameIcons());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("static-access")
	public void initialize() {
		//Frame size
		this.setBounds(100, 100, 1239, 862);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		//Status Panel
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout());
		JTextPane infoText = new JTextPane();
		infoText.setEditable(false);
		infoText.setBackground(statusPanel.getBackground());
		Dimension labelDim = infoText.getPreferredSize();
		infoText.setPreferredSize(new Dimension(labelDim.width+100, labelDim.height));
		infoText.setText(Controler.getVersion()+"-"+Controler.getBuildID());
		statusPanel.add(infoText, BorderLayout.WEST);
		caretPos = new JLabel("");
		statusPanel.add(caretPos, BorderLayout.EAST);
		typeLabel = new JLabel("");
		statusPanel.add(typeLabel, BorderLayout.CENTER);



		JSplitPane splitPane = new JSplitPane();
		this.getContentPane().add(splitPane, BorderLayout.CENTER);
		projectViewer = new ProjectViewer(this);
		//projectViewer.addProject(project);
		splitPane.setLeftComponent(projectViewer);

		experimentPane = new ExperimentPanel(this, hostAdresses);
		optimizationPane = new OptimizationPanel(this, hostAdresses);
		simulationPanel = new SimulationPanel(this);

		activitiesPane =
				new JTabbedPane(JTabbedPane.BOTTOM,
						JTabbedPane.SCROLL_TAB_LAYOUT);

		ImageIcon icon = new ImageIcon(); //createImageIcon("images/middle.gif");
		ProjectControler pControler = new ProjectControler(this);
		pControler.createProject("Default");
		pCList.add(pControler);

		activitiesPane.addTab("Simulation", icon, simulationPanel, "");
		activitiesPane.setMnemonicAt(0, KeyEvent.VK_2);
		//activitiesPane.addChangeListener(simulationPanel);

		activitiesPane.addTab(ExperimentPanel.title, icon, experimentPane, experimentPane.tooltip);
		activitiesPane.setMnemonicAt(0, KeyEvent.VK_2);
		activitiesPane.addChangeListener(experimentPane);
		//		
		activitiesPane.addTab(OptimizationPanel.title, icon, optimizationPane, optimizationPane.tooltip);
		activitiesPane.setMnemonicAt(0, KeyEvent.VK_2);
		activitiesPane.addChangeListener(optimizationPane);


		//Add algorithm
		for(InterfaceAlgorithmFactory algoFactory:Controler.getControler().getAAFList()){
			experimentPane.getAlgoSelPanel().addAlgorithmFactory(algoFactory);
			optimizationPane.getAlgoSelPanel().addAlgorithmFactory(algoFactory);
		}

		//The following line enables to use scrolling tabs.
		activitiesPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		// docking of the pane :
		splitPane.setRightComponent(activitiesPane);
		this.getContentPane().add(splitPane);

		//Benoit:
		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.add(new PlasmaMenuBar(this), BorderLayout.NORTH);
		menuPanel.add(new IconBar(this), BorderLayout.SOUTH);

		this.getContentPane().add(menuPanel, BorderLayout.NORTH);

		adWizard = new GenericWizards();
		
		addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	PlasmaLabGUI main = (PlasmaLabGUI)e.getSource();
				while(!main.getPCList().isEmpty()) {
					ProjectControler pc = main.getPCList().get(0);
					if(pc != null) {
						if (! pc.closeProject())	// cancel exit
							return; 
					}
					else {
						main.getPCList().remove(0);
					}
				}
				log_.debug("Closing the GUI");
				System.exit(0);
		      }
		    });
	}
	
	public ArrayList<ProjectControler> getPCList(){
		return pCList;
	}

	/**
	 * @return the experimentPane
	 */
	public ExperimentPanel getExperimentPane() {
		return experimentPane;
	}
	/**
	 * @return the SimulationPanel
	 */
	public SimulationPanel getSimulationPanel(){
		return simulationPanel;
	}

	/**
	 * @return the projectViewer
	 */
	public ProjectViewer getProjectViewer() {
		return projectViewer;
	}

	public void addProjectPanel(ProjectPanel pp){
		ImageIcon icon = new ImageIcon();
		//projectPanel = pp;
		int expPos = 0; //TODO Left of expPane activitiesPane.indexOfComponent(experimentPane);
		if (expPos < 0) expPos = 0; 
		activitiesPane.insertTab(pp.getName(), icon, pp, PROJECT_TOOLTIP, expPos);
		activitiesPane.setSelectedIndex(expPos);
	}

	public void updateTabName(Component tabbed, String name){
		for(int i=0; i<activitiesPane.getTabCount(); i++){
			if(activitiesPane.getComponentAt(i).equals(tabbed)){
				activitiesPane.setTitleAt(i,name);
				break;
			}
		}
	}	

	public void setTabSaved(Component tabbed, String name, boolean saved){
		for(int i=0; i<activitiesPane.getTabCount(); i++){
			if(activitiesPane.getComponentAt(i).equals(tabbed)){
				if(saved)
					activitiesPane.setTitleAt(i,name);
				else
					activitiesPane.setTitleAt(i, "*"+name);
				break;
			}
		}
	}

	public ProjectControler getActiveProjectControler(){
		if(activitiesPane.getSelectedComponent().getClass().equals(ProjectPanel.class))
			return ((ProjectPanel)activitiesPane.getSelectedComponent()).getPControler();
		else
			return null;
	}

	public void remProjectControler(ProjectControler pc){
		pCList.remove(pc);
		int i=activitiesPane.indexOfComponent(pc.getpPanel());
		if(i!=-1)
			activitiesPane.remove(i);
	}

	public void setFocusTo(Object project, Object leaf) {
		if(project instanceof Project)
			for(ProjectControler pc:pCList){
				//Set focus to the right edition panel
				if(pc.setFocusTo((Project)project, leaf)){
					//Set focus to the project panel
					activitiesPane.setSelectedComponent(pc.getpPanel());
					break;
				}
			}
	}

	public void setFocusToExperiment(){
		activitiesPane.setSelectedComponent(experimentPane);
	}

	public void setFocusToSimulation() {
		activitiesPane.setSelectedComponent(simulationPanel);
	}

	public void setFocusToOptimization() {
		activitiesPane.setSelectedComponent(optimizationPane);
	}


	public OptimizationPanel getOptimizationPane() {
		return optimizationPane;
	}

	public void setCaretPos(int line, int col){
		caretPos.setText("L" + line+" : C"+col+"     ");
	}
	public void setTypeLabel(String type){
		typeLabel.setText(type);
	}
	public void resetCaretPos(){
		caretPos.setText("");
	}



	public GenericWizards getAdWizard() {
		return adWizard;
	}

	public static File getWorkingDir(){
		return workingDir;
	}
	
	public static void setWorkingDirPath(String dir){
		workingDir = new File(dir).getAbsoluteFile();
	    System.setProperty("user.dir", workingDir.getAbsolutePath());
	}
	
	public List<Image> getFrameIcons() {
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
