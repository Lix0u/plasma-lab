grammar GCSLGrammar;

options {
  language = Java;
  backtrack = true;
}

@rulecatch { 
    catch (RecognitionException e) {
        reportError(e);
        displayRecognitionError(e);
        throw e;
    }
}

@parser::header {
package fr.inria.plasmalab.gcsl.tools;

import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
}

@lexer::header {
package fr.inria.plasmalab.gcsl.tools;
import java.util.LinkedList;
}

@members {
  Model model;
  //TODO k = ???
  int k = 1001;
  
  
  public String parseGCSL(Model model){
    //this.k = k;
    this.model = model;
    try {
      return pattern_specification();
    } catch (RecognitionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "error during translation";
  }
  
  private List<String> errors = new LinkedList<String>();
  
  public void displayRecognitionError(RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, this.tokenNames);
        errors.add(hdr + " " + msg);
  }
  public List<String> getErrors() {
        return errors;
  }
}

pattern_specification returns[String property]
  : 'Hypothesis:'p=pattern_specification'Goal:'g=pattern_specification bltl_vars
    {property = "G<=#K"+$p.property+"=>"+$g.property+"\n"+$bltl_vars.text;
    }
  
  | ocl_expr '->' 'forAll' '('Variable '|' behavioral_pattern')'
    {
      String moduleType = $ocl_expr.moduleType;
      String pattern = $behavioral_pattern.property;
      property = "";
      HashMap<String, Module> hash = model.getHash();
      
      //For each module selected by the forAll/exists
      for(Module m:hash.values()){
        if(m.getType().equals(moduleType)){
	        String pTemp = pattern;
	        pTemp = pTemp.replaceAll($Variable.text+"\\.",m.getName()+".");
	        property += ("& "+"("+pTemp+")");
	       }
      }
      //For each module
      for(Module m:hash.values()){
          for(Substitution sub: m.getSubs()){
            property = property.replaceAll(m.getName()+"\\."+sub.getOriginal(), sub.getSubstitute());
          }
          property = property.replaceAll(m.getName()+"\\.","");
      }
      property = property.substring(2);
      property = property.replaceAll("\\[","(");
      property = property.replaceAll("]",")");
    }
  | ocl_expr '->' 'exists' '('Variable '|' behavioral_pattern')'
    {
      String moduleType = $ocl_expr.moduleType;
      String pattern = $behavioral_pattern.property;
      property = "";
      HashMap<String, Module> hash = model.getHash();
      
      //For each module selected by the forAll/exists
      for(Module m:hash.values()){
        if(m.getType().equals(moduleType)){
          String pTemp = pattern;
          pTemp = pTemp.replaceAll($Variable.text+"\\.",m.getName()+".");
          property += ("| "+"("+pTemp+")");
         }
      }
      //For each module
      for(Module m:hash.values()){
          for(Substitution sub: m.getSubs()){
            property = property.replaceAll(m.getName()+"\\."+sub.getOriginal(), sub.getSubstitute());
          }
          property = property.replaceAll(m.getName()+"\\.","");
      }
      property = property.substring(2);
      property = property.replaceAll("\\[","(");
      property = property.replaceAll("]",")");
    }
  //| prop
  | behavioral_pattern
    {
      // TODO Because we can't locate the model, we can't translate the pattern. 
      // This should be modified once PLASMA has been modified to parse a property with a given model
      property = $behavioral_pattern.property;
      System.err.println("Pattern only is not yet supported");
    }
  EOF
  ;

arith_proposition
  : expr ('<' | '<=' | '=' | '>=' | '>' | ) expr;

operator: '+' |'-' |'*' |'/' ;

behavioral_pattern returns [String property]
// Basic B-LTL patterns with sliding intervals
  :
  ('whenever' phi1=ocl 'occurs' phi2=ocl 'occurs' 'within' ab=interval 
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      property = "G<=#"+(k-$ab.b)+"("+$phi1.text+" => ";
      String p2 = "X<=#"+$ab.a+" F<=#"+($ab.b-$ab.a)+" "+$phi2.text;
      if($pr.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
      property+="("+p2+"))";      
      
      if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
    })
    
  | ('whenever' phi1=ocl 'occurs' phi2=ocl 'holds' 'during' 'following' ab=interval
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      property = "G<=#"+(k-$ab.b)+"("+$phi1.text+" => ";
      String p2 = "X<=#"+$ab.a+" G<=#"+($ab.b-$ab.a)+" "+$phi2.text;
      if($pr.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
      property+="("+p2+"))";      
      
      if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
    })
  | ('whenever' phi=ocl 'occurs' phi1=ocl 'implies' phi2=ocl 'during' 'following' ab=interval
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      property = "G<=#"+(k-$ab.b)+"("+$phi.text+" => ";
      String p2 = "X<=#"+$ab.a+" G<=#"+($ab.b-$ab.a)+" ("+$phi1.text+" => "+$phi2.text+")";
      if($pr.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
      property+="("+p2+"))";      
      
      if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
    })
  | ('whenever' phi1=ocl 'occurs' phi2=ocl 'does not' 'occurs' 'during' 'following' ab=interval
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      property = "G<=#"+(k-$ab.b)+"("+$phi1.text+" => ";
      String p2 = "X<=#"+$ab.a+" G<=#"+($ab.b-$ab.a)+" !"+$phi2.text;
      if($pr.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
      property+="("+p2+"))";      
      
      if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
    })
