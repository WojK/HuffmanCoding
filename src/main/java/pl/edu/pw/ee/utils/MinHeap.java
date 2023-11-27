package pl.edu.pw.ee.utils;

import java.util.ArrayList;
import java.util.List;

public class MinHeap<T extends Comparable<T>> {

    private List<T> list;

    public MinHeap() {
        this.list = new ArrayList<>();

    }

    public MinHeap(T[] array) {
        this.list = new ArrayList<>();
        for (T t : array) {
            put(t);
        }

    }

    public void put(T item) {
    
        if(item == null){
            throw new IllegalArgumentException("Null elem");
        }

        list.add(item);
        int n = list.size();
        if (n > 1) {
            int maxIndex = n - 1;
            heapUp(maxIndex);
        }

    }

    private void heapUp(int maxIndex) {
        if (maxIndex == 0) {
            return;
        }

        int parentIndex = (maxIndex + 1) / 2 - 1;
        if (list.get(maxIndex).compareTo(list.get(parentIndex)) < 0) {
            swap(parentIndex, maxIndex);
            heapUp(parentIndex);
        }
    }

    private void swap(int i, int j) {

        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);

    }

    public T pop() {
        int n = list.size();

        if (n == 0) {
            throw new ArrayIndexOutOfBoundsException("Heap is empty");
        }

        T ret = list.get(0);
        swap(0, n - 1);
        list.remove(n - 1);
        heapDown(0);

        return ret;
    }

    private void heapDown(int parentIndex) {

        int leftChild = parentIndex * 2 + 1;
        int rightChild = parentIndex * 2 + 2;

        if (rightChild < list.size()) {
            T parentValue = list.get(parentIndex);
            T rightChildValue = list.get(rightChild);
            T leftChildValue = list.get(leftChild);

            if (rightChildValue.compareTo(leftChildValue) < 0 && rightChildValue.compareTo(parentValue) < 0) {
                swap(rightChild, parentIndex);
                heapDown(rightChild);
                return;
            } else if (leftChildValue.compareTo(parentValue) < 0) {
                swap(leftChild, parentIndex);
                heapDown(leftChild);
                return;
            }
        }

        if (leftChild < list.size()) {
            T parentValue = list.get(parentIndex);
            T leftChildValue = list.get(leftChild);
            if (leftChildValue.compareTo(parentValue) < 0) {
                swap(leftChild, parentIndex);
                heapDown(leftChild);
                return;
            }
        }
        
    }

    public int getSize(){
        return list.size();
    }

}
