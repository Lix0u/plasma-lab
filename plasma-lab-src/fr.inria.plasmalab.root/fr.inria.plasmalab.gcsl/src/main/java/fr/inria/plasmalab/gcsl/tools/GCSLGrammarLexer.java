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
import java.util.LinkedList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class GCSLGrammarLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public GCSLGrammarLexer() {} 
	public GCSLGrammarLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public GCSLGrammarLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g"; }

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:12:7: ( '!' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:12:9: '!'
			{
			match('!'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:13:7: ( '&' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:13:9: '&'
			{
			match('&'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:14:7: ( '(' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:14:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:15:7: ( ')' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:15:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:16:7: ( '*' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:16:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:17:7: ( '+' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:17:9: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:18:7: ( ',' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:18:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:19:7: ( '-' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:19:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:20:7: ( '->' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:20:9: '->'
			{
			match("->"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:21:7: ( '.' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:21:9: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:22:7: ( '/' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:22:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:23:7: ( ';' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:23:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:24:7: ( '<' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:24:9: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:25:7: ( '<=' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:25:9: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:26:7: ( '=' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:26:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:27:7: ( '=>' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:27:9: '=>'
			{
			match("=>"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:28:7: ( '>' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:28:9: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:29:7: ( '>=' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:29:9: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:30:7: ( 'BLTL Vars:' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:30:9: 'BLTL Vars:'
			{
			match("BLTL Vars:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:31:7: ( 'Goal:' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:31:9: 'Goal:'
			{
			match("Goal:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:32:7: ( 'Hypothesis:' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:32:9: 'Hypothesis:'
			{
			match("Hypothesis:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:33:7: ( '[' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:33:9: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__31"

	// $ANTLR start "T__32"
	public final void mT__32() throws RecognitionException {
		try {
			int _type = T__32;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:34:7: ( ']' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:34:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__32"

	// $ANTLR start "T__33"
	public final void mT__33() throws RecognitionException {
		try {
			int _type = T__33;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:35:7: ( 'always' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:35:9: 'always'
			{
			match("always"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__33"

	// $ANTLR start "T__34"
	public final void mT__34() throws RecognitionException {
		try {
			int _type = T__34;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:36:7: ( 'at most' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:36:9: 'at most'
			{
			match("at most"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__34"

	// $ANTLR start "T__35"
	public final void mT__35() throws RecognitionException {
		try {
			int _type = T__35;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:37:7: ( 'at(' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:37:9: 'at('
			{
			match("at("); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__35"

	// $ANTLR start "T__36"
	public final void mT__36() throws RecognitionException {
		try {
			int _type = T__36;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:38:7: ( 'by' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:38:9: 'by'
			{
			match("by"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__36"

	// $ANTLR start "T__37"
	public final void mT__37() throws RecognitionException {
		try {
			int _type = T__37;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:39:7: ( 'does not' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:39:9: 'does not'
			{
			match("does not"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__37"

	// $ANTLR start "T__38"
	public final void mT__38() throws RecognitionException {
		try {
			int _type = T__38;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:40:7: ( 'during' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:40:9: 'during'
			{
			match("during"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__38"

	// $ANTLR start "T__39"
	public final void mT__39() throws RecognitionException {
		try {
			int _type = T__39;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:41:7: ( 'exists' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:41:9: 'exists'
			{
			match("exists"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__39"

	// $ANTLR start "T__40"
	public final void mT__40() throws RecognitionException {
		try {
			int _type = T__40;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:42:7: ( 'false' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:42:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__40"

	// $ANTLR start "T__41"
	public final void mT__41() throws RecognitionException {
		try {
			int _type = T__41;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:43:7: ( 'following' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:43:9: 'following'
			{
			match("following"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__41"

	// $ANTLR start "T__42"
	public final void mT__42() throws RecognitionException {
		try {
			int _type = T__42;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:44:7: ( 'forAll' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:44:9: 'forAll'
			{
			match("forAll"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__42"

	// $ANTLR start "T__43"
	public final void mT__43() throws RecognitionException {
		try {
			int _type = T__43;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:45:7: ( 'forever' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:45:9: 'forever'
			{
			match("forever"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__43"

	// $ANTLR start "T__44"
	public final void mT__44() throws RecognitionException {
		try {
			int _type = T__44;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:46:7: ( 'holds' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:46:9: 'holds'
			{
			match("holds"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__44"

	// $ANTLR start "T__45"
	public final void mT__45() throws RecognitionException {
		try {
			int _type = T__45;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:47:7: ( 'implies' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:47:9: 'implies'
			{
			match("implies"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__45"

	// $ANTLR start "T__46"
	public final void mT__46() throws RecognitionException {
		try {
			int _type = T__46;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:48:7: ( 'mean(' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:48:9: 'mean('
			{
			match("mean("); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__46"

	// $ANTLR start "T__47"
	public final void mT__47() throws RecognitionException {
		try {
			int _type = T__47;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:49:7: ( 'occurs' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:49:9: 'occurs'
			{
			match("occurs"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__47"

	// $ANTLR start "T__48"
	public final void mT__48() throws RecognitionException {
		try {
			int _type = T__48;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:50:7: ( 'prod(' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:50:9: 'prod('
			{
			match("prod("); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__48"

	// $ANTLR start "T__49"
	public final void mT__49() throws RecognitionException {
		try {
			int _type = T__49;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:51:7: ( 'raises' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:51:9: 'raises'
			{
			match("raises"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__49"

	// $ANTLR start "T__50"
	public final void mT__50() throws RecognitionException {
		try {
			int _type = T__50;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:52:7: ( 'sum(' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:52:9: 'sum('
			{
			match("sum("); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__50"

	// $ANTLR start "T__51"
	public final void mT__51() throws RecognitionException {
		try {
			int _type = T__51;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:53:7: ( 'then' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:53:9: 'then'
			{
			match("then"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__51"

	// $ANTLR start "T__52"
	public final void mT__52() throws RecognitionException {
		try {
			int _type = T__52;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:54:7: ( 'times' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:54:9: 'times'
			{
			match("times"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__52"

	// $ANTLR start "T__53"
	public final void mT__53() throws RecognitionException {
		try {
			int _type = T__53;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:55:7: ( 'true' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:55:9: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__53"

	// $ANTLR start "T__54"
	public final void mT__54() throws RecognitionException {
		try {
			int _type = T__54;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:56:7: ( 'whenever' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:56:9: 'whenever'
			{
			match("whenever"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__54"

	// $ANTLR start "T__55"
	public final void mT__55() throws RecognitionException {
		try {
			int _type = T__55;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:57:7: ( 'with a probability' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:57:9: 'with a probability'
			{
			match("with a probability"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__55"

	// $ANTLR start "T__56"
	public final void mT__56() throws RecognitionException {
		try {
			int _type = T__56;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:58:7: ( 'within' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:58:9: 'within'
			{
			match("within"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__56"

	// $ANTLR start "T__57"
	public final void mT__57() throws RecognitionException {
		try {
			int _type = T__57;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:59:7: ( '|' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:59:9: '|'
			{
			match('|'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__57"

	// $ANTLR start "TimeUnit"
	public final void mTimeUnit() throws RecognitionException {
		try {
			int _type = TimeUnit;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:333:9: ( 'step' )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:333:11: 'step'
			{
			match("step"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TimeUnit"

	// $ANTLR start "Natural"
	public final void mNatural() throws RecognitionException {
		try {
			int _type = Natural;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:335:8: ( ( '0' .. '9' )+ )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:335:10: ( '0' .. '9' )+
			{
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:335:10: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Natural"

	// $ANTLR start "Variable"
	public final void mVariable() throws RecognitionException {
		try {
			int _type = Variable;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:337:9: ( ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )* )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:337:11: ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			{
			if ( (input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:337:21: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Variable"

	// $ANTLR start "Ident"
	public final void mIdent() throws RecognitionException {
		try {
			int _type = Ident;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:339:6: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )* )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:339:8: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			{
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:339:8: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Ident"

	// $ANTLR start "Float"
	public final void mFloat() throws RecognitionException {
		try {
			int _type = Float;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:341:6: ( Natural '.' Natural )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:341:8: Natural '.' Natural
			{
			mNatural(); 

			match('.'); 
			mNatural(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Float"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:343:4: ( ( ' ' | '\\t' | '\\n' )+ )
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:343:6: ( ' ' | '\\t' | '\\n' )+
			{
			// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:343:6: ( ' ' | '\\t' | '\\n' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | TimeUnit | Natural | Variable | Ident | Float | WS )
		int alt5=54;
		alt5 = dfa5.predict(input);
		switch (alt5) {
			case 1 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:10: T__10
				{
				mT__10(); 

				}
				break;
			case 2 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:16: T__11
				{
				mT__11(); 

				}
				break;
			case 3 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:22: T__12
				{
				mT__12(); 

				}
				break;
			case 4 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:28: T__13
				{
				mT__13(); 

				}
				break;
			case 5 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:34: T__14
				{
				mT__14(); 

				}
				break;
			case 6 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:40: T__15
				{
				mT__15(); 

				}
				break;
			case 7 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:46: T__16
				{
				mT__16(); 

				}
				break;
			case 8 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:52: T__17
				{
				mT__17(); 

				}
				break;
			case 9 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:58: T__18
				{
				mT__18(); 

				}
				break;
			case 10 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:64: T__19
				{
				mT__19(); 

				}
				break;
			case 11 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:70: T__20
				{
				mT__20(); 

				}
				break;
			case 12 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:76: T__21
				{
				mT__21(); 

				}
				break;
			case 13 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:82: T__22
				{
				mT__22(); 

				}
				break;
			case 14 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:88: T__23
				{
				mT__23(); 

				}
				break;
			case 15 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:94: T__24
				{
				mT__24(); 

				}
				break;
			case 16 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:100: T__25
				{
				mT__25(); 

				}
				break;
			case 17 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:106: T__26
				{
				mT__26(); 

				}
				break;
			case 18 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:112: T__27
				{
				mT__27(); 

				}
				break;
			case 19 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:118: T__28
				{
				mT__28(); 

				}
				break;
			case 20 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:124: T__29
				{
				mT__29(); 

				}
				break;
			case 21 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:130: T__30
				{
				mT__30(); 

				}
				break;
			case 22 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:136: T__31
				{
				mT__31(); 

				}
				break;
			case 23 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:142: T__32
				{
				mT__32(); 

				}
				break;
			case 24 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:148: T__33
				{
				mT__33(); 

				}
				break;
			case 25 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:154: T__34
				{
				mT__34(); 

				}
				break;
			case 26 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:160: T__35
				{
				mT__35(); 

				}
				break;
			case 27 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:166: T__36
				{
				mT__36(); 

				}
				break;
			case 28 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:172: T__37
				{
				mT__37(); 

				}
				break;
			case 29 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:178: T__38
				{
				mT__38(); 

				}
				break;
			case 30 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:184: T__39
				{
				mT__39(); 

				}
				break;
			case 31 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:190: T__40
				{
				mT__40(); 

				}
				break;
			case 32 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:196: T__41
				{
				mT__41(); 

				}
				break;
			case 33 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:202: T__42
				{
				mT__42(); 

				}
				break;
			case 34 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:208: T__43
				{
				mT__43(); 

				}
				break;
			case 35 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:214: T__44
				{
				mT__44(); 

				}
				break;
			case 36 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:220: T__45
				{
				mT__45(); 

				}
				break;
			case 37 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:226: T__46
				{
				mT__46(); 

				}
				break;
			case 38 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:232: T__47
				{
				mT__47(); 

				}
				break;
			case 39 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:238: T__48
				{
				mT__48(); 

				}
				break;
			case 40 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:244: T__49
				{
				mT__49(); 

				}
				break;
			case 41 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:250: T__50
				{
				mT__50(); 

				}
				break;
			case 42 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:256: T__51
				{
				mT__51(); 

				}
				break;
			case 43 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:262: T__52
				{
				mT__52(); 

				}
				break;
			case 44 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:268: T__53
				{
				mT__53(); 

				}
				break;
			case 45 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:274: T__54
				{
				mT__54(); 

				}
				break;
			case 46 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:280: T__55
				{
				mT__55(); 

				}
				break;
			case 47 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:286: T__56
				{
				mT__56(); 

				}
				break;
			case 48 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:292: T__57
				{
				mT__57(); 

				}
				break;
			case 49 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:298: TimeUnit
				{
				mTimeUnit(); 

				}
				break;
			case 50 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:307: Natural
				{
				mNatural(); 

				}
				break;
			case 51 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:315: Variable
				{
				mVariable(); 

				}
				break;
			case 52 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:324: Ident
				{
				mIdent(); 

				}
				break;
			case 53 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:330: Float
				{
				mFloat(); 

				}
				break;
			case 54 :
				// /local/ltraonou/Developpement/Plasma/plasma-lab/fr.inria.plasmalab.gcsl/src/main/antlr3/fr/inria/plasmalab/gcsl/tools/GCSLGrammar.g:1:336: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA5 dfa5 = new DFA5(this);
	static final String DFA5_eotS =
		"\1\45\7\uffff\1\50\3\uffff\1\52\1\54\1\56\3\45\2\uffff\16\65\1\uffff\1"+
		"\111\1\65\12\uffff\3\45\3\65\1\uffff\1\121\22\65\2\uffff\3\45\1\65\3\uffff"+
		"\23\65\3\45\16\65\1\uffff\1\u008e\1\u008f\1\65\1\u0091\2\65\2\uffff\1"+
		"\45\1\65\1\uffff\2\65\1\u0099\3\65\1\u009d\1\65\1\uffff\1\65\1\uffff\1"+
		"\65\2\uffff\1\u00a1\1\uffff\1\65\1\uffff\1\65\1\45\1\u00a5\1\u00a6\1\u00a7"+
		"\1\uffff\1\65\1\u00a9\1\65\1\uffff\1\65\1\u00ac\1\u00ad\1\uffff\1\65\1"+
		"\u00af\1\45\3\uffff\1\65\1\uffff\1\u00b2\1\u00b3\2\uffff\1\65\1\uffff"+
		"\1\45\1\65\2\uffff\1\u00b7\1\45\1\u00b9\1\uffff\1\45\2\uffff";
	static final String DFA5_eofS =
		"\u00bb\uffff";
	static final String DFA5_minS =
		"\1\11\7\uffff\1\76\3\uffff\1\75\1\76\1\75\1\114\1\157\1\171\2\uffff\16"+
		"\60\1\uffff\1\56\1\60\12\uffff\1\124\1\141\1\160\1\60\1\40\1\60\1\uffff"+
		"\23\60\2\uffff\1\114\1\154\1\157\1\60\3\uffff\14\60\1\50\6\60\1\40\1\72"+
		"\1\164\1\60\1\40\10\60\1\50\1\60\1\50\1\60\1\uffff\5\60\1\40\2\uffff\1"+
		"\150\1\60\1\uffff\10\60\1\uffff\1\60\1\uffff\1\60\2\uffff\1\60\1\uffff"+
		"\1\60\1\uffff\1\60\1\145\3\60\1\uffff\3\60\1\uffff\3\60\1\uffff\2\60\1"+
		"\163\3\uffff\1\60\1\uffff\2\60\2\uffff\1\60\1\uffff\1\151\1\60\2\uffff"+
		"\1\60\1\163\1\60\1\uffff\1\72\2\uffff";
	static final String DFA5_maxS =
		"\1\174\7\uffff\1\76\3\uffff\1\75\1\76\1\75\1\114\1\157\1\171\2\uffff\16"+
		"\172\1\uffff\2\172\12\uffff\1\124\1\141\1\160\3\172\1\uffff\23\172\2\uffff"+
		"\1\114\1\154\1\157\1\172\3\uffff\23\172\1\40\1\72\1\164\16\172\1\uffff"+
		"\6\172\2\uffff\1\150\1\172\1\uffff\10\172\1\uffff\1\172\1\uffff\1\172"+
		"\2\uffff\1\172\1\uffff\1\172\1\uffff\1\172\1\145\3\172\1\uffff\3\172\1"+
		"\uffff\3\172\1\uffff\2\172\1\163\3\uffff\1\172\1\uffff\2\172\2\uffff\1"+
		"\172\1\uffff\1\151\1\172\2\uffff\1\172\1\163\1\172\1\uffff\1\72\2\uffff";
	static final String DFA5_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\uffff\1\12\1\13\1\14\6\uffff\1"+
		"\26\1\27\16\uffff\1\60\2\uffff\1\64\1\66\1\11\1\10\1\16\1\15\1\20\1\17"+
		"\1\22\1\21\6\uffff\1\63\23\uffff\1\62\1\65\4\uffff\1\31\1\32\1\33\44\uffff"+
		"\1\51\6\uffff\1\23\1\24\2\uffff\1\34\10\uffff\1\45\1\uffff\1\47\1\uffff"+
		"\1\61\1\52\1\uffff\1\54\1\uffff\1\56\5\uffff\1\37\3\uffff\1\43\3\uffff"+
		"\1\53\3\uffff\1\30\1\35\1\36\1\uffff\1\41\2\uffff\1\46\1\50\1\uffff\1"+
		"\57\2\uffff\1\42\1\44\3\uffff\1\55\1\uffff\1\40\1\25";
	static final String DFA5_specialS =
		"\u00bb\uffff}>";
	static final String[] DFA5_transitionS = {
			"\2\46\25\uffff\1\46\1\1\4\uffff\1\2\1\uffff\1\3\1\4\1\5\1\6\1\7\1\10"+
			"\1\11\1\12\12\43\1\uffff\1\13\1\14\1\15\1\16\3\uffff\1\17\4\uffff\1\20"+
			"\1\21\22\uffff\1\22\1\uffff\1\23\3\uffff\1\24\1\25\1\44\1\26\1\27\1\30"+
			"\1\44\1\31\1\32\3\44\1\33\1\44\1\34\1\35\1\44\1\36\1\37\1\40\2\44\1\41"+
			"\3\44\1\uffff\1\42",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\47",
			"",
			"",
			"",
			"\1\51",
			"\1\53",
			"\1\55",
			"\1\57",
			"\1\60",
			"\1\61",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\62\7\64\1\63\6\64",
			"\12\64\7\uffff\32\64\6\uffff\30\64\1\66\1\64",
			"\12\64\7\uffff\32\64\6\uffff\16\64\1\67\5\64\1\70\5\64",
			"\12\64\7\uffff\32\64\6\uffff\27\64\1\71\2\64",
			"\12\64\7\uffff\32\64\6\uffff\1\72\15\64\1\73\13\64",
			"\12\64\7\uffff\32\64\6\uffff\16\64\1\74\13\64",
			"\12\64\7\uffff\32\64\6\uffff\14\64\1\75\15\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\76\25\64",
			"\12\64\7\uffff\32\64\6\uffff\2\64\1\77\27\64",
			"\12\64\7\uffff\32\64\6\uffff\21\64\1\100\10\64",
			"\12\64\7\uffff\32\64\6\uffff\1\101\31\64",
			"\12\64\7\uffff\32\64\6\uffff\23\64\1\103\1\102\5\64",
			"\12\64\7\uffff\32\64\6\uffff\7\64\1\104\1\105\10\64\1\106\10\64",
			"\12\64\7\uffff\32\64\6\uffff\7\64\1\107\1\110\21\64",
			"",
			"\1\112\1\uffff\12\43\7\uffff\32\45\6\uffff\32\45",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\113",
			"\1\114",
			"\1\115",
			"\12\64\7\uffff\32\64\6\uffff\26\64\1\116\3\64",
			"\1\117\7\uffff\1\120\7\uffff\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\122\25\64",
			"\12\64\7\uffff\32\64\6\uffff\21\64\1\123\10\64",
			"\12\64\7\uffff\32\64\6\uffff\10\64\1\124\21\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\125\16\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\126\5\64\1\127\10\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\130\16\64",
			"\12\64\7\uffff\32\64\6\uffff\17\64\1\131\12\64",
			"\12\64\7\uffff\32\64\6\uffff\1\132\31\64",
			"\12\64\7\uffff\32\64\6\uffff\2\64\1\133\27\64",
			"\12\64\7\uffff\32\64\6\uffff\16\64\1\134\13\64",
			"\12\64\7\uffff\32\64\6\uffff\10\64\1\135\21\64",
			"\12\64\7\uffff\32\64\6\uffff\14\64\1\136\15\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\137\25\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\140\25\64",
			"\12\64\7\uffff\32\64\6\uffff\14\64\1\141\15\64",
			"\12\64\7\uffff\32\64\6\uffff\24\64\1\142\5\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\143\25\64",
			"\12\64\7\uffff\32\64\6\uffff\23\64\1\144\6\64",
			"",
			"",
			"\1\145",
			"\1\146",
			"\1\147",
			"\12\64\7\uffff\32\64\6\uffff\1\150\31\64",
			"",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\151\7\64",
			"\12\64\7\uffff\32\64\6\uffff\10\64\1\152\21\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\153\7\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\154\7\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\155\16\64",
			"\12\64\7\uffff\1\156\31\64\6\uffff\4\64\1\157\25\64",
			"\12\64\7\uffff\32\64\6\uffff\3\64\1\160\26\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\161\16\64",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\162\14\64",
			"\12\64\7\uffff\32\64\6\uffff\24\64\1\163\5\64",
			"\12\64\7\uffff\32\64\6\uffff\3\64\1\164\26\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\165\7\64",
			"\1\166\7\uffff\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\17\64\1\167\12\64",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\170\14\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\171\25\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\172\25\64",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\173\14\64",
			"\12\64\7\uffff\32\64\6\uffff\7\64\1\174\22\64",
			"\1\175",
			"\1\176",
			"\1\177",
			"\12\64\7\uffff\32\64\6\uffff\30\64\1\u0080\1\64",
			"\1\u0081\17\uffff\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\u0082\14\64",
			"\12\64\7\uffff\32\64\6\uffff\23\64\1\u0083\6\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u0084\25\64",
			"\12\64\7\uffff\32\64\6\uffff\16\64\1\u0085\13\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\u0086\16\64",
			"\12\64\7\uffff\32\64\6\uffff\25\64\1\u0087\4\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u0088\7\64",
			"\12\64\7\uffff\32\64\6\uffff\10\64\1\u0089\21\64",
			"\1\u008a\7\uffff\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\21\64\1\u008b\10\64",
			"\1\u008c\7\uffff\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u008d\25\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u0090\7\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u0092\25\64",
			"\1\u0093\17\uffff\12\64\7\uffff\32\64\6\uffff\10\64\1\u0094\21\64",
			"",
			"",
			"\1\u0095",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u0096\7\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\6\64\1\u0097\23\64",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u0098\7\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\26\64\1\u009a\3\64",
			"\12\64\7\uffff\32\64\6\uffff\13\64\1\u009b\16\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u009c\25\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u009e\25\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u009f\7\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u00a0\7\64",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\25\64\1\u00a2\4\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\u00a3\14\64",
			"\1\u00a4",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\10\64\1\u00a8\21\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\21\64\1\u00aa\10\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\22\64\1\u00ab\7\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\4\64\1\u00ae\25\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\1\u00b0",
			"",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\15\64\1\u00b1\14\64",
			"",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\21\64\1\u00b4\10\64",
			"",
			"\1\u00b5",
			"\12\64\7\uffff\32\64\6\uffff\6\64\1\u00b6\23\64",
			"",
			"",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"\1\u00b8",
			"\12\64\7\uffff\32\64\6\uffff\32\64",
			"",
			"\1\u00ba",
			"",
			""
	};

	static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
	static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
	static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
	static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
	static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
	static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
	static final short[][] DFA5_transition;

	static {
		int numStates = DFA5_transitionS.length;
		DFA5_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
		}
	}

	protected class DFA5 extends DFA {

		public DFA5(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 5;
			this.eot = DFA5_eot;
			this.eof = DFA5_eof;
			this.min = DFA5_min;
			this.max = DFA5_max;
			this.accept = DFA5_accept;
			this.special = DFA5_special;
			this.transition = DFA5_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | TimeUnit | Natural | Variable | Ident | Float | WS );";
		}
	}

}
