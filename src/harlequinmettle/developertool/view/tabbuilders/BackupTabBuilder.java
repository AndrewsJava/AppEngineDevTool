package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.backuputils.BackupDefinitionModel;
import harlequinmettle.developertool.view.backuputils.BackupUtilityThread;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.investmentadviserengine.util.SystemTool;
import harlequinmettle.investmentadviserengine.util.TimeDateTool;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JCheckBox;

//Jun 30, 2015  8:16:19 AM 
public class BackupTabBuilder {
	BackupDefinitionModel backupData = getDefaultBackupDataModel();
	BackupUtilityThread backupThread = new BackupUtilityThread(backupData);
	Thread gitBackupThread = null;

	public AtomicBoolean stopRequested = new AtomicBoolean(true);

	// Jun 30, 2015 8:16:27 AM
	public void buildBackupTab(VertialScrollingTab backup) {
		backup.contents.removeAll();
		addFileCopyBackupThreadCheckbox(backup);
		addGitAddCommitThreadCheckbox(backup);
		addClearOldBackupsButton(backup);

	}

	private void addGitAddCommitThreadCheckbox(VertialScrollingTab backup) {
		String id = "GIT add/commit backup thread";
		JCheckBox checkbox = new JCheckBox(id);

		if (DevTool.getSingleton().model.savedDeployPreferences.containsKey(id) && DevTool.getSingleton().model.savedDeployPreferences.get(id)) {
			checkbox.setSelected(true);
			startGitBackupThread();
		}
		checkbox.addActionListener(getBackupThreadRunningCheckboxActionListener());

		backup.contents.add(checkbox);

	}

	private void startGitBackupThread() {

		stopRequested.set(true);
		try {
			gitBackupThread.join();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		stopRequested.set(false);
		gitBackupThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// Oct 17, 2015 10:34:49 AM
				long time = System.currentTimeMillis();
				gitCommitLoop();
				System.out.println(TimeDateTool.timeSince(time));
			}

		});
		gitBackupThread.start();

	}

	// Nov 7, 2015 7:31:56 AM
	private void gitCommitLoop() {
		int commitCounter = 100000;
		while (true) {

			if (stopRequested.get())
				break;

			SystemTool.takeABreak(10000);
			String commitMessage = "" + commitCounter++ + "       " + new Date().toString();

			String addAllGit = GitTabBuilder.getOutputFromGitCommandExecutionForCurrentProject("add --a");
			String commitGit = GitTabBuilder.getOutputFromGitCommandExecutionForCurrentProject("commit -a -m \"" + commitMessage + "\"");
			String gitStatus = "";
			if (commitCounter % 10 == 9)
				gitStatus = GitTabBuilder.getOutputFromGitCommandExecutionForCurrentProject("push origin master");
			System.out.println(addAllGit);
			System.out.println(commitGit);
			System.out.println(gitStatus);
		}
	}

	// Nov 7, 2015 7:23:54 AM
	private void addClearOldBackupsButton(VertialScrollingTab backup) {
		JButtonWithEnterKeyAction removeOldBackups = new JButtonWithEnterKeyAction("clear older backups");
		removeOldBackups.addActionListener(getClearOldBackupsButtonActionListener());

		backup.contents.add(removeOldBackups);
	}

	// Nov 7, 2015 7:22:57 AM
	private void addFileCopyBackupThreadCheckbox(VertialScrollingTab backup) {
		String id = "backup files thread";
		JCheckBox checkbox = new JCheckBox(id);

		if (DevTool.getSingleton().model.savedDeployPreferences.containsKey(id) && DevTool.getSingleton().model.savedDeployPreferences.get(id)) {
			checkbox.setSelected(true);
			backupThread.start();
		}
		checkbox.addActionListener(getBackupThreadRunningCheckboxActionListener());

		backup.contents.add(checkbox);
	}

	// Jun 30, 2015 11:45:03 AM
	private BackupDefinitionModel getDefaultBackupDataModel() {
		BackupDefinitionModel defaultModel = new BackupDefinitionModel();
		defaultModel.interval = 8;// seconds
		defaultModel.origins.put(DevTool.getSingleton().model.project.path, true);
		defaultModel.destinations
				.put(".backup_files_archives" + File.separatorChar + DevTool.getSingleton().model.UITitle.replaceAll(" ", "_"), true);
		return defaultModel;
	}

	private ActionListener getClearOldBackupsButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				backupThread.clearOldBackups();
			}
		};

	}

	private ActionListener getBackupThreadRunningCheckboxActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox checkbox = (JCheckBox) arg0.getSource();
				boolean isChecked = checkbox.isSelected();
				String appId = checkbox.getActionCommand();
				DevTool.getSingleton().model.savedDeployPreferences.put(appId, isChecked);
				DevTool.getSingleton().saveModelState();
				if (isChecked) {
					backupThread = new BackupUtilityThread(backupData);
					backupThread.start();
				} else
					backupThread.stopThread();
			}
		};

	}
}
