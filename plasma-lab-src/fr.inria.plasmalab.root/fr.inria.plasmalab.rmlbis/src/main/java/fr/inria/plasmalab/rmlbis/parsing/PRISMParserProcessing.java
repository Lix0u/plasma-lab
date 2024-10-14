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
package fr.inria.plasmalab.rmlbis.parsing;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class PRISMParserProcessing {
		
	public Model model;
	
	public PRISMParserProcessing(String content) throws PlasmaSyntaxException, RecognitionException, PlasmaRuntimeException {
		if (content.isEmpty())
			throw new PlasmaSyntaxException("Empty RML Model.");
		process(new ANTLRStringStream(content));
	}
		
	public PRISMParserProcessing(File file) throws RecognitionException, IOException, PlasmaRuntimeException, PlasmaSyntaxException {
		process(new ANTLRFileStream(file.getAbsolutePath()));
	}
	
	private void process(CharStream input) throws RecognitionException, PlasmaRuntimeException, PlasmaSyntaxException {
		PRISMModelGrammarLexer lexer = new PRISMModelGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PRISMModelGrammarParser parser = new PRISMModelGrammarParser(tokens);
       	parser.rmlfile();
        
        model = parser.model;
        parser.factory.instantiateModules();	// instantiate the parameterized or renamed modules
        PlasmaSystem defaultSystem = new PlasmaSystem(model, parser.factory); 
        model.setDefaultSystem(defaultSystem);
        parser.factory.check();			// check identifiers consistency and types.
        // model.applySubstitution(); 	// Definitely useless: all the substitutions should have been performed before. Formulas in particular MUST have been substituted.
        model.typeCheck();				// check the types of the commands
        model.updateConstantValues();	// update expressions with the value of the constant
        parser.factory.initializeModules();		// initialize all the instance modules
	}
}
