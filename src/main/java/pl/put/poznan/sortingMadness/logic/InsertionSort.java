package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
import java.util.Comparator;


public class InsertionSort implements SortingInterface {
    private String name;
    private int iterationLimit;
    private int currentIterations;
    public InsertionSort() {
        this.name = "InsertionSort";this.iterationLimit = Integer.MAX_VALUE;
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
        int n = data.size();
        for (int i = 1; i < n; i++) {
            if (currentIterations >= iterationLimit) {
                    break;
                }
            T key = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                int comparison = comparator.compare(data.get(j),key);
                if ((descOrder && comparison < 0) || (!descOrder && comparison > 0)) {
                    data.set(j + 1, data.get(j));
                    j--;
                } else {
                    break;
                }
            }
            currentIterations++;
            data.set(j + 1, key);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        return new SortResult<>(data, duration);    }
}