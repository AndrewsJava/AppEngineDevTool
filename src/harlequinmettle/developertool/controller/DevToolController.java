package harlequinmettle.developertool.controller;

import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.developertool.view.DevToolView;
import harlequinmettle.utils.filetools.ChooseFilePrompter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Jun 20, 2015  11:20:50 AM 
public class DevToolController {
	DevToolModel model;
	DevToolView view;

	// Jun 22, 2015 11:01:39 AM
	public ActionListener appcfgPathFinderButtonAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.appcfg.path = ChooseFilePrompter.filePathChooser();
			model.saveModelState();
			view.setAppcfgButtonName(model.appcfg.buttonTitle + " " + model.appcfg.path);
			view.refreshUI();
		}
	};

	public DevToolController(DevToolModel model, DevToolView view) {
		this.model = model;
		this.view = view;
	}

	public void showApp() {
		view.setTitle(model.UITitle);
		view.setAppcfgButtonNameActionListenerAndToolTip(model.appcfg.buttonTitle + " " + model.appcfg.path, appcfgPathFinderButtonAction,
				model.appcfg.tooltip);

		view.showUI();
		view.setSize(model.WIDTH * 0.5, model.HEIGHT * 0.8);
	}
}
