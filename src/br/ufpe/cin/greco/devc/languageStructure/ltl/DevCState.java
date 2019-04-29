package br.ufpe.cin.greco.devc.languageStructure.ltl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpe.cin.greco.devc.languageStructure.TDCCheckerGenerator;
import br.ufpe.cin.greco.devc.languageStructure.type.StateType;

public class DevCState extends OrthoRegion implements IDevCState  {

	private HashMap<String, IDevCState> inheritors = new HashMap<String, IDevCState>();
	
	private Integer subStateMachine = -1;
	private HashMap<String, IDevCState> sons = new HashMap<String, IDevCState>();
	private HashSet<EntryPoint> entryPoints = new HashSet<EntryPoint>();
	private HashSet<ExitPoint> exitPoints = new HashSet<ExitPoint>();
	private HashMap<String, Violation> violations = new HashMap<String, Violation>();
	private HashMap<String, Behavior> behaviors = new HashMap<String, Behavior>();
	private String label;
	private String name;
	private IDevCState parent;
	private StateType type;
	private String literal_rules = "";
	private HashSet<IDevCState> initialsSons = new HashSet<IDevCState>();
	private Integer level;
	private OrthoRegion homeland;
	private HashMap<String,OrthoRegion> myOrthoRegions = new HashMap<String,OrthoRegion>();
	

	/*public DevCState(HashSet<IDevCState> children, HashSet<String> entryPoints, HashMap<String, String> exitPoints, HashMap<String, String> violations, String label, IDevCState parent) {
		this.children = children;
		this.entryPoints = entryPoints;
		this.exitPoints = exitPoints;
		this.violations = violations;
		this.label = label;
		this.parent = parent;
	}
	
	public DevCState(HashSet<String> entryPoints, HashMap<String, String> exitPoints, HashMap<String, String> violations, String label, IDevCState parent) {
		this.entryPoints = entryPoints;
		this.exitPoints = exitPoints;
		this.violations = violations;
		this.label = label;
		this.parent = parent;
	}
	
	public DevCState(HashSet<IDevCState> children, HashSet<String> entryPoints, HashMap<String, String> exitPoints, HashMap<String, String> violations, String label) {
		this.children = children;
		this.entryPoints = entryPoints;
		this.exitPoints = exitPoints;
		this.violations = violations;
		this.label = label;
	}*/
	
	public StateType getType() {
		return type;
	}

