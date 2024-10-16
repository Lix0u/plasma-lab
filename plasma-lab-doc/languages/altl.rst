Adaptive BLTL (A-BLTL)
======================

We have extended the grammar of BLTL with two adaptive operators that observe
adaptive transitions in stochastic adaptive systems (SAS). The grammar of the new logic is the following::
  
  Property ::= ( ('declare' Variable (';' Variable)*)?
               ('optimize' Variable (';' Variable)*)?
               'end')? Formula
  
  Formula ::= BLTL_Formula
              | BLTL_Formula '; [' Trigger ',' Trigger '] ==>' Formula
              | BLTL_Formula '; [' Trigger ',' Trigger '] <=' Bound '==>' Formula
              | BLTL_Formula '==> [' Trigger ',' Trigger '] ;' Formula
              | BLTL_Formula_Formula '==> [' Trigger ',' Trigger '] <=' Bound ';' Formula Trigger ::=      Trigger '=>' Trigger
              | Trigger '&' Trigger
              | '!' Trigger
              | '(' Trigger ')'
              | 'true'
              | 'false'
              | Numerical Operator Numerical
  
  Bound ::= ('#')? Numerical

  Variable ::= ident ':=' (Interval | number)

  Interval ::= '['number';'number';'number']'

  Numerical ::=  ident | ident "'" | number

  Operator ::=  '<' | '<' | '!=' | '=' | '>=' | '>' | '+' | '-' | '*' | '/'

As presented above, the two adaptive operators of A-BLTL can be either bounded or unbounded. 
Informally the semantics of these operators is the following:

- ``BLTL Property ‘==> [‘ Trigger ‘,’ Trigger ‘] ;’ Property``, is satisfied by an execution if the first BLTL property is satisfied by the first state and there exists an adaptive transition that satisfies the triggers and the second property is satisfied by the state reached just after the adaptive transition.
- ``BLTL Property ‘; [‘ Trigger ‘,’ Trigger ‘] ==>’ Property``, is satisfied by an execution if when the first BLTL property is satisfied by the first state and there exists an adaptive transition that satisfies the triggers, then the second property is satisfied by the state reached just after the adaptive transition. Therefore in this semantics it is not necessary to observe an adaptive transition in order to satisfy the property.

The triggers are Boolean expressions over the variables of the model. The first trigger is evaluated on the state just before an adaptive transition, while the second is evaluated on the state reached just after the adaptive transition. To specify expressions, A-BLTL include an additional prime operator on identifiers that test if a variable exists and returns false in the contrary.


