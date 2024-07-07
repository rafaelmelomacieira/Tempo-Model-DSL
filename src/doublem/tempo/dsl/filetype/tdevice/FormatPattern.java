package doublem.tempo.dsl.filetype.tdevice;

import java.util.HashMap;

public class FormatPattern {
	
	public static enum PatternType {
		SERVICE,
		REGISTER;
	}
	
	private String pattern;
	private int size;
	private HashMap<String,FormatField> fields = new HashMap<String, FormatField>();
	private int maskFormatReady;
	private int maskFormat;
	private boolean hasMask = false;
	private PatternType type = PatternType.SERVICE;
	private boolean isSigned = false;

	public String getPattern() {
		return pattern;
	}

	public int getSize() {
		return size;
	}

	public HashMap<String, FormatField> getFields() {
		return fields;
	}

	public int getMaskFormatReady() {
		return maskFormatReady;
	}

	public int getMaskFormat() {
		return maskFormat;
	}

	public boolean isHasMask() {
		return hasMask;
	}

	public PatternType getType() {
		return type;
	}

	public boolean isSigned() {
		return isSigned;
	}
	
	public FormatPattern(String pattern) {
		this.pattern = pattern;
		runPatternParse();
	}
	
	private void runPatternParse(){
		String[] patternPieces = this.pattern.split("[%]");
		String[] subPatternPieces;
		String string;
		for (int i = patternPieces.length-1;i>=0;i--){
			 string = patternPieces[i];
			if (!string.equals("")){
				subPatternPieces = string.split(":");
				if (subPatternPieces[0].equals("")){
					this.type = PatternType.SERVICE;
				}else{
					if (subPatternPieces[0].endsWith("RESERVED")){
						this.fields.put("RESERVED" + i,new FormatField(FormatField.FormatFieldType.RESERVED,Integer.parseInt(subPatternPieces[1]),this.size));
						this.size = this.size + Integer.parseInt(subPatternPieces[1]);
					}else if (subPatternPieces[0].endsWith("MASK")){
						this.hasMask = true;
						this.maskFormat = Integer.parseInt(subPatternPieces[1].replaceAll("[.]", "0"), 2);
						this.maskFormatReady = Integer.parseInt(subPatternPieces[1].replaceAll("[0]", ".").replaceAll("[1]", "0").replaceAll("[.]", "1"), 2); 
					}else{
						//System.out.println(subPatternPieces[1]);
						this.fields.put(subPatternPieces[0],new FormatField(subPatternPieces[0], FormatField.FormatFieldType.REGULAR,Integer.parseInt(subPatternPieces[1]),this.size));
						this.size = this.size + Integer.parseInt(subPatternPieces[1]);
					}
				}
				if (subPatternPieces.length == 3){
					this.isSigned = true;
					this.type = PatternType.SERVICE;
				}
			}
		}
	}

}
