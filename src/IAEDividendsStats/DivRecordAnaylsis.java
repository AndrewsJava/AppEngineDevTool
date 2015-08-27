// Aug 25, 2015 11:27:05 AM
package IAEDividendsStats;

import harlequinmettle.utils.debugtools.InstanceCounter;
import harlequinmettle.utils.filetools.FileTools;

import java.io.File;

public class DivRecordAnaylsis {

	// Aug 25, 2015 11:27:05 AM
	public static void main(String[] args) {
		analyzeDividendRecordFromHtmlDowloadPage();
	}

	private static void analyzeDividendRecordFromHtmlDowloadPage() {
		// Aug 25, 2015 11:28:31 AM
		String html = FileTools.tryToReadFileToString(new File("dividendrecordsequery.html"), null);
		if (html == null)
			return;
		String[] tables = html.split("<table");
		System.out.println(tables.length);
		countDividends(tables);
	}

	private static void countDividends(String[] tables) {
		// Aug 25, 2015 11:38:17 AM
		InstanceCounter counts = new InstanceCounter();
		for (String divRecord : tables) {
			float divs = divRecord.split("<tr></tr>").length;
			System.out.println(divs + "    " + divRecord);
			counts.add(divs - 1);
		}
		counts.printlnCounts(1);
	}
}
