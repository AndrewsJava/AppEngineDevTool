package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.developertool.view.terminalactions.CommandLineExecutor;

import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.JTextField;

//Jun 26, 2015  12:23:56 PM 
public class GitTabBuilder {

	// Jun 26, 2015 12:24:04 PM
	public void buildGitTab(VertialScrollingTab git) {

		git.contents.removeAll();

		JTextField commitMessage = new JTextField();
		commitMessage.setText("DevTool commit: " + new Date());
		git.contents.add(commitMessage);
		JTextArea output = new JTextArea();
		git.contents.add(output);
		String gitStatus = getGitStatus();
		output.setText(gitStatus);

	}

	// Jun 26, 2015 12:33:52 PM
	private String getGitStatus() {

		String projectDirectoryPath = DevTool.getSingleton().model.gaewar.path.replace("/war", "");
		System.out.println("project directory: " + projectDirectoryPath);
		String gitStatusCommand = "git -C \"" + projectDirectoryPath + "\" status";

		System.out.println("executing command: " + gitStatusCommand);
		return new CommandLineExecutor().getOutputFromCommandExec(gitStatusCommand);

	}
}
