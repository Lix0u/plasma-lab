grammar PRISMModelGrammar;

options {
  language = Java;
  backtrack = true;
}
@parser::header {
package fr.inria.plasmalab.rmlbis.parsing;
 
import fr.inria.plasmalab.rmlbis.ast.models.*;
import fr.inria.plasmalab.rmlbis.ast.rewards.*;
import fr.inria.plasmalab.rmlbis.ast.identifiers.*;
import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.ModuleInstance;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystemInstance;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveAction;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveCommand;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.*;
import fr.inria.plasmalab.rmlbis.ast.expr.types.*;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.*;
import fr.inria.plasmalab.rmlbis.ast.factory.ModuleFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
}

@lexer::header {
package fr.inria.plasmalab.rmlbis.parsing;

import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
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
	//Hack: to be updated by hand when modifying grammar
	public static final String[] tokenNamesTranslated = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "'adaptive'", "'+'", "'&'", "'at'", 
	    "'bool'", "'ceil'", "'clock'", "':'", "','", "'//'", "'const'", "'ctmc'", "'..'", 
	    "'/'", "'.'", "'double'", "'\"'", "'dtmc'", "'endadaptive'", "'endinit'", "'endinvariant'", 
	    "'endmodule'", "'endprocedure'", "'endrewards'", "'endsystem'", "'='", "'false'", 
	    "'floor'", "'formula'", "'func'", "'>='", "'global'", "'>'", "'ident'", "'?'", "'<=>'", 
	    "'=>'", "'init'", "'int'", "an integer", "'invariant'", "'label'", "'<-'", "'['", 
	    "'{'", "'<='", "'log'", "'('", "'<'", "'max'", "'mdp'", "'-'", "'min'", "'mod'", "'module'", 
	    "'*'", "'!='", "'nondeterministic'", "'!'", "'|'", "'||'", "'|||'", "'pow'", 
	    "identifier'.'identifier", "'\''", "'prob'", "'pobabilistic'", "'procedure'", "'pta'", "'->'", 
	    "'rate'", "']'", "'}'", "'rewards'", "')'", "';'", "'stochastic'", "'system'", 
	    "'true'", "white space (' ', '\\t', '\\n', '\\r')"
	};

	public Model model = null;
  	public AdaptiveModel amodel = null;
  	public ModuleFactory factory = null;
	public List<Module> obsModules = null;
	public boolean isSampled = false;
	
	@Override
	protected Object recoverFromMismatchedToken (IntStream input,int ttype,BitSet follow) throws RecognitionException{
		// Desactivate recover from Mismatched input
		throw new MismatchedTokenException (ttype, input);
	}

    @Override
    public void reportError (RecognitionException e) throws PlasmaRuntimeException {
    	String hdr = getErrorHeader(e);
		String msg = getErrorMessage(e, this.tokenNames);
		PlasmaRuntimeException pse = new PlasmaRuntimeException(hdr+" - "+msg, e);
		throw pse;
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
      		msg = "extraneous input "+getTokenErrorDisplay(ute.getUnexpectedToken())+ " expecting "+tokenName;
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
      		msg = "mismatched input "+getTokenErrorDisplay(e.token)+ " expecting "+tokenName;
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
      		msg = "mismatched tree node: "+mtne.node+" expecting "+tokenName;
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
      		msg = "mismatched input "+getTokenErrorDisplay(e.token)+" expecting set "+mse.expecting;
    	}
    	else if ( e instanceof MismatchedNotSetException ) {
      		MismatchedNotSetException mse = (MismatchedNotSetException)e;
      		msg = "mismatched input "+getTokenErrorDisplay(e.token)+" expecting set "+mse.expecting;
    	}
    	else if ( e instanceof FailedPredicateException ) {
      		FailedPredicateException fpe = (FailedPredicateException)e;
      		msg = "rule "+fpe.ruleName+" failed predicate: {"+fpe.predicateText+"}?";
    	}
    	return msg;
    }
}

rmlfile          @init{ ModelType type = ModelType.mdp; } :
( modeltype           { type = $modeltype.t; }
)?                    { model = new Model(type);
                        factory = new ModuleFactory(model);
                      }
declaration*
EOF
;

rmladaptivefile  @init{ ModelType type = ModelType.mdp; } :
ADAPTIVE (modeltype            { type = $modeltype.t; }
)?                    { amodel = new AdaptiveModel(type);
                        model = amodel;
                        factory = new ModuleFactory(model);
                      }
