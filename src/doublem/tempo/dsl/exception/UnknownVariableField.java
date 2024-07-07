package doublem.tempo.dsl.exception;

import doublem.tempo.dsl.filetype.tdevice.Variable;

public class UnknownVariableField extends Exception {
	
	private Variable var;
	private String fieldID;
	
	public UnknownVariableField(Variable var, String fieldID) {
		this.var = var;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Unknown variable field \"" + this.fieldID + "\" in variable \"" + this.var.getName() + "\"." ;
	}

}
