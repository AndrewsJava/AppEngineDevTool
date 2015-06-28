package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.developertool.view.terminalactions.CommandLineExecutor;
import harlequinmettle.utils.filetools.FileTools;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
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
		commitMessage.setText("DevTool: " + new Date());
		commitMessage.addFocusListener(getTextTouchAddDateActionListener(commitMessage));
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

	// Jun 28, 2015 9:33:34 AM
	private FocusListener getTextTouchAddDateActionListener(final JTextField commitMessage) {
		return new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// Jun 28, 2015 9:37:37 AM

				commitMessage.setText("DevTool: " + new Date());
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// Jun 28, 2015 9:37:37 AM

			}

		};

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
				addUpdateToIndexHtml(commitMessage.getText());
			}
		};

	}

	// Jun 28, 2015 8:56:48 AM
	protected void addUpdateToIndexHtml(String text) {

		System.out.println("commit message adding to index.html: " + text);
		DevToolModel model = DevTool.getSingleton().model;
		String xmlfilepath = model.project.path + "war/index.html";
		String fileContents = FileTools.tryToReadFileToString(new File(xmlfilepath), null);
		System.out.println(" index.html: " + fileContents.length());
		if (fileContents == null)
			return;
		String[] parts = fileContents.split("<commit-message>");
		System.out.println(" index.html parts: " + parts.length);
		if (parts.length < 2)
			return;
		String newFileContents = parts[0] + "<commit-message><br>\n\n" + text + parts[1];
		FileTools.tryToWriteStringToFile(new File(xmlfilepath), newFileContents);
	}

	private String getOutputFromGitCommandExecutionForCurrentProject(String gitCommand) {

		String projectDirectoryPath = DevTool.getSingleton().model.project.path;
		System.out.println("project directory: " + projectDirectoryPath);
		String gitStatusCommand = "git -C \"" + projectDirectoryPath + "\" ";
		gitStatusCommand += gitCommand;

		System.out.println("executing command: " + gitStatusCommand);
		return new CommandLineExecutor().getOutputFromCommandExec(gitStatusCommand);

	}

	// Jun 26, 2015 12:33:52 PM
	private String getGitStatus() {

		String projectDirectoryPath = DevTool.getSingleton().model.project.path;
		System.out.println("project directory: " + projectDirectoryPath);
		String gitStatusCommand = "git -C \"" + projectDirectoryPath + "\" status";

		System.out.println("executing command: " + gitStatusCommand);
		return new CommandLineExecutor().getOutputFromCommandExec(gitStatusCommand);

	}
}
