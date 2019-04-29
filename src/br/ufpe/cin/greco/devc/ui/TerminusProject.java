package br.ufpe.cin.greco.devc.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import br.ufpe.cin.greco.devc.languageStructure.devc.parser;

public class TerminusProject {
	private HashSet<TDevCFile> tDevCFiles = new HashSet<TDevCFile>();
	private String name;
	private File projectfile;
	private Integer busWidth = 0;
	private DefaultMutableTreeNode rootElem;
	
	
	public TerminusProject(File file) {
		Properties prop = new Properties();
		this.projectfile = file;
		Integer TDevCfiles = 0;
		try {
			prop.load(new FileInputStream(file));
			if (prop.containsKey("name")) {
				this.name = prop.getProperty("name");
			}else{return;}
			if (prop.containsKey("numofdevcfiles")) {
				TDevCfiles = new Integer(prop.getProperty("numofdevcfiles"));
			}else{}
			for (int i = 0; i < TDevCfiles; i++) {
				//System.out.println(prop.getProperty("devcfile." + i));
				/*DefaultTreeCellRenderer a = new DefaultTreeCellRenderer());
				a.setLeafIcon(new ImageIcon("imgs/rodad.png"));
			    rootElem.setCellRenderer(a);*/
				this.addTDevCFile(new TDevCFile(new File(file.getParent() + file.separator + prop.getProperty("devcfile." + i) + ".tdevc")));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//rootElem = new DefaultMutableTreeNode(this.name,true);
	}
	
	public String getName() {
		return name;
	}

	public void addTDevCFile(TDevCFile tDevC){
		this.tDevCFiles.add(tDevC);
	}

	private void addTDevCSpec(String spec){
		
	}
	
	public void saveFiles() throws IOException{
		IOException throwableExc = null;
		for (TDevCFile tDevCFile : tDevCFiles) {
			if (!tDevCFile.isSaved()){
				try{
					tDevCFile.saveFile(Editor.getInstance().getcodeEditorContent(tDevCFile.getName()));
				}catch (IOException ioe){
					Editor.terminalMsgln("Error during saving " + tDevCFile.getName() + " file.");
				}
			}
		}
		if (throwableExc != null) {throw throwableExc;}
	}
	
	public DefaultMutableTreeNode generateProjectTree() {
		rootElem = new DefaultMutableTreeNode(this.getName(),true);
		rootElem.add(new DefaultMutableTreeNode("Platform", true));
		for (TDevCFile tdevcFile: this.gettDevCFiles()) {
			rootElem.add(tdevcFile.generateTDevCTree());
		}
		return rootElem; 
	}

	public HashSet<TDevCFile> gettDevCFiles() {
		return tDevCFiles;
	}
	
	public void doParse() throws Exception{
		Exception throwableExc = null;
		Editor.terminalMsgln("Starting parsing project " + this.getName());
		long startTime = System.currentTimeMillis();
		for (TDevCFile tdevcFilesNames : this.gettDevCFiles()) {
			try{
				tdevcFilesNames.doParse();
			}catch (Exception e){
				//Editor.terminalMsgln(e.getMessage());
				throwableExc = e;
				continue;
			}
		}
		Editor.terminalMsgln("Parsing time: " + String.format("%.2f", (double)(System.currentTimeMillis() - startTime) / 1000) + "s");
		if (throwableExc != null) {throw throwableExc;}
		//this.tdevcGen.generateServiceDependencies();
	}
	
}
