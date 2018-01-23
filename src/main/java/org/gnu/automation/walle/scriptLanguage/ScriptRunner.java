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
	private TableOf programCommands = new TableOf();
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
		programCommands = this.scriptParser.parsePass1(this.cliArgsParser.getScriptfile());
		
		
		// Parse Pass #2 ...
		scriptParser.parsePass2(symbolTable, cliArgsParser, programCommands);

		
    	// Execute ...
		execute();
		
		// Finish ...
		finish();
		
	}
	
	
	/*
	 * finish() - Finish execution ...
	 */
	private void finish() {
		webPage.quit();
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
		
		int instructionPointer=0;
		while(instructionPointer<programCommands.size()) {
			
			RecordOf recordOfCommandScript = programCommands.get(instructionPointer);
			System.out.print("\rRow: "+(instructionPointer+1)+" ...");
			if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1) != null) {
				
				// Each command of script language ...
				
				if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_CLEAR) ) { 
					executeClear(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_CLICK) ) { 
					executeClick(recordOfCommandScript);
				
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_FINDELEMENTBYXPATH) ) { 
					executeFindElementByxpath(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_ENDFOREACH) ) { 
					instructionPointer = executeEndForEach(recordOfCommandScript, instructionPointer);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_FOREACH) ) { 
					instructionPointer = executeForEach(recordOfCommandScript, instructionPointer);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_GET) ) {
					executeGet(recordOfCommandScript);

				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_LOADTABLEFROM) ) { 
					executeLoadTableFrom(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_REMARK) ) {
					executeRemark();
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SELECTOPTIONBY) ) { 
					executeSelectOptionBy(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SENDKEYENTER) ) { 
					executeSendKeyEnter(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SENDKEYTAB) ) { 
					executeSendKeyTab(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_SENDKEYS) ) { 
					executeSendKeys(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get(ScriptLanguageConstants.TOKEN_NUMBER_1).toUpperCase().equals(ScriptLanguageConstants.COMMAND_WAIT) ) { 
					executeWait(recordOfCommandScript);
					
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
			
			// Next instruction pointer ...
			instructionPointer++;
			
		}
		
	}

	
	/*
	 * executeClear() - Clear web element ...
	 */
	private void executeClear(RecordOf recordOfCommand) throws Exception {
		webPage.clear();
	}
	
	
	/*
	 * executeEndForEach() - End ForEach flow control ...
	 */
	private int executeEndForEach(RecordOf recordOfCommand, int instructionPointer) throws Exception {
		
		// Default next command ...
		int newInstructionPointer = instructionPointer;
		
		if (scriptParser.getSymbolNameForEachEnd(instructionPointer)!=null) {
			
			if (symbolTable.containsKeyProgramAddress(scriptParser.getSymbolNameForEachEnd(instructionPointer))) {
				
				if (symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachEnd(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_BEGIN)!=null) {
					
					if (!symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachEnd(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_BEGIN).equals("")) {
						
						newInstructionPointer = Integer.parseInt(symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachEnd(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_BEGIN)) - 1; 

					} else {
						// Warning: Missing parameters command will be skipped
						System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", (scriptParser.getSymbolNameForEachEnd(instructionPointer))).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
					}
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", (scriptParser.getSymbolNameForEachEnd(instructionPointer))).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
				}

			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", (scriptParser.getSymbolNameForEachEnd(instructionPointer))).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
			}
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", (scriptParser.getSymbolNameForEachEnd(instructionPointer))).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
		}
				

		
		return newInstructionPointer;
	}
	
	
	/*
	 * executeForEach() - Begin ForEach flow control ...
	 */
	private int executeForEach(RecordOf recordOfCommand, int instructionPointer) throws Exception {

		// Default next command ...
		int newInstructionPointer = instructionPointer;
		
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				
				// Is table defined?
				if (symbolTable.containsKeyTableOf(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2))) {
					
					if (scriptParser.getSymbolNameForEachBegin(instructionPointer)!=null) {
						
						if (symbolTable.containsKeyProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer))) {
							
							if ( symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_END)!=null) {
								
								if (!symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_END).equals("")) {

									RecordOf forEachCommand = symbolTable.getRecordOfSymbolTableProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer));
									
									if (forEachCommand!=null) {
										
										// Define forEach command iterator if not already defined ...
										int iteratorIndex = 0;
										if (forEachCommand.get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_ITERATOR)==null) {
											forEachCommand.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_ITERATOR,Integer.toString(iteratorIndex));
										} else {
											iteratorIndex = Integer.valueOf( forEachCommand.get(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_ITERATOR) ) + 1;
										}
										
										// iteratorIndex < tableSize ?
										if ( iteratorIndex < symbolTable.getSymbolTableTableOf(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).size() ) {
											// Update iterator ...
											forEachCommand.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_ITERATOR,Integer.toString(iteratorIndex));
										} else {
											// Remove iterator ...
											forEachCommand.remove(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_ITERATOR);
											// Interrupt loop ...
											newInstructionPointer = Integer.parseInt(symbolTable.getAttributeSymbolTableProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer),org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_FOREACH_END)); 
										}
										symbolTable.putRecordOfSymbolTableProgramAddress(scriptParser.getSymbolNameForEachBegin(instructionPointer), forEachCommand);
										
									} else {
										// Warning: Missing parameters command will be skipped
										System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", (scriptParser.getSymbolNameForEachBegin(instructionPointer))+".RecordOf").replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
									}
									
								} else {
									// Warning: Missing parameters command will be skipped
									System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", scriptParser.getSymbolNameForEachBegin(instructionPointer)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
								}
								
							} else {
								// Warning: Missing parameters command will be skipped
								System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", scriptParser.getSymbolNameForEachBegin(instructionPointer)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
							}
							
						} else {
							// Warning: Missing parameters command will be skipped
							System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", scriptParser.getSymbolNameForEachBegin(instructionPointer)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
						}
						
					} else {
						// Warning: Missing parameters command will be skipped
						System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_SYMBOL_IS_UNDEFINED.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
					}
					
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(ScriptLanguageConstants.MSG_TXT_ERROR_PARAM_TABLE_IS_UNDEFINED.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)).replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)));
				}

			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "tableName"));
			}
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "tableName"));
		}
		
		// Return new Instruction Pointer ...
		return newInstructionPointer;

	}
	
	
	private void executeClick(RecordOf recordOfCommand) throws Exception {
		webPage.click();
	}
	
	
	/*
	 * executeFindElementByxpath( ) - Find element <xpath>
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
	
	
	/*
	 * executeGet( ) - Execute WebDriver get( url )
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
	 * executeLoadTableFrom(recordOfCommand) - 
	 */
	private void executeLoadTableFrom(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3) != null ) {
					if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_3).equals("") ) {
						if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4) != null ) {
							if (!recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4).equals("") ) {
								WorkbookSheetTable workbookSheetTable = new WorkbookSheetTable(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_4));
								TableOf tableOf = workbookSheetTable.getTableOfWorkbookSheetTable();
								symbolTable.addTable(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2), tableOf);
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
	 * executeRemark() - Remarks does nothing
	 */
	private void executeRemark() throws Exception {
		// never was so easy ...
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
	 * executeSendKeys() - Send keys to web element ...
	 */
	private void executeSendKeys(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2) != null ) {
			if (recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2).equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(ScriptLanguageConstants.MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_1)).replaceFirst("%s", "<keys>"));
			} else {
				webPage.sendKeys(symbolReplacement(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2)));
			}
		}
	}
	
	
	/*
	 * executeSendKeyEnter() - Send ENTER keys to web element ...
	 */
	private void executeSendKeyEnter(RecordOf recordOfCommand) throws Exception {
		webPage.sendKeyEnter();
	}
	
	
	/*
	 * executeSendKeyTab() - Send TAB keys to web element ...
	 */
	private void executeSendKeyTab(RecordOf recordOfCommand) throws Exception {
		webPage.sendKeyTab();
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
	 * symbolReplacement(expression) - Replace variable symbols for contents ...
	 */
	private String symbolReplacement(String expression) throws Exception {
		return this.expressionEvaluator.symbolReplacement(expression);
	}
	
	
	/*
	 * evalute(expression) - Evaluate expression ...
	 */
	private String evaluate(String expression) throws Exception {
		return this.expressionEvaluator.evaluate(expression);
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
						symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_VALUE, evaluate(symbolReplacement(recordOfCommand.get(ScriptLanguageConstants.TOKEN_NUMBER_2))));
						symbolTable.addVariable(symbolName, symbolRecordOf);						
						
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
	
