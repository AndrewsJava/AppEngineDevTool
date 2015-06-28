package harlequinmettle.developertool.model;

import java.io.Serializable;
import java.util.HashMap;

//Jun 20, 2015  11:21:26 AM 
public class DevToolModel implements Serializable {

	// Jun 23, 2015 10:52:34 AM
	@Override
	public String toString() {
		return "" + UITitle + "\n" + appcfg + project + addids;

	}

	public HashMap<String, Boolean> savedDeployPreferences = new HashMap<String, Boolean>();

	public DevToolModel() {
		appcfg = new PathButtonData(PathButtonData.APPCFG_BUTTON_ID);
		project = new PathButtonData(PathButtonData.PROJECT_BUTTON_ID);
		addids = new PathButtonData(PathButtonData.ADD_APPLICATION_IDS_BUTTON_ID);
	}

	public String UITitle = "project is undefined";

	public PathButtonData appcfg;
	public PathButtonData project;
	public PathButtonData addids;

}
