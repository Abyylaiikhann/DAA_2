package structures;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {
    @Test
    void randomDataMatchesLinkedList() {
        MyLinkedList a=new MyLinkedList();
        LinkedList<Integer> expected=new LinkedList<>();
        Random r=new Random(42);
        for(int i=0;i<200;i++) {
            int x=r.nextInt(1000);
            a.add(x);
            expected.add(x);
        }
        for(int i=0;i<expected.size();i++) assertEquals(expected.get(i).intValue(),a.get(i));
    }

    @Test
    void edgeCases() {
        MyLinkedList a=new MyLinkedList();
        assertThrows(IndexOutOfBoundsException.class,()->a.get(0));
        a.add(7);
        a.add(0,7);
        a.add(2,8);
        assertEquals(7,a.get(0));
        assertEquals(8,a.get(2));
        assertTrue(a.contains(7));
        assertEquals(7,a.remove(0));
        assertEquals(8,a.remove(a.size()-1));
        assertThrows(IndexOutOfBoundsException.class,()->a.add(4,1));
    }
}
