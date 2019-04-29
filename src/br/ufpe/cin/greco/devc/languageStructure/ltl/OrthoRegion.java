package br.ufpe.cin.greco.devc.languageStructure.ltl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpe.cin.greco.devc.languageStructure.type.StateType;

public class OrthoRegion {
	private String regionName;
	private HashMap<String, IDevCState> regionStates = new HashMap<String, IDevCState>();
	private IDevCState regionInitialState;
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public HashMap<String, IDevCState> getRegionStates() {
		return regionStates;
	}
	public void setRegionStates(HashMap<String, IDevCState> regionStates) {
		this.regionStates = regionStates;
	}
	public IDevCState getRegionInitialState() {
		return regionInitialState;
	}
	public void setRegionInitialState(IDevCState regionInitialState) {
		this.regionInitialState = regionInitialState;
	}
	
	public void addStates(HashMap<String, IDevCState> states) throws Exception{
		for (String stateName : states.keySet()) {
			this.addState(states.get(stateName)); 
		}
	}
	public OrthoRegion(String name) {
		this.setRegionName(name);
	}
	
	public void addState(IDevCState state) throws Exception {
		if (this.regionStates.containsKey(state.getName())){
			Logger.getGlobal().log(Level.SEVERE, " Duplicated State: \"" + state.getName() + "\"");
			throw new Exception(" Duplicated State: \"" + state.getName() + "\"");
		}else{
			this.regionStates.put(state.getName(), state);
			state.setHomeland(this);
			if (state.getType() == StateType.INITIALSTATE){
				if (this.getRegionInitialState() == null){
					this.regionInitialState = state;
				}else{
					Logger.getGlobal().log(Level.SEVERE, " Orthogonal Region \"" + this.getRegionName() + "\" with more than one initial state: \"" + state.getName() + "\" and \"" + this.getRegionInitialState() + "\"");
					throw new Exception(" Orthogonal Region \"" + this.getRegionName() + "\" with more than one initial state: \"" + state.getName() + "\" and \"" + this.getRegionInitialState() + "\"");
				}
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getRegionName();
	}
	
	public String getBitSize() {
		String[] total = Double.toString(Math.ceil(Math.log(this.regionStates.size()) / Math.log(2))).split("[.]");
		return total[0];
	}
}
