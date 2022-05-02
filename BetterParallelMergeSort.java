package pgdp.threads;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class BetterParallelMergeSort extends RecursiveAction {
    private final Comparable[] array;
    private final Comparable[] helper;
    private final int low;
    private final int high;

    public BetterParallelMergeSort(Comparable[] array) {
        helper = new Comparable[array.length];
        this.array = array;
        this.low = 0;
        this.high = array.length - 1; //initialize values for first time
    }

    public BetterParallelMergeSort(final Comparable[] array, final Comparable[] helper, final int low, final int high) {
        this.array = array;
        this.helper = helper;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            if (high - low <= 32768) {  //create limit to make code execution faster
                MergeSort.mergesort(array, helper, low, high);
            } else {
                int middle = (low + high) / 2;
                invokeAll(new BetterParallelMergeSort(array,helper, low, middle),
                        new BetterParallelMergeSort(array,helper, middle + 1, high));
                MergeSort.merge(array, helper, low, middle, high);
            }
        }
    }
}

