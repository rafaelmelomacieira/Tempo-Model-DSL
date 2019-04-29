package br.ufpe.cin.greco.devc.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.template.Template;

public class FileManagement {
	
	private static final String SOURCECODEDIR = "./mddc_source_code";
	private static final String Z3PROPSDIR = "./modelChecking";
	private static final String PROMELAPROPSDIR = "./modelChecking";

	private static final String SYSTEM_SEPARATOR = System.getProperty("file.separator");
	public static final String LOCAL_USER = System.getProperty("user.home");
	public enum ImageType {
		PNG,
		EPS;
		
		public String toString() {
			switch (this) {
			case EPS:
				return "eps";
			case PNG:
				return "png";
			default:
				return "png";
			}
		};
	}

	public static void createDotFile(String name, String baseDir, String content, boolean replace){
		String path = baseDir + SYSTEM_SEPARATOR + name + ".dot";
		File tmpDir = new File(baseDir);
		File tmpFile = new File(path);
		if (!tmpDir.exists()){
			tmpDir.mkdir();
		}
		if (tmpFile.exists()){
			if (replace) {
				genFile(tmpFile, content);
			}else{
				//LOG ERRO!
			}
		}else{
			genFile(tmpFile, content);
		}

	}
	
	public static void createZ3File(String name, String baseDir, String content, boolean replace){
		if (baseDir == null) {
			baseDir = Z3PROPSDIR;
		}
		String path = baseDir + SYSTEM_SEPARATOR + name + ".z3TDevC";
		File tmpDir = new File(baseDir);
		File tmpFile = new File(path);
		if (!tmpDir.exists()){
			tmpDir.mkdir();
		}
		if (tmpFile.exists()){
			if (replace) {
				genFile(tmpFile, content);
			}else{
				//LOG ERRO!
			}
		}else{
			genFile(tmpFile, content);
		}
	}
	
	public static void createPromelaFile(String name, String baseDir, String content, boolean replace){
		if (baseDir == null) {
			baseDir = PROMELAPROPSDIR;
		}
		String path = baseDir + SYSTEM_SEPARATOR + name + ".pTDevC";
		File tmpDir = new File(baseDir);
		File tmpFile = new File(path);
		if (!tmpDir.exists()){
			tmpDir.mkdir();
		}
		if (tmpFile.exists()){
			if (replace) {
				genFile(tmpFile, content);
			}else{
				//LOG ERRO!
			}
		}else{
			genFile(tmpFile, content);
		}
	}
	
	public static Writer getMDDCWriteTarget(String modelName, String component, String baseDir) throws IOException{
		String name = "mddc_" + modelName + "_" + component;
		if (baseDir == null){
			baseDir = SOURCECODEDIR;
		}
		
		String path = baseDir + SYSTEM_SEPARATOR + name + ".sv";
		File tmpDir = new File(baseDir);
		File tmpFile = new File(path);
		if (!tmpDir.exists()){
			tmpDir.mkdir();
		}
		return new FileWriter(tmpFile);
		/*if (tmpFile.exists()){
			if (replace) {
				//genFile(tmpFile, content);
				temp.process(dataModel, out)
			}else{
				//LOG ERRO!
			}
		}else{
			genFile(tmpFile, content);
		}*/
	}

	private static void genFile(File file, String content){
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createImageFile(String name, String baseDir, ImageType type, boolean replace){
		try {
			Runtime.getRuntime().exec("dot -T" + type + " " + baseDir + SYSTEM_SEPARATOR + name + ".dot -o " + baseDir + SYSTEM_SEPARATOR + name + ".png");
		} catch (IOException e) {
			Logger.getGlobal().log(Level.WARNING, " Graphiz not working! ");
		}
	}

}
