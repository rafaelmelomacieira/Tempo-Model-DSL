package doublem.tempo.dsl.ltl;

import java.util.HashMap;

public class LTLRuleTermFactory {
	private static LTLRuleTermFactory instance;

	static{
		instance = new LTLRuleTermFactory();
	}
	
	private StringBuffer ltlf;
	
	
	private LTLRuleTermFactory(){}
	
	public static LTLRuleTermFactory getInstance(){
		return instance;
	}

	public StringBuffer getLtlf() {
		return ltlf;
	}

	public void addTermAfter(String newName){
		
	}
	
	public LTLRuleTerm getNewLTLRuleTerm(String term){
		return new LTLRuleTerm(term);
	}
	
	public LTLRuleTerm getNewLTLRuleTerm(String term, String z3Term){
		return new LTLRuleTerm(term,z3Term);
	}
	
}
