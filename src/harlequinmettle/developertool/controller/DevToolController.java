package harlequinmettle.developertool.controller;

import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;

//Jun 20, 2015  11:20:50 AM 
public class DevToolController {
	DevToolModel model;
	DevToolView view;

	public DevToolController(DevToolModel model, DevToolView view) {
		this.model = model;
		this.view = view;
	}

	public void showApp() {
		view.setTitle(model.UITitle);
		view.addPathButtonFromData(model.appcfg);
		view.addPathButtonFromData(model.gaewar);
		view.addPathButtonFromData(model.addids);
		view.showUI();
		view.setSize(model.WIDTH * 0.5, model.HEIGHT * 0.8);
	}
}
