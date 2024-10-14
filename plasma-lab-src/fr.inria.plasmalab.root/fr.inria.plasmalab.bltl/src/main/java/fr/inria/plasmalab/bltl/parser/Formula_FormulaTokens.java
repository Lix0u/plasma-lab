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
// $ANTLR 3.4 FormulaTokens.g 2017-02-23 16:09:42

package fr.inria.plasmalab.bltl.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class Formula_FormulaTokens extends Lexer {
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
    public static final int Tokens=53;

    // delegates
    // delegators
    public FormulaLexer gFormula;
    public FormulaLexer gParent;
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public Formula_FormulaTokens() {} 
    public Formula_FormulaTokens(CharStream input, FormulaLexer gFormula) {
        this(input, new RecognizerSharedState(), gFormula);
    }
    public Formula_FormulaTokens(CharStream input, RecognizerSharedState state, FormulaLexer gFormula) {
        super(input,state);
        this.gFormula = gFormula;
        gParent = gFormula;
    }
    public String getGrammarFileName() { return "FormulaTokens.g"; }

    // $ANTLR start "BLTL"
    public final void mBLTL() throws RecognitionException {
        try {
            int _type = BLTL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:7:5: ( 'BLTL' )
            // FormulaTokens.g:7:7: 'BLTL'
            {
            match("BLTL"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BLTL"

    // $ANTLR start "DECLARE"
    public final void mDECLARE() throws RecognitionException {
        try {
            int _type = DECLARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:8:8: ( 'declare' )
            // FormulaTokens.g:8:10: 'declare'
            {
            match("declare"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECLARE"

    // $ANTLR start "OPTIMIZE"
    public final void mOPTIMIZE() throws RecognitionException {
        try {
            int _type = OPTIMIZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:9:9: ( 'optimize' )
            // FormulaTokens.g:9:11: 'optimize'
            {
            match("optimize"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OPTIMIZE"

    // $ANTLR start "OVERRIDE"
    public final void mOVERRIDE() throws RecognitionException {
        try {
            int _type = OVERRIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:10:9: ( 'override' )
            // FormulaTokens.g:10:11: 'override'
            {
            match("override"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OVERRIDE"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:11:4: ( 'end' )
            // FormulaTokens.g:11:6: 'end'
            {
            match("end"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "DEADLOCK"
    public final void mDEADLOCK() throws RecognitionException {
        try {
            int _type = DEADLOCK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:12:9: ( 'deadlock' )
            // FormulaTokens.g:12:11: 'deadlock'
            {
            match("deadlock"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DEADLOCK"

    // $ANTLR start "UNTIL"
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:15:6: ( 'U' )
            // FormulaTokens.g:15:8: 'U'
            {
            match('U'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNTIL"

    // $ANTLR start "WEAK"
    public final void mWEAK() throws RecognitionException {
        try {
            int _type = WEAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:16:5: ( 'W' )
            // FormulaTokens.g:16:7: 'W'
            {
            match('W'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WEAK"

    // $ANTLR start "FATALLY"
    public final void mFATALLY() throws RecognitionException {
        try {
            int _type = FATALLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:17:8: ( 'F' )
            // FormulaTokens.g:17:10: 'F'
            {
            match('F'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FATALLY"

    // $ANTLR start "GLOBALLY"
    public final void mGLOBALLY() throws RecognitionException {
        try {
            int _type = GLOBALLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:18:9: ( 'G' )
            // FormulaTokens.g:18:11: 'G'
            {
            match('G'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GLOBALLY"

    // $ANTLR start "NEXT"
    public final void mNEXT() throws RecognitionException {
        try {
            int _type = NEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:19:5: ( 'X' )
            // FormulaTokens.g:19:7: 'X'
            {
            match('X'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEXT"

    // $ANTLR start "REWARD"
    public final void mREWARD() throws RecognitionException {
        try {
            int _type = REWARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:20:7: ( 'R' )
            // FormulaTokens.g:20:9: 'R'
            {
            match('R'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REWARD"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:24:7: ( 'false' )
            // FormulaTokens.g:24:9: 'false'
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
    // $ANTLR end "FALSE"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:25:6: ( 'true' )
            // FormulaTokens.g:25:8: 'true'
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
    // $ANTLR end "TRUE"

    // $ANTLR start "ADD"
    public final void mADD() throws RecognitionException {
        try {
            int _type = ADD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:28:4: ( '+' )
            // FormulaTokens.g:28:6: '+'
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
    // $ANTLR end "ADD"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:29:4: ( '-' )
            // FormulaTokens.g:29:6: '-'
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
    // $ANTLR end "MIN"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            int _type = MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:30:4: ( '*' )
            // FormulaTokens.g:30:6: '*'
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
    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:31:4: ( '/' )
            // FormulaTokens.g:31:6: '/'
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
    // $ANTLR end "DIV"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:34:4: ( '!' )
            // FormulaTokens.g:34:6: '!'
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
    // $ANTLR end "NOT"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:35:4: ( '&' )
            // FormulaTokens.g:35:6: '&'
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
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:36:3: ( '|' )
            // FormulaTokens.g:36:5: '|'
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
    // $ANTLR end "OR"

    // $ANTLR start "IMP"
    public final void mIMP() throws RecognitionException {
        try {
            int _type = IMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:37:4: ( '=>' )
            // FormulaTokens.g:37:6: '=>'
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
    // $ANTLR end "IMP"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:40:3: ( '=' )
            // FormulaTokens.g:40:5: '='
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
    // $ANTLR end "EQ"

    // $ANTLR start "NEQ"
    public final void mNEQ() throws RecognitionException {
        try {
            int _type = NEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:41:4: ( '!=' )
            // FormulaTokens.g:41:6: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEQ"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:42:3: ( '<' )
            // FormulaTokens.g:42:5: '<'
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
    // $ANTLR end "LT"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:43:3: ( '<=' )
            // FormulaTokens.g:43:5: '<='
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
    // $ANTLR end "LE"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:44:3: ( '>=' )
            // FormulaTokens.g:44:5: '>='
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
    // $ANTLR end "GE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:45:3: ( '>' )
            // FormulaTokens.g:45:5: '>'
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
    // $ANTLR end "GT"

    // $ANTLR start "LP"
    public final void mLP() throws RecognitionException {
        try {
            int _type = LP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:49:3: ( '(' )
            // FormulaTokens.g:49:5: '('
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
    // $ANTLR end "LP"

    // $ANTLR start "RP"
    public final void mRP() throws RecognitionException {
        try {
            int _type = RP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:50:3: ( ')' )
            // FormulaTokens.g:50:5: ')'
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
    // $ANTLR end "RP"

    // $ANTLR start "LB"
    public final void mLB() throws RecognitionException {
        try {
            int _type = LB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:51:3: ( '[' )
            // FormulaTokens.g:51:5: '['
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
    // $ANTLR end "LB"

    // $ANTLR start "RB"
    public final void mRB() throws RecognitionException {
        try {
            int _type = RB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:52:3: ( ']' )
            // FormulaTokens.g:52:5: ']'
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
    // $ANTLR end "RB"

    // $ANTLR start "LC"
    public final void mLC() throws RecognitionException {
        try {
            int _type = LC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:53:3: ( '{' )
            // FormulaTokens.g:53:5: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LC"

    // $ANTLR start "RC"
    public final void mRC() throws RecognitionException {
        try {
            int _type = RC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:54:3: ( '}' )
            // FormulaTokens.g:54:5: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RC"

    // $ANTLR start "SH"
    public final void mSH() throws RecognitionException {
        try {
            int _type = SH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:55:3: ( '#' )
            // FormulaTokens.g:55:5: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SH"

    // $ANTLR start "COL"
    public final void mCOL() throws RecognitionException {
        try {
            int _type = COL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:56:4: ( ':' )
            // FormulaTokens.g:56:5: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COL"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:57:5: ( ';' )
            // FormulaTokens.g:57:6: ';'
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
    // $ANTLR end "SEMI"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:58:6: ( ',' )
            // FormulaTokens.g:58:7: ','
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
    // $ANTLR end "COMMA"

    // $ANTLR start "DQ"
    public final void mDQ() throws RecognitionException {
        try {
            int _type = DQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:59:3: ( '\"' )
            // FormulaTokens.g:59:5: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DQ"

    // $ANTLR start "COLEQ"
    public final void mCOLEQ() throws RecognitionException {
        try {
            int _type = COLEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:60:6: ( ':=' )
            // FormulaTokens.g:60:8: ':='
            {
            match(":="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLEQ"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            // FormulaTokens.g:67:13: ( '.' )
            // FormulaTokens.g:67:15: '.'
            {
            match('.'); 

            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DIGITS"
    public final void mDIGITS() throws RecognitionException {
        try {
            int _type = DIGITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:69:8: ( ( '0' .. '9' )+ )
            // FormulaTokens.g:69:10: ( '0' .. '9' )+
            {
            // FormulaTokens.g:69:10: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // FormulaTokens.g:
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
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGITS"

    // $ANTLR start "FLOATING"
    public final void mFLOATING() throws RecognitionException {
        try {
            int _type = FLOATING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:70:10: ( DIGITS '.' DIGITS ( ( 'e' | 'E' ) ( '-' | '+' )? DIGITS )? )
            // FormulaTokens.g:70:12: DIGITS '.' DIGITS ( ( 'e' | 'E' ) ( '-' | '+' )? DIGITS )?
            {
            mDIGITS(); 


            match('.'); 

            mDIGITS(); 


            // FormulaTokens.g:70:30: ( ( 'e' | 'E' ) ( '-' | '+' )? DIGITS )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='E'||LA3_0=='e') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // FormulaTokens.g:70:31: ( 'e' | 'E' ) ( '-' | '+' )? DIGITS
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // FormulaTokens.g:70:43: ( '-' | '+' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='+'||LA2_0=='-') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // FormulaTokens.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            }
                            break;

                    }


                    mDIGITS(); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOATING"

    // $ANTLR start "ALPHA"
    public final void mALPHA() throws RecognitionException {
        try {
            // FormulaTokens.g:72:16: ( 'a' .. 'z' | 'A' .. 'Z' )
            // FormulaTokens.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ALPHA"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            // FormulaTokens.g:73:13: ( ( ALPHA | '_' ) ( ALPHA | '_' | '0' .. '9' )* )
            // FormulaTokens.g:73:15: ( ALPHA | '_' ) ( ALPHA | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // FormulaTokens.g:73:29: ( ALPHA | '_' | '0' .. '9' )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')||(LA4_0 >= 'A' && LA4_0 <= 'Z')||LA4_0=='_'||(LA4_0 >= 'a' && LA4_0 <= 'z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // FormulaTokens.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
            	    break loop4;
                }
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:74:7: ( ID ( DOT ID )* )
            // FormulaTokens.g:74:9: ID ( DOT ID )*
            {
            mID(); 


            // FormulaTokens.g:74:12: ( DOT ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='.') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // FormulaTokens.g:74:13: DOT ID
            	    {
            	    mDOT(); 


            	    mID(); 


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:77:12: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // FormulaTokens.g:77:14: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // FormulaTokens.g:77:14: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // FormulaTokens.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
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
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHITESPACE"

    // $ANTLR start "COMMENTS"
    public final void mCOMMENTS() throws RecognitionException {
        try {
            int _type = COMMENTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // FormulaTokens.g:78:10: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // FormulaTokens.g:78:14: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 



            // FormulaTokens.g:78:19: (~ ( '\\n' | '\\r' ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0 >= '\u0000' && LA7_0 <= '\t')||(LA7_0 >= '\u000B' && LA7_0 <= '\f')||(LA7_0 >= '\u000E' && LA7_0 <= '\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // FormulaTokens.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
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
            	    break loop7;
                }
            } while (true);


            // FormulaTokens.g:78:33: ( '\\r' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\r') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // FormulaTokens.g:78:33: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENTS"

    public void mTokens() throws RecognitionException {
        // FormulaTokens.g:1:8: ( BLTL | DECLARE | OPTIMIZE | OVERRIDE | END | DEADLOCK | UNTIL | WEAK | FATALLY | GLOBALLY | NEXT | REWARD | FALSE | TRUE | ADD | MIN | MUL | DIV | NOT | AND | OR | IMP | EQ | NEQ | LT | LE | GE | GT | LP | RP | LB | RB | LC | RC | SH | COL | SEMI | COMMA | DQ | COLEQ | DIGITS | FLOATING | IDENT | WHITESPACE | COMMENTS )
        int alt9=45;
        alt9 = dfa9.predict(input);
        switch (alt9) {
            case 1 :
                // FormulaTokens.g:1:10: BLTL
                {
                mBLTL(); 


                }
                break;
            case 2 :
                // FormulaTokens.g:1:15: DECLARE
                {
                mDECLARE(); 


                }
                break;
            case 3 :
                // FormulaTokens.g:1:23: OPTIMIZE
                {
                mOPTIMIZE(); 


                }
                break;
            case 4 :
                // FormulaTokens.g:1:32: OVERRIDE
                {
                mOVERRIDE(); 


                }
                break;
            case 5 :
                // FormulaTokens.g:1:41: END
                {
                mEND(); 


                }
                break;
            case 6 :
                // FormulaTokens.g:1:45: DEADLOCK
                {
                mDEADLOCK(); 


                }
                break;
            case 7 :
                // FormulaTokens.g:1:54: UNTIL
                {
                mUNTIL(); 


                }
                break;
            case 8 :
                // FormulaTokens.g:1:60: WEAK
                {
                mWEAK(); 


                }
                break;
            case 9 :
                // FormulaTokens.g:1:65: FATALLY
                {
                mFATALLY(); 


                }
                break;
            case 10 :
                // FormulaTokens.g:1:73: GLOBALLY
                {
                mGLOBALLY(); 


                }
                break;
            case 11 :
                // FormulaTokens.g:1:82: NEXT
                {
                mNEXT(); 


                }
                break;
            case 12 :
                // FormulaTokens.g:1:87: REWARD
                {
                mREWARD(); 


                }
                break;
            case 13 :
                // FormulaTokens.g:1:94: FALSE
                {
                mFALSE(); 


                }
                break;
            case 14 :
                // FormulaTokens.g:1:100: TRUE
                {
                mTRUE(); 


                }
                break;
            case 15 :
                // FormulaTokens.g:1:105: ADD
                {
                mADD(); 


                }
                break;
            case 16 :
                // FormulaTokens.g:1:109: MIN
                {
                mMIN(); 


                }
                break;
            case 17 :
                // FormulaTokens.g:1:113: MUL
                {
                mMUL(); 


                }
                break;
            case 18 :
                // FormulaTokens.g:1:117: DIV
                {
                mDIV(); 


                }
                break;
            case 19 :
                // FormulaTokens.g:1:121: NOT
                {
                mNOT(); 


                }
                break;
            case 20 :
                // FormulaTokens.g:1:125: AND
                {
                mAND(); 


                }
                break;
            case 21 :
                // FormulaTokens.g:1:129: OR
                {
                mOR(); 


                }
                break;
            case 22 :
                // FormulaTokens.g:1:132: IMP
                {
                mIMP(); 


                }
                break;
            case 23 :
                // FormulaTokens.g:1:136: EQ
                {
                mEQ(); 


                }
                break;
            case 24 :
                // FormulaTokens.g:1:139: NEQ
                {
                mNEQ(); 


                }
                break;
            case 25 :
                // FormulaTokens.g:1:143: LT
                {
                mLT(); 


                }
                break;
            case 26 :
                // FormulaTokens.g:1:146: LE
                {
                mLE(); 


                }
                break;
            case 27 :
                // FormulaTokens.g:1:149: GE
                {
                mGE(); 


                }
                break;
            case 28 :
                // FormulaTokens.g:1:152: GT
                {
                mGT(); 


                }
                break;
            case 29 :
                // FormulaTokens.g:1:155: LP
                {
                mLP(); 


                }
                break;
            case 30 :
                // FormulaTokens.g:1:158: RP
                {
                mRP(); 


                }
                break;
            case 31 :
                // FormulaTokens.g:1:161: LB
                {
                mLB(); 


                }
                break;
            case 32 :
                // FormulaTokens.g:1:164: RB
                {
                mRB(); 


                }
                break;
            case 33 :
                // FormulaTokens.g:1:167: LC
                {
                mLC(); 


                }
                break;
            case 34 :
                // FormulaTokens.g:1:170: RC
                {
                mRC(); 


                }
                break;
            case 35 :
                // FormulaTokens.g:1:173: SH
                {
                mSH(); 


                }
                break;
            case 36 :
                // FormulaTokens.g:1:176: COL
                {
                mCOL(); 


                }
                break;
            case 37 :
                // FormulaTokens.g:1:180: SEMI
                {
                mSEMI(); 


                }
                break;
            case 38 :
                // FormulaTokens.g:1:185: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 39 :
                // FormulaTokens.g:1:191: DQ
                {
                mDQ(); 


                }
                break;
            case 40 :
                // FormulaTokens.g:1:194: COLEQ
                {
                mCOLEQ(); 


                }
                break;
            case 41 :
                // FormulaTokens.g:1:200: DIGITS
                {
                mDIGITS(); 


                }
                break;
            case 42 :
                // FormulaTokens.g:1:207: FLOATING
                {
                mFLOATING(); 


                }
                break;
            case 43 :
                // FormulaTokens.g:1:216: IDENT
                {
                mIDENT(); 


                }
                break;
            case 44 :
                // FormulaTokens.g:1:222: WHITESPACE
                {
                mWHITESPACE(); 


                }
                break;
            case 45 :
                // FormulaTokens.g:1:233: COMMENTS
                {
                mCOMMENTS(); 


                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA9_eotS =
        "\1\uffff\4\43\1\52\1\53\1\54\1\55\1\56\1\57\2\43\3\uffff\1\63\1"+
        "\65\2\uffff\1\67\1\71\1\73\7\uffff\1\75\3\uffff\1\76\2\uffff\5\43"+
        "\6\uffff\2\43\16\uffff\5\43\1\115\2\43\1\120\4\43\1\uffff\1\43\1"+
        "\126\1\uffff\4\43\1\133\1\uffff\4\43\1\uffff\1\140\3\43\1\uffff"+
        "\1\144\1\145\1\146\3\uffff";
    static final String DFA9_eofS =
        "\147\uffff";
    static final String DFA9_minS =
        "\1\11\1\114\1\145\1\160\1\156\6\56\1\141\1\162\3\uffff\1\57\1\75"+
        "\2\uffff\1\76\2\75\7\uffff\1\75\3\uffff\1\56\2\uffff\1\124\1\141"+
        "\1\164\1\145\1\144\6\uffff\1\154\1\165\16\uffff\1\114\1\154\1\144"+
        "\1\151\1\162\1\56\1\163\1\145\1\56\1\141\1\154\1\155\1\162\1\uffff"+
        "\1\145\1\56\1\uffff\1\162\1\157\2\151\1\56\1\uffff\1\145\1\143\1"+
        "\172\1\144\1\uffff\1\56\1\153\2\145\1\uffff\3\56\3\uffff";
    static final String DFA9_maxS =
        "\1\175\1\114\1\145\1\166\1\156\6\172\1\141\1\162\3\uffff\1\57\1"+
        "\75\2\uffff\1\76\2\75\7\uffff\1\75\3\uffff\1\71\2\uffff\1\124\1"+
        "\143\1\164\1\145\1\144\6\uffff\1\154\1\165\16\uffff\1\114\1\154"+
        "\1\144\1\151\1\162\1\172\1\163\1\145\1\172\1\141\1\154\1\155\1\162"+
        "\1\uffff\1\145\1\172\1\uffff\1\162\1\157\2\151\1\172\1\uffff\1\145"+
        "\1\143\1\172\1\144\1\uffff\1\172\1\153\2\145\1\uffff\3\172\3\uffff";
    static final String DFA9_acceptS =
        "\15\uffff\1\17\1\20\1\21\2\uffff\1\24\1\25\3\uffff\1\35\1\36\1\37"+
        "\1\40\1\41\1\42\1\43\1\uffff\1\45\1\46\1\47\1\uffff\1\53\1\54\5"+
        "\uffff\1\7\1\10\1\11\1\12\1\13\1\14\2\uffff\1\55\1\22\1\30\1\23"+
        "\1\26\1\27\1\32\1\31\1\33\1\34\1\50\1\44\1\51\1\52\15\uffff\1\5"+
        "\2\uffff\1\1\5\uffff\1\16\4\uffff\1\15\4\uffff\1\2\3\uffff\1\6\1"+
        "\3\1\4";
    static final String DFA9_specialS =
        "\147\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\44\2\uffff\1\44\22\uffff\1\44\1\21\1\41\1\35\2\uffff\1\22"+
            "\1\uffff\1\27\1\30\1\17\1\15\1\40\1\16\1\uffff\1\20\12\42\1"+
            "\36\1\37\1\25\1\24\1\26\2\uffff\1\43\1\1\3\43\1\7\1\10\12\43"+
            "\1\12\2\43\1\5\1\43\1\6\1\11\2\43\1\31\1\uffff\1\32\1\uffff"+
            "\1\43\1\uffff\3\43\1\2\1\4\1\13\10\43\1\3\4\43\1\14\6\43\1\33"+
            "\1\23\1\34",
            "\1\45",
            "\1\46",
            "\1\47\5\uffff\1\50",
            "\1\51",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\60",
            "\1\61",
            "",
            "",
            "",
            "\1\62",
            "\1\64",
            "",
            "",
            "\1\66",
            "\1\70",
            "\1\72",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\74",
            "",
            "",
            "",
            "\1\77\1\uffff\12\42",
            "",
            "",
            "\1\100",
            "\1\102\1\uffff\1\101",
            "\1\103",
            "\1\104",
            "\1\105",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\106",
            "\1\107",
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
            "",
            "",
            "",
            "",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\116",
            "\1\117",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "",
            "\1\125",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\141",
            "\1\142",
            "\1\143",
            "",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\43\1\uffff\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( BLTL | DECLARE | OPTIMIZE | OVERRIDE | END | DEADLOCK | UNTIL | WEAK | FATALLY | GLOBALLY | NEXT | REWARD | FALSE | TRUE | ADD | MIN | MUL | DIV | NOT | AND | OR | IMP | EQ | NEQ | LT | LE | GE | GT | LP | RP | LB | RB | LC | RC | SH | COL | SEMI | COMMA | DQ | COLEQ | DIGITS | FLOATING | IDENT | WHITESPACE | COMMENTS );";
        }
    }
 

}