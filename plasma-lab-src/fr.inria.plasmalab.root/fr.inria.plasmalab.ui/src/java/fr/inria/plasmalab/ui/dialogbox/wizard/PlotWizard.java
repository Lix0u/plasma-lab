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
package fr.inria.plasmalab.ui.dialogbox.wizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.tabs.experimentation.components.GraphicPanel;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class PlotWizard extends AbstractWizard {
	
	final static Logger logger = LoggerFactory.getLogger(PlotWizard.class);

	/**
	 * 
	 */
	//Graphic
	private static final long serialVersionUID = -6452135182644444846L;
	private static String newPlot,cancelStr, okStr;
	private JButton newButton, cancelButton, okButton;
	private JPanel utilityPanel;
	private JPanel newButtonPanel;
	List<PlotPanel> plotPanelList;
	
	//Logic
	List<ResultInterface> results;
	private GraphicPanel gPanel;
	
	public PlotWizard(PlasmaLabGUI window, GraphicPanel gPanel){
		super("Plot...", "Draw plots", null, window);
		
		int x= window.getX()+(window.getSize().width)/2-200;
		int y= window.getY()+50;
		this.setBounds(x, y, 600, 400);
		this.gPanel = gPanel;
		
		plotPanelList = new ArrayList<PlotPanel>();
		
		setResult(new ArrayList<ResultInterface>());
	}
	
	@Override
	protected void init(){
		Properties stringValues = window.getStringValues();
		newPlot = stringValues.getProperty("new_plot");
		cancelStr = stringValues.getProperty("cancel");
		okStr = stringValues.getProperty("ok");
	}

	@Override
	public JPanel createUtilityPanel() {
		utilityPanel = new JPanel();
		utilityPanel.setLayout(new BoxLayout(utilityPanel, BoxLayout.PAGE_AXIS));
		
		newButton = new JButton(newPlot);
		newButton.setActionCommand(newPlot);
		newButton.addActionListener(this);
		newButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		newButtonPanel = new JPanel();
		newButtonPanel.setLayout(new BoxLayout(newButtonPanel, BoxLayout.LINE_AXIS));
		newButtonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		newButtonPanel.add(newButton);
		newButtonPanel.add(Box.createHorizontalGlue());
		

		JScrollPane scrollPanel = new JScrollPane(utilityPanel);
		scrollPanel.setPreferredSize(new Dimension(600,100));
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scrollPanel, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		cancelButton = new JButton(cancelStr);
		cancelButton.setPreferredSize(new Dimension(85, 25));
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		okButton = new JButton(okStr);
		okButton.setPreferredSize(new Dimension(85, 25));
		okButton.setText(okStr);
		okButton.setActionCommand(okStr);
		okButton.addActionListener(this);
		buttonPanel.add(okButton);		
		return buttonPanel;
	}
	
	public void setResult(List<ResultInterface> results) {
		this.results = results;
		//Clear previous results
		plotPanelList.clear();
		addPlotPanel();
	}
	
	public void addPlotPanel(){
		PlotPanel panel = new PlotPanel(results,this);
		plotPanelList.add(panel);
		utilityPanel.removeAll();
		for(PlotPanel pp:plotPanelList)
			utilityPanel.add(pp);
		utilityPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		utilityPanel.add(newButtonPanel);
		utilityPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		utilityPanel.add(Box.createVerticalGlue());
		this.repaint();
		this.invalidate();
		this.validate();
		//this.revalidate();
	}
	
	public void remPlotPanel(PlotPanel toRemove){
		plotPanelList.remove(toRemove);
		utilityPanel.removeAll();
		for(PlotPanel pp:plotPanelList)
			utilityPanel.add(pp);
		utilityPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		utilityPanel.add(newButtonPanel);
		utilityPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		utilityPanel.add(Box.createVerticalGlue());
		this.repaint();
		this.invalidate();
		this.validate();
		//this.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(cancelStr)){
			this.setVisible(false);
		}
		else if(e.getActionCommand().equals(okStr)){
			this.setVisible(false);		
			drawPlots();
		}
		else if(e.getActionCommand().equals(newPlot)){
			this.addPlotPanel();
		}
	}

	public void drawPlots(){
		gPanel.remPlots();
		int count = 0;
		for(PlotPanel pp:plotPanelList){
			String x = pp.getXPlot();
			String y = pp.getYPlot();
			String z = pp.getZPlot();
			String cat = pp.getCategory();
			String name = pp.getNamePlot();
			Color color = pp.getColorPlot();
			
			if(!x.equals("-") && !y.equals("-") && !cat.equals("-")){
				if (name.equals("")){
					name = "plot "+count;
				}
				if(z.equals("-")){
					//2D Plot
					name+=" (X:"+x+" Y:"+y+")";
					double[][] xyData = getPlotData(cat, x, y);
					gPanel.addPlot2(name, color, xyData);
				}
				else{
					//3D Plot
					name+=" (X:"+x+" Y:"+y+" Z:"+z+")";
					double[][] xyzData = getPlotData(cat, x, y, z);
					gPanel.addPlot3(name, color, xyzData);
				}
				count ++;
			}
		}
	}
	
	/**
	 * @param category
	 * @param X
	 * @param Y
	 * @param Z
	 * @return
	 */
	public double[][] getPlotData(String category, String X, String Y, String Z) {		
		List<ResultInterface> res = getResultsFrom(category);
		//Set<String> header = res.get(0).getHeaders();
		
		ArrayList<Double> yValues = new ArrayList<Double>();
		int count = 0;
		for(ResultInterface r:res){
			count ++;
			try {
				Object value = r.getValueOf(Y);
				if(!yValues.contains(value))
					yValues.add(Double.parseDouble(value.toString()));
			} catch (PlasmaExperimentException e) {
				logger.warn("Unknown value: " + Y);
			} catch (NumberFormatException e) {
				logger.warn("Cannot parse value of: " + Y);
			}
		}
		
		int xSize = count/yValues.size();
		
		double[][] array = new double[yValues.size()+1][xSize+1];
		
		for(int i=1; i<=yValues.size();i++){
			array[i][0] = yValues.get(i-1);
		} 
		for(int i=0; i<xSize;i++){
			array[0][i+1] = Double.NaN;
			try {
				array[0][i+1] = Double.parseDouble(res.get(i*yValues.size()).getValueOf(X).toString());
			} catch (NumberFormatException e) {
				logger.warn("Cannot parse value of: " + X);
			} catch (PlasmaExperimentException e) {
				logger.warn("Unknown value: " + X);
			}
			for(int j=0; j<yValues.size();j++){
				array[j+1][i+1] = Double.NaN;
				try {
					array[j+1][i+1] = Double.parseDouble(res.get(i+j*xSize).getValueOf(Z).toString());
				} catch (NumberFormatException e) {
					logger.warn("Cannot parse value of: " + Z);
				} catch (PlasmaExperimentException e) {
					logger.warn("Unknown value: " + Z);
				}
			}
		}
		array[0][0]=0;
		
		return array;
	}	
	
	/**
	 * 
	 * @param X
	 * @param Y
	 * @return
	 */
	public double[][] getPlotData(String category, String X, String Y) {
		List<ResultInterface> res = getResultsFrom(category);
		//Set<String> header = res.get(0).getHeaders();
		
		double[][] array = new double[2][res.size()];
		for(int i=0; i< res.size(); i++){
			array[0][i] = Double.NaN;
			array[1][i] = Double.NaN;
			try {
				array[0][i]=Double.parseDouble(res.get(i).getValueOf(X).toString());
			} catch (NumberFormatException e) {
				logger.warn("Cannot parse value of: " + X);
			} catch (PlasmaExperimentException e) {
				logger.warn("Unknown value: " + X);
			}
			try {
				array[1][i]=Double.parseDouble(res.get(i).getValueOf(Y).toString());
			} catch (NumberFormatException e) {
				logger.warn("Cannot parse value of: " + Y);
			} catch (PlasmaExperimentException e) {
				logger.warn("Unknown value: " + Y);
			}
		}
		return array;
	}
	

	public List<ResultInterface> getResultsFrom(String category){
		ArrayList<ResultInterface> catList = new ArrayList<ResultInterface>();
		for(ResultInterface ri:results){
			if(ri.getCategory().equals(category))
				catList.add(ri);
		}
		return catList;
	}
}
