package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.backuputils.BackupUtilityThread;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

//Jun 30, 2015  8:16:19 AM 
public class BackupTabBuilder {
	BackupUtilityThread backupThread = new BackupUtilityThread();

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

		JButtonWithEnterKeyAction deployGAEProjectButton = new JButtonWithEnterKeyAction("clear older backups");
		deployGAEProjectButton.addActionListener(getClearOldBackupsButtonActionListener());

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
