package harlequinmettle.developertool.view;

import harlequinmettle.developertool.model.PathButtonData;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;
import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

//Jun 20, 2015  11:18:18 AM 
public class DevToolView {

	JFrame UI = new JFrame();
	public PreferredJScrollPane projectsTab;
	JTabbedPane tabs = new JTabbedPane();
	ArrayList<PathButton> pathButtons = new ArrayList<PathButton>();
	ArrayList<JCheckBox> multipeDeployments = new ArrayList<JCheckBox>();

	JButtonWithEnterKeyAction deployGAEProjectButton = new JButtonWithEnterKeyAction("deploy");

	public DevToolView(PreferredJScrollPane buildProjectsTab) {
		this.projectsTab = buildProjectsTab;
	}

	public void addPathButtonFromData(PathButtonData dataForButton) {
		pathButtons.add(new PathButton(dataForButton));
		if (dataForButton.actionIndicator == PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID)
			buildCheckboxesForEachAppID(dataForButton);
	}

	private void buildCheckboxesForEachAppID(PathButtonData dataForButton) {
		// Jun 23, 2015 12:26:40 PM
		multipeDeployments.clear();
		if (dataForButton.actionIndicator == PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID)
			for (String id : dataForButton.path.split(" ")) {
				multipeDeployments.add(new JCheckBox(id));
			}
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
		tabs.removeAll();
		deployGAEProjectButton.addActionListener(deployAction);
		tabs.add("Projects", projectsTab);
		buildAddDataSettingsTab();
		buildAddDeployAppTab();
		buildAddBackupFilesTab();
		buildAddGitTab();
	}

	// Jun 23, 2015 1:21:06 PM
	private void buildAddGitTab() {
		// TODO: build git quick commit interface with index.xml commit message
		// with date/time
	}

	// Jun 23, 2015 1:21:06 PM
	private void buildAddBackupFilesTab() {
		// TODO: use bakcup app to manage threading file copy and backup
	}

	// Jun 23, 2015 12:19:48 PM
	private void buildAddDeployAppTab() {

		VerticalJPanel acitonsTabContents = new VerticalJPanel();
		for (JCheckBox app : multipeDeployments)
			acitonsTabContents.add(app);
		acitonsTabContents.add(deployGAEProjectButton);
		PreferredJScrollPane actionsTabScroller = new PreferredJScrollPane(acitonsTabContents);

		tabs.add("Deploy App", actionsTabScroller);
	}

	// Jun 22, 2015 10:43:27 AM
	private void buildAddDataSettingsTab() {

		VerticalJPanel variablesTabContents = new VerticalJPanel();
		for (PathButton pathbutton : pathButtons)
			variablesTabContents.add(pathbutton);
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

	public ActionListener deployAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (JCheckBox app : multipeDeployments)
				if (app.isSelected())
					new GAEProjectDeployer().deployGAEProject(app.getText());
		}
	};

	// Jun 22, 2015 11:36:48 AM
	public void refreshUI() {

	}
}
