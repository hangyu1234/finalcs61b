package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>{
    private T[] items;
    private int sizeleft;
    public int sizeright;
    public ArrayDeque(){
        sizeleft=0;
        sizeright=0;
        items=(T[]) new Object[8];
    }
    private void resize(int capacity){
        T[] a= (T[]) new Object[capacity];
        if (sizeleft<0){
            System.arraycopy(items,-sizeleft-1,a,0,sizeleft+sizeright);
        }
        else if (sizeright<0){
            System.arraycopy(items,items.length-sizeleft,a,0,sizeleft+sizeright);
        }
        else{
            System.arraycopy(items,items.length-sizeleft,a,0,sizeleft);
            System.arraycopy(items,0,a,sizeleft,sizeright);
        }
        items=a;
        int size=sizeleft+sizeright;
        sizeleft=0;
        sizeright=size;
    }
    public void addFirst(T x){
        if (sizeleft+sizeright>=items.length){resize((sizeleft+sizeright)*2);}
        items[items.length-1-sizeleft]=x;
        sizeleft++;
    }
    public void addLast(T x){
        if (sizeleft+sizeright>=items.length){resize((sizeleft+sizeright)*2);}
        items[sizeright]=x;
        sizeright++;
    }
    public boolean isEmpty(){
        return (sizeleft+sizeright)==0;
    }
    public int size(){
        return sizeleft+sizeright;
    }
    public void printDeque(){
        for (int i=items.length-sizeleft;i<items.length;i++){
            System.out.print(items[i]+" ");
        }
        for (int i=0;i<sizeright;i++){
            System.out.print(items[i]+" ");
        }
        System.out.println();
    }
    public T removeFirst(){
        T returnitem;
        if (sizeleft+sizeright==0){return null;}
        if (sizeleft==0){
            sizeleft--;
            returnitem=items[0];
            resize(items.length);
        }
        else{
            returnitem=items[items.length-sizeleft];
            sizeleft--;
        }
        if (4*(sizeleft+sizeright)<=items.length){resize(items.length/2);}
        return returnitem;
    }
    public T removeLast(){
        T returnitem;
        if (sizeleft+sizeright==0){return null;}
        if (sizeright==0){
            sizeright--;
            returnitem=items[items.length-1];
            resize(items.length);
        }
        else{
            returnitem=items[sizeright-1];
            sizeright--;
        }
        if (4*(sizeleft+sizeright)<items.length){resize(items.length/2);}
        return returnitem;
    }
    public T get(int index){
        if (index>=sizeleft+sizeright){return null;}
        if (index<=sizeleft){
            return items[items.length-sizeleft+index];
        }
        else{return items[index-sizeleft-1];}
    }
    public Iterator<T> iterator(){
        return new ArrayDeque.ArrayIterator();
    }
    public class ArrayIterator implements Iterator<T>{
        public int wizpos;
        public ArrayIterator(){wizpos=items.length-sizeleft;}
        public boolean hasNext(){
            if (wizpos>=items.length-sizeleft){return true;}
            else {return wizpos<sizeright;}
        }
        public T next(){
            T returnItem=items[wizpos];
            wizpos++;
            if (wizpos==items.length){wizpos=0;}
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
            int i=this.items.length-this.sizeleft;
            int j=other.items.length-other.sizeleft;
            while ((i<this.sizeright||i>=this.items.length-sizeleft)&&(j<other.sizeright||j>=other.items.length-other.sizeleft)){
                if (this.items[i]!=other.items[j]){return false;}
                i++;
                j++;
                if (i==this.items.length){i=0;}
                if (j==other.items.length){j=0;}
            }
            return true;
        }
        else {return false;}
    }
}
