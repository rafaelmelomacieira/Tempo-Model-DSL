package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpe.cin.greco.devc.languageStructure.exception.UnknownVariableField;

public class Variable {

	private String name;
	private Integer initialValue;
	
	public Variable(String name, String initialValue) throws Exception {
		this.name = name;
		this.initialValue = resolveValue(initialValue);
	}
	
	public Variable(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}
	
	public Integer getInitialValue(){
		return this.initialValue;
	}
	
	private Integer resolveValue(String value) throws Exception{
		if (value.matches("[0-9]+")){
			return Integer.parseInt(value,10);
		}else if (value.matches("[0][x][0-9abcdefABCDEF]+")){
			return Integer.parseInt(value.substring(2, value.length()),16);
		}else if (value.matches("[b][01]+")){
			return Integer.parseInt(value.substring(1, value.length()),2);
		}
		Logger.getGlobal().log(Level.SEVERE, " Wrong Initial Value Type at Variable \"" + this.getName() + "\"!");
		throw new Exception(" Wrong Initial Value Type at Variable \"" + this.getName() + "\"!");
	}
	
	@Override
	public String toString() {
		if (this.getInitialValue() == null){
			return this.getName();
		}else{
			return this.getName() + " = " + this.getInitialValue();
		}
	}
	
}
