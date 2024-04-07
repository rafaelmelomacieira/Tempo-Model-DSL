import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java_cup.runtime.*;
import br.ufpe.cin.greco.devc.languageStructure.*;
import br.ufpe.cin.greco.devc.languageStructure.type.*;
import br.ufpe.cin.greco.devc.languageStructure.statement.ServiceCall;
import br.ufpe.cin.greco.devc.languageStructure.ltl.*;
import br.ufpe.cin.greco.devc.languageStructure.hfsmd.*;
import br.ufpe.cin.greco.devc.languageStructure.ltl.Behavior;
import com.martiansoftware.jsap.*;
import java.util.logging.Level;
import java.util.logging.Logger;

parser code {:
	
	public static Lex myLex;
	
	private TDCCheckerGenerator tdevcGen;
	
	static Integer baseAddress;
	static String  absoluteDir;
	static String  kernelImageDir;
	static String  appName;
	static String  codeType;
	static double  initialTime;
	//static String  projName;
	
	//1: DevC File Path 
	//2: Base Address
	//3: appname
	//4: Platform Path
	//5: Kernel Image Path
	
	public static JSAP getJSAP(){
		JSAP jsap = new JSAP();
		Switch helpSAP = new Switch("help")
										.setShortFlag('h')
										.setLongFlag("help");
		Switch snifferSAP = new Switch("sniffer")
										.setShortFlag('s')
										.setLongFlag("sniffer");
		FlaggedOption languageSAP = new FlaggedOption("language")
        								.setShortFlag('l')
        								.setDefault("c")
        								.setLongFlag("codelanguage");
		FlaggedOption baseaddressSAP = new FlaggedOption("baseaddress")
										.setRequired(false)
										.setShortFlag('a')
										.setLongFlag("baseaddress");
		FlaggedOption kernelSAP = new FlaggedOption("kernel")
										.setRequired(false)
										.setShortFlag('k')
										.setLongFlag("kernel");
		FlaggedOption projectSAP = new FlaggedOption("project")
										.setRequired(false)
										.setShortFlag('p')
										.setLongFlag("project");
		FlaggedOption platformSAP = new FlaggedOption("platform")
										.setRequired(false)
										.setShortFlag('d')
										.setLongFlag("platformpath");
		UnflaggedOption descSAP = new UnflaggedOption("desc")
										.setRequired(false)
										.setStringParser(JSAP.STRING_PARSER);

		

		helpSAP.setHelp("Shows the list of arguments");
		snifferSAP.setHelp("Generates the Sniffer's Device Driver code");
		languageSAP.setHelp("Defines the generated code language [c; cpp; verilog; systemverilog; vhdl]");
		platformSAP.setHelp("Specifies the Virtual Platform absolute path");
		baseaddressSAP.setHelp("Defines the device base address");
		kernelSAP.setHelp("Defines if this project is using a linux kernel and specifies the kernel image path");
		projectSAP.setHelp("Defines the name of the project/driver under validation");
		descSAP.setHelp("Specifies the DevC file absolte path");
		try {
			jsap.registerParameter(helpSAP);
			jsap.registerParameter(snifferSAP);
			jsap.registerParameter(languageSAP);
			jsap.registerParameter(baseaddressSAP);
			jsap.registerParameter(kernelSAP);
			jsap.registerParameter(platformSAP);
			jsap.registerParameter(projectSAP);
			jsap.registerParameter(descSAP);
		} catch (JSAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsap;
	}
	
	public void setTDCCheckerGenerator(TDCCheckerGenerator tdevcGen){
		this.tdevcGen = tdevcGen;
	}
	
	public TDCCheckerGenerator getTDCCheckerGenerator(){
		return this.tdevcGen;
	}
	
	/*public static void main(String args[]) throws Exception {
		initialTime = System.currentTimeMillis();
		SymbolFactory sf = new DefaultSymbolFactory();
		
		String desc = "";
		JSAP jsap = getJSAP();
		JSAPResult jsapResut = jsap.parse(args);
		
		if (jsapResut.success()){
			if (jsapResut.getBoolean("help")){
				System.out.println(jsap.getUsage());
				System.out.println();
				System.out.println(jsap.getHelp());
			}else{
				baseAddress = Integer.parseInt(jsapResut.getString("baseaddress"),16);
				appName = jsapResut.getString("project");
				absoluteDir = jsapResut.getString("platform");
				if (jsapResut.contains("kernel")){
					kernelImageDir = jsapResut.getString("kernel");
				}else{
					kernelImageDir = "";
				}
				codeType = jsapResut.getString("language");
				desc = jsapResut.getStringArray("desc")[0];
				
				myLex = new Lex(new java.io.FileInputStream(desc),sf);
				//new parser(myLex,sf).parse(); //old JFlex
				new parser(myLex).parse();
			}
		}else{
			for (java.util.Iterator errs = jsapResut.getErrorMessageIterator();errs.hasNext();) {
				System.err.println("Error: " + errs.next());
			}
		}
		/*codeType = args[0];
		baseAddress = Integer.parseInt(args[2],16); 
		appName = args[3];
		absoluteDir = args[4];
		if (args.length > 4){
			kernelImageDir = args[5];
		}else{
			kernelImageDir = "";
		}*/
		/*if (args.length==0){
			myLex = new Lex(System.in,sf);
			new parser(myLex,sf).parse();
		}else{
			myLex = new Lex(new java.io.FileInputStream(args[1]),sf);
			new parser(myLex,sf).parse();
		}* /
	}*/
	//public static void addParam
:}

terminal T_OPEN_PAR, T_CLOSE_PAR, T_PONTVIRG, T_DOISPONTOS, T_OPEN_BRACE, T_CLOSE_BRACE, T_IGUAL,
T_DOT, T_OPEN_BRACKET, T_CLOSE_BRACKET, T_VIRG;
terminal String T_MASKREP;
terminal T_READ, T_WRITE, T_READWRITE, T_DC_REG, T_RESERVED, T_MAX_DELAY, T_MIN_DELAY, T_IMPORT, T_DC_FORMAT,
T_ALIAS, T_DEVICE, T_PATTERN, T_MASK;

terminal DelayType T_SEC, T_MS, T_US, T_NS, T_PS; 

terminal String T_IDENT, T_OPER;
terminal String T_INT;

terminal T_STATE;
terminal T_INITIAL_STATE;
terminal T_GLOBAL_STATE;
terminal T_ORTHO_STATE;
terminal T_ORTHO_REGION;
terminal T_ADD_PROPERTY;
terminal T_ADD_EXIT_POINT;
terminal T_ADD_ENTRY_POINT;
terminal T_LTLF;
terminal T_LLTLF;
terminal T_TEXTSTR;
terminal T_BOOL_IMPL;
terminal T_BOOL_EQUI;
terminal T_BOOL_CONJ;
terminal T_BOOL_DISJ;
terminal T_ALWAYS_OPER;
terminal T_NEXT_OPER;
terminal T_EVENT_OPER;
terminal T_UNTIL_OPER;
terminal T_WEAK_UNTIL_OPER;
terminal T_DUAL_UNTIL_OPER;
terminal T_ASSIGNS;

terminal T_PROP_NEG_OPER;
terminal T_TRUE;
terminal T_FALSE;
terminal T_PLUS;
terminal T_MINUS;
terminal T_MULT;
terminal T_DIV;
terminal T_INTERNAL;
terminal T_EXTERNAL;
terminal T_REGWRITE;
terminal T_REGREAD;
terminal T_COMP_OPER;
terminal T_COMP;
terminal T_LSHIFT;
terminal T_RSHIFT;
terminal T_CRITICAL;
terminal T_WARNING;
terminal T_INFO;
terminal T_VAR;
terminal T_ADD_BEHAVIOR;
terminal T_MEMORY;


non terminal input;
non terminal TDCCheckerGenerator devc_obj_def;
non terminal RegisterFormat format_definition; 
non terminal String bits;
non terminal Register sto_definition, reg_decl;
non terminal AccessType rw;
non terminal Statement expr;
non terminal HashMap<String, Field> fields_decl;
non terminal Field field_decl;

non terminal Delay delay_definition;
non terminal DelayType delay_type;

non terminal String tdevtimport;
non terminal TDCCheckerGenerator tdevcspec;
non terminal Pattern pattern_definition;
non terminal OrthoRegion ortho_region_decl;
non terminal HashMap<String, IDevCState> states_decl;
non terminal DevCState global_state_definition;
non terminal StateType state_type;
non terminal DevCState global_state_body;
non terminal DevCState state_declaration;
non terminal DevCState state_body;
non terminal Violation violation_decl;
non terminal Behavior behavior_decl;
non terminal ExitPoint exitpoint_decl;
non terminal EntryPoint entrypoint_decl;
non terminal String temporal_behavior_decl;
non terminal boolean_seq;
non terminal logic_expr;
non terminal boolean_conj_dis;
non terminal PropositionExpression proposition_expr;
non terminal String reg_comp;
non terminal AccessPropositionExpression reg_access;
non terminal String reg_ref_decl;
non terminal String reg_expr;
non terminal String reg_comp_expr;
non terminal String reg_val;
non terminal unary_oprs;
non terminal binary_oprs;
non terminal LTLRuleTerm ltl_logic_expr;
non terminal LTLRuleTerm propositional_logic;
non terminal ltl_unary_oprs;
non terminal ltl_binary_oprs;
non terminal ltl_logic_until_expr;
non terminal ltl_logic_unary;
non terminal ltl_binary_until_oprs;
non terminal assign_block_decl;
non terminal assigns_decl;
non terminal assign_decl;
non terminal expr_decl;
non terminal String violation_type_decl;
non terminal Variable context_definition;
non terminal mem_definition;
//precedence nonassoc terminal[, terminal...];

precedence left T_BOOL_CONJ, T_BOOL_DISJ, T_BOOL_EQUI, T_BOOL_IMPL;
precedence right T_UNTIL_OPER,T_WEAK_UNTIL_OPER,T_DUAL_UNTIL_OPER;
precedence right T_PROP_NEG_OPER, T_NEXT_OPER, T_ALWAYS_OPER, T_EVENT_OPER;

precedence left T_OPER;
precedence left T_PLUS, T_MINUS;
precedence left T_MULT, T_DIV;
precedence left T_LSHIFT, T_RSHIFT;

/*%union {
     int       t_int;  /* For the lexical analyser. NUMBER tokens 
     char      ident;  /* For the lexical analyser. IDENT tokens 
   }*/
/*%type <t_int>   T_INT
%type <ident> T_IDENT
%type <t_iservicent> address_def*/

/*%start nt_devc_spec*/

input ::= tdevtimport:imp tdevcspec:def1
 {:
 	this.parser.setTDCCheckerGenerator(def1);
 	/*System.out.println("aaaaaaaaaa");
	def1.checkSemantics();*/ //AQUI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 :}
|tdevcspec:def1
 {:
 	this.parser.setTDCCheckerGenerator(def1);
 :};

tdevtimport ::= tdevtimport:imp2 T_IMPORT T_IDENT:imp T_PONTVIRG {: RESULT = imp2 + "\n" + imp; :}
| T_IMPORT T_IDENT:imp T_PONTVIRG {: RESULT = imp; :};

tdevcspec ::= T_DEVICE T_OPEN_PAR T_IDENT:pjName T_CLOSE_PAR T_OPEN_BRACE devc_obj_def:def1 T_CLOSE_BRACE
{:
	def1.setProjectName(pjName);
	RESULT = def1;
	////this.parser.setTDCCheckerGenerator(def1);
	//def1.runCheckerGeneration(parser.initialTime, pjName, parser.baseAddress, parser.appName, parser.absoluteDir, parser.kernelImageDir, parser.codeType);
:}
| error {:System.err.println("Error on Start Declaration! (Line: " + Lex.currentLine + ")");:};

devc_obj_def ::= format_definition:fmt devc_obj_def:def_aut {:
	def_aut.addFormat(fmt);
	RESULT = def_aut;
:}

| pattern_definition:pattern T_PONTVIRG devc_obj_def:def_aut {:
	def_aut.addPattern(pattern);
	RESULT = def_aut;
:}
| sto_definition:sto devc_obj_def:def_aut {:
	def_aut.addRegister(sto);
	RESULT = def_aut;
:}
| mem_definition T_PONTVIRG devc_obj_def:def_aut {:
	//def_aut.addRegister(sto);
	RESULT = def_aut;
:}
| context_definition:ctx devc_obj_def:def_aut {:
	def_aut.addVariable(ctx);
	RESULT = def_aut;
:}
| global_state_definition:state_def {:
	TDCCheckerGenerator def_aut = this.parser.getTDCCheckerGenerator();//TDCCheckerGenerator.getInstance();
	def_aut.setGlobalState(state_def);
	RESULT = def_aut;
:}
| error {:System.err.println("Error on Object Definitions! (Line: " + Lex.currentLine + ")");:};

pattern_definition ::= T_PATTERN T_IDENT:name T_IGUAL T_INT:val {:
	RESULT = new Pattern(name,val,false);
:}
| T_PATTERN T_IDENT:name T_IGUAL T_MASK T_OPEN_PAR bits:bit T_CLOSE_PAR {:
	RESULT = new Pattern(name,bit,true);
:};


format_definition ::= T_DC_FORMAT T_IDENT:namei T_OPEN_BRACE fields_decl:fields T_CLOSE_BRACE
{:
	RESULT = new RegisterFormat(namei, fields);
:};

bits ::= T_MASKREP:masc {: RESULT = masc; :} 
//| T_DOT bits:masc_c {: RESULT = "." + masc_c; :}
//|T_MASKREP:masc bits:masc_c {: RESULT = masc + masc_c; :}
//| T_DOT {: RESULT = "."; :}
| error {: System.err.println("Mask Format Syntax Error: (Line: " + Lex.currentLine + ")"); :};

//**********************************************************
//******************* delay_definition *********************
//**********************************************************

delay_definition ::= T_MIN_DELAY T_OPEN_PAR T_INT:val T_VIRG delay_type:dtype T_CLOSE_PAR  {:RESULT = new Delay(val,dtype,DelayLimitType.MIN);:}
| T_MAX_DELAY T_OPEN_PAR T_INT:val T_VIRG delay_type:dtype T_CLOSE_PAR  {:RESULT = new Delay(val,dtype,DelayLimitType.MAX);:};

delay_type ::= T_SEC {: RESULT = DelayType.S;:}
| T_MS {: RESULT = DelayType.MS;:}
| T_US {: RESULT = DelayType.US;:}
| T_NS {: RESULT = DelayType.NS;:}
| T_PS {: RESULT = DelayType.PS;:};

context_definition ::= T_VAR T_IDENT:id T_PONTVIRG {: RESULT = new Variable(id); :}
| T_VAR T_IDENT:id T_IGUAL T_INT:val T_PONTVIRG {: RESULT = new Variable(id,val); :};

sto_definition ::= T_INTERNAL T_IDENT:id reg_decl:reg {: RESULT = reg; :}
| T_EXTERNAL reg_decl:reg {: RESULT = reg;:}
| error {:System.err.println("Error @ Storage Definition! (Line: " + Lex.currentLine + ")");:};
//| buffer_decl {: RESULT = null; :}; 

mem_definition ::= T_INTERNAL T_MEMORY T_IDENT:id T_OPEN_PAR T_INT:start T_VIRG T_INT:size T_CLOSE_PAR {: System.out.println("Internal Memory: " + id + ", Starting @ " + start + " with size: " + size); :}
| T_INTERNAL T_MEMORY T_IDENT:id T_OPEN_PAR T_INT:start T_VIRG T_INT:size T_CLOSE_PAR T_ALIAS T_IDENT:alias {: System.out.println("Internal Memory[" + alias + "]: " + id + ", Starting @ " + start + " with size: " + size); :}
| T_EXTERNAL T_MEMORY T_IDENT:id T_OPEN_PAR T_INT:start T_VIRG T_INT:size T_CLOSE_PAR {: System.out.println("External Memory: " + id + ", Starting @ " + start + " with size: " + size); :}
| T_EXTERNAL T_MEMORY T_IDENT:id T_OPEN_PAR T_INT:start T_VIRG T_INT:size T_CLOSE_PAR T_ALIAS T_IDENT:alias {: System.out.println("External Memory[" + alias + "]: " + id + ", Starting @ " + start + " with size: " + size); :}
| error {:System.err.println("Error @ Memory Definition! (Line: " + Lex.currentLine + ")");:};
//| buffer_decl {: RESULT = null; :};

reg_decl ::= T_DC_REG T_IDENT:name T_OPEN_PAR T_INT:addr T_CLOSE_PAR T_OPEN_BRACE fields_decl:fields T_CLOSE_BRACE {:
	RESULT = new Register(name, name, addr, AccessType.VOID, fields);
	//RESULT = new Register(ident,new RegisterFormat(fmt),AccessType.READ_WRITE); 
:}
| T_DC_REG T_IDENT:name T_OPEN_PAR T_INT:addr T_CLOSE_PAR T_ALIAS T_IDENT:alias T_OPEN_BRACE fields_decl:fields T_CLOSE_BRACE {:

	RESULT = new Register(name, alias, addr, AccessType.VOID, fields);
	//RESULT = new Register(ident,new RegisterFormat(fmt),AccessType.READ_WRITE); 
:}
| T_DC_REG T_IDENT:name T_OPEN_PAR T_INT:addr T_CLOSE_PAR T_IGUAL T_IDENT:format T_PONTVIRG {:
		RESULT = new Register(name, name, addr, AccessType.VOID, format);
	//RESULT = new Register(ident,new RegisterFormat(fmt),AccessType.READ_WRITE); 
:}
| T_DC_REG T_IDENT:name T_OPEN_PAR T_INT:addr T_CLOSE_PAR T_ALIAS T_IDENT:alias T_IGUAL T_IDENT:format T_PONTVIRG {:
	RESULT = new Register(name, alias, addr, AccessType.VOID, format);
	//RESULT = new Register(ident,new RegisterFormat(fmt),AccessType.READ_WRITE); 
:}
| error {:System.err.println("Error @ Register Definition! (Line: " + Lex.currentLine + ")");:};

fields_decl ::= field_decl:field T_PONTVIRG {:

	HashMap<String, Field> fields = new HashMap<String, Field>(); 
	fields.put(field.getName(),field);
	RESULT = fields;
:}
| field_decl:field T_PONTVIRG fields_decl:fields {:
	fields.put(field.getName(),field);
	RESULT = fields;
:}; 

field_decl ::= rw:accesstype T_IDENT:name T_OPEN_BRACKET T_INT:se T_CLOSE_BRACKET {:
	RESULT = new Field(name, "0", accesstype, FieldType.REGULAR, se, se);
:}
| rw:accesstype T_IDENT:name T_OPEN_BRACKET T_INT:start T_DOISPONTOS T_INT:end T_CLOSE_BRACKET {:
	RESULT = new Field(name, "0", accesstype, FieldType.REGULAR, start, end);
:}
/*| T_RESERVED T_OPEN_BRACE fields_decl:fields T_CLOSE_BRACE {:
	Field field = new Field("reserved_", "0", AccessType.VOID, FieldType.RESERVED);
	field.setFields(fields);
	RESULT = field;
	:}*/ //SubField Decl.
| T_RESERVED T_OPEN_BRACKET T_INT:start T_DOISPONTOS T_INT:end T_CLOSE_BRACKET {:
	RESULT = new Field("reserved_", "0", AccessType.VOID, FieldType.RESERVED, start, end);
:} 
| T_RESERVED T_OPEN_BRACKET T_INT:se T_CLOSE_BRACKET {:
	RESULT = new Field("reserved_", "0", AccessType.VOID, FieldType.RESERVED, se, se);
:}
| T_IDENT:name T_OPEN_BRACE fields_decl:fields T_CLOSE_BRACE {:
	Field field = new Field(name, "0", AccessType.VOID, FieldType.REGULAR);
	field.setFields(fields);
	RESULT = field;
:}
| error {:System.err.println("Error @ field Declr (Line: " + Lex.currentLine + ")");:}; //SubField Decl.

rw ::= T_READ {: RESULT = AccessType.READ; :}
| T_WRITE {:RESULT = AccessType.WRITE; :}
| T_READWRITE {:RESULT = AccessType.READ_WRITE; :};

global_state_definition ::= T_GLOBAL_STATE T_OPEN_BRACE global_state_body:ds T_CLOSE_BRACE {:
	ds.setName("Global_State");
	ds.setType(StateType.GLOBALSTATE);
	RESULT = ds;
:};

global_state_body ::= violation_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addViolation(ltlr);
	RESULT = ds;
:}
| behavior_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addBehavior(ltlr);
	RESULT = ds;
:}
| ortho_region_decl:ord {:
	DevCState ds = new DevCState();
	ds.addOrthoRegion(ord);
	RESULT = ds;
:}
| state_declaration:sd {:
	DevCState ds = new DevCState();
	ds.addSon(sd);
	RESULT = ds;
:}
| violation_decl:ltlr global_state_body:ds {:
	ds.addViolation(ltlr);
	RESULT = ds;
:}
| behavior_decl:ltlr global_state_body:ds {:
	ds.addBehavior(ltlr);
	RESULT = ds;
:}
| state_declaration:sd global_state_body:ds {:
	ds.addSon(sd);
	RESULT = ds;
:}
| ortho_region_decl:ord global_state_body:ds {:
	ds.addOrthoRegion(ord);
	RESULT = ds;
:};

