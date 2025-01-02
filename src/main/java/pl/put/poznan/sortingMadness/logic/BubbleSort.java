package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;


public class BubbleSort implements SortingInterface{
    private String name;
    private int iterationLimit;
    private int currentIterations;
    public BubbleSort() {
        this.name = "BubbleSort";
        this.iterationLimit = Integer.MAX_VALUE;
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

        for (int i = 0; i < n - 1; i++) {
            if (currentIterations >= iterationLimit) {
                break;
            }
            for (int j = 0; j < n - i - 1; j++) {
                int comparison = comparator.compare(data.get(j), data.get(j + 1));
                if ((descOrder && comparison < 0) || (!descOrder && comparison > 0)) {
                    T temp = data.get(j);
                    data.set(j, data.get(j + 1));
                    data.set(j + 1, temp);
                }
            }
            currentIterations++;
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        return new SortResult<>(data, duration);    }
}
