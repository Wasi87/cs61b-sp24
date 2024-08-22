package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.knowm.xchart.XYChart;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;

public class HypohistHandler extends NgordnetQueryHandler {
    private NGramMap map;
    private HyponymsProcessor hypo;

    public HypohistHandler(
        NGramMap map, 
        DirectedAcyclicGraph dag, 
        HashMap<Integer, Set<String>> synsets, 
        HashMap<String, Set<Integer>> wordtoIds
    ) {
        this.map = map;
        this.hypo = new HyponymsProcessor(map, dag, synsets, wordtoIds);
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = new TreeSet<>();
        if (q.k() > 0) {
            words = hypo.findHyponyms(q);
        }
        System.out.println(words.toString());
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            TimeSeries ts = map.weightHistory(word, startYear, endYear);
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
