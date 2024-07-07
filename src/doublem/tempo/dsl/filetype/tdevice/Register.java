package doublem.tempo.dsl.filetype.tdevice;


import java.util.HashMap;

import doublem.tempo.dsl.type.AccessType;
import doublem.tempo.dsl.type.FieldType;

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
	public void buildFields(TDevice tDevice) {
		if(format.equals("")){
			super.buildFields(tDevice);
		}else{
			this.setFields(tDevice.getFormats().get(format).getFields());
			super.buildFields(tDevice);
		}
	}
	
	@Override
	public String toString() {
		return "(0x" + Integer.toHexString(getOffset()).toUpperCase() + ") " + getAlias() + " - " + getName();
	}
}
