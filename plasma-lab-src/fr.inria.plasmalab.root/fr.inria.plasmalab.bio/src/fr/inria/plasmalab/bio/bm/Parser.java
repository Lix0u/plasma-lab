/**
 * This file is part of fr.inria.plasmalab.bio :: Biological models.
 *
 * fr.inria.plasmalab.bio :: Biological models is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio :: Biological models is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio :: Biological models.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of fr.inria.plasmalab.bio.
 *
 * fr.inria.plasmalab.bio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bio.bm;

import java.util.HashMap;
import fr.inria.plasmalab.bio.BioState;

/**
 * BIO language ATG file
 * @author Sean Sedwards
 * @version 6/12/2012
 */


/**
 * Parser Frame template file for BIO language
 * @version 6/12/2012 Sean Sedwards
 */
public class Parser {
	public static final int _EOF = 0;
	public static final int _ident = 1;
	public static final int _integer = 2;
	public static final int _float = 3;
	public static final int maxT = 11;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	boolean debug;

	public HashMap<String,Integer> species = new HashMap<String,Integer>();
public HashMap<String,Integer> variables = new HashMap<String,Integer>();
public HashMap<String,Double> constants = new HashMap<String,Double>();
public Reaction reactions;
public int speciescount = 0, reactioncount = 0;
public UpdateVect [] dependents;

public BioState state;

void addDependent(int index, Reaction r)
{
	UpdateVect v;
	for (v = dependents[index]; v != null; v = v.next) if (v.reaction == r) return;
	v = new UpdateVect(r);
	v.next = dependents[index];
	dependents[index] = v;
}

void createDependents()
{
	dependents = new UpdateVect[speciescount+1];
	for (int i = 1; i <= speciescount; i++)
	{	// all the species (excluding time)
		for (Reaction r = reactions; r != null; r = r.next)
		{	// all the reations
			switch(r.kind)
			{
				case Reaction.gille:	// three dependents
					addDependent(r.r3, r);
				case Reaction.gillc:	// two dependents
				case Reaction.gillf:
					addDependent(r.r2, r);
				case Reaction.gillb:	// one dependent
				case Reaction.gilld:
				case Reaction.gillg:
					addDependent(r.r1, r);
				case Reaction.gilla:	// not dependent
				default:
			}
		}
	}
	for (Reaction r = reactions; r != null; r = r.next)
	{	// all the reactions
		for (ChangeVect v = r.action; v != null; v = v.next)
		{	// all the changes of a reaction
			r.addDependent(dependents[v.index]);
		}
	}
}

// keyword IGNORECASE here.



	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
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
			if (debug) System.out.println(t);
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
	
	void BIO() {
		while (la.kind == 4) {
			ConstantList();
		}
		SpeciesList();
		while (la.kind == 7) {
			SpeciesList();
		}
		Reaction();
		while (la.kind == 1 || la.kind == 9) {
			Reaction();
		}
		createDependents(); 
	}

	void ConstantList() {
		Expect(4);
		ConstDecl();
		while (la.kind == 5) {
			Get();
			ConstDecl();
		}
	}

	void SpeciesList() {
		Expect(7);
		SpecDecl();
		while (la.kind == 5) {
			Get();
			SpecDecl();
		}
	}

	void Reaction() {
		Reaction r = new Reaction(reactioncount++); r.next = reactions; reactions = r; 
		Reactants(r);
		if (la.kind == 1 || la.kind == 2 || la.kind == 3) {
			r.rateconstant = RateConst();
		}
		Expect(8);
		Products(r);
	}

	void ConstDecl() {
		double val; 
		Expect(1);
		String key = t.val; 
		Expect(6);
		val = Number();
		if (constants.containsKey(key)) SemErr("redefinition of " + key); 
		else constants.put(key,val); 
	}

	double  Number() {
		double  val;
		if (la.kind == 2) {
			Get();
		} else if (la.kind == 3) {
			Get();
		} else SynErr(12);
		val = Double.parseDouble(t.val); 
		return val;
	}

	void SpecDecl() {
		int val = 0; 
		Expect(1);
		String key = t.val; 
		if (la.kind == 6) {
			Get();
			Expect(2);
			val = Integer.parseInt(t.val); 
		}
		if (species.containsKey(key)) SemErr("redefinition of " + key); 
		else { species.put(key,val); variables.put(key,++speciescount); } 
	}

	void Reactants(Reaction r) {
		if (la.kind == 9) {
			Get();
			r.kind = Reaction.gilla; 
		} else if (la.kind == 1) {
			r.r1 = Species();
			r.kind = Reaction.gillb; r.action = new ChangeVect(r.r1,-1); 
			if (la.kind == 10) {
				Get();
				r.r2 = Species();
				if (r.r1 == r.r2) { r.kind = Reaction.gilld; r.action.change--; } 
				else { r.kind = Reaction.gillc; r.addChange(r.r2,-1); } 
				if (la.kind == 10) {
					Get();
					r.r3 = Species();
					if (r.r1 != r.r2 && r.r3 != r.r1 && r.r3 != r.r2) { r.kind = Reaction.gille; 
					r.addChange(r.r3,-1); } else if (r.r1 == r.r2 && r.r3 == r.r1) 
					{ r.kind = Reaction.gillg; r.action.change--; } else { r.kind = Reaction.gillf; 
					if (r.r3 == r.r2) { r.r2 = r.r1; r.r1 = r.r3; r.action.change--; } else r.action.next.change--; } 
				}
			}
		} else SynErr(13);
	}

	double  RateConst() {
		double  val;
		val = 0; 
		if (la.kind == 2 || la.kind == 3) {
			val = Number();
		} else if (la.kind == 1) {
			Get();
			if(!constants.containsKey(t.val)) SemErr("expecting constant"); 
			else val = constants.get(t.val); 
		} else SynErr(14);
		return val;
	}

	void Products(Reaction r) {
		int prod; 
		if (la.kind == 9) {
			Get();
		} else if (la.kind == 1) {
			prod = Species();
			r.addChange(prod,1); 
			while (la.kind == 10) {
				Get();
				prod = Species();
				r.addChange(prod,1); 
			}
		} else SynErr(15);
		if (r.action == null) SemErr("illegal reaction"); 
	}

	int  Species() {
		int  index;
		index = -1; 
		Expect(1);
		if (!variables.containsKey(t.val)) SemErr("expecting species"); 
		else index = variables.get(t.val); 
		return index;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		BIO();
		Expect(0);

	}

	private static final boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x}

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
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	private String errorMsg;
	
	public String getMsg() {
		return errorMsg;
	}

	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorMsg = b.toString();
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "ident expected"; break;
			case 2: s = "integer expected"; break;
			case 3: s = "float expected"; break;
			case 4: s = "\"constant\" expected"; break;
			case 5: s = "\",\" expected"; break;
			case 6: s = "\"=\" expected"; break;
			case 7: s = "\"species\" expected"; break;
			case 8: s = "\"->\" expected"; break;
			case 9: s = "\"*\" expected"; break;
			case 10: s = "\"+\" expected"; break;
			case 11: s = "??? expected"; break;
			case 12: s = "invalid Number"; break;
			case 13: s = "invalid Reactants"; break;
			case 14: s = "invalid RateConst"; break;
			case 15: s = "invalid Products"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
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
	public FatalError(String s) { super(s); }
}

} // end Parser