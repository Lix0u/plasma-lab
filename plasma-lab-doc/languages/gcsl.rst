Goal Contracts Specification Language
=====================================

As part of the DANSE project we are implementing a Goal Contracts Specification Language (GCSL). GCSL is a high-level language designed to use à la SPEEDS patterns.

The idea is to use a requirement language more understandable than raw logic. GCSL requirements would then be translated into a low-level logic that can be used by Plasma Lab.

-------------

Reactive Module Implementation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

We made a prototype of the GCSL. As GCSL requirements are dependent on a model language,  we implemented them to be used in conjunction with the Reactive Module language.

There are 9 patterns that we list here. On each of these examples (p, p1, ... pn) denotes a property, while **bold words** are keywords.
Time intervals are denoted by [a - b], where a and b are a number and a time unit. For now we only supports step as a time unit.

GCSL Grammar
------------

.. code:: 

  GCSL ::= 
  | OCL-coll '-&gt;' 'forAll' '(' variable '|' pattern ')'
  | OCL-coll '-&gt;' 'exists' '(' variable '|' pattern ')'
  | OCL-prop
  | pattern
  
  pattern ::=
  | 'whenever' '[' prop ']' 'occurs' '[' prop ']' 'holds' 'during' 'following' interval
  | 'whenever' '[' prop ']' 'occurs' '[' prop ']' 'implies' '[' prop ']' 'during' 'following' interval
  | 'whenever' '[' prop ']' 'occurs' '[' prop ']' 'does' 'not' 'occur' 'during' 'following' interval
  | 'whenever' '[' prop ']' 'occurs' '[' prop ']' 'occurs' ' within'  interval
  | '[' prop ']' 'during' interval 'raises' '[' prop ']'
  | '[' prop ']' 'occurs' interval 'times' 'during' interval 'raises' '[' prop ']'
  | '[' prop ']' 'occurs'  'at' 'most' '[' integer ']' 'times' 'during' interval
  | '[' prop ']' 'during' interval 'implies' '[' prop ']' 'during' interval 'then' '[' prop ']' 'during' interval'
  
  prop ::=
  | OCL-prop
  | expr ( '&lt;' | '&lt;=' | '=' | '&gt;=' | '&gt;' ) expr
  
  expr ::=
  | expr binop expr
  | '(' expr ')'
  | OCL-expr
  | fun '(' expr ')' 
  
  fun ::= 'mean' | 'sum' | 'prod' | 'at'
  binop ::= '+' | '-' | '*' | '/'
  
  interval ::= lp time ('-' time)? rp
  lp ::=  '['  |  '('
  rp ::=  ']'  |  ')'
  
  time ::= int unit | '+oo'
  
  unit ::= 'ms' | 's' | 'min' | 'hour' | 'day' | 'step' | ...

On these patterns we reused the OCL predicates **forAll** and **exists**.
Given an instance of a module, these predicates will generate a list of all modules of the same "type" 
and will instantiate the pattern for this list.
For instance, if we use the philosophers model in RML, we could define the following requirement in GCSL::

  sys.itsphil1 -> forAll(phil | whenever [phil.p1 = 0] occurs [phil.p1 = 10] occurs within [5 step – 25 step])

The philosophers model in RML defines a module phil1 and others modules phil2 to philn by renaming variables of phil1.
Thus, ``sys.itsphil1 -> forAll(phil | p)`` defines phil as a module of the same "type" as phil1.

Adaptive Reactive Module Implementation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The Adaptive RML is a RML extension to design cyber physical systems that may reconfigure themselves when the environment settings change.
This capability is called as the dynamical adaptivity of the system and it can not be expressed easily using standard modelling languages.
This extension is supported by the RML simulator in Plasma Lab.

When some changes of settings occurred, the properties that the system must guaranty may also change to be consistent with the new system 
configuration. It could be necessary to express some properties that where the properties before after the change are expressed in GCSL. We propose two adaptive patterns for that.
They are based on the behavioral specifications (expressed in GCSL) before and after the change that is described using the a simple state property over the system to characterize the kind of change considered by the adaptive patterns. The grammar is given as ::

  ADAPTIVE ::=
  | 'if' assumption 'holds' 'and' 'for' 'all' 'rule' 'that' 'satisfies' trigger 'then'  promise 'holds'
  | 'if' assumption 'holds' 'then' 'there' 'exists' 'a' 'rule' 'satisfying' trigger 'and' promise 'holds'
  
  assumption ::= '[' GSCL ']'
  trigger ::= '[' prop ']'
  promise ::= '[' GSCL ']' | ADAPTIVE

The trigger is a simple state property, the assumption is written in GCSL and the promise can be a GCSL property or defined using an adaptive pattern too.
When promise is used to nest several adaptive properties, the global property specifies properties over sequences of dynamical adaptive event in the system.

