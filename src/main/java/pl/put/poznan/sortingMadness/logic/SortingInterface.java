package pl.put.poznan.sortingMadness.logic;
import java.util.ArrayList;
import java.util.Comparator;

public interface SortingInterface {
    String name = "";
    String getName();
    void setName(String name);
    //<T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> data, boolean descOrder);
    <T> SortResult<T> sort(ArrayList<T> data, Comparator<? super T> comparator, boolean descOrder);
}
