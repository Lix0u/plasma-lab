Options
=======

The configuration file

**Path to a plugin**

::

    plugin file://ABSOLUTE_PATH_TO_PLUGIN_JAR_FILE.jar

**Path to a plugin directory**

::

    plugin_dir RELATIVE_PATH_TO_PLUGIN_DIRECTORY

**Path to a plugin URL**

::

    plugin http://URL_TO_PLUGIN_JAR_FILE.jar

**Path to working directory**

::

    working_dir RELATIVE_PATH_TO_WORKING_DIR

**Path to log4j configuration file**

::

    log4j RELATIVE_PATH_TO_LOG4J_XML_CONFIGURATION_FILE.xml

**Comments**

::

    # Any comment

| plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.matlab/1.2.19-SNAPSHOT/fr.inria.plasmalab.matlab-1.2.19-SNAPSHOT.jar
| //plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.bio/1.2.19-SNAPSHOT/fr.inria.plasmalab.bio-1.2.19-SNAPSHOT.jar
| //plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.bioscala/1.2.19-SNAPSHOT/fr.inria.plasmalab.bioscala-1.2.19-SNAPSHOT.jar
| plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.plugin_bltl/1.2.19-SNAPSHOT/fr.inria.plasmalab.plugin_bltl-1.2.19-SNAPSHOT.jar
| plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.caesar/1.2.19-SNAPSHOT/fr.inria.plasmalab.caesar-1.2.19-SNAPSHOT.jar
| plugin
  file:///home/kcorre/.m2/repository/fr/inria/plasmalab/fr.inria.plasmalab.dummy/1.2.19-SNAPSHOT/fr.inria.plasmalab.dummy-1.2.19-SNAPSHOT.jar

working\_dir ../plasma\_lab\_examples

log4j ../../perso\_log4j.xml
