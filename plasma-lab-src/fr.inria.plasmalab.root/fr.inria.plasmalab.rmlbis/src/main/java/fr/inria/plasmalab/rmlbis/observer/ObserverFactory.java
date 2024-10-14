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
package fr.inria.plasmalab.rmlbis.observer;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class ObserverFactory extends AbstractRequirementFactory{
	private static final String name = "Observer";
	private static final String description = "RML based BLTL observers with score function for importance splitting.";
	private static final String id = "rmlobserver";
	
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
		return new ObserverRequirement(name, "", id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, File file) {
		return new ObserverRequirement(name, file, id);
	}

	@Override
	public AbstractRequirement createAbstractRequirement(String name, String content) {
		return new ObserverRequirement(name, content, id);
	}

}
