package pgdp.threads;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
    private final Comparable[] array;
    private final Comparable[] helper;
    private final int low;
    private final int high;

    public ParallelMergeSort(Comparable[] array) {
        helper = new Comparable[array.length];
        this.high = array.length - 1;
        this.low = 0;
        this.array = array;
    }

    public ParallelMergeSort(final Comparable[] array, final Comparable[] helper, final int low, final int high) {
        this.array = array;
        this.helper = helper;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) { //if low is more than high, don't execute program. (base case)
            int middle = (low + high) / 2;
            invokeAll(new ParallelMergeSort(array, helper, low,  middle), //create new mergesort and invoke them with threads(doing operations parallel, not step by step)
                    new ParallelMergeSort(array, helper, middle + 1, high));
            MergeSort.merge(array, helper, low, middle, high);  //call the method merge to merge given arrays
        }
    }
}

