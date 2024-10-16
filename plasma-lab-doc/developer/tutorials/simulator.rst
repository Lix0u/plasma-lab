Simulator
=========

In PLASMA Lab the concepts of simulator and model are mixed together. We
could say that a model executes itself. For this reason, a simulator
inherits from the *AbstractModel* class, that in turn inherits from the
*AbstractData* class. The *AbstractData* class describes an object, model or
requirement, editable in the PLASMA Lab GUI edition panel.
The *AbstractModel* class adds simulation methods.

In this section, we explain some of the implementation needed for a new
simulator plugin. The language executed by our tutorial simulator is a
succession of ``+`` and ``-``. Starting from 0 it will add or remove one to
the single value of the state. For instance the model ``+++--`` will produce a trace ``0 1 2
3 2 1``. Of course this language has no stochastic property.

We only cover a few important methods.

Configuration
^^^^^^^^^^^^^

The *checkForErrors* function is called before each experimentation/simulation and when modifying the content of the edition
panel in the GUI. The purpose of this function is double: to detect any syntax error
and to build the model before running it.

*Note: the checkForErrors function is part of AbstractData.*

  Our *checkForErrors* function checks if the sequence contains any other
  characters than "+", "-". In the eventuality of a syntax error, a *PlasmaSyntaxException* is added to the list of
  errors.

  .. code:: java

	  @Override
	  public boolean checkForErrors() {
		  // Empty from previous errors
		  errors.clear();

		  // Verify model content
		  InputStream is = new ByteArrayInputStream(content.getBytes());
		  br = new BufferedReader(new InputStreamReader(is));
		  try {
			  while(br.ready()){
				  int c = br.read();
				  if(!(c=='+'||c=='-')){
					  errors.add(new PlasmaSyntaxException(Character.toString((char)c)+" is not a valid command"));
				  }
			  }
		  } catch (IOException e) {
			  errors.add(new PlasmaDataException("Cannot read model content",e));
		  }
		  initialState = new MyState(0,0);
		  return !errors.isEmpty();
	  }

We also create the initial state which will be used to initialize each trace. We could
specify a different initial value for an experiment, for instance using
optimization declaration in BLTL. This would call the *setValueOf*
method to modify the initial state.

  .. code:: java

	  @Override
	  public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		  // This method change the initial state with a set of initial values.
	      for(InterfaceIdentifier id:update.keySet())
		  initialState.setValueOf(id, update.get(id));
	  }


New Path
^^^^^^^^

The *content* String is inherited from AbstractData and contains the
text entered in the edition panel. In our simulator we initialize a
stream reader to read *content* character by character.
We also initialize the trace with the initial state created by the *checkForErrors* methods.

  .. code:: java

        @Override
        public InterfaceState newPath() {
	    trace = new ArrayList<InterfaceState>();
	    trace.add(initialState);
	    InputStream is = new ByteArrayInputStream(content.getBytes());
	    br = new BufferedReader(new InputStreamReader(is));
	    return initialState;
        }


Simulate
^^^^^^^^

In the simulate method we read the next character. In our language,
"+" (resp. "-") **add** 1 to the value (resp. **substract** 1 to the value).
We also add 1 to the time. We build a new state with the new values. If there is no more character to read we throw a
*PlasmaDeadlockException* instead of adding a new state to the trace.

  .. code:: java

        @Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		try {
			    if (!br.ready())
				throw new PlasmaDeadlockException(getCurrentState(), getTraceLength());
			    else {
				int c = br.read();
				InterfaceState current = getCurrentState();
				double currentV = current.getValueOf(VALUEID);
				    double currentT = current.getValueOf(TIMEID);
				if(c=='+')
				    trace.add(new MyState(currentV+1,currentT+1));
				else if(c=='-')
				    trace.add(new MyState(currentV-1,currentT+1));
			    }
		    } catch (IOException e) {
			    throw new PlasmaSimulatorException(e);
		}
		return getCurrentState(); 
	}


State
^^^^^

A state object is used to store the values of the model. It inherits
from the *InterfaceState* interface. Our state object is pretty simple
as we store only two variables, the time and the value.

  .. code:: java

      public class MyState implements InterfaceState {
	  
	double value;
	double time;
	
	public MyState(double value, double time) {
		this.value = value;
		this.time = time;
	}

The *InterfaceState* declares several ways of accessing and setting
values. *InterfaceIdentifier* objects are the main way to identify objects (e.g. variable, constant) through the components of PLASMA Lab.
The *InterfaceState* interface also provides a method for accessing a value by the string of its identifier.

  .. code:: java

	@Override
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException {
	    if(id.equals(MySimulator.VALUEID))
		return value;
	    else if(id.equals(MySimulator.TIMEID))
		return time;
	    else
		throw new PlasmaSimulatorException("Unknown identifier: "+id.getName());
	}
	
	@Override
	public Double getValueOf(String id) throws PlasmaSimulatorException {
	    if(id.equals(MySimulator.VALUEID.getName()))
		return value;
	    else if(id.equals(MySimulator.TIMEID.getName()))
		return time;
	    else
		throw new PlasmaSimulatorException("Unknown identifier: "+id);
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException {
	    if(id.equals(MySimulator.VALUEID))
		this.value = value;
	    else if(id.equals(MySimulator.TIMEID))
		this.time = value;
	    else
		throw new PlasmaSimulatorException("Unknown identifier: "+id.getName());
	}

Except getters and setters, the relation between state objects and their
associated model is free. As their can be a large number of state for a
single model instance, we recommend to keep the memory usage of states
as low as possible.

Identifier
^^^^^^^^^^

Identifiers are a shared objects to identify values (e.g. variable, constant) through different components of PLASMA Lab.
As our model has only two variables, we declare them as static identifiers in the simulator.

  .. code:: java

        protected static final MyId VALUEID = new MyId("X"); //VALUE
        protected static final MyId TIMEID = new MyId("#"); //TIME


An external component, such as a checker component, access the identifier of the model through *getIdentifiers*.
This creates a map to store the identifiers and sort them according to their name.

  .. code:: java

	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		Map<String, InterfaceIdentifier> map = new HashMap<String, InterfaceIdentifier>();
		map.put(TIMEID.getName(), TIMEID);
		map.put(VALUEID.getName(), VALUEID);
	}

We could then write a BLTL property checking if the value of the model
reached a given threshold after 10 simulation steps: ``F<=#10 X > 5``.
This uses *getTraceLength* function to measure the number of steps.

Our model also contains its own notion of time implemented by **TIMEID**.
In the simulator we implement the methods *getTimeId* and *hasTime* to notify
that the model has its own time.

  .. code:: java

        @Override
        public InterfaceIdentifier getTimeId() {
            return TIMEID;
        }

        @Override
        public boolean hasTime() {
            //Return true if model as a dedicated time value. 
            //Otherwise only trace length is used.
            return true;
        }

Then, we could check the property using the time implemented in the states instead of the steps:
``F<=10 X > 5``. This would return the same results as the previous property since time
increases at the same pace than steps (we add 1 to time at each step).

        


