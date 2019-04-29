package br.ufpe.cin.greco.devc.languageStructure.type;

public enum ServiceDependencyType {

	POS ("pos"),
	PRE ("pre"),
	NONE ("none");
	
	private String refName;
	
	private ServiceDependencyType(String refName) {
		this.refName = refName;
	}
	
	public String getRefName() {
		return refName;
	}
}
