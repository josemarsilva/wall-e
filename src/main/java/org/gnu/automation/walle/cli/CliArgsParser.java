package org.gnu.automation.walle.cli;

import org.apache.commons.cli.DefaultParser; 
import org.apache.commons.cli.CommandLine; 
import org.apache.commons.cli.CommandLineParser; 
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
	
	// private constants ...
	private final String APPLICATION = new String("wall-e [options]");
	private final String LIST_OF_COMMANDS = new String("List of script commands implemented:" + "\n"
			+ "* get \"<url>\"" + "\n"
			+ "* wait <milliseconds>" + "\n"
			+ "* findElementByxpath \"<xpath>\"" + "\n"
			+ "* sendKeys \"<keys>\"" + "\n"
			+ "* click" + "\n"
			+ "* saveTableAs \"<filename>\"" + "\n"
			);
	private final String WALL_E_ASCII = new String( ""
			+  "    _.----. .----._       " + "" + "\n"
			+ "   / ( O ) ' ( O ) \\      " + "" + "\n"
			+ "   \\ _ _.-^-._ _ /        " + "" + "\n"
			+  "    `-''''' \\''''`-'     " + "" + "\n"
			+  "   ______//_____          " + "WW  WW  WW  AAAAAAAA  LL       LL          EEEEEEE" + "\n"
			+  "  .----. :: == .----.     " + "WW  WW  WW  AA    AA  LL       LL          EE     " + "\n"
			+  "  []---| ...==|---[]      " + "WW  WW  WW  AAAAAAAA  LL       LL      ::  EEEEE  " + "\n"
			+  "  '----' _____ '----'     " + "WW  WW  WW  AA    AA  LL       LL          EE     " + "\n"
			+  " _____ | | | | | _____    " + "WWWWWWWWWW  AA    AA  LLLLLLL  LLLLLLL     EE     " + "\n"
			+  "|_-_-_|| | | | ||_-_-_|   " + "WWWWWWWWWW  AA    AA  LLLLLLL  LLLLLLL     EEEEEEE" + "\n"
			+  "|_-_-_|'-------'|_-_-_|  " + "\n"
			+  "|_-_-_|         |_-_-_|  " + "https://github.com/josemarsilva/wall-e" + "\n"
			);
	private final String WALL_E_SHORT_HISTORY = new String("WALL-E, abbreviation for Waste Allocation Load Lifter Earth-class, is the last " + "\n"
			                                             + "robot left on Earth. He spends his day packing the garbage on the  planet. But " + "\n"
			                                             + "for 700 years, WALL-E has developed a personality and is  more  than  a robot. " + "\n"
			                                             + "Upon seeing Eve, a mechanical probe on Earth mission,  he  falls  in  love and " + "\n"
			                                             + "resolves to follow her throughout the galaxy.\n\nLooking for Eve ...");

	
	// private properties ...
	private Options options = new Options();
    private String scriptfile = new String("");
    private String param1 = new String("");
    private String param2 = new String("");
    private String param3 = new String("");
    private String param4 = new String("");
    private String param5 = new String("");
    private String param6 = new String("");
    private String param7 = new String("");
    private String param8 = new String("");


	
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
                .longOpt("param#1") 
                .required(false) 
                .desc("parameter #1 - use %1% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p2Option = Option.builder("2")
                .longOpt("param#2") 
                .required(false) 
                .desc("parameter #2 - use %2% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p3Option = Option.builder("3")
                .longOpt("param#3") 
                .required(false) 
                .desc("parameter #3 - use %3% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p4Option = Option.builder("4")
                .longOpt("param#4") 
                .required(false) 
                .desc("parameter #4 - use %4% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p5Option = Option.builder("5")
                .longOpt("param#5") 
                .required(false) 
                .desc("parameter #5 - use %5% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p6Option = Option.builder("6")
                .longOpt("param#6") 
                .required(false) 
                .desc("parameter #6 - use %6% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p7Option = Option.builder("7")
                .longOpt("param#7") 
                .required(false) 
                .desc("parameter #7 - use %7% to reference inside script") 
        		.hasArg()
                .build(); 
        Option p8Option = Option.builder("8")
                .longOpt("param#8") 
                .required(false) 
                .desc("parameter #8 - use %8% to reference inside script") 
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
			CommandLine cmdLine = parser.parse(options, args);
			
	        if (cmdLine.hasOption("help")) { 
	            HelpFormatter formatter = new HelpFormatter(); 
	            formatter.printHelp(APPLICATION, options);
	            System.exit(0);
	        } else if (cmdLine.hasOption("help-command")) {
		            System.out.println(LIST_OF_COMMANDS);
		            System.exit(0);
	        } else {
	        	this.setScriptfile( cmdLine.getOptionValue("scriptfile", "") ); 
	        	this.setParam1( cmdLine.getOptionValue("param#1", "") ); 
	        	this.setParam2( cmdLine.getOptionValue("param#2", "") ); 
	        	this.setParam3( cmdLine.getOptionValue("param#3", "") ); 
	        	this.setParam4( cmdLine.getOptionValue("param#4", "") ); 
	        	this.setParam5( cmdLine.getOptionValue("param#5", "") ); 
	        	this.setParam6( cmdLine.getOptionValue("param#6", "") ); 
	        	this.setParam7( cmdLine.getOptionValue("param#7", "") ); 
	        	this.setParam8( cmdLine.getOptionValue("param#8", "") ); 
	            System.out.println(WALL_E_ASCII);
	            System.out.println(WALL_E_SHORT_HISTORY);
	        }
			
		} catch (ParseException e) {
//			e.printStackTrace();
			System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter(); 
            formatter.printHelp(APPLICATION, options); 
			System.exit(-1);
		} 
        
	}
	
	
	public void setScriptfile(String scriptfile) {
		this.scriptfile = scriptfile;
	}


	public String getScriptfile() {
		return scriptfile;
	}


	public void setParam1(String param) {
		this.param1 = param;
	}


	public String getParam1() {
		return param1;
	}
	
	
	public void setParam2(String param) {
		this.param2 = param;
	}


	public String getParam2() {
		return param2;
	}


	public void setParam3(String param) {
		this.param3 = param;
	}


	public String getParam3() {
		return param3;
	}


	public void setParam4(String param) {
		this.param4 = param;
	}


	public String getParam4() {
		return param4;
	}


	public void setParam5(String param) {
		this.param5 = param;
	}


	public String getParam5() {
		return param5;
	}


	public void setParam6(String param) {
		this.param6 = param;
	}


	public String getParam6() {
		return param6;
	}


	public void setParam7(String param) {
		this.param7 = param;
	}


	public String getParam7() {
		return param7;
	}


	public void setParam8(String param) {
		this.param8 = param;
	}


	public String getParam8() {
		return param8;
	}



}
