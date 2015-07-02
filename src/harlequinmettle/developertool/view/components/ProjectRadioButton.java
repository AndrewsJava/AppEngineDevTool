package harlequinmettle.developertool.view.components;

import harlequinmettle.developertool.DevTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

//Jun 23, 2015  2:32:07 PM 
public class ProjectRadioButton extends JRadioButton {
	public ProjectRadioButton(String text) {
		super(text);
		addActionListener(getSetProjectActionListener());

	}

	private ActionListener getSetProjectActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// getsource ischecked
				DevTool dev = DevTool.getSingleton();
				String projectTitle = ((ProjectRadioButton) arg0.getSource()).getText();
				dev.model.UITitle = projectTitle;
				dev.setCurrentModel(projectTitle);
				DevTool.getSingleton().storeSelectedProjectTitleToMemory(projectTitle);
			}
		};

	}
}
