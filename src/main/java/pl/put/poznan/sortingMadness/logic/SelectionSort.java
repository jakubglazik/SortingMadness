package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
import java.util.Comparator;


public class SelectionSort implements SortingInterface{
    private String name;

    public SelectionSort() {
        this.name = "SelectionSort";
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
    public <T> ArrayList<T> sort(ArrayList<T> data, Comparator<? super T> comperator, boolean descOrder) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;
            for (int j = i + 1; j < n; j++) {
                int comparison = comperator.compare(data.get(j), data.get(selectedIdx));
                if ((descOrder && comparison > 0) || (!descOrder && comparison < 0)) {
                    selectedIdx = j;
                }
            }
            if (selectedIdx != i) {
                T temp = data.get(selectedIdx);
                data.set(selectedIdx, data.get(i));
                data.set(i, temp);
            }
        }
        return data;
    }
}

