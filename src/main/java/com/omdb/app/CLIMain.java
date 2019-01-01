package com.omdb.app;

import org.apache.commons.cli.*;

import com.omdb.command.Command;
import com.omdb.command.MovieRatingCommand;
import com.omdb.command.processor.CommandProcessor;

public class CLIMain {
	
	public static void main(String args[]) {

		Options options = new Options();

        Option input = new Option("m", "input", true, "");
        input.setRequired(true);
        options.addOption(input);
   
        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);
            
        } catch (ParseException e) {
        	
            System.out.println(e.getMessage());
            formatter.printHelp("MovieReviews", options);
            System.exit(1);
        }

        String inputmovie = cmd.getOptionValue("input");
        String[] cmdArgs=new String[1];
        cmdArgs[0]=inputmovie;
        
		CommandProcessor cp=new CommandProcessor();
        Command command=new MovieRatingCommand(cmdArgs);
        cp.setCommand(command);
        cp.processRequest();
	}

}
