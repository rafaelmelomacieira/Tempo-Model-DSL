package br.ufpe.cin.greco.devc.ui;

import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.tree.DefaultMutableTreeNode;

public class TDevCCodeEditor extends JInternalFrame {
	
	private TDevCFile tDevCfile;
	private DefaultMutableTreeNode node;
	private JEditorPane codeEditor;
	
	public TDevCCodeEditor(TDevCFile file,JEditorPane codeEditor) {
		super(file.getName());
		this.tDevCfile = file;
		this.codeEditor = codeEditor;
		this.setResizable(true);
		this.setMaximizable(true);
		this.setTitle(file.getName());
	}
	
	public void setAsModified(){
		this.setUnsavedTitle();
		this.tDevCfile.setModified();
	}
	
	public void setUnsavedTitle(){
		this.setTitle("*" + tDevCfile.getName());
	}

	public void setSavedTitle(){
		this.setTitle(tDevCfile.getName());
	}

	public TDevCFile gettDevCfile() {
		return tDevCfile;
	}

	public JEditorPane getCodeEditor() {
		return codeEditor;
	}
	
	

}
