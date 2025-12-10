package bstmap;

import javax.swing.text.html.HTMLDocument;
import java.util.Set;
import java.util.Comparator;
import java.util.Iterator;
import java.util.HashSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    /** Removes all of the mappings from this map. */
    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;
        private BSTNode father;
        public BSTNode(K _key, V _value) {
            key = _key;
            value = _value;
        }
    }
    private BSTNode root;
    private int size = 0;
    public void clear() {
        size = 0;
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        BSTNode p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) {
                return true;
            } else if (cmp > 0) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        return false;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        BSTNode p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp == 0) {
                return p.value;
            } else if (cmp > 0) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key,value);
            size++;
        } else {
            BSTNode p = root;
            while (p != null) {
                int cmp = key.compareTo(p.key);
                if (cmp == 0) {
                    p.value = value;
                } else if (cmp > 0) {
                    if (p.right != null) {
                        p = p.right;
                    } else {
                        BSTNode n = new BSTNode(key, value);
                        p.right = n;
                        n.father = p;
                        size++;
                        break;
                    }
                } else {
                    if (p.left != null) {
                        p = p.left;
                    } else {
                        BSTNode n = new BSTNode(key, value);
                        size++;
                        p.left = n;
                        n.father = p;
                        break;
                    }
                }
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet();
        addset(keys, root);
        return keys;
    }
    private void addset(Set<K> keys, BSTNode p) {
        if (p.left == null && p.right == null) {
            keys.add(p.key);
        } else if (p.left == null) {
            keys.add(p.key);
            addset(keys, p.right);
        } else if (p.right == null) {
            addset(keys, p.left);
            keys.add(p.key);
        } else {
            addset(keys, p.left);
            keys.add(p.key);
            addset(keys,p.right);
        }
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        return remove(key, null);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    private void son(BSTNode s, BSTNode ns) {
        if (s == root) {
            root = ns;
        } else {
            if (s.father.left == s) {
                s.father.left = ns;
            } else {
                s.father.right = ns;
            }
        }
    }
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        } else {
            BSTNode p = root;
            while (p != null) {
                int cmp = key.compareTo(p.key);
                if (cmp == 0) {
                    if (value != null && p.value != value) {
                        return null;
                    };
                    if (p.left == null && p.right == null) {
                        son(p, null);
                        size--;
                        return p.value;
                    } else if (p.left == null) {
                        son(p, p.right);
                        size--;
                        p.right.father = p.father;
                        return p.value;
                    } else if (p.right == null) {
                        son(p, p.left);
                        size--;
                        p.left.father = p.father;
                        return p.value;
                    } else {
                        BSTNode m = p.left;
                        while (m.right != null) {
                            m = m.right;
                        }
                        son(m, m.left);
                        if (m.left != null) {
                            m.left.father = m.father;
                        }
                        m.father = p.father;
                        m.left = p.left;
                        m.right = p.right;
                        son(p, m);
                        size--;
                        return p.value;
                    }
                } else if (cmp > 0) {
                    p = p.right;
                } else {
                    p = p.left;
                }
            }
        }
        return null;
    }
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
    public void printInOrder() {
        printall(root);
    }
    private void printall(BSTNode p) {
        if (p.left == null && p.right == null) {
            System.out.print(p.value);
        } else if (p.left == null) {
            System.out.print(p.value);
            printall(p.right);
        } else if (p.right == null) {
            printall(p.left);
            System.out.print(p.value);
        } else {
            printall(p.left);
            System.out.print(p.value);
            printall(p.right);
        }
    }
    private class BSTIterator implements Iterator<K> {
        BSTNode wizpos;
        BSTIterator() {
            while (wizpos.left != null) {
                wizpos = wizpos.left;
            }
        }
        public boolean hasNext() {
            BSTNode p = root;
            while (root.right != null) {
                int cmp = wizpos.key.compareTo(p.key);
                if (cmp < 0) {
                    return true;
                } else if (cmp == 0) {
                    if (p.right == null) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    p = p.right;
                }
            }
            return false;
        }
        public K next() {
            K returnkey = wizpos.key;
            if (wizpos.right != null) {
                wizpos = wizpos.right;
                while (wizpos.left != null) {
                    wizpos = wizpos.left;
                }
            } else {
                while (wizpos.father.left != wizpos) {
                    wizpos = wizpos.father;
                }
                wizpos = wizpos.father;
            }
            return returnkey;
        }
    }
}
