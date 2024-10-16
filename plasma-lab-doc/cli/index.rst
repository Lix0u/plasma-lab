Command Line Interface
========================


This chapter describes how to use the Command Line Interface (CLI) of Plasma Lab.


Starts the CLI : 

.. code::

    # \*nix - from the command line
    ./plasmacli.sh

    # windows - from the command line
    plasmacli.bat

Several sub-commands are available and will be explained in the following (shown with the :code:`plasmacli.sh` script, but 
working equivalently with :code:`plasmacli.bat`). They are specified on the command line using :code:`./plasmacli.sh subcommand`.

You can always invoke the help for a subcommand using : 

.. code::

    ./plasmacli.sh subcommand --help

    
Info
----

:code:`./plasmacli.sh info [options]` provides information option about PLASMA Lab's plugins
and algorithms.

- :code:`./plasmacli.sh info` displays the list of plugins (algorithms, models, requirements) that is currently loaded by PLASMA Lab 
(with the plugin list defined in the configuration file).
For each plugin it gives the ID of the plugin, that should be used when running the CLI to identify the plugin, and a short description.
    
- :code:`./plasmacli.sh info -a [algorithm id]` displays information about an algorithm, in particular the list of parameters.    


Launch
------

:code:`./plasmacli.sh launch [options]` is used to start a new experimentation. You'll
have to pass all the parameters required to perform the experimentation
(requirement with option :code:`-r`, model with option :code:`-m`, algorithm with option :code:`-a` and its parameters with options :code:`-A`). 

Models and requirements files must always be written with their type using the following syntax: 
:code:`file:type`.

Example : 

.. code::

    ./plasmacli launch -m demos/raw/philo3.rml:rml -r demos/raw/liveness:bltl -a montecarlo -A"Total samples"=10000 --progress


In this case you should observe the following output 

.. code::
    
    [...]
    [some traces]
    [...]
    [==================================================]   100%
    +----------+---------------+-----------------------+--------+
    | Name     | # Simulations | # Positive Simulation | Result |
    +----------+---------------+-----------------------+--------+
    | liveness | 10000         | 10000                 | 1.0    |
    +----------+---------------+-----------------------+--------+

Simu
----

:code:`./plasmacli.sh simu [options]` is used to start an interactive simulation. A necessary option is the model to simulate.

- Pressing :code:`Enter` will step the simulation by 1 step (initial default value)

- Entering a number :code:`n` will step the simulation by :code:`n` steps. Then :code:`n` will be the new default value for the step number.

- Entering :code:`r` restart the simulation at the initial step.

- Entering a negative number allows to backtrack the simulation.

- Entering :code:`q` will quit the simulation.

Service
-------

:code:`./plasmacli.sh service [options]` is used to start a new service listening on the default port.


