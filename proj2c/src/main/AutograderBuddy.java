package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMap ngm = new NGramMap(wordFile, countFile);
        FileRead fr = new FileRead();
        fr.readSynsets(synsetFile);
        fr.readHyponyms(hyponymFile);
        
        return new HyponymsHandler(ngm, fr.getDag(), fr.getSynsets(), fr.getWordToIds());
    }
}
