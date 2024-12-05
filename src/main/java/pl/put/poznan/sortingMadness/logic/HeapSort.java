package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;

public class HeapSort implements SortingInterface {
    private String name;

    public HeapSort() {
        this.name = "HeapSort";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> data, boolean descOrder) {
        int n = data.size();

        // Budowanie kopca
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(data, n, i, descOrder);
        }

        // Wyciąganie elementów z kopca
        for (int i = n - 1; i > 0; i--) {
            swap(data, 0, i);
            heapify(data, i, 0, descOrder);
        }

        return data;
    }

    private <T extends Comparable<T>> void heapify(ArrayList<T> data, int n, int i, boolean descOrder) {
        int largestOrSmallest = i; // Korzeń
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Sprawdzenie lewego dziecka
        if (left < n && compare(data.get(left), data.get(largestOrSmallest), descOrder)) {
            largestOrSmallest = left;
        }

        // Sprawdzenie prawego dziecka
        if (right < n && compare(data.get(right), data.get(largestOrSmallest), descOrder)) {
            largestOrSmallest = right;
        }

        // Jeśli korzeń nie jest największy/najmniejszy
        if (largestOrSmallest != i) {
            swap(data, i, largestOrSmallest);
            heapify(data, n, largestOrSmallest, descOrder);
        }
    }

    private <T extends Comparable<T>> boolean compare(T a, T b, boolean descOrder) {
        return descOrder ? a.compareTo(b) > 0 : a.compareTo(b) < 0;
    }

    private <T> void swap(ArrayList<T> data, int i, int j) {
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
