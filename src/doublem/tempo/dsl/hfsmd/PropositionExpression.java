package doublem.tempo.dsl.hfsmd;

import doublem.tempo.dsl.type.PropositionExpressionType;

public abstract class PropositionExpression {
	
	private PropositionExpressionType type;
	
	public PropositionExpression(PropositionExpressionType type) {
		this.type = type;
	}
	
	public PropositionExpressionType getType() {
		return type;
	}
	
}
