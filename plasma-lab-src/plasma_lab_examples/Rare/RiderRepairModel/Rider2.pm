// Repair model based on Example 2 of (Ridder 2005)
// True probabilty: 1.916 10-6

ctmc

const n = 4;

const double epsilon = 0.001;
const double mu = 1.0;

module type1
state1 : [0..n] init 0; 				// state is the number of components that failed
[] state1 < n -> (n-state1)*epsilon : (state1'=state1+1);	// fail
[] state1 >= 2 -> mu : (state1'=0);			// repair
endmodule

module type2
state2 : [0..n] init 0; 			
[] state2 < n -> (n-state2)*epsilon : (state2'=state2+1);	
[] state2 >=2 & state1 < 2 -> mu : (state2'=0);
endmodule

module type3
state3 : [0..n] init 0;
[] state3 < n -> (n-state3)*epsilon : (state3'=state3+1);
[] state3 > 0 & state2 < 2 & state1 < 2 -> mu : (state3'=state3-1);
endmodule

module type4
state4 : [0..n] init 0;
[] state4 < n -> (n-state4)*epsilon : (state4'=state4+1);
[] state4 > 0 & state3 = 0 & state2 <2 & state1 < 2 -> mu : (state4'=state4-1);
endmodule

module type5
state5 : [0..n] init 0;
[] state5 < n -> (n-state5)*epsilon : (state5'=state5+1);
[] state5 > 0 & state4 = 0 & state3 = 0 & state2 < 2 & state1 < 2 -> mu : (state5'=state5-1);
endmodule

label "start" = (state1=0 & state2 = 0 & state3 =0 & state4 = 0 & state5=0);
label "failure"= (state1=n | state2=n | state3=n | state4=n | state5=n);