Algorithms
==========

In this section we introduce a generic view of the algorithm class in PLASMA
Lab. For this purpose we consider the case of a local (non distributed)
algorithm. The implementation of distributed algorithms is presented in the next :doc:`section <algorithms_distributed>`.

InterfaceAlgorithmFactory
^^^^^^^^^^^^^^^^^^^^^^^^^

As presented before, algorithms factories implements the *Plugin* interface.
They are responsible for parsing the algorithm's parameters and constructing the scheduler class of the algorithm.

As each algorithm needs various parameter as input, we developed generic
objects to declare these parameters. Each algorithm factory declares a
list of SMCParameter objects. This list is then used by PLASMA Lab to
retrieve correct values from the user.

First we present each type of parameters. Then we describe the way a
list of parameter is used and values retrieved by PLASMA Lab.

SMCParameter
------------

The *SMCParameter* class is the parent class of all parameters. Its
basic attributes are a *name*, a *tip* and a Boolean that defines if the parameter
represents a Boolean value or not.

Used alone, an *SMCParameter* represents a simple text, double or Boolean value.

SMCOption
---------

A *SMCOption* is a Boolean parameter that contains a list of other
*SMCParameter*. If the *SMCOption* is set to true, its children parameters
are enabled and can be filled by the user.

*SMCOption* behaves like a check box object, and it is represented by one in PLASMA Lab GUI.

SMCAlternatives
---------------

An *SMCAlternative* is a Boolean parameter that contains a list of other
*SMCParameter* and points to another *SMCAlternative* (like the linked list mechanism).
If the *SMCAlternative* is set to true, its children parameters are enabled and can be filled, while every other
*SMCAlternative* in the linked list are set to false.

An *SMCAlternative* behaves like a radio button object, and it is represented by one in PLASMA Lab GUI.

SMCObjectParameter
------------------

An *SMCObjectParameter* defines a parameter to be an AbstractModel. It is
specifically used in the CUSUM algorithm, that needs to be executed on
two different models (the basic GUI can only select one model).

The *SMCObjectParameter* is interpreted by the PLASMA Lab GUI as a
ComboBox containing models from the currently selected project.

Parameters workflow
-------------------

- **Creating parameter widgets**

An *InterfaceAlgorithmFactory* object defines a list of *SMCParameter* that correspond to their algorithm.
This list is accessed by the *ParametersPanel* class using the **getParametersList** method.
A widget (CheckBox, RadioButton, TextField, ...) is created in the GUI for each parameter, including children
parameters. Some widgets depend on their parent widget to be enabled.

- **Retrieving parameters values**

When launching a new experiment, the *ExperimentPanel* access to the
*ParametersPanel* corresponding to the selected algorithm and retrieves a
map of String/Object pairs. For each pair, the String corresponds to
the name of the parameter (as provided by the *InterfaceAlgorithmFactory* and
displayed on the widget) and the Object is the value of the parameter.
As said previously, the value could be of type Boolean, String, or even
*AbstractModel*.

Additional parameters may be added to the map such as *distributed*,
which informs the system if a distributed version of an algorithm should
be used.

The map and the selected *InterfaceAlgorithmFactory* is then transmitted to the
*ExperimentationManager*. The *ExperimentationManager* calls the
**createScheduler** function on the factory with the parameters map as an argument.
This function parses the values of the parameters and it checks if they suit the algorithm.
It then creates an instance of the *InterfaceAlgorithmScheduler* ready to be executed and returns it.

- **Alternative workflows**

The previous method to retrieve parameters values requires the use of widgets,
which may be unavailable with user interfaces different than the GUI (command line, interface with another tool, ...).

An alternative workflow (used by the command line) is to construct directly a map of parameters name and values,
defined by the user. In that case the user must be aware of which parameters and values are valid and necessary.
The map is then transmitted to the *createScheduler* function.

Another solution is to call the *fillParametersMap* method of *InterfaceAlgorithmFactory*.
This method converts an array of parameters values (written as String values) into a map of String/Object pairs. 
This method fills the map with the parameters value taken from the array by converting the values to their correct types.
The content and the order of the parameters in the String array is however entirely dependant of the *InterfaceAlgorithmFactory* implementation.

InterfaceAlgorithmScheduler
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code:: java

        public interface InterfaceAlgorithmScheduler extends Runnable

This class provides the main interface for an SMC algorithm (both local or distributed).
This interface extends the *Runnable* interface. 
Thus an algorithm will have a *run* method.

.. code:: java

        public void setExpListener(ExperimentationListener listener);

The *InterfaceAlgorithmScheduler* also defines a *setExpListener* method.
Object implementing the *ExperimentationListener* interface will be kept
up to date on the experiment progress using calls to functions such
as *notifyAlgorithmStarted*, *notifyAlgorithmCompleted* and *publishResults*.

.. code:: java

        public void abortScheduling();

The *abortScheduling* method is called when the user wants to stop a
running experiment before it finishes.


Run method of an SMC Algorithm
------------------------------

The run method of an SMC Algorithm is where the work is done. For instance the run
method of the basic Monte Carlo algorithm could be based on the following pseudo-code:

.. code:: java

        initialize(model, property)
        listeners.notifyAlgorithmStarted

        while(continue)
            state = model.newPath
            positive += property.check(state)
            total ++
            if(total > required)
                continue = false

        listeners.publishResults(getResults(positive))
        listeners.notifyAlgorithmCompleted

For each run, the model initializes a new trace. The property is
checked starting from the initial state. If the property has been
checked on enough traces, the algorithm terminates and the results are
published.

AbstractAlgorithm
^^^^^^^^^^^^^^^^^

This class provides a generic implementation of the basic methods for an SMC algorithm.
It provides in particular an *initializeAlgorithm* method that should be called at the beginning of the *run* method.
This method initializes optimization attributes of the algorithm. 

SMCResult
^^^^^^^^^

The data structure used to return results of an experimentation is the
*SMCResult* interface. This interface inherits from *ResultInterface*.

In PLASMA Lab, a class implementing the *ResultInterface* represents the
output of a task such as a simulation or an experiment. It provides
access to a header array of type String with each header being
associated to a value.

The *SMCResult* interface adds a specific *getPr* method to get a single result of the experiment.
This result is a *double* value, that represents for instance a probability or a Boolean value
with 1 meaning true and 0 meaning false.
Other results are accessed by the *getValue* method of *ResultInterface*.
