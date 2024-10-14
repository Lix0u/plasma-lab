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
package fr.inria.plasmalab.ui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.dialogbox.wizard.GenericWizards;
import fr.inria.plasmalab.ui.dialogbox.wizard.ImportWizard;
import fr.inria.plasmalab.ui.dialogbox.wizard.NewWizard;
import fr.inria.plasmalab.ui.tabs.edition.ProjectPanel;
import fr.inria.plasmalab.workflow.data.AbstractData;

public class IconBar extends JToolBar implements ActionListener{

	private final static ImageIcon newIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/newIcon_24.png"));
	private final static ImageIcon importIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/importIcon_24.png"));
	private final static ImageIcon exportIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/exportIcon_24.png"));
	private final static ImageIcon expIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/expIcon_24.png"));
	private final static ImageIcon simuIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/simIcon_24.png"));
	private final static ImageIcon optIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/optIcon_24.png"));
	private final static ImageIcon openIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/openIcon_24.png"));
	private final static ImageIcon saveIcon = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/saveIcon_24.png"));
	
	private final static ImageIcon newIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/newIcon_24_h.png"));
	private final static ImageIcon importIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/importIcon_24_h.png"));
	private final static ImageIcon exportIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/exportIcon_24_h.png"));
	private final static ImageIcon expIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/expIcon_24_h.png"));
	private final static ImageIcon simuIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/simIcon_24_h.png"));
	private final static ImageIcon optIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/optIcon_24_h.png"));
	private final static ImageIcon openIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/openIcon_24_h.png"));
	private final static ImageIcon saveIcon_h = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/saveIcon_24_h.png"));
	
	private static final Icon CLOSE_0 = new ImageIcon(Thread.currentThread()
			.getContextClassLoader().getResource("images/icons/close0.png"));
	  private static final Icon CLOSE_1 = new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("images/icons/close1.png"));
	
	private final static String exp = "Experimentation", sim="Simulation", opt="Optimization",
			newStr="New", importStr="Import", exportStr="Export", openStr="Open", saveStr="Save",
			closeStr="close";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7318887419124054794L;
	
	private PlasmaLabGUI owner;
	
	public IconBar(PlasmaLabGUI owner){
		super(JToolBar.HORIZONTAL);
		setFloatable(false);
		this.owner = owner;
		addSeparator();
		JButton button = new JButton(newIcon);
		button.setRolloverIcon(newIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(newStr);
		button.setActionCommand(newStr);
		button.addActionListener(this);
		//button.setEnabled(false);
		button.setFocusable(false);
		add(button);
		addSeparator();
		button = new JButton(openIcon);
		button.setRolloverIcon(openIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(openStr);
		button.setActionCommand(openStr);
		button.addActionListener(this);
		//button.setEnabled(false);
		button.setFocusable(false);
		add(button);
		button = new JButton(saveIcon);
		button.setRolloverIcon(saveIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(saveStr);
		button.setActionCommand(saveStr);
		button.addActionListener(this);
		//button.setEnabled(false);
		button.setFocusable(false);
		add(button);
		button = new JButton(importIcon);
		button.setRolloverIcon(importIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(importStr);
		button.setActionCommand(importStr);
		button.addActionListener(this);
		//button.setEnabled(false);
		button.setFocusable(false);
		add(button);
		button = new JButton(exportIcon);
		button.setRolloverIcon(exportIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(exportStr);
		button.setActionCommand(exportStr);
		button.addActionListener(this);
		//button.setEnabled(false);
		button.setFocusable(false);
		add(button);
		addSeparator();
		button = new JButton(simuIcon);
		button.setRolloverIcon(simuIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(sim);
		button.setActionCommand(sim);
		button.addActionListener(this);
		button.setFocusable(false);
		add(button);
		button = new JButton(expIcon);
		button.setRolloverIcon(expIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(exp);
		button.setActionCommand(exp);
		button.addActionListener(this);
		button.setFocusable(false);
		add(button);
		button = new JButton(optIcon);
		button.setRolloverIcon(optIcon_h);
	    button.setBorder(null);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setToolTipText(opt);
		button.setActionCommand(opt);
		button.addActionListener(this);
		button.setFocusable(false);
		add(button);
		
		this.add(Box.createHorizontalGlue());

		 // Create a JButton for the close tab button
	    JButton btnClose = new JButton();
	 
	    // Configure icon and rollover icon for button
	    btnClose.setRolloverIcon(CLOSE_1);
	    btnClose.setIcon(CLOSE_0);
	    btnClose.setActionCommand(closeStr);
	    btnClose.setRolloverEnabled(true);
	 
	    // Set border null so the button doesn't make the tab too big
	    btnClose.setBorder(null);
		btnClose.setBorderPainted(false);
	    btnClose.setOpaque(false);
	    // Make sure the button can't get focus, otherwise it looks funny
	    btnClose.setFocusable(false);
	    btnClose.setContentAreaFilled(false);
	 
	    // Put the panel together
	    btnClose.setBorder(new EmptyBorder(0, 0, 0, 0));
	    btnClose.addActionListener(this);
		
	    this.add(btnClose);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(newStr)){
			NewWizard.showWizard(owner);
		}else if(e.getActionCommand().equals(openStr)){
			GenericWizards.openProject(owner);
		}else if(e.getActionCommand().equals(saveStr)){
			ProjectControler pc = owner.getActiveProjectControler();
			if(pc!= null)
				pc.saveProject();
		}else if(e.getActionCommand().equals(importStr)){
			ImportWizard.showWizard(owner);
		}else if(e.getActionCommand().equals(exportStr)){
			ProjectControler pc = owner.getActiveProjectControler();
			if(pc!= null)
				pc.exportData();
		}else if(e.getActionCommand().equals(exp)){
			owner.setFocusToExperiment();
		}else if(e.getActionCommand().equals(sim)){
			owner.setFocusToSimulation();
		}else if(e.getActionCommand().equals(opt)){
			owner.setFocusToOptimization();
		}else if(e.getActionCommand().equals(closeStr)){
			ProjectControler pc = owner.getActiveProjectControler();
			if(pc!=null){
				ProjectPanel pp = pc.getpPanel();
				AbstractData data = pp.getActiveDataPanel().getData();
				pc.closeData(data, pc.getProject());
			}
		}
	}

}
