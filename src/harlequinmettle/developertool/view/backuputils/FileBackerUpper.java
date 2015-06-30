package harlequinmettle.developertool.view.backuputils;

import harlequinmettle.utils.filetools.FileTools;
import harlequinmettle.utils.filetools.SerializationTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class FileBackerUpper {

	int count = 0;
	String originsFilePath;
	String destinationsFilePath;
	private ArrayList<String> fileTypeFilter;
	private ArrayList<String> fileTypeBackupExclusions;
	TreeMap<String, Integer> counters = new TreeMap<String, Integer>();
	String serializationKeyForCounters = ".serialized_counter_for_file_history_versioning";
	private static final int startNumber = 1000000;

	public FileBackerUpper(String from, String to) {
		originsFilePath = from;
		destinationsFilePath = to;
		counters = SerializationTool.deserializeObject(counters.getClass(), serializationKeyForCounters);
		if (counters == null)
			counters = new TreeMap<String, Integer>();
	}

	public void mirrorNodeFiles() {
		mirrorNodeFiles(originsFilePath);
	}

	public void mirrorNodeFiles(TreeMap<String, Boolean> exclusions) {
		fileTypeBackupExclusions = new ArrayList<String>();
		for (Entry<String, Boolean> ent : exclusions.entrySet()) {
			if (ent.getValue())
				fileTypeBackupExclusions.add(ent.getKey());
		}
		mirrorNodeFiles(originsFilePath);
	}

	private void mirrorNodeFiles(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File original : list) {
			if (original.isDirectory()) {
				mirrorNodeFiles(original.getAbsolutePath());
			} else {
				boolean skipBackup = false;
				if (fileTypeBackupExclusions != null) {
					String fileName = original.getName();

					for (String fileEnding : fileTypeBackupExclusions) {
						if (fileName.endsWith(fileEnding)) {
							skipBackup = true;
							break;
						}
					}
				}

				if (skipBackup)
					continue;
				File current = new File(original.getAbsolutePath().replaceAll(originsFilePath, destinationsFilePath));

				if (isChanged(original, current)) {
					int iteration = startNumber;
					if (counters.containsKey(original.getAbsolutePath())) {
						System.out.println("INCREMENTING ITERATION NUMBER");
						iteration = counters.get(original.getAbsolutePath());
					}
					iteration++;
					counters.put(original.getAbsolutePath(), iteration);
					String historyVersion = "" + iteration;
					File newVersion = new File(original.getAbsolutePath().replaceAll(originsFilePath, destinationsFilePath) + "_"
							+ (Long.MAX_VALUE - System.currentTimeMillis()) + "_" + historyVersion);

					try {
						FileUtils.copyFile(original, current);
						FileUtils.copyFile(original, newVersion);
						SerializationTool.serializeObject(counters, serializationKeyForCounters);
						Thread.yield();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}
		}
	}

	private String checkBackupIteration(String workingFile) {
		if (!counters.containsKey(workingFile))
			counters.put(workingFile, startNumber);
		return "" + counters.get(workingFile);
	}

	private boolean isValidText(File original) {
		String textFromFile = FileTools.tryToReadFileToString(original, null);

		if (textFromFile == null)
			return false;

		if (!textFromFile.matches("^[\\u0000-\\u007F]*$")) {
			System.out.println("file of text DOES NOT matches ascii:  " + original.getAbsolutePath());
			return false;
		}
		return true;
	}

	// checks if there is no backup OR modifiedDate is different
	protected boolean isChanged(File og, File bkup) {
		// if (!isValidText(og))
		// return false;
		boolean hasChanged = (!bkup.exists() || og.lastModified() > bkup.lastModified() + 1000);

		if (hasChanged) {
			System.out.println("files changed so far: " + count++);
			System.out.println("        " + og);
			// System.out.println("        " +bkup);
			// System.out.println("        " );
		}
		return hasChanged;
	}

}
