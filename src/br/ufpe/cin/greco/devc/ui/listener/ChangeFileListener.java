package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import br.ufpe.cin.greco.devc.ui.Editor;
import br.ufpe.cin.greco.devc.ui.TDevCCodeEditor;

public class ChangeFileListener implements KeyListener {

private Component parent;
private TDevCCodeEditor editorWindow;
	
	public ChangeFileListener(Component parent,TDevCCodeEditor editorWindow) {
		this.parent = parent;
		this.editorWindow = editorWindow;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (editorWindow.gettDevCfile().isSaved()){
			editorWindow.setAsModified();
			//Editor.refreshProjectTree(editorWindow.gettDevCfile().getTreeNode());
			//((Editor) this.parent).terminalMsg("File changed!");
			Editor.getInstance().enableOnChangeEditor();
			
		}
	}
	
	/*@Override
	public void caretUpdate(CaretEvent arg0) {
		((Editor) this.parent).setUnsavedTitle();
	}*/

}
