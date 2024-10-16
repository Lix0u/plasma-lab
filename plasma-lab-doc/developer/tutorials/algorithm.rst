Algorithm
=========

In this tutorial we will re-implement a simple Monte-Carlo algorithm.

Factory
-------

The factory is used to load the plugin dynamically and specify the parameters of the algorithm.
It implements the *InterfaceAlgorithmFactory* and it declares a new JSPF *Plugin* using the annotation ``@PluginImplementation``
before the class declaration.

    .. code:: java

      @PluginImplementation
      public class MyAlgorithmFactory implements InterfaceAlgorithmFactory
  
Then we implement the functions of the *InterfaceAlgorithmFactory* interface

- The *id* is a unique identifier for the algorithm. It used to select the algorithm in the CLI and through various components of the GUI.
- The *name* will appear the GUI menus. It should be return by the *toString* method.
- The *description* is a short text displayed in the algorithm selection panel or in the CLI *info* command.

  .. code:: java

	  @Override
	  public String getId() {
		  return "myalgorithm";
	  }

	  @Override
	  public String getName() {
		  return "My Algorithm";
	  }

	  @Override
	  public String getDescription() {
		  return "Reimplementation of Monte Carlo.";
	  }
	  
	  @Override
	  public String toString(){
		  return getName();
	  }
	
The parameters of the algorithm are specified by the *getParametersList* method of the factory.
It returns a list of *SMCParameter*. A basic *SMCParameter* is defined with a name and a description.
It will appear in th GUI as a text field allowing to enter a value.
The different types of parameters are presented on the :doc:`algorithms section <../algorithms>`.

    Our algorithm has a single parameter of type *SMCParameter*, with name **Nb sims**, description **Number of simulations**.
    We set the final value to false to mean that the parameter is not associated to a Boolean value.

    .. code:: java
    
	@Override
	public List<SMCParameter> getParametersList() {
		List<SMCParameter> parameters = new ArrayList<SMCParameter>(1);
		parameters.add(new SMCParameter("Nb Sims","Number of simulations", false));
		return parameters;
	}
	
The value of the parameters will be given by the GUI or the CLI in a map *Map<String, Object>*.
This map is read by the *createScheduler* function when starting a new experiment.
The function parses the value of the parameters, checks if they satisfy the requirements of the algorithm 
and finally instantiates the algorithm.

    In our algorithm we parse the value of the "Nb Sims" parameter into an integer and we check if the value is positive.
  
    .. code:: java
    
	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model,	
	    List<AbstractRequirement> reqs,
	    Map<String, Object> parametersMap) throws PlasmaParameterException {
		int nbSims = 0;
		try {
			nbSims = Integer.parseInt(parametersMap.get("Nb Sims").toString());
		}
		catch(Exception e){
			throw new PlasmaParameterException(e);
		}
		if ( !(nbSims > 0) )
			throw new PlasmaParameterException("Nb Sims" + " must be > 0.");
		return new MyAlgorithm(model, reqs, nbSims, getId());
	}

Alternatively we can specify the parameters with the *fillParametersMap* method.
In that case we parse the value from an array of string, assuming that the values have been given in the right order.

    .. code:: java
    
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, 
	    String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=1)
				parametersMap.put("Nb Sims", Integer.parseInt(parameters[0]));
			else
				throw new PlasmaParameterException("Not enough parameters for MyAlgorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException(e);
		}
	}
	
The other methods of the factory are used to create a distributed algorithm.
We will not present this case in this tutorial.

    Therefore we return false from the *isDistributed* method, and we return null values from
    the *createWorker* and *getResourceHandler* methods:
	
    .. code:: java
    
	@Override
	public boolean isDistributed() {
		return false;
	}
	
	@Override
	public InterfaceAlgorithmWorker createWorker(AbstractModel arg0, List<AbstractRequirement> arg1) {
		return null; // not distributed
	}
	
	@Override
	public Class<?> getResourceHandler() {
		return null; // not distributed
	}

	
Algorithm
---------
	
