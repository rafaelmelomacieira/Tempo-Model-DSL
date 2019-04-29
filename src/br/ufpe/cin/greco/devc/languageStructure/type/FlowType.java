package br.ufpe.cin.greco.devc.languageStructure.type;

public enum FlowType {
	REGULAR("regular"),
	ARBITRARY_MUST("arbitrary_must"),
	ARBITRARY_MIGHT("arbitrary_might"),
	INTERFLOW("interflow"),
	INTERSERVICE("interservice"),
	REJECTION("rejection");
	
	private String refName;
	
	private FlowType(String refName) {
		this.refName = refName;
	}
	
	public String getRefName() {
		return refName;
	}
}
