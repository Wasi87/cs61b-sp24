import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestOneWordKNot0Hyponyms {
    public static final String WORDS_FILE = "data/ngrams/frequency-EECS.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets-EECS.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms-EECS.txt";
    
    public static final String WORDS_FILE_1 = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE_1 = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE_1 = "data/wordnet/synsets.txt";
    public static final String SMALL_HYPONYM_FILE_1 = "data/wordnet/hyponyms.txt";

    // I cannot access EECS files 
    @Test
    public void testActKNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testChangeK3() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE_1, TOTAL_COUNTS_FILE_1, SMALL_SYNSET_FILE_1, SMALL_HYPONYM_FILE_1);
        List<String> words = List.of("change");

        NgordnetQuery nq = new NgordnetQuery(words, 2015, 2020, 3, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[get, right, way]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testIceK10() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE_1, TOTAL_COUNTS_FILE_1, SMALL_SYNSET_FILE_1, SMALL_HYPONYM_FILE_1);
        List<String> words = List.of("ice");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 10, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[crank, frost, glass, ice, trash]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testTinaK10() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE_1, TOTAL_COUNTS_FILE_1, SMALL_SYNSET_FILE_1, SMALL_HYPONYM_FILE_1);
        List<String> words = List.of("tina");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 10, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }
}
