package doublem.tempo.dsl.exception;

import doublem.tempo.dsl.filetype.tdevice.Register;
import doublem.tempo.dsl.filetype.tdevice.RegisterFormat;

public class NoRegisterFieldException extends Exception {
	private RegisterFormat regForm;

	public NoRegisterFieldException(RegisterFormat regForm) {
		this.regForm = regForm;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Register Format pattern \"" + this.regForm.getPattern() + "\" doesn't have any field";
	}
}
