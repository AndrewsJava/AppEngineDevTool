package harlequinmettle.developertool.model;

import harlequinmettle.utils.filetools.SerializationTool;

import java.io.Serializable;

//Jun 20, 2015  11:21:26 AM 
public class DevToolModel implements Serializable {

	public String UITitle = "Developer Tool";

	public final int WIDTH = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

	public final int HEIGHT = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	public PathButtonData appcfg = new PathButtonData();

	private static final String modelSerializationPath = ".devtoolmodel";

	public void saveModelState() {
		SerializationTool.serializeObject(this, modelSerializationPath);
	}

	public static DevToolModel getSavedModelState() {
		return SerializationTool.deserializeObject(DevToolModel.class, modelSerializationPath);
	}

}
