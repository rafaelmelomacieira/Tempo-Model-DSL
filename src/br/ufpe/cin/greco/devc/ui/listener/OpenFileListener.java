package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import br.ufpe.cin.greco.devc.ui.Editor;

public class OpenFileListener implements ActionListener {

private Component parent;
	
	public OpenFileListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		TDevcFileFilter filter = new TDevcFileFilter();
		chooser.setCurrentDirectory(new File("devc_samples/"));
	    chooser.setFileFilter(filter);
		chooser.showOpenDialog(parent);
		File f = chooser.getSelectedFile();
		if (chooser.accept(f)){
			//((Editor) this.parent).setDevCFile(f);
			((Editor) this.parent).enableOnOpenFile();
			((Editor) this.parent).terminalMsg("File " + chooser.getSelectedFile().getName() + " opened successfully");
		}
	}
}
