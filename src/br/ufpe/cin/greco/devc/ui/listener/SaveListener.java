package br.ufpe.cin.greco.devc.ui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.plaf.FileChooserUI;

import br.ufpe.cin.greco.devc.ui.Editor;
import br.ufpe.cin.greco.devc.ui.TDevCCodeEditor;

public class SaveListener implements ActionListener {

	private Component parent;
	private TDevCCodeEditor editorWindow;

	public SaveListener(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*try {
			BufferedWriter bw;
			if (((Editor) this.parent).getCurrentDevCFile() != null){
				bw = new BufferedWriter(new FileWriter(((Editor) this.parent).getCurrentDevCFile()));
				bw.write(((Editor) this.parent).getcodeEditorContent());
				bw.flush();
				((Editor) this.parent).setSavedTitle();
				((Editor) this.parent).enableOnOpenFile();
			} else {
				File tempFile;
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(parent);
				tempFile = chooser.getSelectedFile();
				if (tempFile != null){
					if (tempFile.exists()){
						System.out.println("Ja existe!");
					}else{
						tempFile.createNewFile();
					}
					//((Editor) this.parent).setDevCFile(tempFile);
					bw = new BufferedWriter(new FileWriter(tempFile));
					bw.write(((Editor) this.parent).getcodeEditorContent());
					bw.flush();
				}
			}
			((Editor) this.parent).terminalMsg("File Saved!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	*/
		Editor.getInstance().saveProject();
	}

}
