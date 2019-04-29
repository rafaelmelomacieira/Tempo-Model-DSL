package br.ufpe.cin.greco.devc.languageStructure.hfsmd;

import br.ufpe.cin.greco.devc.languageStructure.hfsmd.BooleanPropositionExpression.LocalBooleanType;
import br.ufpe.cin.greco.devc.languageStructure.type.PropositionExpressionType;

public class IdentifierPropositionExpression extends PropositionExpression {

private String ident;
	
	public IdentifierPropositionExpression(String ident) {
		super(PropositionExpressionType.IDENT);
		this.ident = ident;
	}
	
	public String getIdent() {
		return ident;
	}
	
	@Override
		public String toString() {
			return ident;
		}
	
}
