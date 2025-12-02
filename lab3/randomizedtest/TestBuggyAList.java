package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> right = new AListNoResizing<Integer>();
        BuggyAList<Integer> wrong = new BuggyAList<Integer>();
        for (int i = 4; i < 7; i++){
            right.addLast(i);
            wrong.addLast(i);
        }
        for (int i = 0; i < 3; i++){
            int rightnum = right.removeLast();
            int wrongnum = wrong.removeLast();
            assertEquals(wrongnum, rightnum);
        }
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> R = new BuggyAList<>();
        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0 && L.size() < 1000) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                R.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int sizel = L.size();
                int sizer = R.size();
                assertEquals(sizel, sizer);
            } else if (operationNumber == 2) {
                if (L.size() > 0 && R.size() > 0) {
                    int getl = L.getLast();
                    int getr = R.getLast();
                    assertEquals(getl, getr);
                }
            } else {
                if (L.size() > 0 && R.size() > 0) {
                    int reml = L.removeLast();
                    int remr = R.removeLast();
                    assertEquals(reml, remr);
                }
            }
        }
    }
}
