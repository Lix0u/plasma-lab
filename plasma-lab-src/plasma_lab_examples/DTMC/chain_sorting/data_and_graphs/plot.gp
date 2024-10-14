set terminal postscript size 6,4
set output "cdfs5uni.eps"
set xrange [1:2.4] 
set xlabel "Minimal Speedup"
plot "data5uni" u 5:6 w steps t "Neighbor", "" u 5:7 w steps t "Distant", "" u 5:8 w steps t "Concentric"

set output "cdfs5pre.eps"
set xrange [1:2.4] 
set xlabel "Minimal Speedup"
plot "data5pre" u 5:6 w steps t "Neighbor", "" u 5:7 w steps t "Distant", "" u 5:8 w steps t "Concentric"

set output "cdfs7uni.eps"
set xrange [1:2.5] 
set xlabel "Minimal Speedup"
plot "data7uni" u 5:6 w steps t "Neighbor", "" u 5:7 w steps t "Distant", "" u 5:8 w steps t "Concentric"

set output "cdfs7pre.eps"
set xrange [1:2.5] 
set xlabel "Minimal Speedup"
plot "data7pre" u 5:6 w steps t "Neighbor", "" u 5:7 w steps t "Distant", "" u 5:8 w steps t "Concentric"

#set terminal postscript enhanced size 4,2.7
#set output "cmpP1P2.eps"
#set xrange [0.4:2.1]
#set xlabel "{/Symbol a}"
#set ylabel "Probability"
#plot "cmpP1P2data" u ($2/$1):3 smooth cumulative t ""
#
#set output "cmpP1P2_v2.eps"
#plot "cmpP1P2data" u ($2/$1):4 w steps t ""
#
#set output "cmpP2P1.eps"
#plot "cmpP1P2data" u ($1/$2):3 smooth cumulative t ""
#
#
#
#set output "cmpP1P3.eps"
#plot "cmpP1P3data" u ($2/$1):3 smooth cumulative t ""
#
#set output "cmpP3P1.eps"
#plot "cmpP1P3data" u ($1/$2):3 smooth cumulative t ""
#
#
#
#set output "cmpP2P3.eps"
#plot "cmpP2P3data" u ($2/$1):3 smooth cumulative t ""
#
#set output "cmpP3P2.eps"
#plot "cmpP2P3data" u ($1/$2):3 smooth cumulative t ""
