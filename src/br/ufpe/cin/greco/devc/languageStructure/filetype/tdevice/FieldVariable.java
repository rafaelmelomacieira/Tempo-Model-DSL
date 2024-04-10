package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import java.io.ObjectInputStream.GetField;

public class FieldVariable {

	private String name;
	private FieldVariable field;
	private Field mappedField;
	private Boolean contaisNextField = false; 
	
	public FieldVariable(String name) {
		this.name = name;
	}
	
	public void setMappedField(Field mappedField) {
		this.mappedField = mappedField;
		contaisNextField = true;
	}
	
	public Boolean contaisNextField() {
		return contaisNextField;
	}
	
	public void setField(FieldVariable field) {
		this.field = field;
	}
	
	public FieldVariable getField() {
		return field;
	}
	
	public String getName() {
		return name;
	}
	
	public Field getMappedField() {
		return mappedField;
	}
	
}
