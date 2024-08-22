package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import browser.NgordnetQuery;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;

public class HyponymsProcessor {
    private NGramMap map;
    private DirectedAcyclicGraph dag;
    private HashMap<String, Set<Integer>> wordtoIds;
    private HashMap<Integer, Set<String>> synsets;

    public HyponymsProcessor(
        NGramMap map, 
        DirectedAcyclicGraph dag, 
        HashMap<Integer, Set<String>> synsets,
        HashMap<String, Set<Integer>> wordtoIds
    ) {
        this.map = map;
        this.dag = dag;
        this.synsets = synsets;
        this.wordtoIds = wordtoIds;
    }
    
    public Set<String> findHyponyms(NgordnetQuery q) {
        Set<String> results = new TreeSet<>();
        Integer count = 0;

        // check repeated words
        Set<String> set = new HashSet<>(q.words());

        // check one or multiple search words
        for (String word : set) {
            if (wordtoIds.containsKey(word)) {
                count++;
                if (count > 1) {
                    break;
                }
            }
        }

        if (count == 1) {
            results = handleSingleWordQuery(set, q);
        } else if (count > 1) {
            results = handleMultipleWordQuery(set, q);
        }

        if (q.k() == 0) {
            return results;
        } else if (q.k() > 0) {
            return findTopKWords(results, q);
        }
        return new TreeSet<>();
    }

    private Set<String> handleSingleWordQuery(Set<String> set, NgordnetQuery q) {
        Set<String> results = new TreeSet<>();
        Set<Integer> resultIds = new TreeSet<>();
        for (String word : set) {
            Set<Integer> ids = wordtoIds.get(word);
            for (Integer id : ids) {
                if (q.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
                    resultIds.addAll(dag.traverseFrom(id));
                } else if (q.ngordnetQueryType() == NgordnetQueryType.ANCESTORS) {
                    resultIds.addAll(dag.traverseTo(id));
                }
            }
        }
        for (Integer resultId : resultIds) {
            Set<String> words = synsets.get(resultId);
            results.addAll(words);
        }
        return results;
    }

    private Set<String> handleMultipleWordQuery(Set<String> set, NgordnetQuery q) {
        Set<String> results;
        List<Set<String>> wordSets = new ArrayList<>();
        for (String word : set) {
            Set<Integer> ids = wordtoIds.get(word);
            Set<String> tmpSet = new HashSet<>();
            for (Integer id : ids) {
                Set<Integer> resultIds;
                if (q.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
                    resultIds = dag.traverseFrom(id);
                } else {
                    resultIds = dag.traverseTo(id);
                }
                for (Integer resultId : resultIds) {
                    Set<String> words = synsets.get(resultId);
                    tmpSet.addAll(words);
                }
            }
            wordSets.add(tmpSet);
        }
        results = findDuplicates(wordSets);
        return results;
    }

    private Set<String> findTopKWords(Set<String> results, NgordnetQuery q) {
        TreeMap<Double, TreeSet<String>> wordOccurrences = new TreeMap<>();
        for (String word : results) {
            TimeSeries ts = map.countHistory(word, q.startYear(), q.endYear());
            double occurrences = ts.totalOccurrences();
            if (occurrences > 0) {
                wordOccurrences.computeIfAbsent(occurrences, k -> new TreeSet<>()).add(word);
            }
        }

        TreeSet<String> topKWords = new TreeSet<>();
        for (Map.Entry<Double, TreeSet<String>> entry : wordOccurrences.descendingMap().entrySet()) {
            for (String word : entry.getValue()) {
                topKWords.add(word);
                if (topKWords.size() == q.k()) {
                    break;
                }
            }
            if (topKWords.size() == q.k()) {
                break;
            }
        }
        return topKWords;
    }

    private Set<String> findDuplicates(List<Set<String>> sets) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (Set<String> set : sets) {
            for (String word : set) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        Set<String> duplicates = new TreeSet<>();
        int numSets = sets.size();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 1 && entry.getValue() == numSets) {
                duplicates.add(entry.getKey());
            }
        }
        return duplicates;
    }
}
