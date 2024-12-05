package pl.put.poznan.sortingMadness.rest;

import java.util.List;

public class SortRequest {
    private List<Integer> data;

    // Getter i Setter
    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
