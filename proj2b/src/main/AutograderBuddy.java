package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetServer;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
        String wordFile, String countFile,
        String synsetFile, String hyponymFile) {

        FileRead fr = new FileRead();
        fr.readSynsets(synsetFile);
        fr.readHyponyms(hyponymFile);
        
        return new HyponymsHandler(fr.getDag(), fr.getSynsets(), fr.getWordToIds());
    }
}
