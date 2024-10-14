/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.ast.tools.parser;

import java.util.HashMap;

import fr.inria.plasmalab.nested.ast.*;
import fr.inria.plasmalab.nested.ast.nodes.*;
import fr.inria.plasmalab.nested.ast.operators.*;
import fr.inria.plasmalab.workflow.concrete.Variable;

// Set the name of your grammar here (and at the end of this grammar):


/**
 * Parser frame file for PLASMA-lab
 * @author modifications by Sean Sedwards
 * @version 6/12/2012
 */
public class Parser {
	public static final int _EOF = 0;
	public static final int _ident = 1;
	public static final int _integer = 2;
	public static final int _float = 3;
	public static final int maxT = 31;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	static final String numerror = "expecting number", boolerror = "expecting Boolean", proberror = "must be in (0,1)";//, poserror = "expecting positive value";
static final boolean debug = false;

Expr root;
HashMap<String,Variable> varMap = new HashMap<String, Variable>();

public HashMap<String,Variable> getVarMap(){
	return varMap;
}

public Expr getRoot(){
	return root;
}

class BoundExpr { 
	/* It is a union style structure: 
	   We expect exactly one of the two fields must be not null
	*/
	// public TimeExpr time = null;
	// public StepExpr step = null;
	public double time = Double.NaN;
	public int step = -1;
}


// keyword IGNORECASE here.



	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n, la.val);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (debug) System.out.println(la);
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	Expr  Formula() {
		Expr  expr;
		expr = null; /* Expr pexpr = null; */ 
		expr = Expression();
		root = expr; 
		return expr;
	}

	Expr  Expression() {
		Expr  result;
		Expr r; result = null;
		double proba = Double.NaN;									
		BinOp pathBop = null;	UnOp pathop = null;	
		BoundExpr bound = null;// = new BoundExpr(); 
														
		if (StartOf(1)) {
			result = Implication();
			if (la.kind == 4 || la.kind == 5) {
				if (la.kind == 4) {
					Get();
					pathBop = BinOp.Until;			
				} else {
					Get();
					pathBop = BinOp.WeakUntil;		
				}
				bound = Bound();
				r = Expression();
				if (Double.isNaN(bound.time))										
				result = new Temp2(result, pathBop, bound.step, r);				
				else																
					result = new Temp2(result, pathBop, bound.time, r);				
			}
		} else if (la.kind == 6 || la.kind == 7 || la.kind == 8) {
			if (la.kind == 6 || la.kind == 7) {
				if (la.kind == 6) {
					Get();
					pathop = UnOp.Future;   
				} else {
					Get();
					pathop = UnOp.Globally; 
				}
				bound = Bound();
			} else {
				Get();
				pathop = UnOp.Next; bound = new BoundExpr(); bound.step = 1;			 
				if (la.kind == 13) {
					bound = Bound();
				}
			}
			r = Expression();
			if (Double.isNaN(bound.time))
			result = new Temp1(pathop, bound.step, r);
			else 										
				result = new Temp1(pathop, bound.time, r);						
		} else if (la.kind == 9) {
			Get();
			Expect(10);
			proba = Number();
			Expect(11);
			r = Expression();
			Expect(12);
			result = new Proba(r, proba); 
		} else SynErr(32);
		return result;
	}

	Expr  Implication() {
		Expr  result;
		Expr right; 
		result = Disjunction();
		if (la.kind == 15) {
			Get();
			right = Disjunction();
			result = new Expr2(result, BinOp.Imp, right); 
		}
		return result;
	}

	BoundExpr  Bound() {
		BoundExpr  bound;
		bound = new BoundExpr(); 
		Expect(13);
		Expect(14);
		Expect(2);
		bound.step = Integer.parseInt(t.val); 
		return bound;
	}

	double  Number() {
		double  result;
		result = Double.NaN; 
		if (la.kind == 3) {
			Get();
		} else if (la.kind == 2) {
			Get();
		} else SynErr(33);
		if (Double.isNaN(t.value)) try { t.value = result = Double.parseDouble(t.val); } 
		catch(Exception e) { SemErr(numerror);} else result = t.value; 
		return result;
	}

	Expr  Disjunction() {
		Expr  result;
		Expr right; 
		result = Conjunction();
		while (la.kind == 16) {
			Get();
			right = Conjunction();
			result = new Expr2(result, BinOp.Or, right); 
		}
		return result;
	}

	Expr  Conjunction() {
		Expr  result;
		Expr right; 
		result = Equality();
		while (la.kind == 17) {
			Get();
			right = Equality();
			result = new Expr2(result, BinOp.And, right); 
		}
		return result;
	}

	Expr  Equality() {
		Expr  result;
		Expr right; BinOp op; UnOp neg = null; 
		if (la.kind == 18) {
			Get();
			neg = UnOp.Not	; 
		}
		result = RelExp();
		if (la.kind == 19 || la.kind == 20) {
			if (la.kind == 19) {
				Get();
				op = BinOp.Eq; 
			} else {
				Get();
				op = BinOp.Neq; 
			}
			right = RelExp();
			result = new Expr2(result, op, right); 
		}
		result = neg == null ? result : new Expr1(neg, result); 
		return result;
	}

	Expr  RelExp() {
		Expr  result;
		Expr right;	BinOp relop;	
		result = NumExp();
		if (StartOf(2)) {
			if (la.kind == 21) {
				Get();
				relop = BinOp.Gt; 
			} else if (la.kind == 22) {
				Get();
				relop = BinOp.Lt; 
			} else if (la.kind == 10) {
				Get();
				relop = BinOp.Ge; 
			} else {
				Get();
				relop = BinOp.Le; 
			}
			right = NumExp();
			result = new Expr2(result, relop, right); 
		}
		return result;
	}

	Expr  NumExp() {
		Expr  result;
		Expr right;	BinOp op;	
		result = Term();
		while (la.kind == 23 || la.kind == 24) {
			if (la.kind == 23) {
				Get();
				op = BinOp.Add;		
			} else {
				Get();
				op = BinOp.Min; 	
			}
			right = Term();
			result = new Expr2(result, op, right);	
		}
		return result;
	}

	Expr  Term() {
		Expr  result;
		Expr right; 	BinOp op; 
		result = Factor();
		while (la.kind == 25 || la.kind == 26) {
			if (la.kind == 25) {
				Get();
				op = BinOp.Mul; 
			} else {
				Get();
				op = BinOp.Div; 
			}
			right = Factor();
			result = new Expr2(result, op, right); 
		}
		return result;
	}

	Expr  Factor() {
		Expr  result;
		result = null; UnOp neg = null; double value; 
		if (la.kind == 24) {
			Get();
			neg = UnOp.Neg; 
		}
		if (la.kind == 27) {
			Get();
			result = Expression();
			Expect(28);
		} else if (la.kind == 1) {
			Get();
			result = new Ident(t.val); 
		} else if (la.kind == 2 || la.kind == 3) {
			value = Number();
			result = new Floating(value);
		} else if (la.kind == 29) {
			Get();
			result = new Bool(true);		
		} else if (la.kind == 30) {
			Get();
			result = new Bool(false); 	
		} else SynErr(34);
		result = neg == null ? result : new Expr1(neg, result);	
		return result;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Formula();
		Expect(0);

	}

	private static final boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
		{x,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, T,x,x,T, x,T,T,x, x},
		{x,x,x,x, x,x,x,x, x,x,T,x, x,T,x,x, x,x,x,x, x,T,T,x, x,x,x,x, x,x,x,x, x}

	};


	public String ParseErrors(){
			java.io.PrintStream oldStream = System.out;
		
		java.io.OutputStream out = new java.io.ByteArrayOutputStream();
		java.io.PrintStream newStream = new java.io.PrintStream(out);
		
		errors.errorStream = newStream;
		
		Parse();

		String errorStream = out.toString();
		errors.errorStream = oldStream;

		return errorStream;


	}


