/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.altl.ALTLRequirement;
import fr.inria.plasmalab.altl.parser.ALTLLexer;
import fr.inria.plasmalab.altl.parser.ALTLParser;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class ALTLTestRequirement extends ALTLRequirement {

	public ALTLTestRequirement(String name, File file, String id) {
		super(name, file, id);
	}
	
	
	public boolean checkForErrorsTest() {
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		errors.clear();

		CharStream cs = new ANTLRStringStream(content);
		ALTLLexer lexer = new ALTLLexer(cs);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		ALTLParser parser = new ALTLParser(tokens);
		
		if(model != null)
			parser.gFormulaRules.modelIdMap = model.getIdentifiers();
		else
			parser.gFormulaRules.modelIdMap = new HashMap<String, InterfaceIdentifier>();
		
		try {
			requirementRoot = parser.formula();

			if(!errors.isEmpty()){
				errors.addAll(parser.getErrors());
				return true;
			}
		} catch (RecognitionException e) {
			PlasmaDataException error = new PlasmaDataException(e);
			errors.add(error);
			//e.printStackTrace();
			return true;
		}
		optimizationVariables = parser.gFormulaRules.optimizationVariables;
		return false;		
	}
}
