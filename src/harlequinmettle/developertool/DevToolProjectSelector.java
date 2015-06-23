package harlequinmettle.developertool;

import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;
import harlequinmettle.utils.guitools.PreferredJScrollPane;
import harlequinmettle.utils.guitools.VerticalJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JTextArea;

//Jun 23, 2015  1:45:49 PM 
public class DevToolProjectSelector {

	// Jun 23, 2015 1:47:32 PM
	public PreferredJScrollPane buildProjectsTab() {
		VerticalJPanel variablesTabContents = new VerticalJPanel();

		JTextArea input = new JTextArea();
		input.setText("project name");

		JButtonWithEnterKeyAction addProjectButton = new JButtonWithEnterKeyAction("add new project");

		variablesTabContents.add(input);
		variablesTabContents.add(addProjectButton);

		ButtonGroup bg = new ButtonGroup();
		variablesTabContents.add(addProjectButton);
		for (String project : DevTool.getSingleton().projects) {
			ProjectRadioButton projectRadioButton = new ProjectRadioButton(project);

			variablesTabContents.add(projectRadioButton);
			bg.add(projectRadioButton);
		}
		PreferredJScrollPane variablesTabScroller = new PreferredJScrollPane(variablesTabContents);
		addProjectButton.addActionListener(getAddProjectActionListener(input, variablesTabContents, bg, variablesTabScroller));
		return variablesTabScroller;
	}

	// Jun 23, 2015 2:16:54 PM
	private ActionListener getAddProjectActionListener(final JTextArea input, final VerticalJPanel variablesTabContents, final ButtonGroup bg,
			final PreferredJScrollPane variablesTabScroller) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DevTool.getSingleton().currentProjectTitle = input.getText();
				ProjectRadioButton projectRadioButton = new ProjectRadioButton(DevTool.getSingleton().currentProjectTitle);
				variablesTabContents.add(projectRadioButton);
				bg.add(projectRadioButton);
				variablesTabScroller.validate();
				variablesTabScroller.repaint();
			}
		};

	}
}
