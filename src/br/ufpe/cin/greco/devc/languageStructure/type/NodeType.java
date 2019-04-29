package br.ufpe.cin.greco.devc.languageStructure.type;

public enum NodeType {
	
	A,
	R,
	I,
	In;
	
	public String getName(){
		switch (this) {
		case A:
			return "Accepted";
		case R:
			return "Rejected";
		case I:
			return "Intermediate";
		case In:
			return "Initial";
		default:
			return "Invalid";
		}
	}
}
