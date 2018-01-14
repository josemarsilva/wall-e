package org.gnu.automation.walle.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/*
 * CliArgsParser class is responsible for extract, compile and check consistency
 * for command line arguments passed as parameters in command line
 * 
 * @author josemarsilva
 * 
 */
public class CliArgsParser {
	
	// Properties ...
	
	private CommandLine cmdLine;
	private Options options = new Options();
    private String scriptfile = new String("");


    /*
     * CliArgsParser(args) - Constructor ...
     */
	public CliArgsParser( String[] args ) {
		
		// Options ...
        Option scriptfileOption = Option.builder("f")
        		.longOpt("scriptfile") 
        		.required(true) 
        		.desc("script file to be executed")
        		.hasArg()
        		.build();
        Option helpOption = Option.builder("h") 
        		.longOpt("help") 
        		.required(false) 
        		.desc("shows usage help message") 
        		.build(); 
        Option helpCommand = Option.builder("H") 
        		.longOpt("help-command") 
        		.required(false) 
        		.desc("shows script language sintax help" ) 
        		.build(); 
        Option p1Option = Option.builder("1")
                .longOpt("args#1") 
                .required(false) 
                .desc("parameter argument #1 - use args[1] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p2Option = Option.builder("2")
                .longOpt("args#2") 
                .required(false) 
                .desc("parameter #2 - use use args[2] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p3Option = Option.builder("3")
                .longOpt("args#3") 
                .required(false) 
                .desc("parameter #3 - use use args[3] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p4Option = Option.builder("4")
                .longOpt("args#4") 
                .required(false) 
                .desc("parameter #4 - use use args[4] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p5Option = Option.builder("5")
                .longOpt("args#5") 
                .required(false) 
                .desc("parameter #5 - use use args[5] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p6Option = Option.builder("6")
                .longOpt("args#6") 
                .required(false) 
                .desc("parameter #6 - use use args[6] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p7Option = Option.builder("7")
                .longOpt("args#7") 
                .required(false) 
                .desc("parameter #7 - use use args[7] to reference inside script") 
        		.hasArg()
                .build(); 
        Option p8Option = Option.builder("8")
                .longOpt("args#8") 
                .required(false) 
                .desc("parameter #8 - use use args[8] to reference inside script") 
        		.hasArg()
                .build(); 
        
        options.addOption(scriptfileOption);
        options.addOption(helpOption); 
        options.addOption(helpCommand); 
        options.addOption(p1Option); 
        options.addOption(p2Option); 
        options.addOption(p3Option); 
        options.addOption(p4Option); 
        options.addOption(p5Option); 
        options.addOption(p6Option); 
        options.addOption(p7Option); 
        options.addOption(p8Option); 
        
        CommandLineParser parser = new DefaultParser();
		try {
			cmdLine = parser.parse(options, args);
			
	        if (cmdLine.hasOption("help")) { 
	            HelpFormatter formatter = new HelpFormatter();
	            formatter.printHelp(org.gnu.automation.walle.cli.CliArgsParserConstants.APPLICATION, options);
	            System.exit(0);
	        } else if (cmdLine.hasOption("help-command")) {
		            System.out.println(org.gnu.automation.walle.cli.CliArgsParserConstants.LIST_OF_COMMANDS);
		            System.exit(0);
	        } else {
	        	this.setScriptfile( cmdLine.getOptionValue("scriptfile", "") ); 
	            System.out.println(org.gnu.automation.walle.cli.CliArgsParserConstants.WALL_E_ASCII);
	            System.out.println(org.gnu.automation.walle.cli.CliArgsParserConstants.WALL_E_SHORT_HISTORY);
	        }
			
		} catch (ParseException e) {
//			e.printStackTrace();
			System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter(); 
            formatter.printHelp(org.gnu.automation.walle.cli.CliArgsParserConstants.APPLICATION, options); 
			System.exit(-1);
		} 
        
	}
	
	/*
	 * setScriptfile(scriptfile) - Setter ...
	 */
	public void setScriptfile(String scriptfile) {
		this.scriptfile = scriptfile;
	}


	/*
	 * getScriptfile() - Setter ...
	 */
	public String getScriptfile() {
		return scriptfile;
	}
	
	
	/*
	 * getArgsParamAt(index) - Get args parameter at index position ...
	 */
	public String getArgsParamAt(int index) {
		String argsParamAt = cmdLine.getOptionValue("args#"+index, "");
		return argsParamAt;
		
	}





}
