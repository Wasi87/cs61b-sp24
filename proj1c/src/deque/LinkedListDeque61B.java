package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    public int size;

    public class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T f, Node p, Node n) {
            item = f;
            prev = p;
            next = n;
        }
    }
    
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node first = new Node(x, sentinel, sentinel.next);
        sentinel.next = first;
        first.next.prev = first;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node last = new Node(x, sentinel.prev, sentinel);
        sentinel.prev = last;
        last.prev.next = last;
        size++;
    }

    @Override
    public T get(int index) {
        Node target = sentinel.next;
        if (!isEmpty() && index <= size && index >= 0) {
            for (int i = 0; i < index; i++) {
                target = target.next;
            }
            return target.item;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (!isEmpty() && index <= size && index >= 0) {
        return _getRecursive(sentinel.next, index);
        }
                
        return null;
    }
    /// Return the in `index` element assuming `n` is the first element of the list
    private T _getRecursive(Node n, int index) {
        if (index == 0) {
            return n.item;
        } else {
            n = n.next;
            return _getRecursive(n, index-1);
        }
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            T el = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return el;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (!isEmpty()) {
            T el = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return el;
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }


    @Override
    public Iterator<T> iterator() {
        return new LLSIterator();
    }

    private class LLSIterator implements Iterator<T> {
        private int currentPosition = 0;

        @Override
        public boolean hasNext() {
            // next != first
            // current < length
            return  currentPosition < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            return get(currentPosition++);   
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LinkedListDeque61B<?> otherDeque) {
            if (this.size() != otherDeque.size()) {
                return false;
            }
            Iterator<T> iterator = this.iterator();
            Iterator<?> otherIterator = otherDeque.iterator();
            while (iterator.hasNext() && otherIterator.hasNext()) {
                if (!iterator.next().equals(otherIterator.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}
