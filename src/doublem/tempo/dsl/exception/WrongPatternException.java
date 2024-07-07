package doublem.tempo.dsl.exception;

public class WrongPatternException extends Exception {
	private String pattern;
	private String source;
	
	public WrongPatternException(String source, String pattern) {
		this.pattern = pattern;
		this.source = source;
	}
	
	@Override
	public String getMessage() {
		return "Wrong pattern \"" + this.pattern + "\" at \"" + source + "\"";
	}
}
