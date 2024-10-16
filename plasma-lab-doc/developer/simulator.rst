Simulator
=========

A simulator executes a model to generate observation traces.
In PLASMA Lab a model is described in a textual content.
This can either contain the description of a model
or a link to an external simulator. In that latter case,
the simulator plugin loaded by PLASMA Lab acts only as an interface
between PLASMA LAB and the external simulator.

AbstractModel
-------------

Each simulator inherits from the *AbstractModel* class that itself inherits from *AbstractData*.
*AbstractData* describes a container for a data such as a model (but also a requirement) and provides functions such as
*getName*, *getContent* and *checkForErrors*. As this abstract class is
quite simple we won't get too much into details and we focus on *AbstractModel*.

CheckForErrors
^^^^^^^^^^^^^^

.. code:: java

  public boolean checkForErrors();
  
**checkForErrors** comes from the *AbstractData* class. A call to this
function needs to parse the model content and to initialize any data structure needed
before starting a simulation. If errors were found, the function returns true.

After modifying the model, a single call to *checkForErrors* is needed
before starting a batch of simulation (several calls to *newPath*).

New Path
^^^^^^^^

.. code:: java

  public InterfaceState newPath() throws PlasmaSimulatorException;
  
.. code:: java

  public InterfaceState newPath(List<InterfaceState> initTrace) throws PlasmaSimulatorException;

.. code:: java

  public InterfaceState newPath(long seed) throws PlasmaSimulatorException;

The **newPath** methods initialize a new trace and return the initial
state. A version of this method takes an initial trace as a parameter to start the simulation 
from the last state of this trace.
another version takes a seed as a parameter. The seed can then be freely use by the simulator. Depending on the semantic
used in each simulator, the seed may not have the same usage from one simulator to another.
The method may throw a *PlasmaSimulatorException* if an error occurred in the simulator.

Simulation
^^^^^^^^^^

.. code:: java

  public InterfaceState simulate() throws PlasmaSimulatorException;
	
Once the trace was started with *newPath*, the **simulate** method is
used to progress the simulation step by step. A call to *simulate* will
thus execute a single simulation step. The *simulate* method 
returns the new state added to the trace (that is to say the last state of the trace).
If a deadlock is reached (no new state can be added), a *PlasmaDeadlockException* is thrown.
The method may throw a *PlasmaSimulatorException* if an error occurred in the simulator.

.. code:: java

  public void backtrack() throws PlasmaSimulatorException;

The **backtrack** method cancels the last call to *simulate*.

.. code:: java

  public void cut(int stateNb) throws PlasmaSimulatorException;

The **cut** function takes an integer *stateNb* as a parameter and cut
the current trace at the *stateNb* position. Every state before this
point is deleted as if the state at position *stateNb* was the initial
state.

.. code:: java

  public void clean() throws PlasmaSimulatorException;

The **clean** method is called after a simulation was completed and
before a new one start. It is used in case some operations must be
done in order to return to a safe state. It must not necessarily
be implemented by the simulator. *AbstractModel* already provides
an implementation of this method that does nothing.


State and trace getters
^^^^^^^^^^^^^^^^^^^^^^^

*AbstractModel* provides several getters to states.

.. code:: java
  
  public InterfaceState getCurrentState();

Retrieve the head of the current path, ie: the latest state generated.

.. code:: java

  public InterfaceState getStateAtPos(int pos);

Retrieve the state at the position given in parameter.

.. code:: java

  public List<InterfaceState> getTrace();

Retrieves the current trace. A simulation **trace** (or path) is a list of states.

.. code:: java

  public int getTraceLength() {
    return getTrace().size();
  }

This method is already implemented by *AbstractModel* and return the length of the trace (number of states).

.. code:: java

  public abstract int getDeadlockPos();
  
This method return the position of the last state in the trace if a deadlock occurred or -1 if no deadlock occurred.  


Identifiers getters
^^^^^^^^^^^^^^^^^^^

Several methods allows to access the identifiers used by the model. These identifiers are used to communicate values to the checker or
to the user interfaces.

.. code:: java

  public abstract Map<String, InterfaceIdentifier> getIdentifiers();
  
  
This method returns a map of all the identifiers that can be evaluated on a state and used in the requirements.

.. code:: java
  
  public abstract InterfaceIdentifier[] getHeaders();

This method returns an array of identifiers that are followed along a trace in simulation mode.
They will appear for instance in the simulation results panel of GUI in the same order as in the array. 

.. code:: java

  public abstract InterfaceIdentifier getTimeId();
	
This method returns the identifier that counts the continuous time.
It may be null if the model has no continuous time.

.. code:: java

  public abstract boolean hasTime();

This method returns true if the model provides continuous time (like CTMC).

.. code:: java

  public abstract List<InterfaceIdentifier> getStateProperties();
  
This method return a list of identifiers that represent state properties.
A state property is a boolean formulae that is evaluated on a state. It is represented by an 
*InterfaceIdentifier* whose value will be obtained from a state. It is used in Simulation mode in the properties panel.

Other methods
^^^^^^^^^^^^^

.. code:: java

  public void setValueOf(Map<InterfaceIdentifier,Double> update) throws PlasmaSimulatorException;
  
This method allows to modify the initial value of the model. It is used to perform optimization
of the model parameters or to start the simulator in a different state, for instance by the improtance splitting algorithm.

.. code:: java

  public List<Variable> getOptimizationVariables();
	
This method returns a list of variables on which to perform optimization.
Implemented by default in *Abstractmodel* to return an empty list.

.. code:: java

  public List<VariableConstraint> getOptimizationConstraints();

This method returns a list of constraint on the optimization variables.
Implemented by default in *Abstractmodel* to return an empty list.

InterfaceIdentifiers
--------------------

**InterfaceIdentifier** is an interface used in PLASMA Lab to represents an object acting as an identifier.

A **GenericIdentifier** implementation of the interface is provided in the workflow project.
It has only a name attribute.


InterfaceState
--------------

**InterfaceState** represents a mapping from *InterfaceIdentifiers* to values that constitutes a single state of a simulation.
It can also reference an *InterfaceTransition* object that represents the transition from which this state was reached.
It extends the *ResultInterface*.

The interface provides two methods for accessing the values of the state, either through an 
*InterfaceIdentifier* object or through the name of the identifier.

.. code:: java

  public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException;
	
.. code:: java

  public Double getValueOf(String id) throws PlasmaSimulatorException;
	
A **GenericState** implementation of the interface is provided in the workflow project.
	

Optimization variables
----------------------

Optimization variables are used to initialize a model with a range of
initial state in the same experiment. PLASMA Lab uses this feature with
the :doc:`optimization procedure <../gui/optimization>`.

Optimization variables can be declared in the model or in the requirements
and are retrieved using the **getOptimizationVariables** function.
