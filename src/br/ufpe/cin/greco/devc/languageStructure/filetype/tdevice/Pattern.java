package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import br.ufpe.cin.greco.devc.languageStructure.devc.parser;

public class Pattern {
	private String name;
	private String mask;
	private Boolean hasMask;
	private Integer value;
	
	public String getName() {
		return name;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public Pattern(String name, String value, Boolean mask) {
		this.name = name;
		hasMask = mask;
		if(mask){
			this.value = null;
			this.mask = value;
		}else{
			this.value = resolvAddress(value);
			this.mask = null;
		}
	}
	
	public String getMask() {
		return mask;
	}

	private Integer resolvAddress(String value){
		if (value.matches("[0-9]+")){
			return Integer.parseInt(value,10);
		}else if (value.matches("[0][x][0-9abcdefABCDEF]+")){
			return Integer.parseInt(value.substring(2, value.length()),16);
		}else if (value.matches("[b][01]+")){
			return Integer.parseInt(value.substring(1, value.length()),2);
		}
		return null;
	}
	
	@Override
	public String toString() {
		if (this.getValue() == null){
			return this.getName();
		}else{
			return this.getName() + " = " + this.getValue();
		}
	}
	
}
