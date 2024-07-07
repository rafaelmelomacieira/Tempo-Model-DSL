package doublem.tempo.dsl.type;

public enum CodeLanguage {
	C("C"),
	CPP("C++"),
	VERILOG("Verilog"),
	SYSTEMVERILOG("System Verilog"),
	VHDL("VHDL");
	
	private String codeName;
	
	private CodeLanguage(String codeName){
		this.codeName = codeName;
	}
	
	public String getCodeName() {
		return codeName;
	}
}
