package br.ufpe.cin.greco.devc.languageStructure.ltl;

public class Behavior extends LTLRule {
	private String name;
	private String type;
	
	public String getName() {
		return name;
	}

	public Behavior(String name, String type,  String ltlf) {
		super(ltlf);
		this.name = name;
		this.type = type;
	}

	public Behavior(String type, String ltlf) {
		super(ltlf);
		this.name = "Violation_" + this.hashCode();
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "[" + this.type + "] "+ this.getName() + " { " + this.getLtlf() + " }"; 
	}
}
