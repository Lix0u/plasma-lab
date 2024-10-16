Requirement checker
===================

A requirement (or property) is a specification that is observed other a trace. It is generally specified
in a formal logic like the :doc:`Bounded Linear Temporal Logic <../languages/bltl>`
and comes as a textual description. The checker implements the algorithms necessary
to checked a trace according to the semantics of the requirement.

AbstractRequirement
-------------------

Each checker inherits from the *AbstractRequirement* class that itself inherits
from *AbstractData*.

CheckForErrors
^^^^^^^^^^^^^^

.. code:: java

  public boolean checkForErrors();

**checkForErrors** come from the *AbstractData* class. A call to this
function will parse the requirement and initialize any data structure
needed before checking the requirement. If errors were found, the function
returns true.

After modifying the requirement, a single call to *checkForErrors* is
needed before starting a batch of verification (several calls to
*check*).

Check
^^^^^

.. code:: java

  public Double check(InterfaceState path) throws PlasmaCheckerException;

The **check** function takes an initial *InterfaceState* as a
parameter and checks the requirement from this state. It returns a
double value once a decision has been made. Depending on the requirement
language, the number returned may represent a Boolean or a decimal value.

Although the simulation is initialized (call to *newPath*) before
starting the verification, the checker is in control of the simulation.
During the simulation, the checker will indeed call the *simulate* method
to add new states to the trace on demand.

.. code:: java

  public Double check(int untilStep, InterfaceState path) throws PlasmaCheckerException;

A second version of the *check* function takes an integer as an
additional parameter. This parameter, *untilStep*, tells the checker to
verify the simulation until a given step. This function is used with the simulation
mode of PLASMA Lab. Using this mode, the simulation is directly under control of
the user. If the verification reach the *untilStep* bound, the Checker must not call the
*simulate* method. If no decision can be made once the bound has been
reached, the Checker can use the *Double.NaN* constant (Not A Number) with the
meaning *undecided*.

.. code:: java

  public abstract Double check(String id, double untilValue, InterfaceState path)  throws PlasmaCheckerException;
  
This third version of the *check* function additionally considers as parameters the name of an identifier and a final value.
It should check the requirement until the given variable reaches a certain value or a deadlock is reached.
However, this method doesn't guarantee termination in case the goal value is never reached.

Others
^^^^^^

.. code:: java

  public void clean();

The **clean** method is called after an experiment was completed and
before a new one start. It is used in case some operations must be
done in order to return to a safe state.

.. code:: java

  public void setModel(AbstractModel model);

Set the *AbstractModel* object to verify.

.. code:: java

  public  List<InterfaceState> getLastTrace();

Retrieve the last trace that has been checked with the checker.

.. code:: java

  public void setSpecificParameters(Object[] parameters) throws PlasmaParameterException;

Optional method whose sole purpose is to pass specific parameters for some requirements types.

.. code:: java

  public List<Variable> getOptimizationVariables();

Retrieve optimization variables declared by this requirement.

.. code:: java

  public List<AbstractRequirement> generateInstantiatedRequirement();

Instantiate a list of *AbstractRequirement* from this requirement and the range of *requirement variables* declared in it.
*Requirement variables* are similar to *optimization variables* but are used to generate a set of requirements instead of a set of initial states.
