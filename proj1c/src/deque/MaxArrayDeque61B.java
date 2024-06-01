package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    private Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        this.comparator = c;    
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }

        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(this.get(i), max) > 0) {
                max = this.get(i);
            }
        }
        return max;
    }
}
