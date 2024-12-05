package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
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
    public <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> data, boolean descOrder) {
        int n = data.size();
        for (int i = 1; i < n; i++) {
            T key = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                int comparison = data.get(j).compareTo(key);
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