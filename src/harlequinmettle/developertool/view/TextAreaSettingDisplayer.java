package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.view.components.PathButton;
import harlequinmettle.utils.guitools.WindowDisplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

//Jun 23, 2015  10:34:47 AM 
public class TextAreaSettingDisplayer {

	// Jun 23, 2015 10:35:00 AM
	public static void displayTextAreaForInput(PathButton thisButton) {
		JTextArea input = new JTextArea();
		input.setText(thisButton.buttonData.path);
		input.addKeyListener(getKeyListener(thisButton));

		WindowDisplayer showInFrame = new WindowDisplayer();
		showInFrame.setSize(800, 200);
		showInFrame.showComponentInJFrame(input, false, false);

	}

	// System.out.println("asdfsd"+"asdfasdf");

	private static KeyListener getKeyListener(final PathButton thisButton) {
		// Jun 23, 2015 11:05:39 AM
		return new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// Jun 23, 2015 11:06:17 AM

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// Jun 23, 2015 11:06:17 AM

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// Jun 23, 2015 11:06:17 AM
				thisButton.buttonData.path = ((JTextArea) arg0.getSource()).getText();

				thisButton.setText(thisButton.buttonData.buttonTitle + " " + thisButton.buttonData.path);
				DevTool.getSingleton().saveModelState();

				// DevTool.getSingleton().view.updateGaeDeploymentButtonsComponents();
				DevTool.getSingleton().view.updateIDTabs();
			}

		};

	}
}
