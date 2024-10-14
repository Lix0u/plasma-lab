/**
 * This file is part of fr.inria.plasmalab.systemc :: systemC plugin.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.systemc :: systemC plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.systemc.factory;

import java.io.File;

import fr.inria.plasmalab.systemc.simulator.SystemCSimulator;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SystemCSimulatorFactory extends AbstractModelFactory {
	private static final String id = "systemc";

	@Override
	public String getName() {
		return "SystemC simulator interface";
	}

	@Override
	public String getDescription() {
		return "SystemC simulator interface";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public AbstractModel createAbstractModel(String name) {
		//This constructor is used when creating a new operation
		return new SystemCSimulator(name, "", id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, File file) {
		//This constructor is used when importing a model from a file
		return new SystemCSimulator(name, "", id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, String content) {
		//This constructor is used when opening a saved project
		return new SystemCSimulator(name, content, id);
	}

}
