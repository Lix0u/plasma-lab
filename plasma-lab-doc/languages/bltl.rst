Bounded Linear Temporal Logic
=============================

Our property logic is based on a Bounded Linear Temporal Logic (B-LTL).
This logic enhance Linear Temporal Logic (LTL) operators with bounds expressed
in step or time units. These bounds give the length of the run on which the nested formula must hold.
Any decidable property on states or runs can be used in the formulas including nested B-LTL operators.
Thus, the semantic of our B-LTL logic is the semantic of LTL logic restricted to a time interval.

For a quick introduction to LTL you could read the Wikipedia article on `Linear Temporal Logic <https://en.wikipedia.org/wiki/Linear_temporal_logic>`__.

B-LTL Grammar
^^^^^^^^^^^^^

The grammar accepted by our BLTL plugin is the following::

  Property ::= ( ('declare' Variable (';' Variable)*)?
               ('optimize' Variable (';' Variable)*)?
               'end')? Formula
  
  Formula ::= 'F <=' Bound Formula
              | 'G <=' Bound Formula
              | Formula 'U <=' Bound Formula
              | Formula 'W <=' Bound Formula
              | 'X' ('<=' Bound)? Formula      
              | Formula '&' Formula
              | Formula '|' Formula
              | Formula '=>' Formula
              | '!' Formula
              | '(' Formula ')'
              | 'true'
              | 'false'
              | Numerical Operator Numerical
  
  Bound ::= ('#')? Numerical
  
  Variable ::= ident ':=' (Interval | number)
  
  Interval ::= '['number';'number';'number']'
  
  Numerical ::= ident | number
  
  Operator ::=  '<' | '<' | '!=' | '=' | '>=' | '>' | '+' | '-' | '*' | '/'

The difference with classical Linear Temporal Logic is that temporal operators (F, G, X, U and W) are bounded by a temporal bound.
This bound may either be a number of steps (using the syntax ``<= # Numerical``) or a real-time bound (using the syntax ``<= Numerical``).
If the model is untimed, the two syntax are equivalent.

Optimisation
^^^^^^^^^^^^

It is possible to declare a new variable in a BLTL property and assign a range of values to it.
This generates a set of BLTL formulas that can be checked simultaneously by some SMC algorithms.
The syntax is ``declare variable:=[min;max;inc] end``, where *variable* is the name of the variable,
*min* is the minimum value assigned to the variable, *max* is the maximum value assigned to the variable,
*inc* is the increment between each instantiation. 

For instance::
  
  declare K:=[5;10;5] end
  
  F<=#(K) “eat”

generates a property whose temporal bound is successively 5 and 10.

It is also possible to optimize existing variables in the model and assign similarly a range of initial values to it. 
When checking such a property several initial states will be successively checked by the SMC algorithm.
The syntax is ``optimize variable:=[min;max;inc] end``, where *variable* is the name of the model's variable,
and *min*, *max*, *inc* are as previously.

For instance::
  
  optimize p1:=[0;5;1] end
  
  F<=#5 “eat”

will check the property for each values of the variable p1 from 0 to 5 with an increment of 1.
