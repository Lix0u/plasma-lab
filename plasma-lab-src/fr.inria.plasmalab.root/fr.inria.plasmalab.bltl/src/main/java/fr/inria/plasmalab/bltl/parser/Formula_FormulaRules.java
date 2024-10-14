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
// $ANTLR 3.4 FormulaRules.g 2017-02-23 16:09:41

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
public class Formula_FormulaRules extends Parser {
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
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators
    public FormulaParser gFormula;
    public FormulaParser gParent;


    public Formula_FormulaRules(TokenStream input, FormulaParser gFormula) {
        this(input, new RecognizerSharedState(), gFormula);
    }
    public Formula_FormulaRules(TokenStream input, RecognizerSharedState state, FormulaParser gFormula) {
        super(input, state);
        this.gFormula = gFormula;
        gParent = gFormula;
    }

    public String[] getTokenNames() { return FormulaParser.tokenNames; }
    public String getGrammarFileName() { return "FormulaRules.g"; }



      Expr root;
      public Map<String, InterfaceIdentifier> modelIdMap;
      public Map<String, Variable> requirementVarMap;
      public List<Variable> optimizationVariables;
      public List<InterfaceIdentifier> overrideIdentifiers;
       
      Map<String,Variable> varMap = new HashMap<String, Variable>();

      public Map<String,Variable> getVarMap(){
        return varMap;
      }

      public Expr getRoot() {
        return root;
        }



