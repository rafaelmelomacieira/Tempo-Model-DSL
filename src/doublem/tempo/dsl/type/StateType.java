package doublem.tempo.dsl.type;

public enum StateType {

	GLOBALSTATE,
	INITIALSTATE,
	ORTHOSTATE,
	STATE;
	
	public String getName(){
		switch (this) {
		case GLOBALSTATE:
			return "Global State";
		case INITIALSTATE:
			return "Initial State";
		case ORTHOSTATE:
			return "Orthogonal State";
		case STATE:
			return "Regular State";
		default:
			return " - ";
		}
	}
}
