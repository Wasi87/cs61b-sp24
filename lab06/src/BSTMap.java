import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.checkerframework.checker.units.qual.K;
import org.checkerframework.checker.units.qual.m;


@SuppressWarnings("hiding")
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size = 0;

    public BSTMap() {
        root = null;
    }

    public void printInOrder() {
        System.out.println(_getPrintStrRec(root));
    }
    private String _getPrintStrRec(Node n) {
        String res = new String("");

        if (n.left != null) {
            res += _getPrintStrRec(n.left);
        }
        res += n.value.toString() + " ";
        if (n.right != null) {
            res += _getPrintStrRec(n.right);
        }
        return res;
    }

    public void put(K key, V value) {
        Node parent = null;
        Node current = root;
        
        
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                parent = current;
                current = current.left;
            }
            else if (cmp > 0) {
                parent = current;
                current = current.right;
            }
            // 取代原本值
            else {
                current.value = value;
                return;
            } 
        }

        
        if (parent == null) {
            // 根節點
            root = new Node(key, value);
        } else if (key.compareTo(parent.key) < 0) {
            // 插入左節點
            parent.left = new Node(key, value);
        } else {
            parent.right = new Node(key, value);
        }
        size++;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    public V get(K key) {
        Node current = root;
        
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            }
            else if (cmp > 0) {
                current = current.right;
            }
            else {
                return current.value;
            }
        }
        // printInOrder(current.value);
        return null;
    }

    /** Returns whether this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        Node current = root;
        
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            }
            else if (cmp > 0) {
                current = current.right;
            }
            else {
                return true;
            }
        }
        return false;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** Removes every mapping from this map. */
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        Iterator<K> iterator = iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        return set;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Stack<Node> stack;

        public BSTMapIterator() {
            stack = new Stack<>();
            pushAll(root);
        }
        private void pushAll(Node n) {
            while (n != null) {
                stack.push(n);
                n = n.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node current = stack.pop();
            pushAll(current.right);
            return current.key;
        }

    }


    
    /** Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        if (containsKey(key)) {
            V value = get(key);
            root = _remove(root, key);
            size--;
            return value;
        }
        return null;
    }

    private Node _remove(Node n, K key) {
        if (n == null) {
            return null;
        }

        int cmp = key.compareTo(n.key);
        if (cmp > 0) {
            // 更新鏈結
            n.right = _remove(n.right, key);
        } else if (cmp < 0) {
            n.left = _remove(n.left, key);
        } else {
            if (n.left == null) {
                // 沒有任何子節點 和 沒左子節點
                return n.right;
            } else if (n.right == null) {
                return n.left;
            } else {
                // 返回 鏈結值
                // 返回左子最大或右子最小
                Node minNode = findMin(n.right);
                n.key = minNode.key;
                n.value = minNode.value;
                n.right = _remove(n.right, minNode.key);
            }
        }
        return n;
    }

    private Node findMin(Node n) {
        Node c = n;
        while (c.left != null) {
            c = c.left;
        }
        return c;
    }
}
