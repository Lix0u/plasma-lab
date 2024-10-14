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
package fr.inria.plasmalab.distributed.algorithm.common;

import java.util.HashMap;
import java.util.Map;

public class Order {
	public enum Type {
		BATCH, TERMINATE, WAIT, RESEND;
	}

	private Type type;

	private Map<String, Object> content;

	private String uid;

	public Order() {
		super();
	}

	public Order(Type type, Map<String, Object> content, String uid) {
		this.type = type;
		this.content = content;
		this.uid = uid;
	}


	/**
	 * This method returns the type of the order
	 * 
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * This method set the type of the order
	 * 
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * This method returns the content map of the order
	 * 
	 * @return
	 */
	public Map<String, Object> getContent() {
		return content;
	}

	/**
	 * Returns a value in the content map.
	 * 
	 * @param key
	 * @return an Object in the content map or null.
	 */
	public Object getParam(String key) {
		return content.get(key);
	}

	/**
	 * This method set the content map of the order
	 * 
	 * @param content
	 */
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	/**
	 * Get the uid of the order
	 * 
	 * @return
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Set the uid of the order.
	 * 
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	public static Order createTerminateOrder(String uid) {
		return new Order(Type.TERMINATE, null, uid);
	}

	public static Order createBatchOrder(int toDo, String uid) {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("batch", toDo);
		return new Order(Type.BATCH, content, uid);
	}

	public static Order createWaitOrder(int wait, String uid) {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("wait", wait);
		return new Order(Type.WAIT, content, uid);
	}

	/**
	 * Ask the worker to resend its results because the previous transmission
	 * failed. This order try avoid deadlock that occurs when the scheduler lose
	 * a result message.
	 * 
	 * @return
	 */
	@Deprecated
	public static Order createResendOrder(String uid) {
		return new Order(Type.RESEND, null, uid);
	}
}
