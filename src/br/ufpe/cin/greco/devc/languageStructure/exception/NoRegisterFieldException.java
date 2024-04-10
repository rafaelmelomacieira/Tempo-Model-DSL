package br.ufpe.cin.greco.devc.languageStructure.exception;

import br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice.Register;
import br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice.RegisterFormat;

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
