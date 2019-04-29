package br.ufpe.cin.greco.devc.languageStructure;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import br.ufpe.cin.greco.devc.languageStructure.exception.NoRegisterFieldException;
import br.ufpe.cin.greco.devc.languageStructure.exception.WrongPatternException;
import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.CodeLanguage;
import br.ufpe.cin.greco.devc.languageStructure.type.FieldType;


public class RegisterFormat extends Field {
	private String formatName;
	private String pattern;
	private int size;
	private boolean hasMask = false;
	private long maskFormatReady;
	private long maskFormat;
	private long maskFormatOne;
	//private HashMap<String,Field> fields = new HashMap<String, Field>();
	
	
	
	public String getFormatName() {
		return formatName;
	}

	public String getPattern() {
		return pattern;
	}

	/*public int getSize() {
		return size;
	}*/

	public boolean itHasMask() {
		return hasMask;
	}

	public long getMaskFormatReady() {
		return maskFormatReady;
	}
	
	public long getMaskFormatOne() {
		return maskFormatOne;
	}

	public long getMaskFormat() {
		return maskFormat;
	}

	public RegisterFormat(String formatName, HashMap<String,Field> fields) {
		super(formatName, "0", AccessType.VOID, FieldType.FORMAT);
		this.formatName = formatName;
		this.setFields(fields);
	}
	
	/*public void processPattern(String pattern) throws WrongPatternException {
		this.pattern = pattern;
		try {
			String[] patternPieces = this.pattern.split("[%]");
			String[] subPatternPieces;
			String string;
			for (int i = patternPieces.length-1;i>=0;i--){
				 string = patternPieces[i];
				if (!string.equals("")){
					subPatternPieces = string.split(":");
					if (subPatternPieces[0].equals("")){
						throw new WrongPatternException(this.formatName, this.pattern);
					}else{
						if (subPatternPieces[0].endsWith("RESERVED")){
							this.fields.put("RESERVED" + i,new Field(FieldType.RESERVED,Integer.parseInt(subPatternPieces[1]),this.size));
							this.size = this.size + Integer.parseInt(subPatternPieces[1]);
						}else if (subPatternPieces[0].endsWith("MASK")){
							this.hasMask = true;
							this.maskFormat = Long.parseLong(subPatternPieces[1].replaceAll("[.]", "0"), 2);
							this.maskFormatOne = Long.parseLong(subPatternPieces[1].replaceAll("[.]", "1"), 2);
							this.maskFormatReady = Long.parseLong(subPatternPieces[1].replaceAll("[0]", ".").replaceAll("[1]", "0").replaceAll("[.]", "1"), 2); 
						}else{
							//System.out.println(subPatternPieces[1]);
							this.fields.put(subPatternPieces[0],new Field(subPatternPieces[0], FieldType.REGULAR,Integer.parseInt(subPatternPieces[1]),this.size));
							this.size = this.size + Integer.parseInt(subPatternPieces[1]);
						}
					}
					if (subPatternPieces.length == 3){
						throw new WrongPatternException(this.formatName, this.pattern);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongPatternException(this.formatName,this.pattern);
		}
	}*/
		
	/*public Field getField(String name) throws NoRegisterFieldException{
		if (fields.size() == 0){
			throw new NoRegisterFieldException(this);
		}else{
			return this.fields.get(name);
		}
	}*/
	
	public String getMaskMatchSignature(){
		return "MASK_MATCH_" + this.formatName.toUpperCase();
	}
	
	public String getMaskMatchCode(){
		return "((reg & " + this.getMaskFormat() + ") == " + this.getMaskFormat() + ") && ((reg | " + this.getMaskFormatOne() + ") == " + this.getMaskFormatOne() + ")";
	}

	
	/*public String getFieldSignature(String field) throws NoRegisterFieldException{
		//return "GET_" + this.getFormatName().toUpperCase() + "_" + this.getField(field).getName().toUpperCase();
		return getField(field).getFieldSignature(this.getFormatName());
	}*/
	
	/*public String getFieldCode(String field) throws NoRegisterFieldException{
		/*Field fd = this.getField(field);
		double mask = (Math.pow(2, fd.getSize()) - 1 ) * Math.pow(2,fd.getStart());
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("0");
		return "(reg & " + df.format(mask) + ") > " + fd.getStart();* /
		return getField(field).getFieldCode();
	}*/

	@Override
	public String toString() {
		return getName();
	}
	
}
