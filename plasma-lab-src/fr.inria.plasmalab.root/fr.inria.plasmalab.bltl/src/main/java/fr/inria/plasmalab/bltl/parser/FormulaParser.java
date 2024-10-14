/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
// $ANTLR 3.4 Formula.g 2017-02-23 16:09:41

package fr.inria.plasmalab.bltl.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import fr.inria.plasmalab.bltl.ast.*;
import fr.inria.plasmalab.bltl.ast.nodes.*;
import fr.inria.plasmalab.bltl.ast.leaves.*;
import fr.inria.plasmalab.bltl.ast.leaves.FloatExpr;
import fr.inria.plasmalab.bltl.ast.leaves.DeadlockExpr;
import fr.inria.plasmalab.bltl.ast.operators.*;
import fr.inria.plasmalab.workflow.concrete.GenericIdentifier;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.Range;
import fr.inria.plasmalab.workflow.concrete.VariableType;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class FormulaParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD", "ALPHA", "AND", "BLTL", "COL", "COLEQ", "COMMA", "COMMENTS", "DEADLOCK", "DECLARE", "DIGITS", "DIV", "DOT", "DQ", "DUMMY", "END", "EQ", "FALSE", "FATALLY", "FLOATING", "GE", "GLOBALLY", "GT", "ID", "IDENT", "IMP", "LB", "LC", "LE", "LP", "LT", "MIN", "MUL", "NEQ", "NEXT", "NOT", "OPTIMIZE", "OR", "OVERRIDE", "RB", "RC", "REWARD", "RP", "SEMI", "SH", "TRUE", "UNTIL", "WEAK", "WHITESPACE"
    };

    public static final int EOF=-1;
    public static final int ADD=4;
    public static final int ALPHA=5;
    public static final int AND=6;
    public static final int BLTL=7;
    public static final int COL=8;
    public static final int COLEQ=9;
    public static final int COMMA=10;
    public static final int COMMENTS=11;
    public static final int DEADLOCK=12;
    public static final int DECLARE=13;
    public static final int DIGITS=14;
    public static final int DIV=15;
    public static final int DOT=16;
    public static final int DQ=17;
    public static final int DUMMY=18;
    public static final int END=19;
    public static final int EQ=20;
    public static final int FALSE=21;
    public static final int FATALLY=22;
    public static final int FLOATING=23;
    public static final int GE=24;
    public static final int GLOBALLY=25;
    public static final int GT=26;
    public static final int ID=27;
    public static final int IDENT=28;
    public static final int IMP=29;
    public static final int LB=30;
    public static final int LC=31;
    public static final int LE=32;
    public static final int LP=33;
    public static final int LT=34;
    public static final int MIN=35;
    public static final int MUL=36;
    public static final int NEQ=37;
    public static final int NEXT=38;
    public static final int NOT=39;
    public static final int OPTIMIZE=40;
    public static final int OR=41;
    public static final int OVERRIDE=42;
    public static final int RB=43;
    public static final int RC=44;
    public static final int REWARD=45;
    public static final int RP=46;
    public static final int SEMI=47;
    public static final int SH=48;
    public static final int TRUE=49;
    public static final int UNTIL=50;
    public static final int WEAK=51;
    public static final int WHITESPACE=52;

    // delegates
    public Formula_FormulaRules gFormulaRules;
    public Parser[] getDelegates() {
        return new Parser[] {gFormulaRules};
    }

    // delegators


    public FormulaParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public FormulaParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
        gFormulaRules = new Formula_FormulaRules(input, state, this);
    }

    public String[] getTokenNames() { return FormulaParser.tokenNames; }
    public String getGrammarFileName() { return "Formula.g"; }


      public static final String[] tokenNamesTranslated = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "'+'", "upper and lower case alpha characters", "'&'", "'BLTL'", 
        "':'", "':='", "','", "COMMENTS", "'declare'", "DIGITS", "'/'", "'.'", "'\"'", "DUMMY", "'end'", "'='", "'false'", 
        "'F'", "floating point number", "'>='", "'G'", "'>'", "an alpha-numerical identifier starting with a letter or '_'",
         "dot separated identifiers", "'=>'", "'['", 
        "'<='", "'('", "'<'", "'-'", "'*'", "'!='", "'X'", "'!'", "'optimize'", "'|'", 
        "']'", "')'", "';'", "'#'", "'true'", "'U'", "'W'", "WHITESPACE"
      };
      private List<PlasmaDataException> errors = new ArrayList<PlasmaDataException>();
      
      public List<PlasmaDataException> getErrors() {
            return errors;
      }
      
      @Override
         protected Object recoverFromMismatchedToken (IntStream input,int 
    ttype,BitSet follow) throws RecognitionException{
             // Desactivate recover from Mismatched input
             throw new MismatchedTokenException (ttype, input);
         }

         @Override
         public void reportError (RecognitionException e){
             String hdr = getErrorHeader(e);
             String msg = getErrorMessage(e, this.tokenNames);
             PlasmaSyntaxException pse = new PlasmaSyntaxException(hdr+" - "+msg, e);
             errors.add(pse);
             // throw pse;
         }
         
         //Override error message
      @Override
      public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        String msg = e.getMessage();
        if ( e instanceof UnwantedTokenException ) {
          UnwantedTokenException ute = (UnwantedTokenException)e;
          String tokenName;
          if ( ute.expecting== Token.EOF ) {
            tokenName = "EOF";
          }
          else {
            tokenName = tokenNamesTranslated[ute.expecting];
          }
          msg = "extraneous input "+getTokenErrorDisplay(ute.getUnexpectedToken())+
            " expecting "+tokenName;
        }
        else if ( e instanceof MissingTokenException ) {
          MissingTokenException mte = (MissingTokenException)e;
          String tokenName;
          if ( mte.expecting== Token.EOF ) {
            tokenName = "EOF";
          }
          else {
            tokenName = tokenNamesTranslated[mte.expecting];
          }
          msg = "missing "+tokenName+" at "+getTokenErrorDisplay(e.token);
        }
        else if ( e instanceof MismatchedTokenException ) {
          MismatchedTokenException mte = (MismatchedTokenException)e;
          String tokenName;
          if ( mte.expecting== Token.EOF ) {
            tokenName = "EOF";
          }
          else {
            tokenName = tokenNamesTranslated[mte.expecting];
          }
          msg = "mismatched input "+getTokenErrorDisplay(e.token)+
            " expecting "+tokenName;
        }
        else if ( e instanceof MismatchedTreeNodeException ) {
          MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
          String tokenName;
          if ( mtne.expecting==Token.EOF ) {
            tokenName = "EOF";
          }
          else {
            tokenName = tokenNamesTranslated[mtne.expecting];
          }
          msg = "mismatched tree node: "+mtne.node+
            " expecting "+tokenName;
        }
        else if ( e instanceof NoViableAltException ) {
          //NoViableAltException nvae = (NoViableAltException)e;
          // for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
          // and "(decision="+nvae.decisionNumber+") and
          // "state "+nvae.stateNumber
          System.out.println("test");
          msg = "no viable alternative at input "+getTokenErrorDisplay(e.token);
        }
        else if ( e instanceof EarlyExitException ) {
          //EarlyExitException eee = (EarlyExitException)e;
          // for development, can add "(decision="+eee.decisionNumber+")"
          msg = "required (...)+ loop did not match anything at input "+
            getTokenErrorDisplay(e.token);
        }
        else if ( e instanceof MismatchedSetException ) {
          MismatchedSetException mse = (MismatchedSetException)e;
          msg = "mismatched input "+getTokenErrorDisplay(e.token)+
            " expecting set "+mse.expecting;
        }
        else if ( e instanceof MismatchedNotSetException ) {
          MismatchedNotSetException mse = (MismatchedNotSetException)e;
          msg = "mismatched input "+getTokenErrorDisplay(e.token)+
            " expecting set "+mse.expecting;
        }
        else if ( e instanceof FailedPredicateException ) {
          FailedPredicateException fpe = (FailedPredicateException)e;
          msg = "rule "+fpe.ruleName+" failed predicate: {"+
            fpe.predicateText+"}?";
        }
        return msg;
      }



    // $ANTLR start "formula"
    // Formula.g:160:1: formula returns [Expr val] : ( ( declareVars )? ( optimizeVars )? ( overrideIds )? END )? expr EOF ;
    public final Expr formula() throws RecognitionException {
        Expr val = null;


        Expr expr1 =null;


         gFormulaRules.optimizationVariables = new ArrayList<Variable>(); 
                                             gFormulaRules.requirementVarMap = new HashMap<String,Variable>();
                                             gFormulaRules.overrideIdentifiers = new ArrayList<InterfaceIdentifier>();
        try {
            // Formula.g:163:5: ( ( ( declareVars )? ( optimizeVars )? ( overrideIds )? END )? expr EOF )
            // Formula.g:163:5: ( ( declareVars )? ( optimizeVars )? ( overrideIds )? END )? expr EOF
            {
            // Formula.g:163:5: ( ( declareVars )? ( optimizeVars )? ( overrideIds )? END )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==DECLARE||LA4_0==END||LA4_0==OPTIMIZE||LA4_0==OVERRIDE) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Formula.g:163:6: ( declareVars )? ( optimizeVars )? ( overrideIds )? END
                    {
                    // Formula.g:163:6: ( declareVars )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==DECLARE) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // Formula.g:163:6: declareVars
                            {
                            pushFollow(FOLLOW_declareVars_in_formula77);
                            declareVars();

                            state._fsp--;


                            }
                            break;

                    }


                    // Formula.g:163:19: ( optimizeVars )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==OPTIMIZE) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // Formula.g:163:19: optimizeVars
                            {
                            pushFollow(FOLLOW_optimizeVars_in_formula80);
                            optimizeVars();

                            state._fsp--;


                            }
                            break;

                    }


                    // Formula.g:163:33: ( overrideIds )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==OVERRIDE) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // Formula.g:163:33: overrideIds
                            {
                            pushFollow(FOLLOW_overrideIds_in_formula83);
                            overrideIds();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input,END,FOLLOW_END_in_formula86); 

                    }
                    break;

            }


            pushFollow(FOLLOW_expr_in_formula95);
            expr1=expr();

            state._fsp--;


             val = expr1; 

            match(input,EOF,FOLLOW_EOF_in_formula143); 

            }

        }
         
            
            catch (RecognitionException re) {
                //displayRecognitionError(re);
                reportError(re);
                //recover(input,re);
                //throw re;
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "formula"

    // Delegated rules
    public Expr term() throws RecognitionException { return gFormulaRules.term(); }

    public Expr factor() throws RecognitionException { return gFormulaRules.factor(); }

    public BoundExpr bound() throws RecognitionException { return gFormulaRules.bound(); }

    public Expr disjunction() throws RecognitionException { return gFormulaRules.disjunction(); }

    public double bvalue() throws RecognitionException { return gFormulaRules.bvalue(); }

    public BinOp top() throws RecognitionException { return gFormulaRules.top(); }

    public Range range() throws RecognitionException { return gFormulaRules.range(); }

    public int integer() throws RecognitionException { return gFormulaRules.integer(); }

    public void declareVars() throws RecognitionException { gFormulaRules.declareVars(); }

    public BinOp rop() throws RecognitionException { return gFormulaRules.rop(); }

    public Expr implication() throws RecognitionException { return gFormulaRules.implication(); }

    public void optimizeVars() throws RecognitionException { gFormulaRules.optimizeVars(); }

    public Variable variable() throws RecognitionException { return gFormulaRules.variable(); }

    public Expr relExp() throws RecognitionException { return gFormulaRules.relExp(); }

    public Expr equality() throws RecognitionException { return gFormulaRules.equality(); }

    public BinOp nop() throws RecognitionException { return gFormulaRules.nop(); }

    public Expr expr() throws RecognitionException { return gFormulaRules.expr(); }

    public BinOp eop() throws RecognitionException { return gFormulaRules.eop(); }

    public Expr numExp() throws RecognitionException { return gFormulaRules.numExp(); }

    public void overrideIds() throws RecognitionException { gFormulaRules.overrideIds(); }

    public double number() throws RecognitionException { return gFormulaRules.number(); }

    public Expr conjunction() throws RecognitionException { return gFormulaRules.conjunction(); }

    public double floating() throws RecognitionException { return gFormulaRules.floating(); }


 

    public static final BitSet FOLLOW_declareVars_in_formula77 = new BitSet(new long[]{0x0000050000080000L});
    public static final BitSet FOLLOW_optimizeVars_in_formula80 = new BitSet(new long[]{0x0000040000080000L});
    public static final BitSet FOLLOW_overrideIds_in_formula83 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_END_in_formula86 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_expr_in_formula95 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_formula143 = new BitSet(new long[]{0x0000000000000002L});

}