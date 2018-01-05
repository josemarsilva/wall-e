package org.gnu.automation.walle.scriptCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.gnu.automation.walle.pageObjects.WebPage;
import org.gnu.automation.walle.util.RecordOf;
import org.gnu.automation.walle.util.TableOf;


public class ScriptParser {
	
	// private constants ...	
	private final String MSG_TXT_WARN_UNRECOGNIZED_COMMAND = new String("Warning: Unrecognized command '%s' was ignored!");
	private final String MSG_TXT_WARN_MISSING_PARAMETERS = new String("Warning: Command '%s' require these parameters: '%s'");
	private final String MSG_TXT_WARN_UNRECOGNIZED_PARAMETERS = new String("Warning: Command '%s' unrecognize parameter value: '%s' - command was ignored!");
	private final String MSG_TXT_WARN_EMPTY_COMMAND_LINE_SKIPPED = new String("Warning: Empty command line was skipped!");
	private final String MSG_TXT_ERROR_WEBPAGE_ISNULL = new String("Error: WebPage was not instanced!");
	
	// private properties ...
	private TableOf tableOfCommands = new TableOf();
	private WebPage webPage = new WebPage();
	private Map<String, String> localVariables = new HashMap<String, String>();;
	
	
	/**
	 * parseFile() - read script file with commands
	 * @throws Exception 
	 */
	public void parseFile(String scriptFilename) throws Exception {
		System.out.println("Loading '" + scriptFilename + "' ...");
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(scriptFilename);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				parseCommand(sCurrentLine);
			}
		} catch (IOException e) {
//			e.printStackTrace();
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
	}
	
	
	/**
	 * parseCommand() - parse read row into command structure {[token#1, value#1], [token#2, value#2], .., [token#n, value#n]}  
	 * @throws Exception 
	 */
	public void parseCommand(String sCommandLine) throws Exception {
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
					recordOfCommandTokens.set("token#"+(i+1), splitedCmdLine[i].substring(1, splitedCmdLine[i].length()-1));
				} else {					
					recordOfCommandTokens.set("token#"+(i+1), splitedCmdLine[i]);
				}					
			}
		}
		
		//
		tableOfCommands.add(recordOfCommandTokens);
		
	}
	
	
	/**
	 * executeTableOfCommands() - Execute tableOfCommands 
	 * @throws Exception 
	 */
	public void executeTableOfCommands() throws Exception {
		
		System.out.println("Executing ...");
		
		// for each command of script ...
		for (int i=0; i<tableOfCommands.size(); i++) {
			RecordOf recordOfCommandScript = tableOfCommands.get(i);
			System.out.print("\rRow: "+(i+1)+" ...");
			if ( recordOfCommandScript.get("token#1") != null) {
				if ( recordOfCommandScript.get("token#1").toUpperCase().equals("get".toUpperCase()) ) {
					executeGet(recordOfCommandScript);

				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("wait".toUpperCase()) ) { 
					executeWait(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("findElementByxpath".toUpperCase()) ) { 
					executeFindElementByxpath(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("sendKeys".toUpperCase()) ) { 
					executeSendKeys(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("clear".toUpperCase()) ) { 
					executeClear(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("click".toUpperCase()) ) { 
					executeClick(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("submit".toUpperCase()) ) { 
					executeSubmit(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("exportTableAsCsv".toUpperCase()) ) { 
					executeExportTableAsCsv(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("storeTextInto".toUpperCase()) ) { 
					executeStoreTextInto(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("restoreTextFrom".toUpperCase()) ) { 
					executeRestoreTextFrom(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("selectOptionBy".toUpperCase()) ) { 
					executeSelectOptionBy(recordOfCommandScript);
					
				} else { 
					// Warning: Unrecognized command will be skipped
					System.out.println("\n" + MSG_TXT_WARN_UNRECOGNIZED_COMMAND.replaceAll("%s", recordOfCommandScript.get("token#1")));
				}
			} else {
				// Warning: Empty command line skipped
				System.out.println("\n" + MSG_TXT_WARN_EMPTY_COMMAND_LINE_SKIPPED);
			}
		}
	}
	
	/*
	 * executeSelectOptionBy( recordOfCommand )
	 */
	private void executeSelectOptionBy(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null) {
			if (recordOfCommand.get("token#3") != null) {
				if ( recordOfCommand.get("token#2").toUpperCase().equals("INDEX")
						|| recordOfCommand.get("token#2").toUpperCase().equals("VALUE") 
						|| recordOfCommand.get("token#2").toUpperCase().equals("VISIABLETEXT") ) {
					webPage.selectOptionBy(recordOfCommand.get("token#2"), recordOfCommand.get("token#3"));				
				} else {
					// Warning: Missing parameters command will be skipped
					System.out.println(MSG_TXT_WARN_UNRECOGNIZED_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", recordOfCommand.get("token#2") ));				
				}			
			} else {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#3")).replaceFirst("%s", "[<Index>|\"Value\"]"));				
			}			
		} else {
			// Warning: Missing parameters command will be skipped
			System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#2")).replaceFirst("%s", "[Index|Value]"));			
		}

	}


	/*
	 * executeGet( recordOfCommand ) - Execute WebDriver get( url )
	 */
	private void executeGet(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<url>"));
			} else {
				webPage.get(recordOfCommand.get("token#2"));
			}
		}
	}
	
	
	/*
	 * executeWait( recordOfCommand ) - WebDriver wait ( milliseconds )
	 */
	private void executeWait(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null ) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<milliseconds>"));
			} else {
				webPage.wait(recordOfCommand.get("token#2"));
			}
		}
	}
	

	/*
	 * executeFindElementByxpath( xpath ) - Find element <xpath>
	 */
	private void executeFindElementByxpath(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null ) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<xPath>"));
			} else {
				webPage.findElementByxpath(recordOfCommand.get("token#2"));
			}
		}
	}
	
	
	private void executeSendKeys(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null ) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<keys>"));
			} else {
				webPage.sendKeys(recordOfCommand.get("token#2"));
			}
		}
	}
	
	
	private void executeClear(RecordOf recordOfCommand) throws Exception {
				webPage.clear();
	}
	
	
	private void executeClick(RecordOf recordOfCommand) throws Exception {
		webPage.click();
	}
	
	
	private void executeSubmit(RecordOf recordOfCommand) throws Exception {
		webPage.submit();
	}
	
	
	private void executeStoreTextInto(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null ) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<local-variable>"));
			} else {
				localVariables.put(recordOfCommand.get("token#2").toUpperCase(), webPage.getText());
			}
		}
	}
	
	
	private void executeRestoreTextFrom(RecordOf recordOfCommand) throws Exception {
		if (recordOfCommand.get("token#2") != null ) {
			if (recordOfCommand.get("token#2").equals("") ) {
				// Warning: Missing parameters command will be skipped
				System.out.println(MSG_TXT_WARN_MISSING_PARAMETERS.replaceFirst("%s", recordOfCommand.get("token#1")).replaceFirst("%s", "<local-variable>"));
			} else {
				webPage.setText(localVariables.get(recordOfCommand.get("token#2").toUpperCase()));
			}
		}
		
	}
	
	
	private void executeExportTableAsCsv(RecordOf recordOfCommand) {
		if ( webPage != null) {
			System.out.println(webPage.getCurrentWebElement().getClass() );
			
		} else {
			// Error: Missing parameters command will be skipped
			System.out.println(MSG_TXT_ERROR_WEBPAGE_ISNULL);
			System.exit(-1);
		}
		
	}
	
	
}
