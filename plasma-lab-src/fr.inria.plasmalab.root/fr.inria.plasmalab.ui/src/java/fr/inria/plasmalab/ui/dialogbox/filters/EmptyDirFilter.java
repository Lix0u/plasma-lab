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

import javax.swing.filechooser.FileFilter;

public class EmptyDirFilter extends FileFilter {

	public String description = "Show only empty directories";
	
	public EmptyDirFilter(String description) {
		if (description != null)
			this.description = description;
	}
	
	public EmptyDirFilter() {
		this.description = "";
	}

	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			for(File f: file.listFiles())
				if (!f.isDirectory()) return false;
			return true;
		} else
			return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

}