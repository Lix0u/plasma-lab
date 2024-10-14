lexer grammar FormulaTokens;

options {
  language = Java;
}
//KEYWORDS
BLTL: 'BLTL';
DECLARE: 'declare';
OPTIMIZE: 'optimize';
OVERRIDE: 'override';
END: 'end';
DEADLOCK: 'deadlock';

//MODALITIES
UNTIL: 'U';
WEAK: 'W';
FATALLY: 'F';
GLOBALLY: 'G';
NEXT: 'X';
REWARD: 'R';


// ATOMS
FALSE : 'false';
TRUE : 'true';

// ARITH
ADD: '+';
MIN: '-';
MUL: '*';
DIV: '/';

// BOOL
NOT: '!';
AND: '&';
OR: '|';
IMP: '=>';

// COMPARISONS 
EQ: '=';
NEQ: '!=';
LT: '<';
LE: '<=';
GE: '>=';
GT: '>';


// OTHER SYMBOLS
LP: '(';
RP: ')';
LB: '[';
RB: ']';
LC: '{';
RC: '}';
SH: '#';
COL:':';
SEMI:';';
COMMA:',';
DQ: '"';
COLEQ: ':=';


// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// DO NOT ADD THE token DOT otherwise it cancels the rule IDENT = ID (DOT ID) ?:
// the stream ID DOT ID is always lexed as ID thus DOT ... not as a IDENT
// Reason implicit priority for rule in Antlr...   
fragment DOT: '.';

DIGITS : ('0'..'9')+;
FLOATING : DIGITS '.' DIGITS (('e' | 'E') ('-' | '+')? DIGITS)?; 

fragment ALPHA : 'a'..'z'|'A'..'Z';
fragment ID : (ALPHA | '_') (ALPHA | '_' |'0'..'9')*;
IDENT : ID (DOT ID)*;

//MEANING-LESS
WHITESPACE : (' '|'\t'|'\n'|'\r')+                  { $channel=HIDDEN; };
COMMENTS :   '//' ~('\n'|'\r')* '\r'? '\n'          { $channel=HIDDEN; };
 