    // $ANTLR start "expr"
    // FormulaRules.g:33:1: expr returns [Expr val] : (l= implication ( ( UNTIL | WEAK ) bound r= expr )? | ( ( FATALLY | GLOBALLY ) b1= bound | NEXT (b2= bound )? ) r= expr | REWARD LC DQ IDENT DQ RC r= expr );
    public final Expr expr() throws RecognitionException {
        Expr val = null;


        Token IDENT2=null;
        Expr l =null;

        Expr r =null;

        BoundExpr b1 =null;

        BoundExpr b2 =null;

        BoundExpr bound1 =null;


        try {
            // FormulaRules.g:34:3: (l= implication ( ( UNTIL | WEAK ) bound r= expr )? | ( ( FATALLY | GLOBALLY ) b1= bound | NEXT (b2= bound )? ) r= expr | REWARD LC DQ IDENT DQ RC r= expr )
            int alt6=3;
            switch ( input.LA(1) ) {
            case DEADLOCK:
            case DIGITS:
            case DQ:
            case FALSE:
            case FLOATING:
            case IDENT:
            case LP:
            case MIN:
            case NOT:
            case TRUE:
                {
                alt6=1;
                }
                break;
            case FATALLY:
            case GLOBALLY:
            case NEXT:
                {
                alt6=2;
                }
                break;
            case REWARD:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }

            switch (alt6) {
                case 1 :
                    // FormulaRules.g:34:3: l= implication ( ( UNTIL | WEAK ) bound r= expr )?
                    {
                    pushFollow(FOLLOW_implication_in_expr42);
                    l=implication();

                    state._fsp--;


                     val = l; 

                    // FormulaRules.g:35:3: ( ( UNTIL | WEAK ) bound r= expr )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0 >= UNTIL && LA2_0 <= WEAK)) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // FormulaRules.g:35:48: ( UNTIL | WEAK ) bound r= expr
                            {
                             BinOp binop = null;      

                            // FormulaRules.g:36:6: ( UNTIL | WEAK )
                            int alt1=2;
                            int LA1_0 = input.LA(1);

                            if ( (LA1_0==UNTIL) ) {
                                alt1=1;
                            }
                            else if ( (LA1_0==WEAK) ) {
                                alt1=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 0, input);

                                throw nvae;

                            }
                            switch (alt1) {
                                case 1 :
                                    // FormulaRules.g:36:8: UNTIL
                                    {
                                    match(input,UNTIL,FOLLOW_UNTIL_in_expr133); 

                                     binop = BinOp.Until;     

                                    }
                                    break;
                                case 2 :
                                    // FormulaRules.g:37:8: WEAK
                                    {
                                    match(input,WEAK,FOLLOW_WEAK_in_expr178); 

                                     binop = BinOp.WeakUntil; 

                                    }
                                    break;

                            }


                            pushFollow(FOLLOW_bound_in_expr231);
                            bound1=bound();

                            state._fsp--;


                            pushFollow(FOLLOW_expr_in_expr235);
                            r=expr();

                            state._fsp--;


                              if (!bound1.isTime)
                                                                                val = new StepBoundExpr2(l, binop, bound1.expr, r);
                                                                              else
                                                                                val = new TimeBoundExpr2(l, binop, bound1.expr, r);      

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // FormulaRules.g:43:48: ( ( FATALLY | GLOBALLY ) b1= bound | NEXT (b2= bound )? ) r= expr
                    {
                     UnOp unop = null;  BoundExpr b = null; 

                    // FormulaRules.g:44:3: ( ( FATALLY | GLOBALLY ) b1= bound | NEXT (b2= bound )? )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==FATALLY||LA5_0==GLOBALLY) ) {
                        alt5=1;
                    }
                    else if ( (LA5_0==NEXT) ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;

                    }
                    switch (alt5) {
                        case 1 :
                            // FormulaRules.g:44:5: ( FATALLY | GLOBALLY ) b1= bound
                            {
                            // FormulaRules.g:44:5: ( FATALLY | GLOBALLY )
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( (LA3_0==FATALLY) ) {
                                alt3=1;
                            }
                            else if ( (LA3_0==GLOBALLY) ) {
                                alt3=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 0, input);

                                throw nvae;

                            }
                            switch (alt3) {
                                case 1 :
                                    // FormulaRules.g:44:7: FATALLY
                                    {
                                    match(input,FATALLY,FOLLOW_FATALLY_in_expr375); 

                                     unop = UnOp.Future;   

                                    }
                                    break;
                                case 2 :
                                    // FormulaRules.g:45:7: GLOBALLY
                                    {
                                    match(input,GLOBALLY,FOLLOW_GLOBALLY_in_expr418); 

                                     unop = UnOp.Globally; 

                                    }
                                    break;

                            }


                            pushFollow(FOLLOW_bound_in_expr473);
                            b1=bound();

                            state._fsp--;


                             b = b1; 

                            }
                            break;
                        case 2 :
                            // FormulaRules.g:48:7: NEXT (b2= bound )?
                            {
                            match(input,NEXT,FOLLOW_NEXT_in_expr511); 

                             unop = UnOp.Next; 
                                                                             b = new BoundExpr();
                                                                             b.isTime = false;
                                                                             b.expr = new IntExpr(1); 

                            // FormulaRules.g:52:12: (b2= bound )?
                            int alt4=2;
                            int LA4_0 = input.LA(1);

                            if ( (LA4_0==LE) ) {
                                alt4=1;
                            }
                            switch (alt4) {
                                case 1 :
                                    // FormulaRules.g:52:13: b2= bound
                                    {
                                    pushFollow(FOLLOW_bound_in_expr567);
                                    b2=bound();

                                    state._fsp--;


                                     b = b2; 

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    pushFollow(FOLLOW_expr_in_expr627);
                    r=expr();

                    state._fsp--;


                      if (!b.isTime)
                                                                        val = new StepBoundExpr1(unop, b.expr, r);
                                                                      else                    
                                                                        val = new TimeBoundExpr1(unop, b.expr, r);    

                    }
                    break;
                case 3 :
                    // FormulaRules.g:57:3: REWARD LC DQ IDENT DQ RC r= expr
                    {
                    match(input,REWARD,FOLLOW_REWARD_in_expr645); 

                    match(input,LC,FOLLOW_LC_in_expr647); 

                    match(input,DQ,FOLLOW_DQ_in_expr649); 

                    IDENT2=(Token)match(input,IDENT,FOLLOW_IDENT_in_expr651); 

                    match(input,DQ,FOLLOW_DQ_in_expr653); 

                    match(input,RC,FOLLOW_RC_in_expr655); 

                    pushFollow(FOLLOW_expr_in_expr659);
                    r=expr();

                    state._fsp--;


                     String vname = "\"" + (IDENT2!=null?IDENT2.getText():null) + "\""; 
                                                                     varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));
                                                                     val = new RewardExpr(vname,r); 
                                                                   

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "expr"



    // $ANTLR start "bound"
    // FormulaRules.g:64:1: bound returns [BoundExpr val] : LE ( SH (i= number | LP sExp= numExp RP ) | (f= number | LP tExp= numExp RP ) ) ;
    public final BoundExpr bound() throws RecognitionException {
        BoundExpr val = null;


        double i =0.0;

        Expr sExp =null;

        double f =0.0;

        Expr tExp =null;


         val = new BoundExpr(); 
        try {
            // FormulaRules.g:65:3: ( LE ( SH (i= number | LP sExp= numExp RP ) | (f= number | LP tExp= numExp RP ) ) )
            // FormulaRules.g:65:3: LE ( SH (i= number | LP sExp= numExp RP ) | (f= number | LP tExp= numExp RP ) )
            {
            match(input,LE,FOLLOW_LE_in_bound707); 

            // FormulaRules.g:65:6: ( SH (i= number | LP sExp= numExp RP ) | (f= number | LP tExp= numExp RP ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==SH) ) {
                alt9=1;
            }
            else if ( (LA9_0==DIGITS||LA9_0==FLOATING||LA9_0==LP) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // FormulaRules.g:65:7: SH (i= number | LP sExp= numExp RP )
                    {
                    match(input,SH,FOLLOW_SH_in_bound710); 

                    val.isTime = false;

                    // FormulaRules.g:66:9: (i= number | LP sExp= numExp RP )
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==DIGITS||LA7_0==FLOATING) ) {
                        alt7=1;
                    }
                    else if ( (LA7_0==LP) ) {
                        alt7=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 0, input);

                        throw nvae;

                    }
                    switch (alt7) {
                        case 1 :
                            // FormulaRules.g:66:11: i= number
                            {
                            pushFollow(FOLLOW_number_in_bound762);
                            i=number();

                            state._fsp--;


                             val.expr = new IntExpr((int)i);  

                            }
                            break;
                        case 2 :
                            // FormulaRules.g:67:11: LP sExp= numExp RP
                            {
                            match(input,LP,FOLLOW_LP_in_bound803); 

                            pushFollow(FOLLOW_numExp_in_bound807);
                            sExp=numExp();

                            state._fsp--;


                            match(input,RP,FOLLOW_RP_in_bound809); 

                             val.expr = sExp;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // FormulaRules.g:69:45: (f= number | LP tExp= numExp RP )
                    {
                    val.isTime = true;

                    // FormulaRules.g:70:9: (f= number | LP tExp= numExp RP )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==DIGITS||LA8_0==FLOATING) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==LP) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;

                    }
                    switch (alt8) {
                        case 1 :
                            // FormulaRules.g:70:11: f= number
                            {
                            pushFollow(FOLLOW_number_in_bound898);
                            f=number();

                            state._fsp--;


                             val.expr = new FloatExpr(f); 

                            }
                            break;
                        case 2 :
                            // FormulaRules.g:71:11: LP tExp= numExp RP
                            {
                            match(input,LP,FOLLOW_LP_in_bound939); 

                            pushFollow(FOLLOW_numExp_in_bound943);
                            tExp=numExp();

                            state._fsp--;


                            match(input,RP,FOLLOW_RP_in_bound945); 

                             val.expr = tExp;

                            }
                            break;

                    }


                    }
                    break;

            }


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "bound"



    // $ANTLR start "implication"
    // FormulaRules.g:76:1: implication returns [Expr val] : l= disjunction ( IMP r= disjunction )* ;
    public final Expr implication() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;


        try {
            // FormulaRules.g:77:3: (l= disjunction ( IMP r= disjunction )* )
            // FormulaRules.g:77:3: l= disjunction ( IMP r= disjunction )*
            {
            pushFollow(FOLLOW_disjunction_in_implication995);
            l=disjunction();

            state._fsp--;


             val = l; 

            // FormulaRules.g:78:17: ( IMP r= disjunction )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==IMP) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // FormulaRules.g:78:18: IMP r= disjunction
            	    {
            	    match(input,IMP,FOLLOW_IMP_in_implication1042); 

            	    pushFollow(FOLLOW_disjunction_in_implication1046);
            	    r=disjunction();

            	    state._fsp--;


            	     val = new Expr2(val, BinOp.Imp, r); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "implication"



    // $ANTLR start "disjunction"
    // FormulaRules.g:82:1: disjunction returns [Expr val] : l= conjunction ( OR r= conjunction )* ;
    public final Expr disjunction() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;


        try {
            // FormulaRules.g:83:3: (l= conjunction ( OR r= conjunction )* )
            // FormulaRules.g:83:3: l= conjunction ( OR r= conjunction )*
            {
            pushFollow(FOLLOW_conjunction_in_disjunction1108);
            l=conjunction();

            state._fsp--;


             val = l; 

            // FormulaRules.g:84:17: ( OR r= conjunction )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==OR) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // FormulaRules.g:84:18: OR r= conjunction
            	    {
            	    match(input,OR,FOLLOW_OR_in_disjunction1156); 

            	    pushFollow(FOLLOW_conjunction_in_disjunction1160);
            	    r=conjunction();

            	    state._fsp--;


            	     val = new Expr2(val, BinOp.Or, r); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "disjunction"



    // $ANTLR start "conjunction"
    // FormulaRules.g:88:1: conjunction returns [Expr val] : l= equality ( AND r= equality )* ;
    public final Expr conjunction() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;


        try {
            // FormulaRules.g:88:31: (l= equality ( AND r= equality )* )
            // FormulaRules.g:89:3: l= equality ( AND r= equality )*
            {
            pushFollow(FOLLOW_equality_in_conjunction1225);
            l=equality();

            state._fsp--;


             val = l; 

            // FormulaRules.g:90:14: ( AND r= equality )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==AND) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // FormulaRules.g:90:16: AND r= equality
            	    {
            	    match(input,AND,FOLLOW_AND_in_conjunction1273); 

            	    pushFollow(FOLLOW_equality_in_conjunction1277);
            	    r=equality();

            	    state._fsp--;


            	     val = new Expr2(val, BinOp.And, r); 

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "conjunction"



    // $ANTLR start "equality"
    // FormulaRules.g:94:1: equality returns [Expr val] : ( NOT )? l= relExp ( eop r= relExp )? ;
    public final Expr equality() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;

        BinOp eop3 =null;


         UnOp neg = null; 
        try {
            // FormulaRules.g:95:3: ( ( NOT )? l= relExp ( eop r= relExp )? )
            // FormulaRules.g:95:3: ( NOT )? l= relExp ( eop r= relExp )?
            {
            // FormulaRules.g:95:3: ( NOT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==NOT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // FormulaRules.g:95:4: NOT
                    {
                    match(input,NOT,FOLLOW_NOT_in_equality1352); 

                     neg = UnOp.Not ; 

                    }
                    break;

            }


            pushFollow(FOLLOW_relExp_in_equality1402);
            l=relExp();

            state._fsp--;


             val = l; 

            // FormulaRules.g:97:19: ( eop r= relExp )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==EQ||LA14_0==NEQ) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // FormulaRules.g:97:20: eop r= relExp
                    {
                    pushFollow(FOLLOW_eop_in_equality1449);
                    eop3=eop();

                    state._fsp--;


                    pushFollow(FOLLOW_relExp_in_equality1453);
                    r=relExp();

                    state._fsp--;


                     val = new Expr2(l, eop3, r); 

                    }
                    break;

            }


             val = neg == null ? val : new Expr1(neg, val); 

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "equality"



    // $ANTLR start "eop"
    // FormulaRules.g:101:1: eop returns [BinOp val] : ( EQ | NEQ );
    public final BinOp eop() throws RecognitionException {
        BinOp val = null;


        try {
            // FormulaRules.g:102:3: ( EQ | NEQ )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==EQ) ) {
                alt15=1;
            }
            else if ( (LA15_0==NEQ) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;

            }
            switch (alt15) {
                case 1 :
                    // FormulaRules.g:102:3: EQ
                    {
                    match(input,EQ,FOLLOW_EQ_in_eop1522); 

                     val = BinOp.Eq; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:103:3: NEQ
                    {
                    match(input,NEQ,FOLLOW_NEQ_in_eop1565); 

                     val = BinOp.Neq; 

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "eop"



    // $ANTLR start "relExp"
    // FormulaRules.g:106:1: relExp returns [Expr val] : l= numExp ( rop r= numExp )? ;
    public final Expr relExp() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;

        BinOp rop4 =null;


        try {
            // FormulaRules.g:107:3: (l= numExp ( rop r= numExp )? )
            // FormulaRules.g:107:3: l= numExp ( rop r= numExp )?
            {
            pushFollow(FOLLOW_numExp_in_relExp1618);
            l=numExp();

            state._fsp--;


             val = l; 

            // FormulaRules.g:108:12: ( rop r= numExp )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==GE||LA16_0==GT||LA16_0==LE||LA16_0==LT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // FormulaRules.g:108:13: rop r= numExp
                    {
                    pushFollow(FOLLOW_rop_in_relExp1667);
                    rop4=rop();

                    state._fsp--;


                    pushFollow(FOLLOW_numExp_in_relExp1671);
                    r=numExp();

                    state._fsp--;


                     val = new Expr2(val, rop4, r); 

                    }
                    break;

            }


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "relExp"



    // $ANTLR start "rop"
    // FormulaRules.g:112:1: rop returns [BinOp val] : ( GT | LT | GE | LE );
    public final BinOp rop() throws RecognitionException {
        BinOp val = null;


        try {
            // FormulaRules.g:113:3: ( GT | LT | GE | LE )
            int alt17=4;
            switch ( input.LA(1) ) {
            case GT:
                {
                alt17=1;
                }
                break;
            case LT:
                {
                alt17=2;
                }
                break;
            case GE:
                {
                alt17=3;
                }
                break;
            case LE:
                {
                alt17=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // FormulaRules.g:113:3: GT
                    {
                    match(input,GT,FOLLOW_GT_in_rop1730); 

                     val = BinOp.Gt; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:114:3: LT
                    {
                    match(input,LT,FOLLOW_LT_in_rop1773); 

                     val = BinOp.Lt; 

                    }
                    break;
                case 3 :
                    // FormulaRules.g:115:3: GE
                    {
                    match(input,GE,FOLLOW_GE_in_rop1816); 

                     val = BinOp.Ge; 

                    }
                    break;
                case 4 :
                    // FormulaRules.g:116:3: LE
                    {
                    match(input,LE,FOLLOW_LE_in_rop1859); 

                     val = BinOp.Le; 

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "rop"



    // $ANTLR start "numExp"
    // FormulaRules.g:120:1: numExp returns [Expr val] : l= term ( nop r= term )* ;
    public final Expr numExp() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;

        BinOp nop5 =null;


        try {
            // FormulaRules.g:120:26: (l= term ( nop r= term )* )
            // FormulaRules.g:121:3: l= term ( nop r= term )*
            {
            pushFollow(FOLLOW_term_in_numExp1915);
            l=term();

            state._fsp--;


             val = l; 

            // FormulaRules.g:122:10: ( nop r= term )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==ADD||LA18_0==MIN) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // FormulaRules.g:122:11: nop r= term
            	    {
            	    pushFollow(FOLLOW_nop_in_numExp1966);
            	    nop5=nop();

            	    state._fsp--;


            	    pushFollow(FOLLOW_term_in_numExp1970);
            	    r=term();

            	    state._fsp--;


            	     val = new Expr2(val, nop5, r); 

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "numExp"



    // $ANTLR start "nop"
    // FormulaRules.g:126:1: nop returns [BinOp val] : ( ADD | MIN );
    public final BinOp nop() throws RecognitionException {
        BinOp val = null;


        try {
            // FormulaRules.g:127:3: ( ADD | MIN )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ADD) ) {
                alt19=1;
            }
            else if ( (LA19_0==MIN) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }
            switch (alt19) {
                case 1 :
                    // FormulaRules.g:127:3: ADD
                    {
                    match(input,ADD,FOLLOW_ADD_in_nop2029); 

                     val = BinOp.Add;  

                    }
                    break;
                case 2 :
                    // FormulaRules.g:128:3: MIN
                    {
                    match(input,MIN,FOLLOW_MIN_in_nop2071); 

                     val = BinOp.Min;  

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "nop"



    // $ANTLR start "term"
    // FormulaRules.g:132:1: term returns [Expr val] : l= factor ( top r= factor )* ;
    public final Expr term() throws RecognitionException {
        Expr val = null;


        Expr l =null;

        Expr r =null;

        BinOp top6 =null;


        try {
            // FormulaRules.g:133:3: (l= factor ( top r= factor )* )
            // FormulaRules.g:133:3: l= factor ( top r= factor )*
            {
            pushFollow(FOLLOW_factor_in_term2126);
            l=factor();

            state._fsp--;


             val = l; 

            // FormulaRules.g:134:12: ( top r= factor )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==DIV||LA20_0==MUL) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // FormulaRules.g:134:13: top r= factor
            	    {
            	    pushFollow(FOLLOW_top_in_term2173);
            	    top6=top();

            	    state._fsp--;


            	    pushFollow(FOLLOW_factor_in_term2177);
            	    r=factor();

            	    state._fsp--;


            	     val = new Expr2(val, top6, r); 

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "term"



    // $ANTLR start "top"
    // FormulaRules.g:138:1: top returns [BinOp val] : ( MUL | DIV );
    public final BinOp top() throws RecognitionException {
        BinOp val = null;


        try {
            // FormulaRules.g:139:3: ( MUL | DIV )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==MUL) ) {
                alt21=1;
            }
            else if ( (LA21_0==DIV) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }
            switch (alt21) {
                case 1 :
                    // FormulaRules.g:139:3: MUL
                    {
                    match(input,MUL,FOLLOW_MUL_in_top2236); 

                     val = BinOp.Mul; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:140:3: DIV
                    {
                    match(input,DIV,FOLLOW_DIV_in_top2258); 

                     val = BinOp.Div; 

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "top"



    // $ANTLR start "factor"
    // FormulaRules.g:144:1: factor returns [Expr val] : ( MIN )? ( LP expr RP |id1= IDENT | DQ id2= IDENT DQ | number | TRUE | FALSE | DEADLOCK ) ;
    public final Expr factor() throws RecognitionException {
        Expr val = null;


        Token id1=null;
        Token id2=null;
        Expr expr7 =null;

        double number8 =0.0;


         UnOp neg = null; 
        try {
            // FormulaRules.g:145:3: ( ( MIN )? ( LP expr RP |id1= IDENT | DQ id2= IDENT DQ | number | TRUE | FALSE | DEADLOCK ) )
            // FormulaRules.g:145:3: ( MIN )? ( LP expr RP |id1= IDENT | DQ id2= IDENT DQ | number | TRUE | FALSE | DEADLOCK )
            {
            // FormulaRules.g:145:3: ( MIN )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==MIN) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // FormulaRules.g:145:4: MIN
                    {
                    match(input,MIN,FOLLOW_MIN_in_factor2300); 

                     neg = UnOp.Neg; 

                    }
                    break;

            }


            // FormulaRules.g:146:10: ( LP expr RP |id1= IDENT | DQ id2= IDENT DQ | number | TRUE | FALSE | DEADLOCK )
            int alt23=7;
            switch ( input.LA(1) ) {
            case LP:
                {
                alt23=1;
                }
                break;
            case IDENT:
                {
                alt23=2;
                }
                break;
            case DQ:
                {
                alt23=3;
                }
                break;
            case DIGITS:
            case FLOATING:
                {
                alt23=4;
                }
                break;
            case TRUE:
                {
                alt23=5;
                }
                break;
            case FALSE:
                {
                alt23=6;
                }
                break;
            case DEADLOCK:
                {
                alt23=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }

            switch (alt23) {
                case 1 :
                    // FormulaRules.g:146:12: LP expr RP
                    {
                    match(input,LP,FOLLOW_LP_in_factor2344); 

                    pushFollow(FOLLOW_expr_in_factor2346);
                    expr7=expr();

                    state._fsp--;


                    match(input,RP,FOLLOW_RP_in_factor2348); 

                     val = expr7; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:147:12: id1= IDENT
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_factor2379); 

                     String vname = (id1!=null?id1.getText():null);
                                                          InterfaceIdentifier iId = modelIdMap.get(vname);
                                                          if(iId==null)
                                                            iId = requirementVarMap.get(vname);
                                                          else
                                                            varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));
                                                          val = new IdentExpr(iId, vname);

                    }
                    break;
                case 3 :
                    // FormulaRules.g:154:12: DQ id2= IDENT DQ
                    {
                    match(input,DQ,FOLLOW_DQ_in_factor2409); 

                    id2=(Token)match(input,IDENT,FOLLOW_IDENT_in_factor2413); 

                    match(input,DQ,FOLLOW_DQ_in_factor2415); 

                     String vname = "\"" + (id2!=null?id2.getText():null) + "\""; 
                                                          val = new IdentExpr(modelIdMap.get(vname), vname);
                                                          varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));

                    }
                    break;
                case 4 :
                    // FormulaRules.g:157:12: number
                    {
                    pushFollow(FOLLOW_number_in_factor2439);
                    number8=number();

                    state._fsp--;


                     val = new FloatExpr(number8); 

                    }
                    break;
                case 5 :
                    // FormulaRules.g:158:12: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_factor2472); 

                     val = new BoolExpr(true);   

                    }
                    break;
                case 6 :
                    // FormulaRules.g:159:12: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_factor2507); 

                     val = new BoolExpr(false);  

                    }
                    break;
                case 7 :
                    // FormulaRules.g:160:12: DEADLOCK
                    {
                    match(input,DEADLOCK,FOLLOW_DEADLOCK_in_factor2541); 

                     val = new DeadlockExpr();   

                    }
                    break;

            }


             val = neg == null ? val : new Expr1(neg, val);  

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "factor"



    // $ANTLR start "declareVars"
    // FormulaRules.g:164:1: declareVars : DECLARE var1= variable ( SEMI var2= variable )* ;
    public final void declareVars() throws RecognitionException {
        Variable var1 =null;

        Variable var2 =null;


        try {
            // FormulaRules.g:165:3: ( DECLARE var1= variable ( SEMI var2= variable )* )
            // FormulaRules.g:165:3: DECLARE var1= variable ( SEMI var2= variable )*
            {
            match(input,DECLARE,FOLLOW_DECLARE_in_declareVars2606); 

            pushFollow(FOLLOW_variable_in_declareVars2610);
            var1=variable();

            state._fsp--;


            requirementVarMap.put(var1.getName(),var1);

            // FormulaRules.g:166:11: ( SEMI var2= variable )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==SEMI) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // FormulaRules.g:166:12: SEMI var2= variable
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_declareVars2625); 

            	    pushFollow(FOLLOW_variable_in_declareVars2629);
            	    var2=variable();

            	    state._fsp--;


            	    requirementVarMap.put(var2.getName(),var2);

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "declareVars"



    // $ANTLR start "optimizeVars"
    // FormulaRules.g:170:1: optimizeVars : OPTIMIZE var1= variable ( SEMI var2= variable )* ;
    public final void optimizeVars() throws RecognitionException {
        Variable var1 =null;

        Variable var2 =null;


        try {
            // FormulaRules.g:171:3: ( OPTIMIZE var1= variable ( SEMI var2= variable )* )
            // FormulaRules.g:171:3: OPTIMIZE var1= variable ( SEMI var2= variable )*
            {
            match(input,OPTIMIZE,FOLLOW_OPTIMIZE_in_optimizeVars2643); 

            pushFollow(FOLLOW_variable_in_optimizeVars2647);
            var1=variable();

            state._fsp--;


            optimizationVariables.add(var1);

            // FormulaRules.g:172:12: ( SEMI var2= variable )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==SEMI) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // FormulaRules.g:172:13: SEMI var2= variable
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_optimizeVars2663); 

            	    pushFollow(FOLLOW_variable_in_optimizeVars2667);
            	    var2=variable();

            	    state._fsp--;


            	    optimizationVariables.add(var2);

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "optimizeVars"



    // $ANTLR start "overrideIds"
    // FormulaRules.g:175:1: overrideIds : OVERRIDE id1= IDENT ( SEMI id2= IDENT )* ;
    public final void overrideIds() throws RecognitionException {
        Token id1=null;
        Token id2=null;

        try {
            // FormulaRules.g:176:3: ( OVERRIDE id1= IDENT ( SEMI id2= IDENT )* )
            // FormulaRules.g:176:3: OVERRIDE id1= IDENT ( SEMI id2= IDENT )*
            {
            match(input,OVERRIDE,FOLLOW_OVERRIDE_in_overrideIds2679); 

            id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_overrideIds2683); 

            overrideIdentifiers.add(new GenericIdentifier((id1!=null?id1.getText():null)));

            // FormulaRules.g:177:12: ( SEMI id2= IDENT )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==SEMI) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // FormulaRules.g:177:13: SEMI id2= IDENT
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_overrideIds2698); 

            	    id2=(Token)match(input,IDENT,FOLLOW_IDENT_in_overrideIds2702); 

            	    overrideIdentifiers.add(new GenericIdentifier((id2!=null?id2.getText():null)));

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "overrideIds"



    // $ANTLR start "variable"
    // FormulaRules.g:180:1: variable returns [Variable var] : id= IDENT COLEQ ( number |r= range | bvalue ) ;
    public final Variable variable() throws RecognitionException {
        Variable var = null;


        Token id=null;
        Range r =null;

        double number9 =0.0;

        double bvalue10 =0.0;


         double x = 0, y = 0, z = 1; 
         VariableType type = VariableType.DOUBLE; 
        try {
            // FormulaRules.g:183:3: (id= IDENT COLEQ ( number |r= range | bvalue ) )
            // FormulaRules.g:183:3: id= IDENT COLEQ ( number |r= range | bvalue )
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_variable2767); 

            match(input,COLEQ,FOLLOW_COLEQ_in_variable2769); 

            // FormulaRules.g:184:5: ( number |r= range | bvalue )
            int alt27=3;
            switch ( input.LA(1) ) {
            case DIGITS:
            case FLOATING:
                {
                alt27=1;
                }
                break;
            case LB:
                {
                alt27=2;
                }
                break;
            case FALSE:
            case TRUE:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }

            switch (alt27) {
                case 1 :
                    // FormulaRules.g:184:6: number
                    {
                    pushFollow(FOLLOW_number_in_variable2777);
                    number9=number();

                    state._fsp--;


                    x=y=number9;

                    }
                    break;
                case 2 :
                    // FormulaRules.g:185:7: r= range
                    {
                    pushFollow(FOLLOW_range_in_variable2791);
                    r=range();

                    state._fsp--;


                    x=r.lower; y=r.upper; z=r.step; type=r.type;

                    }
                    break;
                case 3 :
                    // FormulaRules.g:186:7: bvalue
                    {
                    pushFollow(FOLLOW_bvalue_in_variable2801);
                    bvalue10=bvalue();

                    state._fsp--;


                    x=y=bvalue10; type = VariableType.BOOL;

                    }
                    break;

            }


             var = new Variable((id!=null?id.getText():null), x, y, z, type);

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return var;
    }
    // $ANTLR end "variable"



    // $ANTLR start "range"
    // FormulaRules.g:192:1: range returns [Range range] : LB (x1= number SEMI y1= number ( SEMI z1= number )? |b1= bvalue ( SEMI b2= bvalue )? ) RB ;
    public final Range range() throws RecognitionException {
        Range range = null;


        double x1 =0.0;

        double y1 =0.0;

        double z1 =0.0;

        double b1 =0.0;

        double b2 =0.0;


        double x=0, y=0, z=1;
              VariableType type = VariableType.DOUBLE;
        try {
            // FormulaRules.g:195:3: ( LB (x1= number SEMI y1= number ( SEMI z1= number )? |b1= bvalue ( SEMI b2= bvalue )? ) RB )
            // FormulaRules.g:195:3: LB (x1= number SEMI y1= number ( SEMI z1= number )? |b1= bvalue ( SEMI b2= bvalue )? ) RB
            {
            match(input,LB,FOLLOW_LB_in_range2830); 

            // FormulaRules.g:195:5: (x1= number SEMI y1= number ( SEMI z1= number )? |b1= bvalue ( SEMI b2= bvalue )? )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==DIGITS||LA30_0==FLOATING) ) {
                alt30=1;
            }
            else if ( (LA30_0==FALSE||LA30_0==TRUE) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }
            switch (alt30) {
                case 1 :
                    // FormulaRules.g:196:5: x1= number SEMI y1= number ( SEMI z1= number )?
                    {
                    pushFollow(FOLLOW_number_in_range2839);
                    x1=number();

                    state._fsp--;


                    match(input,SEMI,FOLLOW_SEMI_in_range2841); 

                    pushFollow(FOLLOW_number_in_range2845);
                    y1=number();

                    state._fsp--;


                    x=x1; y=y1;

                    // FormulaRules.g:198:5: ( SEMI z1= number )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==SEMI) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // FormulaRules.g:198:6: SEMI z1= number
                            {
                            match(input,SEMI,FOLLOW_SEMI_in_range2863); 

                            pushFollow(FOLLOW_number_in_range2867);
                            z1=number();

                            state._fsp--;


                            z=z1; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // FormulaRules.g:201:5: b1= bvalue ( SEMI b2= bvalue )?
                    {
                    pushFollow(FOLLOW_bvalue_in_range2892);
                    b1=bvalue();

                    state._fsp--;


                    x=b1; y=b1;

                    // FormulaRules.g:203:5: ( SEMI b2= bvalue )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==SEMI) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // FormulaRules.g:203:6: SEMI b2= bvalue
                            {
                            match(input,SEMI,FOLLOW_SEMI_in_range2909); 

                            pushFollow(FOLLOW_bvalue_in_range2913);
                            b2=bvalue();

                            state._fsp--;


                            y=b2;

                            }
                            break;

                    }


                    type = VariableType.BOOL;

                    }
                    break;

            }


            match(input,RB,FOLLOW_RB_in_range2945); 

            range = new Range(x,y,z,type);

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return range;
    }
    // $ANTLR end "range"



    // $ANTLR start "bvalue"
    // FormulaRules.g:211:1: bvalue returns [double val] : ( TRUE | FALSE );
    public final double bvalue() throws RecognitionException {
        double val = 0.0;


        try {
            // FormulaRules.g:212:3: ( TRUE | FALSE )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==TRUE) ) {
                alt31=1;
            }
            else if ( (LA31_0==FALSE) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }
            switch (alt31) {
                case 1 :
                    // FormulaRules.g:212:3: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_bvalue2962); 

                     val = 1; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:213:3: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_bvalue2977); 

                     val = 0; 

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "bvalue"



    // $ANTLR start "number"
    // FormulaRules.g:216:1: number returns [double val] : ( floating | integer );
    public final double number() throws RecognitionException {
        double val = 0.0;


        double floating11 =0.0;

        int integer12 =0;


        try {
            // FormulaRules.g:217:3: ( floating | integer )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==FLOATING) ) {
                alt32=1;
            }
            else if ( (LA32_0==DIGITS) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;

            }
            switch (alt32) {
                case 1 :
                    // FormulaRules.g:217:3: floating
                    {
                    pushFollow(FOLLOW_floating_in_number3004);
                    floating11=floating();

                    state._fsp--;


                     val = floating11; 

                    }
                    break;
                case 2 :
                    // FormulaRules.g:218:3: integer
                    {
                    pushFollow(FOLLOW_integer_in_number3012);
                    integer12=integer();

                    state._fsp--;


                     val = integer12; 

                    }
                    break;

            }
        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "number"



    // $ANTLR start "floating"
    // FormulaRules.g:222:1: floating returns [double val] : FLOATING ;
    public final double floating() throws RecognitionException {
        double val = 0.0;


        Token FLOATING13=null;

        try {
            // FormulaRules.g:223:3: ( FLOATING )
            // FormulaRules.g:223:3: FLOATING
            {
            FLOATING13=(Token)match(input,FLOATING,FOLLOW_FLOATING_in_floating3033); 

             try{val = Double.parseDouble((FLOATING13!=null?FLOATING13.getText():null));}catch(NumberFormatException e){val =0;} 

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "floating"



    // $ANTLR start "integer"
    // FormulaRules.g:226:1: integer returns [int val] : DIGITS ;
    public final int integer() throws RecognitionException {
        int val = 0;


        Token DIGITS14=null;

        try {
            // FormulaRules.g:227:3: ( DIGITS )
            // FormulaRules.g:227:3: DIGITS
            {
            DIGITS14=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_integer3050); 

             try { val = Integer.parseInt((DIGITS14!=null?DIGITS14.getText():null)); }
                           catch (NumberFormatException e) { val =0; }  

            }

        }

            catch (RecognitionException re) {
              reportError(re);
              //keepError(re);
              recover(input,re);
              throw(re);
            }

        finally {
        	// do for sure before leaving
        }
        return val;
    }
    // $ANTLR end "integer"

    // Delegated rules


 

    public static final BitSet FOLLOW_implication_in_expr42 = new BitSet(new long[]{0x000C000000000002L});
    public static final BitSet FOLLOW_UNTIL_in_expr133 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_WEAK_in_expr178 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_bound_in_expr231 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_expr_in_expr235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FATALLY_in_expr375 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_GLOBALLY_in_expr418 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_bound_in_expr473 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_NEXT_in_expr511 = new BitSet(new long[]{0x000220CB12E25000L});
    public static final BitSet FOLLOW_bound_in_expr567 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_expr_in_expr627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REWARD_in_expr645 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LC_in_expr647 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_DQ_in_expr649 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_IDENT_in_expr651 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_DQ_in_expr653 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_RC_in_expr655 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_expr_in_expr659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LE_in_bound707 = new BitSet(new long[]{0x0001000200804000L});
    public static final BitSet FOLLOW_SH_in_bound710 = new BitSet(new long[]{0x0000000200804000L});
    public static final BitSet FOLLOW_number_in_bound762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_bound803 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_numExp_in_bound807 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RP_in_bound809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_bound898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_bound939 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_numExp_in_bound943 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RP_in_bound945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_disjunction_in_implication995 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_IMP_in_implication1042 = new BitSet(new long[]{0x0002008A10A25000L});
    public static final BitSet FOLLOW_disjunction_in_implication1046 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_conjunction_in_disjunction1108 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_OR_in_disjunction1156 = new BitSet(new long[]{0x0002008A10A25000L});
    public static final BitSet FOLLOW_conjunction_in_disjunction1160 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_equality_in_conjunction1225 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_AND_in_conjunction1273 = new BitSet(new long[]{0x0002008A10A25000L});
    public static final BitSet FOLLOW_equality_in_conjunction1277 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_NOT_in_equality1352 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_relExp_in_equality1402 = new BitSet(new long[]{0x0000002000100002L});
    public static final BitSet FOLLOW_eop_in_equality1449 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_relExp_in_equality1453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_eop1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEQ_in_eop1565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numExp_in_relExp1618 = new BitSet(new long[]{0x0000000505000002L});
    public static final BitSet FOLLOW_rop_in_relExp1667 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_numExp_in_relExp1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GT_in_rop1730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LT_in_rop1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GE_in_rop1816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LE_in_rop1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_numExp1915 = new BitSet(new long[]{0x0000000800000012L});
    public static final BitSet FOLLOW_nop_in_numExp1966 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_term_in_numExp1970 = new BitSet(new long[]{0x0000000800000012L});
    public static final BitSet FOLLOW_ADD_in_nop2029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_nop2071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_term2126 = new BitSet(new long[]{0x0000001000008002L});
    public static final BitSet FOLLOW_top_in_term2173 = new BitSet(new long[]{0x0002000A10A25000L});
    public static final BitSet FOLLOW_factor_in_term2177 = new BitSet(new long[]{0x0000001000008002L});
    public static final BitSet FOLLOW_MUL_in_top2236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_top2258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIN_in_factor2300 = new BitSet(new long[]{0x0002000210A25000L});
    public static final BitSet FOLLOW_LP_in_factor2344 = new BitSet(new long[]{0x000220CA12E25000L});
    public static final BitSet FOLLOW_expr_in_factor2346 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RP_in_factor2348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_factor2379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQ_in_factor2409 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_IDENT_in_factor2413 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_DQ_in_factor2415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_number_in_factor2439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_factor2472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_factor2507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEADLOCK_in_factor2541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECLARE_in_declareVars2606 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_variable_in_declareVars2610 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_SEMI_in_declareVars2625 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_variable_in_declareVars2629 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_OPTIMIZE_in_optimizeVars2643 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_variable_in_optimizeVars2647 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_SEMI_in_optimizeVars2663 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_variable_in_optimizeVars2667 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_OVERRIDE_in_overrideIds2679 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_IDENT_in_overrideIds2683 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_SEMI_in_overrideIds2698 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_IDENT_in_overrideIds2702 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_IDENT_in_variable2767 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLEQ_in_variable2769 = new BitSet(new long[]{0x0002000040A04000L});
    public static final BitSet FOLLOW_number_in_variable2777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_range_in_variable2791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bvalue_in_variable2801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LB_in_range2830 = new BitSet(new long[]{0x0002000000A04000L});
    public static final BitSet FOLLOW_number_in_range2839 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_SEMI_in_range2841 = new BitSet(new long[]{0x0000000000804000L});
    public static final BitSet FOLLOW_number_in_range2845 = new BitSet(new long[]{0x0000880000000000L});
    public static final BitSet FOLLOW_SEMI_in_range2863 = new BitSet(new long[]{0x0000000000804000L});
    public static final BitSet FOLLOW_number_in_range2867 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_bvalue_in_range2892 = new BitSet(new long[]{0x0000880000000000L});
    public static final BitSet FOLLOW_SEMI_in_range2909 = new BitSet(new long[]{0x0002000000200000L});
    public static final BitSet FOLLOW_bvalue_in_range2913 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_RB_in_range2945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_bvalue2962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_bvalue2977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_floating_in_number3004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integer_in_number3012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOATING_in_floating3033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIGITS_in_integer3050 = new BitSet(new long[]{0x0000000000000002L});

}