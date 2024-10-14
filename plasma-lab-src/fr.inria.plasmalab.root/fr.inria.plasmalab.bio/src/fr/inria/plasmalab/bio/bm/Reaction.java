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

import fr.inria.plasmalab.bio.BioState;

/**
 * Class for all the types of reaction
 * @author Sean Sedwards
 * @version 6/12/2012
 */
public class Reaction {
	static final int gilla = 0, gillb = 1, gillc = 2, gilld = 3, gille = 4, gillf = 5, gillg = 6;
	double rateconstant = 1;
	int pos = -1, kind = -1;
	int r1 = -1, r2 = -1, r3 = -1;
	public double propensity;
	public Reaction next;
	ChangeVect action;
	public UpdateVect update;

	public Reaction(int pos)
	{
		this.pos = pos;
	}
	
	void addDependent(UpdateVect u)
	{	// all the reactions dependent on a species changed by this reaction
		for (; u != null; u = u.next)
		{
			UpdateVect v;
			for (v = update; v != null; v = v.next)
			{
				if (v.reaction == u.reaction) break;	// already present
			}
			if (v == null)
			{
				v = new UpdateVect(u.reaction);
				v.next = update;
				update = v;
			}
		}
	}
	
	void addChange(int index, int change)
	{
		ChangeVect v, vold;
		for(vold = v = action; v != null; v = v.next)
		{
			if (v.index == index)
			{
				v.change += change;
				break;
			}
			vold = v;
		}
		if (v != null && v.change == 0)
		{	// no change now so remove v
			if (v == action) action = v.next;	// v was first
			else vold.next = v.next;
		}
		else if (change != 0)
		{	// index not present
			v = new ChangeVect(index,change);
			v.next = action;
			action = v;
		}
	}
	
	public void calculatePropensity(BioState state)
	{
    	propensity = rateconstant;
    	switch(kind)
		{
    	case gille:	// A+B+C
    		propensity *= state.getAtPos(r3);
		case gillc:	// A+B
			propensity *= state.getAtPos(r2);
		case gillb:	// A
			propensity *= state.getAtPos(r1);
		case gilla:	// *
			break;
		case gilld:	// A+A
			propensity = rateconstant*state.getAtPos(r1)*(state.getAtPos(r1)-1);
			break;
		case gillf:	// A+A+B
			propensity = rateconstant*state.getAtPos(r2)*state.getAtPos(r1)*(state.getAtPos(r1)-1);
			break;
		case gillg:	// A+A+A
			propensity = rateconstant*state.getAtPos(r1)*(state.getAtPos(r1)-1)*(state.getAtPos(r1)-2);
			break;
			default: System.err.println("unknown reaction kind");
		}
	}
    
    public void updateState(BioState state)
    {
    	for(ChangeVect v = action; v != null; v = v.next)
    	{
    		state.setAtPos(v.index, new Double(state.getAtPos(v.index)+v.change));
    	}
    }
    
    public String dependentsToString()
    {
    	String s = "{";
    	for (UpdateVect v = update; v != null; v = v.next)
    	{
    		s += " " + v.reaction.pos;
    	}
    	return s + " }";
    }
}
