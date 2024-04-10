package br.ufpe.cin.greco.devc.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;

import br.ufpe.cin.greco.devc.languageStructure.TDCCheckerGenerator;
import br.ufpe.cin.greco.devc.languageStructure.devc.Lex;
import br.ufpe.cin.greco.devc.languageStructure.devc.parser;
import br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice.Field;
import br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice.Register;
import br.ufpe.cin.greco.devc.languageStructure.filetype.tdevice.RegisterFormat;
import br.ufpe.cin.greco.devc.languageStructure.ltl.IDevCState;
import java_cup.runtime.DefaultSymbolFactory;

public class TDevCFile {
	private String name;
	private File file;
	private StringBuffer source;
	private boolean compiled;
	private boolean saved;
	private boolean wrongSyntax;
	private DefaultMutableTreeNode rootElem;
	private TDCCheckerGenerator tdevcGen;
	private parser tdevCParser;
	private DefaultMutableTreeNode registerTree, statesTree, orthoRegTree,
	formatsTree, ltlTree, varTree,
	patternTree, behaviorTree, transitionTree, ltlPropTree, statePropTree;
	
	public TDevCFile(File file) {
		this.name = file.getName().split("[.]")[0];
		this.file = file;
		this.compiled = false;
		this.saved = true;
		this.wrongSyntax = false;
		this.source = new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new FileReader(this.getFile()));
			while (br.ready()){
				this.source.append(br.readLine()+"\n");
			}
		}catch (IOException ioe){
			Editor.terminalMsgln("Error during reading file " + this.getName() + ": " + ioe.getMessage());
		}
	}

	public String getSource() {
		return source.toString();
	}

	public void setSource(String source) {
		this.source = new StringBuffer(source);
	}

	public boolean isCompiled() {
		return compiled;
	}

	private void setCompiled(boolean compiled) {
		this.compiled = compiled;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}
	
	public boolean isWrongSyntax() {
		return wrongSyntax;
	}

	private void setWrongSyntax(boolean wrongSyntax) {
		this.wrongSyntax = wrongSyntax;
	}

	public DefaultMutableTreeNode generateTDevCTree() {
		String label = this.getName() + (isSaved()?"":" [*]") + (isCompiled()?"":" [nc]") + (isWrongSyntax()?" [Syntax Error!]":"");
		this.rootElem = new DefaultMutableTreeNode(label,true);
		this.rootElem.add(new DefaultMutableTreeNode(this,true));
		if (isCompiled()){
			generateTreeNodes();
		}
		return rootElem; 
	}
	
	public void RefresTDevCTree() {
		String label = this.getName() + (isSaved()?"":" [*]") + (isCompiled()?"":" [nc]") + (isWrongSyntax()?" [Syntax Error!]":"");
		this.rootElem.setUserObject(label);
		Editor.refreshProjectTree(this.rootElem);
	}
	
	public void genRegisters() throws Exception {
		this.tdevcGen.generateRegisters();
	}
	
	/*public DDFA generateDDFA(){
		this.currentDDFA = tdevcGen.generateDDFA(); 
		return this.currentDDFA;
	}*/
	
	public void genViolations() throws Exception {
		this.tdevcGen.checkSemantics();
	}
	
	public void generateTreeNodes() {
		//rootElem = new DefaultMutableTreeNode(this.tdevcGen.getProjectName(),true);
		registerTree = new DefaultMutableTreeNode("Registers(" + this.tdevcGen.getRegisters().size() + ")", true);
		mountRegistersTree(registerTree);
		this.rootElem.add(registerTree);
		formatsTree = new DefaultMutableTreeNode("Formats(" + this.tdevcGen.getFormats().size() + ")", true);
		mountFormatsTree(formatsTree);
		this.rootElem.add(formatsTree);
		varTree = new DefaultMutableTreeNode("Variables(" + this.tdevcGen.getVariables().size() + ")", true);
		for (String varName : this.tdevcGen.getVariables().keySet()) {
			varTree.add(new DefaultMutableTreeNode(this.tdevcGen.getVariables().get(varName)));
		}
		patternTree = new DefaultMutableTreeNode("Patterns(" + this.tdevcGen.getPatterns().size() + ")", true);
		for (String pattName : this.tdevcGen.getPatterns().keySet()) {
			patternTree.add(new DefaultMutableTreeNode(this.tdevcGen.getPatterns().get(pattName)));
		}
		this.rootElem.add(patternTree);
		this.rootElem.add(varTree);
		this.rootElem.add(new DefaultMutableTreeNode("Protocols", true));
		statesTree = new DefaultMutableTreeNode("States", true);
		ltlTree = new DefaultMutableTreeNode("LTL Properties");
		behaviorTree = new DefaultMutableTreeNode("Behaviors");
		statesTree.add(mountStateTree(this.tdevcGen.getGlobalState()));
		this.rootElem.add(statesTree);
		mountLTLTree(ltlTree,this.tdevcGen.getGlobalState());
		this.rootElem.add(ltlTree);
		mountBehaviorTree(behaviorTree,this.tdevcGen.getGlobalState());
		this.rootElem.add(behaviorTree);
		orthoRegTree = new DefaultMutableTreeNode("Orthogonal Regions", true);
		mountOrthoTree(orthoRegTree, this.tdevcGen.getGlobalState());
		this.rootElem.add(orthoRegTree);
		//rootElem.add(new DefaultMutableTreeNode("Sub-HFSMD", true));
		statePropTree = new DefaultMutableTreeNode("HFSM Propositions (" + this.tdevcGen.getPropositionsLib().size() + ")", true);
		for (String prop : this.tdevcGen.getPropositionsLib().keySet()) {
			statePropTree.add(new DefaultMutableTreeNode(prop + ": " + this.tdevcGen.getPropositionsLib().get(prop)));
		}
		this.rootElem.add(statePropTree);
		ltlPropTree = new DefaultMutableTreeNode("LTL Propositions (" + this.tdevcGen.getLTLPropositionsLib().size() + ")", true);
		for (String prop : this.tdevcGen.getLTLPropositionsLib().keySet()) {
			ltlPropTree.add(new DefaultMutableTreeNode(prop + ": " + this.tdevcGen.getLTLPropositionsLib().get(prop)));
		}
		this.rootElem.add(ltlPropTree);
		/*model.setRoot(rootElem);
		System.err.println(rootElem.getChildCount());
		model.nodeChanged(rootElem);*/
	}

	public void doParse() throws Exception {
		try {
			this.tdevcGen = new TDCCheckerGenerator();
			long startTime = System.currentTimeMillis();
			Editor.terminalMsg("*** " + this.getName() + "...");
			this.tdevCParser = new parser(new Lex(new java.io.FileInputStream(this.getFile()),new DefaultSymbolFactory()));
			this.tdevCParser.setTDCCheckerGenerator(tdevcGen);
			this.tdevCParser.parse();
			genRegisters();
			genViolations();
			this.setCompiled(true);
			Editor.terminalMsgln("[  OK  ] " + String.format("%.2f", (double)(System.currentTimeMillis() - startTime) / 1000) + "s");
		}catch (Exception e){
			setWrongSyntax(true);
			Editor.terminalMsgln("[ FAIL ] ***");
			e.printStackTrace();
			throw e;
		}
	}
	
	private void mountFormatsTree(DefaultMutableTreeNode internal){
		DefaultMutableTreeNode regTree, fieldTree;
		RegisterFormat regFormat;
		for (String regName : this.tdevcGen.getFormats().keySet()) {
			regFormat = this.tdevcGen.getFormats().get(regName);
			regTree = new DefaultMutableTreeNode(regFormat);
			for (String fieldName : regFormat.getFields().keySet()) {
				fieldTree = new DefaultMutableTreeNode(regFormat.getFields().get(fieldName));
				mountRegistersFieldsTree(fieldTree);
				regTree.add(fieldTree);
			}
			internal.add(regTree);
		}
	}
	
	private void mountRegistersTree(DefaultMutableTreeNode internal){
		DefaultMutableTreeNode regTree, fieldTree;
		Register register;
		for (String regName : this.tdevcGen.getRegisters().keySet()) {
			register = this.tdevcGen.getRegisters().get(regName);
			regTree = new DefaultMutableTreeNode(register);
			for (String fieldName : register.getFields().keySet()) {
				fieldTree = new DefaultMutableTreeNode(register.getFields().get(fieldName));
				mountRegistersFieldsTree(fieldTree);
				regTree.add(fieldTree);
			}
			internal.add(regTree);
		}
	}
	
	private void mountRegistersFieldsTree(DefaultMutableTreeNode internal){
		Field field = (Field) internal.getUserObject();
		Field tmpField;
		DefaultMutableTreeNode newField;
		for (String fieldName : field.getFields().keySet()) {
			tmpField = field.getFields().get(fieldName);
			newField = new DefaultMutableTreeNode(tmpField);
			mountRegistersFieldsTree(newField);
			internal.add(newField);
		}
	}
	
	private DefaultMutableTreeNode mountStateTree(IDevCState state){
		DefaultMutableTreeNode internal = new DefaultMutableTreeNode((state.isInitial()?">":"") + state.getName() + (state.getHomeLand()==null?"":" [" + state.getHomeLand() + "]"));
		IDevCState son;
		for (String  sonName : state.getSons().keySet()) {
			son = state.getSons().get(sonName);
			internal.add(mountStateTree(son));
		}
		return internal;
	}
	
	private void mountLTLTree(DefaultMutableTreeNode internal, IDevCState state){
		DefaultMutableTreeNode stateTree;
		if (!state.getViolations().isEmpty()){
			 stateTree = new DefaultMutableTreeNode(state);
			 for (String violationName : state.getViolations().keySet()) {
				 stateTree.add(new DefaultMutableTreeNode(state.getViolations().get(violationName)));
			 }
			 internal.add(stateTree);
		}
		IDevCState son;
		for (String  sonName : state.getSons().keySet()) {
			son = state.getSons().get(sonName);
			mountLTLTree(internal,son);
		}
	}
	
	private void mountBehaviorTree(DefaultMutableTreeNode internal, IDevCState state){
		DefaultMutableTreeNode stateTree;
		if (!state.getBehaviors().isEmpty()){
			 stateTree = new DefaultMutableTreeNode(state);
			 for (String behavName : state.getBehaviors().keySet()) {
				 stateTree.add(new DefaultMutableTreeNode(state.getBehaviors().get(behavName)));
			 }
			 internal.add(stateTree);
		}
		IDevCState son;
		for (String  sonName : state.getSons().keySet()) {
			son = state.getSons().get(sonName);
			mountBehaviorTree(internal,son);
		}
	}
	
	private void mountOrthoTree(DefaultMutableTreeNode internal, IDevCState state){
		DefaultMutableTreeNode orthReg;
		IDevCState regState;
		if(!state.getMyOrthoRegions().isEmpty()){
			DefaultMutableTreeNode stateTree = new DefaultMutableTreeNode(state.getName(),true);
			for (String orthoName : state.getMyOrthoRegions().keySet()) {
				orthReg = new DefaultMutableTreeNode(state.getMyOrthoRegions().get(orthoName));
				/*for (String regStateName : state.getMyOrthoRegions().get(orthoName).getRegionStates().keySet()) {
					regState = state.getMyOrthoRegions().get(orthoName).getRegionStates().get(regStateName);
					orthReg.add(new DefaultMutableTreeNode((regState.isInitial()?">":"") + regStateName));
				}*/
				stateTree.add(orthReg);	
			}
			internal.add(stateTree);
		}
		for (String  sonName : state.getSons().keySet()) {
			mountOrthoTree(internal, state.getSons().get(sonName));
		}
		//}
	}
	
	public void saveFile(String content) throws IOException {
		BufferedWriter bw;
			if (this.getFile() != null){
				bw = new BufferedWriter(new FileWriter(this.getFile()));
				bw.write(content);
				bw.flush();
			}
			/*} else {
				File tempFile;
				JFileChooser chooser = new JFileChooser();
				chooser.showSaveDialog(parent);
				tempFile = chooser.getSelectedFile();
				if (tempFile != null){
					if (tempFile.exists()){
						System.out.println("Ja existe!");
					}else{
						tempFile.createNewFile();
					}
					//((Editor) this.parent).setDevCFile(tempFile);
					bw = new BufferedWriter(new FileWriter(tempFile));
					bw.write(((Editor) this.parent).getcodeEditorContent());
					bw.flush();
				}
			}
			((Editor) this.parent).terminalMsg("File Saved!");*/
			this.saved = true;
			RefresTDevCTree();
			Editor.terminalMsgln(this.getName() + " Saved!");
	}
	
	public void setModified(){
		this.saved = false;
		RefresTDevCTree();
	}
	
	@Override
	public String toString() {
		return "Code: " + this.getName();
	}
	
	public DefaultMutableTreeNode getTreeNode(){
		return this.rootElem;
	}
	
}
