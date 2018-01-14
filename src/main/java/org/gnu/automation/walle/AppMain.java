package org.gnu.automation.walle;

import org.gnu.automation.walle.cli.CliArgsParser;
import org.gnu.automation.walle.scriptLanguage.ScriptParser;
import org.gnu.automation.walle.scriptLanguage.ScriptRunner;

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
    	
    	// Instance new scriptRunner ...
    	ScriptRunner scriptRunner = new ScriptRunner(args);
    	
    	// Run script  ...
    	scriptRunner.run();

    }
}