// Basic B-LTL patterns with absolute intervals
  | (phi1=ocl 'implies' phi2=ocl 'holds' 'forever'
    {
      property = "unknown pattern";
    })
  | ('always' phi1=ocl
    {
      property = "G<=#"+k+"("+$phi1.text+")";
    })
  | ('whenever' phi1=ocl 'occurs' phi2=ocl 'holds'
    {
      property = "G<=#"+k+"("+$phi1.text+" => "+$phi2.text+")";
    })
  | (phi1=ocl 'implies' phi2=ocl 'during' 'following' interval
    {
      property = "X<=#"+$interval.a+" G<=#"+($interval.a-$interval.b)+"("+$phi1.text+" => "+$phi2.text+")";
    })
  | (phi1=ocl 'during' ab=interval 'raises' phi2=ocl
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      property = "X<=#"+$ab.a+"G<=#"+($ab.a-$ab.b)+"("+$phi1.text+" => ";
      String p2 = "F<=#"+(k-$ab.b)+" "+$phi2.text;
      if($pr.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
      property+="("+p2+"))";      
      
      if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
    })
  | (phi=ocl 'during' ab=interval 
    (pr=proba_pattern
      {
        if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )? 
    'implies' phi1=ocl 'during' ac=interval 
    (pr1=proba_pattern
      {
        if(($pr1.pr < 0 ||$pr1.pr > 1)&&$pr1.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )? 
    'then' phi2=ocl 'during' cb=interval 
    (pr2=proba_pattern
      {
        if(($pr2.pr < 0 ||$pr2.pr > 1)&&$pr2.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
      }
    )?
    {
      //property = ""; //TODO see Benoit
      
      String p = "X<=#"+$ab.a+"G<=#"+($ab.a-$ab.b)+$phi.text+")";
      if($pr.pr!=null)
        p = "Pr>="+$pr.pr+"("+p+")";
      String p1 = "X<=#"+$ab.a+"G<=#"+($ac.b-$ac.a)+"("+$phi1.text+")";
      if($pr1.pr!=null)
        p1 = "Pr>="+$pr.pr+"("+p1+")";
      String p2 = "X<=#"+$cb.a+"G<=#"+($cb.b-$cb.a)+"("+$phi2.text+")";
      if($pr2.pr!=null)
        p2 = "Pr>="+$pr.pr+"("+p2+")";
        
      property = p+"=> ("+p1+"&"+p2+")";
    })
// Extended B-LTL pattern with absolute intervals
  | (ocl 'occurs' '['Natural']' 'times' 'during' interval 'raises' ocl pr=proba_pattern?
    {
    //TODO
      System.err.println("no operator has been implemented for this translation");
      //if($ab.b > k) System.err.println("bad time interval, [a="+$ab.a+",b="+$ab.b+"], b must be <= "+k);
      property = "";
      if(($pr.pr < 0 ||$pr.pr > 1)&&$pr.pr!=null) System.err.println("bad probability, with a probability P, P must be in [0,1]");
    })
  | (ocl 'occurs' 'at most' '['Natural']' 'times' 'during' interval pr=proba_pattern?
    {
    //TODO
      System.err.println("no operator has been implemented for this translation");
      property = "";
    })
  ;

proba_pattern returns[Double pr]
  : 'with a probability' Float
  {pr=Double.parseDouble($Float.text);}
  ;


prop
  : rel 
  | rel '&' prop
  | rel '|' prop
  | rel '=>' prop
  | '!' prop
  | '(' prop ')'
  | 'true'
  | 'false'
  ;
  
  
ocl: '['rel']';

rel: expr ('<' | '<=' | '=' | '>=' | '>' ) expr;

ocl_expr returns[String moduleType]: module=Variable
  {
    $moduleType = $module.text;
  };

expr
  :( '(' expr ')'
  | 'mean(' expr ')' 
  | 'sum(' expr ')' 
  | 'prod(' expr ')' 
  | 'at(' expr ',' time ')'
  | Natural
  | Variable'.'Variable
  | Variable ) 
  (operator expr)?
  ;

interval returns [int a, int b]: ('['|'(') t1=time{$a=0; $b=$t1.time;} ('-' t2=time{$a=$b; $b=$t2.time;})? (']'|')')
  {if ($a > $b) System.err.println("bad time interval, ["+$a+","+$b+"]");}
  ;

time returns [int time]: Natural{time = Integer.parseInt($Natural.text);} TimeUnit;

bltl_vars
  :'BLTL Vars:' (bltl_var)*;
bltl_var
  : Ident'=''['Float';'Float']' 'by' Float;


TimeUnit: 'step';

Natural: ('0'..'9')+;

Variable: ('a'..'z')('a'..'z'|'A'..'Z'|'0'..'9')*;

Ident: ('a'..'z'|'A'..'Z'|'0'..'9')*;

Float: Natural'.'Natural;

WS : (' '|'\t'|'\n')+ {$channel=HIDDEN;}
;