state_declaration ::= state_type:type T_IDENT:id T_OPEN_BRACE state_body:ds T_CLOSE_BRACE {:
	ds.setName(id);
	ds.setType(type);
	RESULT = ds;
:}
| error:aa {:System.err.println("Error on State Declaration! (Line: " + Lex.currentLine + ")");:};

state_type ::= T_STATE {: RESULT=StateType.STATE; :}
| T_ORTHO_STATE {: RESULT=StateType.ORTHOSTATE; :}
| T_INITIAL_STATE {: RESULT=StateType.INITIALSTATE; :};

state_body ::= state_declaration:sd {:
	DevCState ds = new DevCState();
	ds.addSon(sd);
	RESULT = ds;
:}
| behavior_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addBehavior(ltlr);
	RESULT = ds;
:}
| violation_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addViolation(ltlr);
	RESULT = ds;
:}
| exitpoint_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addExitPoint(ltlr);
	RESULT = ds;
:}
| entrypoint_decl:ltlr {:
	DevCState ds = new DevCState();
	ds.addEntryPoint(ltlr);
	RESULT = ds;
:}
| ortho_region_decl:ord {:
	DevCState ds = new DevCState();
	ds.addOrthoRegion(ord);
	RESULT = ds;
:}
| state_declaration:sd state_body:ds {:
	ds.addSon(sd);
	RESULT = ds;
:}
| behavior_decl:ltlr state_body:ds {:
	ds.addBehavior(ltlr);
	RESULT = ds;
:}
| violation_decl:ltlr state_body:ds {:
	ds.addViolation(ltlr);
	RESULT = ds;
:}
| exitpoint_decl:exit state_body:ds {:
	ds.addExitPoint(exit);
	RESULT = ds;
:}
| entrypoint_decl:entry state_body:ds {:
	ds.addEntryPoint(entry);
	RESULT = ds;
:}
| ortho_region_decl:ord state_body:ds {:
	ds.addOrthoRegion(ord);
	RESULT = ds;
:};


