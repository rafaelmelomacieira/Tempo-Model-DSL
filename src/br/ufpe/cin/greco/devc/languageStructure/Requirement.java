package br.ufpe.cin.greco.devc.languageStructure;

import java.util.HashSet;

public abstract class Requirement {
	
	private HashSet<Requirement> branchs = new HashSet<Requirement>();
	private String action;
	private String actionObject;
	
		
}