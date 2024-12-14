package pl.put.poznan.sortingMadness.rest;

import java.util.List;

public class SortRequest {
    private List<Object> data; // Accepts raw objects
    private List<String> algorithmNames;
    private boolean descending;
    private String fieldName;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<String> getAlgorithmNames() {
        return algorithmNames;
    }

    public void setAlgorithmNames(List<String> algorithmNames) {
        this.algorithmNames = algorithmNames;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