ortho_region_decl ::= T_ORTHO_REGION T_IDENT:name T_OPEN_BRACE states_decl:states T_CLOSE_BRACE {:
	OrthoRegion region = new OrthoRegion(name);
	region.addStates(states);
	RESULT=region;
:};

states_decl ::= state_declaration:sd {:
	HashMap<String, IDevCState> states = new HashMap<String, IDevCState>();
	states.put(sd.getName(),sd);
	RESULT=states;
:}
| state_declaration:sd states_decl:states {:
	if (states.containsKey(sd.getName())){
		Logger.getGlobal().log(Level.SEVERE, " Duplicated State: \"" + sd.getName() + "\"");
		throw new Exception(" Duplicated State: \"" + sd.getName() + "\"");
	}else{
		states.put(sd.getName(),sd);
		RESULT=states;
	}	
:};

behavior_decl ::= T_ADD_BEHAVIOR T_OPEN_PAR T_IDENT:type T_CLOSE_PAR T_OPEN_BRACE temporal_behavior_decl:ltl T_CLOSE_BRACE {: RESULT = new Behavior(type.toUpperCase(), ltl);:}
| T_ADD_BEHAVIOR T_OPEN_PAR T_IDENT:type T_CLOSE_PAR T_IDENT:id T_OPEN_BRACE temporal_behavior_decl:ltl T_CLOSE_BRACE {: RESULT = new Behavior(id, type.toUpperCase(), ltl);:};

