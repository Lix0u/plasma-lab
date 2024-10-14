#!/bin/bash

#LOCALPATH="/mnt/ECFCC295FCC25A0A/workspace/trunk/"
LOCALPATH="YOUR_PRISM_PATH_HERE"

	function generate {

 		echo "Welcome $NAME"
		$LOCALPATH/prism/bin/prism models/$NAME.pm properties/$NAME.pctl -transientmethod unif  -exportresults results/$NAME.txt,matrix,csv 
	
	}



	NAME="control";
	generate

	NAME="controlMissing1";
	generate

	NAME="controlMissing2";
	generate

	NAME="controlMissing7";
	generate
