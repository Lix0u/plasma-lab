/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
// $ANTLR 3.4 ALTL.g 2017-02-23 16:23:36

  package fr.inria.plasmalab.altl.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class ALTLLexer extends Lexer {
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
    public static final int END=18;
    public static final int EQ=19;
    public static final int FALSE=20;
    public static final int FATALLY=21;
    public static final int FLOATING=22;
    public static final int GE=23;
    public static final int GLOBALLY=24;
    public static final int GT=25;
    public static final int ID=26;
    public static final int IDENT=27;
    public static final int IMP=28;
    public static final int LB=29;
    public static final int LC=30;
    public static final int LE=31;
    public static final int LP=32;
    public static final int LT=33;
    public static final int MIN=34;
    public static final int MUL=35;
    public static final int NEQ=36;
    public static final int NEXT=37;
    public static final int NOT=38;
    public static final int OCCURS=39;
    public static final int OPTIMIZE=40;
    public static final int OR=41;
    public static final int OVERRIDE=42;
    public static final int QUOTE=43;
    public static final int RB=44;
    public static final int RC=45;
    public static final int REWARD=46;
    public static final int RP=47;
    public static final int SEMI=48;
    public static final int SH=49;
    public static final int TRUE=50;
    public static final int UNTIL=51;
    public static final int WEAK=52;
    public static final int WHITESPACE=53;
    public static final int Tokens=54;

    // delegates
    public ALTL_FormulaTokens gFormulaTokens;
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {gFormulaTokens};
    }

    public ALTLLexer() {} 
    public ALTLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ALTLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gFormulaTokens = new ALTL_FormulaTokens(input, state, this);
    }
    public String getGrammarFileName() { return "ALTL.g"; }

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            int _type = QUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALTL.g:300:6: ( '\\'' )
            // ALTL.g:300:8: '\\''
            {
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUOTE"

    // $ANTLR start "OCCURS"
    public final void mOCCURS() throws RecognitionException {
        try {
            int _type = OCCURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ALTL.g:301:7: ( '==>' )
            // ALTL.g:301:9: '==>'
            {
            match("==>"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCCURS"

    public void mTokens() throws RecognitionException {
        // ALTL.g:1:8: ( QUOTE | OCCURS | FormulaTokens. Tokens )
        int alt1=3;
        switch ( input.LA(1) ) {
        case '\'':
            {
            alt1=1;
            }
            break;
        case '=':
            {
            int LA1_2 = input.LA(2);

            if ( (LA1_2=='=') ) {
                alt1=2;
            }
            else {
                alt1=3;
            }
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
        case '!':
        case '\"':
        case '#':
        case '&':
        case '(':
        case ')':
        case '*':
        case '+':
        case ',':
        case '-':
        case '/':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case ':':
        case ';':
        case '<':
        case '>':
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '[':
        case ']':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
        case '{':
        case '|':
        case '}':
            {
            alt1=3;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 1, 0, input);

            throw nvae;

        }

        switch (alt1) {
            case 1 :
                // ALTL.g:1:10: QUOTE
                {
                mQUOTE(); 


                }
                break;
            case 2 :
                // ALTL.g:1:16: OCCURS
                {
                mOCCURS(); 


                }
                break;
            case 3 :
                // ALTL.g:1:23: FormulaTokens. Tokens
                {
                gFormulaTokens.mTokens(); 


                }
                break;

        }

    }


 

}