violation_decl ::= T_ADD_PROPERTY T_OPEN_PAR violation_type_decl:type T_CLOSE_PAR T_OPEN_BRACE temporal_behavior_decl:ltl T_CLOSE_BRACE {: RESULT = new Violation(type.toUpperCase(), ltl);:}
| T_ADD_PROPERTY T_OPEN_PAR violation_type_decl:type T_CLOSE_PAR T_IDENT:id T_OPEN_BRACE temporal_behavior_decl:ltl T_CLOSE_BRACE {: RESULT = new Violation(id, type.toUpperCase(), ltl);:};

exitpoint_decl ::= T_ADD_EXIT_POINT T_OPEN_PAR T_IDENT:id T_CLOSE_PAR T_OPEN_BRACE propositional_logic:ltl T_PONTVIRG assign_block_decl T_CLOSE_BRACE {:
	RESULT = new ExitPoint(id,ltl.getTerm(), ltl.getZ3Term());
:}
| T_ADD_EXIT_POINT T_OPEN_PAR T_IDENT:id T_CLOSE_PAR T_OPEN_BRACE propositional_logic:ltl T_CLOSE_BRACE {:
	RESULT = new ExitPoint(id,ltl.getTerm(),ltl.getZ3Term());
:};

entrypoint_decl ::= T_ADD_ENTRY_POINT T_OPEN_BRACE propositional_logic:ltl T_PONTVIRG assign_block_decl T_CLOSE_BRACE {:
	RESULT = new EntryPoint(ltl.getTerm(),ltl.getZ3Term());
:}
| T_ADD_ENTRY_POINT T_OPEN_BRACE propositional_logic:ltl T_CLOSE_BRACE {:
	RESULT = new EntryPoint(ltl.getTerm(),ltl.getZ3Term());
:};

