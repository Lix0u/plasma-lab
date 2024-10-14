/**
 * This file is part of fr.inria.plasmalab.distributed.
 *
 * fr.inria.plasmalab.distributed is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.distributed is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.distributed.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.distributed.restlet.server;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.distributed.DistributedBay;
import fr.inria.plasmalab.distributed.restlet.common.RootResource;

public class RootServerResource extends ServerResource implements RootResource {

	@Override
	@Get("text")
	public String represent() {
		return "Welcome to the "+getApplication().getName()+"!";
	}

	@Override
	@Get("?ModelContent")
	public String getModelContent() {
		return ((DistributedBay)getApplication()).getModelContent();
	}

	@Override
	@Get("?ModelId")
	public String getModelId() {
		return ((DistributedBay)getApplication()).getModelId();
	}

	@Override
	@Post("?RequirementContent")
	public String getRequirementContent(Integer pos) {
		return ((DistributedBay)getApplication()).getRequirementContent(pos);
	}

	@Override
	@Post("?RequirementId")
	public String getRequirementId(Integer pos) {
		return ((DistributedBay)getApplication()).getRequirementId(pos);
	}

	@Override
	@Post("?RequirementName")
	public String getRequirementName(Integer pos) {
		return ((DistributedBay)getApplication()).getRequirementName(pos);
	}
	
	@Override
	@Get("?RequirementsNb")
	public Integer getRequirementsNb(){
		return((DistributedBay)getApplication()).getRequirementsNb();
	}

	@Override
	@Get("?AlgorithmId")
	public String getAlgorithmId() {
		return ((DistributedBay)getApplication()).getAlgorithmId();
	}

}
