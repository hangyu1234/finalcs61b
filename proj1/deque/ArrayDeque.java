package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int sizeleft;
    private int sizeright;
    public ArrayDeque() {
        sizeleft = 0;
        sizeright = 0;
        items = (T[]) new Object[8];
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (sizeleft < 0) {
            System.arraycopy(items, -sizeleft, a, 0, sizeleft + sizeright);
        } else if (sizeright < 0) {
            System.arraycopy(items, items.length - sizeleft, a, 0, sizeleft + sizeright);
        } else {
            System.arraycopy(items, items.length - sizeleft, a, 0, sizeleft);
            System.arraycopy(items, 0, a, sizeleft, sizeright);
        }
        items = a;
        int size = sizeleft + sizeright;
        sizeleft = 0;
        sizeright = size;
    }
    @Override
    public void addFirst(T x) {
        if (sizeleft + sizeright >= items.length) {
            resize((sizeleft + sizeright) * 2);
        }
        items[items.length - 1 - sizeleft] = x;
        sizeleft++;
    }
    @Override
    public void addLast(T x) {
        if (sizeleft + sizeright >= items.length) {
            resize((sizeleft + sizeright) * 2);
        }
        items[sizeright] = x;
        sizeright++;
    }
    @Override
    public int size() {
        return sizeleft + sizeright;
    }
    @Override
    public void printDeque() {
        for (int i = items.length - sizeleft; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        for (int i = 0; i < sizeright; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        T returnitem;
        if (sizeleft + sizeright == 0) {
            return null;
        }
        if (sizeleft == 0) {
            sizeleft--;
            returnitem = items[0];
            resize(items.length);
        } else {
            returnitem = items[items.length - sizeleft];
            sizeleft--;
        }
        if (4 * (sizeleft + sizeright) <= items.length) {
            if (items.length > 8) {
                resize(items.length / 2);
            }
        }
        return returnitem;
    }
    @Override
    public T removeLast() {
        T returnitem;
        if (sizeleft + sizeright == 0) {
            return null;
        }
        if (sizeright == 0) {
            sizeright--;
            returnitem = items[items.length - 1];
            resize(items.length);
        } else {
            returnitem = items[sizeright - 1];
            sizeright--;
        }
        if (4 * (sizeleft + sizeright) < items.length) {
            if (items.length > 8) {
                resize(items.length / 2);
            }
        }
        return returnitem;
    }
    @Override
    public T get(int index) {
        if (index >= sizeleft + sizeright) {
            return null;
        }
        if (index < sizeleft) {
            return items[items.length - sizeleft + index];
        } else {
            return items[index - sizeleft];
        }
    }
    public Iterator<T> iterator() {
        return new ArrayDeque.ArrayIterator();
    }
    private class ArrayIterator implements Iterator<T> {
        private int wizpos;
        ArrayIterator() {
            if (sizeleft == 0) {
                wizpos = 0;
            } else {
                wizpos = items.length - sizeleft;
            }
        }
        public boolean hasNext() {
            if (wizpos >= items.length - sizeleft) {
                return true;
            } else {
                return wizpos < sizeright;
            }
        }
        public T next() {
            T returnItem = items[wizpos];
            wizpos++;
            if (wizpos == items.length) {
                wizpos = 0;
            }
            return returnItem;
        }
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque) {
            Deque<T> other = (Deque<T>) o;
            if (this.size() != other.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (!this.get(i).equals(other.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}

