package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
import java.util.Comparator;


public class QuickSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;
    public QuickSort() {
        this.name = "QuickSort";this.iterationLimit = Integer.MAX_VALUE;
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

    @Override
    public <T> SortResult<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder) {
        long startTime = System.nanoTime();
        quickSort(data, 0, data.size() - 1, comparator, descOrder);
        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        return new SortResult<>(data, duration);
    }

    private <T> void quickSort(ArrayList<T> data, int low, int high,Comparator<? super T> comperator ,boolean descOrder) {
        if (low < high) {
            if (currentIterations >= iterationLimit) {
                return;
            }

            currentIterations++;
            int pivotIndex = partition(data, low, high, comperator ,descOrder);
            quickSort(data, low, pivotIndex - 1, comperator,descOrder);
            quickSort(data, pivotIndex + 1, high, comperator,descOrder);
        }
    }

    private <T> int partition(ArrayList<T> data, int low, int high, Comparator<? super T> comperator, boolean descOrder) {
        T pivot = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (currentIterations >= iterationLimit) {
                return j;
            }
            int comparison = comperator.compare(data.get(j), pivot);
            if ((descOrder && comparison > 0) || (!descOrder && comparison <= 0)) {
                i++;
                swap(data, i, j);
            }
            currentIterations++;
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