package br.ufpe.cin.greco.devc.languageStructure.hfsmd;

import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.PropositionExpressionType;

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
