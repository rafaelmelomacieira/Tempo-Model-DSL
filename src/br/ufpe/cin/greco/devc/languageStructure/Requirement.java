package br.ufpe.cin.greco.devc.languageStructure;

import java.util.HashSet;

public abstract class Requirement {
	
	private Integer ID;
	private Requirement origin;
	private String action;
	private String actionObject;
	private String condition;
	
		
}

//THE SYSTEM/COMPONENT/MODULE Must Do/Load/Open the door.
//THE SYSTEM/COMPONENT/MODULE Must Do/Load/Open the door at least 10ms.