package br.ufpe.cin.greco.devc.languageStructure.type;

public enum DelayLimitType {
	MAX("max"),
	MIN("min");

	private String refName;

	private DelayLimitType(String refName) {
		this.refName = refName;
	}

	public String getRefName() {
		return refName;
	}
}
