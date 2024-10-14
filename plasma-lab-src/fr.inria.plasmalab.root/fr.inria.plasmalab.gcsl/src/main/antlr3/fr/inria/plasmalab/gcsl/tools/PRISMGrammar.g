grammar PRISMGrammar;

options {
  language = Java;
}

@parser::header {
package fr.inria.plasmalab.gcsl.tools;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
}

@lexer::header {
package fr.inria.plasmalab.gcsl.tools;
}

@members {

  public Model model;

  public Model parseModel(){
    model = new Model();
    try {
      this.reactive_modules();
    } catch (RecognitionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
      return model;
    }
  }

reactive_modules: (model_element)+ EOF;

model_element:
  constant
  | global 
  | module
  | rewards 
  | formula
  | label 
  | ( 'dtmc' | 'probabilistic')
  | 'mdp' ;

label: 'label' string '=' implication';';
  
formula: 'formula' Ident '=' ite ';';

constant: 'const' (
  | 'int'
  | 'double'         
  | 'bool'
  ) Ident
  ('=' numerical)? ';';

global: 'global' Ident
  ':' ( '[' numerical '..' numerical('init' numerical)?
      | 'bool' ('init')? implication)';';
  
module: 'module' Ident{String moduleName = $Ident.text;}
  (
    {
      ArrayList<Substitution> subs = new ArrayList<Substitution>();
    } 
  (local
    //{
    //  subs.add(new Substitution($local.id,$local.id));
    //}
  )* (command)+
    {
      model.declareModuleType(moduleName, moduleName, subs);;
    }
  | '=' renaming
    {
      model.addInstance($renaming.moduleType, moduleName, $renaming.subs);
    }
  ) 'endmodule';

renaming returns [String moduleType, ArrayList<Substitution> subs]
  : Ident{$moduleType = $Ident.text; $subs = new ArrayList<Substitution>();}
  '[' rv1=rename_var
    {
      $subs.add($rv1.sub);
    }
  (',' rv2=rename_var
    {
      $subs.add($rv2.sub);
    }
  )* ']'; 

rename_var returns [Substitution sub]
  : i1=Ident{String a = $i1.text;} '=' i2=Ident{String b = $i2.text;}
    {$sub = new Substitution(a,b);}
  ;

local returns [String id]
  : Ident{$id = $Ident.text;}':' 
  ( '[' numerical'..' numerical']' ('init' numerical)?
  | 'bool' ('init' implication)?)';';

command: '[' (Ident)? ']' implication '->'
  ( 'true'
  | action
  | numerical ':' ( 'true' | action ) ('+' numerical ':' ('true' | action) )*) ';'?;

implication: disjunction ('=>' disjunction)?;

disjunction: conjunction ('|' conjunction)*;

conjunction: relative ('&' relative)*;

relative: '!'?( 'true' | 'false' | numerical (( '>' | '>=' | '=' | '!=' | '<=' | '<' ) numerical)? );
   
ite: implication ('?' numerical ':' numerical)?;

numerical: term (( '+' | '-') term)*;

term: factor (( '*' | '/' ) factor)*;

factor: Ident | number| '(' ite ')' | function;
  
function: 
  ( 'min' '(' ite( ',' ite )*
  | 'max' '(' ite( ',' ite )*) ')';

action: '(' Ident'\'' '=' ite ')' ('&' '(' Ident'\'' '=' ite')' )*;

number: Integer('.'Integer(('e'|'E')('+'|'-')?Integer)?)?;

rewards: 'rewards' 
//(ANY)*
 'endrewards';

string: '"'Ident'"';

Ident: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

Integer: ('0'..'9')+;

WS : (' '|'\t'|'\n')+ 
  {$channel=HIDDEN;};

Comment : '//'('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'('|')'|' '|','|':'|'\t'|'-'|'='|'%'|'/'|'\\'|'.'|'['|']'|'>')*
  {$channel=HIDDEN;};