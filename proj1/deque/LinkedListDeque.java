package deque;
import java.util.Iterator;
public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private static class Node<T> {
        public T item;
        public Node<T> next;
        public Node<T> last;
        public Node(Node<T> l, T i, Node<T> n) {
            last = l;
            item = i;
            next = n;
        }
    }

    private Node<T> sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.last = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T x) {
        size += 1;
        Node<T> add = new Node<T>(sentinel, x, this.sentinel.next);
        this.sentinel.next.last = add;
        sentinel.next = add;
    }
    @Override
    public void addLast(T x) {
        size += 1;
        Node<T> add = new Node<T>(this.sentinel.last, x, sentinel);
        this.sentinel.last.next = add;
        this.sentinel.last = add;
    }
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void printDeque() {
        Node<T> first = this.sentinel.next;
        while (first != sentinel) {
            System.out.print(first.item + " ");
            first = first.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T remove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.last = sentinel;
        size--;
        return remove;
    }
    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null;
        }
        T remove = sentinel.last.item;
        sentinel.last = sentinel.last.last;
        sentinel.last.next = sentinel;
        size--;
        return remove;
    }
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node<T> toget = sentinel.next;
        while (index > 0) {
            toget = toget.next;
            index--;
        }
        return toget.item;
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return helpget(sentinel.next, index);
    }
    private T helpget(Node<T> node, int index) {
        if (index == 0) {
            return node.item;
        } else {
            return helpget(node.next, index - 1);
        }
    }
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> wizpos;
        public LinkedListIterator() {
            wizpos = sentinel.next;
        }
        public boolean hasNext() {
            return wizpos != sentinel;
        }
        public T next() {
            T returnItem = wizpos.item;
            wizpos = wizpos.next;
            return returnItem;
        }
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> other = (LinkedListDeque<T>) o;
            if (this.size() != other.size()) {
                return false;
            }
            Node<T> pt = this.sentinel.next;
            Node<T> po = other.sentinel.next;
            while (pt != sentinel) {
                if (!pt.item.equals(po.item)) {
                    return false;
                }
                pt = pt.next;
                po = po.next;
            }
            return true;
        } else {
            return false;
        }
    }
}

