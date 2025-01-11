package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The MergeSort class implements the Merge Sort algorithm.
 * This algorithm is a divide-and-conquer sorting algorithm that divides the input list into two halves,
 * sorts each half recursively, and then merges the sorted halves back together.
 *
 * This implementation supports sorting in both ascending and descending order.
 */
public class MergeSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;

    /**
     * Default constructor that sets the name of the sorting algorithm to "MergeSort".
     */
    public MergeSort() {
        this.name = "MergeSort";
        this.iterationLimit = Integer.MAX_VALUE;
        this.currentIterations = 0;
    }

    /**
     * Sets the maximum number of iterations the algorithm is allowed to perform.
     *
     * @param iterationLimit the maximum number of iterations
     */
    @Override
    public void setIterationLimit(int iterationLimit) {
        this.iterationLimit = iterationLimit;
    }

    /**
     * Returns the maximum number of iterations the algorithm is allowed to perform.
     *
     * @return the iteration limit
     */
    @Override
    public int getIterationLimit() {
        return this.iterationLimit;
    }

    /**
     * Returns the name of the sorting algorithm.
     *
     * @return the name of the sorting algorithm
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the sorting algorithm.
     *
     * @param name the name to be set for the sorting algorithm
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sorts the given list of data using the Merge Sort algorithm.
     * The method sorts the data either in ascending or descending order
     * based on the provided {@code descOrder} parameter.
     *
     * @param data       the list of elements to be sorted
     * @param comparator the comparator defining the sorting order
     * @param descOrder  if {@code true}, sorts the data in descending order; otherwise, in ascending order
     * @param <T>        the type of elements in the list
     * @return a {@link SortResult} containing the sorted data and the time taken for sorting (in nanoseconds)
     */
    @Override
    public <T> SortResult<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder) {
        long startTime = System.nanoTime();

        if (data.size() > 1) {
            int mid = data.size() / 2;
            ArrayList<T> left = new ArrayList<>(data.subList(0, mid));
            ArrayList<T> right = new ArrayList<>(data.subList(mid, data.size()));

            sort(left, comparator, descOrder);
            sort(right, comparator, descOrder);

            merge(data, left, right, comparator, descOrder);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        return new SortResult<>(data, duration);
    }

    /**
     * Merges two sorted sublists into a single sorted list.
     *
     * @param data       the original list to be updated with merged values
     * @param left       the left sublist
     * @param right      the right sublist
     * @param comparator the comparator defining the sorting order
     * @param descOrder  if {@code true}, sorts the data in descending order; otherwise, in ascending order
     * @param <T>        the type of elements in the list
     */
    private <T> void merge(ArrayList<T> data, ArrayList<T> left, ArrayList<T> right, Comparator<? super T> comparator, boolean descOrder) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (currentIterations >= iterationLimit) {
                while (i < left.size()) {
                    data.set(k++, left.get(i++));
                }
                while (j < right.size()) {
                    data.set(k++, right.get(j++));
                }
                return;
            }
            int comparison = comparator.compare(left.get(i), right.get(j));
            if ((descOrder && comparison > 0) || (!descOrder && comparison <= 0)) {
                data.set(k++, left.get(i++));
            } else {
                data.set(k++, right.get(j++));
            }
            currentIterations++;
        }
        while (i < left.size()) {
            data.set(k++, left.get(i++));
            currentIterations++;
            if (currentIterations >= iterationLimit) {
                break;
            }
        }

        while (j < right.size()) {
            data.set(k++, right.get(j++));
            currentIterations++;
            if (currentIterations >= iterationLimit) {
                break;
            }
        }
    }
}
