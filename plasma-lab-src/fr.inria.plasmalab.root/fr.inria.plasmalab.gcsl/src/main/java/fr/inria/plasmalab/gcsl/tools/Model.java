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
package fr.inria.plasmalab.gcsl.tools;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
	private HashMap<String, Module> hash;
	
	public Model(){
		hash = new HashMap<String, Module>();
	}
	
	public void declareModuleType(String moduleType, String moduleName, ArrayList<Substitution> subs){
		//hash.put(moduleName, new HashMap<String, ArrayList<Substitution>>());
		addInstance(moduleType, moduleName, subs);		
	}
	
	public void addInstance(String moduleType, String moduleName, ArrayList<Substitution> subs){
		hash.put(moduleName, new Module(moduleName, moduleType, subs));
	}
	
	/*public HashMap<String, ArrayList<Substitution>> getHashFromType(String moduleType){
		return hash.get(moduleType);
	}*/
	
	public HashMap<String, Module> getHash(){
		return hash;
	}
	
	
}
