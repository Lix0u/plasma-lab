// Repair model based on Example 3 of (Ridder 2005)
// True probabilty: 7.488 10-7

ctmc

const n1 = 5;
const n2 = 4;
const n3 = 6;
const n4 = 3;
const n5 = 7;
const n6 = 5;

const double epsilon = 0.001;
const double f1 = 2.5*epsilon;
const double f2 = epsilon;
const double f3 = 5*epsilon;
const double f4 = 3*epsilon;
const double f5 = epsilon;
const double f6 = 5*epsilon;

const double r1 = 1.0;
const double r2 = 1.5;
const double r3 = 1.0;
const double r4 = 2.0;
const double r5 = 1.0;
const double r6 = 1.5;

module type1
state1 : [0..n1] init 0; 			// state is the number of components that failed
[] state1 < n1 -> (n1-state1)*f1 : (state1'=state1+1);	// fail
[] state1 > 0 -> r1 : (state1'=state1-1);		// repair
endmodule

module type2
state2 : [0..n2] init 0; 			
[] state2 < n2 -> (n2-state2)*f2 : (state2'=state2+1);	
[] state2 > 0 & state1 = 0 -> r2 : (state2'=state2-1);	// repair if type1 has not failed
endmodule

module type3
state3 : [0..n3] init 0;
[] state3 < n3 -> (n3-state3)*f3 : (state3'=state3+1);
[] state3 > 0 & state2 = 0 & state1 = 0 -> r3 : (state3'=state3-1);	// repair if type1 and type2 have not failed
endmodule

module type4
state4 : [0..n4] init 0;
[] state4 < n4 -> (n4-state4)*f4 : (state4'=state4+1);
[] state4 > 0 & state3 = 0 & state2 =0 & state1 = 0 -> r4 : (state4'=state4-1);	// repair if type1 and type2 and type3 have not failed
endmodule

module type5
state5 : [0..n5] init 0;
[] state5 < n5 -> (n5-state5)*f5 : (state5'=state5+1);
[] state5 > 0 & state4 = 0 & state3 = 0 & state2 =0 & state1 = 0 -> r5 : (state5'=state5-1);	// repair if type1 and type2 and type3 and type4 have not failed
endmodule

module type6
state6 : [0..n6] init 0;
[] state6 < n6 -> (n6-state6)*f6 : (state6'=state6+1);
[] state6 > 0 & state5 = 0 & state4 = 0 & state3 = 0 & state2 =0 & state1 = 0 -> r6 : (state6'=state6-1);	// repair if other types have not failed
endmodule


label "start" = (state1=0 & state2 = 0 & state3 =0 & state4 = 0 & state5=0);
label "failure"= (state1=n1 | state2=n2 | state3=n3 | state4=n4 | state5=n5 | state6=n6);