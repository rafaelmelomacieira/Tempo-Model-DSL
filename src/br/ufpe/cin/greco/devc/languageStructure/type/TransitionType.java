package br.ufpe.cin.greco.devc.languageStructure.type;

public enum TransitionType {
	ASSIGNMENT, CONDITION,  ELSE_CONDITION, ANYWRITING, ANYREADING, ANYVALUE, NO_TRANSITION;
	public String getName(){
		switch (this) {
		case ASSIGNMENT:
			return "Assignment";
		case ELSE_CONDITION:
			return "'Else' Condition";
		case CONDITION:
			return "Failure: Condition Alone";
		case ANYWRITING:
			return "Payload Any Writting";
		case ANYREADING:
			return "Payload Any Reading";
		case ANYVALUE:
			return "Any Value";
		case NO_TRANSITION:
			return "No Transition";
		default:
			return "Invalid Transition Type";
		}
	}
}
