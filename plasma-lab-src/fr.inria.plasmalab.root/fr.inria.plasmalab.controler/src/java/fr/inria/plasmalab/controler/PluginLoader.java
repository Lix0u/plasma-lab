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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.comparison.ComparisonFactory;
import fr.inria.plasmalab.csvsimulator.CSVFactory;
import fr.inria.plasmalab.cusum.factory.CuSumFactory;
import fr.inria.plasmalab.importancesampling.CrossEntropyFactory;
import fr.inria.plasmalab.importancesplitting.ImportanceSplittingFactory;
import fr.inria.plasmalab.montecarlo.MontecarloFactory;
import fr.inria.plasmalab.sequential.factory.SequentialFactory;
import fr.inria.plasmalab.smartsampling.SmartSamplingFactory;
import fr.inria.plasmalab.smartsampling.SequentialSmartSamplingFactory;
import fr.inria.plasmalab.text_data.TextDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

/**
 * The PluginLoaded class loads plugins (model, requirement, algorithm) using JSPF API. 
 * 
 * @author kevin.corre@inria.fr
 */
public class PluginLoader {
	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(PluginLoader.class);

	PluginManager pm;
	PluginManagerUtil pmu;
	private List<InterfaceAlgorithmFactory> iaf;
	private List<AbstractDataFactory> adf;
	private Set<String> idModelSet = new HashSet<String>(), idReqSet = new HashSet<String>();

	public PluginLoader(Set<URI> pluginSources) {
		pm = PluginManagerFactory.createPluginManager();		
		for(URI uri:pluginSources){
			pm.addPluginsFrom(uri);
		}
		pmu = new PluginManagerUtil(pm);
	}
	
	/**
	 * This method load the selected data plugins and return a list of AbstractDataFactory.
	 * Data are model and requirements.
	 * @return a list of AbstractDataFactory.
	 */
	public List<AbstractDataFactory> importDataPackages() {
		adf = new ArrayList<AbstractDataFactory>();
		//Load the text data type.
		adf.add(new TextDataFactory());
				
		for (AbstractDataFactory factory : pmu.getPlugins(AbstractDataFactory.class)) {
			if(factory instanceof AbstractModelFactory && !idModelSet.contains(factory.getId())){
				adf.add(factory);
				idModelSet.add(factory.getId());
				logger.info(factory.getName() + " loaded.");
			}
			if(factory instanceof AbstractRequirementFactory && !idReqSet.contains(factory.getId())){
				adf.add(factory);
				idReqSet.add(factory.getId());
				logger.info(factory.getName() + " loaded.");
			}
		}

//		pm.addPluginsFrom(ClassURI.PLUGIN(MatLabSessionFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(RMLModelFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(RMLAdaptiveFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(BioFactory.class));
//		for (AbstractModelFactory factory : pmu.getPlugins(AbstractModelFactory.class)) {
//			if(!idModelSet.contains(factory.getId())){
//				adf.add(factory);
//				idModelSet.add(factory.getId());
//				logger.info(factory.getName() + " loaded.");
//			}
//		}
		
//		pm.addPluginsFrom(ClassURI.PLUGIN(BLTLFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(NestedFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(GCSLFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(ALTLFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(ObserverFactory.class));
//		pm.addPluginsFrom(ClassURI.PLUGIN(TestFactory.class));
//		for (AbstractRequirementFactory factory : pmu.getPlugins(AbstractRequirementFactory.class)) {
//			if(!idReqSet.contains(factory.getId())){
//				adf.add(factory);
//				idReqSet.add(factory.getId());
//				logger.info(factory.getName() + " loaded.");
//			}
//		}
		
		return adf;
	}

	/**
	 * This method load the selected algorithm plugins and return a list of InterfaceAlgorithmFactory.
	 * @return a list of InterfaceAlgorithmFactory
	 */
	public List<InterfaceAlgorithmFactory> importAlgorithmPackages() {
		iaf = new ArrayList<InterfaceAlgorithmFactory>();
		
		// load main algorithms statically
		iaf.add(new MontecarloFactory());   
		iaf.add(new SequentialFactory());
		iaf.add(new ComparisonFactory());
		iaf.add(new SmartSamplingFactory());    
		iaf.add(new SequentialSmartSamplingFactory());   
		iaf.add(new ImportanceSplittingFactory());
		iaf.add(new CrossEntropyFactory());
		iaf.add(new CuSumFactory());
		
		// load other algorithms from plugin sources
		for (InterfaceAlgorithmFactory factory : pmu.getPlugins(InterfaceAlgorithmFactory.class)) {
			if(!iaf.contains(factory)){
				iaf.add(factory);
			}
		}		
		for (InterfaceAlgorithmFactory factory : iaf) {
			logger.info(factory.getName() + " loaded.");
		}
		return iaf;
	}	
}
