package org.gnu.automation.walle.scriptLanguage.symbols;

/*
 * SymbolsConstants class is responsible for define error and warning 
 * messages, constants values for *Symbols*
 */
public class SymbolsConstants {

	// Constants ...
	
	public final static String SYMBOL_TYPE_CLIARGSPARAM = "cliArgsParam";
	public final static String SYMBOL_TYPE_VARIABLE = "variable";
	public final static String SYMBOL_TYPE_TABLEOF = "table";
	public final static String SYMBOL_TYPE_LASTWEBELEMENTFOUND = "lastWebElementFound";
	public final static String SYMBOL_TYPE_PROGRAMADDRESSSYMBOL= "ProgramAddressSymbol";
	
	public final static String SYMBOL_ATTRIBUTE_VALUE = "value";
	public final static String SYMBOL_ATTRIBUTE_SYMBOLTYPE = "symbolType";
	public final static String SYMBOL_ATTRIBUTE_FOREACH_BEGIN = "forEachBegin";
	public final static String SYMBOL_ATTRIBUTE_FOREACH_END = "forEachEnd";
	public final static String SYMBOL_ATTRIBUTE_FOREACH_ITERATOR = "forEachIterator";
	
	public final static String SYMBOL_NAME_CLIARGSPARAM_PREFIX = "args";
	public final static String SYMBOL_NAME_LASTWEBELEMENTFOUND = "lastWebElementFound";
	public final static String SYMBOL_NAME_FOREACH_BEGIN_SUFIX = "begin";
	public final static String SYMBOL_NAME_FOREACH_END_SUFIX = "end";
	
	
	// WARNING AND ERROR MESSAGES ... 
	
	public final static String MSG_TXT_ERROR_SYMBLE_TYPE_INVALID = new String("Error: Symbole type must be [ SYMBOL_TYPE_VARIABLE, SYMBOL_TYPE_CLIARGSPARAM]");
	
	
}
