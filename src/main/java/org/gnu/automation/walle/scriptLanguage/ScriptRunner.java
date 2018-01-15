package org.gnu.automation.walle.scriptLanguage;

import org.gnu.automation.walle.cli.CliArgsParser;
import org.gnu.automation.walle.dataSources.WorkbookSheetTable;
import org.gnu.automation.walle.pageObjects.WebPage;
import org.gnu.automation.walle.scriptLanguage.expressions.ExpressionEvaluator;
import org.gnu.automation.walle.scriptLanguage.symbols.SymbolTable;
import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;

/*
 * ScriptRunner class is responsible for execute each command from Script 
 * Commands Stack
 */
public class ScriptRunner {
	
	// Properties ...
	private CliArgsParser cliArgsParser;
	private ScriptParser scriptParser;
	private TableOf tableOfCommands = new TableOf();
	private SymbolTable symbolTable = new SymbolTable();
	private WebPage webPage = new WebPage();
	private ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(symbolTable, webPage); 


	/*
	 * ScriptRunner( args ) - Constructor command line arguments
	 */
	public ScriptRunner(String[] args) {
		
		// Instance command line arguments ...
		this.cliArgsParser = new CliArgsParser(args);
		
		// Instance new ScriptParser ...
		this.scriptParser = new ScriptParser();
		
	}
	
	
	/*
	 * run() - load, parse and execute script ...
	 */
	public void run() throws Exception {
		
    	// Load ...
		if (this.cliArgsParser.getScriptfile()==null) {
			throw new Exception(org.gnu.automation.walle.scriptLanguage.ScriptLanguageConstants.MSG_TXT_ERROR_COMMAND_ARG_FILENAME_UNDEFINED);
		} else {
			if (this.cliArgsParser.getScriptfile().equals("")) {
				throw new Exception(org.gnu.automation.walle.scriptLanguage.ScriptLanguageConstants.MSG_TXT_ERROR_COMMAND_ARG_FILENAME_UNDEFINED);
			}
		}
		
		
		// Parse Pass #1 ...
		tableOfCommands = this.scriptParser.parsePass1(this.cliArgsParser.getScriptfile());
		
		
		// Parse Pass #2 ...
		scriptParser.parsePass2(symbolTable, cliArgsParser, tableOfCommands);

		
    	// Execute ...
		execute();
		
		// Finish ...
		finish();
		
	}
	
	
	
	private void finish() {
		System.out.println("\n\nBye !\n");
		
	}


