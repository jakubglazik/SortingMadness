package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
public class MergeSort implements SortingInterface {
    private String name;

    public MergeSort() {
        this.name = "MergeSort";
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
        if (data.size() <= 1) {
            return data;
        }

        int mid = data.size() / 2;
        ArrayList<T> left = new ArrayList<>(data.subList(0, mid));
        ArrayList<T> right = new ArrayList<>(data.subList(mid, data.size()));

        sort(left, descOrder);
        sort(right, descOrder);

        return merge(data, left, right, descOrder);
    }

    private <T extends Comparable<T>> ArrayList<T> merge(ArrayList<T> data, ArrayList<T> left, ArrayList<T> right, boolean descOrder) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            int comparison = left.get(i).compareTo(right.get(j));
            if ((descOrder && comparison > 0) || (!descOrder && comparison <= 0)) {
                data.set(k++, left.get(i++));
            } else {
                data.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            data.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            data.set(k++, right.get(j++));
        }

        return data;
    }
}
