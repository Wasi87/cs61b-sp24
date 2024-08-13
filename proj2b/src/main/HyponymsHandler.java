package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

// synsets.txt
// ID, 同義詞集(多字), 定義 
// 163,chopper pearly,informal terms for a human `tooth'   

// 下義詞 hyponyms.txt
// synset ID, 後面是下為詞的ID(38611, 9007)
// 79537,38611,9007
// 第一欄有可能重複出現

// 圖形類別
// 將 WordNet 資料集檔案轉換為圖形
// 接受一個單字, 用圖遍歷 找出所有下位詞

public class HyponymsHandler extends NgordnetQueryHandler {
    private DirectedAcyclicGraph dag;
    private HashMap wordtoIds;
    private HashMap synsets;


    public HyponymsHandler(DirectedAcyclicGraph dag, HashMap<Integer, Set<String>> synsets, HashMap<String, Set<Integer>> wordtoIds) {
        this.dag = dag;
        this.synsets = synsets;
        this.wordtoIds = wordtoIds;
    }

    @Override
    public String handle(NgordnetQuery q) {
        
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
            Set<Integer> ids;
            Set<Integer> resultIds = new TreeSet<>();
            for (String word : set) {
                ids = (Set<Integer>) wordtoIds.get(word);
                for (Integer id : ids) {
                    resultIds.addAll(dag.traverseFrom(id));
                }
            }
            for (Integer resultId : resultIds) {
                Set<String> words = (Set<String>) synsets.get(resultId);
                results.addAll(words);
            }
        } 

        if (count > 1) {
            Set<Integer> ids;
            List<Set<Integer>> resultIdSets = new ArrayList<>();
            List<Set<String>> wordSets = new ArrayList<>();
            for (String word : set) {
                // 同個單字
                ids = (Set<Integer>) wordtoIds.get(word);
                Set<String> tmpSet = new HashSet<>();
                for (Integer id : ids) {
                    Set<Integer> resultIds = dag.traverseFrom(id);
                    for (Integer resultId : resultIds) {
                        Set<String> words = (Set<String>) synsets.get(resultId);
                        tmpSet.addAll(words);
                    }
                }
                wordSets.add(tmpSet);
            }
            results = findDuplicates(wordSets);
        }
    return results.toString();
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