declaration*
adaptive_system
EOF
;

observerfile @init{ 
                      model = new Model();
                      factory = new ModuleFactory(model);
                      obsModules = new ArrayList<Module>();
                  }:
obs_declaration*
EOF
;


// modeltype is not mandatory in PRISM  
modeltype returns [ModelType t]:
DTMC                { $t = ModelType.dtmc; }
( sampled     		{ $t = ModelType.dtmcsampling; } )?
| CTMC              { $t = ModelType.ctmc; }
( sampled 		    { $t = ModelType.ctmcsampling; } )?
| MDP               { $t = ModelType.mdp; }
  ( SHD             { $t = ModelType.mdpshd;}
  	( sampled       { $t = ModelType.mdpshdsampling;} )?
  | SML             { $t = ModelType.mdp;}
    ( sampled       { $t = ModelType.mdpsampling;} )?
  | sampled		    { $t = ModelType.mdpsampling;}
  )?
| PTA               { $t = ModelType.pta; }
// Backward compatibility
| PROBABILISTIC     { $t = ModelType.dtmc; }
| STOCHASTIC        { $t = ModelType.ctmc; }
| NONDETERMINISTIC  { $t = ModelType.mdp; }
;

sampled:
SAMPLING			{ isSampled = true; }
;

declaration:
global_var          { model.addGlobalVariable($global_var.var); }
| constant          { model.addConstant($constant.cons); }
| module_decl       { model.addModule($module_decl.m); }
| module_instance   { factory.addModuleInstance($module_instance.m); }
| formula_decl      { model.addFormula($formula_decl.f); }
| label_decl        { model.addLabel($label_decl.l); }
| reward_decl       { model.addReward($reward_decl.r); }
| init_decl         { model.setInit($init_decl.e); }
| system_decl       { amodel.addSystem($system_decl.s); }
;

obs_declaration:
global_var          { model.addGlobalVariable($global_var.var); }
| constant          { model.addConstant($constant.cons); }
| formula_decl      { model.addFormula($formula_decl.f); }
| observer_decl     { model.addModule($observer_decl.m); }
;

constant returns [Constant cons]    @init{ Expr init = null; } :
csort IDENT                          
(EQ expr                                { init = $expr.e; }
)? SEMI                                 { $cons = (init != null) ? factory.makeConstant($IDENT.text, $csort.type, init)
                                                                 : factory.makeConstant($IDENT.text, $csort.type);
                                        }
// constant might not be initialized. Default values are assumed (0,0.0,false). Same as Prism ? No, Prism has no default values.
;

csort returns [Type type]:
CONST                                   { type = Type.Integer; }
( ctype                                 { type = $ctype.type; }
)? // const <=> const int
// Backward compatibility
| RATE                                  { type = Type.Double; } // <=> const double
| PROB                                  { type = Type.Double; } // <=> const double
;

ctype returns [Type type]:
INT                                     { type = Type.Integer; }
| BOOL                                  { type = Type.Boolean; }
| DOUBLE                                { type = Type.Double;  }
;
 
global_var returns [Variable var]:
GLOBAL var_declaration SEMI             { $var = $var_declaration.var; }
;


var_declaration returns [Variable var]    @init{ Expr init = null; } : 
IDENT COL sort 
( INIT expr                             { init = $expr.e; }
)?                                      { if ($sort.lower != null & $sort.upper != null)
                                            var = (init == null) ? factory.makeVariable($IDENT.text, $sort.lower, $sort.upper)
                                                                 : factory.makeVariable($IDENT.text, $sort.lower, $sort.upper, init);
                                          else
                                            var = (init == null) ? factory.makeVariable($IDENT.text, $sort.type)
                                                                 : factory.makeVariable($IDENT.text, $sort.type, init);
                                        }
;

sort returns [Type type, Expr lower, Expr upper] :
// any model type and any scope
  BOOL                                  { $type = Type.Boolean; $lower = null; $upper = null; }
| INT                                   { $type = Type.Integer; $lower = null; $upper = null; }  
| DOUBLE                                { $type = Type.Double; $lower = null; $upper = null; }
| LB min=expr DDOT max=expr RB          { $type = Type.Integer; $lower = $min.e; $upper = $max.e; }
| CLOCK                                 { $type = Type.Clock; $lower = null; $upper = null; } 
;


