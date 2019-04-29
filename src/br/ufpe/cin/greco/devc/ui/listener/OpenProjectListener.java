package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import br.ufpe.cin.greco.devc.ui.Editor;

public class OpenProjectListener implements ActionListener {
	
	private Component parent;
	
	public OpenProjectListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		TerminusProjectFileFilter filter = new TerminusProjectFileFilter();
		chooser.setCurrentDirectory(new File("devc_samples/"));
	    chooser.setFileFilter(filter);
		chooser.showOpenDialog(parent);
		File f = chooser.getSelectedFile();
		if (chooser.accept(f)){
			((Editor) this.parent).openProject(f);
			((Editor) this.parent).enableOnOpenFile();
		}
	}
}