violation_type_decl ::= T_CRITICAL {: RESULT="critical"; :}
| T_WARNING {: RESULT="warning"; :}
| T_INFO {: RESULT="info"; :};


assign_block_decl ::= T_ASSIGNS T_OPEN_BRACE assigns_decl T_CLOSE_BRACE {:
:};

assigns_decl ::= assign_decl T_PONTVIRG {::}
| assign_decl T_PONTVIRG assigns_decl {::}; 

assign_decl ::= T_IDENT:id T_IGUAL expr_decl {::};

expr_decl ::= T_OPEN_PAR expr_decl:exp T_CLOSE_PAR {: RESULT = "(" + exp + ")"; :}
| expr_decl:id1 T_PLUS expr_decl:id2  {: RESULT = "(" + id1 + " + " + id2 + ")"; :}
| expr_decl:id1 T_MINUS expr_decl:id2  {: RESULT = "(" + id1 + " - " + id2 + ")"; :}
| expr_decl:id1 T_MULT expr_decl:id2  {: RESULT = "(" + id1 + " * " + id2 + ")"; :}
| expr_decl:id1 T_DIV expr_decl:id2  {: RESULT = "(" + id1 + " / " + id2 + ")"; :}
| expr_decl:id1 T_LSHIFT expr_decl:id2  {: RESULT = "(" + id1 + " << " + id2 + ")"; :}
| expr_decl:id1 T_RSHIFT expr_decl:id2  {: RESULT = "(" + id1 + " >> " + id2 + ")"; :}
| T_IDENT:id {: RESULT = id; :}
| T_INT:val {: RESULT = val; :};

