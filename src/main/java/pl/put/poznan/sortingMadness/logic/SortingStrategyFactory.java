package pl.put.poznan.sortingMadness.logic;

import org.springframework.stereotype.Component;

@Component
public class SortingStrategyFactory {
    public SortingInterface getSorter(String name) {
        switch (name) {
            case "BubbleSort":
                return new BubbleSort();
            case "HeapSort":
                return new HeapSort();
            case "InsertionSort":
                return new InsertionSort();
            case "MergeSort":
                return new MergeSort();
            case "QuickSort":
                return new QuickSort();
            case "SelectionSort":
                return new SelectionSort();
            default:
                throw new IllegalArgumentException("No such sorting algorithm available: " + name);
        }
    }
}
