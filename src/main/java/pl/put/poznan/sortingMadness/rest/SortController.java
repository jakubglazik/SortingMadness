package pl.put.poznan.sortingMadness.rest;

import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingMadness.logic.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sort")
public class SortController {
    private SortingInterface sorter;

    // Method to set the sorter based on the algorithm name
    public void setSorter(String name) {
        switch (name) {
            case "BubbleSort":
                this.sorter = new BubbleSort();
                break;
            case "HeapSort":
                this.sorter = new HeapSort();
                break;
            case "InsertionSort":
                this.sorter = new InsertionSort();
                break;
            case "MergeSort":
                this.sorter = new MergeSort();
                break;
            case "QuickSort":
                this.sorter = new QuickSort();
                break;
            case "SelectionSort":
                this.sorter = new SelectionSort();
                break;
            default:
                throw new IllegalArgumentException("No such sorting algorithm available: " + name);
        }
    }
    @SuppressWarnings("unchecked")
    @PostMapping
    public List<Object> sort(@RequestBody SortRequest request) {
        List<Object> rawData = request.getData();
        boolean descending = request.isDescending();

        List comparableData = new ArrayList();
        for (Object obj : rawData) {
            if (!(obj instanceof Comparable)) {
                throw new IllegalArgumentException("Data contains non-comparable elements: " + obj);
            }
            comparableData.add(obj);
        }

        setSorter(request.getAlgorithmName());

        ArrayList sortedData = sorter.sort(new ArrayList<>(comparableData), descending);

        return new ArrayList<>(sortedData);
    }
}
