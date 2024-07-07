package doublem.tempo.dsl.hfsmd;

import doublem.tempo.dsl.type.PropositionExpressionType;

public class BooleanPropositionExpression extends PropositionExpression {
	
	public enum LocalBooleanType {
		TRUE,
		FALSE;
		
		public Boolean getBoolean() {
			switch (this) {
			case TRUE: return true;
			case FALSE: return false;
			default: return null;
			}
		}
				
	}
	
	private LocalBooleanType boolType;
	
	public BooleanPropositionExpression(LocalBooleanType boolType) {
		super(PropositionExpressionType.BOOLEAN);
		this.boolType = boolType;
	}
	
	public LocalBooleanType getBoolType() {
		return boolType;
	}
	
	@Override
	public String toString() {
		return this.boolType.toString();
	}
}
