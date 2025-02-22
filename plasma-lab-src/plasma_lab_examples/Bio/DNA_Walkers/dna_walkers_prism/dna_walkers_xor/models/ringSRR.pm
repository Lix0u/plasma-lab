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
		stator11		: [0 .. 1] init 1;
		stator12		: [0 .. 1] init 0;
		stator13		: [0 .. 1] init 1;
		stator14		: [0 .. 1] init 0;
		stator15		: [0 .. 1] init 0;
		stator16		: [0 .. 1] init 0;
		stator17		: [0 .. 1] init 0;
		stator18		: [0 .. 1] init 0;
		stator19		: [0 .. 1] init 0;
		stator20		: [0 .. 1] init 0;
		stator21		: [0 .. 1] init 1;
 
		w1 : [0 .. 21] init 1;	// w1=0 is sinkstat for deadlocks
 
 
		blockade13		: [0 .. 1] init 0;
		blockade21		: [0 .. 1] init 0;
		blockade11		: [0 .. 1] init 0;
 
		[block13]  blockade13=0 	->	1000000.0 * failureRate  : (blockade13'=1) & (stator13'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade13'=1);
		[block21]  blockade21=0 	->	1000000.0 * failureRate  : (blockade21'=1) & (stator21'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade21'=1);
		[block11]  blockade11=0 	->	1000000.0 * failureRate  : (blockade11'=1) & (stator11'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade11'=1);
 
 
 
		[step] w1=1 & stator2=0	-> 0.0029999999999999996 : (w1'=2) & (stator2'=1); 

		[step] w1=1 & stator3=0	-> 5.9999999999999995E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=1 & stator4=0	-> 2.9999999999999997E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=1 & stator5=0	-> 5.9999999999999995E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=1 & stator6=0	-> 2.9999999999999997E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=1 & stator7=0	-> 5.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=1 & stator8=0	-> 2.9999999999999997E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=1 & stator9=0	-> 2.9999999999999997E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=1 & stator10=0	-> 2.9999999999999997E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=1 & stator11=0	-> 2.9999999999999997E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=1 & stator12=0	-> 5.9999999999999995E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=1 & stator13=0	-> 0.0029999999999999996 : (w1'=13) & (stator13'=1); 

		[step] w1=1 & stator14=0	-> 2.9999999999999997E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=1 & stator15=0	-> 5.9999999999999995E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=1 & stator16=0	-> 2.9999999999999997E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=1 & stator17=0	-> 5.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=1 & stator18=0	-> 2.9999999999999997E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=1 & stator19=0	-> 2.9999999999999997E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=1 & stator20=0	-> 2.9999999999999997E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=1 & stator21=0	-> 2.9999999999999997E-5 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=2 & stator1=0	-> 0.009 : (w1'=1) & (stator1'=1); 

		[step] w1=2 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=2 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=2 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=2 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=2 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=2 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=2 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=2 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=2 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=2 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=2 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=2 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=2 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=2 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=2 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=2 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=2 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=2 & stator20=0	-> 1.7999999999999998E-4 : (w1'=20) & (stator20'=1); 

		[step] w1=2 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=3 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=3 & stator2=0	-> 0.009 : (w1'=2) & (stator2'=1); 

		[step] w1=3 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1); 

		[step] w1=3 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1); 

		[step] w1=3 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=3 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=3 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=3 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=3 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=3 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=3 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1); 

		[step] w1=3 & stator20=0	-> 1.7999999999999998E-4 : (w1'=20) & (stator20'=1); 

		[step] w1=3 & stator21=0	-> 0.009 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=4 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=4 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=4 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=4 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1); 

		[step] w1=4 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=4 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=4 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=4 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=4 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=4 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=4 & stator20=0	-> 1.7999999999999998E-4 : (w1'=20) & (stator20'=1); 

		[step] w1=4 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=5 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=5 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=5 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=5 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1); 

		[step] w1=5 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1); 

		[step] w1=5 & stator7=0	-> 1.7999999999999997E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=5 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=5 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=5 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=5 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=5 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=6 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=6 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=6 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=6 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=6 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1); 

		[step] w1=6 & stator7=0	-> 9.0E-4 : (w1'=7) & (stator7'=1); 

		[step] w1=6 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=6 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=6 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=6 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=6 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=8 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=8 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=8 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=8 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=8 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=8 & stator6=0	-> 1.7999999999999998E-4 : (w1'=6) & (stator6'=1); 

		[step] w1=8 & stator7=0	-> 9.0E-4 : (w1'=7) & (stator7'=1); 

		[step] w1=8 & stator9=0	-> 0.009 : (w1'=9) & (stator9'=1); 

		[step] w1=8 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=8 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=8 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=8 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=8 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

 
 
		[step] w1=9 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=9 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=9 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=9 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=9 & stator7=0	-> 1.7999999999999997E-5 : (w1'=7) & (stator7'=1); 

		[step] w1=9 & stator8=0	-> 0.009 : (w1'=8) & (stator8'=1); 

		[step] w1=9 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1); 

		[step] w1=9 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=9 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1); 

		[step] w1=9 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=9 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

 
 
		[step] w1=10 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=10 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=10 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=10 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=10 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=10 & stator9=0	-> 0.009 : (w1'=9) & (stator9'=1); 

		[step] w1=10 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1); 

		[step] w1=10 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1); 

		[step] w1=10 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=10 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=10 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

 
 
		[step] w1=11 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=11 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=11 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=11 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=11 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1); 

		[step] w1=11 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1); 

		[step] w1=11 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1); 

		[step] w1=11 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=11 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=11 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=11 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

 
 
		[step] w1=12 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=12 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=12 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=12 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=12 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1); 

		[step] w1=12 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=12 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1); 

		[step] w1=12 & stator13=0	-> 0.009 : (w1'=13) & (stator13'=1); 

		[step] w1=12 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1); 

		[step] w1=12 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1); 

		[step] w1=12 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=12 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=12 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

 
 
		[step] w1=13 & stator1=0	-> 0.009 : (w1'=1) & (stator1'=1); 

		[step] w1=13 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=13 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=13 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=13 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=13 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=13 & stator7=0	-> 8.999999999999999E-6 : (w1'=7) & (stator7'=1); 

		[step] w1=13 & stator8=0	-> 1.7999999999999998E-4 : (w1'=8) & (stator8'=1); 

		[step] w1=13 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=13 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=13 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=13 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1); 

		[step] w1=13 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=13 & stator15=0	-> 1.7999999999999998E-4 : (w1'=15) & (stator15'=1); 

		[step] w1=13 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=13 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=13 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=13 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=13 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

		[step] w1=13 & stator21=0	-> 8.999999999999999E-5 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=14 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=14 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=14 & stator8=0	-> 8.999999999999999E-5 : (w1'=8) & (stator8'=1); 

		[step] w1=14 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1); 

		[step] w1=14 & stator10=0	-> 1.7999999999999998E-4 : (w1'=10) & (stator10'=1); 

		[step] w1=14 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1); 

		[step] w1=14 & stator12=0	-> 0.009 : (w1'=12) & (stator12'=1); 

		[step] w1=14 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=14 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1); 

		[step] w1=14 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=14 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=14 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

 
 
		[step] w1=15 & stator1=0	-> 1.7999999999999998E-4 : (w1'=1) & (stator1'=1); 

		[step] w1=15 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=15 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1); 

		[step] w1=15 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=15 & stator12=0	-> 1.7999999999999998E-4 : (w1'=12) & (stator12'=1); 

		[step] w1=15 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=15 & stator14=0	-> 0.009 : (w1'=14) & (stator14'=1); 

		[step] w1=15 & stator16=0	-> 0.009 : (w1'=16) & (stator16'=1); 

		[step] w1=15 & stator17=0	-> 1.7999999999999997E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=15 & stator18=0	-> 8.999999999999999E-5 : (w1'=18) & (stator18'=1); 

		[step] w1=15 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

 
 
		[step] w1=16 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=16 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=16 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1); 

		[step] w1=16 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=16 & stator13=0	-> 1.7999999999999998E-4 : (w1'=13) & (stator13'=1); 

		[step] w1=16 & stator14=0	-> 1.7999999999999998E-4 : (w1'=14) & (stator14'=1); 

		[step] w1=16 & stator15=0	-> 0.009 : (w1'=15) & (stator15'=1); 

		[step] w1=16 & stator17=0	-> 9.0E-4 : (w1'=17) & (stator17'=1); 

		[step] w1=16 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=16 & stator19=0	-> 8.999999999999999E-5 : (w1'=19) & (stator19'=1); 

		[step] w1=16 & stator20=0	-> 8.999999999999999E-5 : (w1'=20) & (stator20'=1); 

 
 
		[step] w1=18 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=18 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=18 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1); 

		[step] w1=18 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=18 & stator12=0	-> 8.999999999999999E-5 : (w1'=12) & (stator12'=1); 

		[step] w1=18 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=18 & stator14=0	-> 8.999999999999999E-5 : (w1'=14) & (stator14'=1); 

		[step] w1=18 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=18 & stator16=0	-> 1.7999999999999998E-4 : (w1'=16) & (stator16'=1); 

		[step] w1=18 & stator17=0	-> 9.0E-4 : (w1'=17) & (stator17'=1); 

		[step] w1=18 & stator19=0	-> 0.009 : (w1'=19) & (stator19'=1); 

		[step] w1=18 & stator20=0	-> 1.7999999999999998E-4 : (w1'=20) & (stator20'=1); 

		[step] w1=18 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=19 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=19 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1); 

		[step] w1=19 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=19 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1); 

		[step] w1=19 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=19 & stator15=0	-> 8.999999999999999E-5 : (w1'=15) & (stator15'=1); 

		[step] w1=19 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=19 & stator17=0	-> 1.7999999999999997E-5 : (w1'=17) & (stator17'=1); 

		[step] w1=19 & stator18=0	-> 0.009 : (w1'=18) & (stator18'=1); 

		[step] w1=19 & stator20=0	-> 0.009 : (w1'=20) & (stator20'=1); 

		[step] w1=19 & stator21=0	-> 1.7999999999999998E-4 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=20 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=20 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=20 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1); 

		[step] w1=20 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=20 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=20 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=20 & stator16=0	-> 8.999999999999999E-5 : (w1'=16) & (stator16'=1); 

		[step] w1=20 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=20 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=20 & stator19=0	-> 0.009 : (w1'=19) & (stator19'=1); 

		[step] w1=20 & stator21=0	-> 0.009 : (w1'=21) & (stator21'=1); 

 
 
		[step] w1=21 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1); 

		[step] w1=21 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1); 

		[step] w1=21 & stator3=0	-> 0.009 : (w1'=3) & (stator3'=1); 

		[step] w1=21 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1); 

		[step] w1=21 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1); 

		[step] w1=21 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1); 

		[step] w1=21 & stator13=0	-> 8.999999999999999E-5 : (w1'=13) & (stator13'=1); 

		[step] w1=21 & stator17=0	-> 8.999999999999999E-6 : (w1'=17) & (stator17'=1); 

		[step] w1=21 & stator18=0	-> 1.7999999999999998E-4 : (w1'=18) & (stator18'=1); 

		[step] w1=21 & stator19=0	-> 1.7999999999999998E-4 : (w1'=19) & (stator19'=1); 

		[step] w1=21 & stator20=0	-> 0.009 : (w1'=20) & (stator20'=1); 

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
	endmodule
 
	rewards "steps"
 
		[step] true : 1;
 
	endrewards
 
	rewards "time"
 
		 true : 1;
 
	endrewards
 
	rewards "blocked" // time spend in blocked anchorages
 
		w1 =13 | w1 =21 | w1 =11	:	1;
 
	endrewards
