package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.utils.filetools.FileTools;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

//Jun 23, 2015  12:35:59 PM 
public class GAEProjectDeployer {

	// Jun 23, 2015 12:36:32 PM
	public void deployGAEProject(String id) {
		setAppIdInXMLFile(id);
		DevToolModel model = DevTool.getSingleton().model;
		String warpath = model.gaewar.path;
		String appcfg = model.appcfg.path;
		executeCommandWithArguments(appcfg, "update", warpath);

	}

	// Jun 23, 2015 12:37:22 PM
	private void setAppIdInXMLFile(String id) {
		DevToolModel model = DevTool.getSingleton().model;
		String xmlfilepath = model.gaewar.path + "/WEB-INF/appengine-web.xml";
		String fileContents = FileTools.tryToReadFileToString(new File(xmlfilepath), null);
		if (fileContents == null)
			return;
		String[] parts = fileContents.split("<application>.+?</application>");
		String newFileContents = parts[0] + "<application>" + id.trim() + "</application>" + parts[1];
		FileTools.tryToWriteStringToFile(new File(xmlfilepath), newFileContents);
	}

	public void executeCommandWithArguments(String command, String... args) {
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
	}
}
