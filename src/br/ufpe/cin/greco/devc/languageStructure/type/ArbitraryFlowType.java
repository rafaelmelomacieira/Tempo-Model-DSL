package br.ufpe.cin.greco.devc.languageStructure.type;

public enum ArbitraryFlowType {
	
	MIGHT("might", FlowType.ARBITRARY_MIGHT),
	MUST("must", FlowType.ARBITRARY_MUST),
	MAIN("main", FlowType.REGULAR),
	CANNOT("cannot", FlowType.REGULAR);
	
	private String refName;
	private FlowType flowType;
	
	private ArbitraryFlowType(String refName, FlowType flowType) {
		this.refName = refName;
		this.flowType = flowType;
	}
	
	public String getRefName() {
		return refName;
	}
	
	public FlowType getFlowType() {
		return flowType;
	}

}
