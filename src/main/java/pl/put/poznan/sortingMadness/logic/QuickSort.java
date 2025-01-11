package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The QuickSort class implements the Quick Sort algorithm.
 * This algorithm is a highly efficient, divide-and-conquer sorting method.
 * It works by selecting a 'pivot' element, partitioning the array into two subarrays
 * (elements less than or equal to the pivot and elements greater than the pivot),
 * and then recursively sorting the subarrays.
 *
 * This implementation supports sorting in both ascending and descending order.
 */
public class QuickSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;

    /**
     * Default constructor that sets the name of the sorting algorithm to "QuickSort".
     */
    public QuickSort() {
        this.name = "QuickSort";
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
     * Sorts the given list of data using the Quick Sort algorithm.
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
        quickSort(data, 0, data.size() - 1, comparator, descOrder);
        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        return new SortResult<>(data, duration);
    }

    /**
     * Recursively sorts the list using the Quick Sort algorithm.
     *
     * @param data       the list to be sorted
     * @param low        the starting index of the sublist
     * @param high       the ending index of the sublist
     * @param comparator the comparator defining the sorting order
     * @param descOrder  if {@code true}, sorts the data in descending order; otherwise, in ascending order
     * @param <T>        the type of elements in the list
     */
    private <T> void quickSort(ArrayList<T> data, int low, int high, Comparator<? super T> comparator, boolean descOrder) {
        if (low < high) {
            if (currentIterations >= iterationLimit) {
                return;
            }

            currentIterations++;
            int pivotIndex = partition(data, low, high, comparator, descOrder);
            quickSort(data, low, pivotIndex - 1, comparator, descOrder);
            quickSort(data, pivotIndex + 1, high, comparator, descOrder);
        }
    }

    /**
     * Partitions the list into two parts based on the pivot element.
     *
     * @param data       the list to be partitioned
     * @param low        the starting index of the sublist
     * @param high       the ending index of the sublist
     * @param comparator the comparator defining the sorting order
     * @param descOrder  if {@code true}, sorts the data in descending order; otherwise, in ascending order
     * @param <T>        the type of elements in the list
     * @return the index of the pivot element after partitioning
     */
    private <T> int partition(ArrayList<T> data, int low, int high, Comparator<? super T> comparator, boolean descOrder) {
        T pivot = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (currentIterations >= iterationLimit) {
                return j;
            }
            int comparison = comparator.compare(data.get(j), pivot);
            if ((descOrder && comparison > 0) || (!descOrder && comparison <= 0)) {
                i++;
                swap(data, i, j);
            }
            currentIterations++;
        }
        swap(data, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in the list.
     *
     * @param data the list of elements
     * @param i    the index of the first element
     * @param j    the index of the second element
     * @param <T>  the type of elements in the list
     */
    private <T> void swap(ArrayList<T> data, int i, int j) {
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
