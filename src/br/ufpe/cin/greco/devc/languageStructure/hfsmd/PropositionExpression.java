package br.ufpe.cin.greco.devc.languageStructure.hfsmd;

import br.ufpe.cin.greco.devc.languageStructure.type.PropositionExpressionType;

public abstract class PropositionExpression {
	
	private PropositionExpressionType type;
	
	public PropositionExpression(PropositionExpressionType type) {
		this.type = type;
	}
	
	public PropositionExpressionType getType() {
		return type;
	}
	
}
