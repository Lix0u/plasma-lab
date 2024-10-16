Developper Quickstart
=====================

We describle here some instructions for developpers at Inria to build and start developping with plasma.

Prerequisite
------------

We have developpers working with GNU/Linux and others working with Mac OS.

You'll need the following:
 * Maven
 * An IDE (we will use Eclipse)

Add Maven to Eclipse. (Note: Maven is sometimes already installed in Eclipse.) 
From Eclipse, either:
 * Help -> Eclipse MarketPlace and search for Maven, or
 * Install New Software and choose m2e releases repository. Choose Maven. 

Getting the Code
----------------

First configure the forge 
* Create an account on `gforge <https://gforge.inria.fr>`
* Configure SSH  (add your ssh key to the forge)
* (optional) Configure Eclipse to work with Forge. 
  
You can find more details in `A Gentle Introduction to the Inria's Forge
 <http://siteadmin.gforge.inria.fr/gforge.html>`.


    Join PlasmaLab on Forge and follow the steps from the SMC tab to git clone the project. 
    Open the project from Eclipse, by selecting all packages except for the fr.inria.plasmalab.matlab_ui. Once the project is loaded, right click on the matlab package to close it and remove it from the build. (Note:errors that appear at this step are due to Eclipse trying to build the matlab package). 
    Run PlasmaLab from Eclipse. Select fr.inria.plasmalab.uiterminal -> src/java/... -> PlasmaLabTerminalUI.java. Run as "Java application" with "launch" as argument.  

