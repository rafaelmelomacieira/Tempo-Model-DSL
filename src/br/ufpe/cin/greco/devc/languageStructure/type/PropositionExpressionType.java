package br.ufpe.cin.greco.devc.languageStructure.type;

public enum PropositionExpressionType {
	BOOLEAN,
	ACCESS,
	IDENT,
	COMPOSITE;
	
	public String getName(){
		switch (this) {
		case BOOLEAN:
			return "Boolean";
		case ACCESS:
			return "Access";
		case IDENT:
			return "Identifier";
		case COMPOSITE:
			return "Composite";
		default:
			return "Invalid";
		}
	}
}
