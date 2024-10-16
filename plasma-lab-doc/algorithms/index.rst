Algorithms
==========

This section presents PLASMA SMC algorithms. The following algorithms
are currently implemented. The *ID* is used to reference the algorithm
in particular when using the command line. The *distributed*
algorithms implements a separate server and client to distribute simulations.

=======================================      ================  ===========
Name                                         ID                Distributed
=======================================      ================  ===========
:doc:`Monte Carlo <montecarlo>`              montecarlo        Yes
:doc:`Sequential <sequential>`               sequential        Yes
:doc:`Comparison <sequential>`               comparison        No
:doc:`Smart sampling <mdp>`                  smartsampling     Yes
:doc:`Seq. smart sampling <mdp>`             seqsmartsampling  No
:doc:`Importance splitting <splitting>`      splitting         Yes
:doc:`Cross entropy <sampling>`              crossentropy      Yes
CUSUM                                        cusum             No
=======================================      ================  ===========


.. toctree::
   :maxdepth: 2
   
   montecarlo
   sequential
   mdp
   rewards
   splitting
   sampling
   unbounded