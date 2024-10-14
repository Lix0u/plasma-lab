/**
 * This file is part of fr.inria.plasmalab.service.
 *
 * fr.inria.plasmalab.service is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.service is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.service.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.service;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fr.inria.plasmalab.controler.Controler;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class AboutDialog{
	
	private final static String message = "";
	private static String title;
	private final static ImageIcon icon = new ImageIcon(Thread.currentThread()
				.getContextClassLoader()
				.getResource("splashService.png"));
	
	public static void showAboutDialog(Component parentComponent){
		if(title == null)
			title = "Plasma Service v"+Controler.getVersion()+"-"+Controler.getVersion();
		JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
	}
}
