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
// $ANTLR 3.4 Formula.g 2017-02-23 16:09:42

package fr.inria.plasmalab.bltl.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class FormulaLexer extends Lexer {
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
    public Formula_FormulaTokens gFormulaTokens;
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {gFormulaTokens};
    }

    public FormulaLexer() {} 
    public FormulaLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public FormulaLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gFormulaTokens = new Formula_FormulaTokens(input, state, this);
    }
    public String getGrammarFileName() { return "Formula.g"; }

    // $ANTLR start "DUMMY"
    public final void mDUMMY() throws RecognitionException {
        try {
            int _type = DUMMY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Formula.g:181:6: ( 'dummy' )
            // Formula.g:181:8: 'dummy'
            {
            match("dummy"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DUMMY"

    public void mTokens() throws RecognitionException {
        // Formula.g:1:8: ( DUMMY | FormulaTokens. Tokens )
        int alt1=2;
        int LA1_0 = input.LA(1);

        if ( (LA1_0=='d') ) {
            int LA1_1 = input.LA(2);

            if ( (LA1_1=='u') ) {
                int LA1_3 = input.LA(3);

                if ( (LA1_3=='m') ) {
                    int LA1_4 = input.LA(4);

                    if ( (LA1_4=='m') ) {
                        int LA1_5 = input.LA(5);

                        if ( (LA1_5=='y') ) {
                            int LA1_6 = input.LA(6);

                            if ( (LA1_6=='.'||(LA1_6 >= '0' && LA1_6 <= '9')||(LA1_6 >= 'A' && LA1_6 <= 'Z')||LA1_6=='_'||(LA1_6 >= 'a' && LA1_6 <= 'z')) ) {
                                alt1=2;
                            }
                            else {
                                alt1=1;
                            }
                        }
                        else {
                            alt1=2;
                        }
                    }
                    else {
                        alt1=2;
                    }
                }
                else {
                    alt1=2;
                }
            }
            else {
                alt1=2;
            }
        }
        else if ( ((LA1_0 >= '\t' && LA1_0 <= '\n')||LA1_0=='\r'||(LA1_0 >= ' ' && LA1_0 <= '#')||LA1_0=='&'||(LA1_0 >= '(' && LA1_0 <= '-')||(LA1_0 >= '/' && LA1_0 <= '>')||(LA1_0 >= 'A' && LA1_0 <= '[')||LA1_0==']'||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'c')||(LA1_0 >= 'e' && LA1_0 <= '}')) ) {
            alt1=2;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("", 1, 0, input);

            throw nvae;

        }
        switch (alt1) {
            case 1 :
                // Formula.g:1:10: DUMMY
                {
                mDUMMY(); 


                }
                break;
            case 2 :
                // Formula.g:1:16: FormulaTokens. Tokens
                {
                gFormulaTokens.mTokens(); 


                }
                break;

        }

    }


 

}