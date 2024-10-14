# PLASMA Lab - The Statistical Model Checker Tool
# Version 1.4.5-SNAPSHOT

PLASMA Lab is a modular Statistical Model Checking tool written in Java. It provides several statistical model-checking algorithms and it can load plugins that run discrete events simulations on various models and check these simulations against linear temporal properties.

It includes a graphical user interface that allows to run simulations and experiments, as well as edit models and properties. It can also run algorithms directly from the command line. It finally include a small client interface (PLASMA Lab Service) that is used in conjunction with PLASMA Lab to run simulations in distributed mode.

Online documentation is available on
* PLASMA Lab website: https://project.inria.fr/plasma-lab/ -- Overview, Download and Case-sudies
* PLASMA Lab documentation website: http://plasma-lab.gforge.inria.fr/plasma_lab_doc/

## Copyright INRIA

Axel Legay (Team leader)
Louis-Marie Traonouez (Developer)
Jean Quilbeuf (Developer)

## Distribution
PLASMA Lab distribution should include the following content:
* `configs/logback.xml` -- configuration of PLASMA Lab's logs
* `configs/plasmalab.conf` -- example configuration file for specifying plugins and working directory
* `demos/` -- a selection of case-studies
* `libs/` -- PLASMA Lab main libraries and dependencies
* `plugins/` -- PLASMA Lab basic plugins for simulations and property checking
* `plasmacli.sh` -- PLASMA Lab command line (Unix script)
* `plasmacli.bat` -- PLASMA Lab command line (windows script)
* `plasmagui.sh` -- PLASMA Lab graphical interface (Unix script)
* `plasmagui.bat` -- PLASMA Lab graphical interface (windows script)
* `plasmaservice.bat` -- PLASMA Lab Service graphical interface (windows script)
* `README.md` -- this readme

## How to launch PLASMA Lab ?

1. Unzip this archive into a directory of your choice.
2. Start the GUI:
    `./plasmagui.sh launch -- (Unix)`
    `double click on plasmagui.bat -- (Windows)`
3. or use the command line interface:
    `./plasmacli.sh [command] [options] -- (Unix)`
    `./plasmacli.bat [command] [options] -- (Windows)`

For more information about the tool configuration and usage look at the [documentation website]([http://plasma-lab.gforge.inria.fr/plasma_lab_doc/).

