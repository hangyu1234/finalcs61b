package hashmap;

import java.util.*;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
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

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int inisize = 16;
    private int size;
    private double loadFactor = 0.75;
    private int count = 0;


    /** Constructors */
    public MyHashMap() {
        buckets = createTable(inisize);
        size = inisize;
        fillbuckets();
    }

    public MyHashMap(int initialSize) {
        inisize = initialSize;
        buckets = createTable(inisize);
        size = inisize;
        fillbuckets();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        inisize = initialSize;
        size = inisize;
        loadFactor = maxLoad;
        buckets = createTable(inisize);
        fillbuckets();
    }

    private void fillbuckets() {
        for (int i = 0; i < inisize; i++) {
            buckets[i] = createBucket();
        }
    }
    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
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
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /** Removes all of the mappings from this map. */
    public void clear() {
        count = 0;
        buckets = createTable(inisize);
        size = inisize;
    }

    private int position(K key, int size) {
        int i = key.hashCode();
        int result = i % size;
        if (result >= 0) {
            return result;
        } else {
            return result + size;
        }
    }
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        int i = position(key, size);
        Collection<Node> bucket = buckets[i];
        if (bucket == null) {
            return false;
        } else {
            for (Node p : bucket) {
                if (p.key.equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            int i = position(key, size);
            Collection<Node> bucket = buckets[i];
            for (Node p : bucket) {
                if (p.key.equals(key)) {
                    return p.value;
                }
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return count;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        if ((double)count / size > loadFactor) {
            resize();
        }
        int i = position(key, size);
        if (buckets[i] == null) {
            buckets[i] = createBucket();
        }
        Collection<Node> bucket = buckets[i];
        if (containsKey(key)) {
            for (Node p : bucket) {
                if (p.key.equals(key)) {
                    p.value = value;
                }
            }
        } else {
            Node p = createNode(key, value);
            bucket.add(p);
            count++;
        }
    }

    private void resize() {
        MyHashMap<K, V> temp = new MyHashMap<K, V>(size* 2, loadFactor);
        for (int i = 0; i < size; i++) {
            Collection<Node> bucket = buckets[i];
            for (Node p : bucket) {
                temp.put(p.key, p.value);
            }
        }
        size = size * 2;
        this.buckets = temp.buckets;
    }
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> all = new HashSet<>();
        for (int i = 0; i < size; i++) {
            Collection<Node> bucket = buckets[i];
            if (bucket != null) {
                for (Node p : bucket) {
                    all.add(p.key);
                }
            }
        }
        return all;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        return remove(key, null);
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        int i = position(key, size);
        Collection<Node> bucket = buckets[i];
        V result = null;
        for (Node p : bucket) {
            if (p.key.equals(key)) {
                if (p.value.equals(value) || value == null) {
                    result = p.value;
                    bucket.remove(p);
                }
            }
        }
        return result;
    };

    public Iterator<K> iterator() {
        return new hashiterator();
    }
    private class hashiterator implements Iterator<K> {
        int wizpos = 0;
        Iterator<Node> buckiter = reiter();
        public Iterator<Node> reiter() {
            while (buckets[wizpos] == null) {
                wizpos++;
                reiter();
            }
            return buckets[wizpos].iterator();

        }
        public boolean hasNext() {
            if (wizpos == size - 1 && !buckiter.hasNext()) {
                return false;
            } else {
                return true;
            }
        }
        public K next() {
            if (!buckiter.hasNext()) {
                wizpos++;
                buckiter = reiter();
            }
            return buckiter.next().key;
        }
    }
}
