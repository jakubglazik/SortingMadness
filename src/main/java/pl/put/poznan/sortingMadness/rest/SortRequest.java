package pl.put.poznan.sortingMadness.rest;

import java.util.List;

/**
 * Represents the request object for sorting operations.
 * This class encapsulates the input data, the list of sorting algorithms to test,
 * and additional parameters such as sorting order and field for comparison.
 */
public class SortRequest {

    /**
     * The list of data objects to be sorted.
     * Each object can either be a raw object or a map containing fields for comparison.
     */
    private List<Object> data;

    private int iterationLimit=Integer.MAX_VALUE;
    /**
     * The list of algorithm names to be tested for sorting the given data.
     * Each algorithm name should correspond to an available sorting implementation.
     */
    private List<String> algorithmNames;

    /**
     * Indicates whether the sorting should be in descending order.
     * If {@code true}, the sorting is performed in descending order; otherwise, ascending.
     */
    private boolean descending;

    /**
     * The name of the field to be used for sorting, applicable for objects
     * that are maps or have specific fields.
     * If {@code null} or empty, sorting is performed based on the entire object.
     */
    private String fieldName;

    /**
     * Retrieves the list of data objects to be sorted.
     *
     * @return the list of data objects
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * Sets the list of data objects to be sorted.
     *
     * @param data the list of data objects
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

    /**
     * Retrieves the list of algorithm names to be tested for sorting.
     *
     * @return the list of algorithm names
     */
    public List<String> getAlgorithmNames() {
        return algorithmNames;
    }

    /**
     * Sets the list of algorithm names to be tested for sorting.
     *
     * @param algorithmNames the list of algorithm names
     */
    public void setAlgorithmNames(List<String> algorithmNames) {
        this.algorithmNames = algorithmNames;
    }

    /**
     * Checks if the sorting should be in descending order.
     *
     * @return {@code true} if sorting is in descending order; {@code false} otherwise
     */
    public boolean isDescending() {
        return descending;
    }

    /**
     * Sets whether the sorting should be in descending order.
     *
     * @param descending {@code true} for descending order; {@code false} for ascending order
     */
    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    /**
     * Retrieves the name of the field to be used for sorting.
     *
     * @return the field name used for sorting, or {@code null} if sorting is based on entire objects
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the name of the field to be used for sorting.
     * If not set or empty, sorting will be performed based on the entire object.
     *
     * @param fieldName the field name for sorting
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public void setIterationLimit(int iterationLimit){
        this.iterationLimit = iterationLimit;
    }
    public int getIterationLimit(){
        return iterationLimit;
    }
}
