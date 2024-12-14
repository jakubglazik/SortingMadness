package pl.put.poznan.sortingMadness.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingMadness.logic.SortingInterface;
import pl.put.poznan.sortingMadness.logic.SortingStrategyFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sort")
public class SortController {

    private final SortingStrategyFactory strategyFactory;

    // Dependency injection via constructor
    @Autowired
    public SortController(SortingStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
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

        SortingInterface sorter = strategyFactory.getSorter(request.getAlgorithmName());

        if (fieldName != null && !fieldName.isEmpty()) {
            return sortByField(sorter, rawData, fieldName, descending);
        } else {
            return sortByValue(sorter, rawData, descending);
        }
    }

    private List<Object> sortByValue(SortingInterface sorter, List<Object> rawData, boolean descending) {
        if (rawData == null || rawData.isEmpty()) {
            return new ArrayList<>();
        }

        Comparator<Object> comparator = getComparator(rawData);

        ArrayList<Object> sortableList = new ArrayList<>(rawData);

        ArrayList<Object> sortedList = sorter.sort(sortableList, comparator, descending);

        return new ArrayList<>(sortedList);
    }

    private List<Object> sortByField(SortingInterface sorter, List<Object> rawData, String fieldName, boolean descending) {
        // Check and convert rawData to ArrayList<Map<String, Object>>
        ArrayList<Map<String, Object>> sortableList = new ArrayList<>();
        for (Object obj : rawData) {
            if (!(obj instanceof Map)) {
                throw new IllegalArgumentException("Input data contains elements that are not Map<String, Object>.");
            }
            sortableList.add((Map<String, Object>) obj);
        }

        Comparator<Map<String, Object>> comparator = (map1, map2) -> {
            Object value1 = map1.get(fieldName);
            Object value2 = map2.get(fieldName);

            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Field '" + fieldName + "' contains null values.");
            }

            if (!(value1 instanceof Comparable) || !(value2 instanceof Comparable)) {
                throw new IllegalArgumentException("Field '" + fieldName + "' does not implement Comparable.");
            }

            return ((Comparable<Object>) value1).compareTo(value2);
        };

        ArrayList<Map<String, Object>> sortedList = sorter.sort(sortableList, comparator, descending);

        return new ArrayList<>(sortedList);
    }

    private static <T> Comparator<T> getComparator(List<T> data) {
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
