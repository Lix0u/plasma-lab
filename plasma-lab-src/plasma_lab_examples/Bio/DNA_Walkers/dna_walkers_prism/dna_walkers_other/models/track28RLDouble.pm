	ctmc
 
				const double failureRate=0.3;
 
 	module walker
 
		stator1		: [0 .. 1] init 1;
		stator2		: [0 .. 1] init 0;
		stator3		: [0 .. 1] init 0;
		stator4		: [0 .. 1] init 0;
		stator5		: [0 .. 1] init 0;
		stator6		: [0 .. 1] init 0;
		stator7		: [0 .. 1] init 0;
		stator8		: [0 .. 1] init 0;
		stator9		: [0 .. 1] init 1;
		stator10		: [0 .. 1] init 1;
		stator11		: [0 .. 1] init 0;
		stator12		: [0 .. 1] init 0;
		stator13		: [0 .. 1] init 0;
		stator14		: [0 .. 1] init 0;
		stator15		: [0 .. 1] init 1;
		stator16		: [0 .. 1] init 1;
		stator17		: [0 .. 1] init 0;
		stator18		: [0 .. 1] init 0;
		stator19		: [0 .. 1] init 0;
		stator20		: [0 .. 1] init 0;
		stator21		: [0 .. 1] init 0;
		stator22		: [0 .. 1] init 0;
		stator23		: [0 .. 1] init 1;
		stator24		: [0 .. 1] init 1;
		stator25		: [0 .. 1] init 0;
		stator26		: [0 .. 1] init 0;
		stator27		: [0 .. 1] init 0;
		stator28		: [0 .. 1] init 0;
 
		w1 : [0 .. 28] init 1;	// w1=0 is sinkstat for deadlocks
 
 
		blockade9		: [0 .. 1] init 0;
		blockade10		: [0 .. 1] init 0;
		blockade15		: [0 .. 1] init 0;
		blockade16		: [0 .. 1] init 0;
		blockade23		: [0 .. 1] init 0;
		blockade24		: [0 .. 1] init 0;
 
		[block9]  blockade9=0 	->	1000000.0 * failureRate  : (blockade9'=1) & (stator9'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade9'=1);
		[block10]  blockade10=0 	->	1000000.0 * failureRate  : (blockade10'=1) & (stator10'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade10'=1);
		[block15]  blockade15=0 	->	1000000.0 * failureRate  : (blockade15'=1) & (stator15'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade15'=1);
		[block16]  blockade16=0 	->	1000000.0 * failureRate  : (blockade16'=1) & (stator16'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade16'=1);
		[block23]  blockade23=0 	->	1000000.0 * failureRate  : (blockade23'=1) & (stator23'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade23'=1);
		[block24]  blockade24=0 	->	1000000.0 * failureRate  : (blockade24'=1) & (stator24'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade24'=1);
 
 
		[step] w1=1 & stator2=0	-> 0.0029999999999999996 : (w1'=2) & (stator2'=1);
		[step] w1=1 & stator3=0	-> 5.9999999999999995E-5 : (w1'=3) & (stator3'=1);
		[step] w1=1 & stator4=0	-> 2.9999999999999997E-5 : (w1'=4) & (stator4'=1);
		[step] w1=1 & stator5=0	-> 2.9999999999999997E-5 : (w1'=5) & (stator5'=1);
		[step] w1=1 & stator9=0	-> 2.9999999999999997E-5 : (w1'=9) & (stator9'=1);
 
		[step] w1=2 & stator1=0	-> 0.009 : (w1'=1) & (stator1'=1);
		[step] w1=2 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1);
		[step] w1=2 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1);
		[step] w1=2 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=2 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=2 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=2 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
 
		[step] w1=3 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1);
		[step] w1=3 & stator2=0	-> 0.009 : (w1'=2) & (stator2'=1);
		[step] w1=3 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1);
		[step] w1=3 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=3 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=3 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=3 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=3 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=3 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
 
		[step] w1=4 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1);
		[step] w1=4 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1);
		[step] w1=4 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1);
		[step] w1=4 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1);
		[step] w1=4 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1);
		[step] w1=4 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=4 & stator9=0	-> 0.009 : (w1'=9) & (stator9'=1);
		[step] w1=4 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1);
		[step] w1=4 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
 
		[step] w1=5 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1);
		[step] w1=5 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1);
		[step] w1=5 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1);
		[step] w1=5 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1);
		[step] w1=5 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1);
		[step] w1=5 & stator7=0	-> 1.7999999999999998E-4 : (w1'=7) & (stator7'=1);
		[step] w1=5 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1);
		[step] w1=5 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=5 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=5 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
		[step] w1=5 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1);
 
		[step] w1=6 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1);
		[step] w1=6 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=6 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1);
		[step] w1=6 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1);
		[step] w1=6 & stator7=0	-> 0.009 : (w1'=7) & (stator7'=1);
		[step] w1=6 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1);
		[step] w1=6 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
		[step] w1=6 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=6 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1);
		[step] w1=6 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1);
		[step] w1=6 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1);
		[step] w1=6 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
 
		[step] w1=7 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=7 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1);
		[step] w1=7 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=7 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1);
		[step] w1=7 & stator8=0	-> 0.009 : (w1'=8) & (stator8'=1);
		[step] w1=7 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
		[step] w1=7 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1);
		[step] w1=7 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1);
		[step] w1=7 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1);
		[step] w1=7 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1);
		[step] w1=7 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
 
		[step] w1=8 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=8 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1);
		[step] w1=8 & stator7=0	-> 0.009 : (w1'=7) & (stator7'=1);
		[step] w1=8 & stator13=0	-> 0.009 : (w1'=13) & (stator13'=1);
		[step] w1=8 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1);
		[step] w1=8 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1);
		[step] w1=8 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1);
		[step] w1=8 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1);
		[step] w1=8 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
 
		[step] w1=9 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1);
		[step] w1=9 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1);
		[step] w1=9 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1);
		[step] w1=9 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1);
		[step] w1=9 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=9 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=9 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=9 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1);
		[step] w1=9 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1);
		[step] w1=9 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1);
		[step] w1=9 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1);
 
		[step] w1=10 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1);
		[step] w1=10 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=10 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1);
		[step] w1=10 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=10 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=10 & stator9=0	-> 0.009 : (w1'=9) & (stator9'=1);
		[step] w1=10 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1);
		[step] w1=10 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1);
		[step] w1=10 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1);
		[step] w1=10 & stator22=0	-> 8.999999999999999E-5 : (w1'=22) & (stator22'=1);
		[step] w1=10 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1);
		[step] w1=10 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
 
		[step] w1=11 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=11 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1);
		[step] w1=11 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=11 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=11 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1);
		[step] w1=11 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1);
		[step] w1=11 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1);
		[step] w1=11 & stator22=0	-> 1.7999999999999998E-4 : (w1'=22) & (stator22'=1);
		[step] w1=11 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1);
		[step] w1=11 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=11 & stator26=0	-> 8.999999999999999E-5 : (w1'=26) & (stator26'=1);
 
		[step] w1=12 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
		[step] w1=12 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1);
		[step] w1=12 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1);
		[step] w1=12 & stator21=0	-> 0.009 : (w1'=21) & (stator21'=1);
		[step] w1=12 & stator22=0	-> 1.7999999999999998E-4 : (w1'=22) & (stator22'=1);
		[step] w1=12 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1);
		[step] w1=12 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=12 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1);
		[step] w1=12 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1);
 
		[step] w1=13 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=13 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=13 & stator7=0	-> 1.7999999999999998E-4 : (w1'=7) & (stator7'=1);
		[step] w1=13 & stator8=0	-> 0.009 : (w1'=8) & (stator8'=1);
		[step] w1=13 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1);
		[step] w1=13 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1);
		[step] w1=13 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1);
		[step] w1=13 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1);
		[step] w1=13 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1);
		[step] w1=13 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
		[step] w1=13 & stator20=0	-> 8.999999999999999E-6 : (w1'=20) & (stator20'=1);
 
		[step] w1=14 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=14 & stator7=0	-> 1.7999999999999998E-4 : (w1'=7) & (stator7'=1);
		[step] w1=14 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1);
		[step] w1=14 & stator13=0	-> 0.009 : (w1'=13) & (stator13'=1);
		[step] w1=14 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1);
		[step] w1=14 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1);
		[step] w1=14 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1);
		[step] w1=14 & stator18=0	-> 0.009 : (w1'=18) & (stator18'=1);
		[step] w1=14 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1);
		[step] w1=14 & stator20=0	-> 8.999999999999999E-6 : (w1'=20) & (stator20'=1);
 
		[step] w1=15 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=15 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1);
		[step] w1=15 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1);
		[step] w1=15 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1);
		[step] w1=15 & stator16=0	-> 0.009 : (w1'=16) & (stator16'=1);
		[step] w1=15 & stator17=0	-> 1.7999999999999997E-5 : (w1'=17) & (stator17'=1);
		[step] w1=15 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1);
		[step] w1=15 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
		[step] w1=15 & stator20=0	-> 8.999999999999999E-6 : (w1'=20) & (stator20'=1);
 
		[step] w1=16 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1);
		[step] w1=16 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1);
		[step] w1=16 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1);
		[step] w1=16 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1);
		[step] w1=16 & stator17=0	-> 9.0E-4 : (w1'=17) & (stator17'=1);
		[step] w1=16 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1);
		[step] w1=16 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
 
		[step] w1=18 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=18 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=18 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1);
		[step] w1=18 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1);
		[step] w1=18 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1);
		[step] w1=18 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1);
		[step] w1=18 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1);
		[step] w1=18 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1);
		[step] w1=18 & stator19=0	-> 0.009 : (w1'=19) & (stator19'=1);
		[step] w1=18 & stator20=0	-> 1.7999999999999997E-5 : (w1'=20) & (stator20'=1);
		[step] w1=18 & stator25=0	-> 8.999999999999999E-6 : (w1'=25) & (stator25'=1);
 
		[step] w1=19 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=19 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=19 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1);
		[step] w1=19 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1);
		[step] w1=19 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1);
		[step] w1=19 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1);
		[step] w1=19 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1);
		[step] w1=19 & stator18=0	-> 0.009 : (w1'=18) & (stator18'=1);
		[step] w1=19 & stator20=0	-> 9.0E-4 : (w1'=20) & (stator20'=1);
		[step] w1=19 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=19 & stator25=0	-> 8.999999999999999E-6 : (w1'=25) & (stator25'=1);
 
		[step] w1=21 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
		[step] w1=21 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=21 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1);
		[step] w1=21 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1);
		[step] w1=21 & stator22=0	-> 0.009 : (w1'=22) & (stator22'=1);
		[step] w1=21 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1);
		[step] w1=21 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=21 & stator25=0	-> 8.999999999999999E-6 : (w1'=25) & (stator25'=1);
		[step] w1=21 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1);
		[step] w1=21 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1);
		[step] w1=21 & stator28=0	-> 8.999999999999999E-6 : (w1'=28) & (stator28'=1);
 
		[step] w1=22 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=22 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1);
		[step] w1=22 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1);
		[step] w1=22 & stator21=0	-> 0.009 : (w1'=21) & (stator21'=1);
		[step] w1=22 & stator23=0	-> 0.009 : (w1'=23) & (stator23'=1);
		[step] w1=22 & stator24=0	-> 1.7999999999999998E-4 : (w1'=24) & (stator24'=1);
		[step] w1=22 & stator25=0	-> 8.999999999999999E-6 : (w1'=25) & (stator25'=1);
		[step] w1=22 & stator26=0	-> 0.009 : (w1'=26) & (stator26'=1);
		[step] w1=22 & stator27=0	-> 1.7999999999999998E-4 : (w1'=27) & (stator27'=1);
		[step] w1=22 & stator28=0	-> 8.999999999999999E-6 : (w1'=28) & (stator28'=1);
 
		[step] w1=23 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=23 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
		[step] w1=23 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1);
		[step] w1=23 & stator20=0	-> 8.999999999999999E-6 : (w1'=20) & (stator20'=1);
		[step] w1=23 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1);
		[step] w1=23 & stator22=0	-> 0.009 : (w1'=22) & (stator22'=1);
		[step] w1=23 & stator24=0	-> 0.009 : (w1'=24) & (stator24'=1);
		[step] w1=23 & stator25=0	-> 1.7999999999999997E-5 : (w1'=25) & (stator25'=1);
		[step] w1=23 & stator26=0	-> 1.7999999999999998E-4 : (w1'=26) & (stator26'=1);
		[step] w1=23 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1);
		[step] w1=23 & stator28=0	-> 8.999999999999999E-6 : (w1'=28) & (stator28'=1);
 
		[step] w1=24 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=24 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
		[step] w1=24 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1);
		[step] w1=24 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1);
		[step] w1=24 & stator20=0	-> 8.999999999999999E-6 : (w1'=20) & (stator20'=1);
		[step] w1=24 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1);
		[step] w1=24 & stator22=0	-> 1.7999999999999998E-4 : (w1'=22) & (stator22'=1);
		[step] w1=24 & stator23=0	-> 0.009 : (w1'=23) & (stator23'=1);
		[step] w1=24 & stator25=0	-> 9.0E-4 : (w1'=25) & (stator25'=1);
		[step] w1=24 & stator26=0	-> 8.999999999999999E-5 : (w1'=26) & (stator26'=1);
		[step] w1=24 & stator27=0	-> 8.999999999999999E-5 : (w1'=27) & (stator27'=1);
 
		[step] w1=26 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
		[step] w1=26 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1);
		[step] w1=26 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1);
		[step] w1=26 & stator22=0	-> 0.009 : (w1'=22) & (stator22'=1);
		[step] w1=26 & stator23=0	-> 1.7999999999999998E-4 : (w1'=23) & (stator23'=1);
		[step] w1=26 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=26 & stator25=0	-> 8.999999999999999E-6 : (w1'=25) & (stator25'=1);
		[step] w1=26 & stator27=0	-> 0.009 : (w1'=27) & (stator27'=1);
		[step] w1=26 & stator28=0	-> 1.7999999999999997E-5 : (w1'=28) & (stator28'=1);
 
		[step] w1=27 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1);
		[step] w1=27 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1);
		[step] w1=27 & stator22=0	-> 1.7999999999999998E-4 : (w1'=22) & (stator22'=1);
		[step] w1=27 & stator23=0	-> 8.999999999999999E-5 : (w1'=23) & (stator23'=1);
		[step] w1=27 & stator24=0	-> 8.999999999999999E-5 : (w1'=24) & (stator24'=1);
		[step] w1=27 & stator26=0	-> 0.009 : (w1'=26) & (stator26'=1);
		[step] w1=27 & stator28=0	-> 9.0E-4 : (w1'=28) & (stator28'=1);
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
		[]	w1 = 28	->	.000000001	: 	(w1'=28);
	endmodule
 
	rewards "steps"
 
		[step] true : 1;
 
	endrewards
 
	rewards "time"
 
		 true : 1;
 
	endrewards
 
	rewards "blocked" // time spend in blocked anchorages
 
		w1 =9 | w1 =10 | w1 =15 | w1 =16 | w1 =23 | w1 =24	:	1;
 
	endrewards
 
label "deadlockUser" =  
( w1=1 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator9=1)
| ( w1=2 & stator1=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator9=1 & stator10=1)
| ( w1=3 & stator1=1 & stator2=1 & stator4=1 & stator5=1 & stator6=1 & stator7=1 & stator9=1 & stator10=1 & stator11=1)
| ( w1=4 & stator1=1 & stator2=1 & stator3=1 & stator5=1 & stator6=1 & stator7=1 & stator9=1 & stator10=1 & stator11=1)
| ( w1=5 & stator1=1 & stator2=1 & stator3=1 & stator4=1 & stator6=1 & stator7=1 & stator8=1 & stator9=1 & stator10=1 & stator11=1 & stator13=1)
| ( w1=6 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator7=1 & stator8=1 & stator9=1 & stator10=1 & stator13=1 & stator14=1 & stator18=1 & stator19=1)
| ( w1=7 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator8=1 & stator9=1 & stator13=1 & stator14=1 & stator15=1 & stator18=1 & stator19=1)
| ( w1=8 & stator5=1 & stator6=1 & stator7=1 & stator13=1 & stator14=1 & stator15=1 & stator16=1 & stator18=1 & stator19=1)
| ( w1=9 & stator1=1 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator7=1 & stator10=1 & stator11=1 & stator12=1 & stator21=1)
| ( w1=10 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator9=1 & stator11=1 & stator12=1 & stator21=1 & stator22=1 & stator23=1 & stator24=1)
| ( w1=11 & stator3=1 & stator4=1 & stator5=1 & stator9=1 & stator10=1 & stator12=1 & stator21=1 & stator22=1 & stator23=1 & stator24=1 & stator26=1)
| ( w1=12 & stator9=1 & stator10=1 & stator11=1 & stator21=1 & stator22=1 & stator23=1 & stator24=1 & stator26=1 & stator27=1)
| ( w1=13 & stator5=1 & stator6=1 & stator7=1 & stator8=1 & stator14=1 & stator15=1 & stator16=1 & stator17=1 & stator18=1 & stator19=1 & stator20=1)
| ( w1=14 & stator6=1 & stator7=1 & stator8=1 & stator13=1 & stator15=1 & stator16=1 & stator17=1 & stator18=1 & stator19=1 & stator20=1)
| ( w1=15 & stator7=1 & stator8=1 & stator13=1 & stator14=1 & stator16=1 & stator17=1 & stator18=1 & stator19=1 & stator20=1)
| ( w1=16 & stator8=1 & stator13=1 & stator14=1 & stator15=1 & stator17=1 & stator18=1 & stator19=1)
| ( w1=18 & stator6=1 & stator7=1 & stator8=1 & stator13=1 & stator14=1 & stator15=1 & stator16=1 & stator17=1 & stator19=1 & stator20=1 & stator25=1)
| ( w1=19 & stator6=1 & stator7=1 & stator8=1 & stator13=1 & stator14=1 & stator15=1 & stator16=1 & stator18=1 & stator20=1 & stator24=1 & stator25=1)
| ( w1=21 & stator9=1 & stator10=1 & stator11=1 & stator12=1 & stator22=1 & stator23=1 & stator24=1 & stator25=1 & stator26=1 & stator27=1 & stator28=1)
| ( w1=22 & stator10=1 & stator11=1 & stator12=1 & stator21=1 & stator23=1 & stator24=1 & stator25=1 & stator26=1 & stator27=1 & stator28=1)
| ( w1=23 & stator10=1 & stator11=1 & stator12=1 & stator20=1 & stator21=1 & stator22=1 & stator24=1 & stator25=1 & stator26=1 & stator27=1 & stator28=1)
| ( w1=24 & stator10=1 & stator11=1 & stator12=1 & stator19=1 & stator20=1 & stator21=1 & stator22=1 & stator23=1 & stator25=1 & stator26=1 & stator27=1)
| ( w1=26 & stator11=1 & stator12=1 & stator21=1 & stator22=1 & stator23=1 & stator24=1 & stator25=1 & stator27=1 & stator28=1)
| ( w1=27 & stator12=1 & stator21=1 & stator22=1 & stator23=1 & stator24=1 & stator26=1 & stator28=1)
;
