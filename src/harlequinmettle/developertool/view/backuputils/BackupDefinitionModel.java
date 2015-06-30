package harlequinmettle.developertool.view.backuputils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class BackupDefinitionModel implements Serializable {

	public TreeMap<String, Boolean> origins = new TreeMap<String, Boolean>();
	public TreeMap<String, Boolean> destinations = new TreeMap<String, Boolean>();

	TreeMap<String, Boolean> inclusions = new TreeMap<String, Boolean>();
	TreeMap<String, Boolean> exclusions = new TreeMap<String, Boolean>();

	String title = "untitled";
	public int interval = 0;
	int iterations = 60000;

	public BackupDefinitionModel() {

	}

	public String[] getInclusionList() {
		return inclusions.keySet().toArray(new String[inclusions.size()]);
	}

	public String[] getExclusionList() {
		return exclusions.keySet().toArray(new String[exclusions.size()]);
	}

	ArrayList<String> ignoreFileExtensions = new ArrayList<String>();

	public void mapFiles() {
		System.out.println("limiting file backup to: " + inclusions);
		for (Entry<String, Boolean> moveFrom : origins.entrySet()) {
			for (Entry<String, Boolean> moveTo : destinations.entrySet()) {
				if (moveFrom.getValue() && moveTo.getValue()) {
					FileBackerUpper fileCopier = new FileBackerUpper(moveFrom.getKey(), moveTo.getKey());
					fileCopier.mirrorNodeFiles(inclusions);
				}
			}
		}

	}

	public void clearDestinationDirectory() {
		for (Entry<String, Boolean> moveTo : destinations.entrySet())
			try {
				FileUtils.deleteDirectory(new File(moveTo.getKey()));
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
