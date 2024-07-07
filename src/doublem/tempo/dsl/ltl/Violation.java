package doublem.tempo.dsl.ltl;

public class Violation extends LTLRule {
	
	private String name;
	private String type;
	
	public String getName() {
		return name;
	}

	public Violation(String name, String type,  String ltlf) {
		super(ltlf);
		this.name = name;
		this.type = type;
	}

	public Violation(String type, String ltlf) {
		super(ltlf);
		this.name = "Violation_" + this.hashCode();
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "[" + this.type + "] "+ this.getName() + " { " + this.getLtlf() + " }"; 
	}
}
