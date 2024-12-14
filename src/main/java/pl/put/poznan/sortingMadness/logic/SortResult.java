package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;

public class SortResult<T> {
    private final ArrayList<T> sortedData;
    private final long executionTime;

    public SortResult(ArrayList<T> sortedData, long executionTime) {
        this.sortedData = sortedData;
        this.executionTime = executionTime;
    }

    public ArrayList<T> getSortedData() {
        return sortedData;
    }

    public long getExecutionTime() {
        return executionTime;
    }

}
