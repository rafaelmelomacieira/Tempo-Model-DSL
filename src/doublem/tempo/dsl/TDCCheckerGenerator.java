package doublem.tempo.dsl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import doublem.tempo.dsl.codeGen.SystemVerilogCodeGen;
import doublem.tempo.dsl.filetype.tdevice.Field;
import doublem.tempo.dsl.filetype.tdevice.FormatEntity;
import doublem.tempo.dsl.filetype.tdevice.Pattern;
import doublem.tempo.dsl.filetype.tdevice.Register;
import doublem.tempo.dsl.filetype.tdevice.RegisterFormat;
import doublem.tempo.dsl.filetype.tdevice.TDevice;
import doublem.tempo.dsl.filetype.tdevice.VarMap;
import doublem.tempo.dsl.filetype.tdevice.Variable;
import doublem.tempo.dsl.ltl.*;
import doublem.tempo.dsl.model.DeviceModel;
import doublem.tempo.dsl.type.AccessType;
import doublem.tempo.dsl.type.CodeLanguage;
import doublem.tempo.dsl.type.FieldType;
import doublem.tempo.gui.Editor;
import doublem.tempo.utils.FileManagement;
import rwth.i2.ltl2ba4j.DottyWriter;
import rwth.i2.ltl2ba4j.LTL2BA4J;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;


public class TDCCheckerGenerator {

	private String absoluteDir;
	private String appName;
	private String projectName;
	public HashSet<FileDescriptor> getFiles() {
		return files;
	}


	public void setFiles(HashSet<FileDescriptor> files) {
		this.files = files;
	}

	private String kernelImageDir;
	public String getProjectName() {
		return projectName;
	}

	private HashSet<FileDescriptor> files = new HashSet<FileDescriptor>();

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	private CodeLanguage codelanguage;
	
	
	private IDevCState globalState;
	private int nc = 0;

	public String getKernelImageDir() {
		return kernelImageDir;
	}
	
	/*private static TDCCheckerGenerator instance;
	
	static{
		instance = new TDCCheckerGenerator();
	}*/

	private double initialTime;
	//private Service currentService;
	private BufferedWriter bw;
	private File tmpFile;
	private final boolean HEADERS = true;

	//@Deprecated
	//private Behaviors behaviors;
	//@Deprecated
	//private Bindings bindings;
	//@Deprecated
	//private HashMap<String, DeviceException> exceptions = new HashMap<String, DeviceException>();
	//@Deprecated
	//private HashMap<String, DeviceRequirement> requirements = new HashMap<String, DeviceRequirement>();
	//@Deprecated
	//private HashMap<String, Service> services = new HashMap<String, Service>();
	@Deprecated
	private HashMap<String, FormatEntity> formats = new HashMap<String, FormatEntity>();
	@Deprecated
	private HashMap<String, VarMap> varMaps = new HashMap<String, VarMap>();
	//@Deprecated
	//private HashSet<ServiceNode> initialServiceNodes = new HashSet<ServiceNode>();
	//@Deprecated
	//private MethodDefinition metDefinition;

	
	public IDevCState getGlobalState() {
		return globalState;
	}
	
	

	public void setGlobalState(IDevCState gs){
		this.globalState = gs;
		this.globalState.setLevel(1);
		this.globalState.setSubStateMachine(0);
	}
	
	public void generateCode() throws IOException {
		for (FileDescriptor file : getFiles()) {
			generateMDDCSourceCode(file); //DEVE IR PARA O MAIN da geração
		}
	}
		
