package doublem.tempo.dsl.ltl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import doublem.tempo.dsl.type.StateType;

public interface IDevCState {

	public HashMap<String, IDevCState> getInheritors();	
	public HashMap<String, IDevCState> getSons();
	public HashSet<EntryPoint> getEntryPoints();
	public HashSet<ExitPoint> getExitPoints();
	public HashMap<String, Violation> getViolations();
	public HashMap<String, Behavior> getBehaviors();
	public String getLabel();
	public String getName();
	public IDevCState getParent();
	public StateType getType();
	public String getLiteral_rules();
	public void setLiteral_rules(String literal_rules);
	public boolean isInitial();
	public HashSet<IDevCState> getInitialsSons();
	public String getDotTransitions();
	public Integer getSubStateMachine();
	public Integer getLevel();
	public OrthoRegion getHomeLand();
	public HashMap<String,OrthoRegion> getMyOrthoRegions();
	public String getFullFSM();
	public String getBitSize();
	
	
	public void setSons(HashMap<String, IDevCState> children);	
	public void setEntryPoints(HashSet<EntryPoint> entryPoints);
	public void setExitPoints(HashSet<ExitPoint> exitPoints);
	public void setViolations(HashMap<String, Violation> violations);
	public void setBehaviors(HashMap<String, Behavior> behaviors);
	public void setName(String name);
	public void setParent(IDevCState baState);
	public void setType(StateType type);
	public void setInitialsSons(HashSet<IDevCState> states);
	public void setSubStateMachine(Integer subStateMachine);
	public void setLevel(Integer level);
	public void setHomeland(OrthoRegion region);
	
	public void addEntryPoint(EntryPoint ep);
	public void addExitPoint(ExitPoint ep);
	public void addViolation(Violation vio) throws Exception;
	public void addBehavior(Behavior beh) throws Exception;
	public void addSon(IDevCState state) throws Exception;
	public void addInitialSon(IDevCState state);
	public void addOrthoRegion(OrthoRegion region) throws Exception;
	
}
