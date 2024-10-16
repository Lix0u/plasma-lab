Adaptive Reactive Module Language
=================================

We have extended RML with structures that model adaptive systems.
We detail below the modifications and new syntax added to RML.

Modules
^^^^^^^

Modules can now be parameterized using the following construction::

  module name ( parameter1, parameter2, parameter3, ...)
    // declarations
    ...
  endmodule

The parameters syntax is ``parameter ::= type name``, and they can be of the following types:

- ``const int``, ``const double``, ``const bool`` for constant parameters respectively integers, double and Boolean.
- ``int``, ``double``, ``bool`` for variable parameters respectively integers, double and Boolean. Variable parameters are also local variables. Note that therefore they must be renamed by the renaming operation.

Parameterized modules define templates that can be instantiated using the following command::

  module new_name = name ( expression1, expression2, expression3, ...) endmodule

This creates a new module *new_name* that instantiates a parameterized module of type *name* such that each parameter is instantiated by the value of the corresponding expression. In case a parameter is variable, this instantiation corresponds to fixing the initial value of the variable. The new module is a copy of the parameterized module such that all the variables (and parameters) in the new module are renamed, their new name being their old name prefixed by *new_name.*. Note that parameterized modules can also be renamed using the classic renaming command from RML. This operation creates a new parameterized module.

Systems
^^^^^^^

The system construction from RML is extended in Adaptive RML. Several systems can now be designed. 
Each of them defines a different configuration of the adaptive system. A system can also be parameterized. ::
  
  system name ( parameter1, parameter2, parameter3, ...)
    // declarations
    ...
  endsystem

The syntax of the parameters is the same as for modules. However, only constant parameters can be used. The declarations in a system consist in a set of module instantiations.
Each of them uses the syntax previously presented. Contrary to RML in which the system always consists in all the modules of the model, in Adaptive RML each system may contain a different set of modules. Note that non parameterized modules are also added to a system using the same syntax, only in that case the expressions list is empty. Additionally, a declaration in a system can be a formula or a label, i.e. an expressions over the variables of the system, using the same declaration command from RML.

Adaptive Systems
^^^^^^^^^^^^^^^^

Finally, the adaptive system is declared as a set of adaptive command, i.e. meta-transition from one system to another. The syntax is the following::

  adaptive
    init at start_system;
    // adaptive_commands
    ...
  endsystem

*start_system* is a system instantiation: it consists in a system name and a list of expressions to instantiate the parameters of the system.
Adaptive commands are written in the following syntax::
  
  { system ( |  guard  )? } -> ( probability : {new_system} )+;

*system* specifies the current system in which the command is enabled. Additionally a guard can be specified, that is an expression over the variables of the system. Then comes a list of actions, each of them composed by a probability and the definition of the new system. The probabilities are expressions over the variables of the current system. The sum of the values of the probabilities must be lower than one. *new_system* is a system instantiation that defines the next configuration reached by the adaptive system.
