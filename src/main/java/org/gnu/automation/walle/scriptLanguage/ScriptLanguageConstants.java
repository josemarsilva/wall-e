package org.gnu.automation.walle.scriptLanguage;

/*
 * ScriptLanguageConstants class is responsible for define error and warning 
 * messages, constants values for script, etc 
 */
public class ScriptLanguageConstants {
	
	// MESSAGES ...
	
	public final static String MSG_TXT_ERROR_COMMAND_ARG_FILENAME_UNDEFINED = "Error: '-f' filename command argument is undefined!";
	public final static String MSG_TXT_WARN_UNRECOGNIZED_COMMAND = "Warning: Unrecognized command '%s' - command was ignored!";
	public final static String MSG_TXT_WARN_MISSING_PARAMETERS = "Warning: Command '%s' require these parameters: '%s' - command was ignored!";
	public final static String MSG_TXT_WARN_UNRECOGNIZED_PARAMETERS = "Warning: Command '%s' unrecognize parameter value: '%s' - command was ignored!";
	public final static String MSG_TXT_WARN_EMPTY_COMMAND_LINE_SKIPPED = "Warning: Empty command line was skipped!";
	public final static String MSG_TXT_ERROR_WEBPAGE_ISNULL = "Error: WebPage was not instanced!";
	
	
	// COMMANDS ...
	
	public final static String COMMAND_REMARK = "#".toUpperCase();
	public final static String COMMAND_CLEAR = "clear".toUpperCase();
	public final static String COMMAND_CLICK = "click".toUpperCase();
	public final static String COMMAND_FINDELEMENTBYXPATH = "findElementByxpath".toUpperCase();
	public final static String COMMAND_GET = "get".toUpperCase();
	public final static String COMMAND_LOADTABLEFROM = "loadTableFrom".toUpperCase();
	public final static String COMMAND_SELECTOPTIONBY = "selectOptionBy".toUpperCase();
	public final static String COMMAND_SENDKEYENTER = "sendKeyEnter".toUpperCase();
	public final static String COMMAND_SENDKEYS = "sendKeys".toUpperCase();
	public final static String COMMAND_SETEXPRESSIONTOVARIABLE = "setExpressionToVariable".toUpperCase();
	public final static String COMMAND_WAIT = "wait".toUpperCase();
	
	
	// TOKENS ...
	public final static String TOKEN_NUMBER   = "token#";
	public final static String TOKEN_NUMBER_1 = "token#1";
	public final static String TOKEN_NUMBER_2 = "token#2";
	public final static String TOKEN_NUMBER_3 = "token#3";
	public final static String TOKEN_NUMBER_4 = "token#4";
	

	// MISCELLANEOUS ...
	public final static String COMMAND_ARG_FILENAME_PREFIX_HTTP = "HTTP://";
	public final static String COMMAND_ARG_FILENAME_PREFIX_HTTPS = "HTTPS://";

}