	public void setType(StateType type) {
		this.type = type;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public IDevCState getParent() {
		return this.parent;
	}

	@Override
	public HashMap<String, IDevCState> getSons() {
		return this.sons;
	}


	@Override
	public void setParent(IDevCState parent) {
		this.parent = parent;
		
	}
	
	
	@Override
	public void setSons(HashMap<String, IDevCState> sons) {
		this.sons = sons;
		
	}
	
	public HashSet<EntryPoint> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(HashSet<EntryPoint> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public HashSet<ExitPoint> getExitPoints() {
		return exitPoints;
	}

	public void setExitPoints(HashSet<ExitPoint> exitPoints) {
		this.exitPoints = exitPoints;
	}

	public HashMap<String, Violation> getViolations() {
		return violations;
	}
	
	public HashMap<String, Behavior> getBehaviors() {
		return behaviors;
	}

	public void setViolations(HashMap<String, Violation> violations) {
		this.violations = violations;
	}
	
	public void setBehaviors(HashMap<String, Behavior> behavior) {
		this.behaviors = behaviors;
	}
	
	@Override
	public void addEntryPoint(EntryPoint ep) {
		this.entryPoints.add(ep);
	}
	
	@Override
	public void addExitPoint(ExitPoint ep) {
		this.exitPoints.add(ep);
	}
	
	@Override
	public void addViolation(Violation vio) throws Exception {
		if (!this.violations.containsKey(vio.getName())){
			this.violations.put(vio.getName(), vio);
		}else {
			throw new Exception("Two or more violation with the same name: " + vio.getName());
		}
	}
	
	@Override
	public void addBehavior(Behavior beh) throws Exception {
		if (!this.behaviors.containsKey(beh.getName())){
			this.behaviors.put(beh.getName(), beh);
		}else {
			throw new Exception("Two or more violation with the same name: " + beh.getName());
		}
	}
	
	@Override
	public void addSon(IDevCState state) throws Exception {
		if (!this.sons.containsKey(state.getName())){
			this.sons.put(state.getName(), state);
			if (state.isInitial()){
				this.addInitialSon(state);
			}
			this.inheritors.put(state.getName(), state);
			this.inheritors.putAll(state.getInheritors());
		}else {
			throw new Exception("Two or more states with the same name: " + state.getName());
		}
	}

	public String getLiteral_rules() {
		return literal_rules;
	}

	public void setLiteral_rules(String literal_rules) {
		this.literal_rules = literal_rules;
	}
	
	public HashMap<String, IDevCState> getInheritors() {
		return inheritors;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		if (this.sons.isEmpty()) {
			this.label = name;
			this.name = name;
		}else{
			this.label = "cluster_" + name; 
			this.name = name;
		}
		this.setRegionName(name);
	}

	@Override
	public boolean isInitial() {
		return (this.type == StateType.INITIALSTATE);
	}

	@Override
	public HashSet<IDevCState> getInitialsSons() {
		return this.initialsSons;
	}

	@Override
	public void setInitialsSons(HashSet<IDevCState> states) {
		this.initialsSons = states;		
	}
	
	@Override
	public String toString() {
		return (this.isInitial()?">":"") + this.getName();
	}
	
	public String getDot() {
		StringBuffer line = new StringBuffer();
		IDevCState son;
		line.append("digraph " + this.getLabel() + "State {\n");
		line.append(getDotTransitions());
		line.append("}");
		return line.toString();
	}
	
	public String getFullFSM(){
		StringBuffer machine = new StringBuffer();
		HashSet<String> knownStates = new HashSet<String>();
		knownStates.add(this.getName());
		machine.append("digraph " + this.getName() + "{\n");
		internalFullFSM(machine, knownStates , this);
		machine.append("}");
		return machine.toString();
	}
	
	private String internalFullFSM(StringBuffer machine, HashSet<String> knownStates, IDevCState state){
		if (state.isInitial()){
			machine.append(state.getName() + "[shape=\"Mdiamond\"];\n");
		}
		for (ExitPoint exp : state.getExitPoints()) {
			if (!knownStates.contains(exp.getTarget()) && !state.getName().equals(exp.getTargetState().getName())){
				knownStates.add(exp.getTarget());
				internalFullFSM(machine, knownStates, exp.getTargetState());
			}
			machine.append(state.getName() + " -> " + exp.getTargetState().getName() + " [label = \"" + exp.getLtlf() + "\"];\n");
		}
		return "";
	}
	
	public String getDotTransitions(){
		StringBuffer line = new StringBuffer();
		IDevCState son;
		
		line.append("subgraph " + this.getLabel() + " {\n");
		//System.out.println("subgraph " + this.getLabel() + " {");
		//line.append(getDotSons());
		for (String sonN : this.getSons().keySet()) {
			son = this.getSons().get(sonN);
			line.append(son.getDotTransitions());
		}
		line.append("}\n");
		//System.out.println("}\n");
		for (ExitPoint extp : this.getExitPoints()) {
			//System.out.println(this.getLabel() + " -> " + extp.getTargetState().getLabel() + " [label = \"" + extp.getLtlf() + "\"];");
			line.append(this.getLabel() + " -> " + extp.getTargetState().getLabel() + " [label = \"" + extp.getLtlf() + "\"];\n");
		}
		return line.toString();
	}
	
	private String getDotSons(){
		StringBuffer line = new StringBuffer();
		IDevCState son;
		for (String sonN : this.getSons().keySet()) {
			son = this.getSons().get(sonN);
			line.append(son.getLabel() + "\n");
		}
		return line.toString();
	}

	@Override
	public void addInitialSon(IDevCState state) {
		this.initialsSons.add(state);
	}

	@Override
	public Integer getSubStateMachine() {
		return this.subStateMachine;
	}


	@Override
	public void setSubStateMachine(Integer subStateMachine) {
		this.subStateMachine = subStateMachine;
	}

	@Override
	public Integer getLevel() {
		return level;
	}

	@Override
	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public OrthoRegion getHomeLand() {
		return this.homeland;
	}

	@Override
	public HashMap<String, OrthoRegion> getMyOrthoRegions() {
		return this.myOrthoRegions;
	}

	@Override
	public void setHomeland(OrthoRegion region) {
		this.homeland = region;
		
	}

	@Override
	public void addOrthoRegion(OrthoRegion region) throws Exception {
		IDevCState tmpState;
		if (this.getMyOrthoRegions().containsKey(region.getRegionName())){
			//ERRO
		}else{
			this.getMyOrthoRegions().put(region.getRegionName(), region);
			for (String stateName : region.getRegionStates().keySet()) {
				tmpState = region.getRegionStates().get(stateName);
				if (this.sons.containsKey(tmpState)){
					Logger.getGlobal().log(Level.SEVERE, " Duplicated State: \"" + tmpState.getName() + "\"");
					throw new Exception(" Duplicated State: \"" + tmpState.getName() + "\"");
				}else{
					this.addSon(tmpState);
				}
			}
		}
	}
	
	public DevCState() {
		super("");
	}

	@Override
	public String getBitSize() {
		String[] total = Double.toString(Math.ceil(Math.log(this.sons.size()) / Math.log(2))).split("[.]");
		return total[0];
	}
	
}
