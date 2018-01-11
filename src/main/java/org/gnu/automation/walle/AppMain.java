package org.gnu.automation.walle;

import org.gnu.automation.walle.cli.CliArgsParser;
import org.gnu.automation.walle.scriptLanguage.ScriptParser;

/*
 * AppMain class is main application point of starting
 * 
 * @author josemarsilva
 * 
 */
public class AppMain 
{
    public static void main( String[] args ) throws Exception
    {
    	// Instance new command line parser ...
    	CliArgsParser cliArgsParser = new CliArgsParser(args);
    	
    	// Instance new script parser ...
    	ScriptParser scriptParser = new ScriptParser();
    	
    	// Load file ...
    	if (!cliArgsParser.getScriptfile().equals("")) {
    		// Load file and parse tokens ...
    		scriptParser.parseFile(cliArgsParser.getScriptfile());
    		
    		// Compile and execute loaded tokens ...
    		scriptParser.executeTableOfCommands();
    		
    	}
    	
    }
}
