package main;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            TimeSeries ts = map.countHistory(word, startYear, endYear);
            if (!ts.isEmpty()) {
                lts.add(ts);
                labels.add(word);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        
        return encodedImage;
    }
}
