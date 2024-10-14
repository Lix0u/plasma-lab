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
package fr.inria.plasmalab.ui.tabs.edition;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import fr.inria.plasmalab.ui.controler.ProjectControler;
import fr.inria.plasmalab.ui.tabs.edition.observer.DataObserver;
import fr.inria.plasmalab.workflow.data.AbstractData;

public class EditionPanel extends JPanel implements DocumentListener, FocusListener, DataObserver{
	private static final long serialVersionUID = 472690844721069373L;
	// graphical components
	private JTextArea textArea;	

	/* Controler and Logical*/
	private ProjectControler pControler;
	private boolean updateLock;
	private AbstractData data;

	@SuppressWarnings("serial")
	public EditionPanel(ProjectControler pControler, AbstractData data) {
		updateLock=true;
	
		textArea = new JTextArea();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=1;
		c.gridheight=1;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=1;
		JScrollPane scrollArea = new JScrollPane(textArea);
		scrollArea.setLayout(new ScrollPaneLayout());
		scrollArea.setBackground(Color.WHITE);
		this.add(scrollArea,c);
		textArea.setText(data.getContent());
		this.pControler = pControler;
		this.data = data;
		textArea.getDocument().addDocumentListener(this);
		textArea.addCaretListener(pControler);
		textArea.addFocusListener(this);
		
		//TODO Add Observer
		
		final UndoManager undo = new UndoManager();
	    Document doc = textArea.getDocument();
	    
	    // Listen for undo and redo events
	    doc.addUndoableEditListener(new UndoableEditListener() {
	        public void undoableEditHappened(UndoableEditEvent evt) {
	            undo.addEdit(evt.getEdit());
	        }
	    });
	    
	    // Create an undo action and add it to the text component
	    textArea.getActionMap().put("Undo",
	        new AbstractAction("Undo") {
	            public void actionPerformed(ActionEvent evt) {
	                try {
	                    if (undo.canUndo()) {
	                        undo.undo();
	                    }
	                } catch (CannotUndoException e) {
	                }
	            }
	       });
	    
	    // Bind the undo action to ctl-Z
	    textArea.getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, 
		        java.awt.Event.CTRL_MASK), "Undo");
	    
	    // Create a redo action and add it to the text component
	    textArea.getActionMap().put("Redo",
	        new AbstractAction("Redo") {
	            public void actionPerformed(ActionEvent evt) {
	                try {
	                    if (undo.canRedo()) {
	                        undo.redo();
	                    }
	                } catch (CannotRedoException e) {
	                }
	            }
	        });
	    
	    // Bind the redo action to ctl-Y
	    textArea.getInputMap().put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, 
		        java.awt.Event.CTRL_MASK+java.awt.Event.SHIFT_MASK), "Redo");
	}
	
	public void close(){
		//TODO Remove Observer
	}
	
	public void replaceModel(AbstractData newData){
		//TODO Rem Observer on current data
		//TODO Add Observer on new data
		this.data = newData;
		//Update view
		updateDataSignal(data);
	}
		
	@Override
	public String getName(){
		try{
		return data.getName();
		}catch(NullPointerException e){
			return "";
		}
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		System.out.println("updated Model not taken into account");
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		updateDoc();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		updateDoc();
	}
	
	public void updateDoc(){
		if(updateLock){
			updateLock = false;
			pControler.updateData(textArea.getText(), data);
			//TODO update error unless there is a list of compiled error handled by pControler
			updateLock = true;
		}
	}

	public boolean modelIsFocusOwner(){
		return textArea.isFocusOwner();
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource().equals(textArea)){
			//TODO update error unless there is a list of compiled errors
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// Nothing to do
	}

	@Override
	public void updateDataSignal(AbstractData Data) {
		updateLock=false;
		//TODO update name?
		textArea.setText(data.getContent());
		updateLock=true;
	}

	@Override
	public void renamed(String old, AbstractData Data) {
		// TODO Auto-generated method stub
		
	}

	public AbstractData getData() {
		return data;
	}
	
	public JTextArea getTextArea(){
		return textArea;
	}
}
