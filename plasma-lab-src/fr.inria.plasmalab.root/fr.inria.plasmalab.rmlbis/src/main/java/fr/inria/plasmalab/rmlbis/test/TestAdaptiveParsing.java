/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.test;

import java.io.File;
import java.util.ArrayList;

import fr.inria.plasmalab.rmlbis.parsing.AdaptiveParserProcessing;

public class TestAdaptiveParsing {
	
	public static boolean testFile(String filename) throws Exception {
		try {
			AdaptiveParserProcessing parser = new AdaptiveParserProcessing(new File(filename));
			parser.toString(); // do nothing, just remove the warning
			System.out.println(" ..... OK");
			System.out.println(parser.model.toPrism(new ArrayList<String>()));
			return true;
		}
		catch (Exception e) {
			System.err.println(" ..... KO");
			e.printStackTrace();
			//System.exit(0);
			return false;
		}
	}
	
	public static void browseDirectory(String dirname) throws Exception {
    	File dir = new File(dirname);
		if (!dir.getName().startsWith(".")) { 	// ignore hidden files or directories
			if (dir.isFile()) {
				if (dir.getName().endsWith(".nm") || dir.getName().endsWith(".pm") || dir.getName().endsWith(".sm") || dir.getName().endsWith(".rm")) {
					System.out.print("Testing file:" + dirname);
					testFile(dirname);
				}
			}
			else if (dir.isDirectory()) {
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
    		browseDirectory(args[0]);
    	}
    }
}

