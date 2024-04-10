package br.ufpe.cin.greco.devc.languageStructure;

public abstract class FileDescriptor {
	
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
	private String projecyName;
	
	public FileType getFileType() {
		return fileType;
	}

	public String getProjecyName() {
		return projecyName;
	}
	
	public FileDescriptor(String projectName, FileType fileType) {
		this.projecyName = projectName;
		this.fileType = fileType;
	}
	
	public abstract void setDefinition(TerminusFileDefinition tdef);
	

}
