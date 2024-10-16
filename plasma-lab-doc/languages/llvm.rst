LLVM
====

From version 1.4.3, PLASMA Lab includes a plugin that simulates LLVM code.
This plugin is a wrapper of an external simulator `Lodin <https://lodin.boegstedpoulsen.dk/>`__.
This tool is necessary to use the plugin.

To use the PLASMA Lab plugin we create a model of type LLVM and we write in the content of the model
the two following lines:

- The first line must contain the path to the Lodin simulator (the ``sim`` binary) folowed by the options to use.
- The second line must contain the path to the LLVM code to simulate (the ``.ll`` file compiled from C code).

Some Lodin options are mandatory to run with Plasma Lab:

- The ``-X`` option to use the alternative state ouput.
- The ``-C`` to run Lodin in a command based protocol.

Additionaly the option ``-S`` is mandatory to run Lodin with the importance splitting algorithm, as it allows to store the sates of the simulator in order to restart simulations.


