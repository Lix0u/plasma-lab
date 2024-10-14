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
// $ANTLR 3.4 PRISMModelGrammar.g 2017-05-11 15:44:28

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class PRISMModelGrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADAPTIVE", "ADD", "AND", "AT", "BOOL", "CEIL", "CLOCK", "COL", "COMMA", "COMMENT", "CONST", "CTMC", "DDOT", "DIV", "DOT", "DOUBLE", "DQ", "DTMC", "ENDADAPTIVE", "ENDINIT", "ENDINVARIANT", "ENDMODULE", "ENDOBSERVER", "ENDPROCEDURE", "ENDREWARDS", "ENDSYSTEM", "EQ", "FALSE", "FLOOR", "FORMULA", "FUNC", "GE", "GLOBAL", "GT", "IDENT", "IF", "IFF", "IMP", "INIT", "INT", "INTVAL", "INVARIANT", "LABEL", "LARROW", "LB", "LBC", "LE", "LOG", "LP", "LT", "MAXI", "MDP", "MIN", "MINI", "MOD", "MODULE", "MUL", "NEQ", "NONDETERMINISTIC", "NOT", "OBSERVER", "OR", "PAR2", "PAR3", "POW", "PREFIDENT", "PRIME", "PROB", "PROBABILISTIC", "PROCEDURE", "PTA", "RARROW", "RATE", "RB", "RBC", "REWARDS", "RP", "SAMPLING", "SEMI", "SHD", "SML", "STOCHASTIC", "SYSTEM", "TRUE", "VALEXP", "WS"
    };

    public static final int EOF=-1;
    public static final int ADAPTIVE=4;
    public static final int ADD=5;
    public static final int AND=6;
    public static final int AT=7;
    public static final int BOOL=8;
    public static final int CEIL=9;
    public static final int CLOCK=10;
    public static final int COL=11;
    public static final int COMMA=12;
    public static final int COMMENT=13;
    public static final int CONST=14;
    public static final int CTMC=15;
    public static final int DDOT=16;
    public static final int DIV=17;
    public static final int DOT=18;
    public static final int DOUBLE=19;
    public static final int DQ=20;
    public static final int DTMC=21;
    public static final int ENDADAPTIVE=22;
    public static final int ENDINIT=23;
    public static final int ENDINVARIANT=24;
    public static final int ENDMODULE=25;
    public static final int ENDOBSERVER=26;
    public static final int ENDPROCEDURE=27;
    public static final int ENDREWARDS=28;
    public static final int ENDSYSTEM=29;
    public static final int EQ=30;
    public static final int FALSE=31;
    public static final int FLOOR=32;
    public static final int FORMULA=33;
    public static final int FUNC=34;
    public static final int GE=35;
    public static final int GLOBAL=36;
    public static final int GT=37;
    public static final int IDENT=38;
    public static final int IF=39;
    public static final int IFF=40;
    public static final int IMP=41;
    public static final int INIT=42;
    public static final int INT=43;
    public static final int INTVAL=44;
    public static final int INVARIANT=45;
    public static final int LABEL=46;
    public static final int LARROW=47;
    public static final int LB=48;
    public static final int LBC=49;
    public static final int LE=50;
    public static final int LOG=51;
    public static final int LP=52;
    public static final int LT=53;
    public static final int MAXI=54;
    public static final int MDP=55;
    public static final int MIN=56;
    public static final int MINI=57;
    public static final int MOD=58;
    public static final int MODULE=59;
    public static final int MUL=60;
    public static final int NEQ=61;
    public static final int NONDETERMINISTIC=62;
    public static final int NOT=63;
    public static final int OBSERVER=64;
    public static final int OR=65;
    public static final int PAR2=66;
    public static final int PAR3=67;
    public static final int POW=68;
    public static final int PREFIDENT=69;
    public static final int PRIME=70;
    public static final int PROB=71;
    public static final int PROBABILISTIC=72;
    public static final int PROCEDURE=73;
    public static final int PTA=74;
    public static final int RARROW=75;
    public static final int RATE=76;
    public static final int RB=77;
    public static final int RBC=78;
    public static final int REWARDS=79;
    public static final int RP=80;
    public static final int SAMPLING=81;
    public static final int SEMI=82;
    public static final int SHD=83;
    public static final int SML=84;
    public static final int STOCHASTIC=85;
    public static final int SYSTEM=86;
    public static final int TRUE=87;
    public static final int VALEXP=88;
    public static final int WS=89;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public PRISMModelGrammarParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public PRISMModelGrammarParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return PRISMModelGrammarParser.tokenNames; }
    public String getGrammarFileName() { return "PRISMModelGrammar.g"; }


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



    // $ANTLR start "rmlfile"
    // PRISMModelGrammar.g:157:1: rmlfile : ( modeltype )? ( declaration )* EOF ;
    public final void rmlfile() throws RecognitionException {
        ModelType modeltype1 =null;


         ModelType type = ModelType.mdp; 
        try {
            // PRISMModelGrammar.g:157:59: ( ( modeltype )? ( declaration )* EOF )
            // PRISMModelGrammar.g:158:3: ( modeltype )? ( declaration )* EOF
            {
            // PRISMModelGrammar.g:158:3: ( modeltype )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==CTMC||LA1_0==DTMC||LA1_0==MDP||LA1_0==NONDETERMINISTIC||LA1_0==PROBABILISTIC||LA1_0==PTA||LA1_0==STOCHASTIC) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // PRISMModelGrammar.g:158:3: modeltype
                    {
                    pushFollow(FOLLOW_modeltype_in_rmlfile77);
                    modeltype1=modeltype();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { type = modeltype1; }

                    }
                    break;

            }


            if ( state.backtracking==0 ) { model = new Model(type);
                                    factory = new ModuleFactory(model);
                                  }

            // PRISMModelGrammar.g:162:0: ( declaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==CONST||LA2_0==FORMULA||LA2_0==GLOBAL||LA2_0==INIT||LA2_0==LABEL||LA2_0==MODULE||LA2_0==PROB||LA2_0==RATE||LA2_0==REWARDS||LA2_0==SYSTEM) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // PRISMModelGrammar.g:162:0: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_rmlfile115);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input,EOF,FOLLOW_EOF_in_rmlfile118); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "rmlfile"



    // $ANTLR start "rmladaptivefile"
    // PRISMModelGrammar.g:166:1: rmladaptivefile : ADAPTIVE ( modeltype )? ( declaration )* adaptive_system EOF ;
    public final void rmladaptivefile() throws RecognitionException {
        ModelType modeltype2 =null;


         ModelType type = ModelType.mdp; 
        try {
            // PRISMModelGrammar.g:166:59: ( ADAPTIVE ( modeltype )? ( declaration )* adaptive_system EOF )
            // PRISMModelGrammar.g:167:1: ADAPTIVE ( modeltype )? ( declaration )* adaptive_system EOF
            {
            match(input,ADAPTIVE,FOLLOW_ADAPTIVE_in_rmladaptivefile132); if (state.failed) return ;

            // PRISMModelGrammar.g:167:10: ( modeltype )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==CTMC||LA3_0==DTMC||LA3_0==MDP||LA3_0==NONDETERMINISTIC||LA3_0==PROBABILISTIC||LA3_0==PTA||LA3_0==STOCHASTIC) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // PRISMModelGrammar.g:167:11: modeltype
                    {
                    pushFollow(FOLLOW_modeltype_in_rmladaptivefile135);
                    modeltype2=modeltype();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { type = modeltype2; }

                    }
                    break;

            }


            if ( state.backtracking==0 ) { amodel = new AdaptiveModel(type);
                                    model = amodel;
                                    factory = new ModuleFactory(model);
                                  }

            // PRISMModelGrammar.g:172:0: ( declaration )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==CONST||LA4_0==FORMULA||LA4_0==GLOBAL||LA4_0==INIT||LA4_0==LABEL||LA4_0==MODULE||LA4_0==PROB||LA4_0==RATE||LA4_0==REWARDS||LA4_0==SYSTEM) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // PRISMModelGrammar.g:172:0: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_rmladaptivefile174);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            pushFollow(FOLLOW_adaptive_system_in_rmladaptivefile177);
            adaptive_system();

            state._fsp--;
            if (state.failed) return ;

            match(input,EOF,FOLLOW_EOF_in_rmladaptivefile179); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "rmladaptivefile"



    // $ANTLR start "observerfile"
    // PRISMModelGrammar.g:177:1: observerfile : ( obs_declaration )* EOF ;
    public final void observerfile() throws RecognitionException {
         
                              model = new Model();
                              factory = new ModuleFactory(model);
                              obsModules = new ArrayList<Module>();
                          
        try {
            // PRISMModelGrammar.g:181:20: ( ( obs_declaration )* EOF )
            // PRISMModelGrammar.g:182:0: ( obs_declaration )* EOF
            {
            // PRISMModelGrammar.g:182:0: ( obs_declaration )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==CONST||LA5_0==FORMULA||LA5_0==GLOBAL||LA5_0==OBSERVER||LA5_0==PROB||LA5_0==RATE) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // PRISMModelGrammar.g:182:0: obs_declaration
            	    {
            	    pushFollow(FOLLOW_obs_declaration_in_observerfile191);
            	    obs_declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input,EOF,FOLLOW_EOF_in_observerfile194); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "observerfile"



    // $ANTLR start "modeltype"
    // PRISMModelGrammar.g:188:1: modeltype returns [ModelType t] : ( DTMC ( sampled )? | CTMC ( sampled )? | MDP ( SHD ( sampled )? | SML ( sampled )? | sampled )? | PTA | PROBABILISTIC | STOCHASTIC | NONDETERMINISTIC );
    public final ModelType modeltype() throws RecognitionException {
        ModelType t = null;


        try {
            // PRISMModelGrammar.g:188:32: ( DTMC ( sampled )? | CTMC ( sampled )? | MDP ( SHD ( sampled )? | SML ( sampled )? | sampled )? | PTA | PROBABILISTIC | STOCHASTIC | NONDETERMINISTIC )
            int alt11=7;
            switch ( input.LA(1) ) {
            case DTMC:
                {
                alt11=1;
                }
                break;
            case CTMC:
                {
                alt11=2;
                }
                break;
            case MDP:
                {
                alt11=3;
                }
                break;
            case PTA:
                {
                alt11=4;
                }
                break;
            case PROBABILISTIC:
                {
                alt11=5;
                }
                break;
            case STOCHASTIC:
                {
                alt11=6;
                }
                break;
            case NONDETERMINISTIC:
                {
                alt11=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return t;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }

            switch (alt11) {
                case 1 :
                    // PRISMModelGrammar.g:189:0: DTMC ( sampled )?
                    {
                    match(input,DTMC,FOLLOW_DTMC_in_modeltype208); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.dtmc; }

                    // PRISMModelGrammar.g:190:3: ( sampled )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==SAMPLING) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // PRISMModelGrammar.g:190:3: sampled
                            {
                            pushFollow(FOLLOW_sampled_in_modeltype229);
                            sampled();

                            state._fsp--;
                            if (state.failed) return t;

                            if ( state.backtracking==0 ) { t = ModelType.dtmcsampling; }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:191:3: CTMC ( sampled )?
                    {
                    match(input,CTMC,FOLLOW_CTMC_in_modeltype244); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.ctmc; }

                    // PRISMModelGrammar.g:192:3: ( sampled )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==SAMPLING) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // PRISMModelGrammar.g:192:3: sampled
                            {
                            pushFollow(FOLLOW_sampled_in_modeltype263);
                            sampled();

                            state._fsp--;
                            if (state.failed) return t;

                            if ( state.backtracking==0 ) { t = ModelType.ctmcsampling; }

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:193:3: MDP ( SHD ( sampled )? | SML ( sampled )? | sampled )?
                    {
                    match(input,MDP,FOLLOW_MDP_in_modeltype278); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.mdp; }

                    // PRISMModelGrammar.g:194:3: ( SHD ( sampled )? | SML ( sampled )? | sampled )?
                    int alt10=4;
                    switch ( input.LA(1) ) {
                        case SHD:
                            {
                            alt10=1;
                            }
                            break;
                        case SML:
                            {
                            alt10=2;
                            }
                            break;
                        case SAMPLING:
                            {
                            alt10=3;
                            }
                            break;
                    }

                    switch (alt10) {
                        case 1 :
                            // PRISMModelGrammar.g:194:5: SHD ( sampled )?
                            {
                            match(input,SHD,FOLLOW_SHD_in_modeltype300); if (state.failed) return t;

                            if ( state.backtracking==0 ) { t = ModelType.mdpshd;}

                            // PRISMModelGrammar.g:195:4: ( sampled )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0==SAMPLING) ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // PRISMModelGrammar.g:195:6: sampled
                                    {
                                    pushFollow(FOLLOW_sampled_in_modeltype321);
                                    sampled();

                                    state._fsp--;
                                    if (state.failed) return t;

                                    if ( state.backtracking==0 ) { t = ModelType.mdpshdsampling;}

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // PRISMModelGrammar.g:196:5: SML ( sampled )?
                            {
                            match(input,SML,FOLLOW_SML_in_modeltype338); if (state.failed) return t;

                            if ( state.backtracking==0 ) { t = ModelType.mdp;}

                            // PRISMModelGrammar.g:197:5: ( sampled )?
                            int alt9=2;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0==SAMPLING) ) {
                                alt9=1;
                            }
                            switch (alt9) {
                                case 1 :
                                    // PRISMModelGrammar.g:197:7: sampled
                                    {
                                    pushFollow(FOLLOW_sampled_in_modeltype360);
                                    sampled();

                                    state._fsp--;
                                    if (state.failed) return t;

                                    if ( state.backtracking==0 ) { t = ModelType.mdpsampling;}

                                    }
                                    break;

                            }


                            }
                            break;
                        case 3 :
                            // PRISMModelGrammar.g:198:5: sampled
                            {
                            pushFollow(FOLLOW_sampled_in_modeltype377);
                            sampled();

                            state._fsp--;
                            if (state.failed) return t;

                            if ( state.backtracking==0 ) { t = ModelType.mdpsampling;}

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:200:3: PTA
                    {
                    match(input,PTA,FOLLOW_PTA_in_modeltype393); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.pta; }

                    }
                    break;
                case 5 :
                    // PRISMModelGrammar.g:202:3: PROBABILISTIC
                    {
                    match(input,PROBABILISTIC,FOLLOW_PROBABILISTIC_in_modeltype414); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.dtmc; }

                    }
                    break;
                case 6 :
                    // PRISMModelGrammar.g:203:3: STOCHASTIC
                    {
                    match(input,STOCHASTIC,FOLLOW_STOCHASTIC_in_modeltype424); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.ctmc; }

                    }
                    break;
                case 7 :
                    // PRISMModelGrammar.g:204:3: NONDETERMINISTIC
                    {
                    match(input,NONDETERMINISTIC,FOLLOW_NONDETERMINISTIC_in_modeltype437); if (state.failed) return t;

                    if ( state.backtracking==0 ) { t = ModelType.mdp; }

                    }
                    break;

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
        return t;
    }
    // $ANTLR end "modeltype"



    // $ANTLR start "sampled"
    // PRISMModelGrammar.g:207:1: sampled : SAMPLING ;
    public final void sampled() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:207:8: ( SAMPLING )
            // PRISMModelGrammar.g:208:1: SAMPLING
            {
            match(input,SAMPLING,FOLLOW_SAMPLING_in_sampled448); if (state.failed) return ;

            if ( state.backtracking==0 ) { isSampled = true; }

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
        return ;
    }
    // $ANTLR end "sampled"



    // $ANTLR start "declaration"
    // PRISMModelGrammar.g:211:1: declaration : ( global_var | constant | module_decl | module_instance | formula_decl | label_decl | reward_decl | init_decl | system_decl );
    public final void declaration() throws RecognitionException {
        Variable global_var3 =null;

        Constant constant4 =null;

        Module module_decl5 =null;

        ModuleInstance module_instance6 =null;

        Formula formula_decl7 =null;

        Label label_decl8 =null;

        Reward reward_decl9 =null;

        Expr init_decl10 =null;

        PlasmaSystem system_decl11 =null;


        try {
            // PRISMModelGrammar.g:211:12: ( global_var | constant | module_decl | module_instance | formula_decl | label_decl | reward_decl | init_decl | system_decl )
            int alt12=9;
            switch ( input.LA(1) ) {
            case GLOBAL:
                {
                alt12=1;
                }
                break;
            case CONST:
            case PROB:
            case RATE:
                {
                alt12=2;
                }
                break;
            case MODULE:
                {
                int LA12_3 = input.LA(2);

                if ( (LA12_3==IDENT) ) {
                    int LA12_9 = input.LA(3);

                    if ( (LA12_9==EQ) ) {
                        alt12=4;
                    }
                    else if ( (LA12_9==ENDMODULE||LA12_9==IDENT||LA12_9==INVARIANT||LA12_9==LB||LA12_9==LP) ) {
                        alt12=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 9, input);

                        throw nvae;

                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 3, input);

                    throw nvae;

                }
                }
                break;
            case FORMULA:
                {
                alt12=5;
                }
                break;
            case LABEL:
                {
                alt12=6;
                }
                break;
            case REWARDS:
                {
                alt12=7;
                }
                break;
            case INIT:
                {
                alt12=8;
                }
                break;
            case SYSTEM:
                {
                alt12=9;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }

            switch (alt12) {
                case 1 :
                    // PRISMModelGrammar.g:212:0: global_var
                    {
                    pushFollow(FOLLOW_global_var_in_declaration460);
                    global_var3=global_var();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addGlobalVariable(global_var3); }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:213:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_declaration475);
                    constant4=constant();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addConstant(constant4); }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:214:3: module_decl
                    {
                    pushFollow(FOLLOW_module_decl_in_declaration490);
                    module_decl5=module_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addModule(module_decl5); }

                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:215:3: module_instance
                    {
                    pushFollow(FOLLOW_module_instance_in_declaration502);
                    module_instance6=module_instance();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { factory.addModuleInstance(module_instance6); }

                    }
                    break;
                case 5 :
                    // PRISMModelGrammar.g:216:3: formula_decl
                    {
                    pushFollow(FOLLOW_formula_decl_in_declaration510);
                    formula_decl7=formula_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addFormula(formula_decl7); }

                    }
                    break;
                case 6 :
                    // PRISMModelGrammar.g:217:3: label_decl
                    {
                    pushFollow(FOLLOW_label_decl_in_declaration521);
                    label_decl8=label_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addLabel(label_decl8); }

                    }
                    break;
                case 7 :
                    // PRISMModelGrammar.g:218:3: reward_decl
                    {
                    pushFollow(FOLLOW_reward_decl_in_declaration534);
                    reward_decl9=reward_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addReward(reward_decl9); }

                    }
                    break;
                case 8 :
                    // PRISMModelGrammar.g:219:3: init_decl
                    {
                    pushFollow(FOLLOW_init_decl_in_declaration546);
                    init_decl10=init_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.setInit(init_decl10); }

                    }
                    break;
                case 9 :
                    // PRISMModelGrammar.g:220:3: system_decl
                    {
                    pushFollow(FOLLOW_system_decl_in_declaration560);
                    system_decl11=system_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { amodel.addSystem(system_decl11); }

                    }
                    break;

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
        return ;
    }
    // $ANTLR end "declaration"



    // $ANTLR start "obs_declaration"
    // PRISMModelGrammar.g:223:1: obs_declaration : ( global_var | constant | formula_decl | observer_decl );
    public final void obs_declaration() throws RecognitionException {
        Variable global_var12 =null;

        Constant constant13 =null;

        Formula formula_decl14 =null;

        Module observer_decl15 =null;


        try {
            // PRISMModelGrammar.g:223:16: ( global_var | constant | formula_decl | observer_decl )
            int alt13=4;
            switch ( input.LA(1) ) {
            case GLOBAL:
                {
                alt13=1;
                }
                break;
            case CONST:
            case PROB:
            case RATE:
                {
                alt13=2;
                }
                break;
            case FORMULA:
                {
                alt13=3;
                }
                break;
            case OBSERVER:
                {
                alt13=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }

            switch (alt13) {
                case 1 :
                    // PRISMModelGrammar.g:224:0: global_var
                    {
                    pushFollow(FOLLOW_global_var_in_obs_declaration576);
                    global_var12=global_var();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addGlobalVariable(global_var12); }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:225:3: constant
                    {
                    pushFollow(FOLLOW_constant_in_obs_declaration591);
                    constant13=constant();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addConstant(constant13); }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:226:3: formula_decl
                    {
                    pushFollow(FOLLOW_formula_decl_in_obs_declaration606);
                    formula_decl14=formula_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addFormula(formula_decl14); }

                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:227:3: observer_decl
                    {
                    pushFollow(FOLLOW_observer_decl_in_obs_declaration617);
                    observer_decl15=observer_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { model.addModule(observer_decl15); }

                    }
                    break;

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
        return ;
    }
    // $ANTLR end "obs_declaration"



    // $ANTLR start "constant"
    // PRISMModelGrammar.g:230:1: constant returns [Constant cons] : csort IDENT ( EQ expr )? SEMI ;
    public final Constant constant() throws RecognitionException {
        Constant cons = null;


        Token IDENT17=null;
        Expr expr16 =null;

        Type csort18 =null;


         Expr init = null; 
        try {
            // PRISMModelGrammar.g:230:64: ( csort IDENT ( EQ expr )? SEMI )
            // PRISMModelGrammar.g:231:1: csort IDENT ( EQ expr )? SEMI
            {
            pushFollow(FOLLOW_csort_in_constant643);
            csort18=csort();

            state._fsp--;
            if (state.failed) return cons;

            IDENT17=(Token)match(input,IDENT,FOLLOW_IDENT_in_constant645); if (state.failed) return cons;

            // PRISMModelGrammar.g:232:2: ( EQ expr )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==EQ) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // PRISMModelGrammar.g:232:2: EQ expr
                    {
                    match(input,EQ,FOLLOW_EQ_in_constant674); if (state.failed) return cons;

                    pushFollow(FOLLOW_expr_in_constant676);
                    expr16=expr();

                    state._fsp--;
                    if (state.failed) return cons;

                    if ( state.backtracking==0 ) { init = expr16; }

                    }
                    break;

            }


            match(input,SEMI,FOLLOW_SEMI_in_constant714); if (state.failed) return cons;

            if ( state.backtracking==0 ) { cons = (init != null) ? factory.makeConstant((IDENT17!=null?IDENT17.getText():null), csort18, init)
                                                                             : factory.makeConstant((IDENT17!=null?IDENT17.getText():null), csort18);
                                                    }

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
        return cons;
    }
    // $ANTLR end "constant"



    // $ANTLR start "csort"
    // PRISMModelGrammar.g:239:1: csort returns [Type type] : ( CONST ( ctype )? | RATE | PROB );
    public final Type csort() throws RecognitionException {
        Type type = null;


        Type ctype19 =null;


        try {
            // PRISMModelGrammar.g:239:26: ( CONST ( ctype )? | RATE | PROB )
            int alt16=3;
            switch ( input.LA(1) ) {
            case CONST:
                {
                alt16=1;
                }
                break;
            case RATE:
                {
                alt16=2;
                }
                break;
            case PROB:
                {
                alt16=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return type;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // PRISMModelGrammar.g:240:0: CONST ( ctype )?
                    {
                    match(input,CONST,FOLLOW_CONST_in_csort761); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Integer; }

                    // PRISMModelGrammar.g:241:3: ( ctype )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==BOOL||LA15_0==DOUBLE||LA15_0==INT) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // PRISMModelGrammar.g:241:3: ctype
                            {
                            pushFollow(FOLLOW_ctype_in_csort801);
                            ctype19=ctype();

                            state._fsp--;
                            if (state.failed) return type;

                            if ( state.backtracking==0 ) { type = ctype19; }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:244:3: RATE
                    {
                    match(input,RATE,FOLLOW_RATE_in_csort844); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Double; }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:245:3: PROB
                    {
                    match(input,PROB,FOLLOW_PROB_in_csort884); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Double; }

                    }
                    break;

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
        return type;
    }
    // $ANTLR end "csort"



    // $ANTLR start "ctype"
    // PRISMModelGrammar.g:248:1: ctype returns [Type type] : ( INT | BOOL | DOUBLE );
    public final Type ctype() throws RecognitionException {
        Type type = null;


        try {
            // PRISMModelGrammar.g:248:26: ( INT | BOOL | DOUBLE )
            int alt17=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt17=1;
                }
                break;
            case BOOL:
                {
                alt17=2;
                }
                break;
            case DOUBLE:
                {
                alt17=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return type;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }

            switch (alt17) {
                case 1 :
                    // PRISMModelGrammar.g:249:0: INT
                    {
                    match(input,INT,FOLLOW_INT_in_ctype932); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Integer; }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:250:3: BOOL
                    {
                    match(input,BOOL,FOLLOW_BOOL_in_ctype974); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Boolean; }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:251:3: DOUBLE
                    {
                    match(input,DOUBLE,FOLLOW_DOUBLE_in_ctype1013); if (state.failed) return type;

                    if ( state.backtracking==0 ) { type = Type.Double;  }

                    }
                    break;

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
        return type;
    }
    // $ANTLR end "ctype"



    // $ANTLR start "global_var"
    // PRISMModelGrammar.g:254:1: global_var returns [Variable var] : GLOBAL var_declaration SEMI ;
    public final Variable global_var() throws RecognitionException {
        Variable var = null;


        Variable var_declaration20 =null;


        try {
            // PRISMModelGrammar.g:254:34: ( GLOBAL var_declaration SEMI )
            // PRISMModelGrammar.g:255:1: GLOBAL var_declaration SEMI
            {
            match(input,GLOBAL,FOLLOW_GLOBAL_in_global_var1059); if (state.failed) return var;

            pushFollow(FOLLOW_var_declaration_in_global_var1061);
            var_declaration20=var_declaration();

            state._fsp--;
            if (state.failed) return var;

            match(input,SEMI,FOLLOW_SEMI_in_global_var1063); if (state.failed) return var;

            if ( state.backtracking==0 ) { var = var_declaration20; }

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
        return var;
    }
    // $ANTLR end "global_var"



    // $ANTLR start "var_declaration"
    // PRISMModelGrammar.g:259:1: var_declaration returns [Variable var] : IDENT COL sort ( INIT expr )? ;
    public final Variable var_declaration() throws RecognitionException {
        Variable var = null;


        Token IDENT23=null;
        Expr expr21 =null;

        PRISMModelGrammarParser.sort_return sort22 =null;


         Expr init = null; 
        try {
            // PRISMModelGrammar.g:259:70: ( IDENT COL sort ( INIT expr )? )
            // PRISMModelGrammar.g:260:1: IDENT COL sort ( INIT expr )?
            {
            IDENT23=(Token)match(input,IDENT,FOLLOW_IDENT_in_var_declaration1099); if (state.failed) return var;

            match(input,COL,FOLLOW_COL_in_var_declaration1101); if (state.failed) return var;

            pushFollow(FOLLOW_sort_in_var_declaration1103);
            sort22=sort();

            state._fsp--;
            if (state.failed) return var;

            // PRISMModelGrammar.g:261:3: ( INIT expr )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==INIT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // PRISMModelGrammar.g:261:3: INIT expr
                    {
                    match(input,INIT,FOLLOW_INIT_in_var_declaration1108); if (state.failed) return var;

                    pushFollow(FOLLOW_expr_in_var_declaration1110);
                    expr21=expr();

                    state._fsp--;
                    if (state.failed) return var;

                    if ( state.backtracking==0 ) { init = expr21; }

                    }
                    break;

            }


            if ( state.backtracking==0 ) { if ((sort22!=null?sort22.lower:null) != null & (sort22!=null?sort22.upper:null) != null)
                                                        var = (init == null) ? factory.makeVariable((IDENT23!=null?IDENT23.getText():null), (sort22!=null?sort22.lower:null), (sort22!=null?sort22.upper:null))
                                                                             : factory.makeVariable((IDENT23!=null?IDENT23.getText():null), (sort22!=null?sort22.lower:null), (sort22!=null?sort22.upper:null), init);
                                                      else
                                                        var = (init == null) ? factory.makeVariable((IDENT23!=null?IDENT23.getText():null), (sort22!=null?sort22.type:null))
                                                                             : factory.makeVariable((IDENT23!=null?IDENT23.getText():null), (sort22!=null?sort22.type:null), init);
                                                    }

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
        return var;
    }
    // $ANTLR end "var_declaration"


    public static class sort_return extends ParserRuleReturnScope {
        public Type type;
        public Expr lower;
        public Expr upper;
    };


    // $ANTLR start "sort"
    // PRISMModelGrammar.g:271:1: sort returns [Type type, Expr lower, Expr upper] : ( BOOL | INT | DOUBLE | LB min= expr DDOT max= expr RB | CLOCK );
    public final PRISMModelGrammarParser.sort_return sort() throws RecognitionException {
        PRISMModelGrammarParser.sort_return retval = new PRISMModelGrammarParser.sort_return();
        retval.start = input.LT(1);


        Expr min =null;

        Expr max =null;


        try {
            // PRISMModelGrammar.g:271:50: ( BOOL | INT | DOUBLE | LB min= expr DDOT max= expr RB | CLOCK )
            int alt19=5;
            switch ( input.LA(1) ) {
            case BOOL:
                {
                alt19=1;
                }
                break;
            case INT:
                {
                alt19=2;
                }
                break;
            case DOUBLE:
                {
                alt19=3;
                }
                break;
            case LB:
                {
                alt19=4;
                }
                break;
            case CLOCK:
                {
                alt19=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }

            switch (alt19) {
                case 1 :
                    // PRISMModelGrammar.g:273:3: BOOL
                    {
                    match(input,BOOL,FOLLOW_BOOL_in_sort1198); if (state.failed) return retval;

                    if ( state.backtracking==0 ) { retval.type = Type.Boolean; retval.lower = null; retval.upper = null; }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:274:3: INT
                    {
                    match(input,INT,FOLLOW_INT_in_sort1237); if (state.failed) return retval;

                    if ( state.backtracking==0 ) { retval.type = Type.Integer; retval.lower = null; retval.upper = null; }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:275:3: DOUBLE
                    {
                    match(input,DOUBLE,FOLLOW_DOUBLE_in_sort1279); if (state.failed) return retval;

                    if ( state.backtracking==0 ) { retval.type = Type.Double; retval.lower = null; retval.upper = null; }

                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:276:3: LB min= expr DDOT max= expr RB
                    {
                    match(input,LB,FOLLOW_LB_in_sort1316); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_sort1320);
                    min=expr();

                    state._fsp--;
                    if (state.failed) return retval;

                    match(input,DDOT,FOLLOW_DDOT_in_sort1322); if (state.failed) return retval;

                    pushFollow(FOLLOW_expr_in_sort1326);
                    max=expr();

                    state._fsp--;
                    if (state.failed) return retval;

                    match(input,RB,FOLLOW_RB_in_sort1328); if (state.failed) return retval;

                    if ( state.backtracking==0 ) { retval.type = Type.Integer; retval.lower = min; retval.upper = max; }

                    }
                    break;
                case 5 :
                    // PRISMModelGrammar.g:277:3: CLOCK
                    {
                    match(input,CLOCK,FOLLOW_CLOCK_in_sort1343); if (state.failed) return retval;

                    if ( state.backtracking==0 ) { retval.type = Type.Clock; retval.lower = null; retval.upper = null; }

                    }
                    break;

            }
            retval.stop = input.LT(-1);


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
        return retval;
    }
    // $ANTLR end "sort"



    // $ANTLR start "formula_decl"
    // PRISMModelGrammar.g:281:1: formula_decl returns [Formula f] : FORMULA IDENT EQ expr SEMI ;
    public final Formula formula_decl() throws RecognitionException {
        Formula f = null;


        Token IDENT24=null;
        Expr expr25 =null;


        try {
            // PRISMModelGrammar.g:281:33: ( FORMULA IDENT EQ expr SEMI )
            // PRISMModelGrammar.g:282:1: FORMULA IDENT EQ expr SEMI
            {
            match(input,FORMULA,FOLLOW_FORMULA_in_formula_decl1391); if (state.failed) return f;

            IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_formula_decl1393); if (state.failed) return f;

            match(input,EQ,FOLLOW_EQ_in_formula_decl1395); if (state.failed) return f;

            pushFollow(FOLLOW_expr_in_formula_decl1397);
            expr25=expr();

            state._fsp--;
            if (state.failed) return f;

            match(input,SEMI,FOLLOW_SEMI_in_formula_decl1399); if (state.failed) return f;

            if ( state.backtracking==0 ) { f =factory.makeFormula((IDENT24!=null?IDENT24.getText():null),expr25); }

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
        return f;
    }
    // $ANTLR end "formula_decl"



    // $ANTLR start "label_decl"
    // PRISMModelGrammar.g:286:1: label_decl returns [Label l] : LABEL DQ IDENT DQ EQ expr SEMI ;
    public final Label label_decl() throws RecognitionException {
        Label l = null;


        Token IDENT26=null;
        Expr expr27 =null;


        try {
            // PRISMModelGrammar.g:286:29: ( LABEL DQ IDENT DQ EQ expr SEMI )
            // PRISMModelGrammar.g:287:1: LABEL DQ IDENT DQ EQ expr SEMI
            {
            match(input,LABEL,FOLLOW_LABEL_in_label_decl1427); if (state.failed) return l;

            match(input,DQ,FOLLOW_DQ_in_label_decl1429); if (state.failed) return l;

            IDENT26=(Token)match(input,IDENT,FOLLOW_IDENT_in_label_decl1431); if (state.failed) return l;

            match(input,DQ,FOLLOW_DQ_in_label_decl1433); if (state.failed) return l;

            match(input,EQ,FOLLOW_EQ_in_label_decl1435); if (state.failed) return l;

            pushFollow(FOLLOW_expr_in_label_decl1437);
            expr27=expr();

            state._fsp--;
            if (state.failed) return l;

            match(input,SEMI,FOLLOW_SEMI_in_label_decl1439); if (state.failed) return l;

            if ( state.backtracking==0 ) { l =factory.makeLabel((IDENT26!=null?IDENT26.getText():null) ,expr27); }

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
        return l;
    }
    // $ANTLR end "label_decl"



    // $ANTLR start "init_decl"
    // PRISMModelGrammar.g:291:1: init_decl returns [Expr e] : INIT expr ENDINIT ;
    public final Expr init_decl() throws RecognitionException {
        Expr e = null;


        Expr expr28 =null;


        try {
            // PRISMModelGrammar.g:291:27: ( INIT expr ENDINIT )
            // PRISMModelGrammar.g:292:1: INIT expr ENDINIT
            {
            match(input,INIT,FOLLOW_INIT_in_init_decl1463); if (state.failed) return e;

            pushFollow(FOLLOW_expr_in_init_decl1465);
            expr28=expr();

            state._fsp--;
            if (state.failed) return e;

            match(input,ENDINIT,FOLLOW_ENDINIT_in_init_decl1467); if (state.failed) return e;

            if ( state.backtracking==0 ) { e = expr28; }

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
        return e;
    }
    // $ANTLR end "init_decl"



    // $ANTLR start "reward_decl"
    // PRISMModelGrammar.g:296:1: reward_decl returns [Reward r] : REWARDS ( DQ IDENT DQ )? ( reward_statement )* ENDREWARDS ;
    public final Reward reward_decl() throws RecognitionException {
        Reward r = null;


        Token IDENT29=null;
        StateReward reward_statement30 =null;


         String name = ""; 
        try {
            // PRISMModelGrammar.g:296:63: ( REWARDS ( DQ IDENT DQ )? ( reward_statement )* ENDREWARDS )
            // PRISMModelGrammar.g:298:1: REWARDS ( DQ IDENT DQ )? ( reward_statement )* ENDREWARDS
            {
            match(input,REWARDS,FOLLOW_REWARDS_in_reward_decl1514); if (state.failed) return r;

            // PRISMModelGrammar.g:299:3: ( DQ IDENT DQ )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==DQ) ) {
                int LA20_1 = input.LA(2);

                if ( (synpred41_PRISMModelGrammar()) ) {
                    alt20=1;
                }
            }
            switch (alt20) {
                case 1 :
                    // PRISMModelGrammar.g:299:3: DQ IDENT DQ
                    {
                    match(input,DQ,FOLLOW_DQ_in_reward_decl1519); if (state.failed) return r;

                    IDENT29=(Token)match(input,IDENT,FOLLOW_IDENT_in_reward_decl1521); if (state.failed) return r;

                    match(input,DQ,FOLLOW_DQ_in_reward_decl1523); if (state.failed) return r;

                    if ( state.backtracking==0 ) { name = (IDENT29!=null?IDENT29.getText():null); }

                    }
                    break;

            }


            if ( state.backtracking==0 ) { r =factory.makeReward(name); }

            // PRISMModelGrammar.g:301:3: ( reward_statement )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==CEIL||LA21_0==DOT||LA21_0==DQ||(LA21_0 >= FALSE && LA21_0 <= FLOOR)||LA21_0==FUNC||LA21_0==IDENT||LA21_0==INTVAL||LA21_0==LB||(LA21_0 >= LOG && LA21_0 <= LP)||LA21_0==MAXI||(LA21_0 >= MIN && LA21_0 <= MOD)||LA21_0==NOT||(LA21_0 >= POW && LA21_0 <= PREFIDENT)||(LA21_0 >= TRUE && LA21_0 <= VALEXP)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // PRISMModelGrammar.g:301:3: reward_statement
            	    {
            	    pushFollow(FOLLOW_reward_statement_in_reward_decl1598);
            	    reward_statement30=reward_statement();

            	    state._fsp--;
            	    if (state.failed) return r;

            	    if ( state.backtracking==0 ) { r.addRewardStatement(reward_statement30); }

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            match(input,ENDREWARDS,FOLLOW_ENDREWARDS_in_reward_decl1644); if (state.failed) return r;

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
        return r;
    }
    // $ANTLR end "reward_decl"



    // $ANTLR start "reward_statement"
    // PRISMModelGrammar.g:306:1: reward_statement returns [StateReward r] : ( state_reward | trans_reward );
    public final StateReward reward_statement() throws RecognitionException {
        StateReward r = null;


        StateReward state_reward31 =null;

        TransitionReward trans_reward32 =null;


        try {
            // PRISMModelGrammar.g:306:42: ( state_reward | trans_reward )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==CEIL||LA22_0==DOT||LA22_0==DQ||(LA22_0 >= FALSE && LA22_0 <= FLOOR)||LA22_0==FUNC||LA22_0==IDENT||LA22_0==INTVAL||(LA22_0 >= LOG && LA22_0 <= LP)||LA22_0==MAXI||(LA22_0 >= MIN && LA22_0 <= MOD)||LA22_0==NOT||(LA22_0 >= POW && LA22_0 <= PREFIDENT)||(LA22_0 >= TRUE && LA22_0 <= VALEXP)) ) {
                alt22=1;
            }
            else if ( (LA22_0==LB) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return r;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }
            switch (alt22) {
                case 1 :
                    // PRISMModelGrammar.g:307:0: state_reward
                    {
                    pushFollow(FOLLOW_state_reward_in_reward_statement1657);
                    state_reward31=state_reward();

                    state._fsp--;
                    if (state.failed) return r;

                    if ( state.backtracking==0 ) { r = state_reward31; }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:308:3: trans_reward
                    {
                    pushFollow(FOLLOW_trans_reward_in_reward_statement1690);
                    trans_reward32=trans_reward();

                    state._fsp--;
                    if (state.failed) return r;

                    if ( state.backtracking==0 ) { r = trans_reward32; }

                    }
                    break;

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
        return r;
    }
    // $ANTLR end "reward_statement"



    // $ANTLR start "state_reward"
    // PRISMModelGrammar.g:311:1: state_reward returns [StateReward r] : cond= expr COL rew= expr SEMI ;
    public final StateReward state_reward() throws RecognitionException {
        StateReward r = null;


        Expr cond =null;

        Expr rew =null;


        try {
            // PRISMModelGrammar.g:311:37: (cond= expr COL rew= expr SEMI )
            // PRISMModelGrammar.g:312:5: cond= expr COL rew= expr SEMI
            {
            pushFollow(FOLLOW_expr_in_state_reward1731);
            cond=expr();

            state._fsp--;
            if (state.failed) return r;

            match(input,COL,FOLLOW_COL_in_state_reward1733); if (state.failed) return r;

            pushFollow(FOLLOW_expr_in_state_reward1737);
            rew=expr();

            state._fsp--;
            if (state.failed) return r;

            match(input,SEMI,FOLLOW_SEMI_in_state_reward1739); if (state.failed) return r;

            if ( state.backtracking==0 ) { r = new StateReward(cond,rew); }

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
        return r;
    }
    // $ANTLR end "state_reward"



    // $ANTLR start "trans_reward"
    // PRISMModelGrammar.g:315:1: trans_reward returns [TransitionReward r] : synchro cond= expr COL rew= expr SEMI ;
    public final TransitionReward trans_reward() throws RecognitionException {
        TransitionReward r = null;


        Expr cond =null;

        Expr rew =null;

        String synchro33 =null;


        try {
            // PRISMModelGrammar.g:315:43: ( synchro cond= expr COL rew= expr SEMI )
            // PRISMModelGrammar.g:316:1: synchro cond= expr COL rew= expr SEMI
            {
            pushFollow(FOLLOW_synchro_in_trans_reward1766);
            synchro33=synchro();

            state._fsp--;
            if (state.failed) return r;

            pushFollow(FOLLOW_expr_in_trans_reward1770);
            cond=expr();

            state._fsp--;
            if (state.failed) return r;

            match(input,COL,FOLLOW_COL_in_trans_reward1772); if (state.failed) return r;

            pushFollow(FOLLOW_expr_in_trans_reward1776);
            rew=expr();

            state._fsp--;
            if (state.failed) return r;

            match(input,SEMI,FOLLOW_SEMI_in_trans_reward1778); if (state.failed) return r;

            if ( state.backtracking==0 ) { r = new TransitionReward(synchro33,cond,rew); }

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
        return r;
    }
    // $ANTLR end "trans_reward"



    // $ANTLR start "module_decl"
    // PRISMModelGrammar.g:320:1: module_decl returns [Module m] : MODULE IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( local_var )* ( invariant )? ( command )* ENDMODULE ;
    public final Module module_decl() throws RecognitionException {
        Module m = null;


        Token IDENT34=null;
        Parameter p1 =null;

        Parameter p2 =null;

        Variable local_var35 =null;


        try {
            // PRISMModelGrammar.g:320:32: ( MODULE IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( local_var )* ( invariant )? ( command )* ENDMODULE )
            // PRISMModelGrammar.g:321:1: MODULE IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( local_var )* ( invariant )? ( command )* ENDMODULE
            {
            match(input,MODULE,FOLLOW_MODULE_in_module_decl1798); if (state.failed) return m;

            IDENT34=(Token)match(input,IDENT,FOLLOW_IDENT_in_module_decl1800); if (state.failed) return m;

            if ( state.backtracking==0 ) { m = factory.openModule((IDENT34!=null?IDENT34.getText():null)); }

            // PRISMModelGrammar.g:322:3: ( LP p1= parameter ( COMMA p2= parameter )* RP )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LP) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // PRISMModelGrammar.g:322:3: LP p1= parameter ( COMMA p2= parameter )* RP
                    {
                    match(input,LP,FOLLOW_LP_in_module_decl1833); if (state.failed) return m;

                    pushFollow(FOLLOW_parameter_in_module_decl1839);
                    p1=parameter();

                    state._fsp--;
                    if (state.failed) return m;

                    if ( state.backtracking==0 ) { m.addParameter(p1); }

                    // PRISMModelGrammar.g:324:3: ( COMMA p2= parameter )*
                    loop23:
                    do {
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==COMMA) ) {
                            alt23=1;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // PRISMModelGrammar.g:324:5: COMMA p2= parameter
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_module_decl1872); if (state.failed) return m;

                    	    pushFollow(FOLLOW_parameter_in_module_decl1876);
                    	    p2=parameter();

                    	    state._fsp--;
                    	    if (state.failed) return m;

                    	    if ( state.backtracking==0 ) { m.addParameter(p2); }

                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);


                    match(input,RP,FOLLOW_RP_in_module_decl1924); if (state.failed) return m;

                    }
                    break;

            }


            // PRISMModelGrammar.g:328:3: ( local_var )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==IDENT) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // PRISMModelGrammar.g:328:3: local_var
            	    {
            	    pushFollow(FOLLOW_local_var_in_module_decl1931);
            	    local_var35=local_var();

            	    state._fsp--;
            	    if (state.failed) return m;

            	    if ( state.backtracking==0 ) { m.addLocalVariable(local_var35); }

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            // PRISMModelGrammar.g:330:3: ( invariant )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==INVARIANT) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // PRISMModelGrammar.g:330:3: invariant
                    {
                    pushFollow(FOLLOW_invariant_in_module_decl1968);
                    invariant();

                    state._fsp--;
                    if (state.failed) return m;

                    if ( state.backtracking==0 ) { }

                    }
                    break;

            }


            // PRISMModelGrammar.g:332:3: ( command )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==LB) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // PRISMModelGrammar.g:332:3: command
            	    {
            	    pushFollow(FOLLOW_command_in_module_decl2006);
            	    command();

            	    state._fsp--;
            	    if (state.failed) return m;

            	    if ( state.backtracking==0 ) { factory.addCommand(); }

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            match(input,ENDMODULE,FOLLOW_ENDMODULE_in_module_decl2043); if (state.failed) return m;

            if ( state.backtracking==0 ) { factory.closeModule(); }

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
        return m;
    }
    // $ANTLR end "module_decl"



    // $ANTLR start "observer_decl"
    // PRISMModelGrammar.g:337:1: observer_decl returns [Module m] : OBSERVER IDENT ( local_var )* ( obs_command )* ENDOBSERVER ;
    public final Module observer_decl() throws RecognitionException {
        Module m = null;


        Token IDENT36=null;
        Variable local_var37 =null;


        try {
            // PRISMModelGrammar.g:337:34: ( OBSERVER IDENT ( local_var )* ( obs_command )* ENDOBSERVER )
            // PRISMModelGrammar.g:338:1: OBSERVER IDENT ( local_var )* ( obs_command )* ENDOBSERVER
            {
            match(input,OBSERVER,FOLLOW_OBSERVER_in_observer_decl2088); if (state.failed) return m;

            IDENT36=(Token)match(input,IDENT,FOLLOW_IDENT_in_observer_decl2090); if (state.failed) return m;

            if ( state.backtracking==0 ) { m = factory.openModule((IDENT36!=null?IDENT36.getText():null));
                                                      obsModules.add(m);
                                                    }

            // PRISMModelGrammar.g:341:3: ( local_var )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==IDENT) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // PRISMModelGrammar.g:341:3: local_var
            	    {
            	    pushFollow(FOLLOW_local_var_in_observer_decl2121);
            	    local_var37=local_var();

            	    state._fsp--;
            	    if (state.failed) return m;

            	    if ( state.backtracking==0 ) { m.addLocalVariable(local_var37); }

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            // PRISMModelGrammar.g:343:3: ( obs_command )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==LB) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // PRISMModelGrammar.g:343:3: obs_command
            	    {
            	    pushFollow(FOLLOW_obs_command_in_observer_decl2158);
            	    obs_command();

            	    state._fsp--;
            	    if (state.failed) return m;

            	    if ( state.backtracking==0 ) { factory.addCommand(); }

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            match(input,ENDOBSERVER,FOLLOW_ENDOBSERVER_in_observer_decl2191); if (state.failed) return m;

            if ( state.backtracking==0 ) { factory.closeModule(); }

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
        return m;
    }
    // $ANTLR end "observer_decl"



    // $ANTLR start "parameter"
    // PRISMModelGrammar.g:348:1: parameter returns [Parameter param] : ( csort IDENT | sort IDENT );
    public final Parameter parameter() throws RecognitionException {
        Parameter param = null;


        Token IDENT38=null;
        Token IDENT41=null;
        Type csort39 =null;

        PRISMModelGrammarParser.sort_return sort40 =null;


        try {
            // PRISMModelGrammar.g:348:36: ( csort IDENT | sort IDENT )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==CONST||LA30_0==PROB||LA30_0==RATE) ) {
                alt30=1;
            }
            else if ( (LA30_0==BOOL||LA30_0==CLOCK||LA30_0==DOUBLE||LA30_0==INT||LA30_0==LB) ) {
                alt30=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return param;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }
            switch (alt30) {
                case 1 :
                    // PRISMModelGrammar.g:349:0: csort IDENT
                    {
                    pushFollow(FOLLOW_csort_in_parameter2234);
                    csort39=csort();

                    state._fsp--;
                    if (state.failed) return param;

                    IDENT38=(Token)match(input,IDENT,FOLLOW_IDENT_in_parameter2236); if (state.failed) return param;

                    if ( state.backtracking==0 ) { param = factory.makeParameter((IDENT38!=null?IDENT38.getText():null), csort39,true); }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:350:3: sort IDENT
                    {
                    pushFollow(FOLLOW_sort_in_parameter2272);
                    sort40=sort();

                    state._fsp--;
                    if (state.failed) return param;

                    IDENT41=(Token)match(input,IDENT,FOLLOW_IDENT_in_parameter2274); if (state.failed) return param;

                    if ( state.backtracking==0 ) { if ((sort40!=null?sort40.lower:null) != null & (sort40!=null?sort40.upper:null) != null)
                                                                  param = factory.makeParameter((IDENT41!=null?IDENT41.getText():null), (sort40!=null?sort40.lower:null), (sort40!=null?sort40.upper:null), false);
                                                                else
                                                                  param = factory.makeParameter((IDENT41!=null?IDENT41.getText():null), (sort40!=null?sort40.type:null), false);
                                                              }

                    }
                    break;

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
        return param;
    }
    // $ANTLR end "parameter"



    // $ANTLR start "module_instance"
    // PRISMModelGrammar.g:358:1: module_instance returns [ModuleInstance m] : MODULE newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )? ENDMODULE ;
    public final ModuleInstance module_instance() throws RecognitionException {
        ModuleInstance m = null;


        Token newident=null;
        Token oldident=null;
        List<Expr> exprs42 =null;


         Map<String,String> ren = new HashMap<String,String>();
                                                          List<Expr> params = null; 
        try {
            // PRISMModelGrammar.g:359:78: ( MODULE newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )? ENDMODULE )
            // PRISMModelGrammar.g:360:1: MODULE newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )? ENDMODULE
            {
            match(input,MODULE,FOLLOW_MODULE_in_module_instance2322); if (state.failed) return m;

            newident=(Token)match(input,IDENT,FOLLOW_IDENT_in_module_instance2326); if (state.failed) return m;

            match(input,EQ,FOLLOW_EQ_in_module_instance2328); if (state.failed) return m;

            oldident=(Token)match(input,IDENT,FOLLOW_IDENT_in_module_instance2332); if (state.failed) return m;

            // PRISMModelGrammar.g:361:3: ( LP exprs RP )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==LP) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // PRISMModelGrammar.g:361:3: LP exprs RP
                    {
                    match(input,LP,FOLLOW_LP_in_module_instance2339); if (state.failed) return m;

                    pushFollow(FOLLOW_exprs_in_module_instance2341);
                    exprs42=exprs();

                    state._fsp--;
                    if (state.failed) return m;

                    match(input,RP,FOLLOW_RP_in_module_instance2343); if (state.failed) return m;

                    if ( state.backtracking==0 ) { params = exprs42; }

                    }
                    break;

            }


            // PRISMModelGrammar.g:363:3: ( LB renamings[ren] RB )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==LB) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // PRISMModelGrammar.g:363:3: LB renamings[ren] RB
                    {
                    match(input,LB,FOLLOW_LB_in_module_instance2388); if (state.failed) return m;

                    pushFollow(FOLLOW_renamings_in_module_instance2390);
                    renamings(ren);

                    state._fsp--;
                    if (state.failed) return m;

                    match(input,RB,FOLLOW_RB_in_module_instance2393); if (state.failed) return m;

                    }
                    break;

            }


            if ( state.backtracking==0 ) { m = new ModuleInstance((newident!=null?newident.getText():null), (oldident!=null?oldident.getText():null), ren, params); }

            match(input,ENDMODULE,FOLLOW_ENDMODULE_in_module_instance2422); if (state.failed) return m;

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
        return m;
    }
    // $ANTLR end "module_instance"



    // $ANTLR start "renamings"
    // PRISMModelGrammar.g:367:1: renamings[Map<String,String> map] : r1= renaming ( COMMA r2= renaming )* ;
    public final void renamings(Map<String,String> map) throws RecognitionException {
        String[] r1 =null;

        String[] r2 =null;


        try {
            // PRISMModelGrammar.g:367:35: (r1= renaming ( COMMA r2= renaming )* )
            // PRISMModelGrammar.g:368:3: r1= renaming ( COMMA r2= renaming )*
            {
            pushFollow(FOLLOW_renaming_in_renamings2467);
            r1=renaming();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { map.put(r1[0],r1[1]); }

            // PRISMModelGrammar.g:369:3: ( COMMA r2= renaming )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // PRISMModelGrammar.g:369:3: COMMA r2= renaming
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_renamings2511); if (state.failed) return ;

            	    pushFollow(FOLLOW_renaming_in_renamings2515);
            	    r2=renaming();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    if ( state.backtracking==0 ) { map.put(r2[0],r2[1]); }

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);


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
        return ;
    }
    // $ANTLR end "renamings"



    // $ANTLR start "renaming"
    // PRISMModelGrammar.g:373:1: renaming returns [String[] r] : id1= IDENT EQ id2= ( IDENT | PREFIDENT ) ;
    public final String[] renaming() throws RecognitionException {
        String[] r = null;


        Token id1=null;
        Token id2=null;

        try {
            // PRISMModelGrammar.g:373:32: (id1= IDENT EQ id2= ( IDENT | PREFIDENT ) )
            // PRISMModelGrammar.g:374:4: id1= IDENT EQ id2= ( IDENT | PREFIDENT )
            {
            id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_renaming2566); if (state.failed) return r;

            match(input,EQ,FOLLOW_EQ_in_renaming2568); if (state.failed) return r;

            id2=(Token)input.LT(1);

            if ( input.LA(1)==IDENT||input.LA(1)==PREFIDENT ) {
                input.consume();
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return r;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            if ( state.backtracking==0 ) { r = new String[2]; r[0] = (id1!=null?id1.getText():null); r[1] = (id2!=null?id2.getText():null); }

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
        return r;
    }
    // $ANTLR end "renaming"



    // $ANTLR start "local_var"
    // PRISMModelGrammar.g:378:1: local_var returns [Variable var] : var_declaration SEMI ;
    public final Variable local_var() throws RecognitionException {
        Variable var = null;


        Variable var_declaration43 =null;


        try {
            // PRISMModelGrammar.g:378:33: ( var_declaration SEMI )
            // PRISMModelGrammar.g:379:1: var_declaration SEMI
            {
            pushFollow(FOLLOW_var_declaration_in_local_var2606);
            var_declaration43=var_declaration();

            state._fsp--;
            if (state.failed) return var;

            match(input,SEMI,FOLLOW_SEMI_in_local_var2608); if (state.failed) return var;

            if ( state.backtracking==0 ) { var = var_declaration43; }

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
        return var;
    }
    // $ANTLR end "local_var"



    // $ANTLR start "invariant"
    // PRISMModelGrammar.g:382:1: invariant returns [Expr e] : INVARIANT expr ENDINVARIANT ;
    public final Expr invariant() throws RecognitionException {
        Expr e = null;


        Expr expr44 =null;


        try {
            // PRISMModelGrammar.g:382:27: ( INVARIANT expr ENDINVARIANT )
            // PRISMModelGrammar.g:383:1: INVARIANT expr ENDINVARIANT
            {
            match(input,INVARIANT,FOLLOW_INVARIANT_in_invariant2644); if (state.failed) return e;

            pushFollow(FOLLOW_expr_in_invariant2646);
            expr44=expr();

            state._fsp--;
            if (state.failed) return e;

            match(input,ENDINVARIANT,FOLLOW_ENDINVARIANT_in_invariant2648); if (state.failed) return e;

            if ( state.backtracking==0 ) { e = expr44; }

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
        return e;
    }
    // $ANTLR end "invariant"



    // $ANTLR start "command"
    // PRISMModelGrammar.g:386:1: command : synchro expr RARROW actions_set SEMI ;
    public final void command() throws RecognitionException {
        String synchro45 =null;

        Expr expr46 =null;


        try {
            // PRISMModelGrammar.g:386:8: ( synchro expr RARROW actions_set SEMI )
            // PRISMModelGrammar.g:387:1: synchro expr RARROW actions_set SEMI
            {
            pushFollow(FOLLOW_synchro_in_command2672);
            synchro45=synchro();

            state._fsp--;
            if (state.failed) return ;

            pushFollow(FOLLOW_expr_in_command2674);
            expr46=expr();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { if (isSampled)
             												factory.makeSamplingCommand(synchro45, expr46);
             											else
             												factory.makeCommand(synchro45, expr46); 
             										  }

            match(input,RARROW,FOLLOW_RARROW_in_command2696); if (state.failed) return ;

            pushFollow(FOLLOW_actions_set_in_command2698);
            actions_set();

            state._fsp--;
            if (state.failed) return ;

            match(input,SEMI,FOLLOW_SEMI_in_command2700); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "command"



    // $ANTLR start "sampling_param"
    // PRISMModelGrammar.g:395:1: sampling_param returns [Expr e] : LBC expr RBC ;
    public final Expr sampling_param() throws RecognitionException {
        Expr e = null;


        Expr expr47 =null;


        try {
            // PRISMModelGrammar.g:395:32: ( LBC expr RBC )
            // PRISMModelGrammar.g:396:1: LBC expr RBC
            {
            match(input,LBC,FOLLOW_LBC_in_sampling_param2712); if (state.failed) return e;

            pushFollow(FOLLOW_expr_in_sampling_param2714);
            expr47=expr();

            state._fsp--;
            if (state.failed) return e;

            match(input,RBC,FOLLOW_RBC_in_sampling_param2716); if (state.failed) return e;

            if ( state.backtracking==0 ) { e = expr47; }

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
        return e;
    }
    // $ANTLR end "sampling_param"



    // $ANTLR start "synchro"
    // PRISMModelGrammar.g:399:1: synchro returns [String id] : LB ( IDENT )? RB ;
    public final String synchro() throws RecognitionException {
        String id = null;


        Token IDENT48=null;

         id = ""; 
        try {
            // PRISMModelGrammar.g:399:47: ( LB ( IDENT )? RB )
            // PRISMModelGrammar.g:400:1: LB ( IDENT )? RB
            {
            match(input,LB,FOLLOW_LB_in_synchro2743); if (state.failed) return id;

            // PRISMModelGrammar.g:401:3: ( IDENT )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==IDENT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // PRISMModelGrammar.g:401:3: IDENT
                    {
                    IDENT48=(Token)match(input,IDENT,FOLLOW_IDENT_in_synchro2747); if (state.failed) return id;

                    if ( state.backtracking==0 ) { id = (IDENT48!=null?IDENT48.getText():null); }

                    }
                    break;

            }


            match(input,RB,FOLLOW_RB_in_synchro2788); if (state.failed) return id;

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
        return id;
    }
    // $ANTLR end "synchro"



    // $ANTLR start "obs_command"
    // PRISMModelGrammar.g:406:1: obs_command : LB RB expr RARROW updates SEMI ;
    public final void obs_command() throws RecognitionException {
        Expr expr49 =null;


        try {
            // PRISMModelGrammar.g:406:12: ( LB RB expr RARROW updates SEMI )
            // PRISMModelGrammar.g:407:1: LB RB expr RARROW updates SEMI
            {
            match(input,LB,FOLLOW_LB_in_obs_command2796); if (state.failed) return ;

            match(input,RB,FOLLOW_RB_in_obs_command2798); if (state.failed) return ;

            pushFollow(FOLLOW_expr_in_obs_command2800);
            expr49=expr();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { factory.makeCommand("",expr49); }

            match(input,RARROW,FOLLOW_RARROW_in_obs_command2835); if (state.failed) return ;

            if ( state.backtracking==0 ) { factory.makeAction(factory.makeValue(1.0)); }

            pushFollow(FOLLOW_updates_in_obs_command2850);
            updates();

            state._fsp--;
            if (state.failed) return ;

            match(input,SEMI,FOLLOW_SEMI_in_obs_command2852); if (state.failed) return ;

            if ( state.backtracking==0 ) { factory.addAction(); }

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
        return ;
    }
    // $ANTLR end "obs_command"



    // $ANTLR start "actions_set"
    // PRISMModelGrammar.g:413:1: actions_set : ( actions | ( updates ) );
    public final void actions_set() throws RecognitionException {
          // initialize a default action. Overwritten in the action rule.
        					if (isSampled)
        						factory.makeSamplingAction(factory.makeValue(1.0),null);
        					else	
        						factory.makeAction(factory.makeValue(1.0)); 
        				 
        try {
            // PRISMModelGrammar.g:418:7: ( actions | ( updates ) )
            int alt35=2;
            switch ( input.LA(1) ) {
            case CEIL:
            case DOT:
            case DQ:
            case FALSE:
            case FLOOR:
            case FUNC:
            case IDENT:
            case INTVAL:
            case LBC:
            case LOG:
            case MAXI:
            case MIN:
            case MINI:
            case MOD:
            case NOT:
            case POW:
            case PREFIDENT:
            case VALEXP:
                {
                alt35=1;
                }
                break;
            case TRUE:
                {
                int LA35_2 = input.LA(2);

                if ( ((LA35_2 >= ADD && LA35_2 <= AND)||LA35_2==COL||LA35_2==DIV||LA35_2==EQ||LA35_2==GE||LA35_2==GT||(LA35_2 >= IF && LA35_2 <= IMP)||LA35_2==LE||LA35_2==LT||LA35_2==MIN||(LA35_2 >= MUL && LA35_2 <= NEQ)||LA35_2==OR) ) {
                    alt35=1;
                }
                else if ( (LA35_2==SEMI) ) {
                    alt35=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 2, input);

                    throw nvae;

                }
                }
                break;
            case LP:
                {
                int LA35_3 = input.LA(2);

                if ( (LA35_3==IDENT) ) {
                    int LA35_5 = input.LA(3);

                    if ( (LA35_5==PRIME) ) {
                        alt35=2;
                    }
                    else if ( ((LA35_5 >= ADD && LA35_5 <= AND)||LA35_5==DIV||LA35_5==EQ||LA35_5==GE||LA35_5==GT||(LA35_5 >= IF && LA35_5 <= IMP)||LA35_5==LE||LA35_5==LT||LA35_5==MIN||(LA35_5 >= MUL && LA35_5 <= NEQ)||LA35_5==OR||LA35_5==RP) ) {
                        alt35=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA35_3==CEIL||LA35_3==DOT||LA35_3==DQ||(LA35_3 >= FALSE && LA35_3 <= FLOOR)||LA35_3==FUNC||LA35_3==INTVAL||(LA35_3 >= LOG && LA35_3 <= LP)||LA35_3==MAXI||(LA35_3 >= MIN && LA35_3 <= MOD)||LA35_3==NOT||(LA35_3 >= POW && LA35_3 <= PREFIDENT)||(LA35_3 >= TRUE && LA35_3 <= VALEXP)) ) {
                    alt35=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 3, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;

            }

            switch (alt35) {
                case 1 :
                    // PRISMModelGrammar.g:419:0: actions
                    {
                    pushFollow(FOLLOW_actions_in_actions_set2876);
                    actions();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:420:3: ( updates )
                    {
                    // PRISMModelGrammar.g:420:3: ( updates )
                    // PRISMModelGrammar.g:420:5: updates
                    {
                    pushFollow(FOLLOW_updates_in_actions_set2882);
                    updates();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { factory.addAction(); }

                    }


                    }
                    break;

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
        return ;
    }
    // $ANTLR end "actions_set"



    // $ANTLR start "actions"
    // PRISMModelGrammar.g:424:1: actions : action ( ADD action )* ;
    public final void actions() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:424:8: ( action ( ADD action )* )
            // PRISMModelGrammar.g:425:1: action ( ADD action )*
            {
            pushFollow(FOLLOW_action_in_actions2907);
            action();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { factory.addAction(); }

            // PRISMModelGrammar.g:426:3: ( ADD action )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==ADD) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // PRISMModelGrammar.g:426:3: ADD action
            	    {
            	    match(input,ADD,FOLLOW_ADD_in_actions2950); if (state.failed) return ;

            	    pushFollow(FOLLOW_action_in_actions2952);
            	    action();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    if ( state.backtracking==0 ) { factory.addAction(); }

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


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
        return ;
    }
    // $ANTLR end "actions"



    // $ANTLR start "action"
    // PRISMModelGrammar.g:430:1: action : ( sampling_param )? expr COL updates ;
    public final void action() throws RecognitionException {
        Expr sampling_param50 =null;

        Expr expr51 =null;


         Expr sample = null; 
        try {
            // PRISMModelGrammar.g:430:36: ( ( sampling_param )? expr COL updates )
            // PRISMModelGrammar.g:431:3: ( sampling_param )? expr COL updates
            {
            // PRISMModelGrammar.g:431:3: ( sampling_param )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==LBC) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // PRISMModelGrammar.g:431:3: sampling_param
                    {
                    pushFollow(FOLLOW_sampling_param_in_action3000);
                    sampling_param50=sampling_param();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { sample = sampling_param50; }

                    }
                    break;

            }


            pushFollow(FOLLOW_expr_in_action3016);
            expr51=expr();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { if (isSampled)
             												factory.makeSamplingAction(expr51, sample);
             											else
             												factory.makeAction(expr51); 
             										  }

            match(input,COL,FOLLOW_COL_in_action3048); if (state.failed) return ;

            pushFollow(FOLLOW_updates_in_action3050);
            updates();

            state._fsp--;
            if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "action"



    // $ANTLR start "updates"
    // PRISMModelGrammar.g:441:1: updates : ( update ( AND update )* | TRUE );
    public final void updates() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:441:8: ( update ( AND update )* | TRUE )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==LP) ) {
                alt39=1;
            }
            else if ( (LA39_0==TRUE) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;

            }
            switch (alt39) {
                case 1 :
                    // PRISMModelGrammar.g:442:0: update ( AND update )*
                    {
                    pushFollow(FOLLOW_update_in_updates3058);
                    update();

                    state._fsp--;
                    if (state.failed) return ;

                    // PRISMModelGrammar.g:442:8: ( AND update )*
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( (LA38_0==AND) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // PRISMModelGrammar.g:442:10: AND update
                    	    {
                    	    match(input,AND,FOLLOW_AND_in_updates3062); if (state.failed) return ;

                    	    pushFollow(FOLLOW_update_in_updates3064);
                    	    update();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:443:3: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_updates3070); if (state.failed) return ;

                    }
                    break;

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
        return ;
    }
    // $ANTLR end "updates"



    // $ANTLR start "update"
    // PRISMModelGrammar.g:446:1: update : LP IDENT PRIME EQ expr RP ;
    public final void update() throws RecognitionException {
        Token IDENT52=null;
        Expr expr53 =null;


        try {
            // PRISMModelGrammar.g:446:7: ( LP IDENT PRIME EQ expr RP )
            // PRISMModelGrammar.g:447:1: LP IDENT PRIME EQ expr RP
            {
            match(input,LP,FOLLOW_LP_in_update3078); if (state.failed) return ;

            IDENT52=(Token)match(input,IDENT,FOLLOW_IDENT_in_update3080); if (state.failed) return ;

            match(input,PRIME,FOLLOW_PRIME_in_update3082); if (state.failed) return ;

            match(input,EQ,FOLLOW_EQ_in_update3084); if (state.failed) return ;

            pushFollow(FOLLOW_expr_in_update3086);
            expr53=expr();

            state._fsp--;
            if (state.failed) return ;

            match(input,RP,FOLLOW_RP_in_update3088); if (state.failed) return ;

            if ( state.backtracking==0 ) { factory.addAssignation((IDENT52!=null?IDENT52.getText():null),expr53); }

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
        return ;
    }
    // $ANTLR end "update"



    // $ANTLR start "expr"
    // PRISMModelGrammar.g:451:1: expr returns [Expr e] : imply_expr ( IF th= expr COL el= expr )? ;
    public final Expr expr() throws RecognitionException {
        Expr e = null;


        Expr th =null;

        Expr el =null;

        Expr imply_expr54 =null;


        try {
            // PRISMModelGrammar.g:451:23: ( imply_expr ( IF th= expr COL el= expr )? )
            // PRISMModelGrammar.g:452:1: imply_expr ( IF th= expr COL el= expr )?
            {
            pushFollow(FOLLOW_imply_expr_in_expr3120);
            imply_expr54=imply_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = imply_expr54; }

            // PRISMModelGrammar.g:453:2: ( IF th= expr COL el= expr )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==IF) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // PRISMModelGrammar.g:453:2: IF th= expr COL el= expr
                    {
                    match(input,IF,FOLLOW_IF_in_expr3157); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_expr3161);
                    th=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,COL,FOLLOW_COL_in_expr3163); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_expr3167);
                    el=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeITEExpr(e, th, el); }

                    }
                    break;

            }


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
        return e;
    }
    // $ANTLR end "expr"



    // $ANTLR start "imply_expr"
    // PRISMModelGrammar.g:457:1: imply_expr returns [Expr e] : fst= iff_expr ( IMP nxt= iff_expr )* ;
    public final Expr imply_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


         Stack<Expr> stack = new Stack<Expr>(); 
        try {
            // PRISMModelGrammar.g:458:3: (fst= iff_expr ( IMP nxt= iff_expr )* )
            // PRISMModelGrammar.g:458:3: fst= iff_expr ( IMP nxt= iff_expr )*
            {
            pushFollow(FOLLOW_iff_expr_in_imply_expr3263);
            fst=iff_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { stack.push(fst); }

            // PRISMModelGrammar.g:459:4: ( IMP nxt= iff_expr )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==IMP) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // PRISMModelGrammar.g:459:5: IMP nxt= iff_expr
            	    {
            	    match(input,IMP,FOLLOW_IMP_in_imply_expr3298); if (state.failed) return e;

            	    pushFollow(FOLLOW_iff_expr_in_imply_expr3304);
            	    nxt=iff_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { stack.push(nxt); }

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            if ( state.backtracking==0 ) { e = factory.makeRightAssocBinExpr(BinOp.Imp, stack); }

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
        return e;
    }
    // $ANTLR end "imply_expr"



    // $ANTLR start "iff_expr"
    // PRISMModelGrammar.g:463:1: iff_expr returns [Expr e] : fst= or_expr ( IFF nxt= or_expr )* ;
    public final Expr iff_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


        try {
            // PRISMModelGrammar.g:464:3: (fst= or_expr ( IFF nxt= or_expr )* )
            // PRISMModelGrammar.g:464:3: fst= or_expr ( IFF nxt= or_expr )*
            {
            pushFollow(FOLLOW_or_expr_in_iff_expr3390);
            fst=or_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = fst; }

            // PRISMModelGrammar.g:465:3: ( IFF nxt= or_expr )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==IFF) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // PRISMModelGrammar.g:465:4: IFF nxt= or_expr
            	    {
            	    match(input,IFF,FOLLOW_IFF_in_iff_expr3423); if (state.failed) return e;

            	    pushFollow(FOLLOW_or_expr_in_iff_expr3429);
            	    nxt=or_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, BinOp.Iff, nxt); }

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);


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
        return e;
    }
    // $ANTLR end "iff_expr"



    // $ANTLR start "or_expr"
    // PRISMModelGrammar.g:469:1: or_expr returns [Expr e] : fst= and_expr ( OR nxt= and_expr )* ;
    public final Expr or_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


        try {
            // PRISMModelGrammar.g:470:3: (fst= and_expr ( OR nxt= and_expr )* )
            // PRISMModelGrammar.g:470:3: fst= and_expr ( OR nxt= and_expr )*
            {
            pushFollow(FOLLOW_and_expr_in_or_expr3513);
            fst=and_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = fst; }

            // PRISMModelGrammar.g:471:3: ( OR nxt= and_expr )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==OR) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // PRISMModelGrammar.g:471:4: OR nxt= and_expr
            	    {
            	    match(input,OR,FOLLOW_OR_in_or_expr3545); if (state.failed) return e;

            	    pushFollow(FOLLOW_and_expr_in_or_expr3551);
            	    nxt=and_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, BinOp.Or, nxt); }

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);


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
        return e;
    }
    // $ANTLR end "or_expr"



    // $ANTLR start "and_expr"
    // PRISMModelGrammar.g:475:1: and_expr returns [Expr e] : fst= not_expr ( AND nxt= not_expr )* ;
    public final Expr and_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


        try {
            // PRISMModelGrammar.g:476:3: (fst= not_expr ( AND nxt= not_expr )* )
            // PRISMModelGrammar.g:476:3: fst= not_expr ( AND nxt= not_expr )*
            {
            pushFollow(FOLLOW_not_expr_in_and_expr3635);
            fst=not_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = fst; }

            // PRISMModelGrammar.g:477:3: ( AND nxt= not_expr )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==AND) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // PRISMModelGrammar.g:477:4: AND nxt= not_expr
            	    {
            	    match(input,AND,FOLLOW_AND_in_and_expr3667); if (state.failed) return e;

            	    pushFollow(FOLLOW_not_expr_in_and_expr3673);
            	    nxt=not_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, BinOp.And, nxt); }

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);


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
        return e;
    }
    // $ANTLR end "and_expr"



    // $ANTLR start "not_expr"
    // PRISMModelGrammar.g:481:1: not_expr returns [Expr e] : ( NOT )? child= eq_expr ;
    public final Expr not_expr() throws RecognitionException {
        Expr e = null;


        Expr child =null;


         boolean not = false; 
        try {
            // PRISMModelGrammar.g:482:3: ( ( NOT )? child= eq_expr )
            // PRISMModelGrammar.g:482:3: ( NOT )? child= eq_expr
            {
            // PRISMModelGrammar.g:482:3: ( NOT )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==NOT) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // PRISMModelGrammar.g:482:4: NOT
                    {
                    match(input,NOT,FOLLOW_NOT_in_not_expr3749); if (state.failed) return e;

                    if ( state.backtracking==0 ) { not = true; }

                    }
                    break;

            }


            pushFollow(FOLLOW_eq_expr_in_not_expr3801);
            child=eq_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = not ? factory.makeUnExpr(UnOp.Not, child) : child; }

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
        return e;
    }
    // $ANTLR end "not_expr"



    // $ANTLR start "eq_expr"
    // PRISMModelGrammar.g:486:1: eq_expr returns [Expr e] : left= comp_expr ( ( EQ | NEQ ) right= comp_expr )? ;
    public final Expr eq_expr() throws RecognitionException {
        Expr e = null;


        Expr left =null;

        Expr right =null;


         BinOp bop = null; 
        try {
            // PRISMModelGrammar.g:486:64: (left= comp_expr ( ( EQ | NEQ ) right= comp_expr )? )
            // PRISMModelGrammar.g:487:5: left= comp_expr ( ( EQ | NEQ ) right= comp_expr )?
            {
            pushFollow(FOLLOW_comp_expr_in_eq_expr3855);
            left=comp_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = left; }

            // PRISMModelGrammar.g:488:3: ( ( EQ | NEQ ) right= comp_expr )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==EQ||LA47_0==NEQ) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // PRISMModelGrammar.g:488:3: ( EQ | NEQ ) right= comp_expr
                    {
                    // PRISMModelGrammar.g:488:3: ( EQ | NEQ )
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==EQ) ) {
                        alt46=1;
                    }
                    else if ( (LA46_0==NEQ) ) {
                        alt46=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return e;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 46, 0, input);

                        throw nvae;

                    }
                    switch (alt46) {
                        case 1 :
                            // PRISMModelGrammar.g:488:5: EQ
                            {
                            match(input,EQ,FOLLOW_EQ_in_eq_expr3890); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Eq; }

                            }
                            break;
                        case 2 :
                            // PRISMModelGrammar.g:489:5: NEQ
                            {
                            match(input,NEQ,FOLLOW_NEQ_in_eq_expr3933); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Neq; }

                            }
                            break;

                    }


                    pushFollow(FOLLOW_comp_expr_in_eq_expr3977);
                    right=comp_expr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, bop, right); }

                    }
                    break;

            }


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
        return e;
    }
    // $ANTLR end "eq_expr"



    // $ANTLR start "comp_expr"
    // PRISMModelGrammar.g:494:1: comp_expr returns [Expr e] : left= add_expr ( ( LT | LE | GE | GT ) right= add_expr )? ;
    public final Expr comp_expr() throws RecognitionException {
        Expr e = null;


        Expr left =null;

        Expr right =null;


         BinOp bop = null; 
        try {
            // PRISMModelGrammar.g:494:65: (left= add_expr ( ( LT | LE | GE | GT ) right= add_expr )? )
            // PRISMModelGrammar.g:495:6: left= add_expr ( ( LT | LE | GE | GT ) right= add_expr )?
            {
            pushFollow(FOLLOW_add_expr_in_comp_expr4040);
            left=add_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = left; }

            // PRISMModelGrammar.g:496:3: ( ( LT | LE | GE | GT ) right= add_expr )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==GE||LA49_0==GT||LA49_0==LE||LA49_0==LT) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // PRISMModelGrammar.g:497:3: ( LT | LE | GE | GT ) right= add_expr
                    {
                    // PRISMModelGrammar.g:497:3: ( LT | LE | GE | GT )
                    int alt48=4;
                    switch ( input.LA(1) ) {
                    case LT:
                        {
                        alt48=1;
                        }
                        break;
                    case LE:
                        {
                        alt48=2;
                        }
                        break;
                    case GE:
                        {
                        alt48=3;
                        }
                        break;
                    case GT:
                        {
                        alt48=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return e;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 48, 0, input);

                        throw nvae;

                    }

                    switch (alt48) {
                        case 1 :
                            // PRISMModelGrammar.g:497:5: LT
                            {
                            match(input,LT,FOLLOW_LT_in_comp_expr4077); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Lt; }

                            }
                            break;
                        case 2 :
                            // PRISMModelGrammar.g:498:7: LE
                            {
                            match(input,LE,FOLLOW_LE_in_comp_expr4123); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Le; }

                            }
                            break;
                        case 3 :
                            // PRISMModelGrammar.g:499:7: GE
                            {
                            match(input,GE,FOLLOW_GE_in_comp_expr4167); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Ge; }

                            }
                            break;
                        case 4 :
                            // PRISMModelGrammar.g:500:7: GT
                            {
                            match(input,GT,FOLLOW_GT_in_comp_expr4211); if (state.failed) return e;

                            if ( state.backtracking==0 ) { bop = BinOp.Gt; }

                            }
                            break;

                    }


                    pushFollow(FOLLOW_add_expr_in_comp_expr4262);
                    right=add_expr();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, bop, right); }

                    }
                    break;

            }


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
        return e;
    }
    // $ANTLR end "comp_expr"



    // $ANTLR start "add_expr"
    // PRISMModelGrammar.g:506:1: add_expr returns [Expr e] : fst= mult_expr ( ( ADD | MIN ) nxt= mult_expr )* ;
    public final Expr add_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


         BinOp op = null; 
        try {
            // PRISMModelGrammar.g:507:3: (fst= mult_expr ( ( ADD | MIN ) nxt= mult_expr )* )
            // PRISMModelGrammar.g:507:3: fst= mult_expr ( ( ADD | MIN ) nxt= mult_expr )*
            {
            pushFollow(FOLLOW_mult_expr_in_add_expr4324);
            fst=mult_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = fst; }

            // PRISMModelGrammar.g:508:3: ( ( ADD | MIN ) nxt= mult_expr )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==ADD||LA51_0==MIN) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // PRISMModelGrammar.g:509:3: ( ADD | MIN ) nxt= mult_expr
            	    {
            	    // PRISMModelGrammar.g:509:3: ( ADD | MIN )
            	    int alt50=2;
            	    int LA50_0 = input.LA(1);

            	    if ( (LA50_0==ADD) ) {
            	        alt50=1;
            	    }
            	    else if ( (LA50_0==MIN) ) {
            	        alt50=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return e;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 50, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt50) {
            	        case 1 :
            	            // PRISMModelGrammar.g:509:5: ADD
            	            {
            	            match(input,ADD,FOLLOW_ADD_in_add_expr4359); if (state.failed) return e;

            	            if ( state.backtracking==0 ) { op = BinOp.Add; }

            	            }
            	            break;
            	        case 2 :
            	            // PRISMModelGrammar.g:510:5: MIN
            	            {
            	            match(input,MIN,FOLLOW_MIN_in_add_expr4402); if (state.failed) return e;

            	            if ( state.backtracking==0 ) { op = BinOp.Sub; }

            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_mult_expr_in_add_expr4455);
            	    nxt=mult_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, op, nxt); }

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


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
        return e;
    }
    // $ANTLR end "add_expr"



    // $ANTLR start "mult_expr"
    // PRISMModelGrammar.g:515:1: mult_expr returns [Expr e] : fst= min_expr ( ( MUL | DIV ) nxt= min_expr )* ;
    public final Expr mult_expr() throws RecognitionException {
        Expr e = null;


        Expr fst =null;

        Expr nxt =null;


         BinOp op = null; 
        try {
            // PRISMModelGrammar.g:516:3: (fst= min_expr ( ( MUL | DIV ) nxt= min_expr )* )
            // PRISMModelGrammar.g:516:3: fst= min_expr ( ( MUL | DIV ) nxt= min_expr )*
            {
            pushFollow(FOLLOW_min_expr_in_mult_expr4576);
            fst=min_expr();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = fst; }

            // PRISMModelGrammar.g:517:3: ( ( MUL | DIV ) nxt= min_expr )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==DIV||LA53_0==MUL) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // PRISMModelGrammar.g:517:3: ( MUL | DIV ) nxt= min_expr
            	    {
            	    // PRISMModelGrammar.g:517:3: ( MUL | DIV )
            	    int alt52=2;
            	    int LA52_0 = input.LA(1);

            	    if ( (LA52_0==MUL) ) {
            	        alt52=1;
            	    }
            	    else if ( (LA52_0==DIV) ) {
            	        alt52=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return e;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 52, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt52) {
            	        case 1 :
            	            // PRISMModelGrammar.g:517:5: MUL
            	            {
            	            match(input,MUL,FOLLOW_MUL_in_mult_expr4610); if (state.failed) return e;

            	            if ( state.backtracking==0 ) { op = BinOp.Mul; }

            	            }
            	            break;
            	        case 2 :
            	            // PRISMModelGrammar.g:518:5: DIV
            	            {
            	            match(input,DIV,FOLLOW_DIV_in_mult_expr4653); if (state.failed) return e;

            	            if ( state.backtracking==0 ) { op = BinOp.Div; }

            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_min_expr_in_mult_expr4707);
            	    nxt=min_expr();

            	    state._fsp--;
            	    if (state.failed) return e;

            	    if ( state.backtracking==0 ) { e = factory.makeBinExpr(e, op, nxt); }

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);


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
        return e;
    }
    // $ANTLR end "mult_expr"



    // $ANTLR start "min_expr"
    // PRISMModelGrammar.g:523:1: min_expr returns [Expr e] : ( MIN )? child= term ;
    public final Expr min_expr() throws RecognitionException {
        Expr e = null;


        Expr child =null;


         boolean min = false; 
        try {
            // PRISMModelGrammar.g:524:3: ( ( MIN )? child= term )
            // PRISMModelGrammar.g:524:3: ( MIN )? child= term
            {
            // PRISMModelGrammar.g:524:3: ( MIN )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==MIN) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // PRISMModelGrammar.g:524:4: MIN
                    {
                    match(input,MIN,FOLLOW_MIN_in_min_expr4784); if (state.failed) return e;

                    if ( state.backtracking==0 ) { min = true; }

                    }
                    break;

            }


            pushFollow(FOLLOW_term_in_min_expr4838);
            child=term();

            state._fsp--;
            if (state.failed) return e;

            if ( state.backtracking==0 ) { e = min ? factory.makeUnExpr(UnOp.Neg, child) : child; }

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
        return e;
    }
    // $ANTLR end "min_expr"



    // $ANTLR start "term"
    // PRISMModelGrammar.g:528:1: term returns [Expr e] : ( IDENT | DQ IDENT DQ | PREFIDENT | TRUE | FALSE | INTVAL | VALEXP | doubleval | funid LP exprs RP | FUNC LP funid COMMA exprs RP | LP expr RP );
    public final Expr term() throws RecognitionException {
        Expr e = null;


        Token IDENT55=null;
        Token IDENT56=null;
        Token PREFIDENT57=null;
        Token INTVAL58=null;
        Token VALEXP59=null;
        PRISMModelGrammarParser.doubleval_return doubleval60 =null;

        BuiltIn funid61 =null;

        List<Expr> exprs62 =null;

        BuiltIn funid63 =null;

        List<Expr> exprs64 =null;

        Expr expr65 =null;


        try {
            // PRISMModelGrammar.g:528:22: ( IDENT | DQ IDENT DQ | PREFIDENT | TRUE | FALSE | INTVAL | VALEXP | doubleval | funid LP exprs RP | FUNC LP funid COMMA exprs RP | LP expr RP )
            int alt55=11;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt55=1;
                }
                break;
            case DQ:
                {
                alt55=2;
                }
                break;
            case PREFIDENT:
                {
                alt55=3;
                }
                break;
            case TRUE:
                {
                alt55=4;
                }
                break;
            case FALSE:
                {
                alt55=5;
                }
                break;
            case INTVAL:
                {
                int LA55_6 = input.LA(2);

                if ( (LA55_6==EOF||(LA55_6 >= ADD && LA55_6 <= AND)||(LA55_6 >= COL && LA55_6 <= COMMA)||(LA55_6 >= DDOT && LA55_6 <= DIV)||(LA55_6 >= ENDINIT && LA55_6 <= ENDINVARIANT)||LA55_6==EQ||LA55_6==GE||LA55_6==GT||(LA55_6 >= IF && LA55_6 <= IMP)||LA55_6==LE||LA55_6==LT||LA55_6==MIN||(LA55_6 >= MUL && LA55_6 <= NEQ)||LA55_6==OR||LA55_6==RARROW||(LA55_6 >= RB && LA55_6 <= RBC)||LA55_6==RP||LA55_6==SEMI) ) {
                    alt55=6;
                }
                else if ( (LA55_6==DOT) ) {
                    alt55=8;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return e;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 6, input);

                    throw nvae;

                }
                }
                break;
            case VALEXP:
                {
                alt55=7;
                }
                break;
            case DOT:
                {
                alt55=8;
                }
                break;
            case CEIL:
            case FLOOR:
            case LOG:
            case MAXI:
            case MINI:
            case MOD:
            case POW:
                {
                alt55=9;
                }
                break;
            case FUNC:
                {
                alt55=10;
                }
                break;
            case LP:
                {
                alt55=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return e;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;

            }

            switch (alt55) {
                case 1 :
                    // PRISMModelGrammar.g:529:0: IDENT
                    {
                    IDENT55=(Token)match(input,IDENT,FOLLOW_IDENT_in_term4872); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeRef((IDENT55!=null?IDENT55.getText():null)); }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:530:3: DQ IDENT DQ
                    {
                    match(input,DQ,FOLLOW_DQ_in_term4915); if (state.failed) return e;

                    IDENT56=(Token)match(input,IDENT,FOLLOW_IDENT_in_term4917); if (state.failed) return e;

                    match(input,DQ,FOLLOW_DQ_in_term4919); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeRef(new String("\"" + (IDENT56!=null?IDENT56.getText():null) + "\"")); }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:531:3: PREFIDENT
                    {
                    PREFIDENT57=(Token)match(input,PREFIDENT,FOLLOW_PREFIDENT_in_term4954); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeRef((PREFIDENT57!=null?PREFIDENT57.getText():null)); }

                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:532:3: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_term4991); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeValue(true); }

                    }
                    break;
                case 5 :
                    // PRISMModelGrammar.g:533:3: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_term5033); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeValue(false); }

                    }
                    break;
                case 6 :
                    // PRISMModelGrammar.g:534:3: INTVAL
                    {
                    INTVAL58=(Token)match(input,INTVAL,FOLLOW_INTVAL_in_term5074); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeValue(Integer.parseInt((INTVAL58!=null?INTVAL58.getText():null))); }

                    }
                    break;
                case 7 :
                    // PRISMModelGrammar.g:535:3: VALEXP
                    {
                    VALEXP59=(Token)match(input,VALEXP,FOLLOW_VALEXP_in_term5091); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeValue(Double.parseDouble((VALEXP59!=null?VALEXP59.getText():null))); }

                    }
                    break;
                case 8 :
                    // PRISMModelGrammar.g:536:3: doubleval
                    {
                    pushFollow(FOLLOW_doubleval_in_term5125);
                    doubleval60=doubleval();

                    state._fsp--;
                    if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeValue(Double.parseDouble((doubleval60!=null?input.toString(doubleval60.start,doubleval60.stop):null))); }

                    }
                    break;
                case 9 :
                    // PRISMModelGrammar.g:537:3: funid LP exprs RP
                    {
                    pushFollow(FOLLOW_funid_in_term5163);
                    funid61=funid();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,LP,FOLLOW_LP_in_term5165); if (state.failed) return e;

                    pushFollow(FOLLOW_exprs_in_term5167);
                    exprs62=exprs();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,RP,FOLLOW_RP_in_term5169); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeFunction(funid61, exprs62.toArray(new Expr[exprs62.size()])); }

                    }
                    break;
                case 10 :
                    // PRISMModelGrammar.g:538:3: FUNC LP funid COMMA exprs RP
                    {
                    match(input,FUNC,FOLLOW_FUNC_in_term5223); if (state.failed) return e;

                    match(input,LP,FOLLOW_LP_in_term5225); if (state.failed) return e;

                    pushFollow(FOLLOW_funid_in_term5227);
                    funid63=funid();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,COMMA,FOLLOW_COMMA_in_term5229); if (state.failed) return e;

                    pushFollow(FOLLOW_exprs_in_term5231);
                    exprs64=exprs();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,RP,FOLLOW_RP_in_term5233); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = factory.makeFunction(funid63, exprs64.toArray(new Expr[exprs64.size()])); }

                    }
                    break;
                case 11 :
                    // PRISMModelGrammar.g:539:3: LP expr RP
                    {
                    match(input,LP,FOLLOW_LP_in_term5251); if (state.failed) return e;

                    pushFollow(FOLLOW_expr_in_term5253);
                    expr65=expr();

                    state._fsp--;
                    if (state.failed) return e;

                    match(input,RP,FOLLOW_RP_in_term5255); if (state.failed) return e;

                    if ( state.backtracking==0 ) { e = expr65; }

                    }
                    break;

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
        return e;
    }
    // $ANTLR end "term"



    // $ANTLR start "funid"
    // PRISMModelGrammar.g:542:1: funid returns [BuiltIn fun] : ( MAXI | MINI | FLOOR | CEIL | POW | MOD | LOG );
    public final BuiltIn funid() throws RecognitionException {
        BuiltIn fun = null;


        try {
            // PRISMModelGrammar.g:542:28: ( MAXI | MINI | FLOOR | CEIL | POW | MOD | LOG )
            int alt56=7;
            switch ( input.LA(1) ) {
            case MAXI:
                {
                alt56=1;
                }
                break;
            case MINI:
                {
                alt56=2;
                }
                break;
            case FLOOR:
                {
                alt56=3;
                }
                break;
            case CEIL:
                {
                alt56=4;
                }
                break;
            case POW:
                {
                alt56=5;
                }
                break;
            case MOD:
                {
                alt56=6;
                }
                break;
            case LOG:
                {
                alt56=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return fun;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;

            }

            switch (alt56) {
                case 1 :
                    // PRISMModelGrammar.g:543:0: MAXI
                    {
                    match(input,MAXI,FOLLOW_MAXI_in_funid5299); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Max; }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:544:3: MINI
                    {
                    match(input,MINI,FOLLOW_MINI_in_funid5343); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Min; }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:545:3: FLOOR
                    {
                    match(input,FLOOR,FOLLOW_FLOOR_in_funid5385); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Floor; }

                    }
                    break;
                case 4 :
                    // PRISMModelGrammar.g:546:3: CEIL
                    {
                    match(input,CEIL,FOLLOW_CEIL_in_funid5426); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Ceil; }

                    }
                    break;
                case 5 :
                    // PRISMModelGrammar.g:547:3: POW
                    {
                    match(input,POW,FOLLOW_POW_in_funid5468); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Pow; }

                    }
                    break;
                case 6 :
                    // PRISMModelGrammar.g:548:3: MOD
                    {
                    match(input,MOD,FOLLOW_MOD_in_funid5511); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Mod; }

                    }
                    break;
                case 7 :
                    // PRISMModelGrammar.g:549:3: LOG
                    {
                    match(input,LOG,FOLLOW_LOG_in_funid5554); if (state.failed) return fun;

                    if ( state.backtracking==0 ) { fun = BuiltIn.Log; }

                    }
                    break;

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
        return fun;
    }
    // $ANTLR end "funid"



    // $ANTLR start "exprs"
    // PRISMModelGrammar.g:552:1: exprs returns [List<Expr> list] : (fst= expr )? ( COMMA nxt= expr )* ;
    public final List<Expr> exprs() throws RecognitionException {
        List<Expr> list = null;


        Expr fst =null;

        Expr nxt =null;


         list = new ArrayList<Expr>(0); 
        try {
            // PRISMModelGrammar.g:552:78: ( (fst= expr )? ( COMMA nxt= expr )* )
            // PRISMModelGrammar.g:553:3: (fst= expr )? ( COMMA nxt= expr )*
            {
            // PRISMModelGrammar.g:553:3: (fst= expr )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==CEIL||LA57_0==DOT||LA57_0==DQ||(LA57_0 >= FALSE && LA57_0 <= FLOOR)||LA57_0==FUNC||LA57_0==IDENT||LA57_0==INTVAL||(LA57_0 >= LOG && LA57_0 <= LP)||LA57_0==MAXI||(LA57_0 >= MIN && LA57_0 <= MOD)||LA57_0==NOT||(LA57_0 >= POW && LA57_0 <= PREFIDENT)||(LA57_0 >= TRUE && LA57_0 <= VALEXP)) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // PRISMModelGrammar.g:553:3: fst= expr
                    {
                    pushFollow(FOLLOW_expr_in_exprs5621);
                    fst=expr();

                    state._fsp--;
                    if (state.failed) return list;

                    if ( state.backtracking==0 ) { list.add(fst); }

                    }
                    break;

            }


            // PRISMModelGrammar.g:555:3: ( COMMA nxt= expr )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==COMMA) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // PRISMModelGrammar.g:555:3: COMMA nxt= expr
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprs5659); if (state.failed) return list;

            	    pushFollow(FOLLOW_expr_in_exprs5665);
            	    nxt=expr();

            	    state._fsp--;
            	    if (state.failed) return list;

            	    if ( state.backtracking==0 ) { list.add(nxt); }

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


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
        return list;
    }
    // $ANTLR end "exprs"



    // $ANTLR start "system_decl"
    // PRISMModelGrammar.g:560:1: system_decl returns [PlasmaSystem s] : SYSTEM IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( system_var[$s] )* ( system_algebra )? ENDSYSTEM ;
    public final PlasmaSystem system_decl() throws RecognitionException {
        PlasmaSystem s = null;


        Token IDENT66=null;
        Parameter p1 =null;

        Parameter p2 =null;


         ModuleFactory general_factory = factory;
                                                     factory = new ModuleFactory(model);
                                                   
        try {
            // PRISMModelGrammar.g:562:46: ( SYSTEM IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( system_var[$s] )* ( system_algebra )? ENDSYSTEM )
            // PRISMModelGrammar.g:563:1: SYSTEM IDENT ( LP p1= parameter ( COMMA p2= parameter )* RP )? ( system_var[$s] )* ( system_algebra )? ENDSYSTEM
            {
            match(input,SYSTEM,FOLLOW_SYSTEM_in_system_decl5719); if (state.failed) return s;

            IDENT66=(Token)match(input,IDENT,FOLLOW_IDENT_in_system_decl5721); if (state.failed) return s;

            if ( state.backtracking==0 ) { s = new PlasmaSystem((IDENT66!=null?IDENT66.getText():null),amodel,factory); }

            // PRISMModelGrammar.g:564:3: ( LP p1= parameter ( COMMA p2= parameter )* RP )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==LP) ) {
                int LA60_1 = input.LA(2);

                if ( (LA60_1==BOOL||LA60_1==CLOCK||LA60_1==CONST||LA60_1==DOUBLE||LA60_1==INT||LA60_1==LB||LA60_1==PROB||LA60_1==RATE) ) {
                    alt60=1;
                }
            }
            switch (alt60) {
                case 1 :
                    // PRISMModelGrammar.g:564:3: LP p1= parameter ( COMMA p2= parameter )* RP
                    {
                    match(input,LP,FOLLOW_LP_in_system_decl5757); if (state.failed) return s;

                    pushFollow(FOLLOW_parameter_in_system_decl5761);
                    p1=parameter();

                    state._fsp--;
                    if (state.failed) return s;

                    if ( state.backtracking==0 ) { s.addParameter(p1); }

                    // PRISMModelGrammar.g:565:3: ( COMMA p2= parameter )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==COMMA) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // PRISMModelGrammar.g:565:4: COMMA p2= parameter
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_system_decl5793); if (state.failed) return s;

                    	    pushFollow(FOLLOW_parameter_in_system_decl5797);
                    	    p2=parameter();

                    	    state._fsp--;
                    	    if (state.failed) return s;

                    	    if ( state.backtracking==0 ) { s.addParameter(p2); }

                    	    }
                    	    break;

                    	default :
                    	    break loop59;
                        }
                    } while (true);


                    match(input,RP,FOLLOW_RP_in_system_decl5827); if (state.failed) return s;

                    }
                    break;

            }


            // PRISMModelGrammar.g:568:0: ( system_var[$s] )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==INIT||LA61_0==LABEL||LA61_0==MODULE) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // PRISMModelGrammar.g:568:0: system_var[$s]
            	    {
            	    pushFollow(FOLLOW_system_var_in_system_decl5831);
            	    system_var(s);

            	    state._fsp--;
            	    if (state.failed) return s;

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);


            // PRISMModelGrammar.g:569:0: ( system_algebra )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==IDENT||LA62_0==LP) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // PRISMModelGrammar.g:569:0: system_algebra
                    {
                    pushFollow(FOLLOW_system_algebra_in_system_decl5835);
                    system_algebra();

                    state._fsp--;
                    if (state.failed) return s;

                    }
                    break;

            }


            match(input,ENDSYSTEM,FOLLOW_ENDSYSTEM_in_system_decl5838); if (state.failed) return s;

            if ( state.backtracking==0 ) { factory = general_factory; }

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
        return s;
    }
    // $ANTLR end "system_decl"



    // $ANTLR start "system_var"
    // PRISMModelGrammar.g:574:1: system_var[PlasmaSystem s] : ( module_instance | label_decl | init_decl );
    public final void system_var(PlasmaSystem s) throws RecognitionException {
        ModuleInstance module_instance67 =null;

        Label label_decl68 =null;

        Expr init_decl69 =null;


        try {
            // PRISMModelGrammar.g:574:28: ( module_instance | label_decl | init_decl )
            int alt63=3;
            switch ( input.LA(1) ) {
            case MODULE:
                {
                alt63=1;
                }
                break;
            case LABEL:
                {
                alt63=2;
                }
                break;
            case INIT:
                {
                alt63=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;

            }

            switch (alt63) {
                case 1 :
                    // PRISMModelGrammar.g:575:0: module_instance
                    {
                    pushFollow(FOLLOW_module_instance_in_system_var5884);
                    module_instance67=module_instance();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { s.addModuleInstance(module_instance67); }

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:576:3: label_decl
                    {
                    pushFollow(FOLLOW_label_decl_in_system_var5917);
                    label_decl68=label_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { s.addLabel(label_decl68); }

                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:577:3: init_decl
                    {
                    pushFollow(FOLLOW_init_decl_in_system_var5953);
                    init_decl69=init_decl();

                    state._fsp--;
                    if (state.failed) return ;

                    if ( state.backtracking==0 ) { s.setInit(init_decl69); }

                    }
                    break;

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
        return ;
    }
    // $ANTLR end "system_var"



    // $ANTLR start "system_instance"
    // PRISMModelGrammar.g:580:1: system_instance returns [PlasmaSystemInstance s] : IDENT LP exprs RP ;
    public final PlasmaSystemInstance system_instance() throws RecognitionException {
        PlasmaSystemInstance s = null;


        Token IDENT70=null;
        List<Expr> exprs71 =null;


        try {
            // PRISMModelGrammar.g:580:49: ( IDENT LP exprs RP )
            // PRISMModelGrammar.g:581:1: IDENT LP exprs RP
            {
            IDENT70=(Token)match(input,IDENT,FOLLOW_IDENT_in_system_instance5998); if (state.failed) return s;

            match(input,LP,FOLLOW_LP_in_system_instance6000); if (state.failed) return s;

            pushFollow(FOLLOW_exprs_in_system_instance6002);
            exprs71=exprs();

            state._fsp--;
            if (state.failed) return s;

            match(input,RP,FOLLOW_RP_in_system_instance6004); if (state.failed) return s;

            if ( state.backtracking==0 ) { s = new PlasmaSystemInstance(amodel.getSystem((IDENT70!=null?IDENT70.getText():null)),exprs71); }

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
        return s;
    }
    // $ANTLR end "system_instance"



    // $ANTLR start "adaptive_system"
    // PRISMModelGrammar.g:584:1: adaptive_system : ADAPTIVE start_system ( adaptive_command )* ENDADAPTIVE ;
    public final void adaptive_system() throws RecognitionException {
        PlasmaSystemInstance start_system72 =null;

        AdaptiveCommand adaptive_command73 =null;


         AdaptiveSystem sys = new AdaptiveSystem(amodel);
                                                     amodel.setAdaptiveSystem(sys); 
        try {
            // PRISMModelGrammar.g:585:79: ( ADAPTIVE start_system ( adaptive_command )* ENDADAPTIVE )
            // PRISMModelGrammar.g:586:1: ADAPTIVE start_system ( adaptive_command )* ENDADAPTIVE
            {
            match(input,ADAPTIVE,FOLLOW_ADAPTIVE_in_adaptive_system6066); if (state.failed) return ;

            pushFollow(FOLLOW_start_system_in_adaptive_system6103);
            start_system72=start_system();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { sys.setStartSystem(start_system72); }

            // PRISMModelGrammar.g:588:2: ( adaptive_command )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==LBC) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // PRISMModelGrammar.g:588:2: adaptive_command
            	    {
            	    pushFollow(FOLLOW_adaptive_command_in_adaptive_system6151);
            	    adaptive_command73=adaptive_command();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    if ( state.backtracking==0 ) { sys.addTransition(adaptive_command73); }

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            match(input,ENDADAPTIVE,FOLLOW_ENDADAPTIVE_in_adaptive_system6183); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "adaptive_system"



    // $ANTLR start "start_system"
    // PRISMModelGrammar.g:593:1: start_system returns [PlasmaSystemInstance s] : INIT AT system_instance SEMI ;
    public final PlasmaSystemInstance start_system() throws RecognitionException {
        PlasmaSystemInstance s = null;


        PlasmaSystemInstance system_instance74 =null;


        try {
            // PRISMModelGrammar.g:593:46: ( INIT AT system_instance SEMI )
            // PRISMModelGrammar.g:594:1: INIT AT system_instance SEMI
            {
            match(input,INIT,FOLLOW_INIT_in_start_system6195); if (state.failed) return s;

            match(input,AT,FOLLOW_AT_in_start_system6197); if (state.failed) return s;

            pushFollow(FOLLOW_system_instance_in_start_system6199);
            system_instance74=system_instance();

            state._fsp--;
            if (state.failed) return s;

            match(input,SEMI,FOLLOW_SEMI_in_start_system6201); if (state.failed) return s;

            if ( state.backtracking==0 ) { s = system_instance74; }

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
        return s;
    }
    // $ANTLR end "start_system"



    // $ANTLR start "adaptive_command"
    // PRISMModelGrammar.g:597:1: adaptive_command returns [AdaptiveCommand com] : LBC IDENT ( OR expr ) RBC RARROW ( adaptive_actions[com] | adaptive_update[null] ) SEMI ;
    public final AdaptiveCommand adaptive_command() throws RecognitionException {
        AdaptiveCommand com = null;


        Token IDENT75=null;
        Expr expr76 =null;

        AdaptiveAction adaptive_update77 =null;


         PlasmaSystem sys = null; 
                                                     ModuleFactory general_factory = factory;
                                                   
        try {
            // PRISMModelGrammar.g:600:46: ( LBC IDENT ( OR expr ) RBC RARROW ( adaptive_actions[com] | adaptive_update[null] ) SEMI )
            // PRISMModelGrammar.g:601:1: LBC IDENT ( OR expr ) RBC RARROW ( adaptive_actions[com] | adaptive_update[null] ) SEMI
            {
            match(input,LBC,FOLLOW_LBC_in_adaptive_command6272); if (state.failed) return com;

            IDENT75=(Token)match(input,IDENT,FOLLOW_IDENT_in_adaptive_command6274); if (state.failed) return com;

            if ( state.backtracking==0 ) { sys = amodel.getSystem((IDENT75!=null?IDENT75.getText():null)); 
                                                         factory = sys.factory;
                                                       }

            // PRISMModelGrammar.g:604:2: ( OR expr )
            // PRISMModelGrammar.g:604:2: OR expr
            {
            match(input,OR,FOLLOW_OR_in_adaptive_command6313); if (state.failed) return com;

            pushFollow(FOLLOW_expr_in_adaptive_command6315);
            expr76=expr();

            state._fsp--;
            if (state.failed) return com;

            }


            match(input,RBC,FOLLOW_RBC_in_adaptive_command6318); if (state.failed) return com;

            if ( state.backtracking==0 ) { com = new AdaptiveCommand(sys, expr76); }

            match(input,RARROW,FOLLOW_RARROW_in_adaptive_command6356); if (state.failed) return com;

            // PRISMModelGrammar.g:606:3: ( adaptive_actions[com] | adaptive_update[null] )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==CEIL||LA65_0==DOT||LA65_0==DQ||(LA65_0 >= FALSE && LA65_0 <= FLOOR)||LA65_0==FUNC||LA65_0==IDENT||LA65_0==INTVAL||(LA65_0 >= LOG && LA65_0 <= LP)||LA65_0==MAXI||(LA65_0 >= MIN && LA65_0 <= MOD)||LA65_0==NOT||(LA65_0 >= POW && LA65_0 <= PREFIDENT)||(LA65_0 >= TRUE && LA65_0 <= VALEXP)) ) {
                alt65=1;
            }
            else if ( (LA65_0==LBC) ) {
                alt65=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return com;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;

            }
            switch (alt65) {
                case 1 :
                    // PRISMModelGrammar.g:606:3: adaptive_actions[com]
                    {
                    pushFollow(FOLLOW_adaptive_actions_in_adaptive_command6360);
                    adaptive_actions(com);

                    state._fsp--;
                    if (state.failed) return com;

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:607:5: adaptive_update[null]
                    {
                    pushFollow(FOLLOW_adaptive_update_in_adaptive_command6367);
                    adaptive_update77=adaptive_update(null);

                    state._fsp--;
                    if (state.failed) return com;

                    if ( state.backtracking==0 ) { com.addAction(adaptive_update77); }

                    }
                    break;

            }


            match(input,SEMI,FOLLOW_SEMI_in_adaptive_command6391); if (state.failed) return com;

            if ( state.backtracking==0 ) { factory = general_factory; }

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
        return com;
    }
    // $ANTLR end "adaptive_command"



    // $ANTLR start "adaptive_actions"
    // PRISMModelGrammar.g:612:1: adaptive_actions[AdaptiveCommand com] : a1= adaptive_action ( ADD a2= adaptive_action )* ;
    public final void adaptive_actions(AdaptiveCommand com) throws RecognitionException {
        AdaptiveAction a1 =null;

        AdaptiveAction a2 =null;


        try {
            // PRISMModelGrammar.g:612:39: (a1= adaptive_action ( ADD a2= adaptive_action )* )
            // PRISMModelGrammar.g:613:3: a1= adaptive_action ( ADD a2= adaptive_action )*
            {
            pushFollow(FOLLOW_adaptive_action_in_adaptive_actions6443);
            a1=adaptive_action();

            state._fsp--;
            if (state.failed) return ;

            if ( state.backtracking==0 ) { com.addAction(a1); }

            // PRISMModelGrammar.g:614:2: ( ADD a2= adaptive_action )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==ADD) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // PRISMModelGrammar.g:614:2: ADD a2= adaptive_action
            	    {
            	    match(input,ADD,FOLLOW_ADD_in_adaptive_actions6472); if (state.failed) return ;

            	    pushFollow(FOLLOW_adaptive_action_in_adaptive_actions6476);
            	    a2=adaptive_action();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    if ( state.backtracking==0 ) { com.addAction(a2); }

            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);


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
        return ;
    }
    // $ANTLR end "adaptive_actions"



    // $ANTLR start "adaptive_action"
    // PRISMModelGrammar.g:618:1: adaptive_action returns [AdaptiveAction a] : expr COL adaptive_update[$expr.e] ;
    public final AdaptiveAction adaptive_action() throws RecognitionException {
        AdaptiveAction a = null;


        Expr expr78 =null;

        AdaptiveAction adaptive_update79 =null;


        try {
            // PRISMModelGrammar.g:618:43: ( expr COL adaptive_update[$expr.e] )
            // PRISMModelGrammar.g:619:1: expr COL adaptive_update[$expr.e]
            {
            pushFollow(FOLLOW_expr_in_adaptive_action6512);
            expr78=expr();

            state._fsp--;
            if (state.failed) return a;

            match(input,COL,FOLLOW_COL_in_adaptive_action6514); if (state.failed) return a;

            pushFollow(FOLLOW_adaptive_update_in_adaptive_action6516);
            adaptive_update79=adaptive_update(expr78);

            state._fsp--;
            if (state.failed) return a;

            if ( state.backtracking==0 ) { a = adaptive_update79; }

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
        return a;
    }
    // $ANTLR end "adaptive_action"



    // $ANTLR start "adaptive_update"
    // PRISMModelGrammar.g:623:1: adaptive_update[Expr rate] returns [AdaptiveAction a] : LBC system_instance ( OR procedure_instance )* RBC ;
    public final AdaptiveAction adaptive_update(Expr rate) throws RecognitionException {
        AdaptiveAction a = null;


        PlasmaSystemInstance system_instance80 =null;

        ModuleInstance procedure_instance81 =null;


        try {
            // PRISMModelGrammar.g:623:55: ( LBC system_instance ( OR procedure_instance )* RBC )
            // PRISMModelGrammar.g:624:1: LBC system_instance ( OR procedure_instance )* RBC
            {
            match(input,LBC,FOLLOW_LBC_in_adaptive_update6547); if (state.failed) return a;

            pushFollow(FOLLOW_system_instance_in_adaptive_update6549);
            system_instance80=system_instance();

            state._fsp--;
            if (state.failed) return a;

            if ( state.backtracking==0 ) { if (rate!=null)
                                                          a = new AdaptiveAction(system_instance80,rate);
                                                         else
                                                          a = new AdaptiveAction(system_instance80, factory);
                                                       }

            // PRISMModelGrammar.g:630:3: ( OR procedure_instance )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==OR) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // PRISMModelGrammar.g:630:3: OR procedure_instance
            	    {
            	    match(input,OR,FOLLOW_OR_in_adaptive_update6585); if (state.failed) return a;

            	    pushFollow(FOLLOW_procedure_instance_in_adaptive_update6587);
            	    procedure_instance81=procedure_instance();

            	    state._fsp--;
            	    if (state.failed) return a;

            	    if ( state.backtracking==0 ) { a.addProcedure(procedure_instance81); }

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            match(input,RBC,FOLLOW_RBC_in_adaptive_update6613); if (state.failed) return a;

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
        return a;
    }
    // $ANTLR end "adaptive_update"



    // $ANTLR start "procedure_instance"
    // PRISMModelGrammar.g:635:1: procedure_instance returns [ModuleInstance proc] : newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )? ;
    public final ModuleInstance procedure_instance() throws RecognitionException {
        ModuleInstance proc = null;


        Token newident=null;
        Token oldident=null;
        List<Expr> exprs82 =null;


         Map<String,String> ren = new HashMap<String,String>();
                                                                List<Expr> params = null; 
        try {
            // PRISMModelGrammar.g:636:84: (newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )? )
            // PRISMModelGrammar.g:637:9: newident= IDENT EQ oldident= IDENT ( LP exprs RP )? ( LB renamings[ren] RB )?
            {
            newident=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure_instance6631); if (state.failed) return proc;

            match(input,EQ,FOLLOW_EQ_in_procedure_instance6633); if (state.failed) return proc;

            oldident=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure_instance6637); if (state.failed) return proc;

            // PRISMModelGrammar.g:638:3: ( LP exprs RP )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==LP) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // PRISMModelGrammar.g:638:3: LP exprs RP
                    {
                    match(input,LP,FOLLOW_LP_in_procedure_instance6642); if (state.failed) return proc;

                    pushFollow(FOLLOW_exprs_in_procedure_instance6644);
                    exprs82=exprs();

                    state._fsp--;
                    if (state.failed) return proc;

                    match(input,RP,FOLLOW_RP_in_procedure_instance6646); if (state.failed) return proc;

                    if ( state.backtracking==0 ) { params = exprs82; }

                    }
                    break;

            }


            // PRISMModelGrammar.g:640:3: ( LB renamings[ren] RB )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==LB) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // PRISMModelGrammar.g:640:3: LB renamings[ren] RB
                    {
                    match(input,LB,FOLLOW_LB_in_procedure_instance6691); if (state.failed) return proc;

                    pushFollow(FOLLOW_renamings_in_procedure_instance6693);
                    renamings(ren);

                    state._fsp--;
                    if (state.failed) return proc;

                    match(input,RB,FOLLOW_RB_in_procedure_instance6696); if (state.failed) return proc;

                    }
                    break;

            }


            if ( state.backtracking==0 ) { proc = new ModuleInstance((newident!=null?newident.getText():null), (oldident!=null?oldident.getText():null), ren, params); }

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
        return proc;
    }
    // $ANTLR end "procedure_instance"



    // $ANTLR start "system_algebra"
    // PRISMModelGrammar.g:643:1: system_algebra : module_compo ( OR LB channels RB OR module_compo | ( PAR2 module_compo )+ | ( PAR3 module_compo )+ ) ;
    public final void system_algebra() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:643:15: ( module_compo ( OR LB channels RB OR module_compo | ( PAR2 module_compo )+ | ( PAR3 module_compo )+ ) )
            // PRISMModelGrammar.g:644:1: module_compo ( OR LB channels RB OR module_compo | ( PAR2 module_compo )+ | ( PAR3 module_compo )+ )
            {
            pushFollow(FOLLOW_module_compo_in_system_algebra6731);
            module_compo();

            state._fsp--;
            if (state.failed) return ;

            // PRISMModelGrammar.g:644:14: ( OR LB channels RB OR module_compo | ( PAR2 module_compo )+ | ( PAR3 module_compo )+ )
            int alt72=3;
            switch ( input.LA(1) ) {
            case OR:
                {
                alt72=1;
                }
                break;
            case PAR2:
                {
                alt72=2;
                }
                break;
            case PAR3:
                {
                alt72=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;

            }

            switch (alt72) {
                case 1 :
                    // PRISMModelGrammar.g:644:16: OR LB channels RB OR module_compo
                    {
                    match(input,OR,FOLLOW_OR_in_system_algebra6735); if (state.failed) return ;

                    match(input,LB,FOLLOW_LB_in_system_algebra6737); if (state.failed) return ;

                    pushFollow(FOLLOW_channels_in_system_algebra6739);
                    channels();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input,RB,FOLLOW_RB_in_system_algebra6741); if (state.failed) return ;

                    match(input,OR,FOLLOW_OR_in_system_algebra6743); if (state.failed) return ;

                    pushFollow(FOLLOW_module_compo_in_system_algebra6745);
                    module_compo();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:645:18: ( PAR2 module_compo )+
                    {
                    // PRISMModelGrammar.g:645:18: ( PAR2 module_compo )+
                    int cnt70=0;
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==PAR2) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // PRISMModelGrammar.g:645:19: PAR2 module_compo
                    	    {
                    	    match(input,PAR2,FOLLOW_PAR2_in_system_algebra6765); if (state.failed) return ;

                    	    pushFollow(FOLLOW_module_compo_in_system_algebra6767);
                    	    module_compo();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt70 >= 1 ) break loop70;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(70, input);
                                throw eee;
                        }
                        cnt70++;
                    } while (true);


                    }
                    break;
                case 3 :
                    // PRISMModelGrammar.g:646:18: ( PAR3 module_compo )+
                    {
                    // PRISMModelGrammar.g:646:18: ( PAR3 module_compo )+
                    int cnt71=0;
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==PAR3) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // PRISMModelGrammar.g:646:19: PAR3 module_compo
                    	    {
                    	    match(input,PAR3,FOLLOW_PAR3_in_system_algebra6789); if (state.failed) return ;

                    	    pushFollow(FOLLOW_module_compo_in_system_algebra6791);
                    	    module_compo();

                    	    state._fsp--;
                    	    if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt71 >= 1 ) break loop71;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(71, input);
                                throw eee;
                        }
                        cnt71++;
                    } while (true);


                    }
                    break;

            }


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
        return ;
    }
    // $ANTLR end "system_algebra"



    // $ANTLR start "module_compo"
    // PRISMModelGrammar.g:650:1: module_compo : ( IDENT ( channels_hiding | channels_renaming ) | LP system_algebra RP );
    public final void module_compo() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:650:13: ( IDENT ( channels_hiding | channels_renaming ) | LP system_algebra RP )
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==IDENT) ) {
                alt74=1;
            }
            else if ( (LA74_0==LP) ) {
                alt74=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;

            }
            switch (alt74) {
                case 1 :
                    // PRISMModelGrammar.g:651:0: IDENT ( channels_hiding | channels_renaming )
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_module_compo6816); if (state.failed) return ;

                    // PRISMModelGrammar.g:651:7: ( channels_hiding | channels_renaming )
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==DIV) ) {
                        alt73=1;
                    }
                    else if ( (LA73_0==LBC) ) {
                        alt73=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 73, 0, input);

                        throw nvae;

                    }
                    switch (alt73) {
                        case 1 :
                            // PRISMModelGrammar.g:651:8: channels_hiding
                            {
                            pushFollow(FOLLOW_channels_hiding_in_module_compo6819);
                            channels_hiding();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;
                        case 2 :
                            // PRISMModelGrammar.g:651:26: channels_renaming
                            {
                            pushFollow(FOLLOW_channels_renaming_in_module_compo6823);
                            channels_renaming();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // PRISMModelGrammar.g:652:3: LP system_algebra RP
                    {
                    match(input,LP,FOLLOW_LP_in_module_compo6828); if (state.failed) return ;

                    pushFollow(FOLLOW_system_algebra_in_module_compo6830);
                    system_algebra();

                    state._fsp--;
                    if (state.failed) return ;

                    match(input,RP,FOLLOW_RP_in_module_compo6832); if (state.failed) return ;

                    }
                    break;

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
        return ;
    }
    // $ANTLR end "module_compo"



    // $ANTLR start "channels"
    // PRISMModelGrammar.g:655:1: channels : IDENT ( COMMA IDENT )* ;
    public final void channels() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:655:9: ( IDENT ( COMMA IDENT )* )
            // PRISMModelGrammar.g:656:1: IDENT ( COMMA IDENT )*
            {
            match(input,IDENT,FOLLOW_IDENT_in_channels6840); if (state.failed) return ;

            // PRISMModelGrammar.g:656:7: ( COMMA IDENT )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==COMMA) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // PRISMModelGrammar.g:656:8: COMMA IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_channels6843); if (state.failed) return ;

            	    match(input,IDENT,FOLLOW_IDENT_in_channels6845); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);


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
        return ;
    }
    // $ANTLR end "channels"



    // $ANTLR start "channels_hiding"
    // PRISMModelGrammar.g:659:1: channels_hiding : DIV LBC channels RBC ;
    public final void channels_hiding() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:659:16: ( DIV LBC channels RBC )
            // PRISMModelGrammar.g:660:1: DIV LBC channels RBC
            {
            match(input,DIV,FOLLOW_DIV_in_channels_hiding6855); if (state.failed) return ;

            match(input,LBC,FOLLOW_LBC_in_channels_hiding6857); if (state.failed) return ;

            pushFollow(FOLLOW_channels_in_channels_hiding6859);
            channels();

            state._fsp--;
            if (state.failed) return ;

            match(input,RBC,FOLLOW_RBC_in_channels_hiding6861); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "channels_hiding"



    // $ANTLR start "channels_renaming"
    // PRISMModelGrammar.g:663:1: channels_renaming : LBC channel_renaming ( COMMA channel_renaming )* RBC ;
    public final void channels_renaming() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:663:18: ( LBC channel_renaming ( COMMA channel_renaming )* RBC )
            // PRISMModelGrammar.g:664:1: LBC channel_renaming ( COMMA channel_renaming )* RBC
            {
            match(input,LBC,FOLLOW_LBC_in_channels_renaming6869); if (state.failed) return ;

            pushFollow(FOLLOW_channel_renaming_in_channels_renaming6871);
            channel_renaming();

            state._fsp--;
            if (state.failed) return ;

            // PRISMModelGrammar.g:664:22: ( COMMA channel_renaming )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( (LA76_0==COMMA) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // PRISMModelGrammar.g:664:23: COMMA channel_renaming
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_channels_renaming6874); if (state.failed) return ;

            	    pushFollow(FOLLOW_channel_renaming_in_channels_renaming6876);
            	    channel_renaming();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);


            match(input,RBC,FOLLOW_RBC_in_channels_renaming6880); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "channels_renaming"



    // $ANTLR start "channel_renaming"
    // PRISMModelGrammar.g:667:1: channel_renaming : IDENT LARROW IDENT ;
    public final void channel_renaming() throws RecognitionException {
        try {
            // PRISMModelGrammar.g:667:17: ( IDENT LARROW IDENT )
            // PRISMModelGrammar.g:668:1: IDENT LARROW IDENT
            {
            match(input,IDENT,FOLLOW_IDENT_in_channel_renaming6888); if (state.failed) return ;

            match(input,LARROW,FOLLOW_LARROW_in_channel_renaming6890); if (state.failed) return ;

            match(input,IDENT,FOLLOW_IDENT_in_channel_renaming6892); if (state.failed) return ;

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
        return ;
    }
    // $ANTLR end "channel_renaming"


    public static class doubleval_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "doubleval"
    // PRISMModelGrammar.g:680:1: doubleval : ( INTVAL )? DOT ( INTVAL | VALEXP ) ;
    public final PRISMModelGrammarParser.doubleval_return doubleval() throws RecognitionException {
        PRISMModelGrammarParser.doubleval_return retval = new PRISMModelGrammarParser.doubleval_return();
        retval.start = input.LT(1);


        try {
            // PRISMModelGrammar.g:680:10: ( ( INTVAL )? DOT ( INTVAL | VALEXP ) )
            // PRISMModelGrammar.g:681:2: ( INTVAL )? DOT ( INTVAL | VALEXP )
            {
            // PRISMModelGrammar.g:681:2: ( INTVAL )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==INTVAL) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // PRISMModelGrammar.g:681:2: INTVAL
                    {
                    match(input,INTVAL,FOLLOW_INTVAL_in_doubleval6911); if (state.failed) return retval;

                    }
                    break;

            }


            match(input,DOT,FOLLOW_DOT_in_doubleval6915); if (state.failed) return retval;

            if ( input.LA(1)==INTVAL||input.LA(1)==VALEXP ) {
                input.consume();
                state.errorRecovery=false;
                state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


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
        return retval;
    }
    // $ANTLR end "doubleval"

    // $ANTLR start synpred41_PRISMModelGrammar
    public final void synpred41_PRISMModelGrammar_fragment() throws RecognitionException {
        // PRISMModelGrammar.g:299:3: ( DQ IDENT DQ )
        // PRISMModelGrammar.g:299:3: DQ IDENT DQ
        {
        match(input,DQ,FOLLOW_DQ_in_synpred41_PRISMModelGrammar1519); if (state.failed) return ;

        match(input,IDENT,FOLLOW_IDENT_in_synpred41_PRISMModelGrammar1521); if (state.failed) return ;

        match(input,DQ,FOLLOW_DQ_in_synpred41_PRISMModelGrammar1523); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred41_PRISMModelGrammar

    // Delegated rules

    public final boolean synpred41_PRISMModelGrammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred41_PRISMModelGrammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_modeltype_in_rmlfile77 = new BitSet(new long[]{0x0800441200004000L,0x0000000000409080L});
    public static final BitSet FOLLOW_declaration_in_rmlfile115 = new BitSet(new long[]{0x0800441200004000L,0x0000000000409080L});
    public static final BitSet FOLLOW_EOF_in_rmlfile118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADAPTIVE_in_rmladaptivefile132 = new BitSet(new long[]{0x488044120020C010L,0x0000000000609580L});
    public static final BitSet FOLLOW_modeltype_in_rmladaptivefile135 = new BitSet(new long[]{0x0800441200004010L,0x0000000000409080L});
    public static final BitSet FOLLOW_declaration_in_rmladaptivefile174 = new BitSet(new long[]{0x0800441200004010L,0x0000000000409080L});
    public static final BitSet FOLLOW_adaptive_system_in_rmladaptivefile177 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_rmladaptivefile179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_obs_declaration_in_observerfile191 = new BitSet(new long[]{0x0000001200004000L,0x0000000000001081L});
    public static final BitSet FOLLOW_EOF_in_observerfile194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DTMC_in_modeltype208 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_sampled_in_modeltype229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CTMC_in_modeltype244 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_sampled_in_modeltype263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MDP_in_modeltype278 = new BitSet(new long[]{0x0000000000000002L,0x00000000001A0000L});
    public static final BitSet FOLLOW_SHD_in_modeltype300 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_sampled_in_modeltype321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SML_in_modeltype338 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_sampled_in_modeltype360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sampled_in_modeltype377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PTA_in_modeltype393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROBABILISTIC_in_modeltype414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STOCHASTIC_in_modeltype424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NONDETERMINISTIC_in_modeltype437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SAMPLING_in_sampled448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_global_var_in_declaration460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_declaration475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_decl_in_declaration490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_instance_in_declaration502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula_decl_in_declaration510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_decl_in_declaration521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reward_decl_in_declaration534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_init_decl_in_declaration546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_system_decl_in_declaration560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_global_var_in_obs_declaration576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_obs_declaration591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formula_decl_in_obs_declaration606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_observer_decl_in_obs_declaration617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csort_in_constant643 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_constant645 = new BitSet(new long[]{0x0000000040000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_EQ_in_constant674 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_constant676 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_constant714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONST_in_csort761 = new BitSet(new long[]{0x0000080000080102L});
    public static final BitSet FOLLOW_ctype_in_csort801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RATE_in_csort844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROB_in_csort884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_ctype932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_ctype974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_in_ctype1013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GLOBAL_in_global_var1059 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_var_declaration_in_global_var1061 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_global_var1063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_var_declaration1099 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_var_declaration1101 = new BitSet(new long[]{0x0001080000080500L});
    public static final BitSet FOLLOW_sort_in_var_declaration1103 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_INIT_in_var_declaration1108 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_var_declaration1110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_sort1198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_sort1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_in_sort1279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LB_in_sort1316 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_sort1320 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_DDOT_in_sort1322 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_sort1326 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_sort1328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLOCK_in_sort1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FORMULA_in_formula_decl1391 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_formula_decl1393 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_formula_decl1395 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_formula_decl1397 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_formula_decl1399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LABEL_in_label_decl1427 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQ_in_label_decl1429 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_label_decl1431 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQ_in_label_decl1433 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_label_decl1435 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_label_decl1437 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_label_decl1439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INIT_in_init_decl1463 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_init_decl1465 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_ENDINIT_in_init_decl1467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REWARDS_in_reward_decl1514 = new BitSet(new long[]{0x8759104590140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_DQ_in_reward_decl1519 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_reward_decl1521 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQ_in_reward_decl1523 = new BitSet(new long[]{0x8759104590140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_reward_statement_in_reward_decl1598 = new BitSet(new long[]{0x8759104590140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_ENDREWARDS_in_reward_decl1644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_state_reward_in_reward_statement1657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_trans_reward_in_reward_statement1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_state_reward1731 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_state_reward1733 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_state_reward1737 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_state_reward1739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_synchro_in_trans_reward1766 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_trans_reward1770 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_trans_reward1772 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_trans_reward1776 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_trans_reward1778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODULE_in_module_decl1798 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_module_decl1800 = new BitSet(new long[]{0x0011204002000000L});
    public static final BitSet FOLLOW_LP_in_module_decl1833 = new BitSet(new long[]{0x0001080000084500L,0x0000000000001080L});
    public static final BitSet FOLLOW_parameter_in_module_decl1839 = new BitSet(new long[]{0x0000000000001000L,0x0000000000010000L});
    public static final BitSet FOLLOW_COMMA_in_module_decl1872 = new BitSet(new long[]{0x0001080000084500L,0x0000000000001080L});
    public static final BitSet FOLLOW_parameter_in_module_decl1876 = new BitSet(new long[]{0x0000000000001000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_module_decl1924 = new BitSet(new long[]{0x0001204002000000L});
    public static final BitSet FOLLOW_local_var_in_module_decl1931 = new BitSet(new long[]{0x0001204002000000L});
    public static final BitSet FOLLOW_invariant_in_module_decl1968 = new BitSet(new long[]{0x0001000002000000L});
    public static final BitSet FOLLOW_command_in_module_decl2006 = new BitSet(new long[]{0x0001000002000000L});
    public static final BitSet FOLLOW_ENDMODULE_in_module_decl2043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_in_observer_decl2088 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_observer_decl2090 = new BitSet(new long[]{0x0001004004000000L});
    public static final BitSet FOLLOW_local_var_in_observer_decl2121 = new BitSet(new long[]{0x0001004004000000L});
    public static final BitSet FOLLOW_obs_command_in_observer_decl2158 = new BitSet(new long[]{0x0001000004000000L});
    public static final BitSet FOLLOW_ENDOBSERVER_in_observer_decl2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_csort_in_parameter2234 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_parameter2236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sort_in_parameter2272 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_parameter2274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MODULE_in_module_instance2322 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_module_instance2326 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_module_instance2328 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_module_instance2332 = new BitSet(new long[]{0x0011000002000000L});
    public static final BitSet FOLLOW_LP_in_module_instance2339 = new BitSet(new long[]{0x8758104580141200L,0x0000000001810030L});
    public static final BitSet FOLLOW_exprs_in_module_instance2341 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_module_instance2343 = new BitSet(new long[]{0x0001000002000000L});
    public static final BitSet FOLLOW_LB_in_module_instance2388 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_renamings_in_module_instance2390 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_module_instance2393 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_ENDMODULE_in_module_instance2422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_renaming_in_renamings2467 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_renamings2511 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_renaming_in_renamings2515 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_IDENT_in_renaming2566 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_renaming2568 = new BitSet(new long[]{0x0000004000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_renaming2572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_var_declaration_in_local_var2606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_local_var2608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INVARIANT_in_invariant2644 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_invariant2646 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_ENDINVARIANT_in_invariant2648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_synchro_in_command2672 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_command2674 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RARROW_in_command2696 = new BitSet(new long[]{0x875A104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_actions_set_in_command2698 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_command2700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBC_in_sampling_param2712 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_sampling_param2714 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_RBC_in_sampling_param2716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LB_in_synchro2743 = new BitSet(new long[]{0x0000004000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_synchro2747 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_synchro2788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LB_in_obs_command2796 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_obs_command2798 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_obs_command2800 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RARROW_in_obs_command2835 = new BitSet(new long[]{0x0010000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_updates_in_obs_command2850 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_obs_command2852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actions_in_actions_set2876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updates_in_actions_set2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_action_in_actions2907 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_ADD_in_actions2950 = new BitSet(new long[]{0x875A104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_action_in_actions2952 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_sampling_param_in_action3000 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_action3016 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_action3048 = new BitSet(new long[]{0x0010000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_updates_in_action3050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_update_in_updates3058 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_AND_in_updates3062 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_update_in_updates3064 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_TRUE_in_updates3070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_update3078 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_update3080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_PRIME_in_update3082 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_update3084 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_update3086 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_update3088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_imply_expr_in_expr3120 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_IF_in_expr3157 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_expr3161 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_expr3163 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_expr3167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iff_expr_in_imply_expr3263 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_IMP_in_imply_expr3298 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_iff_expr_in_imply_expr3304 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_or_expr_in_iff_expr3390 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_IFF_in_iff_expr3423 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_or_expr_in_iff_expr3429 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_and_expr_in_or_expr3513 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_or_expr3545 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_and_expr_in_or_expr3551 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_not_expr_in_and_expr3635 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_AND_in_and_expr3667 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_not_expr_in_and_expr3673 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_NOT_in_not_expr3749 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_eq_expr_in_not_expr3801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_expr_in_eq_expr3855 = new BitSet(new long[]{0x2000000040000002L});
    public static final BitSet FOLLOW_EQ_in_eq_expr3890 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_NEQ_in_eq_expr3933 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_comp_expr_in_eq_expr3977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_expr_in_comp_expr4040 = new BitSet(new long[]{0x0024002800000002L});
    public static final BitSet FOLLOW_LT_in_comp_expr4077 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_LE_in_comp_expr4123 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_GE_in_comp_expr4167 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_GT_in_comp_expr4211 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_add_expr_in_comp_expr4262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mult_expr_in_add_expr4324 = new BitSet(new long[]{0x0100000000000022L});
    public static final BitSet FOLLOW_ADD_in_add_expr4359 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_MIN_in_add_expr4402 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_mult_expr_in_add_expr4455 = new BitSet(new long[]{0x0100000000000022L});
    public static final BitSet FOLLOW_min_expr_in_mult_expr4576 = new BitSet(new long[]{0x1000000000020002L});
    public static final BitSet FOLLOW_MUL_in_mult_expr4610 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_DIV_in_mult_expr4653 = new BitSet(new long[]{0x0758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_min_expr_in_mult_expr4707 = new BitSet(new long[]{0x1000000000020002L});
    public static final BitSet FOLLOW_MIN_in_min_expr4784 = new BitSet(new long[]{0x0658104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_term_in_min_expr4838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term4872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQ_in_term4915 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_term4917 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQ_in_term4919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PREFIDENT_in_term4954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_term4991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_term5033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTVAL_in_term5074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALEXP_in_term5091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doubleval_in_term5125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_funid_in_term5163 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_LP_in_term5165 = new BitSet(new long[]{0x8758104580141200L,0x0000000001810030L});
    public static final BitSet FOLLOW_exprs_in_term5167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_term5169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNC_in_term5223 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_LP_in_term5225 = new BitSet(new long[]{0x0648000100000200L,0x0000000000000010L});
    public static final BitSet FOLLOW_funid_in_term5227 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_COMMA_in_term5229 = new BitSet(new long[]{0x8758104580141200L,0x0000000001810030L});
    public static final BitSet FOLLOW_exprs_in_term5231 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_term5233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_term5251 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_term5253 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_term5255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAXI_in_funid5299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINI_in_funid5343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOOR_in_funid5385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CEIL_in_funid5426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_POW_in_funid5468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MOD_in_funid5511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LOG_in_funid5554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_exprs5621 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_exprs5659 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_exprs5665 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_SYSTEM_in_system_decl5719 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_system_decl5721 = new BitSet(new long[]{0x0810444020000000L});
    public static final BitSet FOLLOW_LP_in_system_decl5757 = new BitSet(new long[]{0x0001080000084500L,0x0000000000001080L});
    public static final BitSet FOLLOW_parameter_in_system_decl5761 = new BitSet(new long[]{0x0000000000001000L,0x0000000000010000L});
    public static final BitSet FOLLOW_COMMA_in_system_decl5793 = new BitSet(new long[]{0x0001080000084500L,0x0000000000001080L});
    public static final BitSet FOLLOW_parameter_in_system_decl5797 = new BitSet(new long[]{0x0000000000001000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_system_decl5827 = new BitSet(new long[]{0x0810444020000000L});
    public static final BitSet FOLLOW_system_var_in_system_decl5831 = new BitSet(new long[]{0x0810444020000000L});
    public static final BitSet FOLLOW_system_algebra_in_system_decl5835 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ENDSYSTEM_in_system_decl5838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_instance_in_system_var5884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_label_decl_in_system_var5917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_init_decl_in_system_var5953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_system_instance5998 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_LP_in_system_instance6000 = new BitSet(new long[]{0x8758104580141200L,0x0000000001810030L});
    public static final BitSet FOLLOW_exprs_in_system_instance6002 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_system_instance6004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADAPTIVE_in_adaptive_system6066 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_start_system_in_adaptive_system6103 = new BitSet(new long[]{0x0002000000400000L});
    public static final BitSet FOLLOW_adaptive_command_in_adaptive_system6151 = new BitSet(new long[]{0x0002000000400000L});
    public static final BitSet FOLLOW_ENDADAPTIVE_in_adaptive_system6183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INIT_in_start_system6195 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_AT_in_start_system6197 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_system_instance_in_start_system6199 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_start_system6201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBC_in_adaptive_command6272 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_adaptive_command6274 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_adaptive_command6313 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_expr_in_adaptive_command6315 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_RBC_in_adaptive_command6318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RARROW_in_adaptive_command6356 = new BitSet(new long[]{0x875A104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_adaptive_actions_in_adaptive_command6360 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_adaptive_update_in_adaptive_command6367 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SEMI_in_adaptive_command6391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_adaptive_action_in_adaptive_actions6443 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_ADD_in_adaptive_actions6472 = new BitSet(new long[]{0x8758104580140200L,0x0000000001800030L});
    public static final BitSet FOLLOW_adaptive_action_in_adaptive_actions6476 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_in_adaptive_action6512 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COL_in_adaptive_action6514 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_adaptive_update_in_adaptive_action6516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBC_in_adaptive_update6547 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_system_instance_in_adaptive_update6549 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_OR_in_adaptive_update6585 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_procedure_instance_in_adaptive_update6587 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_RBC_in_adaptive_update6613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_procedure_instance6631 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_EQ_in_procedure_instance6633 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_procedure_instance6637 = new BitSet(new long[]{0x0011000000000002L});
    public static final BitSet FOLLOW_LP_in_procedure_instance6642 = new BitSet(new long[]{0x8758104580141200L,0x0000000001810030L});
    public static final BitSet FOLLOW_exprs_in_procedure_instance6644 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_procedure_instance6646 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_LB_in_procedure_instance6691 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_renamings_in_procedure_instance6693 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_procedure_instance6696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_module_compo_in_system_algebra6731 = new BitSet(new long[]{0x0000000000000000L,0x000000000000000EL});
    public static final BitSet FOLLOW_OR_in_system_algebra6735 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_LB_in_system_algebra6737 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_channels_in_system_algebra6739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_RB_in_system_algebra6741 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_system_algebra6743 = new BitSet(new long[]{0x0010004000000000L});
    public static final BitSet FOLLOW_module_compo_in_system_algebra6745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAR2_in_system_algebra6765 = new BitSet(new long[]{0x0010004000000000L});
    public static final BitSet FOLLOW_module_compo_in_system_algebra6767 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_PAR3_in_system_algebra6789 = new BitSet(new long[]{0x0010004000000000L});
    public static final BitSet FOLLOW_module_compo_in_system_algebra6791 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_module_compo6816 = new BitSet(new long[]{0x0002000000020000L});
    public static final BitSet FOLLOW_channels_hiding_in_module_compo6819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_channels_renaming_in_module_compo6823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LP_in_module_compo6828 = new BitSet(new long[]{0x0010004000000000L});
    public static final BitSet FOLLOW_system_algebra_in_module_compo6830 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_RP_in_module_compo6832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_channels6840 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_COMMA_in_channels6843 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_channels6845 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_DIV_in_channels_hiding6855 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_LBC_in_channels_hiding6857 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_channels_in_channels_hiding6859 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_RBC_in_channels_hiding6861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBC_in_channels_renaming6869 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_channel_renaming_in_channels_renaming6871 = new BitSet(new long[]{0x0000000000001000L,0x0000000000004000L});
    public static final BitSet FOLLOW_COMMA_in_channels_renaming6874 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_channel_renaming_in_channels_renaming6876 = new BitSet(new long[]{0x0000000000001000L,0x0000000000004000L});
    public static final BitSet FOLLOW_RBC_in_channels_renaming6880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_channel_renaming6888 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_LARROW_in_channel_renaming6890 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_channel_renaming6892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTVAL_in_doubleval6911 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_doubleval6915 = new BitSet(new long[]{0x0000100000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_set_in_doubleval6917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQ_in_synpred41_PRISMModelGrammar1519 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_synpred41_PRISMModelGrammar1521 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQ_in_synpred41_PRISMModelGrammar1523 = new BitSet(new long[]{0x0000000000000002L});

}