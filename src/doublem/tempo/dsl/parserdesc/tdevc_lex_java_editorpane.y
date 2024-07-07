package doublem.tempo.gui.code;
import jsyntaxpane.DefaultLexer;
import jsyntaxpane.Token;
import jsyntaxpane.TokenType;

%% 

%public

%class DevCLexer

%extends DefaultLexer

%final

%unicode

%char

%type Token

%{
    /**
     * Create an empty lexer, yyrset will be called later to reset and assign
     * the reader
     */
    public DevCLexer() {
        super();
    }

    private Token token(TokenType type) {
        return new Token(type, yychar, yylength());
    }

    private Token token(TokenType type, int pairValue) {
        return new Token(type, yychar, yylength(), (byte)pairValue);
    }

    private static final byte PARAN     = 1;
    private static final byte BRACKET   = 2;
    private static final byte CURLY     = 3;
    private static final byte TYPE      = 4;
%}

IDENT =	[a-zA-Z][a-zA-Z0-9_]*
INT = 	[0-9]+
HEX = [0][x][0-9abcdefABCDEF]+
BIN = [b][01]+
MASK  =   [01.]
SPACE =  [ \t\n\r]*
COMMENT = ([/][/] .* [\t\n\r]+ | [/][*] ([^*/] | [\t\n\r])* [*][/])
TEXTSTR = [\"].*[\"]

%%
<YYINITIAL> {

{COMMENT}	{return token(TokenType.COMMENT);}
{INT}     {return token(TokenType.NUMBER);}
{HEX}     {return token(TokenType.NUMBER);}
{BIN}     {return token(TokenType.NUMBER);}
{TEXTSTR}  {return token(TokenType.STRING);}
"("	{return token(TokenType.OPERATOR,  PARAN);}
")"	{return token(TokenType.OPERATOR,  -PARAN);}
";"	{return token(TokenType.OPERATOR);}
":"	{return token(TokenType.OPERATOR);}
"device"	{return token(TokenType.KEYWORD2);}
"{"	{return token(TokenType.OPERATOR,  CURLY);}
"}"	{return token(TokenType.OPERATOR,  -CURLY); }
"["	{return token(TokenType.OPERATOR,  BRACKET);}
"]"	{return token(TokenType.OPERATOR,  -BRACKET);}
"="	{return token(TokenType.OPERATOR);}
"\""	{return token(TokenType.OPERATOR);}
"'"	{return token(TokenType.OPERATOR);}
"%"	{return token(TokenType.OPERATOR);}
"%RESERVED"	|
"reserved"	{return token(TokenType.KEYWORD);}
"with"	{return token(TokenType.KEYWORD);}
"alias"	    {return token(TokenType.KEYWORD2);}
"%MASK"		{return token(TokenType.KEYWORD);}
"cannot"	|
"must"		|
"might"		|
"main"		|
"dc_register"	| //deprec
"dc_pattern"	| //deprec
"register"	|
"memory"	|
"format"	|
"pattern"	|
"requirement"	|
"exception"	|
"var"		|
"service"	{return token(TokenType.KEYWORD2);}
"mask"		|
"s"			|
"r"			{return token(TokenType.KEYWORD);}
"."			|
"*"			{return token(TokenType.OPERATOR);}
"@"			{return token(TokenType.OPERATOR);}
{MASK}{32}  {return token(TokenType.IDENTIFIER);}
"dc_scml_port" 	{return token(TokenType.KEYWORD);}
"dc_tlm_port"  	{return token(TokenType.KEYWORD);}
"dc_format"		{return token(TokenType.KEYWORD2);}  //deprec
"dc_service"	{return token(TokenType.KEYWORD2);}  //deprec
"tdc_anyvalue"	|   //deprec
"anyvalue"	| 
"services_temporal_properties" |
"dc_reg"		{return token(TokenType.KEYWORD2);} //deprec
"dc_buffer"		{return token(TokenType.KEYWORD);}
"dc_drv_map"	|
"dc_os"			{return token(TokenType.KEYWORD);}
"dc_os_map"		|
"import"		|
"behaviors"		{return token(TokenType.KEYWORD2);}
"bindings"		{return token(TokenType.KEYWORD2);}
"dc_clk_port"	{return token(TokenType.TYPE);}
">"	{return token(TokenType.OPERATOR, -TYPE); }
"<"	{return token(TokenType.OPERATOR, TYPE); }
","	{return token(TokenType.OPERATOR);}
"BULKREAD"		|
"BULKWRITE"		|
"NNWRITEUNTIL"	|
"NNREADUNTIL"	|
"WRITEUNTIL"	|
"READUNTIL"		|
"READ"			|
"WRITE"			|
"OPEN"			|
"INIT" 			|
"RELEASE"		|
"CLOSE"			|
"IOCTL"			|
"SEEK"			|
"VOID"  		|
"RW"			|
"NS"			|
"US"			|
"PS"			|
"MS"			{return token(TokenType.TYPE);}
"set_address"	| //deprec
"setaddress"	| //deprec
"bindTo"		|
"mindelay"		|
"maxdelay"		|
"call"			|
"set_temporal_properties" |
"setactions"	{return token(TokenType.KEYWORD2);}
"addconstraints"	{return token(TokenType.KEYWORD2);}
"mapping"	{return token(TokenType.KEYWORD2);}
"set_behaviors"	{return token(TokenType.KEYWORD2);}
"set_exceptions"	{return token(TokenType.KEYWORD2);}
"setactions"	{return token(TokenType.KEYWORD2);}
/*"set_arbitrary_actions"	{return token(TokenType.KEYWORD);}*/
"internal"			{return token(TokenType.KEYWORD2);}
"external"			{return token(TokenType.KEYWORD2);}
"irqline"			{return token(TokenType.KEYWORD2);}
"address"			{return token(TokenType.TYPE);}
"data"			{return token(TokenType.TYPE);}
"startup"			{return token(TokenType.KEYWORD);}
"addreadrequirement"			{return token(TokenType.KEYWORD);}
"addwriterequirement"			{return token(TokenType.KEYWORD);}
"addrequirement"			{return token(TokenType.KEYWORD);}
"addproperty"			{return token(TokenType.KEYWORD);}
"critical"      {return token(TokenType.KEYWORD);}
"assigns"		{return token(TokenType.KEYWORD);}
"ltlf"			{return token(TokenType.KEYWORD);}
"<>"			|
"()"			|
"U"				|
"W"				|
"V"				|
"[]"			|
"->"			|
"<->"			{return token(TokenType.KEYWORD);}
"addentrypoint"			{return token(TokenType.KEYWORD);}
"addexitpoint"			{return token(TokenType.KEYWORD);}
"addbehavior"			{return token(TokenType.KEYWORD);}
//"datapackage"			{return token(TokenType.KEYWORD);}
"assign"			{return token(TokenType.KEYWORD);}
"startfield"			{return token(TokenType.KEYWORD);}
"endfield"			{return token(TokenType.KEYWORD);}
"statusfield"			{return token(TokenType.KEYWORD);}
"payloadfield"			{return token(TokenType.KEYWORD);}
"lengthfield"			{return token(TokenType.KEYWORD);}
"globalstate"			{return token(TokenType.KEYWORD2);}
"initialstate"			{return token(TokenType.KEYWORD2);}
"state"			{return token(TokenType.KEYWORD2);}
"orthostate"			{return token(TokenType.KEYWORD2);}
"initialstate"			{return token(TokenType.KEYWORD2);}
"globalstate"			{return token(TokenType.KEYWORD2);}
"orthoregion"			{return token(TokenType.KEYWORD2);}
"after"			{return token(TokenType.KEYWORD2);}
"before"			{return token(TokenType.KEYWORD2);}
"requires"		{return token(TokenType.KEYWORD2);}
"requires_or"		{return token(TokenType.KEYWORD2);}
"requires_and"		{return token(TokenType.KEYWORD2);}
"always_requires"		{return token(TokenType.KEYWORD2);}
"wait_state"	{return token(TokenType.KEYWORD);}
"interrupt" 	{return token(TokenType.KEYWORD);}
"polling"		{return token(TokenType.KEYWORD);}
"dbg_print"		{return token(TokenType.KEYWORD);}
"write"		{return token(TokenType.KEYWORD);}
"read"		{return token(TokenType.KEYWORD);}
"if"		{return token(TokenType.KEYWORD);}
"else"		{return token(TokenType.KEYWORD);}
"ltlf"		{return token(TokenType.KEYWORD);}
"literal_ltlf"		{return token(TokenType.KEYWORD);}
"=="    |
"!="    |
"<="    |
">="	|
"&&"	|
"||"	|
"+"	    {return token(TokenType.KEYWORD); }
"uclinux"	{return token(TokenType.KEYWORD2);}
{IDENT}     {return token(TokenType.IDENTIFIER);}
{SPACE}     {            }
.           {            }

}

.|\n                             {  }
<<EOF>>                          { return null; }