#!/bin/bash

#LOCALPATH="/mnt/ECFCC295FCC25A0A/workspace/trunk/"
LOCALPATH="YOUR_PRISM_PATH_HERE"

	function generate {

 		echo "Welcome $NAME"
		$LOCALPATH/prism/bin/prism models/$NAME.pm properties/$NAME.pctl -transientmethod fau -faudelta 1E-10 -fauepsilon 1E-8 -exportresults results/$NAME.txt,matrix,csv 
	
	}



	NAME="track12Block1";
	generate

	NAME="track12Block2";
	generate

	NAME="track12BlockBoth";
	generate

