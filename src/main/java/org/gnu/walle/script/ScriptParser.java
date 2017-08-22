package org.gnu.walle.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.gnu.walle.pageObjects.WebPage;
import org.gnu.walle.util.RecordOf;
import org.gnu.walle.util.TableOf;


public class ScriptParser {
	
	// private constants ...	
	private final String MSG_TXT_WARN_UNRECOGNIZED_COMMAND = new String("Warning: Unrecognized command '%s' was ignored!");
	private final String MSG_TXT_WARN_MISSING_PARAMETERS = new String("Warning: Command '%s' require these parameters: '%s'");
	
	// private properties ...
	private TableOf tableOfCommands = new TableOf();
	private WebPage webPage = new WebPage();
	private Map<String, String> localVariables = new HashMap<String, String>();;
	
	
	/**
	 * parseFile() - 
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
	 * parseCommand() - 
	 * @throws Exception 
	 */
	public void parseCommand(String sCommandLine) throws Exception {
		RecordOf recordOfCommandTokens = new RecordOf(); 
		int tokenNumber = 0;
		int tokenBeginIndex = 0;
		int tokenEndIndex = 0;
		boolean isSpaces = true;
		boolean isToken = false;
		boolean isDoubleQuotes = false;
		for (int positionIndex = 0; positionIndex<sCommandLine.length(); positionIndex++) {
			if (isSpaces) { // Still spaces?
				// Is *NOT* empty space ...
				if (sCommandLine.charAt(positionIndex) != ' ' && 
					sCommandLine.charAt(positionIndex) != '\t' ) {
					isSpaces = false;
					isToken = true;
					tokenBeginIndex = positionIndex;
					tokenNumber++;
					if (sCommandLine.charAt(positionIndex) == '"') {
						isDoubleQuotes = true;
					}
				}
			} else { // *NOT* still empty space ...
				// Is empty space ...
				if ((sCommandLine.charAt(positionIndex) == ' ' || sCommandLine.charAt(positionIndex) == '\t')) {
					if (isDoubleQuotes){
						recordOfCommandTokens.set("token#"+tokenNumber, sCommandLine.substring(tokenBeginIndex+1, positionIndex-1));
						
					} else {
						recordOfCommandTokens.set("token#"+tokenNumber, sCommandLine.substring(tokenBeginIndex, positionIndex));
						
					}
					isSpaces = true;
					isToken = false;
					tokenBeginIndex = positionIndex;
					isDoubleQuotes = false;
				}
				
			}
		}
		if (isToken) {
			if (isDoubleQuotes){
				recordOfCommandTokens.set("token#"+tokenNumber, sCommandLine.substring(tokenBeginIndex+1, sCommandLine.length()-1));

			} else {
				recordOfCommandTokens.set("token#"+tokenNumber, sCommandLine.substring(tokenBeginIndex, sCommandLine.length()));
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
			System.out.print("\rRow: "+i+" ...");
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
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("exportTableAsCsv".toUpperCase()) ) { 
					executeExportTableAsCsv(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("storeTextInto".toUpperCase()) ) { 
					executeStoreTextInto(recordOfCommandScript);
					
				} else if ( recordOfCommandScript.get("token#1").toUpperCase().equals("restoreTextFrom".toUpperCase()) ) { 
					executeRestoreTextFrom(recordOfCommandScript);
					
				} else { 
					// Warning: Unrecognized command will be skipped
					System.out.println("\n" + MSG_TXT_WARN_UNRECOGNIZED_COMMAND.replaceAll("%s", recordOfCommandScript.get("token#1")));
				}
			}
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
		// TODO Auto-generated method stub
		
	}
	
	
}
