package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import java.util.HashMap;

public class VarMap {
	private String name;
	private HashMap<String, Variable> variables = new HashMap<String, Variable>();
	
	public HashMap<String, Variable> getVariables() {
		return variables;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void addVariable(Variable variable){
		this.variables.put(variable.getName(), variable);
	}
	
	public Variable getVariable(String variableName){
		if (this.variables.containsKey(variableName)){
			return this.variables.get(variableName);
		}else{
			return null; //EXCEPTION
		}
	}
	
	public String getName(){
		return this.name;
	}
	
}
