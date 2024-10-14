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
import fr.inria.plasmalab.rmlbis.ast.models.AdaptiveModel;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class AdaptiveParserProcessing {
		
	public AdaptiveModel model;
	
	public AdaptiveParserProcessing(String content) throws RecognitionException, PlasmaSyntaxException  {
		if (content.isEmpty())
			throw new PlasmaSyntaxException("Empty RML Model.");
		process(new ANTLRStringStream(content));
	}
		
	public AdaptiveParserProcessing(File file) throws IOException, RecognitionException, PlasmaSyntaxException, PlasmaRuntimeException  {
		process(new ANTLRFileStream(file.getAbsolutePath()));
	}
	
	private void process(CharStream input) throws RecognitionException, PlasmaRuntimeException, PlasmaSyntaxException  {
		PRISMModelGrammarLexer lexer = new PRISMModelGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PRISMModelGrammarParser parser = new PRISMModelGrammarParser(tokens);
		parser.rmladaptivefile();
        
        model = parser.amodel;       
        parser.factory.instantiateModules();	// instantiate global modules
        for (PlasmaSystem sys : model.getSystems().values()) { // instantiate systems
        	sys.instantiate(model.getModules());
        }
       	model.getAdaptiveSystem().instantiate(model.getModules()); // instantiate procedures
        
       	// parser.factory.check();	// this should be avoided since some identifiers in the global factory might have not been declared.
        for (PlasmaSystem sys : model.getSystems().values()) { 
        	sys.factory.check();
        	sys.factory = null; // remove reference to the factory that can be erased by the garbage collector.
        }
        model.typeCheck();		// check the types of the commands
        model.updateConstantValues();	// update expressions with the value of the constant
		model.getAdaptiveSystem().initialize();	// initialize the start system
	}
}
