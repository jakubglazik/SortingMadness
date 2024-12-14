package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The SelectionSort class implements the Selection Sort algorithm.
 * This algorithm sorts a list by repeatedly selecting the smallest (or largest,
 * depending on the order) element from the unsorted portion of the list
 * and swapping it with the first element in the unsorted portion.
 *
 * This implementation supports sorting in both ascending and descending order.
 */
public class SelectionSort implements SortingInterface {
    private String name;

    /**
     * Default constructor that sets the name of the sorting algorithm to "SelectionSort".
     */
    public SelectionSort() {
        this.name = "SelectionSort";
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
     * Sorts the given list of data using the Selection Sort algorithm.
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
        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;
            for (int j = i + 1; j < n; j++) {
                int comparison = comparator.compare(data.get(j), data.get(selectedIdx));
                if ((descOrder && comparison > 0) || (!descOrder && comparison < 0)) {
                    selectedIdx = j;
                }
            }
            if (selectedIdx != i) {
                T temp = data.get(selectedIdx);
                data.set(selectedIdx, data.get(i));
                data.set(i, temp);
            }
        }
        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        return new SortResult<>(data, duration);
    }
}
