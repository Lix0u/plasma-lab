dtmc

// partition0 : 
// {
//   {c, d},
//   {a, b},
// }
//
// partition1 : 
// {
//   {a, c},
//   {b, d},
// }
//
// partition2 : 
// {
//   {b, c},
//   {a, d},
// }
//


// Actual code
// -----------

// number of step to count
const int k ;

//guard when counting
formula counting = (b) & (c < k) ;

//formulas to update interaction counters
formula updp0a = mod(max(p0a,p0b)+1, k+1);
formula updp0b = mod(max(p0b,p0a,p0c)+1, k+1);
formula updp0c = mod(max(p0c,p0b,p0d)+1, k+1);
formula updp0d = mod(max(p0d,p0c)+1, k+1);

formula updp1a = mod(max(p1a,p1b,p1c)+1, k+1);
formula updp1b = mod(max(p1b,p1a,p1c,p1d)+1, k+1);
formula updp1c = mod(max(p1c,p1b,p1d,p1a)+1, k+1);
formula updp1d = mod(max(p1d,p1c,p1b)+1, k+1);

formula updp2a = mod(max(p2a,p2b,p2d)+1, k+1);
formula updp2b = mod(max(p2b,p2a,p2c)+1, k+1);
formula updp2c = mod(max(p2c,p2b,p2d)+1, k+1);
formula updp2d = mod(max(p2d,p2c,p2a)+1, k+1);


// formulas to use in properties to check
formula p0steps = max(p0a,p0b,p0c,p0d) ;
formula p1steps = max(p1a,p1b,p1c,p1d) ;
formula p2steps = max(p2a,p2b,p2c,p2d) ;

// range of the values to sort
const int maxval = 3 ;

