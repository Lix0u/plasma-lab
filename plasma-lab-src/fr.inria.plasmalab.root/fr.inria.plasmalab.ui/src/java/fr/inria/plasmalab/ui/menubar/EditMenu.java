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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import fr.inria.plasmalab.ui.PlasmaLabGUI;

public class EditMenu extends JMenu {

	private static final long serialVersionUID = -7372248450797941661L;
	private static final String undoStr="undo", redoStr="redo";
	
	private JMenuItem undo, redo;
	
	private void createUndoItem(){
		undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		this.add(undo);
		redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK+InputEvent.SHIFT_DOWN_MASK));
		this.add(redo);
	}
	
	private JMenuItem createCutItem() {
		JMenuItem item;
		
		item = new JMenuItem(new DefaultEditorKit.CutAction());
        item.setText("Cut");
        item.setMnemonic(KeyEvent.VK_T);
        return item;
	}
	
	private JMenuItem createCopyItem() {
		JMenuItem item;
		
		item = new JMenuItem(new DefaultEditorKit.CopyAction());
        item.setText("Copy");
        item.setMnemonic(KeyEvent.VK_C);
        return item;
	}
	
	private JMenuItem createPasteItem() {
		JMenuItem item;
		
		item = new JMenuItem(new DefaultEditorKit.PasteAction());
        item.setText("Paste");
        item.setMnemonic(KeyEvent.VK_P);
        return item;
	}
	
	
	public EditMenu(PlasmaLabGUI main) {
		super("Edit");
		this.setMnemonic('E');
		this.setEnabled(false);
		//TODO
		/*createUndoItem();
		undo.addActionListener(main.getFileControler());
		undo.setActionCommand(undoStr);
		redo.addActionListener(main.getFileControler());
		redo.setActionCommand(redoStr);*/
		this.addSeparator();
		this.add(createCutItem());
		this.add(createCopyItem());
		this.add(createPasteItem());
		this.addSeparator();
		JMenuItem preferences = new JMenuItem("Preferences", null); 
		preferences.setEnabled(false);
		this.add(preferences);
	}
}

