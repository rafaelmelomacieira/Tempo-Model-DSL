package doublem.tempo.dsl.filetype.tdevice;

public class Parameter {
	private String name;
	private Integer size;
	private boolean signed;
	
	public Parameter(String name, Integer size, boolean signed) {
		this.name = name;
		this.size = size;
		this.signed = signed;
	}

	public String getName() {
		return name;
	}

	public Integer getSize() {
		return size;
	}

	public boolean isSigned() {
		return signed;
	}
	
	
}
