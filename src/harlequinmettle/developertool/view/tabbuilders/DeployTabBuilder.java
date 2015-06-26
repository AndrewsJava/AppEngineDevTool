package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.PathButtonData;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.developertool.view.terminalactions.GAEProjectDeployer;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

//Jun 26, 2015  10:31:34 AM 
public class DeployTabBuilder {

	private static final ArrayList<JCheckBox> multipeDeployments = new ArrayList<JCheckBox>();

	// Jun 23, 2015 12:26:40 PM
	public void buildCheckboxesForEachAppID(PathButtonData dataForButton) {
		multipeDeployments.clear();
		if (dataForButton.actionIndicator == PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID)
			for (String id : dataForButton.path.split(" ")) {
				JCheckBox checkbox = new JCheckBox(id);

				// if (DevTool.getSingleton().model.savedDeployPreferences ==
				// null)
				// DevTool.getSingleton().model.savedDeployPreferences = new
				// HashMap<String, Boolean>();
				if (DevTool.getSingleton().model.savedDeployPreferences.containsKey(id)
						&& DevTool.getSingleton().model.savedDeployPreferences.get(id))
					checkbox.setSelected(true);
				checkbox.addActionListener(getSaveDeployPreferencesListener());
				multipeDeployments.add(checkbox);
			}
	}

	// Jun 26, 2015 11:58:12 AM
	private static ActionListener getSaveDeployPreferencesListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox checkbox = (JCheckBox) arg0.getSource();
				boolean isChecked = checkbox.isSelected();
				String appId = checkbox.getActionCommand();
				DevTool.getSingleton().model.savedDeployPreferences.put(appId, isChecked);
				DevTool.getSingleton().saveModelState();
			}
		};

	}

	// Jun 23, 2015 12:19:48 PM
	public void buildDeployTab(VertialScrollingTab deploy) {

		buildCheckboxesForEachAppID(DevTool.getSingleton().model.addids);
		JButtonWithEnterKeyAction deployGAEProjectButton = new JButtonWithEnterKeyAction("deploy");
		deployGAEProjectButton.addActionListener(deployAction);
		deploy.contents.removeAll();
		for (JCheckBox app : multipeDeployments)
			deploy.contents.add(app);
		deploy.contents.add(deployGAEProjectButton);
	}

	public ActionListener deployAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (JCheckBox app : multipeDeployments)
				if (app.isSelected())
					new GAEProjectDeployer().deployGAEProject(app.getText());
		}
	};

}
