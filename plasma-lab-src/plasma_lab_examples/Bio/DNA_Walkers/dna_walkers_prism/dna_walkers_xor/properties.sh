#!/bin/bash

# generates Table 3 results (see README.txt)


PRISM="**YOUR*PATH*HERE***/prism/bin/prism"
BENCHPATH="**YOUR*PATH*HERE***/dna_walkers_benchmark"
PATHS=1000;	# Number of simulations run
ALPHA=0.05;	# Confidence level
DEPTH=50;	# Number of steps before simulation is terminated


	function generate {

 		echo "Starting $1"
		echo "$BENCHPATH/models/$2.pm"
		#select PRISM call: simulation or fast adaptive method

		#$PRISM $BENCHPATH/models/$2.pm $BENCHPATH/properties/$3.pctl -prop $1 -sim -simmethod ci -simsamples $PATHS -simconf $ALPHA -simpathlen $DEPTH -const failureRate=0.3 -exportresults $BENCHPATH/results/$2-$1.txt,matrix,csv;	#simulation
		
		$PRISM $BENCHPATH/models/$2.pm $BENCHPATH/properties/$3.pctl -prop $1 -transientmethod fau -faudelta 1e-8 -fauintervals 1 -epsilon 1e-6 -const failureRate=0.3 -exportresults $BENCHPATH/results/$2-$1.txt,matrix,csv;	#fast adaptive method 
	
	}

	function doCircuit {

	local NAME

	#XOR(X,Y)
	NAME="$1LL"
	generate 2 $NAME $2
	generate 4 $NAME $2
	generate 5 $NAME $2
	
	#XOR(notX,Y)
	NAME="$1RL"
	generate 3 $NAME $2
	generate 4 $NAME $2
	generate 5 $NAME $2
	
	#XOR(X,notY)
	NAME="$1LR"
	generate 3 $NAME $2
	generate 4 $NAME $2
	generate 5 $NAME $2
		
	#XOR(notX,notY)
	NAME="$1RR"
	generate 2 $NAME $2
	generate 4 $NAME $2
	generate 5 $NAME $2

	}
	
	## execution starts here
	
	doCircuit ring 	ring		## Normal circuit. 
	doCircuit ringS ring		## Single block circuit.
	doCircuit ringL ringL		## Large circuit.




	
