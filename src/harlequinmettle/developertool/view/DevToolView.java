package harlequinmettle.developertool.view;

import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.developertool.view.tabbuilders.DeployTabBuilder;
import harlequinmettle.developertool.view.tabbuilders.GitTabBuilder;
import harlequinmettle.developertool.view.tabbuilders.ProjectsTabBuilder;
import harlequinmettle.developertool.view.tabbuilders.VariablesTabBuilder;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//Jun 20, 2015  11:18:18 AM 
public class DevToolView {

	public JFrame UI;
	JTabbedPane tabs;
	static final String projectsTabTitle = "Projects";
	static final String variablesTabTitle = "Variables";
	static final String deployTabTitle = "Deploy App";
	static final String gitTabTitle = "Git";
	static final String backupTabTitle = "Backup";
	static final String[] tabTitles = { projectsTabTitle, variablesTabTitle, deployTabTitle, gitTabTitle, backupTabTitle };
	LinkedHashMap<String, VertialScrollingTab> tabTitleMap = new LinkedHashMap<String, VertialScrollingTab>();

	public static final int WIDTH = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	public static final int HEIGHT = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public DevToolView() {
		for (String tabTitle : tabTitles) {
			tabTitleMap.put(tabTitle, new VertialScrollingTab(tabTitle));
		}

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

	public void showUI() {
		updateOnUI(new Updateable() {
			// Jun 22, 2015 9:43:02 AM
			@Override
			public void updateGUI() {
				buildUITabs();
			}

		});
	}

	// Jun 22, 2015 10:29:32 AM
	protected void buildUITabs() {
		tabs.removeAll();
		new ProjectsTabBuilder().buildProjectsTab(tabTitleMap.get(projectsTabTitle));
		new VariablesTabBuilder().buildVariablesTab(tabTitleMap.get(variablesTabTitle));

		new DeployTabBuilder().buildDeployTab(tabTitleMap.get(deployTabTitle));
		new GitTabBuilder().buildGitTab(tabTitleMap.get(gitTabTitle));
		for (Entry<String, VertialScrollingTab> ent : tabTitleMap.entrySet())
			tabs.add(ent.getKey(), ent.getValue().scroller);
	}

	// Jun 23, 2015 1:21:06 PM
	private void buildAddGitTab() {
		// TODO: build git quick commit interface with index.xml commit message
		// with date/time
	}

	// Jun 23, 2015 1:21:06 PM
	private void buildBackupTab() {
		// TODO: use bakcup app to manage threading file copy and backup
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

	public void updateIDTabs() {
		// Jun 24, 2015 1:10:03 PM
		new DeployTabBuilder().buildDeployTab(tabTitleMap.get(deployTabTitle));
		tabTitleMap.get(deployTabTitle).revalidateRepaint();
	}
}
