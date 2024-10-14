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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import fr.inria.plasmalab.ui.Common;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.VariableType;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;


public class OptimizationComponent extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3619026892496287876L;
	private final static String title = "Optimization variables : ";
	private final static String minS = "min : ", maxS ="max : ", incS="inc : ";
	private final static String both = "True & False";
	
	private JTree jtree;
	DefaultMutableTreeNode rootNode;
	DefaultTreeModel treeModel;
	//private JButton plusButton, minusButton;

	public OptimizationComponent(){
		super();
		jtree = new JTree();
		this.setLayout(new BorderLayout());
		this.add(jtree,BorderLayout.CENTER);
		init();
		// Workaround
		// This thing won't even work correctly but solve the bug 
		// where the VarPanel take double the size it should take.
		setPreferredSize(new Dimension(100,150));
	}
		
	private void init() {
		setBorder(Common.createBorder(title));
		jtree.setVisibleRowCount(50);
		jtree.setRootVisible(false);
		jtree.setShowsRootHandles(true);
		JScrollPane scPane = new JScrollPane();
		scPane.setViewportView(jtree);
		rootNode = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(rootNode);
		jtree.setModel(treeModel);
		
		DualCellTreeRenderer dctr = new DualCellTreeRenderer();
		DualLeafCellEditor dlce = new DualLeafCellEditor(jtree, dctr);
		jtree.setCellRenderer(dctr);
		jtree.setCellEditor(dlce);		
		jtree.setEditable(true);
		
		/*this.setLayout(new BorderLayout());
		Icon icon = new ImageIcon(Thread.currentThread()
				.getContextClassLoader()
				.getResource("images/plusIcon16.png"));
		plusButton = new JButton("", icon);
		plusButton.setPreferredSize(new Dimension(16,16));
		icon = new ImageIcon(Thread.currentThread()
				.getContextClassLoader()
				.getResource("images/minusIcon16.png"));
		minusButton = new JButton("", icon);
		minusButton.setPreferredSize(new Dimension(16,16));*/
		
		this.add(scPane,BorderLayout.CENTER);
		/*JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.add(plusButton);
		buttonPanel.add(minusButton);
		this.add(buttonPanel,BorderLayout.SOUTH);
		plusButton.addActionListener(this);
		minusButton.addActionListener(this);*/
	}
	
	public void setOptimizationReq(AbstractModel model, List<AbstractRequirement> requirement){
		clear();
		//Set optimization variables
		if(model.getOptimizationVariables()!=null)
		for(Variable var:model.getOptimizationVariables()){
			addVar(var);
		}
		// TODO test if multiple instance of the same variable are declared for optimization
		for(AbstractRequirement req:requirement){
			//Set optimization variables
			for(Variable var:req.getOptimizationVariables()){
				addVar(var);
			}
		}
	}
	
	public void addVar(Variable ident){
		// Building the new node
		DefaultMutableTreeNode varNode;
		if(!ident.typeIsBool()){
			VarParamNode minNode = new VarParamNode(minS, ident.getMin().toString(), ident, this);
			VarParamNode maxNode = new VarParamNode(maxS, ident.getMax().toString(), ident, this);
			VarParamNode incNode = new VarParamNode(incS, ident.getInc().toString(), ident, this);
			varNode = new VarNameNode(ident, this, minNode, maxNode, incNode);
		} else {
			VarBooleanNode boolNode = new VarBooleanNode(null, ident, this);
			varNode = new VarNameNode(ident, this, boolNode);
		}

		// Thus, we insert it into the existing TreeModel
	    treeModel.insertNodeInto(varNode, rootNode, rootNode.getChildCount());
    	jtree.scrollPathToVisible(new TreePath(varNode.getPath()));
		
	}
	/**
	 * Clear all optimization variables from the panel.
	 */
	public void clear(){
		jtree.setSelectionPath(null);
		while(rootNode.children().hasMoreElements()){
			DefaultMutableTreeNode toRemoveNode = (DefaultMutableTreeNode) rootNode.children().nextElement();
					treeModel.removeNodeFromParent(toRemoveNode);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*if(e.getSource().equals(plusButton)){
			//Add a new Variable
			projectControler.newVariable();
		}
		else if(e.getSource().equals(minusButton)){
			//Remove selected Variable
			projectControler.remVariable(selectedVar);
		}*/
	}

	private DefaultMutableTreeNode getChildNode(DefaultMutableTreeNode parent, Object target) {
		DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getFirstChild(); 
		
		while (child != null) {
			if (child.getUserObject() == target) return child; 
			child = (DefaultMutableTreeNode) parent.getChildAfter(child);
		}
		return null;
	}
    
    public OptimVariables getOptimVariables() {
    	ArrayList<Variable> optimVariables = new ArrayList<Variable>();
    	
    	for(int i=0; i<rootNode.getChildCount();i++){
    		optimVariables.add(((VarNameNode) rootNode.getChildAt(i)).getVariable());
    	}	
		
		return new OptimVariables(optimVariables);
    }
	
	interface VarNode{
		public JComponent getAsJComponent();
	}
	
	class VarParamNode extends DefaultMutableTreeNode implements VarNode{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 6638191290142525504L;
		private Variable ident;
		private String param;
	    private String value;
	        
	    private JTextField field;
	    private JLabel label;
	    private JPanel jp;
	    
	    private OptimizationComponent owner;
	        
	    public VarParamNode (String param, String value, Variable ident, OptimizationComponent owner){
	    	this.owner = owner;
	    	Dimension dim;
	        label = new JLabel ();
	        dim = label.getPreferredSize();
	        label.setPreferredSize(new Dimension(dim.width+50,dim.height));
	        label.setText(param);
	        field = new JTextField ();
	        dim = field.getPreferredSize();
	        field.setPreferredSize(new Dimension(dim.width+80,dim.height));
	        field.setText(value);
	        //field.setBorder(null);
	        jp = new JPanel ();
	        jp.setLayout (new BoxLayout (jp, BoxLayout.LINE_AXIS));
	        jp.add (label);
	        jp.add (field);
	        jp.setBackground(Color.WHITE);
	        this.ident = ident;
	    }

	    public String toString (){
	        return param + " : " + value;
	    }
	    
	    @Override
	    public JComponent getAsJComponent (){
	        return jp;
	    }

		public double getValue() {
			return Double.parseDouble(field.getText());
		}
	} 
	
	class VarBooleanNode extends DefaultMutableTreeNode implements VarNode{

		/**
		 * 
		 */
		private static final long serialVersionUID = 6638191290142525504L;
		private JComboBox box;
	    private JPanel jp;
	    
	    public VarBooleanNode (Boolean value, Variable ident ,OptimizationComponent owner){
	    	jp = new JPanel ();
	        jp.setLayout (new BoxLayout (jp, BoxLayout.LINE_AXIS));
	        jp.setBackground(Color.WHITE);
	        
	        box = new JComboBox (new Object[]{true,false,both});
	        if(value == null){
	        	box.setSelectedIndex(2);
	        }else if(value){
	        	box.setSelectedIndex(0);
	        }else{
	        	box.setSelectedIndex(1);
	        }
	        jp.add(box);
	    }

	    /*public String toString (){
	        return "VarTypeNode toString()";
	    }*/
	    @Override
	    public JComponent getAsJComponent (){
	        return jp;
	    }		
	    
	    public int getValue(){
	    	return box.getSelectedIndex();
	    }
	}
	
	class VarNameNode extends DefaultMutableTreeNode implements VarNode{

		/**
		 * 
		 */
		private static final long serialVersionUID = 6638191290142525504L;
		private Variable ident;
		private JLabel label;
	    private JPanel jp;
	    
	    private VarParamNode minNode, maxNode, incrNode;
	    private VarBooleanNode boolNode;
	    
	    private OptimizationComponent owner;

	    public VarNameNode (Variable ident, OptimizationComponent owner,
	    		VarParamNode minNode, VarParamNode maxNode, VarParamNode incrNode){
	    	this.owner = owner;
	        jp = new JPanel ();
	        jp.setLayout (new BoxLayout (jp, BoxLayout.LINE_AXIS));
	        label = new JLabel(ident.getName());
	        label.setOpaque(false);
	        jp.add(label);
	        jp.setBackground(Color.WHITE);
	        this.ident = ident;
	        
	        this.minNode = minNode;
	        this.maxNode = maxNode;
	        this.incrNode = incrNode;

	        add(minNode);
	        add(maxNode);
	        add(incrNode);
	    }
	    public VarNameNode (Variable ident, OptimizationComponent owner,
	    		VarBooleanNode boolNode){
	    	this.owner = owner;
	        jp = new JPanel ();
	        jp.setLayout (new BoxLayout (jp, BoxLayout.LINE_AXIS));
	        label = new JLabel(ident.getName());
	        label.setOpaque(false);
	        jp.add(label);
	        jp.setBackground(Color.WHITE);
	        this.ident = ident;
	        
	        this.boolNode = boolNode;

	        add(boolNode);
	    }

	    /*public String toString (){
	        return "VarTypeNode toString()";
	    }*/
	    @Override
	    public JComponent getAsJComponent (){
	        return jp;
	    }
	    
	    public Variable getVariable(){
	    	if(boolNode==null){
	    		double min = minNode.getValue();
	    		double max = maxNode.getValue();
	    		double incr = incrNode.getValue();
		    	return new Variable(label.getText(), min, max, incr, VariableType.DOUBLE);
	    	}else{
		    	switch(boolNode.getValue()){
		    	case 0: 
		    		return new Variable(label.getText(), 1, 1, 0, VariableType.BOOL);
		    	case 1: 
			    	return new Variable(label.getText(), 0, 0, 0, VariableType.BOOL);
		    	default: 
			    	return new Variable(label.getText(), 0, 1, 0, VariableType.BOOL);
		    	}
	    	}
	    }
	}
	
	class DualCellTreeRenderer extends DefaultTreeCellRenderer{
        /**
		 * 
		 */
		private static final long serialVersionUID = -1090897863122919077L;

		public JComponent getTreeCellRendererComponent (JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) 
        {     
            if (value instanceof VarParamNode)
            {
                return ((VarParamNode)value).getAsJComponent ();
            }
            else if(value instanceof VarBooleanNode){
            	return ((VarBooleanNode)value).getAsJComponent();
            }
            else if(value instanceof VarNameNode){
            	return ((VarNameNode)value).getAsJComponent();
            }
            return new JLabel (value.toString());
        }
    } 
	
	class DualLeafCellEditor extends DefaultTreeCellEditor 
    {
        private VarNode node;
            
        public DualLeafCellEditor (JTree jtree, DefaultTreeCellRenderer renderer) 
        {
            super (jtree, renderer);
        }
        
        public Component getTreeCellEditorComponent (JTree tree,
                            Object value,
                            boolean isSelected,
                            boolean expanded,
                            boolean leaf,
                            int row)
        {           
            if (value instanceof VarParamNode)
            {
                node = (VarParamNode) value;
                return node.getAsJComponent ();
            }
            if(value instanceof VarBooleanNode){
            	node = (VarBooleanNode) value;
            	return node.getAsJComponent();
            }
            if(value instanceof VarNameNode){
            	node = (VarNameNode) value;
            	return node.getAsJComponent();
            }
            return new JLabel (value.toString());
        }        
        public boolean isCellEditable (java.util.EventObject eo) 
        {
            return true;
        }
    }
}
