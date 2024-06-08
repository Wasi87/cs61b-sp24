package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /** 
    +------------------+
    | Collection<Node> |
    | [0]              | --> [Node(Key1, Value1), Node(Key2, Value2)]
    +------------------+
    | [1]              | --> [Node(Key3, Value3)]
    +------------------+
    | [2]              | --> [Node(Key4, Value4), Node(Key5, Value5)]
    +------------------+
    | [3]              | --> [Node(Key6, Value6)]
    +------------------+    
     */

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int capacity;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() { 
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) { 
        this(initialCapacity, 0.75);
    }
 
    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) { 
        this.size = 0;
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    private int hash(Object key) {
        return Math.floorMod(key.hashCode(), capacity);
    }
    
    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }
    private class MyHashMapIterator implements Iterator<K> {
        private int bucketIndex;
        private Iterator<Node> bucketIterator;

        public MyHashMapIterator() {
            bucketIndex = 0;
            bucketIterator = buckets[0].iterator();
            nextBucket();
        }

        @Override
        public boolean hasNext() {
            return bucketIndex < capacity;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K key = bucketIterator.next().key;
            nextBucket();
            return key;
        }

        private void nextBucket() {
            // 當符合 這 bucket 到最末時，換下一個 bucket
            while (!bucketIterator.hasNext() && bucketIndex < capacity) {
                bucketIndex++;
                if (bucketIndex < capacity) {
                    bucketIterator = buckets[bucketIndex].iterator();
                }
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if ((double)size / capacity > loadFactor) {
            resize();
        }
        int bucketNumber = hash(key);
        for (Node node : buckets[bucketNumber]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        Node n = new Node(key, value);
        buckets[bucketNumber].add(n);
        size++;
    }

    private void resize() {
        int newCap = capacity * 2;
        Collection<Node>[] newBuckets = new Collection[newCap];
        for (int i = 0; i < newCap; i++) {
            newBuckets[i] = createBucket();
        }

        for (Collection<Node> collection : buckets) {
            for (Node node : collection) {
                int newNum = Math.floorMod(node.key.hashCode(), newCap);
                newBuckets[newNum].add(node);
            }
        }
        buckets = newBuckets;
        capacity = newCap;
    }

    @Override
    public V get(K key) {
        int bucketNumber = hash(key);
        V value = null;
        for (MyHashMap<K, V>.Node node : buckets[bucketNumber]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketNumber = hash(key);
        for (MyHashMap<K, V>.Node node : buckets[bucketNumber]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (Collection<Node> collection : buckets) {
            collection.clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Iterator<K> it = this.iterator();
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }

    @Override
    public V remove(K key) {
        int bucketNumber = hash(key);
        V value = null;
        Iterator<Node> it = buckets[bucketNumber].iterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node.key.equals(key)) {
                value = node.value;
                size--;
                it.remove();
                return value;
            }
        }
        return value;
    }

}
