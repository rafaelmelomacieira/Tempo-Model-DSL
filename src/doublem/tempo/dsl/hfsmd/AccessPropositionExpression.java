package doublem.tempo.dsl.hfsmd;

import doublem.tempo.dsl.type.AccessType;
import doublem.tempo.dsl.type.PropositionExpressionType;

public class AccessPropositionExpression extends PropositionExpression {
	
	public enum LocalAccessType {
		READ,
		WRITE;
		
		public String toString() {
			switch (this) {
			case READ: return "READ";
			case WRITE: return "WRITE";
			default: return "Invalid";
			}
		}
				
	}
	
	private LocalAccessType accessType;
	private String fieldRep;
	
	public AccessPropositionExpression(LocalAccessType accessType, String fieldRep) {
		super(PropositionExpressionType.ACCESS);
		this.fieldRep = fieldRep;
		this.accessType = accessType;
	}
	
	public LocalAccessType getAccessType() {
		return accessType;
	}
	
	public String getFieldRep() {
		return fieldRep;
	}
	
	@Override
	public String toString() {
		return this.accessType + "%" + this.fieldRep;
	}
}
