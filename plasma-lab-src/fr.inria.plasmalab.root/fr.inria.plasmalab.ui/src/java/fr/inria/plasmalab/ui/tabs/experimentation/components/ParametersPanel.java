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
package fr.inria.plasmalab.ui.tabs.experimentation.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCObjectParameter;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.workflow.data.AbstractData;

public class ParametersPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -7431822583054435482L;
	private Map<SMCParameter, Component> componentMap;
	//Key depends from second
	private Map<SMCParameter, SMCParameter> dependanceMap;
	private GridBagConstraints gbc;
	
	public ParametersPanel(String description, List<SMCParameter> paramList){
		super();
		dependanceMap = new HashMap<SMCParameter, SMCParameter>();
		componentMap = new HashMap<SMCParameter, Component>();
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx=0;
		gbc.insets = new Insets(6,6,0,0); 
		
		//Draw description
		gbc.gridx=0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;		
		JTextArea text = new JTextArea(description,0,25);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBackground(new Color(238,238,238));
        this.add(text,gbc);
		gbc.gridy+=1;
		
		for(SMCParameter param:paramList){
			configureParameter(param);
		}
		
		//Init
		actionPerformed(null);
	}
	
	
	public void configureParameter(SMCParameter param){
		//Alternatives
		if(param instanceof SMCAlternatives){
			configureAlternatives((SMCAlternatives) param);
		}
		else if(param instanceof SMCOption){
			configureOption((SMCOption)param);
		}
		else if(param instanceof SMCObjectParameter){
			//Draw name
			gbc.gridx=0;
			gbc.gridwidth=1;
			gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
			this.add(new JLabel(param.getName()+": "), gbc);
			//Draw component
			SMCParametersModelCBox box = new SMCParametersModelCBox(param);
			gbc.gridx=1;
			gbc.gridwidth=2;
			gbc.anchor = GridBagConstraints.BASELINE_LEADING;
			this.add(box,gbc);
			gbc.gridy+=1;
			//Link component in hashmap
			componentMap.put(param, box);
		}
		//Default behavior
		else{
			gbc.gridx=0;
			gbc.gridwidth=1;
			gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
			if(param.isBoolean()){
				SMCParameterCheckBox cb = new SMCParameterCheckBox(param);
				this.add(cb,gbc);
				gbc.gridy+=1;
				//Link component in hashmap
				componentMap.put(param, cb);
			}
			else{
				//Draw name
				this.add(new JLabel(param.getName()+": "), gbc);
				//Draw component
				JTextField tf = new JTextField(10);
				tf.setToolTipText(param.getTip());
				gbc.gridx=1;
				gbc.gridwidth=2;
				gbc.anchor = GridBagConstraints.BASELINE_LEADING;
				this.add(tf,gbc);
				gbc.gridy+=1;
				//Link component in hashmap
				componentMap.put(param, tf);
			}
		}
	}
	
	private void configureOption(SMCOption option){
		SMCParameterCheckBox cb = new SMCParameterCheckBox(option);
		gbc.gridx=0;
		gbc.gridwidth=1;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		this.add(cb,gbc);
		gbc.gridy+=1;
		//Link component in hashmap
		componentMap.put(option, cb);
		//Options
		cb.addActionListener(this);
		for(SMCParameter paramSon:option.getOptions()){
			configureParameter(paramSon);
			dependanceMap.put(option, paramSon);
			setEnabled(paramSon, false);
		}
	}
	
	private void configureAlternatives(SMCAlternatives parent){
		List<SMCParameter> alternatives = parent.getAlternatives();
		gbc.gridx=0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		JRadioButton radio = new SMCParameterRadioButton(parent);
		radio.addActionListener(this);
		componentMap.put(parent, radio);
		this.add(radio, gbc);
		gbc.gridy+=1;
		
		for(SMCParameter alt:alternatives){
			configureParameter(alt);
			dependanceMap.put(alt, parent);
		}
		if(parent.getNext() != null)
			configureAlternatives(parent.getNext());
		radio.doClick();
	}
	
	
	public HashMap<String, Object> getParametersValues(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		for(SMCParameter param:componentMap.keySet()){
			boolean enabled = componentMap.get(param).isEnabled();
			
			if(enabled){
				Component comp = componentMap.get(param);
				if(param.isBoolean()){
					if(comp instanceof JCheckBox)
						map.put(param.getName(), ((JCheckBox)comp).isSelected());
					else if(comp instanceof JRadioButton)
						map.put(param.getName(), ((JRadioButton)comp).isSelected());
				}else{
					if(comp instanceof SMCParametersModelCBox){
						map.put(param.getName(), ((SMCParametersModelCBox)comp).getSelectedItem());
					}else if(comp instanceof JTextField){
						map.put(param.getName(), ((JTextField)comp).getText());
					}
				}
			}
		}
		return map;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e != null){
			if(e.getSource() instanceof SMCParameterCheckBox){
				SMCParameterCheckBox source = (SMCParameterCheckBox)e.getSource();
				SMCParameter param = source.getParameter();
				if(param instanceof SMCOption){
					for(SMCParameter sons:((SMCOption)param).getOptions()){
						setEnabled(sons, source.isSelected());
					}
				}
			}else if(e.getSource() instanceof SMCParameterRadioButton){
				SMCParameterRadioButton source = (SMCParameterRadioButton) e.getSource();
				SMCAlternatives param = (SMCAlternatives) source.getParameter();
				SMCAlternatives next = param.getHead();
				do{
					setEnabled(next, next==param);
					next = next.getNext();
				}while(next != null);
			}
		}
	}
	
	private void setEnabled(SMCParameter param, boolean enabled){
		Component comp = componentMap.get(param);
		if(comp!=null){
			if(param instanceof SMCAlternatives)
				((SMCParameterRadioButton)comp).setSelected(enabled);
			else
				comp.setEnabled(enabled);
		}
		if(param instanceof SMCOption){
			for(SMCParameter opt:((SMCOption)param).getOptions())
				setEnabled(opt, enabled);
		}
		else if(param instanceof SMCAlternatives){
			for(SMCParameter alt:((SMCAlternatives)param).getAlternatives())
				setEnabled(alt, enabled);
		}
	}
	
	public void updateModelList(List<AbstractData> list){
		for(Component comp:componentMap.values()){
			if(comp instanceof SMCParametersModelCBox){
				((SMCParametersModelCBox)comp).removeAllItems();
				for(AbstractData m:list)
					((SMCParametersModelCBox)comp).addItem(m);
			}
		}
	}
	
	
	class SMCParameterRadioButton extends JRadioButton{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1538808808216710433L;
		private SMCParameter parameter;
		
		public SMCParameterRadioButton(SMCParameter parameter){
			super(parameter.getName(),false);
			this.parameter = parameter;
			this.setToolTipText(parameter.getTip());
		}
		
		public SMCParameter getParameter(){
			return parameter;
		}
	}
	
	class SMCParameterCheckBox extends JCheckBox{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7978375896197894143L;
		private SMCParameter parameter;
		
		public SMCParameterCheckBox(SMCParameter parameter){
			super(parameter.getName(),false);
			this.parameter = parameter;
			this.setToolTipText(parameter.getTip());
		}
		
		public SMCParameter getParameter(){
			return parameter;
		}
	}
	
	class SMCParametersModelCBox extends JComboBox{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6764581561472325258L;
		private SMCParameter parameter;
		
		public SMCParametersModelCBox(SMCParameter parameter){
			this.parameter = parameter;
			this.setToolTipText(parameter.getTip());
		}
		
		public SMCParameter getParameter(){
			return parameter;
		}
	}
	
}
