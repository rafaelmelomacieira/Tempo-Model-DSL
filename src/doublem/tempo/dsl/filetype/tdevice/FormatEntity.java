package doublem.tempo.dsl.filetype.tdevice;

public class FormatEntity {
	private String name;
	private String pattern;
	
	public FormatEntity(String name, String pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	public String getName() {
		return name;
	}
	public String getPattern() {
		return pattern;
	}
}
