#! /bin/sh

if [ $# -ne 1 ] ; then
    echo "Usage: $0 nb_philo"
    exit
fi
file="philnofair$1.prop"
echo "Generating $file"

echo "Rmax=?[F \"eat\"]" > "$file"

file="philnofair$1.nm"
echo "Generating $file"

echo "// Randomised dining philosophers based on Lehmann & Rabin 1981" > "$file"
echo "// No fairness" >> "$file"
echo >> "$file"
echo "mdp" >> "$file"
echo >> "$file"
echo "formula lfree = p2>=0 & p2<=4 | p2=6 | p2=10;" >> "$file"
echo "formula rfree = p$1>=0 & p$1<=3 | p$1=5 | p$1=7 | p$1=11;" >> "$file"
echo >> "$file"
echo "module phil1" >> "$file"
echo "	p1: [0..11];" >> "$file"
echo "	[] p1=0 -> (p1'=1);" >> "$file"
echo "	[] p1=1 -> 0.5 : (p1'=2) + 0.5 : (p1'=3);" >> "$file"
echo "	[] p1=2 & lfree -> (p1'=4);" >> "$file"
echo "	[] p1=3 & rfree -> (p1'=5);" >> "$file"
echo "	[] p1=4 & rfree -> (p1'=8);" >> "$file"
echo "	[] p1=4 & !rfree -> (p1'=6);" >> "$file"
echo "	[] p1=5 & lfree -> (p1'=8);" >> "$file"
echo "	[] p1=5 & !lfree -> (p1'=7);" >> "$file"
echo "	[] p1=6 -> (p1'=1);" >> "$file"
echo "	[] p1=7 -> (p1'=1);" >> "$file"
echo "	[] p1=8 -> (p1'=9);" >> "$file"
echo "	[] p1=9 -> (p1'=10);" >> "$file"
echo "	[] p1=9 -> (p1'=11);" >> "$file"
echo "	[] p1=10 -> (p1'=0);" >> "$file"
echo "	[] p1=11 -> (p1'=0);" >> "$file"
echo "endmodule" >> "$file"
echo >> "$file"
i=2
while [ $i -lt $1 ] ; do
    j=$(( $i + 1 ))
    k=$(( $i - 1 ))
    echo "module phil$i  = phil1 [p1=p$i,  p2=p$j,  p$1=p$k]  endmodule" >> "$file"
    i=$(( $i + 1))
done
j=$(( $1 - 1 ))
echo "module phil$1 = phil1 [p1=p$1, p2=p1,  p$1=p$j] endmodule" >> "$file"
echo >> "$file"
echo "rewards \"num_steps\"" >> "$file"
echo "	[] true : 1;" >> "$file"
echo "endrewards" >> "$file"
echo >> "$file"
echo -n "label \"hungry\" = " >> "$file"
i=1
while [ $i -lt $1 ] ; do
    echo -n "p$i>0 & p$i<8 | " >> "$file"
    i=$(( $i + 1 ))
done
echo "p$i>0 & p$i<8;" >> "$file"
echo -n "label \"eat\" = " >> "$file"
i=1
while [ $i -lt $1 ] ; do
    echo -n "p$i>=8 & p$i<=9 | " >> "$file"
    i=$(( $i + 1 ))
done
echo -n "p$i>=8 & p$i<=9;" >> "$file"

echo "Finished !"
