package org.gnu.automation.walle.scriptLanguage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.gnu.automation.walle.cli.CliArgsParser;
import org.gnu.automation.walle.pageObjects.WebPage;
import org.gnu.automation.walle.scriptLanguage.symbols.SymbolTable;
import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;

/*
 * ScriptParser class is responsible for read script file, parse each command
 * line and build Script Commands Stack
 */
public class ScriptParser {
	
	/**
	 * parsePass1(br) - Parse BufferReader into TableOf commands ...
	 * @throws Exception 
	 */
	public TableOf parsePass1(String scriptFilename) throws Exception {
		TableOf tableOfCommands = new TableOf();
		// LoadFile ...
		System.out.println("Loading from file '" + scriptFilename + "' ...");
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(scriptFilename);
			br = new BufferedReader(fr);
			// Parsing
			System.out.println("Parsing Pass #1: Parse Program commands and parameters ...");
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				parseCommand(sCurrentLine, tableOfCommands);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return tableOfCommands;
	}
	
	
	/**
	 * parseCommand() - parse read row into command structure {[token#1, value#1], [token#2, value#2], .., [token#n, value#n]}  
	 * @throws Exception 
	 */
	public void parseCommand(String sCommandLine, TableOf tableOfCommands) throws Exception {
		RecordOf recordOfCommandTokens = new RecordOf(); 
		
		// ReplaceAll \t [tabs] for " " and "  " for " " 
		String sCmdLinePrepared = new String( sCommandLine );
		while (sCmdLinePrepared.contains("\t") || sCmdLinePrepared.contains("  ")) {
			sCmdLinePrepared = new String( sCmdLinePrepared.replaceAll("\t", " ").replaceAll("  ", " ") );
		}
		
		// Replace RightTrim() and LeftTrim()
		sCmdLinePrepared = new String( sCmdLinePrepared.replaceAll("\\s+$", "") ) ;
		sCmdLinePrepared = new String( sCmdLinePrepared.replaceAll("^\\s+", "") ) ;
		
		// Replace spaces outside opening-and-closing quotes (") for \t [TAB]
		boolean bOpenedQuotes = false;
		for (int i=0; i<sCmdLinePrepared.length(); i++) {
			if (bOpenedQuotes) { // OpendQuotes = TRUE
				if (sCmdLinePrepared.charAt(i) == '"') {
					bOpenedQuotes = false;
				}
			} else { // OpendQuotes = FALSE
				// Replace space for tab outside quotes
				if (sCmdLinePrepared.charAt(i) == ' ') {
					sCmdLinePrepared = new String(sCmdLinePrepared.substring(0,i)+'\t'+sCmdLinePrepared.substring(i+1) );
				}
				// Check open quotes
				if (sCmdLinePrepared.charAt(i) == '"') {
					bOpenedQuotes = true;
				}
			}
		}
		
		// Tokenize words with \t [TAB] separator
		String[] splitedCmdLine = sCmdLinePrepared.split("\t");
		for (int i=0;i<splitedCmdLine.length;i++) {
			if (splitedCmdLine[i].length() > 0) {
				if (splitedCmdLine[i].charAt(0) == '"' && splitedCmdLine[i].charAt(splitedCmdLine[i].length()-1) == '"') {
					recordOfCommandTokens.set(ScriptLanguageConstants.TOKEN_NUMBER+(i+1), splitedCmdLine[i].substring(1, splitedCmdLine[i].length()-1));
				} else {					
					recordOfCommandTokens.set(ScriptLanguageConstants.TOKEN_NUMBER+(i+1), splitedCmdLine[i]);
				}					
			}
		}
		
		// add command to tableOfCommands ...
		tableOfCommands.add(recordOfCommandTokens);
		
	}
	
	
	/*
	 * parsePass2( tableOfCommands, symbolTable ) - Build symbolTable with commands ...
	 */
	public void parsePass2(SymbolTable symbolTable, CliArgsParser cliArgsParser, TableOf tableOfCommands) {
		System.out.println("Parsing Pass #2: Build Program Symbol Table ...");
		
		// Build args[i] Symbol Table ...
		buildArgsSymbolTable(symbolTable, cliArgsParser);
		
		// Build args[i] Symbol Table ...
		buildInternalSymbolTable(symbolTable);
				
	}
	
	
	/*
	 * buildArgsSymbolTable() - Build args Symbol Table
	 */
	private void buildArgsSymbolTable(SymbolTable symbolTable, CliArgsParser cliArgsParser) {
		for (int i=1;i<=8;i++) {
			String argsParam = cliArgsParser.getArgsParamAt(i);
			if (argsParam!=null) {
				if (!argsParam.equals("")) {
					RecordOf symbolRecordOf = new RecordOf();
					String symbolName = new String(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_NAME_CLIARGSPARAM_PREFIX + "[" + i + "]"); 
					symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_SYMBOLTYPE, org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_TYPE_CLIARGSPARAM);
					symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_VALUE, argsParam);
					symbolTable.add(symbolName, symbolRecordOf);
				}
			}
		}
	}

	
	/*
	 * buildInternalSymbolTable(symbolTable) - Build internal Symbol Table 
	 */
	private void buildInternalSymbolTable(SymbolTable symbolTable) {
		RecordOf symbolRecordOf = new RecordOf();
		String symbolName = new String(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_NAME_LASTWEBELEMENTFOUND); 
		symbolRecordOf.set(org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_ATTRIBUTE_SYMBOLTYPE, org.gnu.automation.walle.scriptLanguage.symbols.SymbolsConstants.SYMBOL_TYPE_LASTWEBELEMENTFOUND);
		symbolTable.add(symbolName, symbolRecordOf);
	}

	
}
