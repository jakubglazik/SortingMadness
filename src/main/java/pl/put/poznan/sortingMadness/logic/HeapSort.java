package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;


public class HeapSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;
    public HeapSort() {
        this.name = "HeapSort";this.iterationLimit = Integer.MAX_VALUE;
        this.currentIterations = 0;

    }
    @Override
    public void setIterationLimit(int iterationLimit) {
        this.iterationLimit = iterationLimit;
    }

    @Override
    public int getIterationLimit() {
        return this.iterationLimit;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public <T> SortResult<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder) {
        long startTime = System.nanoTime();
        int n = data.size();

        // Budowanie kopca
        for (int i = n / 2 - 1; i >= 0; i--) {
            if (currentIterations >= iterationLimit) {
                return createPartialResult(data, startTime);
            }
            heapify(data, n, i, comparator, descOrder);
        }

        // Wyciąganie elementów z kopca
        for (int i = n - 1; i > 0; i--) {
            if (currentIterations >= iterationLimit) {
                return createPartialResult(data, startTime);
            }
            swap(data, 0, i);
            heapify(data, i, 0, comparator, descOrder);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return new SortResult<>(data, duration);
    }

    private <T> void heapify(ArrayList<T> data, int n, int i, Comparator<? super T> comparator, boolean descOrder) {
        int largestOrSmallest = i; // Korzeń
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Sprawdzenie lewego dziecka
        if (left < n && compare(data.get(left), data.get(largestOrSmallest), comparator, descOrder)) {
            largestOrSmallest = left;
        }

        // Sprawdzenie prawego dziecka
        if (right < n && compare(data.get(right), data.get(largestOrSmallest), comparator, descOrder)) {
            largestOrSmallest = right;
        }

        // Jeśli korzeń nie jest największy/najmniejszy
        if (largestOrSmallest != i) {
            swap(data, i, largestOrSmallest);
            heapify(data, n, largestOrSmallest, comparator, descOrder);
        }
        currentIterations++;

    }

    private <T> boolean compare(T a, T b, Comparator<? super T> comparator, boolean descOrder) {
        int comparison = comparator.compare(a, b);
        return descOrder ? comparison < 0 : comparison > 0;
    }


    private <T> void swap(ArrayList<T> data, int i, int j) {
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
    private <T> SortResult<T> createPartialResult(ArrayList<T> data, long startTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return new SortResult<>(data, duration);
    }
}
