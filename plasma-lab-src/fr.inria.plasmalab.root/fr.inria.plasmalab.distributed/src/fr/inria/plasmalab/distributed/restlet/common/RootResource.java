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
package fr.inria.plasmalab.distributed.restlet.common;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface RootResource {
	@Get("text")
	public String represent();
	

	
	/**
	 * Retrieves the model content
	 * @return
	 */
	@Get("?ModelContent")
	public String getModelContent();
	
	/**
	 * Retrieves the model id
	 * @return
	 */
	@Get("?ModelId")
	public String getModelId();
	
	/**
	 * Retrieves the requirement content
	 * @return
	 */
	@Post("?RequirementContent")
	public String getRequirementContent(Integer pos);
	
	/**
	 * Retrieves the requirement id
	 * @return
	 */
	@Post("?RequirementId")
	public String getRequirementId(Integer pos);
	
	/**
	 * Retrieves the requirement id
	 * @return
	 */
	@Post("?RequirementName")
	public String getRequirementName(Integer pos);
	
	/**
	 * Retrieves the number of Requirement in the experiment
	 * @return
	 */
	@Get("?RequirementsNb")
	public Integer getRequirementsNb();
	
	/**
	 * Retrieves the algorithm id
	 * @return
	 */
	@Get("?AlgorithmId")
	public String getAlgorithmId();
}
