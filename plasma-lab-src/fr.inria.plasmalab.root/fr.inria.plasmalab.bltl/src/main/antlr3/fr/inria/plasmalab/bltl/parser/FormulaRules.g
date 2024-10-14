parser grammar FormulaRules;

options {
  language = Java;
}
@rulecatch {
    catch (RecognitionException re) {
      reportError(re);
      //keepError(re);
      recover(input,re);
      throw(re);
    }
}
@members {

  Expr root;
  public Map<String, InterfaceIdentifier> modelIdMap;
  public Map<String, Variable> requirementVarMap;
  public List<Variable> optimizationVariables;
  public List<InterfaceIdentifier> overrideIdentifiers;
   
  Map<String,Variable> varMap = new HashMap<String, Variable>();

  public Map<String,Variable> getVarMap(){
    return varMap;
  }

  public Expr getRoot() {
    return root;
    }
}

expr returns [Expr val]
: l=implication                                { val = $l.val; }
  (                                            { BinOp binop = null;      }
     ( UNTIL                                   { binop = BinOp.Until;     }
     | WEAK                                    { binop = BinOp.WeakUntil; }
            ) bound r=expr                     {  if (!$bound.val.isTime)
                                                    val = new StepBoundExpr2($l.val, binop, $bound.val.expr, $r.val);
                                                  else
                                                    val = new TimeBoundExpr2($l.val, binop, $bound.val.expr, $r.val);      }                               
                           )?
|                                              { UnOp unop = null;  BoundExpr b = null; }
  ( ( FATALLY                                  { unop = UnOp.Future;   }
    | GLOBALLY                                 { unop = UnOp.Globally; }
               ) b1=bound                      { b = $b1.val; }
      
    | NEXT                                       { unop = UnOp.Next; 
                                                 b = new BoundExpr();
                                                 b.isTime = false;
                                                 b.expr = new IntExpr(1); }
           (b2=bound                           { b = $b2.val; }
                    )?    ) r=expr             {  if (!b.isTime)
                                                    val = new StepBoundExpr1(unop, b.expr, $r.val);
                                                  else                    
                                                    val = new TimeBoundExpr1(unop, b.expr, $r.val);    }
| REWARD LC DQ IDENT DQ RC r=expr              { String vname = "\"" + $IDENT.text + "\""; 
                                                 varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));
                                                 val = new RewardExpr(vname,$r.val); 
                                               }
;
   

bound returns [BoundExpr val]             @init{ val = new BoundExpr(); }
: LE (SH                                    {val.isTime = false;} 
        ( i=number                            { val.expr = new IntExpr((int)$i.val);  }
        | LP sExp=numExp RP                 { val.expr = $sExp.val;}
        )
     |                                      {val.isTime = true;} 
        ( f=number                            { val.expr = new FloatExpr($f.val); }
        | LP tExp=numExp RP                 { val.expr = $tExp.val;}
        )
     )
;

implication returns [Expr val]
: l=disjunction                           { val = $l.val; }
                (IMP r=disjunction        { val = new Expr2(val, BinOp.Imp, $r.val); }
                                   )*
;

disjunction returns [Expr val]
: l=conjunction                           { val = $l.val; } 
                (OR r=conjunction         { val = new Expr2(val, BinOp.Or, $r.val); }
                                  )*
;
 
conjunction returns [Expr val]: 
  l=equality                              { val = $l.val; }
             ( AND r=equality             { val = new Expr2(val, BinOp.And, $r.val); }
                               )*
;

equality returns [Expr val]          @init{ UnOp neg = null; }
: (NOT                                    { neg = UnOp.Not ; }
      )? l=relExp                         { val = $l.val; }
                  (eop r=relExp           { val = new Expr2($l.val, $eop.val, $r.val); }
                                )?        { val = neg == null ? val : new Expr1(neg, val); }
;

eop returns [BinOp val]
: EQ                                      { val = BinOp.Eq; }
| NEQ                                     { val = BinOp.Neq; }
;

relExp returns [Expr val]
: l=numExp                                { val = $l.val; }  
           (rop r=numExp                  { val = new Expr2(val, $rop.val, $r.val); }
                        )?
;

