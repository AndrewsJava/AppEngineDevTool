package harlequinmettle.developertool;

import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;
import harlequinmettle.utils.filetools.FileTools;
import harlequinmettle.utils.filetools.SerializationTool;

import java.io.File;
import java.util.ArrayList;

//Jun 20, 2015  11:15:37 AM 
public class DevTool {

	private static final String modelSerializationPath = ".devtoolmodel";
	// public TreeSet<String> projects =
	// SerializationTool.deserializeObject(TreeSet.class,
	// modelSerializationPath);
	public ArrayList<String> projects = new ArrayList<String>(FileTools.tryToReadFileToCollection(new File(modelSerializationPath)));

	private static DevTool singleton;
	public DevToolModel model;
	public DevToolView view;
	public String currentProjectTitle = getLastSelectedProjectTitleFromMemory();

	public static DevTool getSingleton() {
		return singleton;
	}

	// Jul 2, 2015 10:58:53 AM
	private String getLastSelectedProjectTitleFromMemory() {
		return FileTools.tryToReadFileToString(new File(modelSerializationPath + "_last_project"), "");

	}

	public void storeSelectedProjectTitleToMemory(String projectSelected) {
		FileTools.tryToWriteStringToFile(new File(modelSerializationPath + "_last_project"), projectSelected);

	}

	public DevTool() {
		singleton = this;
		init();
	}

	// Jun 20, 2015 11:16:58 AM
	private void init() {
		view = new DevToolView();
		if (currentProjectTitle == null)
			currentProjectTitle = "no project saved";
		if (projects == null)
			projects = new ArrayList<String>();
		if (projects.size() > 0) {
			if (projects.contains(currentProjectTitle))
				setCurrentModel(currentProjectTitle);
			else
				setCurrentModel(projects.get(0));
		} else {
			updateModel();

		}
	}

	// Jun 20, 2015 11:15:37 AM
	public static void main(String[] args) {
		DevTool tool = new DevTool();
	}

	public void saveModelState() {
		SerializationTool.serializeObject(model, modelSerializationPath + "_" + currentProjectTitle);
	}

	public DevToolModel getSavedModelState() {
		return SerializationTool.deserializeObject(DevToolModel.class, modelSerializationPath + "_" + currentProjectTitle);
	}

	// Jun 23, 2015 1:45:15 PM
	public void setView(DevToolView view) {
		this.view = view;

	}

	// Jun 23, 2015 2:37:25 PM
	public void setCurrentModel(String projectName) {
		currentProjectTitle = projectName;

		projects.add(projectName);
		FileTools.tryToWriteCollectionToFileLines(new File(modelSerializationPath), projects);

		updateModel();
	}

	public void updateModel() {
		// Jun 24, 2015 1:06:16 PM
		model = getSavedModelState();
		if (model == null)
			model = new DevToolModel();
		if (model == null)
			model = new DevToolModel();
		model.UITitle = currentProjectTitle;
		view.UI.setTitle(model.UITitle);
		view.showUI();
	}

}
