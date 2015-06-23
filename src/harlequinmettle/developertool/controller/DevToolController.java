package harlequinmettle.developertool.controller;

import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;

//Jun 20, 2015  11:20:50 AM 
public class DevToolController {
	DevToolModel model;
	DevToolView view;

	public DevToolController() {
	}

	public void showApp() {
		if (model != null) {
			view.setTitle(model.UITitle);
			view.addPathButtonFromData(model.appcfg);
			view.addPathButtonFromData(model.gaewar);
			view.addPathButtonFromData(model.addids);
			view.setSize(model.WIDTH * 0.5, model.HEIGHT * 0.8);
		} else {

			view.setSize(400, 300);
		}
		view.showUI();
	}

	// Jun 23, 2015 1:44:47 PM
	public void setModel(DevToolModel model) {
		this.model = model;

	}

	// Jun 23, 2015 1:45:15 PM
	public void setView(DevToolView view) {
		this.view = view;

	}
}
