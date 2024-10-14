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
package fr.inria.plasmalab.ui.tabs.experimentation.components.jtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class LightCellRenderer extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856728104835478482L;
	private double pr=0;

	public LightCellRenderer() {
        setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
    	if(value == null){
    		setPr(0.);
    	}
    	else if(value instanceof Double)
    		setPr((Double)value);
    	else if(value instanceof Boolean){
    		if((Boolean) value){
    			setPr(1.0);
    		}else{
    			setPr(0.);
    		}
    	}
    	return this;
    }
    
    public void paintComponent (Graphics g) {
		super.paintComponents(g);
	    Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (Double.isNaN(pr) || (pr >= 0.0 && pr <= 1.0)) {
			// fill Ellipse2D.Double
			g2.setPaint(computeColor());
			g2.fill (new Ellipse2D.Double(3, 3, 20, 20));
		}
		else {
			// show value
			g2.setPaint(Color.BLACK);
			DecimalFormat df = new DecimalFormat("#.##");
			g2.drawString(df.format(pr), 5, 18);
		}
	}
    
	
	public void setPr(double pr){
		this.pr=pr;
		this.repaint();
	}
	
	private Color computeColor(){
		int r,g,b;
		if(pr<=0.5){
			r = (int) (160 + 95*(pr*2));
			g = (int) (215*(pr*2));
			b=0;
		}else if(pr<=1){
			r = (int) (255-200*((pr-0.5)*2));
			g = (int) (215-40*((pr-0.5)*2));
			b = (int) (50*(pr*2));
		}else{
			r = g = b = 180;
		}
		return new Color(r, g, b);
	}

}
