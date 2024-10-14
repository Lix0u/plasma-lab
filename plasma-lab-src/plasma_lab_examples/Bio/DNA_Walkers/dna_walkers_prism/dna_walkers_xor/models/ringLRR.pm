	ctmc
 
				const double failureRate;
 
 	module walker
 
		stator1		: [0 .. 1] init 1;
		stator2		: [0 .. 1] init 0;
		stator3		: [0 .. 1] init 0;
		stator4		: [0 .. 1] init 0;
		stator5		: [0 .. 1] init 0;
		stator6		: [0 .. 1] init 0;
		stator7		: [0 .. 1] init 0;
		stator8		: [0 .. 1] init 0;
		stator9		: [0 .. 1] init 0;
		stator10		: [0 .. 1] init 0;
		stator11		: [0 .. 1] init 0;
		stator12		: [0 .. 1] init 0;
		stator13		: [0 .. 1] init 1;
		stator14		: [0 .. 1] init 1;
		stator15		: [0 .. 1] init 1;
		stator16		: [0 .. 1] init 1;
		stator17		: [0 .. 1] init 0;
		stator18		: [0 .. 1] init 0;
		stator19		: [0 .. 1] init 0;
		stator20		: [0 .. 1] init 0;
		stator21		: [0 .. 1] init 0;
		stator22		: [0 .. 1] init 0;
		stator23		: [0 .. 1] init 0;
		stator24		: [0 .. 1] init 0;
		stator25		: [0 .. 1] init 0;
		stator26		: [0 .. 1] init 1;
		stator27		: [0 .. 1] init 1;
 
		w1 : [0 .. 27] init 1;	// w1=0 is sinkstat for deadlocks
 
 
		blockade15		: [0 .. 1] init 0;
		blockade16		: [0 .. 1] init 0;
		blockade13		: [0 .. 1] init 0;
		blockade14		: [0 .. 1] init 0;
		blockade26		: [0 .. 1] init 0;
		blockade27		: [0 .. 1] init 0;
 
		[block15]  blockade15=0 	->	1000000.0 * failureRate  : (blockade15'=1) & (stator15'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade15'=1);
		[block16]  blockade16=0 	->	1000000.0 * failureRate  : (blockade16'=1) & (stator16'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade16'=1);
		[block13]  blockade13=0 	->	1000000.0 * failureRate  : (blockade13'=1) & (stator13'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade13'=1);
		[block14]  blockade14=0 	->	1000000.0 * failureRate  : (blockade14'=1) & (stator14'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade14'=1);
		[block26]  blockade26=0 	->	1000000.0 * failureRate  : (blockade26'=1) & (stator26'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade26'=1);
		[block27]  blockade27=0 	->	1000000.0 * failureRate  : (blockade27'=1) & (stator27'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade27'=1);
 
 
 
		[step] w1=1 & stator2=0	-> 0.0029999999999999996 : (w1'=2) & (stator2'=1); 

		[step] w1=1 & stator3=0	-> 5.9999999999999995E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=1 & stator4=0	-> 2.9999999999999997E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=1 & stator5=0	-> 2.9999999999999997E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=1 & stator6=0	-> 2.9999999999999997E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=1 & stator7=0	-> 2.9999999999999997E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=1 & stator8=0	-> 2.9999999999999997E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=1 & stator9=0	-> 2.9999999999999997E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=1 & stator10=0	-> 2.9999999999999997E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=1 & stator12=0	-> 2.9999999999999997E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=1 & stator13=0	-> 2.9999999999999997E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=1 & stator14=0	-> 2.9999999999999997E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=1 & stator15=0	-> 5.9999999999999995E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=1 & stator16=0	-> 0.0029999999999999996 : (w1'=16) & (stator16'=1); 

		[step] w1=1 & stator17=0	-> 2.9999999999999997E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=1 & stator18=0	-> 2.9999999999999997E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=1 & stator19=0	-> 2.9999999999999997E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=1 & stator20=0	-> 2.9999999999999997E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=1 & stator21=0	-> 2.9999999999999997E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=1 & stator22=0	-> 2.9999999999999997E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=1 & stator23=0	-> 2.9999999999999997E-5 : (w1'=23) & (stator23'=1); 

		[step] w1=1 & stator25=0	-> 2.9999999999999997E-5 : (w1'=25) & (stator25'=1); 

		[step] w1=1 & stator26=0	-> 2.9999999999999997E-5 : (w1'=26) & (stator26'=1); 

		[step] w1=1 & stator27=0	-> 2.9999999999999997E-5 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=2 & stator1=0	-> 0.009 : (w1'=1) & (stator1'=1); 

		[step] w1=2 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=2 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=2 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=2 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=2 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=2 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=2 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=2 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=2 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=2 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=2 & stator17=0	-> 8.999999999999999E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=2 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=2 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=2 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1); 

		[step] w1=2 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1); 

		[step] w1=2 & stator25=0	-> 8.999999999999999E-5 : (w1'=25) & (stator25'=1); 

		[step] w1=2 & stator26=0	-> 8.999999999999999E-5 : (w1'=26) & (stator26'=1); 

		[step] w1=2 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=3 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=3 & stator2=0	-> 0.009 : (w1'=2) & (stator2'=1); 

		[step] w1=3 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1); 

		[step] w1=3 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=3 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=3 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=3 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=3 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=3 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=3 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1); 

		[step] w1=3 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1); 

		[step] w1=3 & stator25=0	-> 8.999999999999999E-5 : (w1'=25) & (stator25'=1); 

		[step] w1=3 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1); 

		[step] w1=3 & stator27=0	-> 0.009 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=4 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=4 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=4 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=4 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1); 

		[step] w1=4 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=4 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=4 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=4 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=4 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=4 & stator25=0	-> 8.999999999999999E-5 : (w1'=25) & (stator25'=1); 

		[step] w1=4 & stator26=0	-> 8.999999999999999E-5 : (w1'=26) & (stator26'=1); 

		[step] w1=4 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=5 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=5 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=5 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=5 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1); 

		[step] w1=5 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1); 

		[step] w1=5 & stator7=0	-> 1.7999999999999998E-4 : (w1'=7) & (stator7'=1); 

		[step] w1=5 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=5 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=5 & stator26=0	-> 8.999999999999999E-5 : (w1'=26) & (stator26'=1); 

		[step] w1=5 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=6 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=6 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=6 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=6 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=6 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1); 

		[step] w1=6 & stator7=0	-> 0.009 : (w1'=7) & (stator7'=1); 

		[step] w1=6 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=6 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=6 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=6 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=7 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=7 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=7 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=7 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=7 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=7 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1); 

		[step] w1=7 & stator8=0	-> 0.009 : (w1'=8) & (stator8'=1); 

		[step] w1=7 & stator9=0	-> 1.7999999999999997E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=7 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

 
 
		[step] w1=8 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=8 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=8 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=8 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=8 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=8 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=8 & stator7=0	-> 0.009 : (w1'=7) & (stator7'=1); 

		[step] w1=8 & stator9=0	-> 9.0E-4 : (w1'=9) & (stator9'=1); 

		[step] w1=8 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=8 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=8 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=8 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

 
 
		[step] w1=10 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=10 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=10 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=10 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=10 & stator9=0	-> 9.0E-4 : (w1'=9) & (stator9'=1); 

		[step] w1=10 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1); 

		[step] w1=10 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1); 

		[step] w1=10 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=10 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=10 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=10 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

 
 
		[step] w1=11 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=11 & stator9=0	-> 1.7999999999999997E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=11 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1); 

		[step] w1=11 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1); 

		[step] w1=11 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=11 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=11 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=11 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

 
 
		[step] w1=12 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=12 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=12 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=12 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=12 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1); 

		[step] w1=12 & stator13=0	-> 0.009 : (w1'=13) & (stator13'=1); 

		[step] w1=12 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=12 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=12 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=12 & stator17=0	-> 8.999999999999999E-5 : (w1'=17) & (stator17'=1); 

 
 
		[step] w1=13 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=13 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=13 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=13 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=13 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1); 

		[step] w1=13 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1); 

		[step] w1=13 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1); 

		[step] w1=13 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=13 & stator17=0	-> 8.999999999999999E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=13 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

 
 
		[step] w1=14 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=14 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=14 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=14 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=14 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=14 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1); 

		[step] w1=14 & stator13=0	-> 0.009 : (w1'=13) & (stator13'=1); 

		[step] w1=14 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1); 

		[step] w1=14 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=14 & stator17=0	-> 1.7999999999999998E-4 : (w1'=17) & (stator17'=1); 

		[step] w1=14 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=14 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

 
 
		[step] w1=15 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=15 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=15 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=15 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=15 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=15 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=15 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=15 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1); 

		[step] w1=15 & stator16=0	-> 0.009 : (w1'=16) & (stator16'=1); 

		[step] w1=15 & stator17=0	-> 0.009 : (w1'=17) & (stator17'=1); 

		[step] w1=15 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=15 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1); 

		[step] w1=15 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=15 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=16 & stator1=0	-> 0.009 : (w1'=1) & (stator1'=1); 

		[step] w1=16 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=16 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=16 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=16 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=16 & stator9=0	-> 8.999999999999999E-6 : (w1'=9) & (stator9'=1); 

		[step] w1=16 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=16 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=16 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=16 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=16 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=16 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1); 

		[step] w1=16 & stator17=0	-> 1.7999999999999998E-4 : (w1'=17) & (stator17'=1); 

		[step] w1=16 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=16 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=16 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=16 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=16 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=16 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=17 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=17 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=17 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=17 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=17 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=17 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1); 

		[step] w1=17 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=17 & stator18=0	-> 0.009 : (w1'=18) & (stator18'=1); 

		[step] w1=17 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1); 

		[step] w1=17 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=17 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=17 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

 
 
		[step] w1=18 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=18 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=18 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=18 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1); 

		[step] w1=18 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=18 & stator17=0	-> 0.009 : (w1'=17) & (stator17'=1); 

		[step] w1=18 & stator19=0	-> 0.009 : (w1'=19) & (stator19'=1); 

		[step] w1=18 & stator20=0	-> 1.7999999999999998E-4 : (w1'=20) & (stator20'=1); 

		[step] w1=18 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=18 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

 
 
		[step] w1=19 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=19 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=19 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1); 

		[step] w1=19 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=19 & stator17=0	-> 1.7999999999999998E-4 : (w1'=17) & (stator17'=1); 

		[step] w1=19 & stator18=0	-> 0.009 : (w1'=18) & (stator18'=1); 

		[step] w1=19 & stator20=0	-> 0.009 : (w1'=20) & (stator20'=1); 

		[step] w1=19 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

		[step] w1=19 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=19 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1); 

 
 
		[step] w1=20 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=20 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=20 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=20 & stator17=0	-> 8.999999999999999E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=20 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=20 & stator19=0	-> 0.009 : (w1'=19) & (stator19'=1); 

		[step] w1=20 & stator21=0	-> 0.009 : (w1'=21) & (stator21'=1); 

		[step] w1=20 & stator22=0	-> 1.7999999999999997E-5 : (w1'=22) & (stator22'=1); 

		[step] w1=20 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1); 

 
 
		[step] w1=21 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=21 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=21 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=21 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=21 & stator17=0	-> 8.999999999999999E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=21 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=21 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1); 

		[step] w1=21 & stator20=0	-> 0.009 : (w1'=20) & (stator20'=1); 

		[step] w1=21 & stator22=0	-> 9.0E-4 : (w1'=22) & (stator22'=1); 

		[step] w1=21 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1); 

		[step] w1=21 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1); 

		[step] w1=21 & stator25=0	-> 8.999999999999999E-5 : (w1'=25) & (stator25'=1); 

 
 
		[step] w1=23 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=23 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=23 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=23 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=23 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=23 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

		[step] w1=23 & stator22=0	-> 9.0E-4 : (w1'=22) & (stator22'=1); 

		[step] w1=23 & stator24=0	-> 0.009 : (w1'=24) & (stator24'=1); 

		[step] w1=23 & stator25=0	-> 1.7999999999999998E-4 : (w1'=25) & (stator25'=1); 

		[step] w1=23 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1); 

		[step] w1=23 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=24 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=24 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=24 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=24 & stator22=0	-> 1.7999999999999997E-5 : (w1'=22) & (stator22'=1); 

		[step] w1=24 & stator23=0	-> 0.009 : (w1'=23) & (stator23'=1); 

		[step] w1=24 & stator25=0	-> 0.009 : (w1'=25) & (stator25'=1); 

		[step] w1=24 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1); 

		[step] w1=24 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=25 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=25 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=25 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=25 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=25 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

		[step] w1=25 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=25 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1); 

		[step] w1=25 & stator24=0	-> 0.009 : (w1'=24) & (stator24'=1); 

		[step] w1=25 & stator26=0	-> 0.009 : (w1'=26) & (stator26'=1); 

		[step] w1=25 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=26 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=26 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=26 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=26 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=26 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=26 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=26 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1); 

		[step] w1=26 & stator24=0	-> 1.7999999999999998E-4 : (w1'=24) & (stator24'=1); 

		[step] w1=26 & stator25=0	-> 0.009 : (w1'=25) & (stator25'=1); 

		[step] w1=26 & stator27=0	-> 0.009 : (w1'=27) & (stator27'=1); 

 
 
		[step] w1=27 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=27 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=27 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=27 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=27 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=27 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=27 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=27 & stator22=0	-> 8.999999999999999E-6 : (w1'=22) & (stator22'=1); 

		[step] w1=27 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1); 

		[step] w1=27 & stator24=0	-> 1.7999999999999998E-4 : (w1'=24) & (stator24'=1); 

		[step] w1=27 & stator25=0	-> 1.7999999999999998E-4 : (w1'=25) & (stator25'=1); 

		[step] w1=27 & stator26=0	-> 0.009 : (w1'=26) & (stator26'=1); 

		[]	w1 = 1	->	.000000001	: 	(w1'=1);
		[]	w1 = 2	->	.000000001	: 	(w1'=2);
		[]	w1 = 3	->	.000000001	: 	(w1'=3);
		[]	w1 = 4	->	.000000001	: 	(w1'=4);
		[]	w1 = 5	->	.000000001	: 	(w1'=5);
		[]	w1 = 6	->	.000000001	: 	(w1'=6);
		[]	w1 = 7	->	.000000001	: 	(w1'=7);
		[]	w1 = 8	->	.000000001	: 	(w1'=8);
		[]	w1 = 9	->	.000000001	: 	(w1'=9);
		[]	w1 = 10	->	.000000001	: 	(w1'=10);
		[]	w1 = 11	->	.000000001	: 	(w1'=11);
		[]	w1 = 12	->	.000000001	: 	(w1'=12);
		[]	w1 = 13	->	.000000001	: 	(w1'=13);
		[]	w1 = 14	->	.000000001	: 	(w1'=14);
		[]	w1 = 15	->	.000000001	: 	(w1'=15);
		[]	w1 = 16	->	.000000001	: 	(w1'=16);
		[]	w1 = 17	->	.000000001	: 	(w1'=17);
		[]	w1 = 18	->	.000000001	: 	(w1'=18);
		[]	w1 = 19	->	.000000001	: 	(w1'=19);
		[]	w1 = 20	->	.000000001	: 	(w1'=20);
		[]	w1 = 21	->	.000000001	: 	(w1'=21);
		[]	w1 = 22	->	.000000001	: 	(w1'=22);
		[]	w1 = 23	->	.000000001	: 	(w1'=23);
		[]	w1 = 24	->	.000000001	: 	(w1'=24);
		[]	w1 = 25	->	.000000001	: 	(w1'=25);
		[]	w1 = 26	->	.000000001	: 	(w1'=26);
		[]	w1 = 27	->	.000000001	: 	(w1'=27);
	endmodule
 
	rewards "steps"
 
		[step] true : 1;
 
	endrewards
 
	rewards "time"
 
		 true : 1;
 
	endrewards
 
	rewards "blocked" // time spend in blocked anchorages
 
		w1 =15 | w1 =16 | w1 =13 | w1 =14 | w1 =26 | w1 =27	:	1;
 
	endrewards
