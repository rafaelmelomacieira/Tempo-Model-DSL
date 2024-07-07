package doublem.tempo.dsl.filetype.tdevice;

public class AddressBind {
	
	private String registerName;
	private String address;
	
	public String getRegisterName() {
		return registerName;
	}

	public String getAddress() {
		return address;
	}
	
	public AddressBind(String registerName, String address) {
		this.registerName = registerName;
		this.address = address;
	}
	
}
