/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.ast.factory;

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.exceptions.WrongParametersNumber;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;
/** 
 * TypeChecking can be done under any context by specifying it
 * when TypeChecking is build 
 * @author bboyer
 *
 */
public class TypeChecking implements ManualVisitor, IdentVisitor {
	
	//functionalities provided by the class TypeChecking 
	public void of(Identifier ident) {
		ident.accept(this);
	}
	
	public void subTypeOf(Type t, Expr expr) {
		expr.accept(this);
		if (!expr.type.subTypeOf(t))
			throw new WrongType(expr, t);
	}

	public Type getTypeOf(Expr expr) {
		expr.accept(this);
		return expr.type;
	}
	
	@Override
	public void visit(UnExpr unary) {
		if (unary.child.type == null) unary.child.accept(this);
		
		Type childType = unary.child.type;
		Type expected = null;

		switch (unary.op) {
		case Neg:
			expected = Type.Double;
			break;
		case Not:
			expected = Type.Boolean;
			break;
		}
		if (!childType.subTypeOf(expected))
			throw new WrongType(unary.child, expected);
		unary.type = childType;
	}

	@Override
	public void visit(BinExpr binary) {

		if (binary.left.type == null) binary.left.accept(this);
		if (binary.right.type == null) binary.right.accept(this);
		Type leftType = binary.left.type;
		Type rightType = binary.right.type;

		switch (binary.op) {

		case And: case Or: case Iff: case Imp:
			if (!leftType.subTypeOf(Type.Boolean))
				throw new WrongType(binary.left, Type.Boolean);
			if (!rightType.subTypeOf(Type.Boolean))
				throw new WrongType(binary.right, Type.Boolean);
			binary.type = Type.Boolean;
			break;

		case Add: case Sub: case Mul: case Div:
			if (!leftType.subTypeOf(Type.Double))
				throw new WrongType(binary.left, Type.Double);
			if (!rightType.subTypeOf(Type.Double))
				throw new WrongType(binary.right, Type.Double);
			// Here, we know that leftType << Double and rightType << Double
			// This implies that we have either leftType << rightType or rightType << leftType
			// (c.f. definition of the method Type.subTypeOf(...))
			// We expect to compute with the most general of both: 
			binary.type = rightType.subTypeOf(leftType) ? leftType : rightType;
			break;

			// '=' and '!=' are polymorphic operators:
		case Eq: case Neq:
			// we check that leftType << rightType or rightType << leftType
			if (leftType.subTypeOf(rightType) || rightType.subTypeOf(leftType))
				binary.type = Type.Boolean;
			else
				throw new WrongType(binary.right, leftType);
			break;

		case Le: case Lt: case Ge: case Gt:
			if (!leftType.subTypeOf(Type.Double))
				throw new WrongType(binary.left, Type.Double);
			if (!rightType.subTypeOf(Type.Double))
				throw new WrongType(binary.right, Type.Double);
			binary.type = Type.Boolean;
			break;
		}
	}

	@Override
	public void visit(ITEExpr ternary) throws WrongType {
		
		if (ternary.ifCond.type == null) ternary.ifCond.accept(this);
		if (ternary.thenCase.type == null) ternary.thenCase.accept(this);
		if (ternary.elseCase.type == null) ternary.elseCase.accept(this);
		
		Type ifType = ternary.ifCond.type;
		Type thenType = ternary.thenCase.type;
		Type elseType = ternary.elseCase.type; 

		if (!ifType.subTypeOf(Type.Boolean))
			throw new WrongType(ternary.ifCond, Type.Boolean);

		if (thenType.subTypeOf(elseType))
			ternary.type = elseType;
		else if (elseType.subTypeOf(thenType))
			ternary.type = thenType;
		else
			throw new WrongType(ternary.elseCase, thenType);
	}

	@Override
	public void visit(Function fun) {
		// 1- Argument Number fits with the arity of the function
		if (!fun.ident.checkArity(fun.parameters.length))
			throw new WrongParametersNumber(fun.ident, fun.parameters.length);

		for (Expr parameter: fun.parameters)
			if (parameter.type == null) parameter.accept(this);
		
		// 2- All parameter must have a type t << Double, built-in functions are all numerical for now...
		// 3- all built-in functions returns a Double. 

		switch (fun.ident) {
		case Ceil:	case Floor: case Mod:
			fun.type = Type.Integer;
			break;
		case Log: {
			Expr parameter = fun.parameters[0]; 
			if (!parameter.type.subTypeOf(Type.Double))
				throw new WrongType(parameter, Type.Double);
			fun.type = Type.Double;
		} break;
		case Max: case Min:
			Type maxType = Type.Integer;
			for (Expr parameter: fun.parameters)
				if (!parameter.type.subTypeOf(Type.Double))
					throw new WrongType(parameter, Type.Double);
				else if (!parameter.type.subTypeOf(Type.Integer))
					maxType = Type.Double;
			fun.type = maxType;
			break;
		case Pow: {
			Expr parameter = fun.parameters[1]; 
			if (!parameter.type.subTypeOf(Type.Integer))
				throw new WrongType(parameter, Type.Integer);
			parameter = fun.parameters[0];
			if (!parameter.type.subTypeOf(Type.Double))
				throw new WrongType(parameter, Type.Double);
			fun.type = parameter.type;
		} break;
		}
	}

	@Override
	public void visit(Value value) {
		// Nothing to do: type of a value must be already set when it is built.
		assert (value.type != null) : "Error: all value must have a type";
	}

	@Override
	public void visit(Ref ref) {
		if (ref.target instanceof Identifier)
			ref.type = ((Identifier) ref.target).type;
		else
			ref.type = Type.Double;
	}

	@Override
	public void visit(Constant constant) {
		constant.value.accept(this);
		if (!constant.value.type.subTypeOf(constant.type))
			throw new WrongType(constant.value, constant.type);
	}

	@Override
	public void visit(Formula formula) {
		formula.body.accept(this);
		formula.type = formula.body.type;
	}

	@Override
	public void visit(Variable variable) {
		if (variable.lowerExpr != null) {
			variable.lowerExpr.accept(this);
			if (!variable.lowerExpr.type.subTypeOf(variable.type))
				throw new WrongType(variable.lowerExpr,variable.type);
			variable.upperExpr.accept(this);
			if (!variable.upperExpr.type.subTypeOf(variable.type))
				throw new WrongType(variable.upperExpr,variable.type);
		}
		variable.initValue.accept(this);
		if (!variable.initValue.type.subTypeOf(variable.type))
			throw new WrongType(variable.initValue,variable.type);
		
	}

	@Override
	public void visit(Parameter parameter) {
		if (parameter.lowerExpr != null) {
			parameter.lowerExpr.accept(this);
			if (!parameter.lowerExpr.type.subTypeOf(parameter.type))
				throw new WrongType(parameter.lowerExpr,parameter.type);
			parameter.upperExpr.accept(this);
			if (!parameter.upperExpr.type.subTypeOf(parameter.type))
				throw new WrongType(parameter.upperExpr,parameter.type);
		}
	}
	
	@Override
	public void visit(Identifier id) { }
	
}
