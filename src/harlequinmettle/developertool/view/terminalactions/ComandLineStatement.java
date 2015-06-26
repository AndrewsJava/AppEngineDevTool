package harlequinmettle.developertool.view.terminalactions;

import java.util.ArrayList;

//Jun 26, 2015  12:36:36 PM 
public class ComandLineStatement {
	String command = "";
	ArrayList<String> commandArguments = new ArrayList<String>();

	public ComandLineStatement(String command) {
		this.command = command;
	}

	public void addArgument(String arg) {
		commandArguments.add(arg);
	}
}
