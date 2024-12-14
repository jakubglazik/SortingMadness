package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The SortingInterface defines a contract for all sorting algorithms in the system.
 * Implementing classes must provide a sorting algorithm implementation
 * that works with a given comparator and supports both ascending and descending order.
 */
public interface SortingInterface {
    /**
     * Name of the sorting algorithm. This field can be used to identify the algorithm type.
     */
    String name = "";

    /**
     * Gets the name of the sorting algorithm.
     *
     * @return the name of the sorting algorithm
     */
    String getName();

    /**
     * Sets the name of the sorting algorithm.
     *
     * @param name the name to be set for the sorting algorithm
     */
    void setName(String name);

    /**
     * Sorts the given list of data using the specified comparator and order.
     * The method sorts the data either in ascending or descending order
     * based on the value of the {@code descOrder} parameter.
     *
     * @param data       the list of elements to be sorted
     * @param comparator the comparator defining the sorting order
     * @param descOrder  if {@code true}, sorts the data in descending order; otherwise, in ascending order
     * @param <T>        the type of elements in the list
     * @return a {@link SortResult} containing the sorted data and the time taken for sorting
     */
    <T> SortResult<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder);
}
