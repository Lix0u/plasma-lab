Checker
=======

In PLASMA Lab the concepts of checker and requirement are mixed
together. We could say that a requirement checks itself. For this
reason, a checker inherits from the *AbstractRequirement* class, that in
turns inherits from the *AbstractData* class. The *AbstractRequirement* class adds verification methods
to the *AbstractData* class.

In this section, we explain some of the implementation needed for a new
checker plugin. The language verified by our plugin contain two values.
The first is a time stamp *TIME* and the second a value *VAL*. 
the checker checks a trace of the model when it reaches  time *TIME*.
If the value of the model variable *"X"* is strictly superior to *VAL*, the requirement is evaluated to true. Otherwise
the evaluation returns false.

Configuration
^^^^^^^^^^^^^

The *checkForErrors* function is called before each experimentation/simulation and when modifying the content of the edition
panel in the GUI. The purpose of this function is double: to detect any syntax error
and to build the requirement before running it.

   Our *checkForErrors* function checks if the sequence contains at least two integer values: one for the time bound, the other for the threshold.
   A PlasmaSyntaxException is added to the list of errors if an error is found.

   .. code:: java

	@Override
	public boolean checkForErrors() {
	    errors.clear();         
	    String[] splitted = content.split(" ");
	    try{
	        if(splitted.length < 2)
	            errors.add(new PlasmaSyntaxException("The checker needs a time and a threshold value"));
	        until = Integer.valueOf(splitted[0]);
	        threshold = Integer.valueOf(splitted[1]);
	    } catch(Exception e){
	        errors.add(new PlasmaDataException("Exception occurred while parsing requirement", e));
	    }
	    return !errors.isEmpty();
	}
   
We also associate the requirement to model with the *setModel* method.
This method is called before *checkForErrors* when starting an experimentation/simulation.
It will allow usually to get the identifiers of the model and then check if the variables use in the requirement
correspond to variables of the model.
   
   With the *setModel* method we get the model and the *TIMEID* used by the model to count time.
   
   .. code:: java
   
        @Override
	public void setModel(AbstractModel model) {
		this.model = model;
		this.TIMEID = model.getTimeId();
	}
	
   Note: before launching an experimentation/simulation no model is associated to the requirement.
   Then, the requirement *checkForErrors* function may not be able to find the variable of the model and can produce errors.
            
Check
^^^^^

The *check* method computes the results of checking the requirement on a trace.
This results is represented by a double value. If the result should only be a Boolean
then the method should return 0 if false, and 1 if true.

  Our requirements check if the value *"X"* is superior to a threshold after
  *TIME* timestamp was reached. We evaluate  To evaluate this property, we simulate the trace up to the needed time by calling the *simulate* method.
  To measure time we use the *TIMEID* provided by the model.
  Once enough states have been generated, we check the variable against the threshold and
  return the result.

  .. code:: java

	@Override
	public Double check(InterfaceState path) throws PlasmaCheckerException {
		try {
			while(model.getCurrentState().getValueOf(TIMEID)<until)
				model.simulate();
			return (model.getCurrentState().getValueOf("X") > threshold ? 1.:0.);
		}
		catch (PlasmaSimulatorException e) {
			throw new PlasmaCheckerException(e);
		}
	}

There are two other check functions. The first is used in simulation mode.
In this mode the user has a direct control over the simulation to add or remove states from the trace.
The check function specifies an *untilStep* parameter that defines the length of the trace that must be checked.
If the requirement cannot check the requirement within this length and more state  are needed, the checker
returns a NaN value meaning *"undecided"*.

Note: Here *untilStep* refers to the step time, not the time implemented in the model.

  Therefore our checker with *untilStep* parameter only checks the model current path and does not simulate new state.

  .. code:: java

	@Override
	public Double check(int untilStep, InterfaceState path) throws PlasmaCheckerException {
	    try {
		for(int step=0; step < untilStep || step >= model.getTraceLength(); step++){
		    InterfaceState state = model.getStateAtPos(step);
		    if(state.getValueOf(TIMEID) == until)
			//Time bound reached
			return (model.getCurrentState().getValueOf("X") > threshold ? 1.:0.);
		}
	    }
	    catch (PlasmaSimulatorException e) {
		throw new PlasmaCheckerException(e);
	    }   
	    //Untilstep reached or not enough state to reach time bound
	    return Double.NaN;
	}

The final check function takes has parameters an identifier and a value. It checks the trace until it is decided or the identifier reaches         
the given value. This function is used by the importance splitting algorithm only, and may not be implemented otherwise.