temporal_behavior_decl ::= T_LTLF T_OPEN_PAR ltl_logic_expr:ltlf T_CLOSE_PAR {: RESULT = ltlf.getTerm();:}
| T_LLTLF T_OPEN_PAR T_TEXTSTR:ltlformula T_CLOSE_PAR {: RESULT = ltlformula.toString(); :};
//| boolean_seq;

//******************************MANTEM******************************
/*boolean_seq ::= boolean_seq T_PONTVIRG logic_expr
| logic_expr;*/

propositional_logic ::= propositional_logic:term T_BOOL_EQUI propositional_logic:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " <-> " + term2.getTerm(), "EQUIV(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
:}
| propositional_logic:term T_BOOL_IMPL propositional_logic:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " -> " + term2.getTerm(), "IMPL(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
:}
| propositional_logic:term T_BOOL_CONJ propositional_logic:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " && " + term2.getTerm(), "And(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
:}
| propositional_logic:term T_BOOL_DISJ propositional_logic:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " || " + term2.getTerm(), "Or(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
:}
| T_PROP_NEG_OPER propositional_logic:term {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("!" + term.getTerm(), "NEG(" + term.getZ3Term() + ")");
:}
| T_OPEN_PAR propositional_logic:term T_CLOSE_PAR {:
	//LTLRuleTerm esq = new LTLRuleTerm("(");
	//LTLRuleTerm dir = new LTLRuleTerm(")");
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("(" + term.getTerm() + ")","(" + term.getZ3Term() + ")");
:}
| proposition_expr:prop {:
	Integer propID,propID2;
	/*System.out.println(prop.toString());
	System.out.println(prop.getType());*/
	switch (prop.getType()) {
		case BOOLEAN:
			propID = this.parser.getTDCCheckerGenerator().addProposition(prop.toString());
			RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("p" + propID, "p" + propID + "");
			break;
		case ACCESS:
			propID = this.parser.getTDCCheckerGenerator().addProposition(prop.toString());
			RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("p" + propID, "p" + propID + "");
			break;
		case IDENT:
			propID = this.parser.getTDCCheckerGenerator().addProposition(prop.toString());
			RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("p" + propID, "p" + propID + "");
			break;
		case COMPOSITE:
			//System.out.print(((CompositePropositionExpression) prop).getPropExprs().get(0).toString() + " && " + ((CompositePropositionExpression) prop).getPropExprs().get(1).toString());
			propID = this.parser.getTDCCheckerGenerator().addProposition(((CompositePropositionExpression) prop).getPropExprs().get(0).toString());
			//System.out.print(" - " + propID);
			LTLRuleTerm term = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("p" + propID, "p" + propID + "");
			propID2 = this.parser.getTDCCheckerGenerator().addProposition(((CompositePropositionExpression) prop).getPropExprs().get(1).toString());
			//System.out.println(" - " + propID2);
			LTLRuleTerm term2 = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("p" + propID2, "p" + propID2 + "");
			if(((CompositePropositionExpression) prop).isConjunction()) {
				RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " && " + term2.getTerm(), "And(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
			}else{
				RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " || " + term2.getTerm(), "Or(" + term.getZ3Term() + "," + term2.getZ3Term() + ")");
			}
			break;
		default:
			System.out.println("NULO!!");
			RESULT = null;
			break;
	}
	//propID = TDCCheckerGenerator.getInstance().addProposition(prop);
	//TDCCheckerGenerator.getInstance().addTerm(newName);
