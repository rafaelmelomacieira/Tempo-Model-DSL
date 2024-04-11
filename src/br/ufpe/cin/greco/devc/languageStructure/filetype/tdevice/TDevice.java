package br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpe.cin.greco.devc.languageStructure.FileDescriptor;
import br.ufpe.cin.greco.devc.languageStructure.TerminusFileDefinition;
import br.ufpe.cin.greco.devc.languageStructure.ltl.EntryPoint;
import br.ufpe.cin.greco.devc.languageStructure.ltl.ExitPoint;
import br.ufpe.cin.greco.devc.languageStructure.ltl.IDevCState;
import br.ufpe.cin.greco.devc.languageStructure.ltl.OrthoRegion;
import br.ufpe.cin.greco.devc.languageStructure.ltl.Violation;
import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.FieldType;
import br.ufpe.cin.greco.devc.ui.Editor;
import br.ufpe.cin.greco.devc.utils.FileManagement;

public class TDevice extends FileDescriptor  {
	
	private Integer baseAddress;
	private Integer wordSize = 32; //Deve ir para TPlatform
		
	private HashMap<String, Pattern> patterns = new HashMap<String, Pattern>();
	private HashMap<String, Register> registers = new HashMap<String, Register>();
	private HashMap<String, Register> registersAlias = new HashMap<String, Register>();
	private HashMap<String, RegisterFormat> regFormats = new HashMap<String, RegisterFormat>();
	private HashMap<String, Variable> variables = new HashMap<String, Variable>();

	
	public HashMap<String, RegisterFormat> getFormats() {
		return regFormats;
	}
	
	public void addFormat(RegisterFormat regFormat){
		this.regFormats.put(regFormat.getName(), regFormat);
	}
	
	public HashMap<String, Register> getRegisters() {
		return registers;
	}
	
	public void addRegister(Register register){
		if (register != null){
			this.registers.put(register.getName(), register);
			addRegistersAlias(register);
		}
	}
	
	private void addRegistersAlias(Register reg){
		this.registersAlias.put(reg.getAlias(), reg);
	}
	

		
	public HashMap<String, Variable> getVariables(){
		return this.variables;
	}
	
	public void addVariable(Variable var) throws Exception {
		if (this.variables.containsKey(var)){
			Logger.getGlobal().log(Level.SEVERE, " Variable \"" + var.getName() + "\" already defined!");
			throw new Exception(" Variable \"" + var.getName() + "\" already defined!");
		}else{
			System.out.println("VAR: " + var.getName());
			this.variables.put(var.getName(), var);
		}
	}
	
	public HashMap<String, Register> getRegistersAlias() {
		return registersAlias;
	}

