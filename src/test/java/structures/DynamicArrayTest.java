package structures;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    @Test
    void randomDataMatchesArrayList() {
        DynamicArray a=new DynamicArray();
        ArrayList<Integer> expected=new ArrayList<>();
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
        DynamicArray a=new DynamicArray();
        assertThrows(IndexOutOfBoundsException.class,()->a.get(0));
        a.add(5);
        a.add(0,5);
        a.add(2,9);
        assertEquals(5,a.get(0));
        assertEquals(9,a.get(2));
        assertTrue(a.contains(5));
        assertEquals(5,a.remove(0));
        assertEquals(9,a.remove(a.size()-1));
        assertThrows(IndexOutOfBoundsException.class,()->a.add(-1,3));
        assertThrows(IndexOutOfBoundsException.class,()->a.remove(3));
    }
}
