import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void addFirstTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addFirst(8); 
        arr.addFirst(7);
        arr.addFirst(6);
        arr.addFirst(5);
        arr.addFirst(4);
        arr.addFirst(3);
        arr.addFirst(2);
        arr.addFirst(1);
    }

    @Test
    public void addLastTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addLast(8); 
        arr.addLast(7);
        arr.addLast(6);
        arr.addLast(5);
        arr.addLast(4);
        arr.addLast(3);
        arr.addLast(2);
        arr.addLast(1);
    }

    @Test
    public void addFirstAddLastTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addLast(5);
        arr.addFirst(2);
        arr.addLast(6);
        arr.addLast(7);
        arr.addLast(8);
        arr.addFirst(1);
    }

    @Test
    public void isEmptyTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        assertThat(arr.isEmpty()).isTrue();
        arr.addFirst(5);
        assertThat(arr.isEmpty()).isFalse();

        arr.removeLast();
        assertThat(arr.isEmpty()).isTrue();
        arr.addFirst(4);
        arr.removeLast();
        assertThat(arr.isEmpty()).isTrue();
    }

    @Test
    public void sizeTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        assertThat(arr.size()).isEqualTo(0);
        arr.addFirst(5);
        assertThat(arr.size()).isEqualTo(1);
        arr.addLast(0);
        assertThat(arr.size()).isEqualTo(2);
        
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        assertThat(arr.size()).isEqualTo(0);
    }

    @Test
    public void getTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addLast(5);
        arr.addFirst(2);
        arr.addLast(6);
        arr.addLast(7);
        arr.addLast(8);
        arr.addFirst(1);
        assertThat(arr.get(0)).isEqualTo(1);
        assertThat(arr.get(1)).isEqualTo(2);
        assertThat(arr.get(2)).isEqualTo(5);
        assertThat(arr.get(3)).isEqualTo(6);
        assertThat(arr.get(4)).isEqualTo(7);
        assertThat(arr.get(5)).isEqualTo(8);
        assertThat(arr.get(6)).isNull();

        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();
        arr1.addLast(1);
        arr1.addLast(2);
        arr1.addLast(3);
        arr1.addLast(4);
        assertThat(arr1.get(0)).isEqualTo(1);
        assertThat(arr1.get(1)).isEqualTo(2);
        assertThat(arr1.get(2)).isEqualTo(3);
        assertThat(arr1.get(3)).isEqualTo(4);
        assertThat(arr1.get(4)).isNull();
        assertThat(arr1.get(-3)).isNull();
        assertThat(arr1.get(8)).isNull();
        assertThat(arr1.get(100)).isNull();
    }

    @Test
    public void getRecursive() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addLast(5);
        arr.addFirst(2);
        arr.addLast(6);
        arr.addLast(7);
        arr.addLast(8);
        arr.addFirst(1);
        assertThat(arr.getRecursive(0)).isEqualTo(1);
        assertThat(arr.getRecursive(1)).isEqualTo(2);
        assertThat(arr.getRecursive(2)).isEqualTo(5);
        assertThat(arr.getRecursive(3)).isEqualTo(6);
        assertThat(arr.getRecursive(4)).isEqualTo(7);
        assertThat(arr.getRecursive(5)).isEqualTo(8);
        assertThat(arr.getRecursive(6)).isNull();

        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();
        arr1.addLast(1);
        arr1.addLast(2);
        arr1.addLast(3);
        arr1.addLast(4);
        assertThat(arr1.getRecursive(0)).isEqualTo(1);
        assertThat(arr1.getRecursive(1)).isEqualTo(2);
        assertThat(arr1.getRecursive(2)).isEqualTo(3);
        assertThat(arr1.getRecursive(3)).isEqualTo(4);
        assertThat(arr1.getRecursive(4)).isNull();
        assertThat(arr1.getRecursive(-3)).isNull();
        assertThat(arr1.getRecursive(8)).isNull();
        assertThat(arr1.getRecursive(100)).isNull();
    }


    @Test
    public void toListTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addLast(8);
        arr.addLast(7);
        arr.addLast(6);
        arr.addLast(5);
        arr.addLast(4);
        arr.addLast(3);
        arr.addLast(2);
        arr.addLast(1);
        assertThat(arr.toList()).containsExactly(8, 7, 6, 5, 4, 3, 2, 1).inOrder();

        ArrayDeque61B<Integer> arr1 = new ArrayDeque61B<>();
        arr1.addFirst(8); 
        arr1.addFirst(7);
        arr1.addFirst(6);
        arr1.addFirst(5);
        arr1.addFirst(4);
        arr1.addFirst(3);
        arr1.addFirst(2);
        arr1.addFirst(1);
        assertThat(arr1.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8).inOrder();
        
        ArrayDeque61B<Integer> arr2 = new ArrayDeque61B<>();
        arr2.addLast(5);
        arr2.addFirst(2);
        arr2.addLast(6);
        arr2.addLast(7);
        arr2.addLast(8);
        arr2.addFirst(1);
        assertThat(arr2.toList()).containsExactly(1, 2, 5, 6, 7, 8).inOrder(); 
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        // 只添加last
        arr.addLast(5);
        arr.addLast(6);
        arr.addLast(7);
        assertThat(arr.removeFirst()).isEqualTo(5);
        assertThat(arr.removeFirst()).isEqualTo(6);
        assertThat(arr.removeFirst()).isEqualTo(7);
        assertThat(arr.removeFirst()).isNull();

        // 刪完全部重添加 
        arr.addLast(6); // [6]
        arr.addFirst(3); // [3, 6]
        arr.addLast(7); // [3, 6, 7]
        arr.addLast(8); // [3, 6, 7, 8]
        arr.addFirst(2); // [2, 3, 6, 7, 8]
        assertThat(arr.removeFirst()).isEqualTo(2);
        arr.addFirst(1); // [1, 3, 6, 7, 8]
        assertThat(arr.removeFirst()).isEqualTo(1);   
        assertThat(arr.removeFirst()).isEqualTo(3);   
        assertThat(arr.removeFirst()).isEqualTo(6);   
        assertThat(arr.removeFirst()).isEqualTo(7);   
        assertThat(arr.removeFirst()).isEqualTo(8);   
    }

    @Test
    public void removeLastTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addFirst(8); 
        arr.addFirst(7);
        arr.addFirst(6);
        arr.addFirst(5);
        arr.addFirst(4);
        arr.addFirst(3);
        arr.addFirst(2);
        arr.addFirst(1);
        assertThat(arr.removeLast()).isEqualTo(8);
        assertThat(arr.removeLast()).isEqualTo(7);
        assertThat(arr.removeLast()).isEqualTo(6);
        assertThat(arr.removeLast()).isEqualTo(5);
        assertThat(arr.removeLast()).isEqualTo(4);
        assertThat(arr.removeLast()).isEqualTo(3);
        assertThat(arr.removeLast()).isEqualTo(2);
        assertThat(arr.removeLast()).isEqualTo(1);
        assertThat(arr.removeLast()).isNull();
    }

    @Test
    public void resizeTest() {
        ArrayDeque61B<Integer> arr = new ArrayDeque61B<>();
        arr.addFirst(8);
        arr.addFirst(7);
        arr.addFirst(6);
        arr.addFirst(5);
        arr.addFirst(4);
        arr.addFirst(3);
        arr.addFirst(2);
        arr.addFirst(1);
        arr.addLast(9);   // [1, 2, 3, 4, 5, 6, 7, 8, 9]
        assertThat(arr.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();

        // 16 * 0.5 = 8 小於8會執行resize
        arr.removeFirst();  // [2, 3, 4, 5, 6, 7, 8, 9]
        arr.removeLast();   // [2, 3, 4, 5, 6, 7, 8]
        assertThat(arr.toList()).containsExactly(2, 3, 4, 5, 6, 7, 8).inOrder();
        arr.removeFirst();  // [3, 4, 5, 6, 7, 8]
        arr.removeFirst();  // [4, 5, 6, 7, 8]
        arr.removeLast();   // [4, 5, 6, 7]
        assertThat(arr.toList()).containsExactly(4, 5, 6, 7).inOrder();
        arr.removeFirst();  // [5, 6, 7]
        arr.removeLast();   // [5, 6]
        arr.removeLast();   // [5]
        arr.removeLast();
        assertThat(arr.toList()).isEmpty();
    }

}