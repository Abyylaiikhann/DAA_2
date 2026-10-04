package structures;

import org.junit.jupiter.api.Test;
import java.util.PriorityQueue;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {
    @Test
    void compareWithPriorityQueue() {
        MinHeap heap=new MinHeap();
        PriorityQueue<Integer> q=new PriorityQueue<>();
        Random r=new Random(42);
        for(int i=0;i<500;i++) {
            int x=r.nextInt(5000);
            heap.insert(x);
            q.add(x);
            checkHeap(heap);
        }
        while(!q.isEmpty()) {
            assertEquals(q.peek().intValue(),heap.peekMin());
            assertEquals(q.poll().intValue(),heap.extractMin());
            checkHeap(heap);
        }
    }

    @Test
    void emptyAndDuplicates() {
        MinHeap heap=new MinHeap();
        assertThrows(IllegalStateException.class,heap::peekMin);
        assertThrows(IllegalStateException.class,heap::extractMin);
        heap.insert(3);
        heap.insert(3);
        heap.insert(1);
        assertEquals(1,heap.extractMin());
        assertEquals(3,heap.extractMin());
        assertEquals(3,heap.extractMin());
    }

    private void checkHeap(MinHeap heap) {
        for(int i=1;i<heap.size();i++) {
            int parent=(i-1)/2;
            assertTrue(heap.valueAt(parent)<=heap.valueAt(i));
        }
    }
}
