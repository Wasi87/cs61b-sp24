package main;

import browser.NgordnetServer;
import ngrams.NGramMap;

import org.slf4j.LoggerFactory;

// k>0, 範圍時間內出現最多次的字, 按字母順序返回
// 在時間內出現頻率為0，則不該顯示
// 符合字少於 k值，則只返回符合字


public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        NGramMap ngm = new NGramMap(wordFile, countFile);
        FileRead fr = new FileRead();
        fr.readSynsets(synsetFile);
        fr.readHyponyms(hyponymFile);
        
        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(ngm, fr.getDag(), fr.getSynsets(), fr.getWordToIds()));
        hns.register("hypohist", new HypohistHandler(ngm, fr.getDag(), fr.getSynsets(), fr.getWordToIds()));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
