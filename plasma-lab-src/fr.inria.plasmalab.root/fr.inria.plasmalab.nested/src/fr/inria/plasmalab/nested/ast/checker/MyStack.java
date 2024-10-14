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
package fr.inria.plasmalab.nested.ast.checker;

import java.util.Stack;

public class MyStack<Elt> extends Stack<Elt> {

    private static final long serialVersionUID = 3079609684372775396L;
    public boolean debug = false;

    @Override
    public Elt pop() {
        Elt e = super.pop();
        if (debug)
            System.out.println("pop of " + super.toString());// + "size = " + super.size());
        return e;

    }

    @Override
    public Elt peek() {
        if (debug)
            System.out.println("peek of " + super.toString());// + "size = " + super.size());
        return super.peek();
    }

    @Override
    public Elt push(Elt obj) {
        if (debug)
            System.out.println("push of " + super.toString() + "::" + obj);// + "size = " + super.size());
        return super.push(obj);
    }
} 