/**
 * This file is part of fr.inria.plasmalab.importancesplitting.
 *
 * fr.inria.plasmalab.importancesplitting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesplitting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesplitting.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesplitting.server;

import org.restlet.resource.Post;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.restlet.common.CommonServerResource;
import fr.inria.plasmalab.importancesplitting.data.ImportanceSplittingResult;

public class ISServerResource extends CommonServerResource  implements InterfaceISServerRessource {
	
	@Post("?resultsSplitting")
	@Override
	public void postISResults(ImportanceSplittingResult result) {
		getScheduler().putResult(result);
	}
	
	protected AbstractScheduler getScheduler(){
		return (AbstractScheduler)getApplication().getContext().getAttributes().get("splitting");
	}

	
}
