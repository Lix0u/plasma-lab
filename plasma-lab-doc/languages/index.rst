Languages
=========

PLASMA Lab uses two types of data, models and requirements.
On these pages you will find details on these languages.
You could also take at look at our collection of `examples <https://project.inria.fr/plasma-lab/examples/>`__.

Models
^^^^^^

In its current version, PLASMA Lab is bundled with three model languages:

- Reactive Module Language (RML) of the PRISM tool, a popular probabilistic model checker tool supporting a wide range of models. More documentation on this language can be found on PRISM `website <http://www.prismmodelchecker.org/manual/ThePRISMLanguage/Introduction>`__.

- :doc:`Adaptive Reactive Module Language <arml>` is an extension of RML for adaptive systems.
- :doc:`Biological Language <bio>`.

Additionally, three plugins allow to interface PLASMA Lab with external tools:

- :doc:`MATLAB/Simulink  <plasma2simulink>`
- :doc:`SystemC/MAG <systemc>`
- :doc:`LLVM <llvm>`

As we are using a modular architecture, you can also develop your own model language and integrate it into Plasma Lab.

.. toctree::
   :hidden:

   arml
   bio
   plasma2simulink
   systemc
   llvm

Requirements
^^^^^^^^^^^^

Requirements languages also are modular. Plasma Lab comes with five:

- :doc:`Bounded-LTL (BLTL) <bltl>`.
- :doc:`Adaptive BLTL (ALTL) <altl>`.
- :doc:`Goal Contract Specification Language (GCSL) <gcsl>`.
- :doc:`Nested BLTL <nested>`.
- :doc:`Observers <../algorithms/splitting>`

.. toctree::
   :hidden:

   bltl
   altl
   gcsl
   nested