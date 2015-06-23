package harlequinmettle.developertool.view;

import harlequinmettle.developertool.DevTool;
import harlequinmettle.developertool.model.DevToolModel;
import harlequinmettle.utils.filetools.FileTools;

import java.io.File;

//Jun 23, 2015  12:35:59 PM 
public class GAEProjectDeployer {

	// Jun 23, 2015 12:36:32 PM
	public void deployGAEProject(String id) {
		setAppIdInXMLFile(id);
	}

	// Jun 23, 2015 12:37:22 PM
	private void setAppIdInXMLFile(String id) {
		DevToolModel model = DevTool.getSingleton().model;
		String xmlfilepath = model.gaewar.path + "/WEB-INF/appengine-web.xml";
		String fileContents = FileTools.tryToReadFileToString(new File(xmlfilepath), null);
		if (fileContents == null)
			return;
		String[] parts = fileContents.split("<application>.+?</application>");
		String newFileContents = parts[0] + "<application>" + id.trim() + "</application>" + parts[1];
		FileTools.tryToWriteStringToFile(new File(xmlfilepath), newFileContents);
	}
}
