package main;

import java.util.HashMap;
import java.util.Set;


import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

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
    private HyponymsProcessor hypo;

    public HyponymsHandler(
        NGramMap map, 
        DirectedAcyclicGraph dag, 
        HashMap<Integer, Set<String>> synsets, 
        HashMap<String, Set<Integer>> wordtoIds
    ) {
        this.hypo = new HyponymsProcessor(map, dag, synsets, wordtoIds);
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> results = hypo.findHyponyms(q);
        return results.toString();
    }
}
