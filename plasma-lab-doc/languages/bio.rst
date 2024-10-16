Biological language
===================
 
Here is the BNF grammar of our biological modeling language::

  BIO ::= [“constant” ConstantList]
          “species” SpeciesList
          ReactionList
  
  ConstantList ::= ConstDecl [“,” ConstantList]
  
  ConstDecl ::= ident “=” Number
  
  SpeciesList ::= SpecDecl [“,” SpeciesList]

  SpecDecl ::= ident [ “=” integer]
  
  ReactionList ::= Reaction [ReactionList]
  
  Reaction ::= Reactants [RateConst] “->” Products
  
  Reactants ::= “*”
                | Species [ “+” Species [“+” Species ]]
  
  Species ::= ident
  
  Products ::= “*”
               | Species {“+” Species}
  
  RateConst ::= Number
                | ident
  
  Number ::= integer
             | float
