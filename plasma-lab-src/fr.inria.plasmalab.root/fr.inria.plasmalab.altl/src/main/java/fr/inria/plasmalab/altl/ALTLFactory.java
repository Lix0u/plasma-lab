/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class ALTLFactory extends AbstractRequirementFactory{
	private static final String name = "ALTL", description = "Adaptive Bounded-Linear Temporal Logic.";
	private static final String id = "altl";

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
		return new ALTLRequirement(name, "", id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, File file) {
		return new ALTLRequirement(name, file, id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, String content) {
		return new ALTLRequirement(name, content, id);
	}

}
