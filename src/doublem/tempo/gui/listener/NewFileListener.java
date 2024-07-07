package doublem.tempo.gui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import doublem.tempo.gui.Editor;

public class NewFileListener implements ActionListener {

private Component parent;
	
	public NewFileListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//((Editor) this.parent).setDevCFile(null);
	}

}
