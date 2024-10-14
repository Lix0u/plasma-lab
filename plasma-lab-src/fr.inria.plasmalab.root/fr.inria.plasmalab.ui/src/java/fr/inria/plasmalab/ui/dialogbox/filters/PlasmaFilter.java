/**
 * This file is part of fr.inria.plasmalab.ui.
 *
 * fr.inria.plasmalab.ui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.ui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.ui.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.ui.dialogbox.filters;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

public class PlasmaFilter extends FileFilter {
	public String description;
	public Set<String> extensions;
	public boolean caseSensitive = false;

	private String normalize(String str) {
		return caseSensitive ? str : str.toLowerCase(); 
	}

	public PlasmaFilter(String description, String extensions[]) {
		this.extensions = new HashSet<String> ();
		this.description = (description == null ? "" : description);

		for (String ext: extensions)
			if (ext.charAt(0) == '.')
				this.extensions.add(normalize(ext));
			else
				this.extensions.add("." + normalize(ext));
	}
	
	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) 
			return true;
		else {
			String path = normalize(file.getAbsolutePath());
			for (String extension : this.extensions)
				if (path.endsWith(extension)) return true;
			return false;
		}
	}

	@Override
	public String getDescription() {
		return description;
	}

	public final static String[] modelExtension = { "model", "pm", "bio", "sm", "nm" };
	public final static String[] projectExtension = { "plasma", "project" };
	public final static String[] logicExtension = { "contract", "prop" };

	public static PlasmaFilter forModelFiles() {
		return new PlasmaFilter("Model Files", modelExtension);
	}

	public static PlasmaFilter forProjectFiles() {
		return new PlasmaFilter("Plasma Project Files", projectExtension);
	}

	public static PlasmaFilter forLogicFiles() {
		return new PlasmaFilter("BLTL Files", logicExtension);
	}
	
	public static PlasmaFilter forDirs() {
		return new PlasmaFilter("Only Directories", new String[0]);
	}

	
	
}