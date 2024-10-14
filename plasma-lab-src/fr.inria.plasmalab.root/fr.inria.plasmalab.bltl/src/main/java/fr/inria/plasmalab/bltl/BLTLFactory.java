/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class BLTLFactory extends AbstractRequirementFactory{
	private static final String name = "BLTL", description = "Bounded-Linear Temporal Logic.";
	private static final String id = "bltl";

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
	public AbstractRequirement createAbstractRequirement(String name) {
		return new BLTLRequirement(name , "", id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, File file) throws PlasmaDataException {
		return new BLTLRequirement(name, file, id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, String content) {
		return new BLTLRequirement(name, content, id);
	}

}
