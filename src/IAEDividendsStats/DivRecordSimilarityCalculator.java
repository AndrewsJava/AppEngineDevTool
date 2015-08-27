// Aug 26, 2015 11:28:51 AM
package IAEDividendsStats;

import harlequinmettle.utils.debugtools.InstanceCounter;
import harlequinmettle.utils.timetools.TimeRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

public class DivRecordSimilarityCalculator extends DivRecordDatabaseBuilder {

	public static final int TODAY = (int) TimeRecord.dayNumber();

	public static void main(String[] args) {
		// Aug 26, 2015 11:28:51 AM

		buildDividendDatabase();
		analyzeDivs();
		info.printlnCounts(1);
	}

	private static void analyzeDivs() {
		// Aug 26, 2015 11:34:53 AM
		int counter = 0;
		for (Entry<String, ConcurrentSkipListMap<Short, Float>> ent : DIVIDEND_DATA.entrySet()) {
			// System.out.println("ticker: " + ent.getKey() + "    ");
			extractRegularDividends(ent.getValue(), ent.getKey());
			// if (counter++ > 10)
			// return;
		}
	}

	static float max = Float.NEGATIVE_INFINITY;
	static float maxDayDiff = Float.NEGATIVE_INFINITY;
	static float maxDivDiff = Float.NEGATIVE_INFINITY;
	static InstanceCounter info = new InstanceCounter();

	private static ConcurrentSkipListMap<Short, Float> extractRegularDividends(ConcurrentSkipListMap<Short, Float> yearsDivs, String ticker) {
		if (yearsDivs.size() > 8)
			return yearsDivs;
		// TODO: find most regular dividends for calculations
		ArrayList<Short> days = new ArrayList<Short>(yearsDivs.keySet());
		ArrayList<Float> dividends = new ArrayList<Float>(yearsDivs.values());
		ConcurrentSkipListMap<Float, Short> match = new ConcurrentSkipListMap<Float, Short>();
		for (int i = 0; i < yearsDivs.size(); i++) {
			short currentDay = days.get(i);
			float extraneousDivFactorSum = 0f;
			for (int j = 0; j < yearsDivs.size(); j++) {

				short nextDay = days.get(j);
				if (currentDay < TODAY - 365 || nextDay < TODAY - 365)
					continue;
				if (currentDay == nextDay)
					continue;
				float currentDiv = dividends.get(i);
				float nextDiv = dividends.get(j);

				float timeInterval = Math.abs(nextDay - currentDay);

				// System.out.println("       d    " + currentDay +
				// "       d2    " + nextDay + "       diff    " + timeInterval
				// + "       df/30    "
				// + timeInterval / 30);
				timeInterval /= 30;
				timeInterval /= yearsDivs.size();
				extraneousDivFactorSum += timeInterval - (long) timeInterval;
				// float amountDiff = (Math.abs(nextDiv - currentDiv));
				// if (amountDiff == 0)
				// continue;
				// amountDiff = roundToCents(Math.abs(nextDiv - currentDiv));
				// if (amountDiff != amountDiff) {
				// System.out.println("         nan         : " + amountDiff +
				// "    ");
				// }
				// info.add(amountDiff);

			}
			extraneousDivFactorSum *= 100;
			extraneousDivFactorSum = (int) extraneousDivFactorSum;
			if (extraneousDivFactorSum > 200)
				System.out.println(yearsDivs.size() + "           " + ticker + "           " + extraneousDivFactorSum + "    "
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date((long) currentDay * 24 * 3600 * 1000)) + "         $"
						+ yearsDivs.get(currentDay));

			match.put(extraneousDivFactorSum, currentDay);

		}
		// for (Entry<Float, Short> ent : match.entrySet())
		// System.out.println("           " + ent.getKey() + "    "
		// + new SimpleDateFormat("yyyy-MM-dd").format(new Date((long)
		// ent.getValue() * 24 * 3600 * 1000)) + "         $"
		// + yearsDivs.get(ent.getValue()));
		return yearsDivs;
	}

	private static float roundToCents(float abs) {
		// Aug 26, 2015 12:22:52 PM
		return (float) ((int) (abs * 100)) / 100.0f;
	}
}
