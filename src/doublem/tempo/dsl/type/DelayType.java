package doublem.tempo.dsl.type;

public enum DelayType {
	S("Second",1),
	MS("Millisecond",-10^3),
	US("Microsecond",-10^6),
	NS("Nanosecond",-10^9),
	PS("Picosecond",-10^12);

	private String refName;
	private Integer toSecond;

	private DelayType(String refName, Integer toSecond) {
		this.refName = refName;
		this.toSecond = toSecond;
	}

	public String getRefName() {
		return refName;
	}

	public Integer getToSecond() {
		return toSecond;
	}

}
