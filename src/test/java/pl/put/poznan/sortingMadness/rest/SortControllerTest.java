//4 metody testujace, 22 mock wywolania metod
package pl.put.poznan.sortingMadness.rest;

import org.junit.jupiter.api.Test;
import pl.put.poznan.sortingMadness.logic.SortingInterface;
import pl.put.poznan.sortingMadness.logic.SortingStrategyFactory;
import pl.put.poznan.sortingMadness.logic.SortResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortControllerTest {

    @Test
    void testSort_Success_SortByValue_WithMockRequest() {
        // Arrange
        SortingStrategyFactory strategyFactory = mock(SortingStrategyFactory.class);
        SortingInterface mockSorter = mock(SortingInterface.class);
        SortRequest request = mock(SortRequest.class);
        SortController sortController = new SortController(strategyFactory);

        when(request.getData()).thenReturn(Arrays.asList(5, 3, 8, 1));
        when(request.getAlgorithmNames()).thenReturn(Collections.singletonList("BubbleSort"));
        when(request.isDescending()).thenReturn(false);

        SortResult<Object> mockResult = new SortResult<>(
                new ArrayList<>(Arrays.asList(1, 3, 5, 8)), // Sorted data
                123L // Execution time
        );

        when(strategyFactory.getSorter("BubbleSort")).thenReturn(mockSorter);
        when(mockSorter.sort(any(ArrayList.class), any(Comparator.class), eq(false))).thenReturn(mockResult);

        // Act
        Map<String, SortResult<Object>> results = sortController.sort(request);

        // Assert
        assertNotNull(results);
        assertTrue(results.containsKey("BubbleSort"));
        assertEquals(Arrays.asList(1, 3, 5, 8), results.get("BubbleSort").getSortedData());
        assertEquals(123L, results.get("BubbleSort").getExecutionTime());

        verify(request).getData();
        verify(request).getAlgorithmNames();
        verify(request).isDescending();
        verify(strategyFactory).getSorter("BubbleSort");
        verify(mockSorter).sort(any(ArrayList.class), any(Comparator.class), eq(false));
    }

    @Test
    void testSort_Success_MultipleAlgorithms() {
        // Arrange
        SortingStrategyFactory strategyFactory = mock(SortingStrategyFactory.class);
        SortingInterface bubbleSortMock = mock(SortingInterface.class);
        SortingInterface quickSortMock = mock(SortingInterface.class);
        SortRequest request = mock(SortRequest.class);
        SortController sortController = new SortController(strategyFactory);

        when(request.getData()).thenReturn(Arrays.asList(10, 20, 5, 15));
        when(request.getAlgorithmNames()).thenReturn(Arrays.asList("BubbleSort", "QuickSort"));
        when(request.isDescending()).thenReturn(false);

        SortResult<Object> bubbleSortResult = new SortResult<>(
                new ArrayList<>(Arrays.asList(5, 10, 15, 20)), 200L);
        SortResult<Object> quickSortResult = new SortResult<>(
                new ArrayList<>(Arrays.asList(5, 10, 15, 20)), 100L);

        when(strategyFactory.getSorter("BubbleSort")).thenReturn(bubbleSortMock);
        when(strategyFactory.getSorter("QuickSort")).thenReturn(quickSortMock);
        when(bubbleSortMock.sort(any(ArrayList.class), any(Comparator.class), eq(false))).thenReturn(bubbleSortResult);
        when(quickSortMock.sort(any(ArrayList.class), any(Comparator.class), eq(false))).thenReturn(quickSortResult);

        // Act
        Map<String, SortResult<Object>> results = sortController.sort(request);

        // Assert
        assertNotNull(results);
        assertTrue(results.containsKey("BubbleSort"));
        assertTrue(results.containsKey("QuickSort"));

        assertEquals(Arrays.asList(5, 10, 15, 20), results.get("BubbleSort").getSortedData());
        assertEquals(200L, results.get("BubbleSort").getExecutionTime());

        assertEquals(Arrays.asList(5, 10, 15, 20), results.get("QuickSort").getSortedData());
        assertEquals(100L, results.get("QuickSort").getExecutionTime());

        verify(request).getData();
        verify(request).getAlgorithmNames();
        verify(strategyFactory).getSorter("BubbleSort");
        verify(strategyFactory).getSorter("QuickSort");
        verify(bubbleSortMock).sort(any(ArrayList.class), any(Comparator.class), eq(false));
        verify(quickSortMock).sort(any(ArrayList.class), any(Comparator.class), eq(false));
    }

    @Test
    void testSort_Success_SortByField() {
        // Arrange
        SortingStrategyFactory strategyFactory = mock(SortingStrategyFactory.class);
        SortingInterface mockSorter = mock(SortingInterface.class);
        SortRequest request = mock(SortRequest.class);
        SortController sortController = new SortController(strategyFactory);

        when(request.getData()).thenReturn(Arrays.asList(
                Map.of("value", 3),
                Map.of("value", 8),
                Map.of("value", 1)
        ));
        when(request.getAlgorithmNames()).thenReturn(Collections.singletonList("BubbleSort"));
        when(request.isDescending()).thenReturn(false);
        when(request.getFieldName()).thenReturn("value");

        SortResult<Map<String, Object>> mockResult = new SortResult<>(
                new ArrayList<>(Arrays.asList(
                        Map.of("value", 1),
                        Map.of("value", 3),
                        Map.of("value", 8)
                )),
                123L // Execution time
        );

        when(strategyFactory.getSorter("BubbleSort")).thenReturn(mockSorter);
        when(mockSorter.sort(any(ArrayList.class), any(Comparator.class), eq(false))).thenReturn((SortResult) mockResult);

        // Act
        Map<String, SortResult<Object>> results = sortController.sort(request);

        // Assert
        assertNotNull(results);
        assertTrue(results.containsKey("BubbleSort"));
        assertEquals(
                Arrays.asList(
                        Map.of("value", 1),
                        Map.of("value", 3),
                        Map.of("value", 8)
                ),
                results.get("BubbleSort").getSortedData()
        );
        assertEquals(123L, results.get("BubbleSort").getExecutionTime());

        verify(request).getData();
        verify(request).getAlgorithmNames();
        verify(request).getFieldName();
        verify(strategyFactory).getSorter("BubbleSort");
        verify(mockSorter).sort(any(ArrayList.class), any(Comparator.class), eq(false));
    }

    @Test
    void testSort_InvalidField_ThrowsException() {
        // Arrange
        SortingStrategyFactory strategyFactory = mock(SortingStrategyFactory.class);
        SortingInterface mockSorter = mock(SortingInterface.class);
        SortRequest request = mock(SortRequest.class);
        SortController sortController = new SortController(strategyFactory);

        // Create mutable maps to allow null values
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("value", 5);

        Map<String, Object> invalidMap = new HashMap<>();
        invalidMap.put("value", null);

        when(request.getData()).thenReturn(Arrays.asList(validMap, invalidMap));
        when(request.getAlgorithmNames()).thenReturn(Collections.singletonList("BubbleSort"));
        when(request.getFieldName()).thenReturn("value");

        when(strategyFactory.getSorter("BubbleSort")).thenReturn(mockSorter);

        // Mock the sorter to throw an IllegalArgumentException
        when(mockSorter.sort(any(ArrayList.class), any(Comparator.class), eq(false)))
                .thenThrow(new IllegalArgumentException("Field 'value' contains null values."));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sortController.sort(request));
        assertEquals("Field 'value' contains null values.", exception.getMessage());

        // Verify interactions with mocks
        verify(request).getData();
        verify(request).getAlgorithmNames();
        verify(request).getFieldName();
        verify(strategyFactory).getSorter("BubbleSort");
        verify(mockSorter).sort(any(ArrayList.class), any(Comparator.class), eq(false));
    }


}
