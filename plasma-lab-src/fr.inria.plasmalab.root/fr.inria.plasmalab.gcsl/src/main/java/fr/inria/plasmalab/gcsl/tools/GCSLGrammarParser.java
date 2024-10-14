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
// $ANTLR 3.5 /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g 2015-02-20 11:14:44

package fr.inria.plasmalab.gcsl.tools;

import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class GCSLGrammarParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "Float", "Ident", "Natural", "TimeUnit", 
		"Variable", "WS", "'!'", "'&'", "'('", "')'", "'*'", "'+'", "','", "'-'", 
		"'->'", "'.'", "'/'", "';'", "'<'", "'<='", "'='", "'=>'", "'>'", "'>='", 
		"'BLTL Vars:'", "'Goal:'", "'Hypothesis:'", "'['", "']'", "'always'", 
		"'at most'", "'at('", "'by'", "'does not'", "'during'", "'exists'", "'false'", 
		"'following'", "'forAll'", "'forever'", "'holds'", "'implies'", "'mean('", 
		"'occurs'", "'prod('", "'raises'", "'sum('", "'then'", "'times'", "'true'", 
		"'whenever'", "'with a probability'", "'within'", "'|'"
	};
	public static final int EOF=-1;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int T__57=57;
	public static final int Float=4;
	public static final int Ident=5;
	public static final int Natural=6;
	public static final int TimeUnit=7;
	public static final int Variable=8;
	public static final int WS=9;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public GCSLGrammarParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public GCSLGrammarParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return GCSLGrammarParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g"; }


	  Model model;
	  //TODO k = ???
	  int k = 1001;
	  
	  
	  public String parseGCSL(Model model){
	    //this.k = k;
	    this.model = model;
	    try {
	      return pattern_specification();
	    } catch (RecognitionException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return "error during translation";
	  }
	  
	  private List<String> errors = new LinkedList<String>();
	  
	  public void displayRecognitionError(RecognitionException e) {
	        String hdr = getErrorHeader(e);
	        String msg = getErrorMessage(e, this.tokenNames);
	        errors.add(hdr + " " + msg);
	  }
	  public List<String> getErrors() {
	        return errors;
	  }



	// $ANTLR start "pattern_specification"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:63:1: pattern_specification returns [String property] : ( 'Hypothesis:' p= pattern_specification 'Goal:' g= pattern_specification bltl_vars | ocl_expr '->' 'forAll' '(' Variable '|' behavioral_pattern ')' | ocl_expr '->' 'exists' '(' Variable '|' behavioral_pattern ')' | behavioral_pattern EOF );
	public final String pattern_specification() throws RecognitionException {
		String property = null;


		Token Variable4=null;
		Token Variable7=null;
		String p =null;
		String g =null;
		ParserRuleReturnScope bltl_vars1 =null;
		String ocl_expr2 =null;
		String behavioral_pattern3 =null;
		String ocl_expr5 =null;
		String behavioral_pattern6 =null;
		String behavioral_pattern8 =null;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:64:3: ( 'Hypothesis:' p= pattern_specification 'Goal:' g= pattern_specification bltl_vars | ocl_expr '->' 'forAll' '(' Variable '|' behavioral_pattern ')' | ocl_expr '->' 'exists' '(' Variable '|' behavioral_pattern ')' | behavioral_pattern EOF )
			int alt1=4;
			switch ( input.LA(1) ) {
			case 30:
				{
				alt1=1;
				}
				break;
			case Variable:
				{
				int LA1_2 = input.LA(2);
				if ( (LA1_2==18) ) {
					int LA1_4 = input.LA(3);
					if ( (LA1_4==42) ) {
						alt1=2;
					}
					else if ( (LA1_4==39) ) {
						alt1=3;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return property;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 1, 4, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					if (state.backtracking>0) {state.failed=true; return property;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 1, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 31:
			case 33:
			case 54:
				{
				alt1=4;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return property;}
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:64:5: 'Hypothesis:' p= pattern_specification 'Goal:' g= pattern_specification bltl_vars
					{
					match(input,30,FOLLOW_30_in_pattern_specification68); if (state.failed) return property;
					pushFollow(FOLLOW_pattern_specification_in_pattern_specification71);
					p=pattern_specification();
					state._fsp--;
					if (state.failed) return property;
					match(input,29,FOLLOW_29_in_pattern_specification72); if (state.failed) return property;
					pushFollow(FOLLOW_pattern_specification_in_pattern_specification75);
					g=pattern_specification();
					state._fsp--;
					if (state.failed) return property;
					pushFollow(FOLLOW_bltl_vars_in_pattern_specification77);
					bltl_vars1=bltl_vars();
					state._fsp--;
					if (state.failed) return property;
					if ( state.backtracking==0 ) {property = "G<=#K"+p+"=>"+g+"\n"+(bltl_vars1!=null?input.toString(bltl_vars1.start,bltl_vars1.stop):null);
					    }
					}
					break;
				case 2 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:68:5: ocl_expr '->' 'forAll' '(' Variable '|' behavioral_pattern ')'
					{
					pushFollow(FOLLOW_ocl_expr_in_pattern_specification92);
					ocl_expr2=ocl_expr();
					state._fsp--;
					if (state.failed) return property;
					match(input,18,FOLLOW_18_in_pattern_specification94); if (state.failed) return property;
					match(input,42,FOLLOW_42_in_pattern_specification96); if (state.failed) return property;
					match(input,12,FOLLOW_12_in_pattern_specification98); if (state.failed) return property;
					Variable4=(Token)match(input,Variable,FOLLOW_Variable_in_pattern_specification99); if (state.failed) return property;
					match(input,57,FOLLOW_57_in_pattern_specification101); if (state.failed) return property;
					pushFollow(FOLLOW_behavioral_pattern_in_pattern_specification103);
					behavioral_pattern3=behavioral_pattern();
					state._fsp--;
					if (state.failed) return property;
					match(input,13,FOLLOW_13_in_pattern_specification104); if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      String moduleType = ocl_expr2;
					      String pattern = behavioral_pattern3;
					      property = "";
					      HashMap<String, Module> hash = model.getHash();
					      
					      //For each module selected by the forAll/exists
					      for(Module m:hash.values()){
					        if(m.getType().equals(moduleType)){
						        String pTemp = pattern;
						        pTemp = pTemp.replaceAll((Variable4!=null?Variable4.getText():null)+"\\.",m.getName()+".");
						        property += ("& "+"("+pTemp+")");
						       }
					      }
					      //For each module
					      for(Module m:hash.values()){
					          for(Substitution sub: m.getSubs()){
					            property = property.replaceAll(m.getName()+"\\."+sub.getOriginal(), sub.getSubstitute());
					          }
					          property = property.replaceAll(m.getName()+"\\.","");
					      }
					      property = property.substring(2);
					      property = property.replaceAll("\\[","(");
					      property = property.replaceAll("]",")");
					    }
					}
					break;
				case 3 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:94:5: ocl_expr '->' 'exists' '(' Variable '|' behavioral_pattern ')'
					{
					pushFollow(FOLLOW_ocl_expr_in_pattern_specification116);
					ocl_expr5=ocl_expr();
					state._fsp--;
					if (state.failed) return property;
					match(input,18,FOLLOW_18_in_pattern_specification118); if (state.failed) return property;
					match(input,39,FOLLOW_39_in_pattern_specification120); if (state.failed) return property;
					match(input,12,FOLLOW_12_in_pattern_specification122); if (state.failed) return property;
					Variable7=(Token)match(input,Variable,FOLLOW_Variable_in_pattern_specification123); if (state.failed) return property;
					match(input,57,FOLLOW_57_in_pattern_specification125); if (state.failed) return property;
					pushFollow(FOLLOW_behavioral_pattern_in_pattern_specification127);
					behavioral_pattern6=behavioral_pattern();
					state._fsp--;
					if (state.failed) return property;
					match(input,13,FOLLOW_13_in_pattern_specification128); if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      String moduleType = ocl_expr5;
					      String pattern = behavioral_pattern6;
					      property = "";
					      HashMap<String, Module> hash = model.getHash();
					      
					      //For each module selected by the forAll/exists
					      for(Module m:hash.values()){
					        if(m.getType().equals(moduleType)){
					          String pTemp = pattern;
					          pTemp = pTemp.replaceAll((Variable7!=null?Variable7.getText():null)+"\\.",m.getName()+".");
					          property += ("| "+"("+pTemp+")");
					         }
					      }
					      //For each module
					      for(Module m:hash.values()){
					          for(Substitution sub: m.getSubs()){
					            property = property.replaceAll(m.getName()+"\\."+sub.getOriginal(), sub.getSubstitute());
					          }
					          property = property.replaceAll(m.getName()+"\\.","");
					      }
					      property = property.substring(2);
					      property = property.replaceAll("\\[","(");
					      property = property.replaceAll("]",")");
					    }
					}
					break;
				case 4 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:121:5: behavioral_pattern EOF
					{
					pushFollow(FOLLOW_behavioral_pattern_in_pattern_specification143);
					behavioral_pattern8=behavioral_pattern();
					state._fsp--;
					if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      // TODO Because we can't locate the model, we can't translate the pattern. 
					      // This should be modified once PLASMA has been modified to parse a property with a given model
					      property = behavioral_pattern8;
					      System.err.println("Pattern only is not yet supported");
					    }
					match(input,EOF,FOLLOW_EOF_in_pattern_specification153); if (state.failed) return property;
					}
					break;

			}
		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return property;
	}
	// $ANTLR end "pattern_specification"



	// $ANTLR start "arith_proposition"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:131:1: arith_proposition : expr ( '<' | '<=' | '=' | '>=' | '>' |) expr ;
	public final void arith_proposition() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:3: ( expr ( '<' | '<=' | '=' | '>=' | '>' |) expr )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:5: expr ( '<' | '<=' | '=' | '>=' | '>' |) expr
			{
			pushFollow(FOLLOW_expr_in_arith_proposition166);
			expr();
			state._fsp--;
			if (state.failed) return;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:10: ( '<' | '<=' | '=' | '>=' | '>' |)
			int alt2=6;
			switch ( input.LA(1) ) {
			case 22:
				{
				alt2=1;
				}
				break;
			case 23:
				{
				alt2=2;
				}
				break;
			case 24:
				{
				alt2=3;
				}
				break;
			case 27:
				{
				alt2=4;
				}
				break;
			case 26:
				{
				alt2=5;
				}
				break;
			case Natural:
			case Variable:
			case 12:
			case 35:
			case 46:
			case 48:
			case 50:
				{
				alt2=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:11: '<'
					{
					match(input,22,FOLLOW_22_in_arith_proposition169); if (state.failed) return;
					}
					break;
				case 2 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:17: '<='
					{
					match(input,23,FOLLOW_23_in_arith_proposition173); if (state.failed) return;
					}
					break;
				case 3 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:24: '='
					{
					match(input,24,FOLLOW_24_in_arith_proposition177); if (state.failed) return;
					}
					break;
				case 4 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:30: '>='
					{
					match(input,27,FOLLOW_27_in_arith_proposition181); if (state.failed) return;
					}
					break;
				case 5 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:37: '>'
					{
					match(input,26,FOLLOW_26_in_arith_proposition185); if (state.failed) return;
					}
					break;
				case 6 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:132:43: 
					{
					}
					break;

			}

			pushFollow(FOLLOW_expr_in_arith_proposition191);
			expr();
			state._fsp--;
			if (state.failed) return;
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "arith_proposition"



	// $ANTLR start "operator"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:134:1: operator : ( '+' | '-' | '*' | '/' );
	public final void operator() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:134:9: ( '+' | '-' | '*' | '/' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:
			{
			if ( (input.LA(1) >= 14 && input.LA(1) <= 15)||input.LA(1)==17||input.LA(1)==20 ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "operator"



	// $ANTLR start "behavioral_pattern"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:136:1: behavioral_pattern returns [String property] : ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? ) | (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' ) | ( 'always' phi1= ocl ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' ) | (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval ) | (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? ) | (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? ) | ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? ) | ( ocl 'occurs' 'at most' '[' Natural ']' 'times' 'during' interval (pr= proba_pattern )? ) );
	public final String behavioral_pattern() throws RecognitionException {
		String property = null;


		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;
		ParserRuleReturnScope phi =null;
		ParserRuleReturnScope ac =null;
		Double pr1 =null;
		ParserRuleReturnScope cb =null;
		Double pr2 =null;
		ParserRuleReturnScope interval9 =null;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:138:3: ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? ) | (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' ) | ( 'always' phi1= ocl ) | ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' ) | (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval ) | (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? ) | (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? ) | ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? ) | ( ocl 'occurs' 'at most' '[' Natural ']' 'times' 'during' interval (pr= proba_pattern )? ) )
			int alt13=12;
			switch ( input.LA(1) ) {
			case 54:
				{
				int LA13_1 = input.LA(2);
				if ( (synpred13_GCSLGrammar()) ) {
					alt13=1;
				}
				else if ( (synpred15_GCSLGrammar()) ) {
					alt13=2;
				}
				else if ( (synpred17_GCSLGrammar()) ) {
					alt13=3;
				}
				else if ( (synpred19_GCSLGrammar()) ) {
					alt13=4;
				}
				else if ( (synpred22_GCSLGrammar()) ) {
					alt13=7;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return property;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 13, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 31:
				{
				int LA13_2 = input.LA(2);
				if ( (synpred20_GCSLGrammar()) ) {
					alt13=5;
				}
				else if ( (synpred23_GCSLGrammar()) ) {
					alt13=8;
				}
				else if ( (synpred25_GCSLGrammar()) ) {
					alt13=9;
				}
				else if ( (synpred29_GCSLGrammar()) ) {
					alt13=10;
				}
				else if ( (synpred31_GCSLGrammar()) ) {
					alt13=11;
				}
				else if ( (true) ) {
					alt13=12;
				}

				}
				break;
			case 33:
				{
				alt13=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return property;}
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:3: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:3: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:4: 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )?
					{
					match(input,54,FOLLOW_54_in_behavioral_pattern226); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern230);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern232); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern236);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern238); if (state.failed) return property;
					match(input,56,FOLLOW_56_in_behavioral_pattern240); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern244);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:140:5: (pr= proba_pattern )?
					int alt3=2;
					int LA3_0 = input.LA(1);
					if ( (LA3_0==55) ) {
						alt3=1;
					}
					switch (alt3) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:140:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern254);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      property = "G<=#"+(k-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => ";
					      String p2 = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+" F<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0))+" "+(phi2!=null?input.toString(phi2.start,phi2.stop):null);
					      if(pr!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					      property+="("+p2+"))";      
					      
					      if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					    }
					}

					}
					break;
				case 2 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )?
					{
					match(input,54,FOLLOW_54_in_behavioral_pattern288); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern292);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern294); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern298);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,44,FOLLOW_44_in_behavioral_pattern300); if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern302); if (state.failed) return property;
					match(input,41,FOLLOW_41_in_behavioral_pattern304); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern308);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:156:5: (pr= proba_pattern )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==55) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:156:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern317);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      property = "G<=#"+(k-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => ";
					      String p2 = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+" G<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0))+" "+(phi2!=null?input.toString(phi2.start,phi2.stop):null);
					      if(pr!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					      property+="("+p2+"))";      
					      
					      if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					    }
					}

					}
					break;
				case 3 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:5: ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:5: ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:6: 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )?
					{
					match(input,54,FOLLOW_54_in_behavioral_pattern346); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern350);
					phi=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern352); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern356);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,45,FOLLOW_45_in_behavioral_pattern358); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern362);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern364); if (state.failed) return property;
					match(input,41,FOLLOW_41_in_behavioral_pattern366); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern370);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:171:5: (pr= proba_pattern )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==55) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:171:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern379);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      property = "G<=#"+(k-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+"("+(phi!=null?input.toString(phi.start,phi.stop):null)+" => ";
					      String p2 = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+" G<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0))+" ("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => "+(phi2!=null?input.toString(phi2.start,phi2.stop):null)+")";
					      if(pr!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					      property+="("+p2+"))";      
					      
					      if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					    }
					}

					}
					break;
				case 4 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )?
					{
					match(input,54,FOLLOW_54_in_behavioral_pattern408); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern412);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern414); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern418);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,37,FOLLOW_37_in_behavioral_pattern420); if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern422); if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern424); if (state.failed) return property;
					match(input,41,FOLLOW_41_in_behavioral_pattern426); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern430);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:186:5: (pr= proba_pattern )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==55) ) {
						alt6=1;
					}
					switch (alt6) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:186:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern439);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      property = "G<=#"+(k-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => ";
					      String p2 = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+" G<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0))+" !"+(phi2!=null?input.toString(phi2.start,phi2.stop):null);
					      if(pr!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					      property+="("+p2+"))";      
					      
					      if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					    }
					}

					}
					break;
				case 5 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:5: (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:5: (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:6: phi1= ocl 'implies' phi2= ocl 'holds' 'forever'
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern471);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,45,FOLLOW_45_in_behavioral_pattern473); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern477);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,44,FOLLOW_44_in_behavioral_pattern479); if (state.failed) return property;
					match(input,43,FOLLOW_43_in_behavioral_pattern481); if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      property = "unknown pattern";
					    }
					}

					}
					break;
				case 6 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:205:5: ( 'always' phi1= ocl )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:205:5: ( 'always' phi1= ocl )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:205:6: 'always' phi1= ocl
					{
					match(input,33,FOLLOW_33_in_behavioral_pattern495); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern499);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      property = "G<=#"+k+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+")";
					    }
					}

					}
					break;
				case 7 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds'
					{
					match(input,54,FOLLOW_54_in_behavioral_pattern513); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern517);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern519); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern523);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,44,FOLLOW_44_in_behavioral_pattern525); if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      property = "G<=#"+k+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => "+(phi2!=null?input.toString(phi2.start,phi2.stop):null)+")";
					    }
					}

					}
					break;
				case 8 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:5: (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:5: (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:6: phi1= ocl 'implies' phi2= ocl 'during' 'following' interval
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern541);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,45,FOLLOW_45_in_behavioral_pattern543); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern547);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern549); if (state.failed) return property;
					match(input,41,FOLLOW_41_in_behavioral_pattern551); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern553);
					interval9=interval();
					state._fsp--;
					if (state.failed) return property;
					if ( state.backtracking==0 ) {
					      property = "X<=#"+(interval9!=null?((GCSLGrammarParser.interval_return)interval9).a:0)+" G<=#"+((interval9!=null?((GCSLGrammarParser.interval_return)interval9).a:0)-(interval9!=null?((GCSLGrammarParser.interval_return)interval9).b:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => "+(phi2!=null?input.toString(phi2.start,phi2.stop):null)+")";
					    }
					}

					}
					break;
				case 9 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:5: (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:5: (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:6: phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )?
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern569);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern571); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern575);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					match(input,49,FOLLOW_49_in_behavioral_pattern577); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern581);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:218:5: (pr= proba_pattern )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0==55) ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:218:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern590);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      property = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+"G<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+" => ";
					      String p2 = "F<=#"+(k-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+" "+(phi2!=null?input.toString(phi2.start,phi2.stop):null);
					      if(pr!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					      property+="("+p2+"))";      
					      
					      if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					    }
					}

					}
					break;
				case 10 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:5: (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:5: (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:6: phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )?
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern621);
					phi=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern623); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern627);
					ab=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:233:5: (pr= proba_pattern )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==55) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:233:6: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern637);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					match(input,45,FOLLOW_45_in_behavioral_pattern659); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern663);
					phi1=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern665); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern669);
					ac=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:239:5: (pr1= proba_pattern )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==55) ) {
						alt9=1;
					}
					switch (alt9) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:239:6: pr1= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern679);
							pr1=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr1 < 0 ||pr1 > 1)&&pr1!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					match(input,51,FOLLOW_51_in_behavioral_pattern701); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern705);
					phi2=ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern707); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern711);
					cb=interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:245:5: (pr2= proba_pattern )?
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==55) ) {
						alt10=1;
					}
					switch (alt10) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:245:6: pr2= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern721);
							pr2=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							if ( state.backtracking==0 ) {
							        if((pr2 < 0 ||pr2 > 1)&&pr2!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
							      }
							}
							break;

					}

					if ( state.backtracking==0 ) {
					      //property = ""; //TODO see Benoit
					      
					      String p = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+"G<=#"+((ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)-(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0))+(phi!=null?input.toString(phi.start,phi.stop):null)+")";
					      if(pr!=null)
					        p = "Pr>="+pr+"("+p+")";
					      String p1 = "X<=#"+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+"G<=#"+((ac!=null?((GCSLGrammarParser.interval_return)ac).b:0)-(ac!=null?((GCSLGrammarParser.interval_return)ac).a:0))+"("+(phi1!=null?input.toString(phi1.start,phi1.stop):null)+")";
					      if(pr1!=null)
					        p1 = "Pr>="+pr+"("+p1+")";
					      String p2 = "X<=#"+(cb!=null?((GCSLGrammarParser.interval_return)cb).a:0)+"G<=#"+((cb!=null?((GCSLGrammarParser.interval_return)cb).b:0)-(cb!=null?((GCSLGrammarParser.interval_return)cb).a:0))+"("+(phi2!=null?input.toString(phi2.start,phi2.stop):null)+")";
					      if(pr2!=null)
					        p2 = "Pr>="+pr+"("+p2+")";
					        
					      property = p+"=> ("+p1+"&"+p2+")";
					    }
					}

					}
					break;
				case 11 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:5: ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:5: ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:6: ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )?
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern751);
					ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern753); if (state.failed) return property;
					match(input,31,FOLLOW_31_in_behavioral_pattern755); if (state.failed) return property;
					match(input,Natural,FOLLOW_Natural_in_behavioral_pattern756); if (state.failed) return property;
					match(input,32,FOLLOW_32_in_behavioral_pattern757); if (state.failed) return property;
					match(input,52,FOLLOW_52_in_behavioral_pattern759); if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern761); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern763);
					interval();
					state._fsp--;
					if (state.failed) return property;
					match(input,49,FOLLOW_49_in_behavioral_pattern765); if (state.failed) return property;
					pushFollow(FOLLOW_ocl_in_behavioral_pattern767);
					ocl();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:74: (pr= proba_pattern )?
					int alt11=2;
					int LA11_0 = input.LA(1);
					if ( (LA11_0==55) ) {
						alt11=1;
					}
					switch (alt11) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:74: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern771);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							}
							break;

					}

					if ( state.backtracking==0 ) {
					    //TODO
					      System.err.println("no operator has been implemented for this translation");
					      //if((ab!=null?((GCSLGrammarParser.interval_return)ab).b:0) > k) System.err.println("bad time interval, [a="+(ab!=null?((GCSLGrammarParser.interval_return)ab).a:0)+",b="+(ab!=null?((GCSLGrammarParser.interval_return)ab).b:0)+"], b must be <= "+k);
					      property = "";
					      if((pr < 0 ||pr > 1)&&pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
					    }
					}

					}
					break;
				case 12 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:274:5: ( ocl 'occurs' 'at most' '[' Natural ']' 'times' 'during' interval (pr= proba_pattern )? )
					{
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:274:5: ( ocl 'occurs' 'at most' '[' Natural ']' 'times' 'during' interval (pr= proba_pattern )? )
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:274:6: ocl 'occurs' 'at most' '[' Natural ']' 'times' 'during' interval (pr= proba_pattern )?
					{
					pushFollow(FOLLOW_ocl_in_behavioral_pattern786);
					ocl();
					state._fsp--;
					if (state.failed) return property;
					match(input,47,FOLLOW_47_in_behavioral_pattern788); if (state.failed) return property;
					match(input,34,FOLLOW_34_in_behavioral_pattern790); if (state.failed) return property;
					match(input,31,FOLLOW_31_in_behavioral_pattern792); if (state.failed) return property;
					match(input,Natural,FOLLOW_Natural_in_behavioral_pattern793); if (state.failed) return property;
					match(input,32,FOLLOW_32_in_behavioral_pattern794); if (state.failed) return property;
					match(input,52,FOLLOW_52_in_behavioral_pattern796); if (state.failed) return property;
					match(input,38,FOLLOW_38_in_behavioral_pattern798); if (state.failed) return property;
					pushFollow(FOLLOW_interval_in_behavioral_pattern800);
					interval();
					state._fsp--;
					if (state.failed) return property;
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:274:71: (pr= proba_pattern )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==55) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:274:71: pr= proba_pattern
							{
							pushFollow(FOLLOW_proba_pattern_in_behavioral_pattern804);
							pr=proba_pattern();
							state._fsp--;
							if (state.failed) return property;
							}
							break;

					}

					if ( state.backtracking==0 ) {
					    //TODO
					      System.err.println("no operator has been implemented for this translation");
					      property = "";
					    }
					}

					}
					break;

			}
		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return property;
	}
	// $ANTLR end "behavioral_pattern"



	// $ANTLR start "proba_pattern"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:282:1: proba_pattern returns [Double pr] : 'with a probability' Float ;
	public final Double proba_pattern() throws RecognitionException {
		Double pr = null;


		Token Float10=null;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:283:3: ( 'with a probability' Float )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:283:5: 'with a probability' Float
			{
			match(input,55,FOLLOW_55_in_proba_pattern828); if (state.failed) return pr;
			Float10=(Token)match(input,Float,FOLLOW_Float_in_proba_pattern830); if (state.failed) return pr;
			if ( state.backtracking==0 ) {pr=Double.parseDouble((Float10!=null?Float10.getText():null));}
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return pr;
	}
	// $ANTLR end "proba_pattern"



	// $ANTLR start "prop"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:288:1: prop : ( rel | rel '&' prop | rel '|' prop | rel '=>' prop | '!' prop | '(' prop ')' | 'true' | 'false' );
	public final void prop() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:289:3: ( rel | rel '&' prop | rel '|' prop | rel '=>' prop | '!' prop | '(' prop ')' | 'true' | 'false' )
			int alt14=8;
			switch ( input.LA(1) ) {
			case 12:
				{
				int LA14_1 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}
				else if ( (synpred38_GCSLGrammar()) ) {
					alt14=6;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 46:
				{
				int LA14_2 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 50:
				{
				int LA14_3 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 48:
				{
				int LA14_4 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 4, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 35:
				{
				int LA14_5 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 5, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case Natural:
				{
				int LA14_6 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 6, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case Variable:
				{
				int LA14_7 = input.LA(2);
				if ( (synpred33_GCSLGrammar()) ) {
					alt14=1;
				}
				else if ( (synpred34_GCSLGrammar()) ) {
					alt14=2;
				}
				else if ( (synpred35_GCSLGrammar()) ) {
					alt14=3;
				}
				else if ( (synpred36_GCSLGrammar()) ) {
					alt14=4;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 7, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 10:
				{
				alt14=5;
				}
				break;
			case 53:
				{
				alt14=7;
				}
				break;
			case 40:
				{
				alt14=8;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}
			switch (alt14) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:289:5: rel
					{
					pushFollow(FOLLOW_rel_in_prop848);
					rel();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 2 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:290:5: rel '&' prop
					{
					pushFollow(FOLLOW_rel_in_prop855);
					rel();
					state._fsp--;
					if (state.failed) return;
					match(input,11,FOLLOW_11_in_prop857); if (state.failed) return;
					pushFollow(FOLLOW_prop_in_prop859);
					prop();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 3 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:291:5: rel '|' prop
					{
					pushFollow(FOLLOW_rel_in_prop865);
					rel();
					state._fsp--;
					if (state.failed) return;
					match(input,57,FOLLOW_57_in_prop867); if (state.failed) return;
					pushFollow(FOLLOW_prop_in_prop869);
					prop();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 4 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:292:5: rel '=>' prop
					{
					pushFollow(FOLLOW_rel_in_prop875);
					rel();
					state._fsp--;
					if (state.failed) return;
					match(input,25,FOLLOW_25_in_prop877); if (state.failed) return;
					pushFollow(FOLLOW_prop_in_prop879);
					prop();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 5 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:293:5: '!' prop
					{
					match(input,10,FOLLOW_10_in_prop885); if (state.failed) return;
					pushFollow(FOLLOW_prop_in_prop887);
					prop();
					state._fsp--;
					if (state.failed) return;
					}
					break;
				case 6 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:294:5: '(' prop ')'
					{
					match(input,12,FOLLOW_12_in_prop893); if (state.failed) return;
					pushFollow(FOLLOW_prop_in_prop895);
					prop();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_prop897); if (state.failed) return;
					}
					break;
				case 7 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:295:5: 'true'
					{
					match(input,53,FOLLOW_53_in_prop903); if (state.failed) return;
					}
					break;
				case 8 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:296:5: 'false'
					{
					match(input,40,FOLLOW_40_in_prop909); if (state.failed) return;
					}
					break;

			}
		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prop"


	public static class ocl_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "ocl"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:300:1: ocl : '[' rel ']' ;
	public final GCSLGrammarParser.ocl_return ocl() throws RecognitionException {
		GCSLGrammarParser.ocl_return retval = new GCSLGrammarParser.ocl_return();
		retval.start = input.LT(1);

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:300:4: ( '[' rel ']' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:300:6: '[' rel ']'
			{
			match(input,31,FOLLOW_31_in_ocl924); if (state.failed) return retval;
			pushFollow(FOLLOW_rel_in_ocl925);
			rel();
			state._fsp--;
			if (state.failed) return retval;
			match(input,32,FOLLOW_32_in_ocl926); if (state.failed) return retval;
			}

			retval.stop = input.LT(-1);

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "ocl"



	// $ANTLR start "rel"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:302:1: rel : expr ( '<' | '<=' | '=' | '>=' | '>' ) expr ;
	public final void rel() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:302:4: ( expr ( '<' | '<=' | '=' | '>=' | '>' ) expr )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:302:6: expr ( '<' | '<=' | '=' | '>=' | '>' ) expr
			{
			pushFollow(FOLLOW_expr_in_rel933);
			expr();
			state._fsp--;
			if (state.failed) return;
			if ( (input.LA(1) >= 22 && input.LA(1) <= 24)||(input.LA(1) >= 26 && input.LA(1) <= 27) ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			pushFollow(FOLLOW_expr_in_rel956);
			expr();
			state._fsp--;
			if (state.failed) return;
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "rel"



	// $ANTLR start "ocl_expr"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:304:1: ocl_expr returns [String moduleType] : module= Variable ;
	public final String ocl_expr() throws RecognitionException {
		String moduleType = null;


		Token module=null;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:304:36: (module= Variable )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:304:38: module= Variable
			{
			module=(Token)match(input,Variable,FOLLOW_Variable_in_ocl_expr968); if (state.failed) return moduleType;
			if ( state.backtracking==0 ) {
			    moduleType = (module!=null?module.getText():null);
			  }
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return moduleType;
	}
	// $ANTLR end "ocl_expr"



	// $ANTLR start "expr"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:309:1: expr : ( '(' expr ')' | 'mean(' expr ')' | 'sum(' expr ')' | 'prod(' expr ')' | 'at(' expr ',' time ')' | Natural | Variable '.' Variable | Variable ) ( operator expr )? ;
	public final void expr() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:310:3: ( ( '(' expr ')' | 'mean(' expr ')' | 'sum(' expr ')' | 'prod(' expr ')' | 'at(' expr ',' time ')' | Natural | Variable '.' Variable | Variable ) ( operator expr )? )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:310:4: ( '(' expr ')' | 'mean(' expr ')' | 'sum(' expr ')' | 'prod(' expr ')' | 'at(' expr ',' time ')' | Natural | Variable '.' Variable | Variable ) ( operator expr )?
			{
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:310:4: ( '(' expr ')' | 'mean(' expr ')' | 'sum(' expr ')' | 'prod(' expr ')' | 'at(' expr ',' time ')' | Natural | Variable '.' Variable | Variable )
			int alt15=8;
			switch ( input.LA(1) ) {
			case 12:
				{
				alt15=1;
				}
				break;
			case 46:
				{
				alt15=2;
				}
				break;
			case 50:
				{
				alt15=3;
				}
				break;
			case 48:
				{
				alt15=4;
				}
				break;
			case 35:
				{
				alt15=5;
				}
				break;
			case Natural:
				{
				alt15=6;
				}
				break;
			case Variable:
				{
				int LA15_7 = input.LA(2);
				if ( (LA15_7==19) ) {
					alt15=7;
				}
				else if ( (LA15_7==EOF||LA15_7==Natural||LA15_7==Variable||(LA15_7 >= 11 && LA15_7 <= 17)||LA15_7==20||(LA15_7 >= 22 && LA15_7 <= 27)||LA15_7==32||LA15_7==35||LA15_7==46||LA15_7==48||LA15_7==50||LA15_7==57) ) {
					alt15=8;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 15, 7, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}
			switch (alt15) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:310:6: '(' expr ')'
					{
					match(input,12,FOLLOW_12_in_expr982); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr984);
					expr();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_expr986); if (state.failed) return;
					}
					break;
				case 2 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:311:5: 'mean(' expr ')'
					{
					match(input,46,FOLLOW_46_in_expr992); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr994);
					expr();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_expr996); if (state.failed) return;
					}
					break;
				case 3 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:312:5: 'sum(' expr ')'
					{
					match(input,50,FOLLOW_50_in_expr1003); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr1005);
					expr();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_expr1007); if (state.failed) return;
					}
					break;
				case 4 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:313:5: 'prod(' expr ')'
					{
					match(input,48,FOLLOW_48_in_expr1014); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr1016);
					expr();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_expr1018); if (state.failed) return;
					}
					break;
				case 5 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:314:5: 'at(' expr ',' time ')'
					{
					match(input,35,FOLLOW_35_in_expr1025); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr1027);
					expr();
					state._fsp--;
					if (state.failed) return;
					match(input,16,FOLLOW_16_in_expr1029); if (state.failed) return;
					pushFollow(FOLLOW_time_in_expr1031);
					time();
					state._fsp--;
					if (state.failed) return;
					match(input,13,FOLLOW_13_in_expr1033); if (state.failed) return;
					}
					break;
				case 6 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:315:5: Natural
					{
					match(input,Natural,FOLLOW_Natural_in_expr1039); if (state.failed) return;
					}
					break;
				case 7 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:316:5: Variable '.' Variable
					{
					match(input,Variable,FOLLOW_Variable_in_expr1045); if (state.failed) return;
					match(input,19,FOLLOW_19_in_expr1046); if (state.failed) return;
					match(input,Variable,FOLLOW_Variable_in_expr1047); if (state.failed) return;
					}
					break;
				case 8 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:317:5: Variable
					{
					match(input,Variable,FOLLOW_Variable_in_expr1053); if (state.failed) return;
					}
					break;

			}

			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:318:3: ( operator expr )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( ((LA16_0 >= 14 && LA16_0 <= 15)||LA16_0==17||LA16_0==20) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:318:4: operator expr
					{
					pushFollow(FOLLOW_operator_in_expr1061);
					operator();
					state._fsp--;
					if (state.failed) return;
					pushFollow(FOLLOW_expr_in_expr1063);
					expr();
					state._fsp--;
					if (state.failed) return;
					}
					break;

			}

			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "expr"


	public static class interval_return extends ParserRuleReturnScope {
		public int a;
		public int b;
	};


	// $ANTLR start "interval"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:321:1: interval returns [int a, int b] : ( '[' | '(' ) t1= time ( '-' t2= time )? ( ']' | ')' ) ;
	public final GCSLGrammarParser.interval_return interval() throws RecognitionException {
		GCSLGrammarParser.interval_return retval = new GCSLGrammarParser.interval_return();
		retval.start = input.LT(1);

		int t1 =0;
		int t2 =0;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:321:32: ( ( '[' | '(' ) t1= time ( '-' t2= time )? ( ']' | ')' ) )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:321:34: ( '[' | '(' ) t1= time ( '-' t2= time )? ( ']' | ')' )
			{
			if ( input.LA(1)==12||input.LA(1)==31 ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			pushFollow(FOLLOW_time_in_interval1087);
			t1=time();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) {retval.a =0; retval.b =t1;}
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:321:72: ( '-' t2= time )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==17) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:321:73: '-' t2= time
					{
					match(input,17,FOLLOW_17_in_interval1091); if (state.failed) return retval;
					pushFollow(FOLLOW_time_in_interval1095);
					t2=time();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) {retval.a =retval.b; retval.b =t2;}
					}
					break;

			}

			if ( input.LA(1)==13||input.LA(1)==32 ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			if ( state.backtracking==0 ) {if (retval.a > retval.b) System.err.println("bad time interval, ["+retval.a+","+retval.b+"]");}
			}

			retval.stop = input.LT(-1);

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "interval"



	// $ANTLR start "time"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:325:1: time returns [int time] : Natural TimeUnit ;
	public final int time() throws RecognitionException {
		int time = 0;


		Token Natural11=null;

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:325:24: ( Natural TimeUnit )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:325:26: Natural TimeUnit
			{
			Natural11=(Token)match(input,Natural,FOLLOW_Natural_in_time1122); if (state.failed) return time;
			if ( state.backtracking==0 ) {time = Integer.parseInt((Natural11!=null?Natural11.getText():null));}
			match(input,TimeUnit,FOLLOW_TimeUnit_in_time1125); if (state.failed) return time;
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return time;
	}
	// $ANTLR end "time"


	public static class bltl_vars_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "bltl_vars"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:327:1: bltl_vars : 'BLTL Vars:' ( bltl_var )* ;
	public final GCSLGrammarParser.bltl_vars_return bltl_vars() throws RecognitionException {
		GCSLGrammarParser.bltl_vars_return retval = new GCSLGrammarParser.bltl_vars_return();
		retval.start = input.LT(1);

		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:328:3: ( 'BLTL Vars:' ( bltl_var )* )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:328:4: 'BLTL Vars:' ( bltl_var )*
			{
			match(input,28,FOLLOW_28_in_bltl_vars1134); if (state.failed) return retval;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:328:17: ( bltl_var )*
			loop18:
			while (true) {
				int alt18=2;
				int LA18_0 = input.LA(1);
				if ( (LA18_0==Ident) ) {
					alt18=1;
				}

				switch (alt18) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:328:18: bltl_var
					{
					pushFollow(FOLLOW_bltl_var_in_bltl_vars1137);
					bltl_var();
					state._fsp--;
					if (state.failed) return retval;
					}
					break;

				default :
					break loop18;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "bltl_vars"



	// $ANTLR start "bltl_var"
	// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:329:1: bltl_var : Ident '=' '[' Float ';' Float ']' 'by' Float ;
	public final void bltl_var() throws RecognitionException {
		try {
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:330:3: ( Ident '=' '[' Float ';' Float ']' 'by' Float )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:330:5: Ident '=' '[' Float ';' Float ']' 'by' Float
			{
			match(input,Ident,FOLLOW_Ident_in_bltl_var1148); if (state.failed) return;
			match(input,24,FOLLOW_24_in_bltl_var1149); if (state.failed) return;
			match(input,31,FOLLOW_31_in_bltl_var1150); if (state.failed) return;
			match(input,Float,FOLLOW_Float_in_bltl_var1151); if (state.failed) return;
			match(input,21,FOLLOW_21_in_bltl_var1152); if (state.failed) return;
			match(input,Float,FOLLOW_Float_in_bltl_var1153); if (state.failed) return;
			match(input,32,FOLLOW_32_in_bltl_var1154); if (state.failed) return;
			match(input,36,FOLLOW_36_in_bltl_var1156); if (state.failed) return;
			match(input,Float,FOLLOW_Float_in_bltl_var1158); if (state.failed) return;
			}

		}
		 
		    catch (RecognitionException e) {
		        reportError(e);
		        displayRecognitionError(e);
		        throw e;
		    }

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "bltl_var"

	// $ANTLR start synpred13_GCSLGrammar
	public final void synpred13_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:3: ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:3: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:3: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:139:4: 'whenever' phi1= ocl 'occurs' phi2= ocl 'occurs' 'within' ab= interval (pr= proba_pattern )?
		{
		match(input,54,FOLLOW_54_in_synpred13_GCSLGrammar226); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred13_GCSLGrammar230);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred13_GCSLGrammar232); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred13_GCSLGrammar236);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred13_GCSLGrammar238); if (state.failed) return;
		match(input,56,FOLLOW_56_in_synpred13_GCSLGrammar240); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred13_GCSLGrammar244);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:140:5: (pr= proba_pattern )?
		int alt19=2;
		int LA19_0 = input.LA(1);
		if ( (LA19_0==55) ) {
			alt19=1;
		}
		switch (alt19) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:140:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred13_GCSLGrammar254);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred13_GCSLGrammar

	// $ANTLR start synpred15_GCSLGrammar
	public final void synpred15_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:5: ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:155:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' 'during' 'following' ab= interval (pr= proba_pattern )?
		{
		match(input,54,FOLLOW_54_in_synpred15_GCSLGrammar288); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred15_GCSLGrammar292);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred15_GCSLGrammar294); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred15_GCSLGrammar298);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,44,FOLLOW_44_in_synpred15_GCSLGrammar300); if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred15_GCSLGrammar302); if (state.failed) return;
		match(input,41,FOLLOW_41_in_synpred15_GCSLGrammar304); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred15_GCSLGrammar308);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:156:5: (pr= proba_pattern )?
		int alt20=2;
		int LA20_0 = input.LA(1);
		if ( (LA20_0==55) ) {
			alt20=1;
		}
		switch (alt20) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:156:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred15_GCSLGrammar317);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred15_GCSLGrammar

	// $ANTLR start synpred17_GCSLGrammar
	public final void synpred17_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi =null;
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:5: ( ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:5: ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:5: ( 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:170:6: 'whenever' phi= ocl 'occurs' phi1= ocl 'implies' phi2= ocl 'during' 'following' ab= interval (pr= proba_pattern )?
		{
		match(input,54,FOLLOW_54_in_synpred17_GCSLGrammar346); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred17_GCSLGrammar350);
		phi=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred17_GCSLGrammar352); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred17_GCSLGrammar356);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,45,FOLLOW_45_in_synpred17_GCSLGrammar358); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred17_GCSLGrammar362);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred17_GCSLGrammar364); if (state.failed) return;
		match(input,41,FOLLOW_41_in_synpred17_GCSLGrammar366); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred17_GCSLGrammar370);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:171:5: (pr= proba_pattern )?
		int alt21=2;
		int LA21_0 = input.LA(1);
		if ( (LA21_0==55) ) {
			alt21=1;
		}
		switch (alt21) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:171:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred17_GCSLGrammar379);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred17_GCSLGrammar

	// $ANTLR start synpred19_GCSLGrammar
	public final void synpred19_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:5: ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:185:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'does not' 'occurs' 'during' 'following' ab= interval (pr= proba_pattern )?
		{
		match(input,54,FOLLOW_54_in_synpred19_GCSLGrammar408); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred19_GCSLGrammar412);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred19_GCSLGrammar414); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred19_GCSLGrammar418);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,37,FOLLOW_37_in_synpred19_GCSLGrammar420); if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred19_GCSLGrammar422); if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred19_GCSLGrammar424); if (state.failed) return;
		match(input,41,FOLLOW_41_in_synpred19_GCSLGrammar426); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred19_GCSLGrammar430);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:186:5: (pr= proba_pattern )?
		int alt22=2;
		int LA22_0 = input.LA(1);
		if ( (LA22_0==55) ) {
			alt22=1;
		}
		switch (alt22) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:186:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred19_GCSLGrammar439);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred19_GCSLGrammar

	// $ANTLR start synpred20_GCSLGrammar
	public final void synpred20_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:5: ( (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:5: (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:5: (phi1= ocl 'implies' phi2= ocl 'holds' 'forever' )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:201:6: phi1= ocl 'implies' phi2= ocl 'holds' 'forever'
		{
		pushFollow(FOLLOW_ocl_in_synpred20_GCSLGrammar471);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,45,FOLLOW_45_in_synpred20_GCSLGrammar473); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred20_GCSLGrammar477);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,44,FOLLOW_44_in_synpred20_GCSLGrammar479); if (state.failed) return;
		match(input,43,FOLLOW_43_in_synpred20_GCSLGrammar481); if (state.failed) return;
		}

		}

	}
	// $ANTLR end synpred20_GCSLGrammar

	// $ANTLR start synpred22_GCSLGrammar
	public final void synpred22_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:5: ( ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:5: ( 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds' )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:209:6: 'whenever' phi1= ocl 'occurs' phi2= ocl 'holds'
		{
		match(input,54,FOLLOW_54_in_synpred22_GCSLGrammar513); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred22_GCSLGrammar517);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred22_GCSLGrammar519); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred22_GCSLGrammar523);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,44,FOLLOW_44_in_synpred22_GCSLGrammar525); if (state.failed) return;
		}

		}

	}
	// $ANTLR end synpred22_GCSLGrammar

	// $ANTLR start synpred23_GCSLGrammar
	public final void synpred23_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope phi2 =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:5: ( (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:5: (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:5: (phi1= ocl 'implies' phi2= ocl 'during' 'following' interval )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:213:6: phi1= ocl 'implies' phi2= ocl 'during' 'following' interval
		{
		pushFollow(FOLLOW_ocl_in_synpred23_GCSLGrammar541);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,45,FOLLOW_45_in_synpred23_GCSLGrammar543); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred23_GCSLGrammar547);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred23_GCSLGrammar549); if (state.failed) return;
		match(input,41,FOLLOW_41_in_synpred23_GCSLGrammar551); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred23_GCSLGrammar553);
		interval();
		state._fsp--;
		if (state.failed) return;
		}

		}

	}
	// $ANTLR end synpred23_GCSLGrammar

	// $ANTLR start synpred25_GCSLGrammar
	public final void synpred25_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope ab =null;
		ParserRuleReturnScope phi2 =null;
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:5: ( (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:5: (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:5: (phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:217:6: phi1= ocl 'during' ab= interval 'raises' phi2= ocl (pr= proba_pattern )?
		{
		pushFollow(FOLLOW_ocl_in_synpred25_GCSLGrammar569);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred25_GCSLGrammar571); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred25_GCSLGrammar575);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		match(input,49,FOLLOW_49_in_synpred25_GCSLGrammar577); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred25_GCSLGrammar581);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:218:5: (pr= proba_pattern )?
		int alt23=2;
		int LA23_0 = input.LA(1);
		if ( (LA23_0==55) ) {
			alt23=1;
		}
		switch (alt23) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:218:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred25_GCSLGrammar590);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred25_GCSLGrammar

	// $ANTLR start synpred29_GCSLGrammar
	public final void synpred29_GCSLGrammar_fragment() throws RecognitionException {
		ParserRuleReturnScope phi =null;
		ParserRuleReturnScope ab =null;
		Double pr =null;
		ParserRuleReturnScope phi1 =null;
		ParserRuleReturnScope ac =null;
		Double pr1 =null;
		ParserRuleReturnScope phi2 =null;
		ParserRuleReturnScope cb =null;
		Double pr2 =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:5: ( (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:5: (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:5: (phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:232:6: phi= ocl 'during' ab= interval (pr= proba_pattern )? 'implies' phi1= ocl 'during' ac= interval (pr1= proba_pattern )? 'then' phi2= ocl 'during' cb= interval (pr2= proba_pattern )?
		{
		pushFollow(FOLLOW_ocl_in_synpred29_GCSLGrammar621);
		phi=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred29_GCSLGrammar623); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred29_GCSLGrammar627);
		ab=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:233:5: (pr= proba_pattern )?
		int alt24=2;
		int LA24_0 = input.LA(1);
		if ( (LA24_0==55) ) {
			alt24=1;
		}
		switch (alt24) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:233:6: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred29_GCSLGrammar637);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		match(input,45,FOLLOW_45_in_synpred29_GCSLGrammar659); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred29_GCSLGrammar663);
		phi1=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred29_GCSLGrammar665); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred29_GCSLGrammar669);
		ac=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:239:5: (pr1= proba_pattern )?
		int alt25=2;
		int LA25_0 = input.LA(1);
		if ( (LA25_0==55) ) {
			alt25=1;
		}
		switch (alt25) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:239:6: pr1= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred29_GCSLGrammar679);
				pr1=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		match(input,51,FOLLOW_51_in_synpred29_GCSLGrammar701); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred29_GCSLGrammar705);
		phi2=ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred29_GCSLGrammar707); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred29_GCSLGrammar711);
		cb=interval();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:245:5: (pr2= proba_pattern )?
		int alt26=2;
		int LA26_0 = input.LA(1);
		if ( (LA26_0==55) ) {
			alt26=1;
		}
		switch (alt26) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:245:6: pr2= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred29_GCSLGrammar721);
				pr2=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred29_GCSLGrammar

	// $ANTLR start synpred31_GCSLGrammar
	public final void synpred31_GCSLGrammar_fragment() throws RecognitionException {
		Double pr =null;

		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:5: ( ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? ) )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:5: ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? )
		{
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:5: ( ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )? )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:6: ocl 'occurs' '[' Natural ']' 'times' 'during' interval 'raises' ocl (pr= proba_pattern )?
		{
		pushFollow(FOLLOW_ocl_in_synpred31_GCSLGrammar751);
		ocl();
		state._fsp--;
		if (state.failed) return;
		match(input,47,FOLLOW_47_in_synpred31_GCSLGrammar753); if (state.failed) return;
		match(input,31,FOLLOW_31_in_synpred31_GCSLGrammar755); if (state.failed) return;
		match(input,Natural,FOLLOW_Natural_in_synpred31_GCSLGrammar756); if (state.failed) return;
		match(input,32,FOLLOW_32_in_synpred31_GCSLGrammar757); if (state.failed) return;
		match(input,52,FOLLOW_52_in_synpred31_GCSLGrammar759); if (state.failed) return;
		match(input,38,FOLLOW_38_in_synpred31_GCSLGrammar761); if (state.failed) return;
		pushFollow(FOLLOW_interval_in_synpred31_GCSLGrammar763);
		interval();
		state._fsp--;
		if (state.failed) return;
		match(input,49,FOLLOW_49_in_synpred31_GCSLGrammar765); if (state.failed) return;
		pushFollow(FOLLOW_ocl_in_synpred31_GCSLGrammar767);
		ocl();
		state._fsp--;
		if (state.failed) return;
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:74: (pr= proba_pattern )?
		int alt27=2;
		int LA27_0 = input.LA(1);
		if ( (LA27_0==55) ) {
			alt27=1;
		}
		switch (alt27) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:266:74: pr= proba_pattern
				{
				pushFollow(FOLLOW_proba_pattern_in_synpred31_GCSLGrammar771);
				pr=proba_pattern();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

		}

	}
	// $ANTLR end synpred31_GCSLGrammar

	// $ANTLR start synpred33_GCSLGrammar
	public final void synpred33_GCSLGrammar_fragment() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:289:5: ( rel )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:289:5: rel
		{
		pushFollow(FOLLOW_rel_in_synpred33_GCSLGrammar848);
		rel();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred33_GCSLGrammar

	// $ANTLR start synpred34_GCSLGrammar
	public final void synpred34_GCSLGrammar_fragment() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:290:5: ( rel '&' prop )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:290:5: rel '&' prop
		{
		pushFollow(FOLLOW_rel_in_synpred34_GCSLGrammar855);
		rel();
		state._fsp--;
		if (state.failed) return;
		match(input,11,FOLLOW_11_in_synpred34_GCSLGrammar857); if (state.failed) return;
		pushFollow(FOLLOW_prop_in_synpred34_GCSLGrammar859);
		prop();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred34_GCSLGrammar

	// $ANTLR start synpred35_GCSLGrammar
	public final void synpred35_GCSLGrammar_fragment() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:291:5: ( rel '|' prop )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:291:5: rel '|' prop
		{
		pushFollow(FOLLOW_rel_in_synpred35_GCSLGrammar865);
		rel();
		state._fsp--;
		if (state.failed) return;
		match(input,57,FOLLOW_57_in_synpred35_GCSLGrammar867); if (state.failed) return;
		pushFollow(FOLLOW_prop_in_synpred35_GCSLGrammar869);
		prop();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred35_GCSLGrammar

	// $ANTLR start synpred36_GCSLGrammar
	public final void synpred36_GCSLGrammar_fragment() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:292:5: ( rel '=>' prop )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:292:5: rel '=>' prop
		{
		pushFollow(FOLLOW_rel_in_synpred36_GCSLGrammar875);
		rel();
		state._fsp--;
		if (state.failed) return;
		match(input,25,FOLLOW_25_in_synpred36_GCSLGrammar877); if (state.failed) return;
		pushFollow(FOLLOW_prop_in_synpred36_GCSLGrammar879);
		prop();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred36_GCSLGrammar

	// $ANTLR start synpred38_GCSLGrammar
	public final void synpred38_GCSLGrammar_fragment() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:294:5: ( '(' prop ')' )
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:294:5: '(' prop ')'
		{
		match(input,12,FOLLOW_12_in_synpred38_GCSLGrammar893); if (state.failed) return;
		pushFollow(FOLLOW_prop_in_synpred38_GCSLGrammar895);
		prop();
		state._fsp--;
		if (state.failed) return;
		match(input,13,FOLLOW_13_in_synpred38_GCSLGrammar897); if (state.failed) return;
		}

	}
	// $ANTLR end synpred38_GCSLGrammar

	// Delegated rules

	public final boolean synpred17_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred17_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred25_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred25_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred29_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred29_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred22_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred22_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred31_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred31_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred34_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred34_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred13_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred13_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred19_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred19_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred15_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred15_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred33_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred33_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred36_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred36_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred35_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred35_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred38_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred38_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred20_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred20_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred23_GCSLGrammar() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred23_GCSLGrammar_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_30_in_pattern_specification68 = new BitSet(new long[]{0x00400002C0000100L});
	public static final BitSet FOLLOW_pattern_specification_in_pattern_specification71 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_pattern_specification72 = new BitSet(new long[]{0x00400002C0000100L});
	public static final BitSet FOLLOW_pattern_specification_in_pattern_specification75 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_bltl_vars_in_pattern_specification77 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_expr_in_pattern_specification92 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_pattern_specification94 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_42_in_pattern_specification96 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_pattern_specification98 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_Variable_in_pattern_specification99 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_pattern_specification101 = new BitSet(new long[]{0x0040000280000000L});
	public static final BitSet FOLLOW_behavioral_pattern_in_pattern_specification103 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_pattern_specification104 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_expr_in_pattern_specification116 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_pattern_specification118 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_39_in_pattern_specification120 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_pattern_specification122 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_Variable_in_pattern_specification123 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_pattern_specification125 = new BitSet(new long[]{0x0040000280000000L});
	public static final BitSet FOLLOW_behavioral_pattern_in_pattern_specification127 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_pattern_specification128 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_behavioral_pattern_in_pattern_specification143 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_pattern_specification153 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_arith_proposition166 = new BitSet(new long[]{0x000540080DC01140L});
	public static final BitSet FOLLOW_22_in_arith_proposition169 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_23_in_arith_proposition173 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_24_in_arith_proposition177 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_27_in_arith_proposition181 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_26_in_arith_proposition185 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_arith_proposition191 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_behavioral_pattern226 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern230 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern232 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern236 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern238 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_56_in_behavioral_pattern240 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern244 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern254 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_behavioral_pattern288 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern292 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern294 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern298 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_behavioral_pattern300 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern302 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_behavioral_pattern304 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern308 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern317 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_behavioral_pattern346 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern350 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern352 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern356 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_behavioral_pattern358 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern362 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern364 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_behavioral_pattern366 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern370 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern379 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_behavioral_pattern408 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern412 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern414 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern418 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_37_in_behavioral_pattern420 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern422 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern424 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_behavioral_pattern426 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern430 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern439 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern471 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_behavioral_pattern473 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern477 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_behavioral_pattern479 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_43_in_behavioral_pattern481 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_33_in_behavioral_pattern495 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern499 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_behavioral_pattern513 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern517 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern519 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern523 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_behavioral_pattern525 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern541 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_behavioral_pattern543 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern547 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern549 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_behavioral_pattern551 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern553 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern569 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern571 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern575 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_behavioral_pattern577 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern581 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern590 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern621 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern623 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern627 = new BitSet(new long[]{0x0080200000000000L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern637 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_behavioral_pattern659 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern663 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern665 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern669 = new BitSet(new long[]{0x0088000000000000L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern679 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_behavioral_pattern701 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern705 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern707 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern711 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern721 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern751 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern753 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_behavioral_pattern755 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_Natural_in_behavioral_pattern756 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_behavioral_pattern757 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_behavioral_pattern759 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern761 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern763 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_behavioral_pattern765 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern767 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern771 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_behavioral_pattern786 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_behavioral_pattern788 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_34_in_behavioral_pattern790 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_behavioral_pattern792 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_Natural_in_behavioral_pattern793 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_behavioral_pattern794 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_behavioral_pattern796 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_behavioral_pattern798 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_behavioral_pattern800 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_behavioral_pattern804 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_55_in_proba_pattern828 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_Float_in_proba_pattern830 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_prop848 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_prop855 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_prop857 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_prop859 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_prop865 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_prop867 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_prop869 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_prop875 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_25_in_prop877 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_prop879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_prop885 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_prop887 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_prop893 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_prop895 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_prop897 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_53_in_prop903 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_prop909 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_31_in_ocl924 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_rel_in_ocl925 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_ocl926 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_rel933 = new BitSet(new long[]{0x000000000DC00000L});
	public static final BitSet FOLLOW_set_in_rel935 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_rel956 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Variable_in_ocl_expr968 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_expr982 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr984 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_expr986 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_46_in_expr992 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr994 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_expr996 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_50_in_expr1003 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr1005 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_expr1007 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_48_in_expr1014 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr1016 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_expr1018 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_35_in_expr1025 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr1027 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_16_in_expr1029 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_time_in_expr1031 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_expr1033 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_Natural_in_expr1039 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_Variable_in_expr1045 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_expr1046 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_Variable_in_expr1047 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_Variable_in_expr1053 = new BitSet(new long[]{0x000000000012C002L});
	public static final BitSet FOLLOW_operator_in_expr1061 = new BitSet(new long[]{0x0005400800001140L});
	public static final BitSet FOLLOW_expr_in_expr1063 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_interval1079 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_time_in_interval1087 = new BitSet(new long[]{0x0000000100022000L});
	public static final BitSet FOLLOW_17_in_interval1091 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_time_in_interval1095 = new BitSet(new long[]{0x0000000100002000L});
	public static final BitSet FOLLOW_set_in_interval1100 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_Natural_in_time1122 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_TimeUnit_in_time1125 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_28_in_bltl_vars1134 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_bltl_var_in_bltl_vars1137 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_Ident_in_bltl_var1148 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_bltl_var1149 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_bltl_var1150 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_Float_in_bltl_var1151 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_bltl_var1152 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_Float_in_bltl_var1153 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_bltl_var1154 = new BitSet(new long[]{0x0000001000000000L});
	public static final BitSet FOLLOW_36_in_bltl_var1156 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_Float_in_bltl_var1158 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_synpred13_GCSLGrammar226 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred13_GCSLGrammar230 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred13_GCSLGrammar232 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred13_GCSLGrammar236 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred13_GCSLGrammar238 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_56_in_synpred13_GCSLGrammar240 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred13_GCSLGrammar244 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred13_GCSLGrammar254 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_synpred15_GCSLGrammar288 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred15_GCSLGrammar292 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred15_GCSLGrammar294 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred15_GCSLGrammar298 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_synpred15_GCSLGrammar300 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred15_GCSLGrammar302 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_synpred15_GCSLGrammar304 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred15_GCSLGrammar308 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred15_GCSLGrammar317 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_synpred17_GCSLGrammar346 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred17_GCSLGrammar350 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred17_GCSLGrammar352 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred17_GCSLGrammar356 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_synpred17_GCSLGrammar358 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred17_GCSLGrammar362 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred17_GCSLGrammar364 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_synpred17_GCSLGrammar366 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred17_GCSLGrammar370 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred17_GCSLGrammar379 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_synpred19_GCSLGrammar408 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred19_GCSLGrammar412 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred19_GCSLGrammar414 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred19_GCSLGrammar418 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_37_in_synpred19_GCSLGrammar420 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred19_GCSLGrammar422 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred19_GCSLGrammar424 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_synpred19_GCSLGrammar426 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred19_GCSLGrammar430 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred19_GCSLGrammar439 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_synpred20_GCSLGrammar471 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_synpred20_GCSLGrammar473 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred20_GCSLGrammar477 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_synpred20_GCSLGrammar479 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_43_in_synpred20_GCSLGrammar481 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_synpred22_GCSLGrammar513 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred22_GCSLGrammar517 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred22_GCSLGrammar519 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred22_GCSLGrammar523 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_synpred22_GCSLGrammar525 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_synpred23_GCSLGrammar541 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_synpred23_GCSLGrammar543 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred23_GCSLGrammar547 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred23_GCSLGrammar549 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_synpred23_GCSLGrammar551 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred23_GCSLGrammar553 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_synpred25_GCSLGrammar569 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred25_GCSLGrammar571 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred25_GCSLGrammar575 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_synpred25_GCSLGrammar577 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred25_GCSLGrammar581 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred25_GCSLGrammar590 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_synpred29_GCSLGrammar621 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred29_GCSLGrammar623 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred29_GCSLGrammar627 = new BitSet(new long[]{0x0080200000000000L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred29_GCSLGrammar637 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_45_in_synpred29_GCSLGrammar659 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred29_GCSLGrammar663 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred29_GCSLGrammar665 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred29_GCSLGrammar669 = new BitSet(new long[]{0x0088000000000000L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred29_GCSLGrammar679 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_synpred29_GCSLGrammar701 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred29_GCSLGrammar705 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred29_GCSLGrammar707 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred29_GCSLGrammar711 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred29_GCSLGrammar721 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ocl_in_synpred31_GCSLGrammar751 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred31_GCSLGrammar753 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_synpred31_GCSLGrammar755 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_Natural_in_synpred31_GCSLGrammar756 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_synpred31_GCSLGrammar757 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_synpred31_GCSLGrammar759 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_synpred31_GCSLGrammar761 = new BitSet(new long[]{0x0000000080001000L});
	public static final BitSet FOLLOW_interval_in_synpred31_GCSLGrammar763 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_synpred31_GCSLGrammar765 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_ocl_in_synpred31_GCSLGrammar767 = new BitSet(new long[]{0x0080000000000002L});
	public static final BitSet FOLLOW_proba_pattern_in_synpred31_GCSLGrammar771 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_synpred33_GCSLGrammar848 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_synpred34_GCSLGrammar855 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_synpred34_GCSLGrammar857 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_synpred34_GCSLGrammar859 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_synpred35_GCSLGrammar865 = new BitSet(new long[]{0x0200000000000000L});
	public static final BitSet FOLLOW_57_in_synpred35_GCSLGrammar867 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_synpred35_GCSLGrammar869 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_synpred36_GCSLGrammar875 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_25_in_synpred36_GCSLGrammar877 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_synpred36_GCSLGrammar879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_synpred38_GCSLGrammar893 = new BitSet(new long[]{0x0025410800001540L});
	public static final BitSet FOLLOW_prop_in_synpred38_GCSLGrammar895 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_13_in_synpred38_GCSLGrammar897 = new BitSet(new long[]{0x0000000000000002L});
}
