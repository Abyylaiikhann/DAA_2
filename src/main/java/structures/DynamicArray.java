package structures;

import metrics.Metrics;

public class DynamicArray {
    private int[] data;
    private int size;
    private final Metrics metrics=new Metrics();

    public DynamicArray() {
        data=new int[4];
    }

    public void add(int x) {
        ensureCapacity();
        data[size]=x;
        metrics.moves++;
        size++;
    }

    public void add(int index,int x) {
        if(index<0||index>size) throw new IndexOutOfBoundsException();
        ensureCapacity();
        for(int i=size;i>index;i--) {
            data[i]=data[i-1];
            metrics.steps++;
            metrics.moves++;
        }
        data[index]=x;
        metrics.moves++;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);
        int value=data[index];
        metrics.steps++;
        for(int i=index;i<size-1;i++) {
            data[i]=data[i+1];
            metrics.steps++;
            metrics.moves++;
        }
        size--;
        return value;
    }

    public int get(int index) {
        checkIndex(index);
        metrics.steps++;
        return data[index];
    }

    public boolean contains(int x) {
        for(int i=0;i<size;i++) {
            metrics.steps++;
            metrics.comparisons++;
            if(data[i]==x) return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }

    private void ensureCapacity() {
        if(size==data.length) {
            int[] newData=new int[data.length*2];
            for(int i=0;i<size;i++) {
                newData[i]=data[i];
                metrics.steps++;
                metrics.moves++;
            }
            data=newData;
        }
    }

    private void checkIndex(int index) {
        if(index<0||index>=size) throw new IndexOutOfBoundsException();
    }
}
