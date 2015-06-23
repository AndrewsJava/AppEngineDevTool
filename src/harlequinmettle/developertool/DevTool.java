package harlequinmettle.developertool;

import harlequinmettle.developertool.controller.DevToolController;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;
import harlequinmettle.utils.filetools.SerializationTool;

//Jun 20, 2015  11:15:37 AM 
public class DevTool {

	private static final String modelSerializationPath = ".devtoolmodel";

	private static DevTool singleton;

	public DevToolModel model;

	public static DevTool getSingleton() {
		return singleton;
	}

	public DevTool() {
		if (singleton == null)
			singleton = this;
		model = getSingleton().getSavedModelState();
		if (model == null)
			model = new DevToolModel();
		init();
	}

	// Jun 20, 2015 11:16:58 AM
	private void init() {

		DevToolView view = new DevToolView();
		DevToolController controller = new DevToolController(model, view);
		controller.showApp();
	}

	// Jun 20, 2015 11:15:37 AM
	public static void main(String[] args) {
		DevTool tool = new DevTool();
	}

	public void saveModelState() {
		System.out.println("saving state: " + model);
		SerializationTool.serializeObject(model, modelSerializationPath);
	}

	public static DevToolModel getSavedModelState() {
		return SerializationTool.deserializeObject(DevToolModel.class, modelSerializationPath);
	}
}
