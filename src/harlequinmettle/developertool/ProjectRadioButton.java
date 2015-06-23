package harlequinmettle.developertool;

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
				DevTool.getSingleton().setCurrentModel(((ProjectRadioButton) arg0.getSource()).getText());

			}
		};

	}
}
