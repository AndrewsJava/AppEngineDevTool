package harlequinmettle.developertool.view;

import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;
import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

//Jun 20, 2015  11:18:18 AM 
public class DevToolView {
	JFrame UI = new JFrame();
	private String appcfgPathButtonTitle;
	private String appcfgPathButtonTooltip = "";
	private ActionListener appcfgPathDefineAction;
	JTabbedPane tabs = new JTabbedPane();
	JButtonWithEnterKeyAction appEngineDeployScriptPathFinderButton;

	public void setAppcfgButtonNameActionListenerAndToolTip(String name, ActionListener action, String tooltip) {
		appcfgPathButtonTitle = name;
		appcfgPathDefineAction = action;
		appcfgPathButtonTooltip = tooltip;
	}

	public void showUI() {
		updateOnUI(new Updateable() {
			// Jun 22, 2015 9:43:02 AM
			@Override
			public void updateGUI() {
				buildUITabs();
				UI.add(tabs);

				UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				UI.setVisible(true);
			}

		});
	}

	// Jun 22, 2015 10:29:32 AM
	protected void buildUITabs() {

		buildAddDataSettingsTab();
	}

	// Jun 22, 2015 10:43:27 AM
	private void buildAddDataSettingsTab() {
		if (appcfgPathButtonTitle == null || appcfgPathDefineAction == null)
			return;
		VerticalJPanel variablesTabContents = new VerticalJPanel();
		appEngineDeployScriptPathFinderButton = new JButtonWithEnterKeyAction(appcfgPathButtonTitle);
		// "set path to app engine deployment shell script: appcfg.sh"
		appEngineDeployScriptPathFinderButton.setToolTipText(appcfgPathButtonTooltip);
		appEngineDeployScriptPathFinderButton.addActionListener(appcfgPathDefineAction);
		variablesTabContents.add(appEngineDeployScriptPathFinderButton);
		PreferredJScrollPane variablesTabScroller = new PreferredJScrollPane(variablesTabContents);

		tabs.add("Variables", variablesTabScroller);
	}

	// Jun 22, 2015 9:13:18 AM
	public void setTitle(String title) {
		UI.setTitle(title);
	}

	// Jun 22, 2015 9:28:35 AM
	public void setSize(double width, double height) {
		UI.setSize((int) width, (int) height);
	}

	public void updateOnUI(final Updateable updateGUI) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				updateGUI.updateGUI();
			}
		});
	}

	// Jun 22, 2015 11:36:07 AM
	public void setAppcfgButtonName(String name) {
		appEngineDeployScriptPathFinderButton.setText(name);

	}

	// Jun 22, 2015 11:36:48 AM
	public void refreshUI() {

	}
}
