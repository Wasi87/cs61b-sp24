import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHumanActionK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("human_action");
    
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);   
    }

    // 不存在的單字
    @Test
    public void testNullK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("dog");
    
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);   
    }

    // 無下義詞
    @Test
    public void testNoHyponymsK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("flashback");
    
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[flashback]";
        assertThat(actual).isEqualTo(expected);   
    }

    // 重複下義詞
    @Test
    public void testRepeatHyponymsK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("happening");
    
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[adjustment, alteration, change, conversion, happening, increase, jump, leap, modification, mutation, natural_event, occurrence, occurrent, saltation, transition]";
        assertThat(actual).isEqualTo(expected);   
    }
}
