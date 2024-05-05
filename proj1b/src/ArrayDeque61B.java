import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] arr;
    private int size;
    private int firstIndex;

    private int cap = 8;
    private int MINLENGTH = 4;
    private double FACTOR = (size <= 15) ? 0.5 : 0.25;


    public ArrayDeque61B() {
        arr = (T[]) new Object[cap];
        size = 0; 
        firstIndex = 0;
    }
    
    @Override
    public void addFirst(T x) {
        if (cap == size) {
            resize();
        }
        
        firstIndex = Math.floorMod(firstIndex - 1, cap); // start from index 7
        arr[firstIndex] = x;
        size++;

        for (int i = 0; i < cap; i++) {
            System.out.printf("index %d value %d\n", i, arr[i]);
        }
    }
    
    @Override
    public void addLast(T x) {
        if (cap == size) {
            resize();
        }

        int lastIndex = (firstIndex + size) % cap; // start from index 0
        arr[lastIndex] = x;
        size++;

        for (int i = 0; i < cap; i++) {
            System.out.printf("index %d value %d\n", i, arr[i]);
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int index = Math.floorMod(i + firstIndex, cap);
            returnList.add(arr[index]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        // if ((double)size / cap <= FACTOR) {
        //     resize();
        // }

        if (size > 0) {
            // System.out.printf("first index %d", firstIndex);
            T value = arr[firstIndex];
            // System.out.println(value);
            arr[firstIndex] = null;
            firstIndex = Math.floorMod(firstIndex + 1, cap);
            size--;
            return value;
        }
        
        return null;
    }

    @Override
    public T removeLast() {
        if ((double)size/cap <= FACTOR) {
            resize();
        }

        if (size > 0) {
            int lastIndex = Math.floorMod(firstIndex + size - 1, cap);
            System.out.printf("last index %d\n", lastIndex);
            T value = arr[lastIndex];
            System.out.println(value);
            arr[lastIndex] = null;
            size--;
            return value;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }

        int curPos = firstIndex + index;
        int curInd = Math.floorMod(curPos, cap); // curPos 0 return 0
        return arr[curInd];
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null; 
        }
        return _getRecursive(firstIndex, index);
    }

    private T _getRecursive(int curIndex, int targetIndex) {
        if (targetIndex == 0) {
            return arr[curIndex];
        } 
        int nextIndex = (curIndex + 1) % cap;
        return _getRecursive(nextIndex, targetIndex - 1);
    }

    private void resize() {
        int newCap;
        if (cap == size) {
            newCap = cap * 2;
        } else if ((double)size / cap <= FACTOR) {
            newCap = Math.max(MINLENGTH, cap / 2);
        } else {
            return;
        }

        T[] newArr = (T[]) new Object[newCap];

        for (int i = 0; i < size; i++) {
            newArr[i] = arr[(firstIndex + i) % cap];
            System.out.printf("\ncopy index %d, arr value %d\n", i, newArr[i]);
        }

        arr = newArr;
        cap = newCap;
        firstIndex = 0;
    }
}
