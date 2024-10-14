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
/**
 * 
 */
package fr.inria.plasmalab.ui.tabs.experimentation.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.dialogbox.wizard.PlotWizard;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * @author kevin.corre@inria.fr
 *
 */
public class GraphicPanel extends JPanel implements ActionListener, ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5800522003868948995L;
	private static String plot2Str, plot3Str, editPlotStr;
	private Plot2DPanel plot2;
	private Plot3DPanel plot3;
	
	private JPanel buttonPanel;
	private JButton generate;
	private PlotWizard plotWizard;
	private JComboBox plotChooser;
	private boolean is2d;
	
	private PlasmaLabGUI window;
	
	public GraphicPanel(PlasmaLabGUI window){
		this.window = window;
		Properties stringValues = window.getStringValues();
		plot2Str = stringValues.getProperty("plot2");
		plot3Str = stringValues.getProperty("plot3");
		editPlotStr = stringValues.getProperty("edit_plot");
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		plot2 = new Plot2DPanel();
		plot2.addLegend("EAST");
		//plot2.setAxisLabel(1, "Pr");
		plot3 = new Plot3DPanel();
		plot3.addLegend("EAST");
		//plot3.setAxisLabel(2, "Pr");
		add(plot2);
		is2d = true;
		
		generate = new JButton(editPlotStr);
		generate.addActionListener(this);
		generate.setEnabled(false);
		plotChooser = new JComboBox();
		plotChooser.addItem(plot2Str);
		plotChooser.addItem(plot3Str);
		plotChooser.addItemListener(this);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(plotChooser);
		buttonPanel.add(generate);
		add(buttonPanel);
	}
	
	
	public void addPlot2(String name, Color color, double[][] XY){
		plot2.addLinePlot(name, color, XY);
		plot2.addScatterPlot("", color, XY);
	}
	public void addPlot3(String name, Color color, double[][] XYZ){
		plot3.addLinePlot(name, color, XYZ);
		plot3.addGridPlot(name, color, XYZ);
		plot3.addScatterPlot(name, color, XYZ);
	}
	public void remPlots(){
		plot2.removeAllPlots();
		plot3.removeAllPlots();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(plotWizard==null){
			plotWizard = new PlotWizard(window,this);
		}
		plotWizard.setVisible(true);
	}

	public void setResult(List<ResultInterface> results) {
		if(plotWizard==null){
			plotWizard = new PlotWizard(window,this);
		}
		this.plotWizard.setResult(results);
		generate.setEnabled(true);
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(plotChooser)){
			if(plotChooser.getSelectedItem().equals(plot2Str)){
				if(!is2d){
					removeAll();
					add(plot2);
					add(buttonPanel);
					is2d=true;
				}
			}else{
				if(is2d){
					removeAll();
					add(plot3);
					add(buttonPanel);
					is2d = false;
				}
			}
			this.repaint();
			this.revalidate();
		}
	}
}
