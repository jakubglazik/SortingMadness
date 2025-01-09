package pl.put.poznan.sortingMadness.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingMadness.logic.SortingInterface;
import pl.put.poznan.sortingMadness.logic.SortingStrategyFactory;
import pl.put.poznan.sortingMadness.logic.SortResult;

import java.util.*;

@RestController
@RequestMapping("/api/sort")
public class SortController {

    private final SortingStrategyFactory strategyFactory;

    @Autowired
    public SortController(SortingStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }


    @SuppressWarnings("unchecked")
    @PostMapping
    public Map<String, SortResult<Object>> sort(@RequestBody SortRequest request) {
        List<Object> rawData = request.getData();
        List<String> algorithmNames = request.getAlgorithmNames();
        boolean descending = request.isDescending();
        String fieldName = request.getFieldName();
        int maxIterations = request.getIterationLimit();
        if (rawData.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be empty.");
        }

//        if (algorithmNames.isEmpty()) {
//            throw new IllegalArgumentException("Algorithm names cannot be empty.");
//        }
        if (algorithmNames.isEmpty()) {
            algorithmNames = selectBestAlgorithms(rawData);
        }

        Map<String, SortResult<Object>> results = new HashMap<>();

        for (String algorithmName : algorithmNames) {
            SortingInterface sorter = strategyFactory.getSorter(algorithmName);
            sorter.setIterationLimit(maxIterations);
            SortResult<Object> result;
            if (fieldName != null && !fieldName.isEmpty()) {
                result = sortByField(sorter, rawData, fieldName, descending);
            } else {
                result = sortByValue(sorter, rawData, descending);
            }

            results.put(algorithmName, result);
        }

        return results;
    }

    private SortResult<Object> sortByValue(SortingInterface sorter, List<Object> rawData, boolean descending) {
        Comparator<Object> comparator = getComparator(rawData);

        ArrayList<Object> sortableList = new ArrayList<>(rawData);

        return sorter.sort(sortableList, comparator, descending);
    }

    private SortResult<Object> sortByField(SortingInterface sorter, List<Object> rawData, String fieldName, boolean descending) {
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

        SortResult<Map<String, Object>> sortResult = sorter.sort(sortableList, comparator, descending);
        return new SortResult<>(new ArrayList<>(sortResult.getSortedData()), sortResult.getExecutionTime());
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

    public List<String> selectBestAlgorithms(List<Object> data) {
        int dataSize = data.size();
        boolean isPartiallySorted = isPartiallySorted(data);
        int uniqueValuesCount = countUniqueValues(data);

        if (dataSize < 20) {
            if (isPartiallySorted) {
                return List.of("InsertionSort");
            } else {
                return List.of("SelectionSort");
            }
        } else if (dataSize < 1000) {
            if (uniqueValuesCount < dataSize / 2) {
                return List.of("HeapSort");
            } else if (isPartiallySorted) {
                return List.of("InsertionSort");
            } else {
                return List.of("QuickSort");
            }
        } else {
            if (uniqueValuesCount < dataSize / 2) {
                return List.of("HeapSort");
            } else {
                return List.of("MergeSort");
            }
        }
    }

    private boolean isPartiallySorted(List<Object> data) {
        int sortedCount = 0;
        for (int i = 1; i < data.size(); i++) {
            Object value1 = data.get(i - 1);
            Object value2 = data.get(i);

            if (value1 instanceof Map) {
                value1 = ((Map<String, Object>) value1).values().toArray()[0];  // Bierzemy pierwszą wartość z mapy
            }
            if (value2 instanceof Map) {
                value2 = ((Map<String, Object>) value2).values().toArray()[0];
            }

            if (compare(value1, value2) <= 0) {
                sortedCount++;
            }
        }
        return (double) sortedCount / data.size() > 0.6;
    }

    private int countUniqueValues(List<Object> data) {
        Set<Object> uniqueValues = new HashSet<>();
        for (Object value : data) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).values().toArray()[0];
            }
            uniqueValues.add(value);
        }
        return uniqueValues.size();
    }

    @SuppressWarnings("unchecked")
    private int compare(Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            throw new IllegalArgumentException("Cannot compare null values.");
        }

        if (value1 instanceof Number && value2 instanceof Number) {
            return Double.compare(((Number) value1).doubleValue(), ((Number) value2).doubleValue());
        }

        if (value1 instanceof String && value2 instanceof String) {
            return ((String) value1).compareTo((String) value2);
        }

        if (value1 instanceof Comparable && value2 instanceof Comparable) {
            return ((Comparable<Object>) value1).compareTo(value2);
        }

        throw new IllegalArgumentException("Unsupported data types for comparison: "
                + value1.getClass().getName() + " and " + value2.getClass().getName());
    }
}
