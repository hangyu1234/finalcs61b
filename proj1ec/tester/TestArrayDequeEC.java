package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.introcs.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void Array1() {
        StudentArrayDeque<Integer> deque1 = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> deque2 = new ArrayDequeSolution<Integer>();
        Integer a = 500;
        String message = "";
        for (Integer i = 0; i < a; i++){
            Integer num = StdRandom.uniform(0, 4);
            if (num == 0) {
                deque1.addFirst(i);
                deque2.addFirst(i);
                message = message + "addFirst(" + i.toString() + ")\n";
                assertEquals(message, deque1.size(), deque2.size());
            } else if (num == 1) {
                deque1.addLast(i);
                deque2.addLast(i);
                message = message + "addLast(" + i.toString() + ")\n";
                assertEquals(message, deque1.size(),deque2.size());
            } else if (num ==2 ) {
                if (deque1.isEmpty() || deque2.isEmpty()) {
                    continue;
                }
                Integer remf1 = deque1.removeFirst();
                Integer remf2 = deque2.removeFirst();
                message = message + "removeFirst()\n";
                assertEquals(message, remf1, remf2);
                assertEquals(deque1.size(), deque2.size());
            } else {
                if (deque1.isEmpty() || deque2.isEmpty()) {
                    continue;
                }
                Integer reml1 = deque1.removeLast();
                Integer reml2 = deque2.removeLast();
                message = message + "removeLast()\n";
                assertEquals(message, reml1, reml2);
                assertEquals(deque1.size(), deque2.size());
            }
        }
    }
}
