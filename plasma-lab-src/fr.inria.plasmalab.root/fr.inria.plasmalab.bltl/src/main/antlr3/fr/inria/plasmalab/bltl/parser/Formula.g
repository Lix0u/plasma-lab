grammar Formula;

options {
  language = Java;
}

import FormulaRules, FormulaTokens;

@parser::header {
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
}

@lexer::header {
package fr.inria.plasmalab.bltl.parser;
}

@rulecatch { 
    
    catch (RecognitionException re) {
        //displayRecognitionError(re);
        reportError(re);
        //recover(input,re);
        //throw re;
    }
}

@members {
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
}

formula returns [Expr val]    @init{ gFormulaRules.optimizationVariables = new ArrayList<Variable>(); 
                                     gFormulaRules.requirementVarMap = new HashMap<String,Variable>();
                                     gFormulaRules.overrideIdentifiers = new ArrayList<InterfaceIdentifier>();}
:   (declareVars? optimizeVars? overrideIds? END)? 
    expr         { $val = $expr.val; } //|
    /*["Promises:" Expression<out pexpr>] 
  "Goals:" Expression<out Expr gexpr> 
                      (. if(pexpr==null){ .)
                      (.  root = gexpr;.)
                      (.}.)
                      (.else{.)
                      (.  root = new Expr2(pexpr, BinOp.Imp, new ParenthesisExpr(gexpr));.)
                      (.  } .)
                      (.root = new StepBoundExpr1(UnOp.Globally, new IdentExpr("K"), new ParenthesisExpr(root));.)
                      (. varMap.put("K", new Variable("K")); .)
  */
                             EOF
;

// IDENT : ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
// Required to force Antlr to generate the expected FormulaLexer.java ...
DUMMY: 'dummy';