package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.backuputils.BackupDefinitionModel;
import harlequinmettle.developertool.view.backuputils.BackupUtilityThread;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;

//Jun 30, 2015  8:16:19 AM 
public class BackupTabBuilder {
	BackupDefinitionModel backupData = getDefaultBackupDataModel();
	BackupUtilityThread backupThread = new BackupUtilityThread(backupData);

	// Jun 30, 2015 8:16:27 AM
	public void buildBackupTab(VertialScrollingTab backup) {
		backup.contents.removeAll();
		String id = "backup files thread";
		JCheckBox checkbox = new JCheckBox(id);

		if (DevTool.getSingleton().model.savedDeployPreferences.containsKey(id) && DevTool.getSingleton().model.savedDeployPreferences.get(id)) {
			checkbox.setSelected(true);
			backupThread.start();
		}
		checkbox.addActionListener(getBackupThreadRunningCheckboxActionListener());

		JButtonWithEnterKeyAction removeOldBackups = new JButtonWithEnterKeyAction("clear older backups");
		removeOldBackups.addActionListener(getClearOldBackupsButtonActionListener());

		backup.contents.add(checkbox);
		backup.contents.add(removeOldBackups);
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
				if (isChecked)
					backupThread.start();
				else
					backupThread.stopThread();
			}
		};

	}
}
