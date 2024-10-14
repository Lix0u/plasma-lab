	ctmc
 
				const double failureRate=0.3;
 
 	module walker
 
		stator1		: [0 .. 1] init 1;
		stator2		: [0 .. 1] init 0;
		stator3		: [0 .. 1] init 0;
		stator4		: [0 .. 1] init 0;
		stator5		: [0 .. 1] init 1;
		stator6		: [0 .. 1] init 0;
		stator7		: [0 .. 1] init 0;
		stator8		: [0 .. 1] init 0;
		stator9		: [0 .. 1] init 0;
		stator10		: [0 .. 1] init 0;
		stator11		: [0 .. 1] init 0;
		stator12		: [0 .. 1] init 0;
 
		w1 : [0 .. 12] init 1;	// w1=0 is sinkstat for deadlocks
 
 
		blockade5		: [0 .. 1] init 0;
 
		[block5]  blockade5=0 	->	1000000.0 * failureRate  : (blockade5'=1) & (stator5'=0)	+	 1000000.0 * (1.0 - failureRate ) : (blockade5'=1);
 
 
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
		[step] w1=5 & stator8=0	-> 8.999999999999999E-6 : (w1'=8) & (stator8'=1);
		[step] w1=5 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=5 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
		[step] w1=5 & stator11=0	-> 8.999999999999999E-5 : (w1'=11) & (stator11'=1);
 
		[step] w1=6 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1);
		[step] w1=6 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=6 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1);
		[step] w1=6 & stator5=0	-> 0.009 : (w1'=5) & (stator5'=1);
		[step] w1=6 & stator7=0	-> 0.009 : (w1'=7) & (stator7'=1);
		[step] w1=6 & stator8=0	-> 1.7999999999999997E-5 : (w1'=8) & (stator8'=1);
		[step] w1=6 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
		[step] w1=6 & stator10=0	-> 8.999999999999999E-5 : (w1'=10) & (stator10'=1);
 
		[step] w1=7 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=7 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1);
		[step] w1=7 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=7 & stator6=0	-> 0.009 : (w1'=6) & (stator6'=1);
		[step] w1=7 & stator8=0	-> 9.0E-4 : (w1'=8) & (stator8'=1);
		[step] w1=7 & stator9=0	-> 8.999999999999999E-5 : (w1'=9) & (stator9'=1);
 
		[step] w1=9 & stator1=0	-> 8.999999999999999E-5 : (w1'=1) & (stator1'=1);
		[step] w1=9 & stator2=0	-> 1.7999999999999998E-4 : (w1'=2) & (stator2'=1);
		[step] w1=9 & stator3=0	-> 1.7999999999999998E-4 : (w1'=3) & (stator3'=1);
		[step] w1=9 & stator4=0	-> 0.009 : (w1'=4) & (stator4'=1);
		[step] w1=9 & stator5=0	-> 1.7999999999999998E-4 : (w1'=5) & (stator5'=1);
		[step] w1=9 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=9 & stator7=0	-> 8.999999999999999E-5 : (w1'=7) & (stator7'=1);
		[step] w1=9 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1);
		[step] w1=9 & stator11=0	-> 1.7999999999999998E-4 : (w1'=11) & (stator11'=1);
		[step] w1=9 & stator12=0	-> 8.999999999999999E-6 : (w1'=12) & (stator12'=1);
 
		[step] w1=10 & stator2=0	-> 8.999999999999999E-5 : (w1'=2) & (stator2'=1);
		[step] w1=10 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=10 & stator4=0	-> 1.7999999999999998E-4 : (w1'=4) & (stator4'=1);
		[step] w1=10 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=10 & stator6=0	-> 8.999999999999999E-5 : (w1'=6) & (stator6'=1);
		[step] w1=10 & stator9=0	-> 0.009 : (w1'=9) & (stator9'=1);
		[step] w1=10 & stator11=0	-> 0.009 : (w1'=11) & (stator11'=1);
		[step] w1=10 & stator12=0	-> 1.7999999999999997E-5 : (w1'=12) & (stator12'=1);
 
		[step] w1=11 & stator3=0	-> 8.999999999999999E-5 : (w1'=3) & (stator3'=1);
		[step] w1=11 & stator4=0	-> 8.999999999999999E-5 : (w1'=4) & (stator4'=1);
		[step] w1=11 & stator5=0	-> 8.999999999999999E-5 : (w1'=5) & (stator5'=1);
		[step] w1=11 & stator9=0	-> 1.7999999999999998E-4 : (w1'=9) & (stator9'=1);
		[step] w1=11 & stator10=0	-> 0.009 : (w1'=10) & (stator10'=1);
		[step] w1=11 & stator12=0	-> 9.0E-4 : (w1'=12) & (stator12'=1);
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
	endmodule
 
	rewards "steps"
 
		[step] true : 1;
 
	endrewards
 
	rewards "time"
 
		 true : 1;
 
	endrewards
 
	rewards "blocked" // time spend in blocked anchorages
 
		w1 =5	:	1;
 
	endrewards
 
label "deadlockUser" =  
( w1=1 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator9=1)
| ( w1=2 & stator1=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator9=1 & stator10=1)
| ( w1=3 & stator1=1 & stator2=1 & stator4=1 & stator5=1 & stator6=1 & stator7=1 & stator9=1 & stator10=1 & stator11=1)
| ( w1=4 & stator1=1 & stator2=1 & stator3=1 & stator5=1 & stator6=1 & stator7=1 & stator9=1 & stator10=1 & stator11=1)
| ( w1=5 & stator1=1 & stator2=1 & stator3=1 & stator4=1 & stator6=1 & stator7=1 & stator8=1 & stator9=1 & stator10=1 & stator11=1)
| ( w1=6 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator7=1 & stator8=1 & stator9=1 & stator10=1)
| ( w1=7 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator8=1 & stator9=1)
| ( w1=9 & stator1=1 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator7=1 & stator10=1 & stator11=1 & stator12=1)
| ( w1=10 & stator2=1 & stator3=1 & stator4=1 & stator5=1 & stator6=1 & stator9=1 & stator11=1 & stator12=1)
| ( w1=11 & stator3=1 & stator4=1 & stator5=1 & stator9=1 & stator10=1 & stator12=1)
;