:};

ltl_logic_expr ::= ltl_logic_expr:term T_UNTIL_OPER ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " U " + term2.getTerm());
:}
| ltl_logic_expr:term T_WEAK_UNTIL_OPER ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " W " + term2.getTerm());
:}
| ltl_logic_expr:term T_DUAL_UNTIL_OPER ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " D " + term2.getTerm());
:}
| T_ALWAYS_OPER  ltl_logic_expr:term {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("[]" + term.getTerm());
:}
| T_EVENT_OPER  ltl_logic_expr:term {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("<>" + term.getTerm());
:}
| T_NEXT_OPER ltl_logic_expr:term {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("()" + term.getTerm());
:}
| ltl_logic_expr:term T_BOOL_EQUI ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " <-> " + term2.getTerm());
:}
| ltl_logic_expr:term T_BOOL_IMPL ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " -> " + term2.getTerm());
:}
| ltl_logic_expr:term T_BOOL_CONJ ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " && " + term2.getTerm());
:}
| ltl_logic_expr:term T_BOOL_DISJ ltl_logic_expr:term2 {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm(term.getTerm() + " || " + term2.getTerm());
:}
| T_PROP_NEG_OPER ltl_logic_expr:term {:
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("!" + term.getTerm());
:}
| T_OPEN_PAR ltl_logic_expr:term T_CLOSE_PAR {:
	//LTLRuleTerm esq = new LTLRuleTerm("(");
	//LTLRuleTerm dir = new LTLRuleTerm(")");
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("(" + term.getTerm() + ")");
:}
| proposition_expr:prop {:
	Integer propID;
	propID = this.parser.getTDCCheckerGenerator().addLTLProposition(prop.toString());
	//TDCCheckerGenerator.getInstance().addTerm(newName);
	RESULT = LTLRuleTermFactory.getInstance().getNewLTLRuleTerm("q" + propID, "q" + propID + "");
:};

//| propositional_logic:propL {: RESULT = propL;:};

/*ltl_logic_until_expr ::= ltl_logic_until_expr ltl_binary_until_oprs ltl_logic_unary
| T_OPEN_PAR ltl_logic_until_expr ltl_binary_until_oprs ltl_logic_unary T_CLOSE_PAR
| ltl_logic_unary;

ltl_logic_unary ::= ltl_unary_oprs ltl_logic_unary
| T_OPEN_PAR ltl_unary_oprs ltl_logic_unary T_CLOSE_PAR
| proposition_expr;*/

/*ltl_logic_expr ::= proposition_expr 
| T_OPEN_PAR ltl_logic_expr T_CLOSE_PAR
| proposition_expr ltl_binary_oprs ltl_logic_expr
| ltl_unary_oprs ltl_logic_expr;*/

/*ltl_unary_oprs ::= T_PROP_NEG_OPER
| T_ALWAYS_OPER
| T_EVENT_OPER
| T_NEXT_OPER;*/

/*ltl_binary_oprs ::= T_BOOL_EQUI
| T_BOOL_IMPL
| T_BOOL_CONJ
| T_BOOL_DISJ
| T_UNTIL_OPER
| T_WEAK_UNTIL_OPER
| T_DUAL_UNTIL_OPER;*/

