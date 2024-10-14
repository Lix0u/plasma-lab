#!/bin/bash

#LOCALPATH="/mnt/ECFCC295FCC25A0A/workspace/trunk/"
LOCALPATH="YOUR_PRISM_PATH_HERE"

	function generate {

 		echo "Welcome $NAME"
		$LOCALPATH/prism/bin/prism models/$NAME.pm properties/$NAME.pctl -sim -simmethod ci -simsamples 100000 -simconf 0.05 -simpathlen 50 -exportresults results/$NAME.txt,matrix,csv;
	
	}


	NAME="track28LLDouble";
	generate

	NAME="track28LRDouble";
	generate

	NAME="track28RLDouble";
	generate

	NAME="track28RRDouble";
	generate

