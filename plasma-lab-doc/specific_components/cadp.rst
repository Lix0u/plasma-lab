CADP
====

CADP ("Construction and Analysis of Distributed Processes", formerly
known as "CAESAR/ALDEBARAN Development Package") is a toolbox for the
design of asynchronous concurrent systems. Developed by the CONVECS
(INRIA/LIG) team. CADP offers functionalities ranging from step-by-step
simulation to massively parallel model-checking. Its includes several
high-level formalism among which are LOTOS (ISO International Standard
8807) and LNT.

CADP is designed in a modular way and puts the emphasis on intermediate
formats and programming interfaces (such as the BCG and OPEN/CAESAR
software environments), which allow the CADP tools to be combined with
other tools and adapted to various specification languages.

The PLASMA Lab/CADP integration aims at bringing PLASMA Lab SMC library
to the CADP toolbox and support of the LOTOS/LNT languages in PLASMA
Lab.

Interfacing with CADP
---------------------

Concept
^^^^^^^

Included in the CADP toolbox is CAESAR, a compiler that translates the
behavioural part of a LOTOS specification into either a C program (to be
executed or simulated) or into an LTS (to be verified using bisimulation
tools and/or temporal logic evaluators). The LTS can be represented in
the BCG (Binary-Coded Graphs). Compared to ASCII-based formats for LTSs,
the BCG format uses a binary representation with compression techniques
resulting in much smaller (up to 20 times) files. BCG is independent
from any source language but keeps track of the objects (types,
functions, variables) defined in the source programs. The CADP toolbox
contains several tool for BCG manipulation.

To interface PLASMA Lab with CADP, we took the idea of a proxy object to
interface two software from the MATLAB Control API (used in
PLASMA2Simulink). This proxy would interface a BCG exploration tool,
base on executor, with PLASMA Lab.

Realization
^^^^^^^^^^^

The components is implemented in two projects.
**fr.inria.plasmalab.caesar** is a PLASMA Lab plugin written in
scala/java. As well as the expected AbstractModel implementation, its
task is to configure and launch the CADP instance, connect to it and
control it. **fr.inria.plasmalab.caesarinterface** is a C project.
Derived from the executor tool of the CADP toolbox, it implements a TCP
server which receives commands from the *fr.inria.plasmalab.caesar*
client and returns traces. These commands are then used to control the
BCG graph exploration.

The *caesarinterface* project is compiled as a static library and passed
as a parameter to CADP . This call is made by the PLASMA Lab plugin to
start the CADP/Server before creating the client.

.. code:: bash

        Process(bcg_open+" "+BCG_FILE+" libplasmacaesar.a "+MAX_PATH_LENGTH+" "+STRATEGY).run

PLASMA Lab was built with a state based approach in mind, and use only
numerical values as internal representation. On contrary, LOTOS/LNT is
an action based language and does not fit with the numerical value
representation. To accommodate this, the plugin map action label to a
unique numerical values while a state contains a single value
corresponding to the action label. Likewise, external identifiers (from
properties) can be evaluated to a numerical value using the map and
compared to a state value. It is then possible to test property such as:

.. code:: bltl

        F<=#100 FAIL

Remaining work
^^^^^^^^^^^^^^

Although a prototype is showing promising results, important task are
still to be done. Here is a list of the most important issues:

-  Executor does not simulate time although others CADP tool do.
-  Executor does not use rate to resolve non-determinism.

Both issues are linked to time simulation. They would be solved by
working on the *caesarinterface* code to solve non-determinism.

-  TCP Client-Server stability.

How to use it
^^^^^^^^^^^^^
