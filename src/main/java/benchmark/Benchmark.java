package benchmark;

import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;
import metrics.Metrics;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES={100,1000,10000,100000};

    public static void main(String[] args) throws Exception {
        File dir=new File("results");
        dir.mkdirs();
        try(PrintWriter out=new PrintWriter(new File(dir,"results.csv"))) {
            out.println("workload,variant,structure,n,time_ms,steps,moves,comparisons");
            for(int n:SIZES) runW1(n,out);
            for(int n:SIZES) runW2(n,out);
            for(int n:SIZES) runW3(n,"head",out);
            for(int n:SIZES) runW3(n,"middle",out);
            for(int n:SIZES) runW4(n,out);
        }
        System.out.println("results/results.csv created");
    }

    private static void runW1(int n,PrintWriter out) {
        int[] values=randomValues(n);
        int[] indexes=new int[10000];
        Random r=new Random(42+n);
        for(int i=0;i<indexes.length;i++) indexes[i]=r.nextInt(n);
        Result a=measureArrayGet(values,indexes);
        Result l=measureListGet(values,indexes);
        write(out,"W1","-","DynamicArray",n,a);
        write(out,"W1","-","MyLinkedList",n,l);
    }

    private static void runW2(int n,PrintWriter out) {
        int[] values=randomValues(n);
        int[] queries=new int[1000];
        for(int i=0;i<500;i++) queries[i]=values[(i*37)%n];
        for(int i=500;i<1000;i++) queries[i]=Integer.MIN_VALUE+i;
        Result a=measureArrayContains(values,queries);
        Result l=measureListContains(values,queries);
        write(out,"W2","-","DynamicArray",n,a);
        write(out,"W2","-","MyLinkedList",n,l);
    }

    private static void runW3(int n,String variant,PrintWriter out) {
        int[] values=randomValues(n);
        Result a=measureArrayChange(values,variant);
        Result l=measureListChange(values,variant);
        write(out,"W3",variant,"DynamicArray",n,a);
        write(out,"W3",variant,"MyLinkedList",n,l);
    }

    private static void runW4(int n,PrintWriter out) {
        int[] values=randomValues(n);
        Result h=measureHeap(values);
        write(out,"W4","-","MinHeap",n,h);
    }

    private static Result measureArrayGet(int[] values,int[] indexes) {
        runArrayGet(values,indexes);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            DynamicArray a=fillArray(values);
            a.resetMetrics();
            long start=System.nanoTime();
            int sum=0;
            for(int x:indexes) sum+=a.get(x);
            times[k]=System.nanoTime()-start;
            if(sum==123456789) System.out.print("");
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureListGet(int[] values,int[] indexes) {
        runListGet(values,indexes);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            MyLinkedList a=fillList(values);
            a.resetMetrics();
            long start=System.nanoTime();
            int sum=0;
            for(int x:indexes) sum+=a.get(x);
            times[k]=System.nanoTime()-start;
            if(sum==123456789) System.out.print("");
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureArrayContains(int[] values,int[] queries) {
        runArrayContains(values,queries);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            DynamicArray a=fillArray(values);
            a.resetMetrics();
            long start=System.nanoTime();
            int found=0;
            for(int x:queries) if(a.contains(x)) found++;
            times[k]=System.nanoTime()-start;
            if(found<0) System.out.print("");
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureListContains(int[] values,int[] queries) {
        runListContains(values,queries);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            MyLinkedList a=fillList(values);
            a.resetMetrics();
            long start=System.nanoTime();
            int found=0;
            for(int x:queries) if(a.contains(x)) found++;
            times[k]=System.nanoTime()-start;
            if(found<0) System.out.print("");
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureArrayChange(int[] values,String variant) {
        runArrayChange(values,variant);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            DynamicArray a=fillArray(values);
            a.resetMetrics();
            long start=System.nanoTime();
            for(int i=0;i<1000;i++) {
                int index=variant.equals("head")?0:values.length/2;
                a.add(index,i);
            }
            for(int i=0;i<1000;i++) {
                int index=variant.equals("head")?0:values.length/2;
                a.remove(index);
            }
            times[k]=System.nanoTime()-start;
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureListChange(int[] values,String variant) {
        runListChange(values,variant);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            MyLinkedList a=fillList(values);
            a.resetMetrics();
            long start=System.nanoTime();
            for(int i=0;i<1000;i++) {
                int index=variant.equals("head")?0:values.length/2;
                a.add(index,i);
            }
            for(int i=0;i<1000;i++) {
                int index=variant.equals("head")?0:values.length/2;
                a.remove(index);
            }
            times[k]=System.nanoTime()-start;
            m=copy(a.getMetrics());
        }
        return result(times,m);
    }

    private static Result measureHeap(int[] values) {
        runHeap(values);
        long[] times=new long[5];
        Metrics m=null;
        for(int k=0;k<5;k++) {
            MinHeap h=new MinHeap();
            h.resetMetrics();
            long start=System.nanoTime();
            for(int x:values) h.insert(x);
            int prev=Integer.MIN_VALUE;
            while(h.size()>0) {
                int x=h.extractMin();
                if(x<prev) throw new IllegalStateException("heap output is not sorted");
                prev=x;
            }
            times[k]=System.nanoTime()-start;
            m=copy(h.getMetrics());
        }
        return result(times,m);
    }

    private static void runArrayGet(int[] values,int[] indexes) {
        DynamicArray a=fillArray(values);
        for(int x:indexes) a.get(x);
    }

    private static void runListGet(int[] values,int[] indexes) {
        MyLinkedList a=fillList(values);
        for(int x:indexes) a.get(x);
    }

    private static void runArrayContains(int[] values,int[] queries) {
        DynamicArray a=fillArray(values);
        for(int x:queries) a.contains(x);
    }

    private static void runListContains(int[] values,int[] queries) {
        MyLinkedList a=fillList(values);
        for(int x:queries) a.contains(x);
    }

    private static void runArrayChange(int[] values,String variant) {
        DynamicArray a=fillArray(values);
        for(int i=0;i<1000;i++) a.add(variant.equals("head")?0:values.length/2,i);
        for(int i=0;i<1000;i++) a.remove(variant.equals("head")?0:values.length/2);
    }

    private static void runListChange(int[] values,String variant) {
        MyLinkedList a=fillList(values);
        for(int i=0;i<1000;i++) a.add(variant.equals("head")?0:values.length/2,i);
        for(int i=0;i<1000;i++) a.remove(variant.equals("head")?0:values.length/2);
    }

    private static void runHeap(int[] values) {
        MinHeap h=new MinHeap();
        for(int x:values) h.insert(x);
        while(h.size()>0) h.extractMin();
    }

    private static DynamicArray fillArray(int[] values) {
        DynamicArray a=new DynamicArray();
        for(int x:values) a.add(x);
        return a;
    }

    private static MyLinkedList fillList(int[] values) {
        MyLinkedList a=new MyLinkedList();
        for(int x:values) a.add(x);
        return a;
    }

    private static int[] randomValues(int n) {
        int[] values=new int[n];
        Random r=new Random(42);
        for(int i=0;i<n;i++) values[i]=r.nextInt(1000000);
        return values;
    }

    private static Result result(long[] times,Metrics m) {
        Arrays.sort(times);
        return new Result(times[2]/1000000.0,m.steps,m.moves,m.comparisons);
    }

    private static Metrics copy(Metrics m) {
        Metrics x=new Metrics();
        x.steps=m.steps;
        x.moves=m.moves;
        x.comparisons=m.comparisons;
        return x;
    }

    private static void write(PrintWriter out,String workload,String variant,String structure,int n,Result r) {
        out.printf("%s,%s,%s,%d,%.6f,%d,%d,%d%n",workload,variant,structure,n,r.time,r.steps,r.moves,r.comparisons);
    }

    private record Result(double time,long steps,long moves,long comparisons) {}
}
