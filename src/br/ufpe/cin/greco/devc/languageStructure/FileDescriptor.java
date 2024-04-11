package br.ufpe.cin.greco.devc.languageStructure;

import java.util.HashMap;
import java.util.HashSet;

import br.ufpe.cin.greco.devc.languageStructure.ltl.EntryPoint;
import br.ufpe.cin.greco.devc.languageStructure.ltl.IDevCState;

public abstract class FileDescriptor {
	
	private String projectName;
	
	private IDevCState globalState;
	
	public enum FileType {
		DEVICE("Device"),
		MODULE("Module"),
		PLATFORM("Platform"),
		SYSTEM("System"),
		SUBSYSTEM("Subsystem"),
		COMPONENT("Component");
		
		private String description;
		
		private FileType(String description) {
			this.description = description;
		}
		
		public String toString() {
			return this.description;
		}
	};
	
	private FileType fileType;
	private HashMap<String, String> propositionsLib = new HashMap<String, String>();
	private HashMap<String, String> ltlPropositionsLib = new HashMap<String, String>();
	private HashMap<IDevCState,HashSet<EntryPoint>> entryPoints = new HashMap<IDevCState,HashSet<EntryPoint>>();
	
	public FileType getFileType() {
		return fileType;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public FileDescriptor(String projectName, FileType fileType) {
		this.projectName = projectName;
		this.fileType = fileType;
	}
	
	public abstract void setDefinition(TerminusFileDefinition tdef);
	
	public Integer addProposition(String prop){
		System.out.println("Prop: " + prop);
		for (String str : propositionsLib.keySet()) {
			if (propositionsLib.get(str).equalsIgnoreCase(prop)){
				System.out.println("Already as " + str);
				return Integer.parseInt(str.replace("p", ""));
			}
		}
		//String newName = "p" + propositionsLib.size() + "";
		propositionsLib.put("p" + propositionsLib.size(), prop);
		System.out.println("New as p" + (propositionsLib.size() - 1));
		return propositionsLib.size() - 1; 
	}
	
	public Integer addLTLProposition(String prop){
		for (String str : ltlPropositionsLib.keySet()) {
			if (ltlPropositionsLib.get(str).equalsIgnoreCase(prop)){
				return Integer.parseInt(str.replace("q", ""));
			}
		}
		//String newName = "q" + ltlPropositionsLib.size() + "]";
		ltlPropositionsLib.put("q" + ltlPropositionsLib.size(), prop);
		return ltlPropositionsLib.size() - 1; 
	}
	
	public HashMap<String,String> getPropositionsLib(){
		return this.propositionsLib;
	}
	
	public HashMap<String,String> getLTLPropositionsLib(){
		return this.ltlPropositionsLib;
	}
	
	public IDevCState getGlobalState() {
		return globalState;
	}
	
	

	public void setGlobalState(IDevCState gs){
		this.globalState = gs;
		this.globalState.setLevel(1);
		this.globalState.setSubStateMachine(0);
	}

}
