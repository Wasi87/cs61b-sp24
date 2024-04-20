import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        if ( L != null) {
            for (int i=0; i<L.size(); i++) {
                sum += L.get(i);
            }
            return sum;
        }
        return 0;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            if (L.get(i)%2 == 0) {
                ls.add(L.get(i));
            }
        }
        return ls;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> ls = new ArrayList<>();
        for (int i = 0; i < L1.size(); i++) {
            if (L2.contains(L1.get(i))) {
                ls.add(L1.get(i));
            }            
        }
        return ls;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (int i = 0; i < words.size(); i++) {
            String str = words.get(i);
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == c) {
                    count++;
                }
            }
        }
        return count;
    }
}
