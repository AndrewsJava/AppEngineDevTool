package harlequinmettle.developertool.view.terminalactions;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

//Jun 26, 2015  10:58:05 AM 
public class CommandLineExecutor {

	public void doIt() {
		try {
			String scriptContent = "#!/bin/bash \n echo \"yeah toast!\" > /tmp/toast.txt";
			Writer output = new BufferedWriter(new FileWriter("/tmp/toast.sh"));
			output.write(scriptContent);
			output.close();
			Runtime.getRuntime().exec("chmod u+x /tmp/toast.sh");
		} catch (IOException ex) {
		}
	}

	public void executeCommandWithArguments(ComandLineStatement... commands) {
		DefaultExecutor executor = new DefaultExecutor();
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(out, out);
		CommandLine cmdLine = new CommandLine(commands[0].command);
		for (String arg : commands[0].commandArguments)
			cmdLine.addArgument(arg);
		try {
			executor.execute(cmdLine, resultHandler);
			resultHandler.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String res = out.toString();
		System.out.println(res);
	}

	public String executeCommandWithArguments(String command, String... args) {
		DefaultExecutor executor = new DefaultExecutor();
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(out, out);
		CommandLine cmdLine = new CommandLine(command);
		executor.setStreamHandler(streamHandler);
		for (String arg : args)
			cmdLine.addArgument(arg);
		try {
			executor.execute(cmdLine, resultHandler);
			resultHandler.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String res = out.toString();
		System.out.println(res);
		return res;
	}

	public String getOutputFromCommandExec(String command) {
		DefaultExecutor exec = new DefaultExecutor();
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		CommandLine commandline = CommandLine.parse(command);
		exec.setStreamHandler(streamHandler);
		exec.setExitValue(139);
		try {
			exec.execute(commandline, resultHandler);
			resultHandler.waitFor();
		} catch (Exception e) {
		}
		return (outputStream.toString());
	}

	public String getOutputOfCommandLine(String command) {
		DefaultExecutor executor = new DefaultExecutor();
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(out, out);
		CommandLine cmdLine = new CommandLine(command);
		executor.setStreamHandler(streamHandler);

		try {
			executor.execute(cmdLine, resultHandler);
			resultHandler.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String res = out.toString();
		return (res);
	}
}
