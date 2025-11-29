package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c){
        comparator=c;
    }
    public T max(){
        return max(comparator);
    }
    public T max(Comparator<T> c){
        if (this.size()==0){return null;}
        else {
            T maxitem=this.get(0);
            for (int i=0;i<this.size();i++){
                int cmp=c.compare(maxitem,get(i));
                if (cmp<0){maxitem=get(i);}
            }
            return maxitem;
        }
    }
}
