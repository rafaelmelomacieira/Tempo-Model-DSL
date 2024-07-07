package doublem.tempo.dsl.type;

public enum NodeTransitionType {
	ASSIGNMENT, ANYVALUE, CONDITION_ANYVALUE, CONDITION_ASSIGNMENT, ANYWRITING, ANYREADING, NO_TRANSITION;

	public String getName(){
		switch (this) {
		case ASSIGNMENT:
			return "Assignment Only";
		case ANYWRITING:
			return "Payload Any Writing";
		case ANYREADING:
			return "Payload Any Reading";
		case ANYVALUE:
			return "Any Value Only";
		case CONDITION_ANYVALUE:
			return "Condition with Any Value";
		case CONDITION_ASSIGNMENT:
			return "Condition with Assignment";
		case NO_TRANSITION:
			return "No Transition";
		default:
			return "** Invalid Node Transition Type **";
		}
		
	}
}
