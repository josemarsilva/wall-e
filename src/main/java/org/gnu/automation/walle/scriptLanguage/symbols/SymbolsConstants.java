package org.gnu.automation.walle.scriptLanguage.symbols;

/*
 * SymbolsConstants class is responsible for define error and warning 
 * messages, constants values for *Symbols*
 */
public class SymbolsConstants {

	// Constants ...
	
	public final static int SYMBOL_TYPE_CLIARGSPARAM = 1;
	public final static int SYMBOL_TYPE_VARIABLE = 2;
	public final static int SYMBOL_TYPE_TABLEOF = 3;
	
	public final static String SYMBOL_NAME_CLIARGSPARAM_PREFIX = "args";
	
	
	// WARNING AND ERROR MESSAGES ... 
	
	public final static String MSG_TXT_ERROR_SYMBLE_TYPE_INVALID = new String("Error: Symbole type must be [ SYMBOL_TYPE_VARIABLE, SYMBOL_TYPE_CLIARGSPARAM]");
	
	
}