/*boolean_expr ::= boolean_expr T_BOOL_EQUI boolean_conj_dis
| boolean_expr T_BOOL_IMPL boolean_conj_dis
| boolean_conj_dis
| T_OPEN_PAR boolean_expr T_CLOSE_PAR
| T_PROP_NEG_OPER T_OPEN_PAR boolean_expr T_CLOSE_PAR;

boolean_conj_dis ::= boolean_conj_dis T_BOOL_CONJ boolean_prop
| boolean_conj_dis T_BOOL_DISJ boolean_prop
| boolean_prop
| T_PROP_NEG_OPER boolean_prop;*/

proposition_expr ::= T_TRUE {: RESULT = new BooleanPropositionExpression(BooleanPropositionExpression.LocalBooleanType.TRUE);:}
| T_FALSE {: RESULT = new BooleanPropositionExpression(BooleanPropositionExpression.LocalBooleanType.FALSE);:}
| T_IDENT:state {: RESULT = new IdentifierPropositionExpression(state);:}
| reg_access:reg {:
	RESULT = reg;
:}
|reg_access:reg T_OPER:op reg_comp_expr:id2 {:
	CompositePropositionExpression compExp = new CompositePropositionExpression(true);
	compExp.addPropostionExpr(reg);
	compExp.addPropostionExpr(new IdentifierPropositionExpression("(" + reg.getFieldRep() + " " + op + " " + id2 + ")"));
	RESULT = compExp; 
	//RESULT = reg;
:}
| reg_comp_expr:id1 T_OPER:op reg_comp_expr:id2 {:
	RESULT = new IdentifierPropositionExpression("(" + id1 + " " + op + " " + id2 + ")");
	//RESULT = "(" + id1 + " " + op + " " + id2 + ")";
:}
/*| reg_comp_expr:comp {:
	RESULT = comp;
:}*/;


//T_PLUS, T_MINUS, T_MULT, T_DIV
reg_expr ::= reg_expr:id1 T_PLUS reg_expr:id2  {:RESULT = "(" + id1 + " + " + id2 + ")"; :}
| reg_expr:id1 T_MINUS reg_expr:id2  {:  RESULT = "(" + id1 + " - " + id2 + ")"; :}
| reg_expr:id1 T_MULT reg_expr:id2  {:  RESULT = "(" + id1 + " * " + id2 + ")"; :}
| reg_expr:id1 T_DIV reg_expr:id2  {:  RESULT = "(" + id1 + " / " + id2 + ")"; :}
| reg_val:reg {:  RESULT = reg; :};
/*| reg_access:access {: RESULT = access; :};*/

reg_comp_expr ::= reg_comp_expr:id1 T_OPER:op reg_comp_expr:id2 {:
	RESULT = "(" + id1 + " " + op + " " + id2 + ")";
:}
| reg_expr:exp {:  RESULT = exp;:};
/*reg_comp_expr ::= reg_comp_expr T_COMP_OPER reg_val
| reg_val;*/

reg_val ::= reg_ref_decl:reg {: RESULT= reg; :}
| T_INT:int1 {: RESULT = int1; :};

reg_access ::= T_REGWRITE T_OPEN_PAR reg_ref_decl:reg T_CLOSE_PAR {:
	RESULT = new AccessPropositionExpression(AccessPropositionExpression.LocalAccessType.WRITE, reg);
	//RESULT = "WRITE%" + reg;
:}
|T_REGREAD T_OPEN_PAR reg_ref_decl:reg T_CLOSE_PAR {:
	RESULT = new AccessPropositionExpression(AccessPropositionExpression.LocalAccessType.READ, reg);
	//RESULT = "READ%" + reg;
:};

reg_ref_decl ::= T_IDENT:id T_DOT reg_ref_decl:reg {:
	RESULT = id +"."+ reg;
:}
| T_IDENT:id {: RESULT = id; :};
//******************************MANTEM******************************


/*logic_expr ::= proposition_expr 
| T_OPEN_PAR logic_expr T_CLOSE_PAR
| logic_expr binary_oprs logic_expr
| unary_oprs logic_expr;*/

/*unary_oprs ::= 

binary_oprs ::= T_BOOL_EQUI
| T_BOOL_IMPL
| T_BOOL_CONJ
| T_BOOL_DISJ;*/

/*

Operands (opd):
	true, false, user-defined names starting with a lower-case letter,
	or embedded expressions inside curly braces, e.g.,: { a+b>n }.

Unary Operators (unop):
	[]	(the temporal operator always)
	<>	(the temporal operator eventually)
	! 	(the boolean operator for negation)

Binary Operators (binop):
	U 	(the temporal operator strong until)
	W	(the temporal operator weak until
	V 	(the dual of U): (p V q) means !(!p U !q))
	&&	(the boolean operator for logical and)
	||	(the boolean operator for logical or)
	/\	(alternative form of &&)
	\/	(alternative form of ||)
	->	(the boolean operator for logical implication)
	<->	(the boolean operator for logical equivalence)

*/ 
