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
package fr.inria.plasmalab.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Common {
	public static final int defaultBorderSize = 5;
	final static Logger logger = LoggerFactory.getLogger(Common.class);
	public static JFrame main = null;
	
	public static Border createBorder(String title) {
		return BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(defaultBorderSize, defaultBorderSize,
													defaultBorderSize, defaultBorderSize)
				);
	}
	
	
	public static JPanel wrapInLeftRightLayout(JComponent jc) {
		JPanel jp;
		jp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp.add(jc);
		return jp;
	}

	
    public static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = PlasmaLabGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
  	      	logger.error("Couldn't find file: " + path);
            return null;
        }
    }
	
    /*public static int saveModifications(ModelInterface model) {
    	String msg = "'" + model + "' has been modified. Save changes ?";
    	return JOptionPane.showConfirmDialog(main, msg, "Save Model", JOptionPane.YES_NO_CANCEL_OPTION);
    }*/
    
    public static final int Yes = JOptionPane.YES_OPTION;
    public static final int No = JOptionPane.NO_OPTION;
    public static final int Cancel = JOptionPane.CANCEL_OPTION;
}
