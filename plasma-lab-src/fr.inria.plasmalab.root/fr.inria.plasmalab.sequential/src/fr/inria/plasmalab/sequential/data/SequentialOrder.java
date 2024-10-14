/**
 * This file is part of fr.inria.plasmalab.sequential.
 *
 * fr.inria.plasmalab.sequential is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.sequential is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.sequential.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.sequential.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.distributed.algorithm.common.Order;

public class SequentialOrder extends Order {
	
	public static Order createSequentialBatchOrder(int toDo, List<List<Boolean>> again, String uid){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("batch", toDo);
		content.put("again", again);
		return new Order(Type.BATCH, content, uid);
	}
}
