/**
 * This file is part of fr.inria.plasmalab.controler.
 *
 * fr.inria.plasmalab.controler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.controler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.controler.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaPluginNotFound;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 * The Controler class is the main entry point of Plasma Lab, its API.
 *
 */
public class Controler {
	
	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(Controler.class);
	
	private final static InputStream dconf = Controler.class.getClassLoader().getResourceAsStream("plasmalab.conf");
	private static Controler myself=null;
	private static String workingDirPath;
	private static String version = "NA";
	private static String buildID = "NA";
	
	private Set<URI> pluginSources;
	
	private PluginLoader pluginLoader;

	private List<AbstractDataFactory> adfList;
	private List<InterfaceAlgorithmFactory> aafList;
		
	/**
	 * 
	 * Create a new controller.
	 * The exedir will be set via the PLASMA_HOME env var.
	 * 
	 * @param conffile
	 * @return
	 */
	public static Controler createControler(File conffile) {
		Map<String, String> env = System.getenv();
		String plasmaHome = env.get("PLASMA_HOME");
		if (plasmaHome == null) {
			plasmaHome = "./";
		}
		if(myself==null){
			myself = new Controler(new File(plasmaHome), conffile);
		}
		return myself;
	}
	/**
	 * Singleton implementation to get the Controler object.
	 * 
	 * This method has now parameter and should not be used to initialize 
	 * the Controler.
	 * @throws a PlasmaException if the Controler wasn't initialized before its call.
	 * 
	 * @return the Controler singleton.
	 */
	public static Controler getControler(){
		if (myself == null){
			logger.error("Critical: PLASMA Controler was not initialized");
			throw new PlasmaRuntimeException("Critical: PLASMA Controler was not initialized");
		}	
		return myself;
	}
	
	/**
	 * Controler constructor.
	 * 
	 * This constructor is private due to Singleton pattern.
	 * 
	 * @param args array of arguments from the command line.
	 */
	private Controler(File exedir, File conffile){
		//init
		workingDirPath = ".";
		System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
		
		//Add a JacksonConverter for deserialization. 
		//Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		//set version number
		initVersionBuildNumbers();
		
		//Read configuration options
		pluginSources = new HashSet<URI>();
		
		if (conffile == null) {
			//Read default conf
			BufferedReader reader = new BufferedReader(new InputStreamReader(dconf));
			readConf(reader,exedir);
		}
		else {
			try{
				BufferedReader reader = new BufferedReader(new FileReader(conffile));
				logger.info("Reading configuration file from " + conffile.getAbsolutePath());
				// take everything from the conf 
				// the exedir is set to null to reset the PLASMA_HOME
				// TODO clean this.
				readConf(reader, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Load plugins
		pluginLoader = new PluginLoader(pluginSources);
		
		//Get Data
		adfList = pluginLoader.importDataPackages();
		//Get Algorithm
		aafList = pluginLoader.importAlgorithmPackages();
	}

	/**
	 * Initialization method reading the configuration file.
	 * 
	 * @param reader a BufferedReader of the config file.
	 */
	private void readConf(BufferedReader reader, File exedir) {
		try {
			while(reader.ready()){
				String[] line = reader.readLine().split(" ");
				if(!line[0].equals("#")){
					/** plugin **/
					if(line[0].equals("plugin") && line.length>1){
						try {
							pluginSources.add(new URI(line[1]));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					/** plugin_dir **/
					else if(line[0].equals("plugin_dir") && line.length>1){
						File dir;
						if (exedir != null)
							dir = new File(exedir, line[1]);
						else
							dir = new File(line[1]);
						if(dir.exists()) {
							pluginSources.add(dir.toURI());
						}
					}
					/** WORKING DIR **/
					// TODO deprecate this
					else if(line[0].equals("working_dir") && line.length>1){
						workingDirPath=line[1];
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error in the default config file");
			e.printStackTrace();
		}
	}
	
	/** For each requirement, search for its factory and use it to create the requirement from the input file 
	 * @return List of AbstractRequirement
	 */
	public List<AbstractRequirement> createRequirements(List<Requirement> requirementsStrings) {
		List<AbstractRequirement> requirements = new ArrayList<AbstractRequirement>();
		for (Requirement requirement : requirementsStrings) {
			String requirementType = requirement.getType();
			String requirementFile = requirement.getFile();
			boolean found = false;
			for (AbstractDataFactory adf : adfList) {
				if (adf.getId().equals(requirementType) && adf instanceof AbstractRequirementFactory) {
					try {
						AbstractRequirement r = ((AbstractRequirementFactory) adf)
									.createAbstractRequirement(requirementFile, new File(requirementFile));
						requirements.add(r);
					} catch (PlasmaDataException e) {
						logger.warn("Error while loading requirement: " + requirementFile + ". " + e.getMessage() + ". Skipped.");
					}
					found = true;
					break;
				}
			}
			if (!found)
				logger.warn("Missing plugin for requirement ID: " + requirementType + ". Skipped.");
		}
		return requirements;
	}

	/** Search for the model factory and use it to create the model from the input file 
	 * @param modelType	plugin id
	 * @param modelFile	file that contains the model
	 * @return AbstractModel of the model
	 * @throws PlasmaPluginNotFound	if no factory was not found to create the model 
	 * @throws PlasmaDataException	if an error occured while creating the model
	 */
	public AbstractModel createModel(String modelType, String modelFile) throws PlasmaPluginNotFound, PlasmaDataException {
		for (AbstractDataFactory adf : adfList) {
			if (adf.getId().equals(modelType) && adf instanceof AbstractModelFactory) {
				return ((AbstractModelFactory) adf).createAbstractModel(modelFile, new File(modelFile));
			}
		}
		throw new PlasmaPluginNotFound(modelType);
	}

	/**
	 * Get the AbstractDataFactory list (model and requirement)
	 * @return the ADF list
	 */
	public List<AbstractDataFactory> getADFList(){
		return adfList;
	}
	/**
	 * Get the InterfaceAlgorithmFactory list
	 * @return the IAF list
	 */
	public List<InterfaceAlgorithmFactory> getAAFList(){
		return aafList;
	}
	/**
	 * Get the working directory.
	 * @return the absolute path to the working directory.
	 */
	public String getWorkingDirPath(){
		return workingDirPath;
	}
	/**
	 * Initialize the version build number
	 */
	private void initVersionBuildNumbers(){
		Properties mavenProperties = new Properties();
		try {
			mavenProperties.load(Controler.class.getClassLoader().getResourceAsStream("version.properties"));
			version = (String) mavenProperties.get("version");
			buildID = (String) mavenProperties.get("build");
		} catch (IOException e1) {
			logger.debug("Failed to load mavenProperties (version, buildID).",e1);
		}
		String[] split= version.split("-");
		version = split[0];
	}
	
	/**
	 * Get the version number.
	 * @return the version number.
	 */
	public static String getVersion(){
		return version;
	}
	
	/**
	 * Get the build ID.
	 * @return the build ID.
	 */
	public static String getBuildID(){
		return buildID;
	}

}