module chain

  // --------- 
  // VARIABLES 
  // _________ 

  //state variable
  s: [0..31] init 0 ;

  // variables to store the last exec date for each interaction, for partition0
  p0a:[0..k] init 0;
  p0b:[0..k] init 0;
  p0c:[0..k] init 0;
  p0d:[0..k] init 0;

  // variables to store the last exec date for each interaction, for partition1
  p1a:[0..k] init 0;
  p1b:[0..k] init 0;
  p1c:[0..k] init 0;
  p1d:[0..k] init 0;

  // variables to store the last exec date for each interaction, for partition2
  p2a:[0..k] init 0;
  p2b:[0..k] init 0;
  p2c:[0..k] init 0;
  p2d:[0..k] init 0;

  //boolean to switch counting 
  b: bool init false;
  //counter
  c: [0..k] init 0;


  // ----------- 
  // TRANSITIONS 
  // ___________ 


  [] (!b) &  (s=0) -> 1.0: (s'=1);
  [] (!b) &  (s=1) -> 1.0: (s'=2);
  [] (!b) &  (s=2) -> 0.5: (s'=3)
                     +0.5: (s'=4);
  [] (!b) &  (s=3) -> 1.0: (s'=5);
  [] (!b) &  (s=4) -> 0.5: (s'=5)
                     +0.5: (s'=8);
  [] (!b) &  (s=5) -> 0.5: (s'=6)
                     +0.5: (s'=9);
  [] (!b) &  (s=6) -> 0.5: (s'=7)
                     +0.5: (s'=10);
  [] (!b) &  (s=7) -> 1.0: (s'=11);
  [] (!b) &  (s=8) -> 0.5: (s'=9)
                     +0.5: (s'=16);
  [] (!b) &  (s=9) -> 0.5: (s'=10)
                     +0.5: (s'=17);
  [] (!b) &  (s=10) -> 0.333333333333: (s'=11)
                      +0.333333333333: (s'=12)
                      +0.333333333333: (s'=18);
  [] (!b) &  (s=11) -> 0.5: (s'=13)
                      +0.5: (s'=19);
  [] (!b) &  (s=12) -> 0.5: (s'=13)
                      +0.5: (s'=20);
  [] (!b) &  (s=13) -> 0.5: (s'=14)
                      +0.5: (s'=21);
  [] (!b) &  (s=14) -> 0.5: (s'=15)
                      +0.5: (s'=22);
  [] (!b) &  (s=15) -> 1.0: (s'=23);
  [] (!b) &  (s=16) -> 0.5: (s'=17)
                      +0.5: (s'=0);
  [] (!b) &  (s=17) -> 0.5: (s'=18)
                      +0.5: (s'=1);
  [] (!b) &  (s=18) -> 0.333333333333: (s'=19)
                      +0.333333333333: (s'=20)
                      +0.333333333333: (s'=2);
  [] (!b) &  (s=19) -> 0.5: (s'=21)
                      +0.5: (s'=3);
  [] (!b) &  (s=20) -> 0.333333333333: (s'=21)
                      +0.333333333333: (s'=24)
                      +0.333333333333: (s'=4);
  [] (!b) &  (s=21) -> 0.333333333333: (s'=22)
                      +0.333333333333: (s'=25)
                      +0.333333333333: (s'=5);
  [] (!b) &  (s=22) -> 0.333333333333: (s'=23)
                      +0.333333333333: (s'=26)
                      +0.333333333333: (s'=6);
  [] (!b) &  (s=23) -> 0.5: (s'=27)
                      +0.5: (s'=7);
  [] (!b) &  (s=24) -> 0.5: (s'=25)
                      +0.5: (s'=8);
  [] (!b) &  (s=25) -> 0.5: (s'=26)
                      +0.5: (s'=9);
  [] (!b) &  (s=26) -> 0.333333333333: (s'=27)
                      +0.333333333333: (s'=28)
                      +0.333333333333: (s'=10);
  [] (!b) &  (s=27) -> 0.5: (s'=29)
                      +0.5: (s'=11);
  [] (!b) &  (s=28) -> 0.5: (s'=29)
                      +0.5: (s'=12);
  [] (!b) &  (s=29) -> 0.5: (s'=30)
                      +0.5: (s'=13);
  [] (!b) &  (s=30) -> 0.5: (s'=31)
                      +0.5: (s'=14);
  [] (!b) &  (s=31) -> 1.0: (s'=15);
  // transition to start counting
  [] (!b) -> (b'=true) ;

  [] (counting) &  (s=0) -> 1.0: (s'=1) ;
  [] (counting) &  (s=1) -> 1.0: (s'=2) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a) ;
  [] (counting) &  (s=2) -> 0.5: (s'=3)
                           +0.5: (s'=4) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b) ;
  [] (counting) &  (s=3) -> 1.0: (s'=5) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b) ;
  [] (counting) &  (s=4) -> 0.5: (s'=5)
                           +0.5: (s'=8) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c) ;
  [] (counting) &  (s=5) -> 0.5: (s'=6) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                           +0.5: (s'=9) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c) ;
  [] (counting) &  (s=6) -> 0.5: (s'=7)
                           +0.5: (s'=10) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c) ;
  [] (counting) &  (s=7) -> 1.0: (s'=11) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c) ;
  [] (counting) &  (s=8) -> 0.5: (s'=9)
                           +0.5: (s'=16) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=9) -> 0.5: (s'=10) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                           +0.5: (s'=17) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=10) -> 0.333333333333: (s'=11)
                            +0.333333333333: (s'=12) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.333333333333: (s'=18) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=11) -> 0.5: (s'=13) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.5: (s'=19) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=12) -> 0.5: (s'=13)
                            +0.5: (s'=20) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=13) -> 0.5: (s'=14) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                            +0.5: (s'=21) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=14) -> 0.5: (s'=15)
                            +0.5: (s'=22) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=15) -> 1.0: (s'=23) & (c'=c+1) & (p0d'=updp0d) & (p1d'=updp1d) & (p2d'=updp2d) ;
  [] (counting) &  (s=16) -> 0.5: (s'=17)
                            +0.5: (s'=0) ;
  [] (counting) &  (s=17) -> 0.5: (s'=18) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                            +0.5: (s'=1) ;
  [] (counting) &  (s=18) -> 0.333333333333: (s'=19)
                            +0.333333333333: (s'=20) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.333333333333: (s'=2) ;
  [] (counting) &  (s=19) -> 0.5: (s'=21) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.5: (s'=3) ;
  [] (counting) &  (s=20) -> 0.333333333333: (s'=21)
                            +0.333333333333: (s'=24) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c)
                            +0.333333333333: (s'=4) ;
  [] (counting) &  (s=21) -> 0.333333333333: (s'=22) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                            +0.333333333333: (s'=25) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c)
                            +0.333333333333: (s'=5) ;
  [] (counting) &  (s=22) -> 0.333333333333: (s'=23)
                            +0.333333333333: (s'=26) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c)
                            +0.333333333333: (s'=6) ;
  [] (counting) &  (s=23) -> 0.5: (s'=27) & (c'=c+1) & (p0c'=updp0c) & (p1c'=updp1c) & (p2c'=updp2c)
                            +0.5: (s'=7) ;
  [] (counting) &  (s=24) -> 0.5: (s'=25)
                            +0.5: (s'=8) ;
  [] (counting) &  (s=25) -> 0.5: (s'=26) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                            +0.5: (s'=9) ;
  [] (counting) &  (s=26) -> 0.333333333333: (s'=27)
                            +0.333333333333: (s'=28) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.333333333333: (s'=10) ;
  [] (counting) &  (s=27) -> 0.5: (s'=29) & (c'=c+1) & (p0b'=updp0b) & (p1b'=updp1b) & (p2b'=updp2b)
                            +0.5: (s'=11) ;
  [] (counting) &  (s=28) -> 0.5: (s'=29)
                            +0.5: (s'=12) ;
  [] (counting) &  (s=29) -> 0.5: (s'=30) & (c'=c+1) & (p0a'=updp0a) & (p1a'=updp1a) & (p2a'=updp2a)
                            +0.5: (s'=13) ;
  [] (counting) &  (s=30) -> 0.5: (s'=31)
                            +0.5: (s'=14) ;
  [] (counting) &  (s=31) -> 1.0: (s'=15) ;

endmodule