rop returns [BinOp val]
: GT                                      { val = BinOp.Gt; }
| LT                                      { val = BinOp.Lt; }
| GE                                      { val = BinOp.Ge; }
| LE                                      { val = BinOp.Le; }
;


numExp returns [Expr val]:
  l=term                                  { val = $l.val; }    
         (nop r=term                      { val = new Expr2(val, $nop.val, $r.val); }
                    )*
;

nop returns [BinOp val]
: ADD                                     { val = BinOp.Add;  }
| MIN                                     { val = BinOp.Min;  }
;
 

term returns [Expr val]
: l=factor                                { val = $l.val; }
           (top r=factor                  { val = new Expr2(val, $top.val, $r.val); }
                        )*
;

top returns [BinOp val]
: MUL                 { val = BinOp.Mul; }
| DIV                 { val = BinOp.Div; }
;


factor returns [Expr val]      @init{ UnOp neg = null; }
: (MIN                              { neg = UnOp.Neg; }
      )? ( LP expr RP               { $val = $expr.val; }
         | id1=IDENT                { String vname = $id1.text;
                                      InterfaceIdentifier iId = modelIdMap.get(vname);
                                      if(iId==null)
                                        iId = requirementVarMap.get(vname);
                                      else
                                        varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));
                                      $val = new IdentExpr(iId, vname);}
         | DQ id2=IDENT DQ          { String vname = "\"" + $id2.text + "\""; 
                                      $val = new IdentExpr(modelIdMap.get(vname), vname);
                                      varMap.put(vname, new Variable(vname,  VariableType.DOUBLE));}
         | number                   { $val = new FloatExpr($number.val); }
         | TRUE                     { $val = new BoolExpr(true);   }
         | FALSE                    { $val = new BoolExpr(false);  }
         | DEADLOCK                 { $val = new DeadlockExpr();   }
                    )               { $val = neg == null ? $val : new Expr1(neg, $val);  }
;

declareVars
: DECLARE var1=variable{requirementVarMap.put($var1.var.getName(),$var1.var);} 
          (SEMI var2=variable{requirementVarMap.put($var2.var.getName(),$var2.var);})*
;

 
optimizeVars
: OPTIMIZE var1=variable{optimizationVariables.add($var1.var);} 
           (SEMI var2=variable{optimizationVariables.add($var2.var);})*
;

overrideIds
: OVERRIDE id1=IDENT{overrideIdentifiers.add(new GenericIdentifier($id1.text));}
           (SEMI id2=IDENT{overrideIdentifiers.add(new GenericIdentifier($id2.text));})*
;
 
variable returns [Variable var]                                         
 @init{ double x = 0, y = 0, z = 1; 
 VariableType type = VariableType.DOUBLE; }
: id=IDENT COLEQ 
    (number {x=y=$number.val;}  
    | r=range {x=$r.range.lower; y=$r.range.upper; z=$r.range.step; type=$r.range.type;}
    | bvalue {x=y=$bvalue.val; type = VariableType.BOOL;}
    )

 { var = new Variable($id.text, x, y, z, type);}
;

range returns [Range range]
@init{double x=0, y=0, z=1;
      VariableType type = VariableType.DOUBLE;}
: LB(
    x1=number SEMI y1=number 
        {x=$x1.val; y=$y1.val;}
    (SEMI z1=number
        {z=$z1.val; }
    )?
  | b1=bvalue
        {x=$b1.val; y=$b1.val;}
    (SEMI b2=bvalue
        {y=$b2.val;}
    )?
        {type = VariableType.BOOL;}
  )RB
  {range = new Range(x,y,z,type);}
;

bvalue returns [double val]
: TRUE          { val = 1; }
| FALSE         { val = 0; }
;  
  
number returns [double val]
: floating   { $val = $floating.val; }
| integer    { $val = $integer.val; }
;

  
floating returns [double val]
: FLOATING   { try{$val = Double.parseDouble($FLOATING.text);}catch(NumberFormatException e){$val=0;} }
;

integer returns [int val]
: DIGITS     { try { $val = Integer.parseInt($DIGITS.text); }
               catch (NumberFormatException e) { $val=0; }  }
;


