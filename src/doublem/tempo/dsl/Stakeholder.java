package doublem.tempo.dsl;

import java.util.HashMap;
import java.util.HashSet;

public class Stakeholder {
	private String name;
	private HashMap<Version, String> analisys = new HashMap<Version, String>();
	private HashSet<Needs> needs = new HashSet<Needs>();
	private HashSet<Requirement> requirements = new HashSet<Requirement>();
}

/*Stakeholder "rafael macieira" {

review(1){
	
	analisys{"sdasdasdasdsad"}

	need(entity1){"adsadawfd as dfsad"}
	need(entity2){"adsadawfd as dfsad"}
	need(entity3){"adsadawfd as dfsad"}
	need(entity4){"adsadawfd as dfsad"}
	
	req{"s sdfs fsfd sdf"}
	req{"s sdfs fsfd sdf"}
	req{"s sdfs fsfd sdf"}
	req{"s sdfs fsfd sdf"}
}
	
}
*/