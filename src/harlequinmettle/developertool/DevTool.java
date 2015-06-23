package harlequinmettle.developertool;

import harlequinmettle.developertool.controller.DevToolController;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;
import harlequinmettle.utils.filetools.SerializationTool;

import java.util.TreeSet;

//Jun 20, 2015  11:15:37 AM 
public class DevTool {

	private static final String modelSerializationPath = ".devtoolmodel";
	TreeSet<String> projects = SerializationTool.deserializeObject(TreeSet.class, modelSerializationPath);

	private static DevTool singleton;
	DevToolController controller;
	public DevToolModel model;
	DevToolView view;
	public String currentProjectTitle = "undefined";

	public static DevTool getSingleton() {
		return singleton;
	}

	public DevTool() {
		if (singleton == null)
			singleton = this;
		if (projects == null)
			projects = new TreeSet<String>();
		getSingleton();
		if (projects.size() > 0) {
			currentProjectTitle = projects.pollFirst();
			model = DevTool.getSingleton().getSavedModelState();
		}
		// if (model == null)
		// model = new DevToolModel();
		init();
	}

	// Jun 20, 2015 11:16:58 AM
	private void init() {

		DevToolProjectSelector projectSelectorView = new DevToolProjectSelector();

		view = new DevToolView(projectSelectorView.buildProjectsTab());
		controller = new DevToolController();
		controller.setModel(model);
		controller.setView(view);
		controller.showApp();
	}

	// Jun 20, 2015 11:15:37 AM
	public static void main(String[] args) {
		DevTool tool = new DevTool();
	}

	public void saveModelState() {
		System.out.println("saving state: " + model);
		SerializationTool.serializeObject(model, modelSerializationPath + "_" + currentProjectTitle);
	}

	public DevToolModel getSavedModelState() {
		return SerializationTool.deserializeObject(DevToolModel.class, modelSerializationPath + "_" + currentProjectTitle);
	}

	// Jun 23, 2015 2:37:25 PM
	public void setCurrentModel(String projectName) {
		currentProjectTitle = projectName;
		projects.add(projectName);

		SerializationTool.serializeObject(projects, modelSerializationPath);
		model = getSavedModelState();
		controller.setModel(model);

		controller.showApp();
	}
}
