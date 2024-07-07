package doublem.tempo.dsl.ltl;

public abstract class LTLRule {
	
	private String ltlf;
	private String z3ltlf;
	
	public String getLtlf() {
		return ltlf;
	}
	
	public String getz3Ltlf() {
		return z3ltlf;
	}

	public void setLtlf(String ltlf) {
		this.ltlf = ltlf;
	}
	
	public LTLRule(String ltlf) {
		this.ltlf = ltlf;
	}
	
	public LTLRule(String ltlf, String z3ltlf) {
		this.ltlf = ltlf;
		this.z3ltlf = z3ltlf;
	}
	
	public String getNegatedLtlf() {
		return "!("+ltlf+")";
	}
	
	@Override
	public String toString() {
		return ltlf;
	}

}
