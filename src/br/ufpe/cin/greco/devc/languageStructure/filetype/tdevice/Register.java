package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;


import java.util.HashMap;

import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.FieldType;

public class Register extends Field {
	
	private String alias = "";
	private String format = "";
	//private String name;
	//private RegisterFormat format;
	boolean needBuffer = false;

	public Register(String name, String alias, String offset, AccessType type, HashMap<String, Field> fields) {
		super(name, offset, type, FieldType.REGISTER);
		this.setFields(fields);
		this.root = true;
		this.leaf = false;
		this.alias = alias;
	}
	public Register(String name, String alias, String offset, AccessType type, String format) {
		super(name, offset, type, FieldType.REGISTER);
		this.format = format;
		this.root = true;
		this.leaf = false;
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
	
	@Override
	public void buildFields(TDCCheckerGenerator tdevcChecker) {
		if(format.equals("")){
			super.buildFields(tdevcChecker);
		}else{
			this.setFields(tdevcChecker.getFormats().get(format).getFields());
			super.buildFields(tdevcChecker);
		}
	}
	
	@Override
	public String toString() {
		return "(0x" + Integer.toHexString(getOffset()).toUpperCase() + ") " + getAlias() + " - " + getName();
	}
}
