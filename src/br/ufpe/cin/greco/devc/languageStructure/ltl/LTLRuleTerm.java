package br.ufpe.cin.greco.devc.languageStructure.ltl;

public class LTLRuleTerm implements Comparable<LTLRuleTerm>{
	
	private String term;
	private String z3Term;
	
	@Deprecated
	private LTLRuleTerm firstTerm;
	@Deprecated
	private LTLRuleTerm operTerm;
	@Deprecated
	private LTLRuleTerm secondTerm;
	
	
	public String getTerm() {
		return term;
	}
	
	public String getZ3Term() {
		return z3Term;
	}

	public LTLRuleTerm(String term){
		this.term = term;
		this.z3Term = term;
	}
	
	public LTLRuleTerm(String term, String z3Term){
		this.term = term;
		this.z3Term = z3Term;
	}

	@Override
	public int compareTo(LTLRuleTerm o) {
		//if (this.)
		return 0;
	}
}
