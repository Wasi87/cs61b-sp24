import java.util.Iterator;

import org.junit.Test;

import deque.LinkedListDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class LinkedListDeque61BTest {
    @Test
    public void testIteratorEmpty() {
        LinkedListDeque61B<Integer> deck = new LinkedListDeque61B<>();
        assertThat(deck.iterator().hasNext()).isFalse();
    }

    @Test
    public void testIterator() {
        LinkedListDeque61B<Integer> deck = new LinkedListDeque61B<>();
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
        LinkedListDeque61B<String> strDeck = new LinkedListDeque61B<>();
        
        strDeck.addFirst("Hello");
        strDeck.addFirst(", ");
        strDeck.addFirst("this");
        strDeck.addFirst("is");
        strDeck.addFirst("reverse");

        System.out.println(strDeck.toString());
    }

    @Test
    public void testEqualsSameInstance() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");
        assertThat(deck.equals(deck)).isTrue();
    }

    @Test 
    public void testEqualsEmpty() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        LinkedListDeque61B<String> deck2 = new LinkedListDeque61B<>();
        assertThat(deck.equals(deck2)).isTrue();
    }

    @Test
    public void testEqualsNull() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        LinkedListDeque61B<String> deck2 = new LinkedListDeque61B<>();
        assertThat(deck.equals(null)).isFalse();
    }

    @Test
    public void testEqualsDifferentInstances() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");
        
        LinkedListDeque61B<String> deck2 = new LinkedListDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isTrue();
    }

    @Test
    public void testEqualsDifferentInstancesDifferentContent() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("d");
        
        LinkedListDeque61B<String> deck2 = new LinkedListDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isFalse();
    }

    @Test
    public void testEqualsDifferentSizes() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        
        LinkedListDeque61B<String> deck2 = new LinkedListDeque61B<>();
        deck2.addFirst("a");
        deck2.addFirst("b");
        deck2.addFirst("c");

        assertThat(deck.equals(deck2)).isFalse();
    }

    @Test
    public void testDifferentTypes() {
        LinkedListDeque61B<String> deck = new LinkedListDeque61B<>();
        deck.addFirst("a");
        deck.addFirst("b");
        deck.addFirst("c");

        String notADeque = "notADeque";

        assertThat(deck.equals(notADeque)).isFalse();
    }

    @Test 
    public void testToStringEmpty() {
        LinkedListDeque61B<String> strDeck = new LinkedListDeque61B<>();

        assertThat(strDeck.toString()).isEqualTo("[]");
    }

    @Test
    public void testToStringWithElement() {
        LinkedListDeque61B<String> strDeck = new LinkedListDeque61B<>();

        strDeck.addLast("front");
        strDeck.addLast("middle");
        strDeck.addLast("back");

        assertThat(strDeck.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testToStringAfterRemoving() {
        LinkedListDeque61B<String> strDeck = new LinkedListDeque61B<>();

        strDeck.addLast("front");
        strDeck.addLast("middle");
        strDeck.addLast("back");

        strDeck.removeLast();
        assertThat(strDeck.toString()).isEqualTo("[front, middle]");
    }

    @Test
    public void testToStringWithMixedType() {
        LinkedListDeque61B<Object> deck = new LinkedListDeque61B<>();

        deck.addFirst("string");
        deck.addFirst(12);

        assertThat(deck.toString()).isEqualTo("[12, string]");
    }
}
