package structures;

import metrics.Metrics;

public class MinHeap {
    private int[] data=new int[4];
    private int size;
    private final Metrics metrics=new Metrics();

    public void insert(int x) {
        ensureCapacity();
        data[size]=x;
        metrics.moves++;
        int i=size;
        size++;
        while(i>0) {
            int parent=(i-1)/2;
            metrics.steps+=2;
            metrics.comparisons++;
            if(data[parent]<=data[i]) break;
            int temp=data[parent];
            data[parent]=data[i];
            data[i]=temp;
            metrics.moves+=3;
            i=parent;
        }
    }

    public int peekMin() {
        if(size==0) throw new IllegalStateException();
        metrics.steps++;
        return data[0];
    }

    public int extractMin() {
        if(size==0) throw new IllegalStateException();
        int result=data[0];
        metrics.steps++;
        size--;
        if(size>0) {
            data[0]=data[size];
            metrics.steps++;
            metrics.moves++;
            int i=0;
            while(true) {
                int left=2*i+1;
                int right=left+1;
                if(left>=size) break;
                int smallest=left;
                if(right<size) {
                    metrics.steps+=2;
                    metrics.comparisons++;
                    if(data[right]<data[left]) smallest=right;
                }
                metrics.steps+=2;
                metrics.comparisons++;
                if(data[i]<=data[smallest]) break;
                int temp=data[i];
                data[i]=data[smallest];
                data[smallest]=temp;
                metrics.moves+=3;
                i=smallest;
            }
        }
        return result;
    }

    public int size() {
        return size;
    }

    public int valueAt(int index) {
        if(index<0||index>=size) throw new IndexOutOfBoundsException();
        return data[index];
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
}
