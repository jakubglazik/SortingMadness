package pl.put.poznan.sortingMadness.logic;

import java.util.ArrayList;
import java.util.Comparator;


public class BubbleSort implements SortingInterface{
    private String name;

    public BubbleSort() {
        this.name = "BubbleSort";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public <T> ArrayList<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int comparison = comparator.compare(data.get(j), data.get(j + 1));
                if ((descOrder && comparison < 0) || (!descOrder && comparison > 0)) {
                    T temp = data.get(j);
                    data.set(j, data.get(j + 1));
                    data.set(j + 1, temp);
                }
            }
        }
        return data;
    }
}
