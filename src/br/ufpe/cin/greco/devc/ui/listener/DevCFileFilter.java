package br.ufpe.cin.greco.devc.ui.listener;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class DevCFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.getName().toUpperCase().endsWith(".DEVC") ||
				f.getName().toUpperCase().endsWith(".TDEVC") ||
				f.getName().toUpperCase().endsWith(".TERMINUS") ||
				f.isDirectory()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