	/*
	 * loadFromFilename() - returns bufferReader with scripts reading from filename ...
	 */
	private void loadFromHttpHttps(String urlFileSpecification) {
		System.out.println("Download from url '" + urlFileSpecification + "' ..."); // TODO FIXME
	}
	
	
	/**
	 * execute() - Execute tableOfCommands 
	 * @throws Exception 
	 */
	public void execute() throws Exception {
		
		System.out.println("Executing ...");
		
		// for each command of script ...
		for (int i=0; i<tableOfCommands.size(); i++) {
			RecordOf recordOfCommandScript = tableOfCommands.get(i);
			System.out.print("\rRow: "+(i+1)+" ...");
			if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1) != null) {
				
				// Each command of script language ...
				
				if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_REMARK) ) {
					executeRemark();
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_GET) ) {
					executeGet(recordOfCommandScript);

				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_WAIT) ) { 
					executeWait(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_FINDELEMENTBYXPATH) ) { 
					executeFindElementByxpath(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SENDKEYS) ) { 
					executeSendKeys(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SENDKEYENTER) ) { 
					executeSendKeyEnter(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_CLEAR) ) { 
					executeClear(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_CLICK) ) { 
					executeClick(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_LOADTABLEFROM) ) { 
					executeLoadTableFrom(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SELECTOPTIONBY) ) { 
					executeSelectOptionBy(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SETEXPRESSIONTOVARIABLE) ) { 
					executeSetExpressionToVariable(recordOfCommandScript);
					
				} else { 
					// Warning: Unrecognized command will be skipped
					System.out.println("\n" + ScriptLanguageConstants.MSG_TXT_WARN_UNRECOGNIZED_COMMAND.replaceAll("%s", recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
				}
			} else {
				// Warning: Empty command line skipped
//				System.out.println("\n" + ScriptLanguageConstants.MSG_TXT_WARN_EMPTY_COMMAND_LINE_SKIPPED);
			}
		}
		
	}
	
	/*
	 * executeSelectOptionBy( recordOfCommand )
	 */
	private void executeSelectOptionBy(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3) != null) {
				if ( recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).toUpperCase().equals("INDEX")
						|| recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).toUpperCase().equals("VALUE") 
						|| recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).toUpperCase().equals("VISIABLETEXT") ) {
					webPage.selectOptionBy(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2), recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3));				
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_UNRECOGNIZED_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) ));				
				}			
			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "[<Index>|\"Value\"]"));				
			}			
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).replaceFirst("%s", "[Index|Value]"));			
		}

	}


	/*
	 * executeRemark() - Remarks does nothing
	 */
	private void executeRemark() throws Exception {
		// never was so easy ...
	}

	
	/*
	 * executeGet( recordOfCommand ) - Execute WebDriver get( url )
	 */
	private void executeGet(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "<url>"));
			} else {
				webPage.get(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2));
			}
		}
	}
	
	
	/*
	 * executeWait( recordOfCommand ) - WebDriver wait ( milliseconds )
	 */
	private void executeWait(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "<milliseconds>"));
			} else {
				webPage.wait(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2));
			}
		}
	}
	

	/*
	 * executeFindElementByxpath( xpath ) - Find element <xpath>
	 */
	private void executeFindElementByxpath(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "<xPath>"));
			} else {
				webPage.findElementByxpath(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2));
			}
		}
	}
	
	
	private String evaluate(String expression) throws Exception {
		return this.expressionEvaluator.evaluate(expression);
	}
	
	
	private void executeSendKeys(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "<keys>"));
			} else {
				webPage.sendKeys(evaluate(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)));
			}
		}
	}
	
	
	private void executeSendKeyEnter(RecordOf recordOfCommand) throws Exception {
		webPage.sendKeyEnter();
	}
	
	
	private void executeClear(RecordOf recordOfCommand) throws Exception {
				webPage.clear();
	}
	
	
	private void executeClick(RecordOf recordOfCommand) throws Exception {
		webPage.click();
	}
	
	
	private void executeLoadTableFrom(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3) != null ) {
					if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3).equals("") ) {
						if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4) != null ) {
							if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4).equals("") ) {
								WorkbookSheetTable workbookSheetTable = new WorkbookSheetTable(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4));
								TableOf tableOf = workbookSheetTable.getTableOfWorkbookSheetTable();
								symbolTable.add(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2), tableOf);
							} else {
								// Warning: Missing parameters command will be skipped
								System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "dataSource"));
							}
						} else {
							// Warning: Missing parameters command will be skipped
							System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "dataSource"));
						}
					} else {
						// Warning: Missing parameters command will be skipped
						System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "dataSourceType"));
					}
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "dataSourceType"));
				}
			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "tableName"));
			}
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "tableName"));
		}
	}
	
	
	/*
	 * executeSetExpressionToVariable( expression, variable ) - Set value evaluated by expression into variable ...
	 */
	private void executeSetExpressionToVariable(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null) {
			if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("")) {
				if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3) != null) {
					if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3).equals("")) {
						RecordOf symbolRecordOf = new RecordOf();
						String symbolName = new String(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)); 
						symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_SYMBOLTYPE, org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_TYPE_VARIABLE);
						symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_VALUE, evaluate(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)));
						symbolTable.add(symbolName, symbolRecordOf);						
						
					} else {
						// Warning: Missing parameters command will be skipped
						System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "<variable>"));			
					}			
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3)).replaceFirst("%s", "<variable>"));			
				}			
			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).replaceFirst("%s", "<expression>"));			
			}			
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).replaceFirst("%s", "<expression>"));			
		}
	}
	

}
	
