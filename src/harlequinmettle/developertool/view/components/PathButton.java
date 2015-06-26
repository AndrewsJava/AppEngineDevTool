package harlequinmettle.developertool.view.components;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.PathButtonData;
import harlequinmettle.developertool.view.TextAreaSettingDisplayer;
import harlequinmettle.utils.filetools.ChooseFilePrompter;
import harlequinmettle.utils.guitools.JButtonWithEnterKeyAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

//Jun 23, 2015  9:40:51 AM 
public class PathButton extends JButtonWithEnterKeyAction {
	public PathButtonData buttonData;
	PathButton thisButton;

	public PathButton(PathButtonData buttonData) {
		super(buttonData.buttonTitle + " " + buttonData.path);
		this.buttonData = buttonData;
		setToolTipText(buttonData.tooltip);
		addActionListener(action);
		setHorizontalAlignment(SwingConstants.LEFT);
		thisButton = this;
	}

	public ActionListener action = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (buttonData.actionIndicator == PathButtonData.GAEWAR_BUTTON_ID)
				buttonData.path = ChooseFilePrompter.directoryPathChooser();
			else if (buttonData.actionIndicator == PathButtonData.APPCFG_BUTTON_ID)
				buttonData.path = ChooseFilePrompter.filePathChooser();
			else if (buttonData.actionIndicator == PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID)
				TextAreaSettingDisplayer.displayTextAreaForInput(thisButton);
			DevTool.getSingleton().saveModelState();
			setText(buttonData.buttonTitle + " " + buttonData.path);

		}
	};
}
