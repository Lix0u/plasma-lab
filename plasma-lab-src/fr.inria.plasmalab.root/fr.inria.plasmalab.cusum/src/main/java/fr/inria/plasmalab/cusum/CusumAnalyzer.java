/**
 * This file is part of fr.inria.plasmalab.cusum.
 *
 * fr.inria.plasmalab.cusum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.cusum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.cusum.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.cusum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CusumAnalyzer {

    public double[] buffer;
    public BufferedReader input;
    public PrintStream output;
    public int series;


    public CusumAnalyzer(String pathIn, String pathOut) throws FileNotFoundException {
        input = new BufferedReader(new FileReader(pathIn));
        output = new PrintStream(pathOut);
        buffer = null;
        series = 0;
    }

    public void nxtSeries() {
        try {
            String[] line = input.readLine().split(" ");
            buffer = new double[line.length];

            for (int i = 0; i < line.length; i++)
                buffer[i] = Double.parseDouble(line[i]);
            series++;
        } catch (IOException e) {
            // I/O Error... nothing special to do, game over
            e.printStackTrace();
        } catch (NullPointerException e) {
            buffer = null;
        }
    }

    public boolean ready() {
        return (buffer != null);
    }

    public double[] drift(){
        double [] drift = new double[buffer.length - 1];

        for (int i = 0; i < drift.length; i++)
            drift[i] = buffer[i+1] - buffer[i];
        return drift;
    }


    public void peaks(double drift[]) {

        List<Integer> indices = new ArrayList<Integer>();
        boolean negative = true;

        for (int i = 0; i < drift.length; i++)
            if (negative && drift[i] >= 0) {
                negative = false;
                indices.add(i);
            } else if (!negative && drift[i] < 0) {
                negative = true;
                indices.add(i);
            }

        if (indices.size() > 0) {
            if (drift.length > 0) {
                int lastIndex = drift.length - 1;
                int lastStored = indices.get(indices.size() - 1);

                if (lastIndex != lastStored)
                    indices.add(lastIndex);
            }

            // output.println("#" + series);

            int last = 0;
            for (Integer index : indices) {
                output.print((index - last) + " ");
                last = index;
            }
            output.println();
            /*for (Integer index : indices)
                output.print(buffer[index] + " ");
            output.println("\n");*/
        }
    }
    static boolean debug = false;
    public static PrintStream dbg_output = System.out;

    public static void dumpArray(double[] array) {
        if (debug) {
            if (array != null) {
                for (double val: array)
                    dbg_output.println(val + " ");
                dbg_output.println();
            } else
                dbg_output.println("null");
        }
    }


    public static void main(String[] args) {

        if (args.length < 2) {
            StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
            StackTraceElement main = stack[stack.length - 1];
            String mainClass = main.getClassName ();
            System.out.println("Usage: "+mainClass+" Vinfile outfile");
            System.out.println("\t files are in TSV format...");
            System.exit(0);
        }

        try {
            CusumAnalyzer le = new CusumAnalyzer(args[0], args[1]);
            dbg_output = le.output;
            System.out.println("input file = " + args[0]);
            System.out.println("output file = " + args[1]);
            le.nxtSeries();

            //le.dumpArray(le.buffer);
            @SuppressWarnings("unused")
			int i = 1;
            while (le.ready()) {
                double drift[] = le.drift();
                //dumpArray(drift);
                le.peaks(drift);
                le.nxtSeries();
                //System.out.println("# " + i);
                i++;
                //dumpArray(le.buffer);
            }
            le.input.close();
            le.output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