	public void generateMDDCSourceCode(FileDescriptor fileDesc) throws IOException{
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setDirectoryForTemplateLoading(new File("templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		SystemVerilogCodeGen sv = new SystemVerilogCodeGen();
		sv.setConfiguration(cfg);
		DeviceModel devmodel = new DeviceModel(this.getProjectName());
		for (String regname : fileDesc.getRegisters().keySet()) {
			devmodel.addRegister(fileDesc.getRegisters().get(regname));
		}
		devmodel.setGlobalState(globalState);
		devmodel.setHfsmProp(fileDesc.getPropositionsLib());
		devmodel.setHfsmProp(fileDesc.getPropositionsLib());
		sv.getDevicesFSMSourceCode(devmodel);
	}
	
	/*private void subStateMachineSegmentation(IDevCState state){
		Integer counter = 0;
		IDevCState son;
		System.out.println("State: " + state.getName() + ": " + state.getInitialsSons().size());
		for (String sonName : state.getSons().keySet()) {
			son = state.getSons().get(sonName);
			if (son.isInitial()){
				son.setSubStateMachine(++counter);
				System.out.println(son.getName() + "->" + son.getSubStateMachine());
				subStateMachineSegmentation(son);
			}
		}
	}
	
	private void runSubStateMachine(IDevCState state) throws Exception{
		IDevCState son;
		for (String sonName : state.getSons().keySet()) {
			son = state.getSons().get(sonName);
			if (son.isInitial()){
				System.out.println(state.getName() + (son.isInitial()?" *X ":" X ") + son.getName());
				//runSubStateMachineFromInitialSon(son);
			}
			runSubStateMachine(son);
			System.out.println("From Father: " + state.getName() + " INTO Son: " + son.getName());
		}
	}
	
	private void runSubStateMachineFromInitialSon(IDevCState initialState) throws Exception{
		IDevCState target;
		for (ExitPoint ep : initialState.getExitPoints()) {
			target = ep.getTargetState();
			if(target.getLevel() == initialState.getLevel()){
				if (target.getSubStateMachine() == -1){
					if (target.getLevel() == initialState.getLevel()){
						if (target.isInitial() && !ep.isFromEntryPoint()){
							Logger.getGlobal().log(Level.SEVERE, " Merging of two diferents execution lines at \"" + initialState.getName() + " -> " + target.getName() + "\"");
							throw new Exception(" Merging of two diferents execution lines at \"" + initialState.getName() + " -> " + target.getName() + "\"");
						}else{
							System.out.println("[" + initialState.getLevel() + "]" + initialState.getName() + " -> " + "[" + initialState.getLevel() + "]" + target.getName() + "[" + target.getSubStateMachine() + "]");
							target.setSubStateMachine(initialState.getSubStateMachine());
							runSubStateMachineFromInitialSon(target);
						}
					}else{
						Logger.getGlobal().log(Level.SEVERE, " Link between states in different HSM levels \"" + initialState.getName() + " -> " + target.getName() + "\"");
						throw new Exception(" Link between states in different HSM levels \"" + initialState.getName() + " -> " + target.getName() + "\"");
					}
				}else{
					if (!target.getSubStateMachine().equals(target.getSubStateMachine())){
						Logger.getGlobal().log(Level.SEVERE, " Merging of two diferents execution lines at \"" + initialState.getName() + " -> " + target.getName() + "\"");
						throw new Exception(" Merging of two diferents execution lines at \"" + initialState.getName() + " -> " + target.getName() + "\"");
					}else{
						System.out.println("[" + initialState.getLevel() + "]" + initialState.getName() + " -> " + "[" + initialState.getLevel() + "]" + target.getName() + "[" + target.getSubStateMachine() + "]*");
					}
				}
			}else{
				Logger.getGlobal().log(Level.INFO, " State " + target.getName() + " at \"" + initialState.getName() + " -> " + target.getName() + "\" out of scope");
				throw new Exception(" State " + target.getName() + " at \"" + initialState.getName() + " -> " + target.getName() + "\" out of scope");
			}
		}
	}*/
	
	
	//public TDCCheckerGenerator(MethodDefinition metDef) {
	public TDCCheckerGenerator(){}
	
	public void bindVariableToFields(){
		
	}
	
	
	
	/*public HashMap<String, DeviceException> getExceptions() {
		return exceptions;
	}
	
	public HashMap<String, DeviceRequirement> getRequirements() {
		return requirements;
	}*/
	
	
	
	/*public void addRequirement(DeviceRequirement req){
		this.requirements.put(req.getName(), req);
	}
	
	public void addException(DeviceException exc){
		this.exceptions.put(exc.getName(), exc);
	}*/
	
	/*public static TDCCheckerGenerator getInstance() {
		return instance;
	}*/
	
	/*public Behaviors getBehaviors() {
		return behaviors;
	}
	
	public Bindings getBindings() {
		return bindings;
	}*/
	
	
	
	/*public void setBehavior(Behaviors behaviors){
		this.behaviors = behaviors;
		/*for (String str : behaviors.getActions().keySet()) {
			System.out.println("Actions >> " + str);
			System.out.println("1 Stmt >> " + behaviors.getActions().get(str).getMainStatement());
		}* /
	}*/
	
	/*public void setBindings (Bindings bindings){
		this.bindings = bindings;

		//List all Bindings
		/*for (String pos : bindings.getPre().keySet()) {
			System.out.println("Pre[" + pos + "]");
			for (String resp : bindings.getPre().get(pos)) {
				System.out.println("\t" + resp);
			}
		}
		for (String pos : bindings.getPos().keySet()) {
			System.out.println("Pos[" + pos + "]");
			for (String resp : bindings.getPos().get(pos)) {
				System.out.println("\t" + resp);
			}
		}
		for (String pos : bindings.getAlwaysRequires().keySet()) {
			System.out.println("Always[" + pos + "]");
			for (String resp : bindings.getAlwaysRequires().get(pos)) {
				System.out.println("\t" + resp);
			}
		}
		for (String pos : bindings.getServiceConstraints().keySet()) {
			System.out.println("ServiceConstraint[" + pos + "]");
			for (String resp : bindings.getServiceConstraints().get(pos)) {
				System.out.println("\t" + resp);
			}
		}* /
	}*/
	
	

	/*public Service getCurrentService() {
		return currentService;
	}*/

	

	/*public HashMap<String, Service> getServices() {
		return services;
	}

	public void addVarMap(VarMap varMap){
		this.varMaps.put(varMap.getName(), varMap);
	}

	public void addService(Service service){
		this.services.put(service.getName(), service);
	}*/

	
	

	/*public void addFormat(FormatEntity format){
		this.formats.put(format.getName(), format);
	}*/
	
	
	
	/*public boolean bindServicesDependencies(){
		Service service;
		for (String serviceName : services.keySet()) {
			service = services.get(serviceName);
			for (String preServ : service.getPreServices().keySet()) {
				if (services.get(preServ).getPosServices().size() > 0){
					if (!services.get(preServ).getPosServices().containsKey(serviceName)){
						System.err.println("Failure: Service " + serviceName + " not in Pos " + preServ);
						return false;
					}
				}
			}
			for (String posServ : service.getPosServices().keySet()) {
				if (services.get(posServ).getPreServices().size() > 0){
					if (!services.get(posServ).getPreServices().containsKey(serviceName)){
						System.err.println("Failure: Service " + serviceName + " not in Pre " + posServ);
						return false;
					}
				}
			}
		}
		return true;
	}*/
	
	/*public DDFA generateDDFA(){
		////runBinds();
		if (bindServicesDependencies()){;
			DDFA ddfa = new DDFA(this.projectName);
			//SCDFA
			////ddfa.linkStates();
			return ddfa;
		}else{
			return null;
		}
	}*/

	/*public void generateServiceDependencies(){
		
	/*	HashSet<Service> initial_services = new HashSet<Service>();
		HashSet<ServiceNode> initial_nodes = new HashSet<ServiceNode>();
		HashSet<Service> visited_services = new HashSet<Service>();
		HashSet<Service> visited_nodes = new HashSet<Service>();
		Service tempService;
		ServiceNode tempNode;

		//Select the initial states!
		for (String service : getServices().keySet()) {
			tempService = getServices().get(service);
			if (tempService.isInitial()){
				initial_services.add(tempService);
				tempNode = new ServiceNode(tempService);
				initial_nodes.add(tempNode);
			}
		}

		for (ServiceNode serviceNode : initial_nodes) {
			initialServiceNodes.add(serviceNode);
			visited_services.add(serviceNode.getService());
			serviceDependenciesBind(visited_services,serviceNode.getService());
			visited_nodes.add(serviceNode.getService());
			serviceDependencies(visited_nodes,serviceNode);
		}

		//generateServiceDependenciesGraph(filePath);* /
	}*/

	/*private void serviceDependenciesBind(HashSet<Service> visited_services, Service initial){
		Service service;
		Delay tmpDelay;
		if (initial.getDeniedServices().size() > 0){
			for (String serviceName : getServices().keySet()) {
				if (!initial.getDeniedServices().contains(serviceName)){
					service = this.getServices().get(serviceName);
					if (!initial.getServiceDependencyBind().containsKey(serviceName)){
						tmpDelay = initial.getAllowedServices().get(serviceName);
						if (tmpDelay == null) {
							tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
						}
						initial.addServiceDependency(service, tmpDelay);
						if (!visited_services.contains(service)){
							visited_services.add(service);
							serviceDependenciesBind(visited_services, service);
						}
					}
				}
			}
		}else if (initial.getAllowedServices().size() > 0){
			for (String serviceName : initial.getAllowedServices().keySet()) {
				service = this.getServices().get(serviceName);
				if (!initial.getServiceDependencyBind().containsKey(serviceName)){
					tmpDelay = initial.getAllowedServices().get(serviceName);
					if (tmpDelay == null) {
						tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
					}
					initial.addServiceDependency(service, tmpDelay);
					if (!visited_services.contains(service)){
						visited_services.add(service);
						serviceDependenciesBind(visited_services, service);
					}
				}
			} 
		}else{
			for (String serviceName : getServices().keySet()) {
				service = this.getServices().get(serviceName);
				tmpDelay = initial.getAllowedServices().get(serviceName);
				if (tmpDelay == null) {
					tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
				}
				initial.addServiceDependency(service, tmpDelay);
				if (!visited_services.contains(service)){
					visited_services.add(service);
					serviceDependenciesBind(visited_services, service);
				}
			}
		}
	}*/
	
	/*private void serviceDependencies(HashSet<Service> visited_nodes, ServiceNode initial){
		/*Service service;
		ServiceNode serviceNode;
		Delay tmpDelay;

		if (initial.getService().getDeniedServices().size() > 0){
			for (String serviceName : getServices().keySet()) {
				if (!initial.getService().getDeniedServices().contains(serviceName)){
					service = this.getServices().get(serviceName);
					serviceNode = new ServiceNode(service);
					tmpDelay = initial.getService().getAllowedServices().get(serviceName);
					if (tmpDelay == null) {
						tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
					}
					initial.addAllowedNodes(serviceNode,tmpDelay);
					if (!visited_nodes.contains(serviceNode.getService())){
						visited_nodes.add(serviceNode.getService());
						serviceDependencies(visited_nodes,serviceNode);
					}
				}
			}
		}else if (initial.getService().getAllowedServices().size() > 0){
			for (String serviceName : initial.getService().getAllowedServices().keySet()) {
				service = this.getServices().get(serviceName);
				serviceNode = new ServiceNode(service);
				if (!initial.getAllowedServices().containsKey(serviceNode)){
					tmpDelay = initial.getService().getAllowedServices().get(serviceName);
					if (tmpDelay == null) {
						tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
					}
					initial.addAllowedNodes(serviceNode,tmpDelay);
					if (!visited_nodes.contains(serviceNode.getService())){
						visited_nodes.add(serviceNode.getService());
						serviceDependencies(visited_nodes, serviceNode);
					}
				}
			} 
		}else{
			for (String serviceName : getServices().keySet()) {
				service = this.getServices().get(serviceName);
				serviceNode = new ServiceNode(service);
				tmpDelay = initial.getService().getAllowedServices().get(serviceName);
				if (tmpDelay == null) {
					tmpDelay = new Delay("0", DelayType.S, DelayLimitType.MIN);
				}
				initial.addAllowedNodes(serviceNode,tmpDelay);
				if (!visited_nodes.contains(serviceNode.getService())){
					visited_nodes.add(serviceNode.getService());
					serviceDependencies(visited_nodes,serviceNode);
				}

			}
		}* /
	}*/

	//public DirectedGraph<Service, Delay> generateServiceDependenciesGraph(String filePath){
	/*public void generateServiceDependenciesGraph(String filePath){
		/*StringBuffer buffer = new StringBuffer();
		DirectedGraph<Service, Delay> digraph = new DirectedSparseGraph<Service, Delay>(); 
		buffer.append("digraph services_dependencies {\n");
		buffer.append("node [style=filled];\n");
		HashSet<ServiceNode> visitedNodes = new HashSet<ServiceNode>();
		for (ServiceNode serviceNode : this.initialServiceNodes) {
			if (!visitedNodes.contains(serviceNode)){
				visitedNodes.add(serviceNode);
				digraph.addVertex(serviceNode.getService());
				serviceDependenciesGraph(buffer, serviceNode, visitedNodes);
				visitedNodes = new HashSet<ServiceNode>();
				visitedNodes.add(serviceNode);
				serviceDependenciesGraph(digraph, serviceNode, visitedNodes);
			}
		}
		buffer.append("}\n");
		try {
			File file = new File(filePath);
			if (!file.exists()) file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(buffer.toString());
			bw.flush();
			bw.close();
			return digraph;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return digraph;
		}* /
	}*/
	
	/*private void serviceDependenciesGraph(DirectedGraph<Service, Delay> digraph, ServiceNode serviceNode, HashSet<ServiceNode> visitedNodes){
		for (ServiceNode son : serviceNode.getAllowedServices().keySet()) {
			//digraph.addVertex(serviceNode.getService());
			if (!visitedNodes.contains(son)){
				digraph.addVertex(son.getService());
			}
			digraph.addEdge(serviceNode.getAllowedServices().get(son), serviceNode.getService(), son.getService());
			if (!visitedNodes.contains(son)){
				visitedNodes.add(son);
				serviceDependenciesGraph(digraph, son, visitedNodes);
			}
		}
	}*/
	
	/*private void serviceDependenciesGraph(StringBuffer graphCode, ServiceNode serviceNode, HashSet<ServiceNode> visitedNodes){
		for (ServiceNode son : serviceNode.getAllowedServices().keySet()) {
			graphCode.append(serviceNode.getService().getName() + " -> " + son.getService().getName() + " ");
			if (serviceNode.getAllowedServices().get(son) != null){
				graphCode.append("[label=\"");
				graphCode.append(serviceNode.getAllowedServices().get(son).getLimit().getRefName());
				graphCode.append(" Delay: ");
				graphCode.append(serviceNode.getAllowedServices().get(son).getValue() + " ");
				graphCode.append(serviceNode.getAllowedServices().get(son).getType());
				graphCode.append("\"]");
			}
			graphCode.append(";\n");
			if (!visitedNodes.contains(son)){
				visitedNodes.add(son);
				serviceDependenciesGraph(graphCode, son, visitedNodes);
			}
		}
		/*StringBuffer buffer = new StringBuffer();
		buffer.append("digraph services_dependencies {\n");
		buffer.append("node [style=filled];\n");

		for (String service : getServices().keySet()) {
			if (getServices().get(service).isInitial()){
				buffer.append("node [shape = doublecircle];\n");
				buffer.append(service + ";\n");
				buffer.append("node [shape = circle];\n");
			}
		}

		for (String service : getServices().keySet()) {
			for (String subServ: getServices().keySet()) {
				if (!(subServ.equals(service)) && !(getServices().get(service).getDeniedServices().contains(subServ))) {
					if (getServices().get(service).getAllowedServices().size() == 0){
						buffer.append(service + " -> " + subServ + ";\n");
					}else if (getServices().get(service).getAllowedServices().containsKey(subServ)){
						buffer.append(service + " -> " + subServ);
						if (getServices().get(service).getAllowedServices().containsKey(subServ)){
							if (getServices().get(service).getAllowedServices().get(subServ) != null){
								buffer.append(" [label = \"" + getServices().get(service).getAllowedServices().get(subServ).getLimit().getRefName()
										+ " Delay = " + getServices().get(service).getAllowedServices().get(subServ).getValue() + " " +
										getServices().get(service).getAllowedServices().get(subServ).getType().getRefName() + "\"];\n");
							}else{
								buffer.append(";\n");
							}
						}else{
							buffer.append(";\n");
						}
					}
				}
			}
		}
		buffer.append("}\n");

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/rmm2/graph.dot")));
			bw.write(buffer.toString());
			bw.flush();
			bw.close();
			System.out.println("Ok!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}* /
	}*/

	/*public void runCheckerGeneration(double initialTime, String projectName, Integer baseAddress, String appName, String absoluteDir, String kernelImageDir, String codeType){
		//  
		//final String CUR_SERVICE = "drv_send_data";

		this.projectName = projectName;
		this.baseAddress = baseAddress;
		this.appName = appName;
		this.absoluteDir = absoluteDir;
		this.initialTime = initialTime;
		this.kernelImageDir = kernelImageDir;

		if (codeType.equalsIgnoreCase("c")){
			this.codelanguage = CodeLanguage.C;
		}else if (codeType.equalsIgnoreCase("cpp")){
			this.codelanguage = CodeLanguage.CPP;
		}else if (codeType.equalsIgnoreCase("verilog")){
			this.codelanguage = CodeLanguage.VERILOG;
		}else if (codeType.equalsIgnoreCase("sverilog")){
			this.codelanguage = CodeLanguage.SYSTEMVERILOG;
		}else if (codeType.equalsIgnoreCase("vhdl")){
			this.codelanguage = CodeLanguage.VHDL;
		}else{
			this.codelanguage = CodeLanguage.C;
		}

		printHeader();
		////runBinds();
		genAutomatas();
		genPayloadActions();
		//String resp = new TDEVCAutomataEngine().generate(this);
		//String resp = new TDEVCAutomatas().generate(services.get("drv_send_data"));
		//this.currentService = services.get(CUR_SERVICE);
		//this.currentService.getFormat().getParams().size();
		//String resp = new TDEVCAutomataEngine().generate(this);
		/*String resp = new TDEVCAutomataEngine().generate(this);
		resp = resp + "\n\n===============================================================================\n";
		resp = resp + "===============================================================================\n\n";
		resp = resp + new TDEVCHeaderAutomataSuperClass().generate(this);
		resp = resp + "\n\n===============================================================================\n";
		resp = resp + "===============================================================================\n\n";
		resp = resp + new TDEVCAutomataSuperClass().generate(this);
		resp = resp + "\n\n===============================================================================\n";
		resp = resp + "===============================================================================\n\n";
		resp = resp + new TDEVCHeaderAutomatas().generate(this);
		resp = resp + "\n\n===============================================================================\n";
		resp = resp + "===============================================================================\n\n";
		resp = resp + new TDEVCAutomatas().generate(this);* /
		generateAutomatonsCode(this.codelanguage);
		//System.out.println(resp);
		printBottom();
	}*/

	/*public VariableField getVariableFieldByRef(String reference) throws UnknownVariableField {
		String[] pieces = reference.split("[.]");
		switch (pieces.length) {
		case 3:
			return this.varMaps.get(pieces[0]).getVariable(pieces[1]).getField(pieces[2]);
		case 2:
			return this.varMaps.get(pieces[0]).getVariable(pieces[1]).getUniqueField();
		default:
			return null; //EXCEPTION
		}
	}*/

	/*public HashMap<String,String> getAllFieldVariableAssignment() {
		HashMap<String,String> signatures = new HashMap<String,String>();
		Variable var;
		VariableField varField;
		Field field;
		try {
			for (String varmap : this.varMaps.keySet()) {
				for (String vari : this.varMaps.get(varmap).getVariables().keySet()) {
					var = this.varMaps.get(varmap).getVariable(vari);
					for (String fieldS : var.getFields().keySet()) {
						varField = var.getField(fieldS);
						field = varField.getField();
						if (field != null){
							signatures.put(varField.getCodeRef(this.codelanguage,true,""),
									field.getFieldSignature(varField.getRegister().getFormat().getFormatName()) 
									+ "(" + varField.getRegister().getName()
									+ ")");
						}
					}
				}
			}
			return signatures;
		} catch (UnknownVariableField e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return signatures;
		} catch (NoRegisterFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return signatures;
		}
	}*/

	/*public HashSet<String> getAllFieldMaskSignatures() {
		HashSet<String> signatures = new HashSet<String>();
		RegisterFormat rfmt;
		for (String rf : getAllRegisterFormats().keySet()) {
			rfmt = getAllRegisterFormats().get(rf);
			if (rfmt.itHasMask()) {
				signatures.add(rfmt.getMaskMatchSignature() + "(reg)\t\t\t"
						+ rfmt.getMaskMatchCode());
			}
			/*
			 * for (String field : rfmt.getFields().keySet()) {
			 * signatures.add(getAllRegisterFormats
			 * ().get(rf).getFieldSignature(field) + "(reg)\t\t\t" +
			 * getAllRegisterFormats().get(rf).getFieldCode( field)); }
			 * /
		}
		return signatures;

	}*/

	/*public HashSet<String> getAllFieldSignatures() {
		HashSet<String> signatures = new HashSet<String>();
		RegisterFormat rfmt;
		try {
			for (String rf : getAllRegisterFormats().keySet()) {
				rfmt = getAllRegisterFormats().get(rf);
				for (String field : rfmt.getFields().keySet()) {
					signatures.add(getAllRegisterFormats().get(rf).getFieldSignature(field)
							+ "(reg)\t\t\t"
							+ getAllRegisterFormats().get(rf).getFieldCode(
									field));
				}
			}
			return signatures;
		} catch (NoRegisterFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return signatures;
		}
	}*/

	/*public HashMap<String,RegisterFormat> getAllRegisterFormats(){
		HashMap<String,RegisterFormat> formats = new HashMap<String, RegisterFormat>();
		RegisterFormat rf;
		for (String registerName : getRegisters().keySet()) {
			rf = getRegisters().get(registerName).getFormat();
			if (formats.containsKey(rf.getFormatName())){
				continue;
			}else{
				formats.put(rf.getFormatName(), rf);
			}
		}
		return formats;
	}*/

	/*private void doRegisterBind() throws WrongPatternException{
		Register register;
		for (String regName : this.registers.keySet()) {
			register = this.registers.get(regName);
			register.getFormat().processPattern(this.formats.get(register.getFormat().getFormatName()).getPattern());
		}
	}*/

	/*private void doFieldBind() throws NoRegisterFieldException {
		VarMap varMap;
		Variable var;
		VariableField varField;
		for (String stringVarMap : this.varMaps.keySet()) {
			varMap = this.varMaps.get(stringVarMap);
			for (String stringVar : varMap.getVariables().keySet()) {
				var = varMap.getVariables().get(stringVar);
				for (String stringVarField : var.getFields().keySet()) {
					varField = var.getFields().get(stringVarField);
					if (varField.getType() == VariableFieldType.ALIAS){
						varField.setRegister(this.registers.get(varField.getSource()));
						varField.setField(this.registers.get(varField.getSource()).getFormat().getField(varField.getAlias()));
					}else{
						varField.setRegister(this.registers.get(varField.getSource()));
					}
				}
			}
		}
	}*/

	/*private void doServicesBinds() throws BindException, UnknownVariableField, VoidServiceFormatException, WrongPatternException {
		Service service;
		HashMap<ActionType,Action> actions;
		HashMap<ActionType,Action> newActions = new HashMap<ActionType, Action>();
		Action action;
		for (String serviceName : this.services.keySet()) {
			newActions = new HashMap<ActionType, Action>();
			//System.out.print("-> " + serviceName+ " _ ");
			service = this.services.get(serviceName);
			actions = this.metDefinition.getActions(serviceName);
			for (ActionType actionType : actions.keySet()) {
				/*switch (actionType){
				case PRE:
					System.out.print(" [PRE] ");
					break;
				case POS:
					System.out.print(" [POS] ");
					break;
				default:
					System.out.print(" [ X ] ");
					break;
				}* /
				action = actions.get(actionType);
				////if (action.getMainStatement().getStatementNode(actionType) != null){
				//System.out.println("> " + action.getServiceName());
				//System.out.println(action.getMainStatement().getStatementNode(actionType).getInitial().getName());
				doStatementBind(action.getMainStatement());
				newActions.put(actionType, action);
				////}
			}
			service.setActions(newActions);
			if (service.getType() != AccessType.VOID){
				service.getFormat().processPattern(this.formats.get(service.getFormat().getName()).getPattern());
				service.setFieldBind(getVariableFieldByRef(metDefinition.getServiceBind(serviceName).getVarFieldID())); //PODE SER NULO!
			}
		}
	}*/

	/*private void doStatementBind(Statement first) throws BindException, UnknownVariableField{
		first.doBinds(this);
		if(first.hasNext()){
			doStatementBind(first.getNextStatement());
		}
	}

	private void genAutomatas() {
		Service service;
		//Action action;
		////Node node;
		HashSet<String> visitedNodes = new HashSet<String>();
		//System.out.println("digraph B {");
		for (String serviceName : this.services.keySet()) {
			service = this.services.get(serviceName);
			for (ActionType actionType : service.getActions().keySet()) {
				service.getActions().get(actionType).genStatementStates();
				////node = service.getActions().get(actionType).genStatementStates();
				////runNode(node,visitedNodes);
			}
		}
		//System.out.println("}");
	}*/

	/*public void runNode(Node node, HashSet<String> visitedNodes){
		//System.out.println("Edges: " + node.getEdges().size());
		for (Edge edge : node.getEdges()) {
			//System.out.println(edge.getFrom().getName() + " -> " + edge.getTo().getName() + " [label = \""  + (edge.isNot()?"¬":"") + edge.getTransitions().get(0).toString() + "\",fontsize=8];");
			//System.out.print(edge.getFrom().getName() + " -> " + edge.getTo().getName() + " [label = \""  + (edge.isNot()?"¬":""));
			for (Statement stmt : edge.getTransitions()) {
				if (stmt instanceof Condition){
					//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					Condition cond = (Condition) stmt;
					///System.out.print(cond.toString() + "aaaaaaaaaaaaaaaaaaaaaaaaaaaa2222222222222222222");
					while (cond.hasNextCondition()){
						///System.out.print(" ((((((((((((((" + cond.getNextLogicOper() + " ");
						cond = cond.getNextCondition();
						///System.out.print(cond.toString());
					}
				}else{
					///System.out.print(stmt.toString());
				}
			}
			///System.out.println("\",fontsize=8];");
			/*for (Statement stmt : edge.getTransitions()) {
				System.out.println(stmt);
			}* /
			if (!visitedNodes.contains(edge.getTo().getName())){
				visitedNodes.add(edge.getTo().getName());
				runNode(edge.getTo(),visitedNodes);
			}
		}
	}*/

	/*private void doAddressBind(){
		for (String reg : getRegisters().keySet()) {
			try {
				getRegisters().get(reg).setAddress(Integer.parseInt(metDefinition.getAddressBind(reg).getAddress()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

	/*private void genPayloadActions(){
		Service service;
		for (String serviceName : this.getServices().keySet()) {
			service = this.getServices().get(serviceName);
			try {
				service.doPayloadActions();
			} catch (NoPayloadActionAvailable e) {
				// TODO Auto-generated catch block
				System.out.println("\n** Info [" + service.getName() + "]: " + e.getMessage());
			}
		}
	}*/

	@Deprecated
	/*private void runBinds(){
		try{
			////doRegisterBind();
			////doFieldBind();
			//doActionBind();
			doServicesBinds();
			////doAddressBind();
		}catch (DeviceException e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		/*for (String bind : this.services.keySet()) {
			try{
				System.out.println("Serviços: " + this.services.get(bind).getName() + " - " + this.services.get(bind).getFormat().getPattern());
				System.out.println(" -- Parametros:  " + this.services.get(bind).getFormat().getParams().size());
				for (Parameter param : this.services.get(bind).getFormat().getParams()) {
					System.out.println(" -- Nome: " + param.getName() + ", Tamanho: " + param.getSize());
				}
			}catch (Exception e) {
				System.err.println("** [TDC] Warning: " + e.getMessage());
			}
		}
		for (String bind : this.registers.keySet()) {
			try{
				System.out.println("Registrador: " + this.registers.get(bind).getName() + " - " + this.registers.get(bind).getFormat().getPattern());
				System.out.println(" -- Field:  " + this.registers.get(bind).getFormat().getFields().size());
				for (String field : this.registers.get(bind).getFormat().getFields().keySet()) {
					Field fil = this.registers.get(bind).getFormat().getFields().get(field);
					System.out.println(" -- Nome: " + field + ", Tamanho: " + fil.getSize());
				}
			}catch (Exception e) {
				System.err.println("** [TDC] Warning: " + e.getMessage());
			}
		}* /
	}*/

	private void printHeader(){
		System.out.println();
		System.out.println();
		System.out.println("##################################");
		System.out.println("##    Temporal DevC Checker     ##");
		System.out.println("##################################");
		System.out.println("##      Greco/CIn - UFPE        ##");
		System.out.println("##  http://cin.ufpe.br/~greco/  ##");
		System.out.println("##   Copyright (c) 2011, 2012   ##");
		System.out.println("##################################");
		System.out.println("##    By Rafael Melo Macieira   ##");
		System.out.println("##        rmm2@cin.ufpe.br      ##");
		System.out.println("##################################");
		System.out.println();
		System.out.println("** Starting Code Generation... **");
		System.out.println();
		System.out.println("** Code Type: " + this.codelanguage.getCodeName());
		System.out.println("** Project: " + this.projectName);
		System.out.println("** Using OS Kernel Image: " + (this.getKernelImageDir().equals("")?"[No]":("[Yes] -> " + this.kernelImageDir)));
		System.out.println("** Output Dir: " + this.absoluteDir + "driver_debugger/" + this.projectName + "/");
		//System.out.println("** Device Base Address: [" + this.baseAddress + "]");
		System.out.println();
	}

	public void printBottom(){
		double execTime = (System.currentTimeMillis() - this.initialTime)/1000; 
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00.000");
		System.out.println("#################################################");
		System.out.println("## ** Generation Time: " + df.format(execTime) + "segs              ##");
		System.out.println("#################################################");
		System.out.println("##   Run graphgen to create the graphs' .pngs  ##");
		System.out.println("#################################################");
		System.out.println();
		System.out.println();

	}

	private void genFile(boolean header, String filename, String body) throws IOException{
		if (header){
			tmpFile = new File(this.absoluteDir + "driver_debugger/" + this.projectName + "/headers/" + filename);
			bw = new BufferedWriter(new FileWriter(tmpFile));
			bw.write(body);
			bw.flush();
			bw.close();
		}else{
			tmpFile = new File(this.absoluteDir + "driver_debugger/" + this.projectName + "/" + filename);
			bw = new BufferedWriter(new FileWriter(tmpFile));
			bw.write(body);
			bw.flush();
			bw.close();
		}
		System.out.print(".");
	}

	/*public void generateAutomatonsCode(CodeLanguage type) {
		switch (type) {
		case C:
			generateAutomatonsCCode();
			break;
		case CPP:
			generateAutomatonsCPPCode();
			break;
		case VERILOG:
			generateAutomatonsVerilogCode();
			break;
		case SYSTEMVERILOG:
			generateAutomatonsSystemVerilogCode();
			break;
		case VHDL:
			generateAutomatonsVHDLCode();
			break;
		default:
			generateAutomatonsCPPCode();
			break;
		}
	}*/

	/*public void generateAutomatonsVerilogCode() {
		System.err.println("Not Implemented Yet!");
	}
	public void generateAutomatonsSystemVerilogCode() {
		System.err.println("Not Implemented Yet!");
	}
	public void generateAutomatonsVHDLCode() {
		System.err.println("Not Implemented Yet!");
	}
	public void generateAutomatonsCCode() {
		String dir = this.absoluteDir + "driver_debugger/" + this.projectName + "/";
		try {
			System.out.print("\n** Generating C Files...");
			tmpFile = new File(dir);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
				tmpFile = new File(dir + "headers/");
				tmpFile.mkdir();
				tmpFile = new File(dir + "build/");
				tmpFile.mkdir();
			}
			Service service;
			String TDEVCHeaderAutomatasResult;
			String TDEVCAutomatasResult;
			String TDEVCHeaderAutomataEngineResult; //System.out.println(new TDEVCHeaderAutomataEngine().generate(this));
			String TDEVCAutomataEngineResult;
			String TDEVCHeaderAutomataSuperClassResult;
			String TDEVCAutomataSuperClassResult;
			String TDEVCMakefileResult;
			String TDEVCAddressResult;
			String TDEVCGraphGenResult;

			for (String serviceS : getServices().keySet()) {
				service = getServices().get(serviceS);
				this.currentService = service;
				TDEVCHeaderAutomatasResult = new TDEVCHeaderAutomatasC().generate(this);
				TDEVCAutomatasResult = new TDEVCAutomatasC().generate(this);
				genFile(false,"tdc_automata_" + service.getName() + ".c",TDEVCAutomatasResult);
				genFile(HEADERS,"tdc_automata_" + service.getName() + ".h",TDEVCHeaderAutomatasResult);
				//genFile(false,"tdc_automata_" + service.getName() + ".h",TDEVCHeaderAutomatasResult);
				genFile(false,"tdc_automata_" + service.getName() + ".dot",service.toString()); //Grafos
			}

			TDEVCAutomataSuperClassResult = new TDEVCAutomataSuperClassC().generate(this);
			TDEVCHeaderAutomataSuperClassResult = new TDEVCHeaderAutomataSuperClassC().generate(this);
			TDEVCHeaderAutomataEngineResult = new TDEVCHeaderAutomataEngineC().generate(this);
			TDEVCAutomataEngineResult = new TDEVCAutomataEngineC().generate(this);
			TDEVCGraphGenResult = new TDEVCGraphGen().generate(this);
			genFile(false,"tdc_automata.c",TDEVCAutomataSuperClassResult);
			genFile(false,"tdc_automata_engine.c",TDEVCAutomataEngineResult);
			genFile(HEADERS,"tdc_automata.h",TDEVCHeaderAutomataSuperClassResult);
			genFile(HEADERS,"tdc_automata_engine.h",TDEVCHeaderAutomataEngineResult);
			/*genFile(false,"tdc_automata.h",TDEVCHeaderAutomataSuperClassResult);
			genFile(false,"tdc_automata_engine.h",TDEVCHeaderAutomataEngineResult);* /

			genFile(false,"graphgen",TDEVCGraphGenResult);

			TDEVCMakefileResult = new TDEVCMakefile().generate(this);
			genFile(false,"Makefile",TDEVCMakefileResult);

			TDEVCAddressResult = new TDEVCAddress().generate(this);
			genFile(HEADERS,"address.sh",TDEVCAddressResult);
			//genFile(false,"address.sh",TDEVCAddressResult);
			/*addressResult = address.generate(this);
			makefileDepResult = makefileDep.generate(this);
			automataClassResult = automataClass.generate(this);
			automataHeaderClassResult = headerAutomataClass.generate(this);
			automataEngineResult = automataEngine.generate(this);
			automataHeaderEngineResult = headerAutomataEngine.generate(this);
			makefileResult = makefile.generate(this);
			mainResult = mainExample.generate(this);

			genFile(false,"tdc_automata.cpp",automataClassResult);
			genFile(true,"tdc_automata.h",automataHeaderClassResult);
			genFile(false,"tdc_automata_engine.cpp",automataEngineResult);
			genFile(true,"tdc_automata_engine.h",automataHeaderEngineResult);
			genFile(true,"address.sh",addressResult);
			genFile(true,"makefiledep",makefileDepResult);
			genFile(false,"Makefile",makefileResult);

			//MAIN TESTE//
			genFile(false,"main.cpp",mainResult);
			//genFile(false,"main.cpp","#include \"headers/tdc_automata.h\"\n#include \"headers/tdc_automata_engine.h\"\n#include <stdio.h>\n\nint main(){\nprintf(\"TESTE\\n\");\nreturn 0;\n}");
			//////////////* /

			System.out.println("Done.");
			System.out.println();
			System.out.println("** Success!");
			System.out.println();
			System.out.println();
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println("IO Error! - " + dir);
			System.out.println();
			System.out.println("ERROR: [" + ioe.getMessage() + "]");
			System.out.println();
			System.out.println("** Fail!");
			System.out.println();
			System.out.println();
		}
	}

	public void generateAutomatonsCPPCode() {
		String dir = this.absoluteDir + "driver_debugger/" + this.projectName + "/";
		try {
			System.out.print("\n** Generating CPP Files...");
			tmpFile = new File(dir);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
				tmpFile = new File(dir + "headers/");
				tmpFile.mkdir();
				tmpFile = new File(dir + "build/");
				tmpFile.mkdir();
			}
			Service service;
			String TDEVCHeaderAutomatasResult;
			String TDEVCAutomatasResult;
			String TDEVCHeaderAutomataEngineResult; //System.out.println(new TDEVCHeaderAutomataEngine().generate(this));
			String TDEVCAutomataEngineResult;
			String TDEVCHeaderAutomataSuperClassResult;
			String TDEVCAutomataSuperClassResult;
			String TDEVCMakefileResult;
			String TDEVCAddressResult;
			String TDEVCGraphGenResult;

			for (String serviceS : getServices().keySet()) {
				service = getServices().get(serviceS);
				this.currentService = service;
				TDEVCHeaderAutomatasResult = new TDEVCHeaderAutomatas().generate(this);
				TDEVCAutomatasResult = new TDEVCAutomatas().generate(this);
				genFile(false,"tdc_automata_" + service.getName() + ".cpp",TDEVCAutomatasResult);
				genFile(HEADERS,"tdc_automata_" + service.getName() + ".h",TDEVCHeaderAutomatasResult);
				//genFile(false,"tdc_automata_" + service.getName() + ".h",TDEVCHeaderAutomatasResult);
				genFile(false,"tdc_automata_" + service.getName() + ".dot",service.toString()); //Grafos
			}

			TDEVCAutomataSuperClassResult = new TDEVCAutomataSuperClass().generate(this);
			TDEVCHeaderAutomataSuperClassResult = new TDEVCHeaderAutomataSuperClass().generate(this);
			TDEVCHeaderAutomataEngineResult = new TDEVCHeaderAutomataEngine().generate(this);
			TDEVCAutomataEngineResult = new TDEVCAutomataEngine().generate(this);
			TDEVCGraphGenResult = new TDEVCGraphGen().generate(this);
			genFile(false,"tdc_automata.cpp",TDEVCAutomataSuperClassResult);
			genFile(false,"tdc_automata_engine.cpp",TDEVCAutomataEngineResult);
			genFile(HEADERS,"tdc_automata.h",TDEVCHeaderAutomataSuperClassResult);
			genFile(HEADERS,"tdc_automata_engine.h",TDEVCHeaderAutomataEngineResult);
			/*genFile(false,"tdc_automata.h",TDEVCHeaderAutomataSuperClassResult);
			genFile(false,"tdc_automata_engine.h",TDEVCHeaderAutomataEngineResult);* /

			genFile(false,"graphgen",TDEVCGraphGenResult);

			TDEVCMakefileResult = new TDEVCMakefile().generate(this);
			genFile(false,"Makefile",TDEVCMakefileResult);

			TDEVCAddressResult = new TDEVCAddress().generate(this);
			genFile(HEADERS,"address.sh",TDEVCAddressResult);
			//genFile(false,"address.sh",TDEVCAddressResult);
			/*addressResult = address.generate(this);
			makefileDepResult = makefileDep.generate(this);
			automataClassResult = automataClass.generate(this);
			automataHeaderClassResult = headerAutomataClass.generate(this);
			automataEngineResult = automataEngine.generate(this);
			automataHeaderEngineResult = headerAutomataEngine.generate(this);
			makefileResult = makefile.generate(this);
			mainResult = mainExample.generate(this);

			genFile(false,"tdc_automata.cpp",automataClassResult);
			genFile(true,"tdc_automata.h",automataHeaderClassResult);
			genFile(false,"tdc_automata_engine.cpp",automataEngineResult);
			genFile(true,"tdc_automata_engine.h",automataHeaderEngineResult);
			genFile(true,"address.sh",addressResult);
			genFile(true,"makefiledep",makefileDepResult);
			genFile(false,"Makefile",makefileResult);

			//MAIN TESTE//
			genFile(false,"main.cpp",mainResult);
			//genFile(false,"main.cpp","#include \"headers/tdc_automata.h\"\n#include \"headers/tdc_automata_engine.h\"\n#include <stdio.h>\n\nint main(){\nprintf(\"TESTE\\n\");\nreturn 0;\n}");
			//////////////* /

			System.out.println("Done.");
			System.out.println();
			System.out.println("** Success!");
			System.out.println();
			System.out.println();
		}catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println("IO Error! - " + dir);
			System.out.println();
			System.out.println("ERROR: [" + ioe.getMessage() + "]");
			System.out.println();
			System.out.println("** Fail!");
			System.out.println();
			System.out.println();
		}

	}*/
	
	


	
}
//ctrl_send_data.set_actions(pre){wait_state();}
//ctrl_send_data.bindTo(lcd_ctrl_reg);
//dc_service <ctrl_type, WRITE> ctrl_send_data;