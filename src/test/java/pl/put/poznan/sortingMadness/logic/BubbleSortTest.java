package pl.put.poznan.sortingMadness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    private BubbleSort bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }

    @Test
    void testSortAscendingOrderWithIntegers() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 2));
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 8));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortDescendingOrderWithIntegers() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 2));
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), true);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(8, 5, 3, 2, 1));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithStringLengthComparator() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList("apple", "orange", "banana", "kiwi"));
        SortResult<String> result = bubbleSort.sort(data, Comparator.comparingInt(String::length), false);

        ArrayList<String> expected = new ArrayList<>(Arrays.asList("kiwi", "apple", "orange", "banana"));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithEmptyList() {
        ArrayList<Integer> data = new ArrayList<>();
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        assertNotNull(result.getSortedData());
        assertTrue(result.getSortedData().isEmpty());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithSingleElementList() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(42));
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(42));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithDuplicateValues() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(5, 1, 3, 5, 2, 1));
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 5, 5));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithNegativeValues() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(-3, -1, -5, 0, 2));
        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(-5, -3, -1, 0, 2));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithIterationLimitNotReached() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 2));
        bubbleSort.setIterationLimit(10); // High enough to complete sorting

        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 8));
        assertEquals(expected, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithIterationLimitReached() {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 2));
        bubbleSort.setIterationLimit(1); // Only one pass

        SortResult<Integer> result = bubbleSort.sort(data, Comparator.naturalOrder(), false);

        ArrayList<Integer> partiallySorted = new ArrayList<>(Arrays.asList(3, 5, 1, 2, 8));
        assertEquals(partiallySorted, result.getSortedData());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSortWithCustomObjectComparator() {
        class Person {
            String name;
            int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public String toString() {
                return name + " (" + age + ")";
            }
        }

        ArrayList<Person> data = new ArrayList<>(Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        ));

        SortResult<Person> result = bubbleSort.sort(data, Comparator.comparingInt(p -> p.age), false);

        ArrayList<Person> expected = new ArrayList<>(Arrays.asList(
                new Person("Bob", 25),
                new Person("Alice", 30),
                new Person("Charlie", 35)
        ));
        assertEquals(expected.toString(), result.getSortedData().toString());
        assertTrue(result.getExecutionTime() > 0);
    }

    @Test
    void testSetAndGetName() {
        assertEquals("BubbleSort", bubbleSort.getName());
        bubbleSort.setName("CustomBubbleSort");
        assertEquals("CustomBubbleSort", bubbleSort.getName());
    }

    @Test
    void testSetAndGetIterationLimit() {
        assertEquals(Integer.MAX_VALUE, bubbleSort.getIterationLimit());
        bubbleSort.setIterationLimit(5);
        assertEquals(5, bubbleSort.getIterationLimit());
    }
}
