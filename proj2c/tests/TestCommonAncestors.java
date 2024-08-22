import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestCommonAncestors {
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String LARGE_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec for a common-ancestors query on the word "adjustment".
     * You should add more tests for the other spec examples! */
    @Test
    public void testSpecAdjustment() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("adjustment");

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 0, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[adjustment, alteration, event, happening, modification, natural_event, occurrence, occurrent]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEventK3() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("event");
    
        NgordnetQuery nq = new NgordnetQuery(words, 2015, 2020, 3, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[case, process, state]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEventK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("event");
    
        NgordnetQuery nq = new NgordnetQuery(words, 2015, 2020, 0, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[abstract_entity, abstraction, attribute, case, circumstance, condition, consequence, effect, entity, event, issue, natural_phenomenon, outcome, phenomenon, physical_entity, physical_phenomenon, physical_process, process, psychological_feature, result, state, status, upshot]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testChangeK5() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("change");
    
        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 5, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[change, good, process, result, whole]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testChangeAndAlterationK5() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
            LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("change", "alteration");
    
        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 5, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, entity, event]";
        assertThat(actual).isEqualTo(expected);
    }
}
