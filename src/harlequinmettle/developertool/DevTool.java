package harlequinmettle.developertool;

import harlequinmettle.developertool.controller.DevToolController;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;

//Jun 20, 2015  11:15:37 AM 
public class DevTool {
	DevToolModel model = DevToolModel.getSavedModelState();

	public DevTool() {
		init();
	}

	// Jun 20, 2015 11:16:58 AM
	private void init() {
		if (model == null)
			model = new DevToolModel();
		DevToolView view = new DevToolView();
		DevToolController controller = new DevToolController(model, view);
		controller.showApp();
	}

	// Jun 20, 2015 11:15:37 AM
	public static void main(String[] args) {
		DevTool tool = new DevTool();
	}
}
