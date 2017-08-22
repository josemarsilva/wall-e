package org.gnu.walle;

import org.apache.commons.cli.ParseException;

import org.gnu.walle.cli.CliArgsParser;
import org.gnu.walle.script.ScriptParser;

/**
 * Hello world!
 *
 */
public class Main 
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
