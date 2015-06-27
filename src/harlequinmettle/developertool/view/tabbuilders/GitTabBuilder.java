package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.developertool.view.terminalactions.CommandLineExecutor;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Jun 26, 2015  12:23:56 PM 
public class GitTabBuilder {

	// Jun 26, 2015 12:24:04 PM
	public void buildGitTab(VertialScrollingTab git) {
		int fontSize = 15;
		git.contents.removeAll();
		JPanel sizeAdjusted = new JPanel();
		JPanel commitPanel = new JPanel();
		commitPanel.setLayout(new FlowLayout());
		sizeAdjusted.setLayout(new BorderLayout());
		JButtonWithEnterKeyAction addCommitButton = new JButtonWithEnterKeyAction("add/commit");
		git.contents.add(sizeAdjusted);
		JTextField commitMessage = new JTextField();
		commitMessage.setFont(new Font("Arial", Font.PLAIN, fontSize));
		commitMessage.setText("DevTool commit: " + new Date());
		JTextArea output = new JTextArea();
		addCommitButton.addActionListener(getAddCommitActionListener(output, commitMessage));
		output.setFont(new Font("Arial", Font.PLAIN, fontSize));
		String gitStatus = getGitStatus();
		output.setText(gitStatus);

		commitPanel.add(commitMessage);
		commitPanel.add(addCommitButton);
		sizeAdjusted.add(commitPanel, BorderLayout.NORTH);
		sizeAdjusted.add(output, BorderLayout.CENTER);

	}

	// Jun 27, 2015 11:03:57 AM
	private ActionListener getAddCommitActionListener(final JTextArea output, final JTextField commitMessage) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// String gittest =
				// "git -C \"/home/andrew/DEVELOPER/CODE/InvestmentAdviserEngine\" add --a";
				// String gittest =
				// "git -C \"/home/andrew/DEVELOPER/CODE/InvestmentAdviserEngine\" commit -a -m \"test commit\"";
				String gitStatus = getOutputFromGitCommandExecutionForCurrentProject("add --a");
				gitStatus += getOutputFromGitCommandExecutionForCurrentProject("commit -a -m \"" + commitMessage.getText() + "\"");
				gitStatus += getGitStatus();
				output.setText(gitStatus);
			}
		};

	}

	private String getOutputFromGitCommandExecutionForCurrentProject(String gitCommand) {

		String projectDirectoryPath = DevTool.getSingleton().model.gaewar.path.replace("/war", "");
		System.out.println("project directory: " + projectDirectoryPath);
		String gitStatusCommand = "git -C \"" + projectDirectoryPath + "\" ";
		gitStatusCommand += gitCommand;

		System.out.println("executing command: " + gitStatusCommand);
		return new CommandLineExecutor().getOutputFromCommandExec(gitStatusCommand);

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
