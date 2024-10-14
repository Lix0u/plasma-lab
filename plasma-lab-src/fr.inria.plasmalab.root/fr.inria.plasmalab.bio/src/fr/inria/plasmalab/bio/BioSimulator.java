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
package fr.inria.plasmalab.bio;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import fr.inria.plasmalab.bio.bm.Parser;
import fr.inria.plasmalab.bio.bm.Reaction;
import fr.inria.plasmalab.bio.bm.Scanner;
import fr.inria.plasmalab.bio.bm.UpdateVect;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

/**
 * <p>Simulator is the external simulator class expected by PLASMA-lab.</p>
 * <p>PLASMA-lab is a version of the statistical model checker PLASMA which is designed to be
 * used with an external source of simulation traces. PLASMA-lab also contains methods that
 * allow external programs to control the model-checking process and to receive results.</p>
 * @author Sean Sedwards
 * @author kevin.corre@inria.fr Integration into PLASMA Lab
 * @version 30/05/2013 BIO language
 * 
 */
public class BioSimulator extends AbstractModel {
	/**
	 * Used to initialise variables before value is correctly set.
	 */
	public static int ERROR = -1;
	/**
	 * Signifies an undefined type in the state.
	 */
	public static int UNDEFINED = 0;
	/**
	 * Signifies a numerical variable type in the state. 
	 */
	public static int NUMBER = 1;
	/**
	 * Signifies a Boolean variable type in the state.
	 */
	public static int BOOLEAN = 2;
	/**
	 * Type of a continuous time clock.
	 */
	public static int TIME = 3;
	/**
	 * Type of a discrete time step counter.
	 */
	public static int STEP = 4;
	/**
	 * The value that must be used to indicate false for a Boolean variable.
	 */
	public static int FALSE = 0;
	/**
	 * The value that must be used to indicate undecided for a Boolean variable.
	 */
	public static int UNDECIDED = 1;
	/**
	 * The value that must be used to indicate true for a Boolean variable.
	 */
	public static int TRUE = 2;
	
	/**
	 * Maps an String to its Identifier object.
	 */
	Map<String,InterfaceIdentifier> identifiers = new HashMap<String,InterfaceIdentifier>();

	/**
	 * The length of the state array
	 */
	int statelength = -1;
	
	/** 
	 * Contains the current trace. ie a list of State.
	 */
	private List<InterfaceState> trace;

	/**
	 * Contains the types of the corresponding variables in the state array.
	 * Position 0 must have type TIME or type STEP. Variables have type NUMBER or BOOLEAN. 
	 */
	int [] types;

	private Parser parser;
	private Reaction [] system;
	static private Random ran = new Random();
	private double totalpropensity;
	private int deadlockPos;
	InterfaceState initState;
	/*
	 * ************************************ CONSTRUCTOR ******************************************************
	 */
	
