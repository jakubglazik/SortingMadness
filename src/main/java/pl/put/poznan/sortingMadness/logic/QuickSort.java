package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
public class QuickSort implements SortingInterface {
    private String name;

    public QuickSort() {
        this.name = "QuickSort";
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
        quickSort(data, 0, data.size() - 1, descOrder);
        return data;
    }

    private <T extends Comparable<T>> void quickSort(ArrayList<T> data, int low, int high, boolean descOrder) {
        if (low < high) {
            int pivotIndex = partition(data, low, high, descOrder);
            quickSort(data, low, pivotIndex - 1, descOrder);
            quickSort(data, pivotIndex + 1, high, descOrder);
        }
    }

    private <T extends Comparable<T>> int partition(ArrayList<T> data, int low, int high, boolean descOrder) {
        T pivot = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int comparison = data.get(j).compareTo(pivot);
            if ((descOrder && comparison > 0) || (!descOrder && comparison <= 0)) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, high);
        return i + 1;
    }

    private <T> void swap(ArrayList<T> data, int i, int j) {
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}