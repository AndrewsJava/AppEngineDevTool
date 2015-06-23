package harlequinmettle.developertool.model;

import java.io.Serializable;

//Jun 20, 2015  11:21:26 AM 
public class DevToolModel implements Serializable {

	// Jun 23, 2015 10:52:34 AM
	@Override
	public String toString() {
		return "" + UITitle + "\n" + appcfg + gaewar + addids;

	}

	public DevToolModel() {
		appcfg = new PathButtonData(PathButtonData.APPCFG_BUTTON_ID);
		gaewar = new PathButtonData(PathButtonData.GAEWAR_BUTTON_ID);
		addids = new PathButtonData(PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID);
	}

	public String UITitle = "project is undefined";

	public final int WIDTH = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	public final int HEIGHT = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public PathButtonData appcfg;
	public PathButtonData gaewar;
	public PathButtonData addids;

}
