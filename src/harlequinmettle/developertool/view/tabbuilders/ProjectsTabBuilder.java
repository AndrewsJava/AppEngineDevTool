package harlequinmettle.developertool.view.tabbuilders;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.components.ProjectRadioButton;
import harlequinmettle.developertool.view.components.VertialScrollingTab;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;
import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JTextArea;

//Jun 26, 2015  9:55:03 AM 
public class ProjectsTabBuilder {

	// Jun 23, 2015 1:47:32 PM
	public void buildProjectsTab(VertialScrollingTab projects) {
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
			if (project.equals(DevTool.getSingleton().currentProjectTitle)) {
				projectRadioButton.setSelected(true);

			}
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

				DevTool.getSingleton().storeSelectedProjectTitleToMemory(projectAddName);
			}
		};

	}

}
