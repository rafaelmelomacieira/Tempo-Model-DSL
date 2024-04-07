package br.ufpe.cin.greco.devc.languageStructure.devc;

import java_cup.runtime.*;
import java.util.HashSet;
%%
%public
%class Lex
%cup
%line
%{
    public static Integer currentLine = 0;
	public Lex(java.io.InputStream r, SymbolFactory sf){
		this(r);
		this.sf=sf;
	}
	private SymbolFactory sf;
	//public HashSet<String> varlist = new HashSet<String>();
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}


IDENT =	[a-zA-Z_][a-zA-Z0-9_]*
INT = 	[0-9]+
HEX = 	[0][x][0-9abcdefABCDEF]+
BIN = 	b[01]+
MASK  =   [01.]*
SPACE =  [ \t]*
NEWLINE = [\n\r]*
COMMENT = ([/][/] .* [\t\n\r]+ | [/][*] ([^*/] | [\t\n\r])* [*][/])
TEXTSTR = [\"].*[\"]
%%
{COMMENT}	{			 }
{INT}     {return sf.newSymbol("",sym.T_INT,yytext());}
{HEX}     {return sf.newSymbol("",sym.T_INT,yytext());}
{BIN}     {return sf.newSymbol("",sym.T_INT,yytext());}
{TEXTSTR}     {return sf.newSymbol("",sym.T_TEXTSTR,yytext().replace("\"", ""));}
"("	{return sf.newSymbol("",sym.T_OPEN_PAR);}
")"	{return sf.newSymbol("",sym.T_CLOSE_PAR);}
";"	{return sf.newSymbol("",sym.T_PONTVIRG);}
":"	{return sf.newSymbol("",sym.T_DOISPONTOS);}
//"DEVC"	{return sf.newSymbol("",sym.T_DEVC);} //deprec
"device"	{return sf.newSymbol("",sym.T_DEVICE);}
"{"	{return sf.newSymbol("",sym.T_OPEN_BRACE);}
"}"	{return sf.newSymbol("",sym.T_CLOSE_BRACE);}
"["	{return sf.newSymbol("",sym.T_OPEN_BRACKET);}
"]"	{return sf.newSymbol("",sym.T_CLOSE_BRACKET);}
"="	{return sf.newSymbol("",sym.T_IGUAL);}
//"\""	{return sf.newSymbol("",sym.T_ASPAS);}
//"'"	{return sf.newSymbol("",sym.T_ASPA);}
/*"%"	{return sf.newSymbol("",sym.T_PERCENT);}
"%RESERVED"	{return sf.newSymbol("",sym.T_PERCENT_RESERVED);} //deprec
"reserved"	{return sf.newSymbol("",sym.T_RESERVED);} //deprec
"%MASK"	{return sf.newSymbol("",sym.T_PERCENT_MASK);} //deprec*/
"mask"	{return sf.newSymbol("",sym.T_MASK);} //deprec
/*"s"	{return sf.newSymbol("",sym.T_S);}
"r"	{return sf.newSymbol("",sym.T_R);}*/
"."	{return sf.newSymbol("",sym.T_DOT);}
//"*"	{return sf.newSymbol("",sym.T_AST);}
{MASK}{32}       {return sf.newSymbol("",sym.T_MASKREP,yytext());}
/*"dc_scml_port"	{return sf.newSymbol("",sym.T_DC_SCML_PORT);}
"dc_tlm_port"	{return sf.newSymbol("",sym.T_DC_TLM_PORT);}
"dc_clk_port"	{return sf.newSymbol("",sym.T_DC_CLK_PORT);}*/
//"dc_format"	{return sf.newSymbol("",sym.T_DC_FORMAT);} //deprec
"format"	{return sf.newSymbol("",sym.T_DC_FORMAT);}
//"dc_service"	{return sf.newSymbol("",sym.T_DC_SERVICE);}  //deprec
//"service"	{return sf.newSymbol("",sym.T_DC_SERVICE);}
"pattern"	{return sf.newSymbol("",sym.T_PATTERN);}
//"doreset"	{return sf.newSymbol("",sym.T_DORESET);}
//"tdc_anyvalue" {return sf.newSymbol("",sym.T_ANYVALUE);} //deprec
//"anyvalue" {return sf.newSymbol("",sym.T_ANYVALUE);} //deprec
//"allows"  {return sf.newSymbol("",sym.T_ALLOWS);}
//"denies"  {return sf.newSymbol("",sym.T_DENIES);}
/*"main"  {return sf.newSymbol("",sym.T_MAIN);}
"must"  {return sf.newSymbol("",sym.T_MUST);}
"might"  {return sf.newSymbol("",sym.T_MIGHT);}
"cannot"  {return sf.newSymbol("",sym.T_CANNOT);}*/
"reserved"  {return sf.newSymbol("",sym.T_RESERVED);}
//"with"  {return sf.newSymbol("",sym.T_WITH);}
"mindelay"  {return sf.newSymbol("",sym.T_MIN_DELAY);}
"maxdelay"  {return sf.newSymbol("",sym.T_MAX_DELAY);}
/*">"	{return sf.newSymbol("",sym.T_MAIOR,">");}
"<"	{return sf.newSymbol("",sym.T_MENOR,"<");}*/
","	{return sf.newSymbol("",sym.T_VIRG);}
/*"BULKREAD"	{return sf.newSymbol("",sym.T_BREAD);}
"BULKWRITE"	{return sf.newSymbol("",sym.T_BWRITE);}
"NNWRITEUNTIL"	{return sf.newSymbol("",sym.T_NNWRITEU);}
"NNREADUNTIL"	{return sf.newSymbol("",sym.T_NNREADU);}
"WRITEUNTIL"	{return sf.newSymbol("",sym.T_WRITEU);}
"READUNTIL"	{return sf.newSymbol("",sym.T_READU);}*/
"read"	{return sf.newSymbol("",sym.T_REGREAD);}
"write"	{return sf.newSymbol("",sym.T_REGWRITE);}
"READ"	{return sf.newSymbol("",sym.T_READ);}
"WRITE"	{return sf.newSymbol("",sym.T_WRITE);}
"RW"	{return sf.newSymbol("",sym.T_READWRITE);}
"S"		{return sf.newSymbol("",sym.T_SEC);}
"MS"	{return sf.newSymbol("",sym.T_MS);}
"US"	{return sf.newSymbol("",sym.T_US);}
"NS"	{return sf.newSymbol("",sym.T_NS);}
"PS"	{return sf.newSymbol("",sym.T_PS);}
//"MIN"	{return sf.newSymbol("",sym.T_MIN);}
//"MAX"	{return sf.newSymbol("",sym.T_MAX);}
"import"	{return sf.newSymbol("",sym.T_IMPORT);}
//"@"  	{return sf.newSymbol("",sym.T_ARROUBA);}
//"dc_reg"	{return sf.newSymbol("",sym.T_DC_REG);} //deprec
"register"	{return sf.newSymbol("",sym.T_DC_REG);}
"memory"	{return sf.newSymbol("",sym.T_MEMORY);}
"alias"	{return sf.newSymbol("",sym.T_ALIAS);}
/*"dc_buffer"	{return sf.newSymbol("",sym.T_DC_BUFFER);}
"dc_drv_map"	{return sf.newSymbol("",sym.T_DC_DRV_MAP);}
"dc_os"	{return sf.newSymbol("",sym.T_DC_OS);}
"dc_os_map"	{return sf.newSymbol("",sym.T_DC_OS_MAP);}
"OPEN"	{return sf.newSymbol("",sym.T_OPEN);}
"INIT"	{return sf.newSymbol("",sym.T_INIT);}
"RELEASE"	{return sf.newSymbol("",sym.T_RELEASE);}
"CLOSE"	{return sf.newSymbol("",sym.T_CLOSE);}
"IOCTL"	{return sf.newSymbol("",sym.T_IOCTL);}
"SEEK"	{return sf.newSymbol("",sym.T_SEEK);}
"VOID"  {return sf.newSymbol("",sym.T_VOID);}
"ctor"	{return sf.newSymbol("",sym.T_DEV_CTOR);}
"behaviors"	{return sf.newSymbol("",sym.T_DEV_BEHAVIORS);}
"bindings"	{return sf.newSymbol("",sym.T_DEV_BINDINGS);}
"set_address"	{return sf.newSymbol("",sym.T_SET_ADDRESS);}
"bindTo"	{return sf.newSymbol("",sym.T_BINDTO);}
"set_temporal_properties"	{return sf.newSymbol("",sym.T_SET_TEMP_PROP);}
"services_temporal_properties" 	{return sf.newSymbol("",sym.T_SERV_TEMP_PROP);}
"setactions"	{return sf.newSymbol("",sym.T_SET_ACTION);}
"set_arbitrary_actions"	{return sf.newSymbol("",sym.T_SET_ARB_ACTION);}
"pre"	{return sf.newSymbol("",sym.T_PRE);}
"pos"	{return sf.newSymbol("",sym.T_POS);}

"after"			{return sf.newSymbol("",sym.T_AFTER);}
"before"			{return sf.newSymbol("",sym.T_BEFORE);}
"always_requires"		{return sf.newSymbol("",sym.T_AREQUIRES);}*/
//"requiresall"		{return sf.newSymbol("",sym.T_REQUIRESALL);}
/*"dbg_print"		{return sf.newSymbol("",sym.T_DBGPRINT);}
"requirement"	{return sf.newSymbol("",sym.T_REQUIREMENT);}
"exception"	    {return sf.newSymbol("",sym.T_EXCEPTION);}
"addconstraints"	{return sf.newSymbol("",sym.T_ADDCONSTRAINTS);}*/
//"global" {return sf.newSymbol("", sym.T_GLOBAL);}
"internal" {return sf.newSymbol("", sym.T_INTERNAL);}
"external" {return sf.newSymbol("", sym.T_EXTERNAL);} 
/*"trigger" {return sf.newSymbol("", sym.T_TRIGGER);}*/
"var" {return sf.newSymbol("", sym.T_VAR);}
/*"requires"		{return sf.newSymbol("",sym.T_REQUIRES);}
"requires_or"		{return sf.newSymbol("",sym.T_REQUIRES);}
"requires_and"		{return sf.newSymbol("",sym.T_REQUIRES_AND);}
"wait_state"	{return sf.newSymbol("",sym.T_WAIT_STATE);}
"interrupt"	{return sf.newSymbol("",sym.T_INTERRUPT);}
"polling"	{return sf.newSymbol("",sym.T_POLLING);}*/
"=="    {return sf.newSymbol("",sym.T_OPER,"==");}
"!="    {return sf.newSymbol("",sym.T_OPER,"!=");}
"<="    {return sf.newSymbol("",sym.T_OPER,"<=");}
">="	{return sf.newSymbol("",sym.T_OPER,">=");}
"<"	{return sf.newSymbol("",sym.T_OPER,"<");}
">"	{return sf.newSymbol("",sym.T_OPER,">");}
">>"	{return sf.newSymbol("",sym.T_RSHIFT,">");}
"<<"	{return sf.newSymbol("",sym.T_LSHIFT,">");}
//"&&"	{return sf.newSymbol("",sym.T_LOGIC_OPER,"&&");}
//"||"	{return sf.newSymbol("",sym.T_LOGIC_OPER,"||");}
"[]"	{return sf.newSymbol("",sym.T_ALWAYS_OPER);}
"()"	{return sf.newSymbol("",sym.T_NEXT_OPER);}
"<>"	{return sf.newSymbol("",sym.T_EVENT_OPER);}
"U"	{return sf.newSymbol("",sym.T_UNTIL_OPER);}
"W"	{return sf.newSymbol("",sym.T_WEAK_UNTIL_OPER);}
"V"	{return sf.newSymbol("",sym.T_DUAL_UNTIL_OPER);}
"->"	{return sf.newSymbol("",sym.T_BOOL_IMPL);}
"<->"	{return sf.newSymbol("",sym.T_BOOL_EQUI);}
"&&"	{return sf.newSymbol("",sym.T_BOOL_CONJ);}
"||"	{return sf.newSymbol("",sym.T_BOOL_DISJ);}
"~"	{return sf.newSymbol("",sym.T_PROP_NEG_OPER); }
"!"	{return sf.newSymbol("",sym.T_PROP_NEG_OPER); }
"¬"	{return sf.newSymbol("",sym.T_PROP_NEG_OPER); }
"+"	{return sf.newSymbol("",sym.T_PLUS);}
"-"	{return sf.newSymbol("",sym.T_MINUS);}
"*"	{return sf.newSymbol("",sym.T_MULT);}
"/"	{return sf.newSymbol("",sym.T_DIV);}
"state" {return sf.newSymbol("", sym.T_STATE);}
"initialstate" {return sf.newSymbol("", sym.T_INITIAL_STATE);}
"globalstate" {return sf.newSymbol("", sym.T_GLOBAL_STATE);}
"orthostate" {return sf.newSymbol("", sym.T_ORTHO_STATE);}
"orthoregion" {return sf.newSymbol("", sym.T_ORTHO_REGION);}
"addproperty" {return sf.newSymbol("", sym.T_ADD_PROPERTY);}
"addbehavior" {return sf.newSymbol("", sym.T_ADD_BEHAVIOR);}
"addexitpoint" {return sf.newSymbol("", sym.T_ADD_EXIT_POINT);}
"addentrypoint" {return sf.newSymbol("", sym.T_ADD_ENTRY_POINT);}
"assigns" {return sf.newSymbol("", sym.T_ASSIGNS);}
"ltlf" {return sf.newSymbol("", sym.T_LTLF);}
"literal_ltlf" {return sf.newSymbol("", sym.T_LLTLF);}
"false" {return sf.newSymbol("", sym.T_FALSE);}
"true" {return sf.newSymbol("", sym.T_TRUE);}
"critical" {return sf.newSymbol("", sym.T_CRITICAL);}
"warning" {return sf.newSymbol("", sym.T_WARNING);}
"info" {return sf.newSymbol("", sym.T_INFO);}
//"uclinux"	{return sf.newSymbol("",sym.T_UCLINUX);}
{IDENT}     {return sf.newSymbol("Ident",sym.T_IDENT,yytext());}
{SPACE}     {            }
{NEWLINE} {Lex.currentLine = yyline;}