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
package fr.inria.plasmalab.rmlbis.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.factory.ModuleFactory;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.exceptions.WrongParametersNumber;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ModuleInstance {
	
	private String new_module_name;
	private String original_module_name;
	private Map<String,String> renamings;
	private List<Expr> parameters;
	private Module module;	
	
	public ModuleInstance(String new_name, String original_name, Map<String,String> renamings, List<Expr> parameters) {
		assert renamings != null;
		
		this.new_module_name = new_name;
		this.original_module_name = original_name;
		this.renamings = renamings;
		this.parameters = parameters;
		this.module = null;
	}
	
//	public ModuleInstance(Module m) {	
//		this.new_module_name = m.getName();
//		this.original_module_name = m.getName();
//		this.renamings = null;
//		this.parameters = null;
//		this.module = m;
//	}
	
	public String getName() {
		return new_module_name;
	}
	
	public Module getModule() {
		return module;
	}
	
	/** Create a new module.
	 *  This module is either a renaming of a original module or a instance of a parameterized module. 
	 *  If the original module is both a renaming and a parameterized module then the module is sequentially renamed and then instantiated.
	 * @param factory ModuleFactory used to create Identifiers 
	 * @param originals List of original modules from the model. 
 	 * @param originals List of original modules previously instantiated. 
	 * @return a new module. The module is only an instance if parameters is not null.
	 * @throws PlasmaSyntaxException 
	 */
	public Module instantiate(ModuleFactory factory, Map<String,Module> originals,  Map<String,Module> new_originals) throws PlasmaSyntaxException {
		module = originals.get(original_module_name);
		if (module == null && new_originals != null)
			module = new_originals.get(original_module_name);
		if (module == null)
				throw new PlasmaSyntaxException("No module named: " + original_module_name);
		
		if (parameters != null) {
			module = module.instantiate(factory, new_module_name, renamings);
			if (parameters.size() < module.getParameters().size())
				throw new WrongParametersNumber(module, parameters.size(), module.getParameters().size());
		}
		else {
			module = module.rename(factory, new_module_name, renamings);
		}
		renamings = null; // renamings is useless and corrupted by the previous operations
		return module;
	}
	
	/** Initialize an instance of a module.
	 *  If the module is parameterized parameters and local variables are initialized.
	 *  If the module is not parameterized only the local variables are initialized.
	 *  If the module is not an instance nothing is done.
	 */
	public void initialize() {
		if (module == null)
			throw new PlasmaRuntimeException("Module " + new_module_name + " has not been instantiated.");
		if (module.isInstance()) {
			if (parameters != null)
				module.initialize(parameters);
			else
				module.initialize(new ArrayList<Expr>());
		}
	}
	
	public String toPrism(List<String> idents) throws PlasmaSyntaxException {
		assert module != null;
		assert parameters.size() >= module.getParameters().size();
		
		String global_ret = "";
		String module_ret = "module " + module.getName() + "\n";
		
		// print parameters
		for (int i = 0; i < module.getParameters().size(); i++) {
			Parameter param = module.getParameters().get(i);
			Expr value = parameters.get(i);
			if (param.constant) {
				global_ret += "const " + param.toPrism("", value) + ";\n";
			}
			else {
				module_ret += "\t" + param.toPrism("", value) + ";\n";
				idents.add(param.getName());
			}
		}
		
		// print local variables
		for (Variable v : module.getLocalVariables().values()) {
			if (!(v instanceof Parameter)) {
				module_ret += "\t" + v.toPrism("") + ";\n";
				idents.add(v.getName());
			}
		}
		
		module_ret += module.toPrism("","");
		module_ret += "endmodule\n";
		return global_ret + module_ret;
	}

	public String toPrism(String sysPrefix, String guardPrefix, List<String> idents) throws PlasmaSyntaxException {
		assert module != null;
		assert parameters.size() >= module.getParameters().size();
		
		String global_ret = "";
		String module_ret = "module " + sysPrefix + module.getName() + "\n";
		
		// print parameters
		for (int i = 0; i < module.getParameters().size(); i++) {
			Parameter param = module.getParameters().get(i);
			Expr value = parameters.get(i);
			if (param.constant) {
				global_ret += "const " + param.toPrism(sysPrefix, value) + ";\n";
			}
			else {
				module_ret += "\t" + param.toPrism(sysPrefix, value) + ";\n";
				idents.add(sysPrefix.replace("_",".")+param.getName());
			}
		}
		
		// print local variables
		for (Variable v : module.getLocalVariables().values()) {
			if (!(v instanceof Parameter)) {
				module_ret += "\t" + v.toPrism(sysPrefix) + ";\n";
				idents.add(sysPrefix.replace("_",".")+v.getName());
			}
		}
		
		module_ret += module.toPrism(sysPrefix,guardPrefix);
		module_ret += "endmodule\n";
		return global_ret + module_ret;
	}
}