formula_decl returns [Formula f]:
FORMULA IDENT EQ expr SEMI              { $f=factory.makeFormula($IDENT.text,$expr.e); }
;


label_decl returns [Label l]:
LABEL DQ IDENT DQ EQ expr SEMI          { $l=factory.makeLabel($IDENT.text ,$expr.e); }
;


init_decl returns [Expr e]:
INIT expr ENDINIT                       { $e = $expr.e; }
;


reward_decl returns [Reward r]    @init { String name = ""; } :
// PRISM seems to accept both state and transition rewards in a single reward structure
REWARDS 
( DQ IDENT DQ                           { name = $IDENT.text; } 
              )?                        { $r=factory.makeReward(name); }
( reward_statement                      { $r.addRewardStatement($reward_statement.r); }
                  )*
ENDREWARDS
;

reward_statement returns [StateReward r] :
state_reward                            { $r = $state_reward.r; }
| trans_reward                          { $r = $trans_reward.r; }
;

state_reward returns [StateReward r]:
cond=expr COL rew=expr SEMI             { $r = new StateReward($cond.e,$rew.e); }
;

trans_reward returns [TransitionReward r] :
synchro cond=expr COL rew=expr SEMI     { $r = new TransitionReward($synchro.id,$cond.e,$rew.e); }
;


module_decl returns [Module m] :
MODULE IDENT                            { $m = factory.openModule($IDENT.text); }
( LP
  p1=parameter                          { $m.addParameter($p1.param); }
  ( COMMA p2=parameter                  { $m.addParameter($p2.param); }
                      )*
  RP
)?
( local_var                             { $m.addLocalVariable($local_var.var); }
)*
( invariant                             { } // $m.setInvariant($invariant.e); }
)?
( command                               { factory.addCommand(); }
)*
ENDMODULE                               { factory.closeModule(); }
;

observer_decl returns [Module m] :
OBSERVER IDENT                          { $m = factory.openModule($IDENT.text);
                                          obsModules.add($m);
                                        }
( local_var                             { $m.addLocalVariable($local_var.var); }
)*
( obs_command                           { factory.addCommand(); }
)*
ENDOBSERVER                             { factory.closeModule(); }
;

parameter returns [Parameter param]: 
csort IDENT                               { param = factory.makeParameter($IDENT.text, $csort.type,true); }
| sort IDENT                              { if ($sort.lower != null & $sort.upper != null)
                                              param = factory.makeParameter($IDENT.text, $sort.lower, $sort.upper, false);
                                            else
                                              param = factory.makeParameter($IDENT.text, $sort.type, false);
                                          }
;


module_instance returns [ModuleInstance m] @init{ Map<String,String> ren = new HashMap<String,String>();
                                                  List<Expr> params = null; }:
MODULE newident=IDENT EQ oldident=IDENT   
( LP exprs RP                                   { params = $exprs.list; }  
)?
( LB renamings[ren] RB )?                       { $m = new ModuleInstance($newident.text, $oldident.text, ren, params); }
ENDMODULE                                 
;

renamings[Map<String,String> map] :
r1=renaming                                       { $map.put($r1.r[0],$r1.r[1]); }
( COMMA r2=renaming                               { $map.put($r2.r[0],$r2.r[1]); }
)* 
;

renaming returns [String[\] r] :
id1=IDENT EQ id2=(IDENT | PREFIDENT)              { $r = new String[2]; $r[0] = $id1.text; $r[1] = $id2.text; }
;


local_var returns [Variable var]:
var_declaration SEMI                      { $var = $var_declaration.var; }
;
 
invariant returns [Expr e]:
INVARIANT expr ENDINVARIANT               { e = $expr.e; }
;

command:
synchro expr             				  { if (isSampled)
 												factory.makeSamplingCommand($synchro.id, $expr.e);
 											else
 												factory.makeCommand($synchro.id, $expr.e); 
 										  }
RARROW actions_set SEMI
;

sampling_param returns [Expr e]:
LBC expr RBC							  { e = $expr.e; }
;

synchro returns [String id] @init{ id = ""; } :
LB
( IDENT                                   { id = $IDENT.text; }
)?
RB
;

obs_command:
LB RB expr                                { factory.makeCommand("",$expr.e); }
RARROW		 							  { factory.makeAction(factory.makeValue(1.0)); }
updates
SEMI									  { factory.addAction(); }
;

