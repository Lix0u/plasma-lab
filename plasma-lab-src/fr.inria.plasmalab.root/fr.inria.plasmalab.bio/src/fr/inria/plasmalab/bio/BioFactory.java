/**
 * This file is part of fr.inria.plasmalab.bio :: Biological models.
 *
 * fr.inria.plasmalab.bio :: Biological models is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio :: Biological models is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio :: Biological models.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of fr.inria.plasmalab.bio.
 *
 * fr.inria.plasmalab.bio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bio;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;


/**
 * This {@link Factory} generates Simulator and Checker to be used by PLASMA Lab. 
 * <p>
 * RMLFactory implements {@link Factory} interface. It provides method to generate {@link Simulator} 
 * and property {@link Checker}.
 * It also provides informations such as the package name and description which are used by the 
 * PLASMA Lab GUI.
 * <p>
 * {@link Factory} extends the {@link Plugin} interface. This interface is part of JSPF. 
 * 
 * @author kevin.corre@inria.fr
 *
 */
@PluginImplementation
public class BioFactory extends AbstractModelFactory {
	private static final String name = "Bio", description = "A PlasmaLab implementation for Biological language.";
	private static final String id = "bio";

	@Override
	public AbstractModel createAbstractModel(String name) {
		return new BioSimulator(name, "", id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, File file) {
		return new BioSimulator(name, file, id);
	}

	@Override
	public AbstractModel createAbstractModel(String name, String content) {
		return new BioSimulator(name, content, id);
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
