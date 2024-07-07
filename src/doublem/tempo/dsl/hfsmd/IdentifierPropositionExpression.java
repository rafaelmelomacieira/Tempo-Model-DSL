package doublem.tempo.dsl.hfsmd;

import doublem.tempo.dsl.hfsmd.BooleanPropositionExpression.LocalBooleanType;
import doublem.tempo.dsl.type.PropositionExpressionType;

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