actions_set @init{  // initialize a default action. Overwritten in the action rule.
					if (isSampled)
						factory.makeSamplingAction(factory.makeValue(1.0),null);
					else	
						factory.makeAction(factory.makeValue(1.0)); 
				 }:
actions
| ( updates								  { factory.addAction(); } 
  ) 
;

actions:
action                                    { factory.addAction(); }  
( ADD action                              { factory.addAction(); }
)*
;

action @init{ Expr sample = null; }:
( sampling_param 						  { sample = $sampling_param.e; } 
)?
expr		  	 	                      { if (isSampled)
 												factory.makeSamplingAction($expr.e, sample);
 											else
 												factory.makeAction($expr.e); 
 										  }
COL updates
;

updates:
update ( AND update)*
| TRUE
;

update:
LP IDENT PRIME EQ expr RP                 { factory.addAssignation($IDENT.text,$expr.e); }
;


expr returns [Expr e] :
imply_expr                                 { e = $imply_expr.e; }
(IF th=expr COL el=expr                    { e = factory.makeITEExpr(e, th, el); }
)?                                         //{ System.out.println("end expr: " + e.toString()); }        
;

imply_expr returns [Expr e]           @init{ Stack<Expr> stack = new Stack<Expr>(); }
: fst = iff_expr                            { stack.push($fst.e); }
   (IMP nxt = iff_expr                      { stack.push($nxt.e); }
)*                                          { e = factory.makeRightAssocBinExpr(BinOp.Imp, stack); }
;

iff_expr returns [Expr e]
: fst = or_expr                           { e = $fst.e; }
  (IFF nxt = or_expr                      { e = factory.makeBinExpr(e, BinOp.Iff, $nxt.e); }
                      )*                  
;

or_expr returns [Expr e] 
: fst = and_expr                          { e = $fst.e; }
  (OR nxt = and_expr                      { e = factory.makeBinExpr(e, BinOp.Or, $nxt.e); }
                    )*                    
;

and_expr returns [Expr e] 
: fst = not_expr                          { e = $fst.e; }
  (AND nxt = not_expr                     { e = factory.makeBinExpr(e, BinOp.And, $nxt.e); }
                       )*
;

not_expr returns [Expr e]         	 @init{ boolean not = false; }
: (NOT                                    { not = true; }
      )? child = eq_expr                  { e = not ? factory.makeUnExpr(UnOp.Not, child) : child; }     
;

eq_expr returns [Expr e]          	 @init{ BinOp bop = null; } :
left=comp_expr                            { e = $left.e; }
( ( EQ                                    { bop = BinOp.Eq; }
  | NEQ                                   { bop = BinOp.Neq; }
  ) right=comp_expr                       { e = factory.makeBinExpr(e, bop, right); }     
)?
;

comp_expr returns [Expr e]           @init{ BinOp bop = null; } :
left = add_expr                            { e = $left.e; }
(
  ( LT                                     { bop = BinOp.Lt; }
    | LE                                   { bop = BinOp.Le; }
    | GE                                   { bop = BinOp.Ge; }
    | GT                                   { bop = BinOp.Gt; }   
  )
  right = add_expr                         { e = factory.makeBinExpr(e, bop, right); }
)?
;

add_expr returns [Expr e]             @init{ BinOp op = null; }
: fst = mult_expr                          { e = $fst.e; }
(
  ( ADD                                    { op = BinOp.Add; }
  | MIN                                    { op = BinOp.Sub; }
        ) nxt = mult_expr                  { e = factory.makeBinExpr(e, op, $nxt.e); }
                          )*                                         
;

mult_expr returns [Expr e]            @init{ BinOp op = null; }
: fst = min_expr                           { e = $fst.e; }
( ( MUL                                    { op = BinOp.Mul; }
  | DIV                                    { op = BinOp.Div; }  
       ) nxt = min_expr                    { e = factory.makeBinExpr(e, op, $nxt.e); }
                        )*
;

min_expr returns [Expr e]            @init{ boolean min = false; }
: (MIN                                     { min = true; }
       )? child = term                     { e = min ? factory.makeUnExpr(UnOp.Neg, child) : child; }
;