	public BioSimulator(String name, File file, String id) {
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());;
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		content.substring(0, content.length()-2);
		this.origin = file;
		errors = new ArrayList<PlasmaDataException>();
		
	}

	public BioSimulator(String name, String content, String id) {
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
		errors = new ArrayList<PlasmaDataException>();
	}

	@Override
	public InterfaceState newPath() {
		trace = new ArrayList<InterfaceState>();
		//initialize initial State
		InterfaceState state = initState.cloneState();
		//add initial state to trace
		trace.add(state);
		deadlockPos = -1;
		return state;
	}

	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("Bio simulator doesn't support seed initialization");
	}

	public InterfaceState createInitialState() {
		//initialize initial State
		InterfaceState state = new BioState(statelength, identifiers);

		for(Entry<String,Integer> specy : parser.species.entrySet())
		{	// initialise state with variable initial values
			((BioState) state).setAtPos(((BioIdentifier)identifiers.get(specy.getKey())).getStatePos(), specy.getValue().doubleValue());
		}
		
		return state;
	}


	@Override
	public InterfaceState newPath(List<InterfaceState> initTrace) {
		this.trace = new ArrayList<InterfaceState>(initTrace);
		return trace.get(trace.size()-1);
	}

	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		BioState state = new BioState((BioState) trace.get(trace.size()-1));
		computePropensity(state);
		parser.state = state;
		double amu = 0, r, a;
		if (totalpropensity > 0)
		{
			for(r=ran.nextDouble(); r == 0; r=ran.nextDouble());
			state.setAtPos(0, ((BioState) state).getAtPos(0)-Math.log(r)/totalpropensity);
			for(r=ran.nextDouble(); r == 0; r=ran.nextDouble());
			a = r*totalpropensity;
			
			int index;
			for(index = 0; a > amu && index < system.length; index++)
				amu += system[index].propensity;
			if (index == system.length)
				totalpropensity = amu;	// take advantage
			index--;
			system[index].updateState(state);
			for (UpdateVect v = system[index].update; v != null; v = v.next)
			{
				totalpropensity -= v.reaction.propensity;
				v.reaction.calculatePropensity(state);
				totalpropensity += v.reaction.propensity;
			}
			trace.add(state);
			return state;
		}
		else
		{
			deadlockPos = trace.size()-1;
			throw new PlasmaDeadlockException(state, trace.size());
		}
	}

	@Override
	public void cut(int stateNb) throws PlasmaSimulatorException {
		if(stateNb >= trace.size())
			throw new PlasmaSimulatorException("Cannot cut at a position superior than trace length.");
		for(int i=0; i<stateNb; i++ )
			trace.remove(0);
	}

	private void computePropensity(BioState state) {
		totalpropensity = 0;
		for(int i = 0; i < parser.reactioncount; i++)
		{	// initialise propensities
			system[i].calculatePropensity(state);
			totalpropensity += system[i].propensity; 
		}
	}

	@Override
	public void backtrack() throws PlasmaSimulatorException {
		if(trace.size()>1)
			trace.remove(trace.size()-1);
		else
			throw new PlasmaSimulatorException("Trace is already at initial state. Cannot backtrack.");
	}

	@Override
	public InterfaceState getStateAtPos(int pos) {
		return trace.get(pos);
	}

	@Override
	public List<InterfaceIdentifier> getStateProperties() {
		return new ArrayList<InterfaceIdentifier>(0);
	}

	@Override
	public boolean checkForErrors() {
		identifiers.clear();
		errors.clear();
		Scanner scanner = new Scanner(new ByteArrayInputStream(
				content.getBytes()));
		parser = new Parser(scanner);
		parser.Parse();
		if (parser.errors.count == 0) {
			identifiers.put("", new BioIdentifier("", 0)); // position of time

			for (String id : parser.variables.keySet()) {
				identifiers.put(id, (InterfaceIdentifier) new BioIdentifier(id,
						parser.variables.get(id)));
			}
			parser.species.put("", 0); // initial value of time
			statelength = parser.speciescount + 1; // includes time
			types = new int[statelength];
			// names = new String[statelength];
			Arrays.fill(types, NUMBER);
			types[0] = TIME;
			system = new Reaction[parser.reactioncount];
			/*
			 * for(Iterator<String> iter = parser.species.keySet().iterator();
			 * iter.hasNext();) { String key = iter.next();
			 * System.out.println(key); int index = parser.variables.get(key);
			 * int value = parser.species.get(key); names[index] = key;
			 * //System.out.println(key + " = " + value); }
			 */
			int i = 0;
			for (Reaction r = parser.reactions; r != null; r = r.next) {
				system[i++] = r;
			}
			
			//Reset initial state
			initState = createInitialState();			
			return false;
		} else {
			errors.add(new PlasmaSyntaxException(parser.errors.getMsg()));
			return true;
		}
		
	}

	@Override
	public InterfaceState getCurrentState() {
		return trace.get(trace.size()-1);
	}

	@Override
	public int getDeadlockPos() {
		return deadlockPos;
	}

	@Override
	public List<InterfaceState> getTrace() {
		return trace;
	}

	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		return identifiers;
	}

	@Override
	public boolean hasTime() {	
		return true;
	}

	@Override
	public InterfaceIdentifier getTimeId() {
		return identifiers.get("");
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		return getIdentifiers().values().toArray(new InterfaceIdentifier[getIdentifiers().size()]); 
	}

	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		//Modify values of init State
		for(InterfaceIdentifier id:update.keySet())
			initState.setValueOf(id, update.get(id));
	}
}

