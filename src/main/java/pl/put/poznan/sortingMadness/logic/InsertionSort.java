package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The InsertionSort class implements the Insertion Sort algorithm.
 * This algorithm sorts a list by iterating through the elements,
 * taking one element at a time, and inserting it into its correct position
 * relative to the already sorted portion of the list.
 *
 * This implementation supports sorting in both ascending and descending order.
 */
public class InsertionSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;

    /**
     * Default constructor that sets the name of the sorting algorithm to "InsertionSort".
     */
    public InsertionSort() {
        this.name = "InsertionSort";
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
     * Sorts the given list of data using the Insertion Sort algorithm.
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
        int n = data.size();
        for (int i = 1; i < n; i++) {
            if (currentIterations >= iterationLimit) {
                break;
            }
            T key = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                int comparison = comparator.compare(data.get(j), key);
                if ((descOrder && comparison < 0) || (!descOrder && comparison > 0)) {
                    data.set(j + 1, data.get(j));
                    j--;
                } else {
                    break;
                }
            }
            currentIterations++;
            data.set(j + 1, key);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        return new SortResult<>(data, duration);
    }
}
