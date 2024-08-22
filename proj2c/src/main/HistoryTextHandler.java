package main;

import java.util.List;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        TimeSeries ts;
        String response = "";
        for (String word : words) {
            ts = map.weightHistory(word, startYear, endYear);
            response += word + ": ";
            response += ts.toString() + "\n";
        }
        return response;
    }
}
