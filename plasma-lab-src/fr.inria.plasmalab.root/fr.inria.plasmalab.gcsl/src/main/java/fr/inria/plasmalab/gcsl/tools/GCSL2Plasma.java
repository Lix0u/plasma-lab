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

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;


public class GCSL2Plasma {
	
	private Model model;
	
	public GCSL2Plasma(){
		model = null;
	}
	
	public boolean setModel(AbstractModel absModel){
		//parse model
		PRISMGrammarLexer lex;
		lex = new PRISMGrammarLexer(new ANTLRStringStream(absModel.getContent()));
        CommonTokenStream tokens = new CommonTokenStream(lex);
 
        PRISMGrammarParser parser = new PRISMGrammarParser(tokens);
        
        model = parser.parseModel();
                
        //TODO Check for error
        return true;
	}
	
	
	public String translate(String gcslProperty)throws PlasmaDataException{
		//parse and translate GCSL property
		CharStream cs = new ANTLRStringStream(gcslProperty);
		GCSLGrammarLexer lexGCSL = new GCSLGrammarLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexGCSL);
 
        GCSLGrammarParser parserGCSL = new GCSLGrammarParser(tokens);
        String translation = parserGCSL.parseGCSL(model);
        if(parserGCSL.getErrors().size()>0){
        	String errMsg = "Error while translating GCSL.";
        	for(String err:parserGCSL.getErrors())
        		errMsg+="\n"+err;
        	throw new PlasmaDataException(errMsg);
        }
        return translation;
	}
}
