/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.test;

import java.io.File;

public class TestParsing {
	
	public static boolean testFile(String filename) throws Exception {
		ALTLTestRequirement req = new ALTLTestRequirement("formula", new File(filename), "altl");
		
		boolean error = req.checkForErrorsTest();
		if (!error) {
			System.out.println(" ..... OK");
			System.out.println(req.toString());
			return true;
		}
		else {
			System.err.println(" ..... KO");
			System.exit(0);
			return false;
		}
	}
	
	public static String[] filetypes = { "pctl", "altl", "bltl", "ltl" };
	
	public static boolean isRelevant(String target) {
		for (String suffix: filetypes)
			if (target.endsWith("." + suffix))
				return true;
		return false;
	}
	
	public static boolean isVisible(String target) {
		return !target.startsWith(".");
	}
	
	
	public static void browseDirectory(String dirname) throws Exception {
    	File dir = new File(dirname);
		if (isVisible(dir.getName())) { 	// ignore hidden files or directories
			if (dir.isFile()) {
				if (isRelevant(dir.getName())) {
					System.out.print("Testing file:" + dirname);
					testFile(dirname);
				}
			} else if (dir.isDirectory()) {
				String[] chld = dir.list();
				System.out.println("In: " + dirname);
				for (int i = 0; i < chld.length; i++) {
					browseDirectory(dirname + "/" + chld[i]);
				}
			}
		}
	}
		
    public static void main(String args[]) throws Exception {
    	if (args.length>0) {
//    		testFile(args[0]);
    		browseDirectory(args[0]);
    	}
    }
}

