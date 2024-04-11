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
import br.ufpe.cin.greco.devc.languageStructure.ltl.Violation;
import br.ufpe.cin.greco.devc.languageStructure.type.AccessType;
import br.ufpe.cin.greco.devc.languageStructure.type.FieldType;
import br.ufpe.cin.greco.devc.ui.Editor;
import br.ufpe.cin.greco.devc.utils.FileManagement;

public class TDevice extends FileDescriptor  {
	
	private Integer baseAddress;
	
		
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
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public TDevice(String projectName, FileType fileType) {
		super(projectName, fileType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefinition(TerminusFileDefinition tdef) {
		// TODO Auto-generated method stub
		
	}
	
	

	public String getAppName() {
		return appName;
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
			FileManagement.createPromelaFile("promelaFile_" + this.getProjectName(), null, promelaContent.toString(), true);
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
		z3Content.append(propositionsLib.size() + "\n");
		try{
			for (int i = 0; i < propositionsLib.size(); i++) {
				z3Content.append(getValidationFormatProposition(propositionsLib.get("p" + i)) + "\n");
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
			FileManagement.createZ3File("z3File_" + this.getProjectName(), null, z3Content.toString(), true);
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
					if (this.getRegisters().containsKey(typeProp[0]) || tDevice.getRegistersAlias().containsKey(typeProp[0])){
						return "reg%" + typeProp[0];
					}else if (globalState.getInheritors().containsKey(typeProp[0])){
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
					if (this.getRegisters().containsKey(typeProp[0]) || tDevice.getRegistersAlias().containsKey(typeProp[0])){
						stage1 = "reg%" + typeProp[0];
					}else if (globalState.getInheritors().containsKey(typeProp[0])){
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
					if (tDevice.getRegisters().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " reg%" + typeProp[0];
					}else if (globalState.getInheritors().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " est%" + typeProp[0];
					}else if (tDevice.getVariables().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " var%" + typeProp[0];
					}else if (getPatterns().containsKey(typeProp[0])){
						return stage1 + " " + segProp[1] + " pat%" + typeProp[0];
					}else{
						return stage1 + " " + segProp[1] + " " + resolveIntegerFormat(typeProp[0]);
					}
				}
			}
	
		default:
			return getValidationFormatProposition(segProp[0] + " " + segProp[1] + " " + segProp[2], tDevice);
			//throw new Exception("falha [" + prop + "] Size:" + segProp.length);
		}
	}
	
	ublic void validateStates(IDevCState state, String ltls) throws Exception{
		String cltl;
		//int initial = -1;
		Integer counter = 0;
		state.setLiteral_rules(ltls);
		IDevCState son, tmptarget;
		if (!state.getEntryPoints().isEmpty()){
			entryPoints.put(state, state.getEntryPoints());
		}
		for (ExitPoint extp : state.getExitPoints()) {
			if (this.globalState.getInheritors().containsKey(extp.getTarget())){
				tmptarget = this.globalState.getInheritors().get(extp.getTarget());
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
			IDevCState gs = this.globalState;
			validateStates(gs,mergeLTLf(globalState));
			appendEntryPoints(globalState);
			//linkInitialStates(globalState);
			for (String prop : tDevice.getPropositionsLib().keySet()) {
				System.out.println(prop + " - " + tDevice.getPropositionsLib().get(prop));
			}
			createZ3Files(globalState);
			createPromelaFiles(globalState);
			checkForModelsInconsistencies(globalState);
			checkForPropertiesContradictions();
			createViolationsDotFiles(globalState);
			createBehaviorsDotFiles(globalState);
			createOrthogonalRegionDotFiles(globalState);
			/*for (String ltl: ltlPropositionsLib.keySet()) {
				System.out.println(ltl + ": " + ltlPropositionsLib.get(ltl));
			}*/
			Runtime rt = Runtime.getRuntime();
			Process ps = rt.exec("/bin/ls \"/home\"");
			while(ps.getInputStream().read() != -1){
				readVal.append((char) ps.getInputStream().read());
			}
			Editor.terminalMsg(String.valueOf(readVal));
			FileManagement.createDotFile("globalStateGraph", "./dotFiles", globalState.getFullFSM(), true);
			FileManagement.createImageFile("globalStateGraph", "./dotFiles", FileManagement.ImageType.PNG, true);
			generateMDDCSourceCode();
		}catch (Exception e){
			System.err.println("Falha: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void checkForPropertiesContradictions(){
		//aqui vanessa chama a checagem de LTLs.
		System.out.println("Checking for Contradictions");
	}

}
