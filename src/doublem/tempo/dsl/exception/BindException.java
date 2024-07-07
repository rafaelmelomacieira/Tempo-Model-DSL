package doublem.tempo.dsl.exception;

import doublem.tempo.dsl.filetype.tdevice.Bindable;

public class BindException extends Exception {
	Bindable bind;
	String bindReference;
	
	public BindException(Bindable bind) {
		this.bindReference = bind.toString();
	}
	public BindException(String bindReference) {
		this.bindReference = bindReference;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Bind Error on \"" + this.bindReference + "\"";
	}
}