	public TDevice(String elementName, FileType fileType) {
		super(elementName, fileType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefinition(TerminusFileDefinition tdef) {
		// TODO Auto-generated method stub
		
	}
	
	
	public Integer getBaseAddress() {
		return baseAddress;
	}

	public String getAbsoluteDir() {
		return absoluteDir;
	}
	
	public HashMap<String, Pattern> getPatterns() {
		return patterns;
	}
	
	public void addPattern(Pattern pattern){
		this.patterns.put(pattern.getName(), pattern);
	}
	
	public void generateRegisters(){
		int i = 0;
		for (Register reg : registers.values()) {
			reg.buildFields(this);
			reg.checkIntegrity();
		}
		createRegistersDotFiles();
		/*for (String serv : bindings.getPosServices().keySet()) {
			System.out.println(serv + " [Pos]-> " + bindings.getPosServices().get(serv));
		}
		for (String serv : bindings.getPreServices().keySet()) {
			System.out.println(serv + " [Pre]-> " + bindings.getPreServices().get(serv));
		}
		for (String serv : bindings.getPosRequirments().keySet()) {
			System.out.println(serv + " [Pos]-> " + bindings.getPosRequirments().get(serv));
		}
		for (String serv : bindings.getPreRequirments().keySet()) {
			System.out.println(serv + " [Pre]-> " + bindings.getPreRequirments().get(serv));
		}
		for (String serv : bindings.getAlwaysRequiredRequirments().keySet()) {
			System.out.println(serv + " [Always]-> " + bindings.getAlwaysRequiredRequirments().get(serv));
		}
		for (String serv : bindings.getAlwaysRequiredServices().keySet()) {
			System.out.println(serv + " [Always]-> " + bindings.getAlwaysRequiredServices().get(serv));
		}*/
	}
	
	private void createRegistersDotFiles(){
		for (String regName : this.getRegisters().keySet()) {
			createRegisterDotFiles(this.getRegisters().get(regName));
		}
	}
		
	public void createRegisterDotFiles(Register reg){
		StringBuffer buf = new StringBuffer();
		int i = 0, regSize = reg.getFields().size();
		buf.append("digraph " + reg.getAlias() + "{\n");
		buf.append("node [shape = \"record\" \n style=\"filled\" ];\n");
		for (Map.Entry<Integer,Field> entry : reg.getSortedFields().entrySet()) {
			//buf.append("<f" + i + "> " + ((Field) entry.getValue()) + (((Field) entry.getValue()).getAccessType()==AccessType.READ?"fontcolor=red":"fontcolor=black"));
			buf.append("node" + i + " [ label = \"" + ((Field) entry.getValue()) + "\"\n");
			//buf.append((Field) entry.getValue());
			if(((Field) entry.getValue()).getType()==FieldType.RESERVED) {
				buf.append(" fillcolor = \"red\"");
			}else if (((Field) entry.getValue()).getAccessType()==AccessType.READ){
				buf.append(" fillcolor = \"yellow\"");
			}else if(((Field) entry.getValue()).getAccessType()==AccessType.WRITE) {
				buf.append(" fillcolor = \"blue\"");
			}else if(((Field) entry.getValue()).getAccessType()==AccessType.READ_WRITE) {
				buf.append(" fillcolor = \"green\"");
			} else{
				buf.append(" fillcolor = \"white\"");
			}
			/*if (i < regSize - 1) {
				buf.append(" | ");
			}*/
			i++;
			buf.append("];\n");
		}
		buf.append("}");
		FileManagement.createDotFile("register_" + reg.getAlias(), "./dotFiles", buf.toString(), true);
		FileManagement.createImageFile("register_" + reg.getAlias(), "./dotFiles", FileManagement.ImageType.PNG, true);
	}

	private void createPromelaFiles(IDevCState state){
		IDevCState currentState;
		StringBuffer promelaContent = new StringBuffer();
		StringBuffer promelaContentTmp = new StringBuffer();
		int counter = 0, violationsSize;
		String level;
		for (String regName : this.getRegisters().keySet()) {
			counter++;
			promelaContentTmp.append("reg%" + this.getRegisters().get(regName).getAlias() + " " +  new String(new char[this.getRegisters().get(regName).getSize()]).replace("\0", ".") + "\n");
			counter += RegFieldsForCheckingFiles(this.getRegisters().get(regName).getAlias(),this.getRegisters().get(regName), promelaContentTmp);
		}
		for (String varName : this.getVariables().keySet()) {
			counter++;
			promelaContentTmp.append("var%" + varName  + " " +  new String(new char[this.wordSize]).replace("\0", ".") + "\n");
		}
		for (String patName : this.getPatterns().keySet()) {
			counter++;
			promelaContentTmp.append("pat%" + patName + " " + this.getPatterns().get(patName).getMask() + "\n");
		}
		promelaContent.append(counter + "\n");
		promelaContent.append(promelaContentTmp.toString());
		promelaContent.append(getLTLPropositionsLib().size() + "\n");
		try{
			for (int i = 0; i < getLTLPropositionsLib().size(); i++) {
				promelaContent.append(getValidationFormatProposition(getLTLPropositionsLib().get("q" + i)) + "\n");
			}
			//promelaContent.append(state.getInheritors().size() + "\n");
			promelaContent.append(promelaDepthSearch(state,0));
			FileManagement.createPromelaFile("promelaFile_" + this.getElementName(), null, promelaContent.toString(), true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//aqui vanessa chama a checagem da HFSMD.
	}
	
	private String promelaDepthSearch(IDevCState state, Integer level){
		Integer stateLevel, violationsAmount;
		StringBuffer buffer = new StringBuffer();
		Iterator<String> vioIter;
		Violation violation;
		vioIter = state.getViolations().keySet().iterator();
		buffer.append(new String(new char[level]).replace("\0", "."));
		if (vioIter.hasNext()){
			violation = state.getViolations().get(vioIter.next());
			buffer.append(state.getName() + "%" + violation.getName() + "%" +  violation.getLtlf());
			while(vioIter.hasNext()){
				violation = state.getViolations().get(vioIter.next());
				buffer.append("%" + state.getName() + "%" + violation.getName() + "%" +  violation.getLtlf());
			}
		}else{
			buffer.append(state.getName());
		}
		buffer.append("\n");
		/*currentState = state.getInheritors().get(stateName);
		promelaContent.append("State(" + stateName + ")\n");
		violationsSize = currentState.getViolations().size();*/
		for (String stateName : state.getInheritors().keySet()) {
			buffer.append(promelaDepthSearch(getGlobalState().getInheritors().get(stateName), level+1));
			/*for (ExitPoint ext : currentState.getExitPoints()) {
				promelaContent.append(ext.getz3Ltlf() + " ");
			}*/
		}
		return buffer.toString();
	}

	private void createZ3Files(IDevCState state){
		IDevCState currentState;
		StringBuffer z3Content = new StringBuffer();
		StringBuffer z3ContentTmp = new StringBuffer();
		int counter = 0;
		for (String regName : this.getRegisters().keySet()) {
			counter++;
			z3ContentTmp.append("reg%" + this.getRegisters().get(regName).getAlias() + " " +  new String(new char[this.getRegisters().get(regName).getSize()]).replace("\0", ".") + "\n");
			counter += RegFieldsForCheckingFiles(this.getRegisters().get(regName).getAlias(),this.getRegisters().get(regName), z3ContentTmp);
		}
		for (String varName : this.getVariables().keySet()) {
			counter++;
			z3ContentTmp.append("var%" + varName  + " " +  new String(new char[this.wordSize]).replace("\0", ".") + "\n");
		}
		for (String patName : this.getPatterns().keySet()) {
			counter++;
			z3ContentTmp.append("pat%" + patName + " " + this.getPatterns().get(patName).getMask() + "\n");
		}
		z3Content.append(counter + "\n");
		z3Content.append(z3ContentTmp.toString());
		z3Content.append(getPropositionsLib().size() + "\n");
		try{
			for (int i = 0; i < getPropositionsLib().size(); i++) {
				z3Content.append(getValidationFormatProposition(getPropositionsLib().get("p" + i)) + "\n");
			}
			z3Content.append(state.getInheritors().size() + "\n");
			for (String stateName : state.getInheritors().keySet()) {
				currentState = state.getInheritors().get(stateName);
				z3Content.append("State(" + stateName + ")\n");
				for (ExitPoint ext : currentState.getExitPoints()) {
					z3Content.append(ext.getz3Ltlf() + " ");
				}
				z3Content.append("\n");
			}
			FileManagement.createZ3File("z3File_" + this.getElementName(), null, z3Content.toString(), true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//aqui vanessa chama a checagem da HFSMD.
	}
	
	private Integer RegFieldsForCheckingFiles(String father, Field field, StringBuffer z3Content){
		Integer counter = 0;
		for (String fieldName : field.getFields().keySet()) {
			if (field.getFields().get(fieldName).getType()!=FieldType.RESERVED) {
				counter++;
				z3Content.append("cmp%" + father + "%" + fieldName + "\n");
				counter += RegFieldsForCheckingFiles(father + "%" + fieldName, field.getFields().get(fieldName),z3Content);
			}
		}
		return counter;
	}
	
	private String getValidationFormatProposition(String prop) throws Exception{
		//proposicoes no formato cmp_ para campo; est_ para estado; reg_ para registrador e var_ para variavel
		String[] typeProp, segProp = prop.replace("(", "").replace(")", "").split(" ");
		switch (segProp.length) {
		case 1:
			typeProp = segProp[0].split("%");
			if (typeProp.length >= 2){
				if (typeProp[1].contains(".")){
					return typeProp[0] + "%cmp%" + typeProp[1].replace(".", "%");   
				}else{
					return typeProp[0] + "%reg%" + typeProp[1].replace(".", "%");
				}
			}else{
				if (typeProp[0].contains(".")){
					return "cmp%" + typeProp[0].replace(".", "%");
				}else{
					if (this.getRegisters().containsKey(typeProp[0]) || this.getRegistersAlias().containsKey(typeProp[0])){
						return "reg%" + typeProp[0];
					}else if (getGlobalState().getInheritors().containsKey(typeProp[0])){
						return "est%" + typeProp[0];
					}else if (this.getVariables().containsKey(typeProp[0])){
						return "var%" + typeProp[0];
					}else{
						//System.err.println(typeProp[0] + " in " + prop);
						throw new Exception("Inexistent Property:" + typeProp[0] + " in " + prop);
					}
				}
			}
		case 3:
			String stage1;
			typeProp = segProp[0].split("%");
			if (typeProp.length >= 2){
				if (typeProp[1].contains(".")){
					stage1 = typeProp[0] + "%cmp%" + typeProp[1].replace(".", "%");   
				}else{
					stage1 = typeProp[0] + "%reg%" + typeProp[1].replace(".", "%");
				}
			}else{
				if (typeProp[0].contains(".")){
					stage1 = "cmp%" + typeProp[0].replace(".", "%");
				}else{
					if (this.getRegisters().containsKey(typeProp[0]) || getRegistersAlias().containsKey(typeProp[0])){
						stage1 = "reg%" + typeProp[0];
					}else if (getGlobalState().getInheritors().containsKey(typeProp[0])){
						stage1 = "est%" + typeProp[0];
					}else if (this.getVariables().containsKey(typeProp[0])){
						stage1 = "var%" + typeProp[0];
					}else{
						//System.err.println(typeProp[0]);
						throw new Exception("Inexistent Proposition Term:" + typeProp[0] + " in " + prop);
					}
				}
			}
			typeProp = segProp[2].split("%");
			if (typeProp.length >= 2){
				if (typeProp[1].contains(".")){
					return stage1 + " " + segProp[1] + " " + typeProp[0] + "%cmp%" + typeProp[1];   
				}else{
					return stage1 + " " + segProp[1] + " " + typeProp[0] + "%reg%" + typeProp[1];
				}
			}else{
				if (typeProp[0].contains(".")){
					return stage1 + " " + segProp[1] + " cmp%" + typeProp[0];
				}else{
					if (this.getRegisters().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " reg%" + typeProp[0];
					}else if (getGlobalState().getInheritors().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " est%" + typeProp[0];
					}else if (this.getVariables().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " var%" + typeProp[0];
					}else if (this.getPatterns().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " pat%" + typeProp[0];
					}else{
						return stage1 + " " + segProp[1] + " " + resolveIntegerFormat(typeProp[0]);
					}
				}
			}
	
		default:
			return getValidationFormatProposition(segProp[0] + " " + segProp[1] + " " + segProp[2]);
			//throw new Exception("falha [" + prop + "] Size:" + segProp.length);
		}
	}
	
	public void validateStates(IDevCState state, String ltls) throws Exception{
		String cltl;
		//int initial = -1;
		Integer counter = 0;
		state.setLiteral_rules(ltls);
		IDevCState son, tmptarget;
		if (!state.getEntryPoints().isEmpty()){
			getEntryPoints().put(state, state.getEntryPoints());
		}
		for (ExitPoint extp : state.getExitPoints()) {
			if (getGlobalState().getInheritors().containsKey(extp.getTarget())){
				tmptarget = getGlobalState().getInheritors().get(extp.getTarget());
				if (state.getHomeLand() == tmptarget.getHomeLand()){
					extp.setTargetState(tmptarget);
				}else{
					Logger.getGlobal().log(Level.SEVERE, " Merging of two Orthogonl Region at \"" + state.getName() + " -> " + extp.getTarget() + "\"");
					throw new Exception(" Merging of two Orthogonl Region at \"" + state.getName() + " -> " + extp.getTarget() + "\"");
				}
			}else{
				Logger.getGlobal().log(Level.SEVERE, " State \"" + extp.getTarget() + "\" at \"" + state.getName() + " -> " + extp.getTarget() + "\" does not exists!");
				throw new Exception(" State \"" + extp.getTarget() + "\" at \"" + state.getName() + " -> " + extp.getTarget() + "\" does not exists!");
			}
		}
		for (String key : state.getSons().keySet()) {
			son = state.getSons().get(key);
			son.setLevel(state.getLevel() + 1);
			/*if (initial == -1){
				initial = 0;
			}*/
			if (!son.getViolations().isEmpty()){
				cltl = new String(ltls + " && " + mergeLTLf(son));
			}else{
				cltl = new String(ltls);
			}
			/*if (son.isInitial()){
				state.addInitialSon(son);
			}*/
			validateStates(son, cltl);
		}
		/*if (initial != -1 && initial != 1){
			Logger.getGlobal().log(Level.SEVERE, "[Specification Error] State " + state.getName() + " with " + initial + " Initial State(s)\n");
		}*/
	}
	
	public void checkSemantics(){
		StringBuffer readVal = new StringBuffer();
		try{
			IDevCState gs = getGlobalState();
			validateStates(gs,mergeLTLf(gs));
			appendEntryPoints(gs);
			//linkInitialStates(globalState);
			for (String prop : getPropositionsLib().keySet()) {
				System.out.println(prop + " - " + getPropositionsLib().get(prop));
			}
			createZ3Files(gs);
			createPromelaFiles(gs);
			checkForModelsInconsistencies(gs);
			checkForPropertiesContradictions();
			createViolationsDotFiles(gs);
			createBehaviorsDotFiles(gs);
			createOrthogonalRegionDotFiles(gs);
			/*for (String ltl: ltlPropositionsLib.keySet()) {
				System.out.println(ltl + ": " + ltlPropositionsLib.get(ltl));
			}*/
			Runtime rt = Runtime.getRuntime();
			Process ps = rt.exec("/bin/ls \"/home\"");
			while(ps.getInputStream().read() != -1){
				readVal.append((char) ps.getInputStream().read());
			}
			Editor.terminalMsg(String.valueOf(readVal));
			FileManagement.createDotFile("globalStateGraph", "./dotFiles", getGlobalState().getFullFSM(), true);
			FileManagement.createImageFile("globalStateGraph", "./dotFiles", FileManagement.ImageType.PNG, true);
			//generateMDDCSourceCode(); FOI PARA O TDCCheckerGen
		}catch (Exception e){
			System.err.println("Falha: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Integer resolveIntegerFormat(String value){
		if (value.matches("[0-9]+")){
			return Integer.parseInt(value,10);
		}else if (value.matches("[0][x][0-9abcdefABCDEF]+")){
			return Integer.parseInt(value.substring(2, value.length()),16);
		}else if (value.matches("[b][01]+")){
			return Integer.parseInt(value.substring(1, value.length()),2);
		}
		return null;
	}
	
	protected void checkForModelsInconsistencies(IDevCState state){
		System.out.println("Checking for Inconsistencies");
	}
	
	
	
		
	protected void createViolationsDotFiles(IDevCState state) {
		Violation viol;
		for (String vioName : state.getViolations().keySet()) {
			viol = state.getViolations().get(vioName);
			//REMOVER  FileManagement.createDotFile("violation_" + viol.getName(), "./dotFiles", DottyWriter.automatonToDot(LTL2BA4J.formulaToBA(viol.getNegatedLtlf())), true);
			FileManagement.createImageFile("violation_" + viol.getName(), "./dotFiles", FileManagement.ImageType.PNG, true);
		}
		for (String sonName : state.getSons().keySet()) {
			createViolationsDotFiles(state.getSons().get(sonName));
		}
	}
	
	protected void createBehaviorsDotFiles(IDevCState state) {
		br.ufpe.cin.greco.devc.languageStructure.ltl.Behavior behavior;
		for (String behaveName : state.getBehaviors().keySet()) {
			behavior = state.getBehaviors().get(behaveName);
//REMOVER			FileManagement.createDotFile("behavior_" + behavior.getName(), "./dotFiles", DottyWriter.automatonToDot(LTL2BA4J.formulaToBA(behavior.getLtlf())), true);
			FileManagement.createImageFile("behavior_" + behavior.getName(), "./dotFiles", FileManagement.ImageType.PNG, true);
		}
		for (String sonName : state.getSons().keySet()) {
			createBehaviorsDotFiles(state.getSons().get(sonName));
		}
	}
	
	protected void createOrthogonalRegionDotFiles(IDevCState state){
		OrthoRegion tmpReg;
		for (String regName : state.getMyOrthoRegions().keySet()) {
			tmpReg = state.getMyOrthoRegions().get(regName);
			FileManagement.createDotFile("orthoReg_" + tmpReg.getRegionName(), "./dotFiles", tmpReg.getRegionInitialState().getFullFSM(), true);
			FileManagement.createImageFile("orthoReg_" + tmpReg.getRegionName(), "./dotFiles", FileManagement.ImageType.PNG, true);
		}
		for (String sonName : state.getSons().keySet()) {
			createOrthogonalRegionDotFiles(state.getSons().get(sonName));
		}
	}

	private void linkInitialStates(IDevCState state) {
		IDevCState target;
		for (ExitPoint extp : state.getExitPoints()) {
			target = extp.getTargetState();
			//System.out.println(state.getName() + " -> " + target.getName());
			//System.out.println(state.getSons() + " -> " + target.getSons());
			if (!target.getSons().isEmpty()){
				for (IDevCState son : target.getInitialsSons()) {
					extp.setTargetState(son);
					extp.setTarget(son.getName());
				}
			}else{
				extp.setTargetState(target);
				extp.setTarget(target.getName());
			}
		}
		for (String key : state.getSons().keySet()) {
			linkInitialStates(state.getSons().get(key));
		}
	}

	private String mergeLTLf(IDevCState state){
		int ltlNum = state.getViolations().size();
		int i = 0;
		StringBuffer sb = new StringBuffer();
		for (String keyset : state.getViolations().keySet()) {
			sb.append(state.getViolations().get(keyset).getLtlf());
			i++;
			if (i < ltlNum) {
				sb.append(" && ");
			}
		}
		return sb.toString();
	}
	
	protected void appendEntryPoints(IDevCState state){
		ExitPoint exp;
		IDevCState source, target;
		//if(state.getSons().isEmpty()){
		
		for (IDevCState key : getEntryPoints().keySet()) {
			for (String sourceName : state.getInheritors().keySet()){
				source = state.getInheritors().get(sourceName);
				if (source.getLevel() == key.getLevel() && source.getName() != key.getName() && source.getHomeLand() == key.getHomeLand()){
					for (EntryPoint eKey : getEntryPoints().get(key)) {
						//System.out.println("[ENTRY]: " + source.getName() + "[" + this.getPropositionsLib().get(eKey.getLtlf()) + "] -> " + key.getName());
						exp = new ExitPoint(key.getName(), eKey.getLtlf(), eKey.getz3Ltlf(),  true);
						exp.setTargetState(state.getInheritors().get(key.getName()));
						source.getExitPoints().add(exp);
						}
				}
			}
		}
		/*if (!state.getName().equalsIgnoreCase(key.getName())){
				for (EntryPoint eKey : entryPoints.get(key)) {
					exp = new ExitPoint(key.getName(), eKey.getLtlf());
					//System.out.println(state.getLabel() + " -> " + key.getLabel());
					exp.setTargetState(this.globalState.getInheritors().get(key.getName()));
					state.getExitPoints().add(exp);
				}
			}
		}*/
		//}
		/*for (ExitPoint ext : state.getExitPoints()) {
			//System.out.println(state.getLabel() + " -> " + ext.getTargetState().getLabel() + " [ label = \"" + ext.getLtlf() + "\"];");
			//System.out.println(state.getLabel());
		}*/
		
		/*for (String key : state.getSons().keySet()) {
			if (state.getSons().isEmpty()){
			}
			if (state.getSons().get(key).getSons().size() == 0){
				appendEntryPoints(state.getSons().get(key));
			}else{
				appendEntryPoints(state.getSons().get(key));
			}
		}*/
	}
	
	protected void checkForPropertiesContradictions(){
		//aqui vanessa chama a checagem de LTLs.
		System.out.println("Checking for Contradictions");
	}

}
