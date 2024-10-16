Checking unbounded A-BLTL properties
====================================

PLASMA Lab can check unbounded properties from the A-BLTL logic. The algorithm
that is used involved a reachability check of the underlying finite automata model.
To do this PLASMA Lab calls the PRISM model-checker. We explain below how to configure
PLASMA Lab in order to use this functionality:

- Step 1: install PRISM in your system.
- Step 2: download the script `prism_reach <http://plasma-lab.gforge.inria.fr/plasma_lab_bundle/prism_reach>`__.
- Step 3: in the script edit the line with the path to PRISM
- Step 4: launch PLASMA Lab with the option ``-Dprism_reach=path`` where *path* is the path to the script. 

