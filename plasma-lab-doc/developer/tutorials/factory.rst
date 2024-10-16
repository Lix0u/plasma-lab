Data factory
============

The first thing to add a new simulator or a new checker is to implement a *factory* interface.
The factory interfaces are as follows:

.. code:: java
  
  public interface AbstractDataFactory extends Plugin
  public abstract class AbstractModelFactory implements AbstractDataFactory
  public abstract class AbstractRequirementFactory implements AbstractDataFactory
  
-  *AbstractDataFactory*  creates a data with no semantics.
-  *AbstractModelFactory*  creates a new simulator.
-  *AbstractRequirementFactory* creates a new checker.

Each Factory implementation declares a new JSPF *Plugin*. It is tag with ``@PluginImplementation``
before the class declaration.

  We implement a new factory with one of the following declarations:

  .. code:: java
	
	@PluginImplementation
        public class MySimulatorFactory extends AbstractModelFactory    
        
  .. code:: java
	
	@PluginImplementation
        public class MyCheckerFactory extends AbstractRequirementFactory
        
     
Then we implement the functions of the AbstractDataFactory interface:

- The *name* of the factory appears in the GUI menus
- The *description* is a short text displayed by the GUI *About windows* and the CLI *info* command.
- The *id* is a unique identifier used to identified the data type and the factory in the CLI, in the saved projects and in the distributed services.

  .. code:: java

	@Override
	public String getName() {
		return "My plugin";
	}

	@Override
	public String getDescription() {
		return "This is a tutorial plugin";
	}

	@Override
	public String getId() {
		return "myplugin";
	}


The main purpose of the Factory interface is to provide a way of
instantiating a simulator or a checker without knowing their classes.
This is done by implementing the next two methods (inherited from their respective super class).

  .. code:: java

	  @Override
	  public AbstractModel createAbstractModel(String name) {
		  return new MySimulator(name, "", getId());
	  }

	  @Override
	  public AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException {
		  return new MySimulator(name, file, id);
	  }

	  @Override
	  public AbstractModel createAbstractModel(String name, String content) {
		  return new MySimulator(name, content, getId());
	  }
	  
	  
  .. code:: java

	  @Override
	  public AbstractRequirement createAbstractRequirement(String name) {
		  return new MyChecker(name, "", getId());
	  }

	  @Override
	  public AbstractRequirement createAbstractRequirement(String name, File file) throws PlasmaDataException {
		  return new MyChecker(name, file, getId());
	  }

	  @Override
	  public AbstractRequirement createAbstractRequirement(String name, String content) {
		  return new MyChecker(name, content, getId());
	  }


And that's all for the factory!
