package harlequinmettle.developertool.model;

import java.io.Serializable;

//Jun 22, 2015  11:30:02 AM 
public class PathButtonData implements Serializable {
	@Override
	public String toString() {
		String string = "button title: " + buttonTitle + "\n";
		string += "button info: " + path + "\n";
		string += "button tooltip: " + tooltip + "\n";
		return string;
	}

	// even for folder pathfinder
	// odd for file pathfinder
	public static final int APPCFG_BUTTON_ID = 1010101;
	public static final int GAEWAR_BUTTON_ID = 2224442;
	public static final int ADD_APPLICATION_IDS_BUTTON_ID = 3673763;

	public PathButtonData(String buttonTitle, String path, String tooltip) {
		this.buttonTitle = buttonTitle;
		this.path = path;
		this.tooltip = tooltip;
	}

	public PathButtonData(int buttonID) {
		if (buttonID == APPCFG_BUTTON_ID) {
			this.buttonTitle = buttonTitle_appcfg;
			this.path = path_appcfg;
			this.tooltip = tooltip_appcfg;
		}
		if (buttonID == GAEWAR_BUTTON_ID) {
			this.buttonTitle = buttonTitle_gaewar;
			this.path = path_gaewar;
			this.tooltip = tooltip_gaewar;
		}
		if (buttonID == ADD_APPLICATION_IDS_BUTTON_ID) {
			this.buttonTitle = buttonTitle_addids;
			this.path = path_addids;
			this.tooltip = tooltip_addids;
		}

		actionIndicator = buttonID;
	}

	// TODO: use validation in the button action listner
	public String validation;

	public String buttonTitle;

	public String path;

	public String tooltip;

	public int actionIndicator;

	private String buttonTitle_gaewar = "war folder location: ";

	private String path_gaewar = "undefined";

	private String tooltip_gaewar = "google app engine war folder location (typically next to src file in eclipse gae projects)";

	private String buttonTitle_appcfg = "appcfg.sh location: ";

	private String path_appcfg = "undefined";

	private String tooltip_appcfg = "appcfg.sh in elcipse appengine pluggin jdk folder bin (make sure it and run_java.sh are marked as executable)";

	private String buttonTitle_addids = "gae application id(s): ";

	private String path_addids = "undefined";

	private String tooltip_addids = "gae application id(s) for deployment set in appengine-web.xml (for multiple ids separate with spaces)";

}
