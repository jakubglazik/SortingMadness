//7 metod testujacych
package pl.put.poznan.sortingMadness.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingStrategyFactoryTest {

    private final SortingStrategyFactory factory = new SortingStrategyFactory();

    @Test
    void testGetBubbleSort() {
        SortingInterface sorter = factory.getSorter("BubbleSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof BubbleSort, "Expected instance of BubbleSort");
    }

    @Test
    void testGetHeapSort() {
        SortingInterface sorter = factory.getSorter("HeapSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof HeapSort, "Expected instance of HeapSort");
    }

    @Test
    void testGetInsertionSort() {
        SortingInterface sorter = factory.getSorter("InsertionSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof InsertionSort, "Expected instance of InsertionSort");
    }

    @Test
    void testGetMergeSort() {
        SortingInterface sorter = factory.getSorter("MergeSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof MergeSort, "Expected instance of MergeSort");
    }

    @Test
    void testGetQuickSort() {
        SortingInterface sorter = factory.getSorter("QuickSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof QuickSort, "Expected instance of QuickSort");
    }

    @Test
    void testGetSelectionSort() {
        SortingInterface sorter = factory.getSorter("SelectionSort");
        assertNotNull(sorter);
        assertTrue(sorter instanceof SelectionSort, "Expected instance of SelectionSort");
    }

    @Test
    void testInvalidSorterName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> factory.getSorter("InvalidSort"));
        assertEquals("No such sorting algorithm available: InvalidSort", exception.getMessage());
    }
}
