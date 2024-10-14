/**
 * This file is part of fr.inria.plasmalab.llvm :: LLVM plugin.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.llvm :: LLVM plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.llvm;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class LLVMFactory extends AbstractModelFactory {
	private static final String name = "LLVM";
	private static final String description = "Plugin that interfaces Plasma Lab with a LLVM simulator";
	private static final String id = "llvm";
	
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
		return new LLVMSimulator(name, "", id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException {
		return new LLVMSimulator(name, file, id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, String content) throws PlasmaDataException {
		return new LLVMSimulator(name, content, id);
	}

}
