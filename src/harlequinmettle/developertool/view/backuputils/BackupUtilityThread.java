package harlequinmettle.developertool.view.backuputils;

public class BackupUtilityThread extends Thread {

	BackupDefinitionModel modelData;
	public boolean quit = false;

	public BackupUtilityThread(BackupDefinitionModel modelData) {
		this.modelData = modelData;
	}

	public BackupUtilityThread() {
		this.modelData = new BackupDefinitionModel();
		this.modelData.iterations = 1;
	}

	@Override
	public void run() {
		System.out.println("interval for thread: " + modelData.interval);
		for (int i = 0; i < modelData.iterations; i++) {
			if (quit)
				break;
			mapFiles();

			try {
				System.out.println("interval for thread: " + modelData.interval);
				Thread.sleep(modelData.interval * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void mapFiles() {
		System.out.println("mapping files");
		modelData.mapFiles();
	}

	// Jun 30, 2015 8:59:55 AM
	public void stopThread() {
		quit = true;

	}

	// Jun 30, 2015 9:08:04 AM
	public void clearOldBackups() {
		modelData.clearDestinationDirectory();

	}

}
