package doublem.tempo.dsl.filetype.tdevice;

public class FormatField {
	public static enum FormatFieldType{
		REGULAR,
		RESERVED,
	}
	private String name;
	private int size;
	private int start;
	private int end;
	private FormatFieldType type;
	
	public FormatField(FormatFieldType type, int fieldSize, int currentRegSize) {
		this.name = "RESERVED";
		this.type = type;
		this.size = fieldSize;
		this.start = currentRegSize;
		this.end = this.start + this.size - 1;
	}
	
	public FormatField(String name, FormatFieldType type, int fieldSize, int currentRegSize) {
		this.name = name;
		this.type = type;
		this.size = fieldSize;
		this.start = currentRegSize;
		this.end = this.start + this.size - 1;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public FormatFieldType getType() {
		return type;
	}

	
}
