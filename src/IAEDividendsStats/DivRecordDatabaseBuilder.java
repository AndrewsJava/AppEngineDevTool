// Aug 26, 2015 10:23:56 AM
package IAEDividendsStats;

import harlequinmettle.utils.filetools.FileTools;
import harlequinmettle.utils.numbertools.format.NumberTools;
import harlequinmettle.utils.stringtools.StringTools;
import harlequinmettle.utils.timetools.TimeRecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

public class DivRecordDatabaseBuilder {
	public static ConcurrentSkipListMap<String, ConcurrentSkipListMap<Short, Float>> DIVIDEND_DATA = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<Short, Float>>();

	public static void main(String[] args) {
		// Aug 26, 2015 10:23:56 AM
		buildDividendDatabase();
	}

	protected static void buildDividendDatabase() {
		String html = FileTools.tryToReadFileToString(new File("dividendrecordsequery.html"), null);
		if (html == null)
			return;
		String[] tables = html.split("<table");
		for (String rawHtmlTable : tables) {
			putDataFromTableIntoDatabase(rawHtmlTable);
		}
	}

	private static void putDataFromTableIntoDatabase(String rawHtmlTable) {
		// Aug 26, 2015 10:52:59 AM
		ArrayList<String> tickerDividendTable = StringTools.extractAllPattern(rawHtmlTable, "<td.*?>.*?</td>");
		if (tickerDividendTable.size() < 3)
			return;
		String ticker = StringTools.replaceAllRegex(tickerDividendTable.get(0), "<.*?>", "").trim();
		ConcurrentSkipListMap<Short, Float> record = new ConcurrentSkipListMap<Short, Float>();
		for (int index = 1; index < tickerDividendTable.size(); index += 2) {

			String date = StringTools.replaceAllRegex(tickerDividendTable.get(index), "<.*?>", "").trim();
			String amount = StringTools.replaceAllRegex(tickerDividendTable.get(index + 1), "<.*?>", "").trim();
			short dateNumber = (short) TimeRecord.dayNumber(new SimpleDateFormat("yyyy-MM-dd"), date, -1);
			float divAmount = NumberTools.tryToParseFloat(amount, Float.NaN);
			record.put(dateNumber, divAmount);
			// if (dateNumber < 0)
			// System.out.println("ticker: " + ticker + "    " + dateNumber +
			// "   ---   " + divAmount);
			// if (divAmount != divAmount)
			// System.out.println("ticker: " + ticker + "    " + dateNumber +
			// "   ---   " + divAmount);
		}
		DIVIDEND_DATA.put(ticker, record);
		// System.out.println(" : " + tickerDividendTable);
	}
}
