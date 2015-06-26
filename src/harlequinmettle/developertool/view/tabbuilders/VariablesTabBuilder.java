package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.PathButtonData;
import harlequinmettle.developertool.view.components.PathButton;
import harlequinmettle.developertool.view.components.VertialScrollingTab;

import java.util.ArrayList;

//Jun 26, 2015  10:24:35 AM 
public class VariablesTabBuilder {

	public final static ArrayList<PathButton> variableSettingButtons = new ArrayList<PathButton>();

	// Jun 22, 2015 10:43:27 AM
	public void buildVariablesTab(VertialScrollingTab variables) {
		updateGaeDeploymentButtonsComponents();
		variables.contents.removeAll();

		for (PathButton pathbutton : variableSettingButtons)
			variables.contents.add(pathbutton);

	}

	public void addPathButtonFromData(PathButtonData dataForButton) {
		variableSettingButtons.add(new PathButton(dataForButton));
	}

	// Jun 24, 2015 12:32:59 PM
	public void updateGaeDeploymentButtonsComponents() {
		variableSettingButtons.clear();
		addPathButtonFromData(DevTool.getSingleton().model.appcfg);
		addPathButtonFromData(DevTool.getSingleton().model.gaewar);
		addPathButtonFromData(DevTool.getSingleton().model.addids);

	}

}
