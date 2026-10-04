package structures;

import metrics.Metrics;

public class MyLinkedList {
    private static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value=value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics=new Metrics();

    public void add(int x) {
        Node node=new Node(x);
        if(head==null) {
            head=node;
            tail=node;
            metrics.moves+=2;
        } else {
            tail.next=node;
            tail=node;
            metrics.moves+=2;
        }
        size++;
    }

    public void add(int index,int x) {
        if(index<0||index>size) throw new IndexOutOfBoundsException();
        if(index==size) {
            add(x);
            return;
        }
        Node node=new Node(x);
        if(index==0) {
            node.next=head;
            head=node;
            metrics.moves+=2;
            if(size==0) {
                tail=node;
                metrics.moves++;
            }
        } else {
            Node prev=nodeAt(index-1);
            node.next=prev.next;
            prev.next=node;
            metrics.moves+=2;
        }
        size++;
    }

    public int remove(int index) {
        checkIndex(index);
        if(index==0) {
            int value=head.value;
            metrics.steps++;
            head=head.next;
            metrics.moves++;
            size--;
            if(size==0) {
                tail=null;
                metrics.moves++;
            }
            return value;
        }
        Node prev=nodeAt(index-1);
        Node target=prev.next;
        metrics.steps++;
        prev.next=target.next;
        metrics.moves++;
        if(index==size-1) {
            tail=prev;
            metrics.moves++;
        }
        size--;
        return target.value;
    }

    public int get(int index) {
        checkIndex(index);
        return nodeAt(index).value;
    }

    public boolean contains(int x) {
        Node cur=head;
        while(cur!=null) {
            metrics.steps++;
            metrics.comparisons++;
            if(cur.value==x) return true;
            cur=cur.next;
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

    private Node nodeAt(int index) {
        Node cur=head;
        for(int i=0;i<index;i++) {
            cur=cur.next;
            metrics.steps++;
        }
        metrics.steps++;
        return cur;
    }

    private void checkIndex(int index) {
        if(index<0||index>=size) throw new IndexOutOfBoundsException();
    }
}
