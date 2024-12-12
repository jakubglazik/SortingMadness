package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
import java.util.Comparator;


public class InsertionSort implements SortingInterface {
    private String name;

    public InsertionSort() {
        this.name = "InsertionSort";
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
    public <T> ArrayList<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder) {
        int n = data.size();
        for (int i = 1; i < n; i++) {
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
            data.set(j + 1, key);
        }
        return data;
    }
}