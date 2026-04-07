import java.io.*;
import java.util.*;

public class MergeSortCSV {

    // Merge Sort
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else               arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void main(String[] args) {
        String filePath = "merge_sort_dataset.csv";
        List<Integer> numbers = new ArrayList<>();

        // Read CSV
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (firstLine) { firstLine = false; continue; } // skip header
                if (!line.isEmpty()) {
                    numbers.add(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Convert to array
        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();

        System.out.println("Total numbers: " + arr.length);
        System.out.println("\nBefore sorting (first 10): " + Arrays.toString(Arrays.copyOfRange(arr, 0, 10)));

        // Sort
        long startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();

        System.out.println("After sorting  (first 10): " + Arrays.toString(Arrays.copyOfRange(arr, 0, 10)));
        System.out.println("After sorting  (last  10): " + Arrays.toString(Arrays.copyOfRange(arr, arr.length - 10, arr.length)));
        System.out.printf("\nTime taken: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        // Optional: Write sorted output to a new CSV
        try (PrintWriter pw = new PrintWriter(new FileWriter("sorted_output.csv"))) {
            pw.println("numbers");
            for (int num : arr) pw.println(num);
            System.out.println("Sorted data written to sorted_output.csv");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}