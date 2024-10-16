Plugins
=======

There are three types of plugins, Simulator, Checker and Algorithms.
A plugin is build around two main classes: a Factory, and a
Model/Requirement/Scheduler class.

The *Factory* can be seen as the plugin entry point. It implements
methods to retrieve the plugin's name and description. But its main task
is to instantiate the class that is implementing the Simulator, the Checker
or the Algorithm interface.

Models and requirements factories inherit from the *AbstractModelFactory* or
*AbstractRequirementFactory* classes, that both implement the *AbstractDataFactory* interface.
Algorithms factories inherit the *InterfaceAlgorithmFactory*.

PLASMA Lab plugin system use the **Java Simple Plugin Framework**. To
load a plugin, the factory class must implement the *Plugin* interface and have
the tag *@PluginImplementation* before the class declaration.
More details and a 5 min tutorial can be found on their website:
    `<http://code.google.com/p/jspf/>`__
