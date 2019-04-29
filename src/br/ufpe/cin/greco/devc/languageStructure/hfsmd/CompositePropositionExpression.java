package br.ufpe.cin.greco.devc.languageStructure.hfsmd;

import java.util.ArrayList;

import br.ufpe.cin.greco.devc.languageStructure.type.PropositionExpressionType;

public class CompositePropositionExpression extends PropositionExpression {
	
	private ArrayList<PropositionExpression> propExprs = new ArrayList<PropositionExpression>();
	private Boolean conjunction;
	
	public CompositePropositionExpression(Boolean conjunction) {
		super(PropositionExpressionType.COMPOSITE);
		this.conjunction = conjunction;
	}
	
	public void addPropostionExpr(PropositionExpression expr){
		this.propExprs.add(expr);
	}
	
	public ArrayList<PropositionExpression> getPropExprs() {
		return propExprs;
	}
	
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		Integer counter = 0;
		for (PropositionExpression prop : getPropExprs()) {
			ret.append(prop.toString());
			counter++;
			if(!(counter.equals(getPropExprs().size()))){
				if(this.conjunction) {
					ret.append(" && ");
				}else{
					ret.append(" || ");
				}
			}
		}
		return ret.toString();
		//return super.toString();
	}
	
	public Boolean isConjunction() {
		return conjunction;
	}
	
}
