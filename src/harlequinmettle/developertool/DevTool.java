package harlequinmettle.developertool;

import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;
import harlequinmettle.utils.filetools.SerializationTool;

import java.util.TreeSet;

//Jun 20, 2015  11:15:37 AM 
public class DevTool {

	private static final String modelSerializationPath = ".devtoolmodel";
	public TreeSet<String> projects = SerializationTool.deserializeObject(TreeSet.class, modelSerializationPath);

	private static DevTool singleton;
	public DevToolModel model;
	public DevToolView view;
	public String currentProjectTitle = "undefined";

	public static DevTool getSingleton() {
		return singleton;
	}

	public DevTool() {
		singleton = this;
		init();
	}

	// Jun 20, 2015 11:16:58 AM
	private void init() {
		view = new DevToolView();
		if (projects == null)
			projects = new TreeSet<String>();
		if (projects.size() > 0) {
			setCurrentModel(projects.pollFirst());
		} else {
			updateModel();

		}
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

	// Jun 23, 2015 1:45:15 PM
	public void setView(DevToolView view) {
		this.view = view;

	}

	// Jun 23, 2015 2:37:25 PM
	public void setCurrentModel(String projectName) {
		currentProjectTitle = projectName;
		projects.add(projectName);

		SerializationTool.serializeObject(projects, modelSerializationPath);

		updateModel();
	}

	public void updateModel() {
		// Jun 24, 2015 1:06:16 PM
		model = getSavedModelState();
		if (model == null)
			model = new DevToolModel();
		if (model == null)
			model = new DevToolModel();
		view.setTitle(model.UITitle);
		view.showUI();
	}

}
