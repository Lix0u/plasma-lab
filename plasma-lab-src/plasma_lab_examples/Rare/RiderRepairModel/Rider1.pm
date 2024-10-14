// Repair model based on Example 1 of (Ridder 2005)
// Property: X (!(state1=0 & state2=0 & state3=0) U <=#10000 state1=n & state2 = n & state3=n)
// True probabilty: 7.79675941302816E-6 for n=3
//                          1.17735 10-7 for n=4
ctmc 

const int n=3;
const double epsilon = 0.1;
const double mu = 1.0;

module type1
state1 : [0..n] init 0;
[] state1 < n -> (n-state1)*epsilon*epsilon : (state1'=state1+1);	// lambda = 1.8
[] state1 >= 2 -> mu : (state1'=0);					// lambda = 0.002
endmodule

module type2
state2 : [0..n] init 0;
[] state2 < n -> (n-state2)*epsilon : (state2'=state2+1);	// lambda = 1.2
[] state2 >= 2 & state1 < 2 -> mu : (state2'=0);		// lambda = 0.88
endmodule

module type3
state3 : [0..n] init 0;
[] state3 < n -> (n-state3)*epsilon : (state3'=state3+1);		// lambda = 1.3
[] state3 > 0 & state2 < 2 & state1 < 2 -> mu : (state3'=state3-1);	// lambda = 0.87
endmodule

label "start" = (state1=0 & state2=0 & state3=0);
label "failure"= (state1=n & state2=n & state3=n);