/**
 * This file is part of fr.inria.plasmalab.matlab.
 *
 * fr.inria.plasmalab.matlab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class MatLabSessionFactory extends AbstractModelFactory {
	protected final static Logger logger = LoggerFactory.getLogger(MatLabSessionFactory.class);
	
	private static final String name = "MATLAB Session";
	private static final String description = "A session between PLASMA Lab and MATLAB.";
	private static final String id = "matlab";

	// Singleton proxy
	private MatlabProxy proxy = null;
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public AbstractModel createAbstractModel(String name) throws PlasmaDataException {
		try {
			return new MatLabSessionModel(name, "", getProxy(), id);
		} catch (MatlabConnectionException e) {
			throw new PlasmaDataException(e);
		} catch (MatlabInvocationException e) {
			throw new PlasmaDataException(e);
		}
	}
	@Override
	public AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException {
		try {
			logger.info("Create MATLAB session model");
			return new MatLabSessionModel(name, file, getProxy(), id);
		} catch (MatlabConnectionException e) {
			throw new PlasmaDataException(e);
		} catch (MatlabInvocationException e) {
			throw new PlasmaDataException(e);
		}
	}
	@Override
	public AbstractModel createAbstractModel(String name, String content) throws PlasmaDataException {
		try {
			logger.info("Create MATLAB session model");
			return new MatLabSessionModel(name, content, getProxy(), id);
		} catch (MatlabConnectionException e) {
			throw new PlasmaDataException(e);
		} catch (MatlabInvocationException e) {
			throw new PlasmaDataException(e);
		}
	}
	
	public MatlabProxy getProxy() throws MatlabConnectionException, MatlabInvocationException{
		if(proxy == null){
			MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
					.setUsePreviouslyControlledSession(true)
//					.setHidden(true)
					.build();
			MatlabProxyFactory factory = new MatlabProxyFactory(options);
			proxy = factory.getProxy();		
			proxy.eval("clc");
		}
		return proxy;
	}

}
