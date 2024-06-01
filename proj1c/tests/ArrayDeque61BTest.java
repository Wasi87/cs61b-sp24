import org.junit.Test;

import deque.ArrayDeque61B;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class ArrayDeque61BTest {
    @Test
    public void testIteratorEmpty() {
        ArrayDeque61B<Integer> deck = new ArrayDeque61B<>();
        assertThat(deck.iterator().hasNext()).isFalse();
    }

    @Test
    public void testIterator() {
        ArrayDeque61B<Integer> deck = new ArrayDeque61B<>();
        deck.addFirst(1);  // [1]
        deck.addLast(2);  // [1, 2]
        deck.addLast(3);  // [1, 2, 3]

        assertThat(deck).containsExactly(1, 2, 3).inOrder();

        Iterator<Integer> it = deck.iterator();
        int expected = 1;
        while (it.hasNext()) {
            assertThat(it.next()).isEqualTo(expected);
            expected++;
        }

        deck.removeFirst();  // [2, 3]
        assertThat(deck.iterator().hasNext()).isTrue();
        assertThat(deck.iterator().next()).isEqualTo(2);

        deck.removeFirst();  // [3]
        assertThat(deck.iterator().hasNext()).isTrue();
        assertThat(deck.iterator().next()).isEqualTo(3);
        
        deck.removeFirst();
        assertThat(deck.iterator().hasNext()).isFalse();
        assertThat(deck.iterator().next()).isEqualTo(null);
    }

    @Test
    public void testStringIterator() {
        ArrayDeque61B<String> strDeck = new ArrayDeque61B<>();
        
        strDeck.addFirst("Hello");
        strDeck.addFirst(", ");
        strDeck.addFirst("this");
        strDeck.addFirst("is");
        strDeck.addFirst("reverse");

        System.out.println(strDeck.toString());
    }

    @Test
    public void testEqualsSameInstance() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");
        assertThat(deck.equals(deck)).isTrue();
    }

    @Test 
    public void testEqualsEmpty() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        ArrayDeque61B<String> deck2 = new ArrayDeque61B<>();
        assertThat(deck.equals(deck2)).isTrue();
    }

    @Test
    public void testEqualsNull() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        ArrayDeque61B<String> deck2 = new ArrayDeque61B<>();
        assertThat(deck.equals(null)).isFalse();
    }

    @Test
    public void testEqualsDifferentInstances() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");
        
        ArrayDeque61B<String> deck2 = new ArrayDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isTrue();
    }

    @Test
    public void testEqualsDifferentInstancesDifferentContent() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("d");
        
        ArrayDeque61B<String> deck2 = new ArrayDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isFalse();
    }

    @Test
    public void testEqualsDifferentSizes() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        
        ArrayDeque61B<String> deck2 = new ArrayDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isFalse();
    }

    @Test
    public void testDifferentTypes() {
        ArrayDeque61B<String> deck = new ArrayDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");

        String notADeque = "notADeque";

        assertThat(deck.equals(notADeque)).isFalse();
    }

    @Test 
    public void testToStringEmpty() {
        ArrayDeque61B<String> strDeck = new ArrayDeque61B<>();

        assertThat(strDeck.toString()).isEqualTo("[]");
    }

    @Test
    public void testToStringWithElement() {
        ArrayDeque61B<String> strDeck = new ArrayDeque61B<>();

        strDeck.addLast("front");
        strDeck.addLast("middle");
        strDeck.addLast("back");

        assertThat(strDeck.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testToStringAfterRemoving() {
        ArrayDeque61B<String> strDeck = new ArrayDeque61B<>();

        strDeck.addLast("front");
        strDeck.addLast("middle");
        strDeck.addLast("back");

        strDeck.removeLast();
        assertThat(strDeck.toString()).isEqualTo("[front, middle]");
    }

    @Test
    public void testToStringWithMixedType() {
        ArrayDeque61B<Object> deck = new ArrayDeque61B<>();

        deck.addFirst("string");
        deck.addFirst(12);

        assertThat(deck.toString()).isEqualTo("[12, string]");
    }
}
