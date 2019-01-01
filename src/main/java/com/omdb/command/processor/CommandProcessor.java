package com.omdb.command.processor;

import com.omdb.command.Command;

public class CommandProcessor {
	
	private Command command;
	
	public void setCommand(Command command) {
		this.command=command;
	}
	
	public void processRequest() {
		
		command.execute();
		
	}

}
