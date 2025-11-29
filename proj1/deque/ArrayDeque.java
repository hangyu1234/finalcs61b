package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>{
    private T[] items;
    private int sizeleft;
    private int sizeright;
    private int middle;
    public ArrayDeque(){
        sizeleft=0;
        sizeright=0;
        items=(T[]) new Object[8];
        middle=items.length/2;
    }
    private void resize(int capacity){
        T[] a= (T[]) new Object[capacity];
        System.arraycopy(items,middle-sizeleft,a,capacity/2-sizeleft,sizeleft+sizeright);
        items=a;
        middle=capacity/2;
    }
    public void addFirst(T x){
        if (sizeleft+1>middle){resize((sizeleft+sizeright)*2);}
        items[middle-sizeleft-1]=x;
        sizeleft++;
    }
    public void addLast(T x){
        if (middle+sizeright>=items.length){resize((sizeleft+sizeright)*2);}
        items[middle+sizeright]=x;
        sizeright++;
    }
    public boolean isEmpty(){
        return (sizeleft+sizeright)==0;
    }
    public int size(){
        return sizeleft+sizeright;
    }
    public void printDeque(){
        for (int i=middle-sizeleft;i<middle+sizeright;i++){
            System.out.print(items[i]+" ");
        }
        System.out.println();
    }
    public T removeFirst(){
        if (sizeleft+sizeright==0){return null;}
        if ((middle>2*sizeleft)&&(middle+2*sizeright<items.length)){resize(middle);}
        sizeleft-=1;
        return items[middle-sizeleft-1];
    }
    public T removeLast(){
        if (sizeleft+sizeright==0){return null;}
        if ((middle>2*sizeleft)&&(middle+2*sizeright<items.length)){resize(middle);}
        sizeright-=1;
        return items[middle+sizeright];
    }
    public T get(int index){
        if (index>=sizeleft+sizeright){return null;}
        return items[middle-sizeleft+index];
    }
    public Iterator<T> iterator(){
        return new ArrayDeque.ArrayIterator();
    }
    public class ArrayIterator implements Iterator<T>{
        public int wizpos;
        public ArrayIterator(){wizpos=middle-sizeleft;}
        public boolean hasNext(){
            return wizpos<middle+sizeright;
        }
        public T next(){
            T returnItem=items[wizpos];
            wizpos++;
            return returnItem;
        }
        public void remove(){
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
    public boolean equals(Object o){
        if (this==o){return true;}
        if (o instanceof ArrayDeque){
            ArrayDeque<T> other = (ArrayDeque<T>) o;
            if (this.size()!=other.size()){return false;}
            int i=this.middle-this.sizeleft;
            int j=other.middle-other.sizeleft;
            while (i<this.middle+this.sizeright&&j<other.middle+other.sizeright){
                if (this.items[i]!=other.items[j]){return false;}
                i++;
                j++;
            }
            return true;
        }
        else {return false;}
    }
}
