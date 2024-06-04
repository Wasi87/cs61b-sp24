package ngrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    
    private Map<String, TimeSeries> wordTimeSeriesMap;
    private TimeSeries totalCountTimeSeries; // 年, 次數

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In inWords = new In(wordsFilename);
        wordTimeSeriesMap = new HashMap<>();

        while (inWords.hasNextLine()) {
            String nextLine = inWords.readLine();
            String[] parts = nextLine.split("\t");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            wordTimeSeriesMap.computeIfAbsent(word, k -> new TimeSeries()).put(year, count);
        }

        In inCounts = new In(countsFilename);
        totalCountTimeSeries = new TimeSeries();
        while (inCounts.hasNextLine()) {
            String nextLine = inCounts.readLine();
            String[] parts = nextLine.split(",");
            int year = Integer.parseInt(parts[0]);
            double totalWordsCount = Double.parseDouble(parts[1]);

            totalCountTimeSeries.put(year, totalWordsCount);
        }

        
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (wordTimeSeriesMap.containsKey(word)) {
            TimeSeries wordTS = wordTimeSeriesMap.get(word);
            return new TimeSeries(wordTS, startYear, endYear);
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (wordTimeSeriesMap.containsKey(word)) {
            TimeSeries wordTS = wordTimeSeriesMap.get(word);
            return wordTS;
        }
        return new TimeSeries();
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries copied = new TimeSeries();
        copied.putAll(totalCountTimeSeries);
        return copied;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (wordTimeSeriesMap.containsKey(word)) {
            return countHistory(word, startYear, endYear).dividedBy(totalCountTimeSeries);
        }
        return new TimeSeries();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (wordTimeSeriesMap.containsKey(word)) {
            return countHistory(word).dividedBy(totalCountTimeSeries);
        }
        return new TimeSeries();
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summed = new TimeSeries();
        for (String word : words) {
            TimeSeries ts = wordTimeSeriesMap.get(word);
            TimeSeries copied = new TimeSeries(ts, startYear, endYear);
            System.out.println(copied);
            summed = summed.plus(copied);
        }
        return summed.dividedBy(totalCountTimeSeries);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summed = new TimeSeries();
        for (String word : words) {
            TimeSeries ts = wordTimeSeriesMap.get(word);
            summed = summed.plus(ts);
        }
        return summed.dividedBy(totalCountTimeSeries);
    }
}
