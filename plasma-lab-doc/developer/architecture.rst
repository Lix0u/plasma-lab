Architecture
============

One of the main differences between PLASMA Lab and other SMC tools is that
PLASMA Lab proposes an API abstraction of the concepts of stochastic model
simulator, property checker (monitoring) and SMC algorithm. In other words,
the tool has been designed to be capable of using external simulators or input
languages. This not only reduces the effort of integrating new algorithms, but
also allows us to create direct plug-in interfaces with standard modelling and
simulation tools used by industry. The latter being done without using extra
compilers

The figure below presents the tool architecture:
- The core of Plasma Lab is a lightweight **Controller** that manages the experiments and the distribution mechanism.
- It implements an API to control the experiments either through **user interfaces** or **external tools**.
- It loads three types of plugins: 1. **Algorithms**, 2. **Checkers**, and 3. **Simulators**. These plugins communicate with each other and with the controller through the API.

.. image:: ../images/architecture.png

--------------

MVC Pattern
-----------

PLASMA Lab can also be seen as implementing the Model-View-Controller
(MVC) pattern. Here, *View components* use the Controller API to
communicate with the *Controller component*. In turn, the *Controller*
controls the *Model components*.

In the following section we detail which PLASMA Lab project corresponds
to which type of components and what is their purpose.

View Components
~~~~~~~~~~~~~~~

-  **PLASMA Lab Graphical User Interface** -- *fr.inria.plasmalab.ui*

This is the GUI of PLASMA Lab, implemented with Swing. It incorporates all functionalities of PLASMA Lab and allows to open and edit PLASMA files (project files).
   
-  **PLASMA Lab Command Line** -- *fr.inria.plasmalab.terminal*

This view is a command line interface for PLASMA Lab. It provides experimentation and simulation functionalities, including the command line for PLASMA Lab service.

-  **PLASMA Lab Service** -- *fr.inria.plasmalab.service*

This is a small graphical interface for PLASMA Lab distributed service. It is deployed on a distant computer to help run a distributed experiment.
  
-  **PLASMA2Simulink** -- *fr.inria.plasmalab.matlab\_ui*

This is a small graphical "APP" running from Matlab to use PLASMA Lab SMC algorithms with a Simulink model.
  
--------------

Model Components
~~~~~~~~~~~~~~~~

The Model part contains three type of components. **Algorithm**,
**Simulator** (Model) and **Checker** (Requirement/Property). They all
are related by a simple workflow. An Algorithm asks a Checker to check a
property. The Checker will in turn ask the Simulator to run a simulation
and will use the trace returned to decide the property. The decision
will be returned to the Algorithm.

Checker and Simulator components are **plugins** that can be loaded on
startup. Algorithms were designed to be plugins too but this feature was
discarded. The plugin system use the Java Simple Plugin Framework (JSPF).

Here is a list of project for each of these categories.

**Algorithms**

- :doc:`Monte Carlo based algorithm <../algorithms/montecarlo>` -- *fr.inria.plasmalab.montecarlo*

  It includes algorithms for nondeterministic models.
     
- :doc:`Sequential Hypothesis Testing <../algorithms/sequential>` -- *fr.inria.plasmalab.sequential*

  It includes the sequential algorithm for nondeterministic models
    
- :doc:`Importance splitting algorithms <../algorithms/splitting>` -- *fr.inria.plasmalab.importancesplitting*
  
- :doc:`Importance sampling and the cross entropy algorithm <../algorithms/sampling>` -- *fr.inria.plasmalab.importancesampling*
  
- CUSUM -- *fr.inria.plasmalab.cusum*

**Checker**

- :doc:`Bounded Linear Temporal Logic <../languages/bltl>` -- *fr.inria.plasmalab.bltl*

- :doc:`Adaptive Linear Temporal Logic <../languages/altl>` -- *fr.inria.plasmalab.altl*

- :doc:`Global Contract Specification Language <../languages/gcsl>` -- *fr.inria.plasmalab.gcsl*
  
- :doc:`Nested BLTL <../languages/nested>` -- *fr.inria.plasmalab.nested*

**Simulator**

- `Reactive Module Language <http://www.prismmodelchecker.org/manual/ThePRISMLanguage/Introduction>`__ -- *fr.inria.plasmalab.rmlbis*

  The project also includes the simulator for :doc:`Adaptive RML <../languages/arml>` and the observers requirements used with importance splitting.
  
- :doc:`Biological language <../languages/bio>` -- *fr.inria.plasmalab.bio*
  
- MatLab interface -- *fr.inria.plasmalab.matlab*

- SystemC interface -- *fr.inria.plasmalab.systemc*

--------------

Controller Component
~~~~~~~~~~~~~~~~~~~~

The Controller component (project *fr.inria.plasmalab.controler*) acts as
an interface between the Models and the Views. The second purpose of the
Controller is to initialize PLASMA Lab with configuration files and load
plugins.

    Note: The project GUI has its own Controller object to deal with
    file management, interface callback, etc.

--------------

Common
~~~~~~

Various projects define classes and interfaces used by several
component. The main one is the Workflow (project
*fr.inria.plasmalab.workflow*) that contains definitions of several
interfaces and classes, as well as a set of PLASMA Lab Exceptions.

Here is a list of common project

-  **Workflow** -- *fr.inria.plasmalab.workflow*

  Interface and class definitions used by several components, in
  particular Data and Factory interfaces for Simulator and Checker
  components. Definitions of Plasma Lab exception classes.

-  **Algorithm** -- *fr.inria.plasmalab.algorithm*

  Defines Data and Factory interfaces for Algorithm components.

-  **Distributed** -- *fr.inria.plasmalab.distributed*

  Defines the Restlet architecture and components used in all distributed
  Algorithms as well as a Heartbeat mechanism to detect loss of connection
  from Workers.

- **GUI command line** -- *fr.inria.plasmalab.uiterminal*  

  It handles the launch and the configuration of the GUI from a terminal.

- **Common command line** -- *fr.inria.plasmalab.common-cli* 

  It provides common features between the two command line terminal projects.

- **Text data** -- *fr.inria.plasmalab.text_data*

  This loads data as plain text, without syntax. It is used in particular to load a project
  whose plugin is missing and incorrect.

- **Root** -- *fr.inria.plasmalab.root*

  This project contains no code but is the base project for compiling PLASMA Lab.

