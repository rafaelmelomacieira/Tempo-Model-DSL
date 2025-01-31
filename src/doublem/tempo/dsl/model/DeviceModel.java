package doublem.tempo.dsl.model;

import java.util.HashMap;
import java.util.Map;

import doublem.tempo.dsl.filetype.tdevice.Register;
import doublem.tempo.dsl.ltl.IDevCState;
import doublem.tempo.dsl.type.RegisterType;

public class DeviceModel {
	
	private String name;
	private Map<String,Object> registers = new HashMap();
	private IDevCState globalState;
	private HashMap<String,String> hfsmProp = new HashMap<String,String>();
	private HashMap<String,String> baProp = new HashMap<String,String>();
	
	
	public String getName() {
		return name;
	}

	public Map<String, Object> getRegisters() {
		return registers;
	}
	
	public DeviceModel(String name) {
		this.name = name;
	}	
	
	public void addRegister(Register reg){
		this.registers.put(reg.getAlias(), reg);
	}

	public IDevCState getGlobalState() {
		return globalState;
	}

	public void setGlobalState(IDevCState globalState) {
		this.globalState = globalState;
	}

	public HashMap<String, String> getHfsmProp() {
		return hfsmProp;
	}

	public void setHfsmProp(HashMap<String, String> hfsmProp) {
		this.hfsmProp = hfsmProp;
	}

	public HashMap<String, String> getBaProp() {
		return baProp;
	}

	public void setBaProp(HashMap<String, String> baProp) {
		this.baProp = baProp;
	}

	
}
