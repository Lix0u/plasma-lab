/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.simulator;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RMLAdaptiveFactory extends AbstractModelFactory {
	private static final String name = "RMLAdaptive", description = "Adaptive extension for the Reactive Module Language.";
	private static final String id = "rmladaptive";
	
	@Override
	public AbstractModel createAbstractModel(String name) {
		return new RMLAdaptiveModel(name,"",id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException {
		return new RMLAdaptiveModel(name,file,id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, String content) {
		return new RMLAdaptiveModel(name,content,id);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
}
