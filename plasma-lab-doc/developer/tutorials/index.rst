Plugins development tutorial
============================

This tutorial explains how to develop new plugins for PLASMA Lab (algorithm, checker or simulator). The sources of this tutorial can be downloaded below:

- `sources.zip <http://plasma-lab.gforge.inria.fr/plasma_lab_tutorial/sources.zip>`__ are the sources of the new plugins.
- `myalgorithm.jar <http://plasma-lab.gforge.inria.fr/plasma_lab_tutorial/myalgorithm.jar>`__ is the new algorithm plugin produced by the tutorial.
- `mysimulator.jar <http://plasma-lab.gforge.inria.fr/plasma_lab_tutorial/mysimulator.jar>`__ is the simulator plugin produced by the tutorial.
- `mychecker.jar <http://plasma-lab.gforge.inria.fr/plasma_lab_tutorial/mychecker.jar>`__ is the checker plugin produced by the tutorial.
- `testmysim.plasma <http://plasma-lab.gforge.inria.fr/plasma_lab_tutorial/testmysim.plasma>`__ is a PLASMA project to test the new plugins.

The tutorial projects require the *fr.inria.plasmalab.algorithm* and *fr.inria.plasmalab.workflow* libraries of Plasma Lab,
as well as the *jspf.core* library for defining plugins, and the *slf4j-api* library for logging. 

The outline of this section if the following:

.. toctree::
  :maxdepth: 2
  
  algorithm
  factory
  simulator
  checker

