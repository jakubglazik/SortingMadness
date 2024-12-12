package pl.put.poznan.sortingMadness.rest;

import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingMadness.logic.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        String fieldName = request.getFieldName();

        if (rawData.isEmpty()) {
            return new ArrayList<>();
        }

        setSorter(request.getAlgorithmName());

        if (fieldName != null && !fieldName.isEmpty()) {
            return sortByField(rawData, fieldName, descending);
        } else {
            return sortByValue(rawData, descending);
        }
    }


    public List<Object> sortByValue(List<Object> rawData, boolean descending) {
        if (rawData == null || rawData.isEmpty()) {
            return new ArrayList<>();
        }

        Comparator<Object> comparator = getComparator(rawData);

        ArrayList<Object> sortableList = new ArrayList<>(rawData);

        ArrayList<Object> sortedList = sorter.sort(sortableList, comparator, descending);

        return new ArrayList<>(sortedList);
    }


    public List<Object> sortByField(List<Object> rawData, String fieldName, boolean descending) {
        // Sprawdzenie i przekształcenie rawData do ArrayList<Map<String, Object>>
        ArrayList<Map<String, Object>> sortableList = new ArrayList<>();
        for (Object obj : rawData) {
            if (!(obj instanceof Map)) {
                throw new IllegalArgumentException("Dane wejściowe zawierają elementy inne niż Map<String, Object>.");
            }
            sortableList.add((Map<String, Object>) obj);
        }

        Comparator<Map<String, Object>> comparator = (map1, map2) -> {
            Object value1 = map1.get(fieldName);
            Object value2 = map2.get(fieldName);

            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Pole '" + fieldName + "' zawiera wartość null.");
            }

            if (!(value1 instanceof Comparable) || !(value2 instanceof Comparable)) {
                throw new IllegalArgumentException("Pole '" + fieldName + "' nie implementuje interfejsu Comparable.");
            }

            return ((Comparable<Object>) value1).compareTo(value2);
        };
        
        ArrayList<Map<String, Object>> sortedList = sorter.sort(sortableList, comparator, descending);

        return new ArrayList<>(sortedList);
    }

    public static <T> Comparator<T> getComparator(List<T> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("The data list is null or empty.");
        }
        T firstElement = data.get(0);
        if (firstElement instanceof Integer) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof String) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Double) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Comparable) {
            return (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + firstElement.getClass().getName());
        }
    }
}