public static class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.err;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	public String firstError = null;	

	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
		if (firstError == null) firstError = b.toString();
	}
	
	public void SynErr (int line, int col, int n, String val) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "ident expected"; break;
			case 2: s = "integer expected"; break;
			case 3: s = "float expected"; break;
			case 4: s = "\"U\" expected"; break;
			case 5: s = "\"W\" expected"; break;
			case 6: s = "\"F\" expected"; break;
			case 7: s = "\"G\" expected"; break;
			case 8: s = "\"X\" expected"; break;
			case 9: s = "\"Pr\" expected"; break;
			case 10: s = "\">=\" expected"; break;
			case 11: s = "\"[\" expected"; break;
			case 12: s = "\"]\" expected"; break;
			case 13: s = "\"<=\" expected"; break;
			case 14: s = "\"#\" expected"; break;
			case 15: s = "\"=>\" expected"; break;
			case 16: s = "\"|\" expected"; break;
			case 17: s = "\"&\" expected"; break;
			case 18: s = "\"!\" expected"; break;
			case 19: s = "\"=\" expected"; break;
			case 20: s = "\"!=\" expected"; break;
			case 21: s = "\">\" expected"; break;
			case 22: s = "\"<\" expected"; break;
			case 23: s = "\"+\" expected"; break;
			case 24: s = "\"-\" expected"; break;
			case 25: s = "\"*\" expected"; break;
			case 26: s = "\"/\" expected"; break;
			case 27: s = "\"(\" expected"; break;
			case 28: s = "\")\" expected"; break;
			case 29: s = "\"true\" expected"; break;
			case 30: s = "\"false\" expected"; break;
			case 31: s = "??? expected"; break;
			case 32: s = "invalid Expression"; break;
			case 33: s = "invalid Number"; break;
			case 34: s = "invalid Factor"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s + ", \"" + val + "\" found");
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


public static class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	int line = -1;
	int col = -1;
	public FatalError(String s, int line, int col)
	{
		super(s);
		this.line = line;
		this.col = col;
	}
	public FatalError(String s) { super(s); }
}


} // end Parser
