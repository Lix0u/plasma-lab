/**
 * This file is part of fr.inria.plasmalab.gcsl.
 *
 * fr.inria.plasmalab.gcsl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.gcsl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.gcsl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.gcsl;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class GCSLFactory extends AbstractRequirementFactory{
	private static final String name = "GCSL", description = "Bounded-Linear Temporal Logic.";
	private static final String id = "gcsl";
	
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
		return new GCSLRequirement(name, "", id);
	}
	
	@Override
	public AbstractRequirement createAbstractRequirement(String name, File file) {
		return new GCSLRequirement(name, file, id);
	}
	
	@Override
	public AbstractRequirement createAbstractRequirement(String name, String content) {
		return new GCSLRequirement(name, content, id);
	}
	
}