term returns [Expr e]:
IDENT                                      { e = factory.makeRef($IDENT.text); }
| DQ IDENT DQ                              { e = factory.makeRef(new String("\"" + $IDENT.text + "\"")); }
| PREFIDENT                                { e = factory.makeRef($PREFIDENT.text); }
| TRUE                                     { e = factory.makeValue(true); }
| FALSE                                    { e = factory.makeValue(false); }
| INTVAL								   { e = factory.makeValue(Integer.parseInt($INTVAL.text)); }	
| VALEXP		                           { e = factory.makeValue(Double.parseDouble($VALEXP.text)); }
| doubleval                                { e = factory.makeValue(Double.parseDouble($doubleval.text)); }	
| funid LP exprs RP                        { e = factory.makeFunction($funid.fun, $exprs.list.toArray(new Expr[$exprs.list.size()])); }                         
| FUNC LP funid COMMA exprs RP             { e = factory.makeFunction($funid.fun, $exprs.list.toArray(new Expr[$exprs.list.size()])); }
| LP expr RP                               { e = $expr.e; }
;

funid returns [BuiltIn fun]:
MAXI                                       { fun = BuiltIn.Max; }
| MINI                                     { fun = BuiltIn.Min; }
| FLOOR                                    { fun = BuiltIn.Floor; }
| CEIL                                     { fun = BuiltIn.Ceil; }
| POW                                      { fun = BuiltIn.Pow; }
| MOD                                      { fun = BuiltIn.Mod; }
| LOG                                      { fun = BuiltIn.Log; }
;

exprs returns [List<Expr> list]      @init{ list = new ArrayList<Expr>(0); } :
( fst = expr                              { list.add(fst); }
)?
( COMMA nxt = expr                        { list.add(nxt); }
)*       
;


system_decl returns [PlasmaSystem s] @init { ModuleFactory general_factory = factory;
                                             factory = new ModuleFactory(model);
                                           } :
SYSTEM IDENT                               { s = new PlasmaSystem($IDENT.text,amodel,factory); }
( LP p1=parameter                          { s.addParameter($p1.param); }
  (COMMA p2=parameter                      { s.addParameter($p2.param); }
  )*
RP)?
system_var[$s]*
system_algebra?
ENDSYSTEM                                  { factory = general_factory; }
;


system_var [PlasmaSystem s]:
module_instance                            { $s.addModuleInstance($module_instance.m); }
| label_decl                               { $s.addLabel($label_decl.l); }
| init_decl                                { $s.setInit($init_decl.e); }
;

system_instance returns [PlasmaSystemInstance s]:
IDENT LP exprs RP                          { s = new PlasmaSystemInstance(amodel.getSystem($IDENT.text),$exprs.list); }
;

adaptive_system                      @init { AdaptiveSystem sys = new AdaptiveSystem(amodel);
                                             amodel.setAdaptiveSystem(sys); } :
ADAPTIVE                                   
start_system                               { sys.setStartSystem($start_system.s); }             
(adaptive_command                          { sys.addTransition($adaptive_command.com); }
)*
ENDADAPTIVE
;

start_system returns [PlasmaSystemInstance s]:
INIT AT system_instance SEMI               { $s = $system_instance.s; }
;

adaptive_command returns [AdaptiveCommand com]
@init                                      { PlasmaSystem sys = null; 
                                             ModuleFactory general_factory = factory;
                                           } :
LBC IDENT                                  { sys = amodel.getSystem($IDENT.text); 
                                             factory = sys.factory;
                                           } 
(OR expr) RBC                              { $com = new AdaptiveCommand(sys, $expr.e); }     
RARROW
( adaptive_actions[com]
  | adaptive_update[null]                  { $com.addAction($adaptive_update.a); }
)
SEMI                                       { factory = general_factory; }
;

adaptive_actions [AdaptiveCommand com]:
a1=adaptive_action                         { $com.addAction($a1.a); }
(ADD a2=adaptive_action                    { $com.addAction($a2.a); }
)*
;

adaptive_action returns [AdaptiveAction a]:
expr
COL adaptive_update [$expr.e]              { $a = $adaptive_update.a; }
;

adaptive_update [Expr rate] returns [AdaptiveAction a]:
LBC
system_instance                            { if (rate!=null)
                                              $a = new AdaptiveAction($system_instance.s,rate);
                                             else
                                              $a = new AdaptiveAction($system_instance.s, factory);
                                           }   
( OR procedure_instance                    { $a.addProcedure($procedure_instance.proc); }
)*
RBC
;

procedure_instance returns [ModuleInstance proc] @init{ Map<String,String> ren = new HashMap<String,String>();
                                                        List<Expr> params = null; }:
newident=IDENT EQ oldident=IDENT 
( LP exprs RP                                   { params = $exprs.list; }  
)?
( LB renamings[ren] RB )?                       { $proc = new ModuleInstance($newident.text, $oldident.text, ren, params); }
;

system_algebra:
module_compo ( OR LB channels RB OR module_compo
               | (PAR2 module_compo)+
               | (PAR3 module_compo)+
             )
;

module_compo:
IDENT (channels_hiding | channels_renaming)
| LP system_algebra RP
;

channels:
IDENT (COMMA IDENT)*
;

channels_hiding:
DIV LBC channels RBC
;

channels_renaming:
LBC channel_renaming (COMMA channel_renaming)* RBC
;

channel_renaming:
IDENT LARROW IDENT
;

//doubleval returns [double val]:
//( ival=INTVAL								{ val = Double.parseDouble($ival.text); }
//| dval=((INTVAL? DOT) INTVAL)				{ val = Double.parseDouble($dval.text); }
//)
//( ('E' | 'e') plusval=INTVAL				{ val *= Math.pow(10, Integer.parseInt($plusval.text)); }
//  |	('E-' | 'e-') minusval=INTVAL			{ val *= Math.pow(10, (-1) * Integer.parseInt($minusval.text)); }
//)?
//;

doubleval: 
(INTVAL)? DOT (INTVAL | VALEXP);

//doublevalexp:
//(INTVAL | (INTVAL)? DOT INTVAL) ('e'|'E') MIN? INTVAL;

// TOKENS
CTMC: 'ctmc';
DTMC: 'dtmc';
MDP: 'mdp';
SHD: 'shd';
SML: 'sml';
PTA: 'pta';
PROBABILISTIC: 'probabilistic';
STOCHASTIC: 'stochastic';
NONDETERMINISTIC: 'nondeterministic';
SAMPLING: 'sampling';

EQ: '=';
NEQ: '!=';
LT: '<';
LE: '<=';
GT: '>';
GE: '>=';
ADD: '+';
MIN: '-';
MUL: '*';
DIV: '/';
AND: '&';
OR: '|';
NOT: '!';
IF: '?';
IFF: '<=>';
IMP: '=>';

DDOT: '..';
DOT: '.';
COMMA: ',';
SEMI: ';';
COL: ':';
DQ: '"';
PRIME: '\'';
LB: '[';
RB: ']';
LP: '(';
RP: ')';
LBC: '{';
RBC: '}';
RARROW: '->';
LARROW: '<-'; 
PAR2: '||';
PAR3: '|||';

GLOBAL: 'global';
CONST: 'const';
RATE: 'rate';
PROB: 'prob';
INT: 'int';
BOOL: 'bool';
DOUBLE: 'double';
CLOCK: 'clock';
FUNC: 'func';
FORMULA: 'formula';
LABEL: 'label';
INVARIANT: 'invariant';
ENDINVARIANT: 'endinvariant';
INIT: 'init';
ENDINIT: 'endinit';
REWARDS: 'rewards';
ENDREWARDS: 'endrewards';
MODULE: 'module';
ENDMODULE: 'endmodule';
OBSERVER: 'observer';
ENDOBSERVER: 'endobserver';
SYSTEM: 'system';
ENDSYSTEM: 'endsystem';
PROCEDURE: 'procedure';
ENDPROCEDURE: 'endprocedure';
ADAPTIVE: 'adaptive';
ENDADAPTIVE: 'endadaptive';
// START: 'start';
AT: 'at';
MAXI: 'max';
MINI: 'min';
FLOOR: 'floor';
CEIL: 'ceil';
POW: 'pow';
MOD: 'mod';
LOG: 'log';

TRUE: 'true';
FALSE: 'false';
IDENT : ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
PREFIDENT: IDENT DOT IDENT;
INTVAL: ('0'..'9')+;
//DOUBLEVAL: (INTVAL)? '.' INTVAL;  //{ System.out.println("DOUBLEVAL " + $text); } ;
VALEXP: INTVAL ('e'|'E') ('-')? INTVAL; //  { System.out.println("DOUBLEVALEXP " + $text); } ;

WS : (' '|'\t'|'\n'|'\r')+ {$channel=HIDDEN;};

COMMENT :   '//' ~('\n'|'\r')* '\r'? '\n' { $channel=HIDDEN; };