The main class of our new SMC algorithm extends the *AbstractAlgorithm* class.
This class implements some functions from the *InterfaceAlgorithmScheduler* interface
that are common between most SMC algorithm.

    The *AbstractAlgorithm* class already provides the basic attributes for the algorithm (*model, requirements, nodeURI*)
    that we load in the constructor with additionally the number of simulations parameter: 

    .. code:: java

	public MyAlgorithm(AbstractModel model, List<AbstractRequirement> reqs, int nbSims, String id) {
		this.model = model;
		this.requirements = reqs;
		this.nbSims = nbSims;
		this.nodeURI = id;
	}
	
	
    Then we implement the *run* method of the algorithm.
    
    .. code:: java
    
	@Override
	public void run() {
		initializeAlgorithm();
		listener.notifyAlgorithmStarted(nodeURI);
		logger.info("Starting " + nodeURI  + " with " + nbSims + " simulations.");
		
		List<ResultInterface> results = new ArrayList<ResultInterface>(1);
		double result = 0.0;
		try {
			for (int i=1; i<= nbSims && !stopOrderReceived; i++) {
				InterfaceState path = model.newPath();
				double res = requirements.get(0).check(path);
				if (res > 0) {
					result += res; 
				}
			}
		}
		catch (PlasmaExperimentException e) {
			//logger.error(e.getMessage(),e);
			listener.notifyAlgorithmError(nodeURI, e.toString());
			errorOccured = true;
		}
		result /= nbSims;
		results.add(new MyResult(requirements.get(0), result, nbSims));
		
		if(!errorOccured){
			// Notify new results
			listener.publishResults(nodeURI, results);
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}
	
    - We first call the *initializeAlgorithm* method from  *AbstractAlgorithm* in order to perform necessary initializations.
    - We can send notifications to the user interface via the *listener* attribute.
    - We perform *nbSims* simulations by calling the *model.newPath()* method. 
    - Each simulation is checked against the (first) requirement using *requirements.get(0).check(path)*
    - The algorithm is stopped whenever *stopOrderReceived* is set to true (by the *AbstractAlgorithm* class).
    - At the end of the algorithm we create the result (the ratio of the sums of the simulations results with the number of simulations) and we add this to a *ResultInterface* object. 
    - The list of results (in our case a single one) is send to the user interface via the *listener.publishResults* method.
	
Result
------

The final class that we need for our new algorithm is an object to store and send the results of the experimentation.

    Our *MyResult* class implements the *SMCResult* interface.
    
    .. code:: java
      
	public class MyResult implements SMCResult
	
A result is a collection of values associated to an identifier.

    In our result we store only two value: the probability and the number of simulations.
    These two values are the minimal values needed in an SMCResult.
    We create two identifiers associated to these values:

    .. code:: java
    
        private static final ResultIdentifier probaId = new ResultIdentifier("Probability", false);
	private static final ResultIdentifier simId = new ResultIdentifier("#Simulations", false);
    
    We implement a constructor for the result that takes these two values and the requirement that has been checked.
    
    .. code:: java

	public MyResult(AbstractRequirement req, double proba, int nbsims) {
		this.origin = req;
		this.probability = proba;
		this.nbsimulations = nbsims;
	}
	
Then we implement the methods of the *SMCResult* interface.

  The *getCategory* method returns the name of the requirement that has been checked:
    
  .. code:: java
    
	@Override
	public String getCategory() {
		return origin.getName();
	}

  The *getHeaders* method returns an array of the identifiers of the result:
  
  .. code:: java
   
	@Override
	public InterfaceIdentifier[] getHeaders() {
		    InterfaceIdentifier[] ret = new InterfaceIdentifier[2];
		    ret[0] = probaId;
		    ret[1] = simId;
		    return ret;
	
	}

  Two *getValueOf* methods allow to get the values associated to an identifier, either using the name of the identifier or directly
  the identifier object:
   
  .. code:: java

	@Override
	public Object getValueOf(String header) throws PlasmaExperimentException {
		if (header == probaId.getName())
			return probability;
		else if (header == simId.getName())
			return nbsimulations;
		else
			throw new PlasmaExperimentException("header " + header + " not found in MyResult.");
	}
	
	@Override
	public Object getValueOf(InterfaceIdentifier id) throws PlasmaExperimentException {
		if (id == probaId)
			return probability;
		else if (id == simId)
			return nbsimulations;
		else
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in MyResult.");
	}

  The last getters return the requirement, the probability and the number of simulations:
    
  .. code:: java
    
	@Override
	public AbstractRequirement getOriginRequirement() {
		return origin;
	}

	@Override
	public double getPr() {
		return probability;
	}

	@Override
	public int getTotalCount() {
		return nbsimulations;
	}
	
	