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
package fr.inria.plasmalab.rmlbis.observer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.parsing.PRISMModelGrammarLexer;
import fr.inria.plasmalab.rmlbis.parsing.PRISMModelGrammarParser;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ObserverParserProcessing {
		
	private AbstractModel model;
	public Model requirement;
	public List<Module> observers;
	public List<Identifier> externalIds;
	
	public ObserverParserProcessing(AbstractModel model, String content) throws RecognitionException, PlasmaSyntaxException, PlasmaRuntimeException  {
		if (content.isEmpty())
			throw new PlasmaSyntaxException("Empty Observer Model.");
		this.model = model;
		process(new ANTLRStringStream(content));
	}
		
	public ObserverParserProcessing(AbstractModel model, File file) throws IOException, RecognitionException, PlasmaRuntimeException, PlasmaSyntaxException  {
		this.model = model;
		process(new ANTLRFileStream(file.getAbsolutePath()));
	}
	
	private void process(CharStream input) throws RecognitionException, PlasmaRuntimeException, PlasmaSyntaxException {
		PRISMModelGrammarLexer lexer = new PRISMModelGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PRISMModelGrammarParser parser = new PRISMModelGrammarParser(tokens);
       	parser.observerfile();
       	requirement = parser.model;
       	observers = parser.obsModules;
	    externalIds = new ArrayList<Identifier>();
	    if (model != null) { // model is null when only checking the syntax
		    Map<String,InterfaceIdentifier> modelIdents = model.getIdentifiers();
		    for (Ref r : parser.factory.getIdents().values()) {
		   		if (r.target == null) {
		   			InterfaceIdentifier extId = modelIdents.get(r.name);
		   			if (extId != null)
		   				externalIds.add(parser.factory.makeExternalId(r.name, extId.isBoolean()?Type.Boolean:Type.Double));
		   		}
		   	}
		    
		    requirement.setDefaultSystem(new PlasmaSystem(requirement, parser.factory));
	        parser.factory.check();			// check identifiers consistency and types.
	        requirement.typeCheck();		// check the types of the commands
	        requirement.updateConstantValues();	// update expressions with the value of the constant
	        
			if (requirement.getDefaultSystem().getIdent("score") == null)
				throw new PlasmaSyntaxException("Observer is missing a \"score\" variable");
			if (requirement.getDefaultSystem().getIdent("decided") == null)
				throw new PlasmaSyntaxException("Observer is missing a \"decided\" variable");
		}
	}
	
}
