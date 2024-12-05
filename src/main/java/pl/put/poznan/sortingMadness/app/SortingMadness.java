package pl.put.poznan.sortingMadness.app;

import pl.put.poznan.sortingMadness.logic.BubbleSort;

import java.util.ArrayList;
import java.util.Scanner;

public class SortingMadness {
    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter numbers to sort (separated by spaces):");

        String input = scanner.nextLine();

        ArrayList<Integer> numbers = new ArrayList<>();
        for (String number : input.split("\\s+")) {
            try {
                numbers.add(Integer.parseInt(number));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + number + " is not a number. Skipping...");
            }
        }

        boolean descOrder = true;

        System.out.println("Before sorting: " + numbers);

        ArrayList<Integer> sortedNumbers = bubbleSort.sort(numbers, descOrder);

        System.out.println("Sorted (descending): " + sortedNumbers);

        scanner.close();
    }
}
