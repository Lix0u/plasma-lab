Configuration
=============

PLASMA Lab GUI can be launch with the following options:

.. code::

	Usage: launch [options]
	  Options:
		--conf
	      Path to Plasma Lab configuration file.
	    -d, --debug
	      Debug level
	      Default: 0
		--help
	      Print help.
	      Default: false
		--log
	      Use a logback xml file

PLASMA Lab use a text configuration file to load plugins and set
parameters. If no configuration file was provided, a default one is
loaded from the jar resources.

Here is the list of configuration:

-  plugin ABSOLUTE\_PATH\_JAR

This option will tell PLASMA Lab to look for plugins in the jar file
located at ABSOLUTE\_PATH\_JAR and load them.

-  plugin\_dir ABSOLUTE\_PATH\_DIR

This option will tell PLASMA Lab to look for every plugins located in
the ABSOLUTE\_PATH\_DIR directory and load them.

-  working\_dir RELATIVE\_PATH\_DIR

This option will set RELATIVE\_PATH\_DIR as the working directory. The
working directory is the default directory when loading or saving files.

-  log4j RELATIVE\_PATH\_XML

This option tell PLASMA Lab to load the log4j configuration file located
at RELATIVE\_PATH\_XML. In the case where this option was not set, a
default log4j configuration file is loaded from the jar resources.
