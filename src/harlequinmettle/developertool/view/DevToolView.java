package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.PathButtonData;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;
import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//Jun 20, 2015  11:18:18 AM 
public class DevToolView {

	JFrame UI;
	JTabbedPane tabs;
	static final String projectsTabTitle = "Projects";
	static final String variablesTabTitle = "Variables";
	static final String deployTabTitle = "Deploy App";
	static final String[] tabTitles = { projectsTabTitle, variablesTabTitle, deployTabTitle };
	HashMap<String, VertialScrollingTab> tabTitleMap = new HashMap<String, VertialScrollingTab>();
	public ArrayList<PathButton> pathButtons = new ArrayList<PathButton>();
	ArrayList<JCheckBox> multipeDeployments = new ArrayList<JCheckBox>();

	JButtonWithEnterKeyAction deployGAEProjectButton;

	public final int WIDTH = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	public final int HEIGHT = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public DevToolView() {
		for (String tabTitle : tabTitles) {
			tabTitleMap.put(tabTitle, new VertialScrollingTab(tabTitle));
		}
		deployGAEProjectButton = new JButtonWithEnterKeyAction("deploy");
		deployGAEProjectButton.addActionListener(deployAction);

		UI = new JFrame();
		tabs = new JTabbedPane();
		UI.add(tabs);
		UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UI.setVisible(true);
		setSize(WIDTH * 0.5, HEIGHT * 0.8);
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

			}
		});
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
			}

		});
	}

	// Jun 24, 2015 12:32:59 PM
	public void updateGaeDeploymentButtonsComponents() {
		pathButtons.clear();
		addPathButtonFromData(DevTool.getSingleton().model.appcfg);
		addPathButtonFromData(DevTool.getSingleton().model.gaewar);
		addPathButtonFromData(DevTool.getSingleton().model.addids);

	}

	// Jun 22, 2015 10:29:32 AM
	protected void buildUITabs() {
		tabs.removeAll();
		updateGaeDeploymentButtonsComponents();
		buildProjectsTab();
		buildVariablesTab();
		buildDeployTab();
		for (Entry<String, VertialScrollingTab> ent : tabTitleMap.entrySet())
			tabs.add(ent.getKey(), ent.getValue().scroller);
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
	private void buildDeployTab() {
		VertialScrollingTab deploy = tabTitleMap.get(deployTabTitle);
		deploy.contents.removeAll();
		for (JCheckBox app : multipeDeployments)
			deploy.contents.add(app);
		deploy.contents.add(deployGAEProjectButton);
	}

	// Jun 22, 2015 10:43:27 AM
	private void buildVariablesTab() {

		VertialScrollingTab variables = tabTitleMap.get(variablesTabTitle);
		variables.contents.removeAll();

		for (PathButton pathbutton : pathButtons)
			variables.contents.add(pathbutton);

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

	// Jun 23, 2015 1:47:32 PM
	public void buildProjectsTab() {
		VertialScrollingTab projects = tabTitleMap.get(projectsTabTitle);
		projects.contents.removeAll();
		JTextArea input = new JTextArea();
		input.setText("project name");

		JButtonWithEnterKeyAction addProjectButton = new JButtonWithEnterKeyAction("add new project");

		projects.contents.add(input);
		projects.contents.add(addProjectButton);

		ButtonGroup bg = new ButtonGroup();
		projects.contents.add(addProjectButton);
		for (String project : DevTool.getSingleton().projects) {
			ProjectRadioButton projectRadioButton = new ProjectRadioButton(project);
			if (project.equals(DevTool.getSingleton().currentProjectTitle))
				projectRadioButton.setSelected(true);
			projects.contents.add(projectRadioButton);
			bg.add(projectRadioButton);
		}
		addProjectButton.addActionListener(getAddProjectActionListener(input, projects.contents, bg, projects.scroller));

	}

	// Jun 23, 2015 2:16:54 PM
	private ActionListener getAddProjectActionListener(final JTextArea input, final VerticalJPanel variablesTabContents, final ButtonGroup bg,
			final PreferredJScrollPane projectSelectorScroller) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String projectAddName = input.getText();
				if (DevTool.getSingleton().projects.contains(projectAddName))
					return;
				DevTool.getSingleton().projects.add(projectAddName);
				DevTool.getSingleton().setCurrentModel(projectAddName);
				ProjectRadioButton projectRadioButton = new ProjectRadioButton(projectAddName);
				variablesTabContents.add(projectRadioButton);
				bg.add(projectRadioButton);
				projectRadioButton.setSelected(true);

				variablesTabContents.validate();
				variablesTabContents.repaint();
			}
		};

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
		showUI();
	}

	public void updateIDTabs() {
		// Jun 24, 2015 1:10:03 PM
		updateGaeDeploymentButtonsComponents();
		buildDeployTab();
		tabTitleMap.get(deployTabTitle).revalidateRepaint();
	}
}
