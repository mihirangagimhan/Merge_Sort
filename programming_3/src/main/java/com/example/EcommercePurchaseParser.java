package com.example;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EcommercePurchaseParser {

    public static void main(String[] args) throws IOException {
        // Replace "path/to/your/file.json" with the actual path to your JSON file
        File jsonFile = new File("C:\\Users\\MSI\\OneDrive\\Desktop\\Programming III\\programming_3\\src\\main\\java\\com\\example\\product_history.json");
        // calls the parseJsonData method to parse the JSON data from the jsonFile and store the parsed information in a List of PurchaseRecord objects named records.
        List<PurchaseRecord> records = parseJsonData(jsonFile);

        // Sort records based on purchaseDate
        mergeSort(records);

        
        // Access the parsed and potentially converted data
        for (PurchaseRecord record : records) {
            System.out.println(record);
        }
    }

    private static List<PurchaseRecord> parseJsonData(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<PurchaseRecord> records = new ArrayList<>();

        // Read the entire JSON file as a String
        String jsonData = new String(java.nio.file.Files.readAllBytes(jsonFile.toPath()));

        // Parse the JSON string into a list of PurchaseRecord objects
        records = mapper.readValue(jsonData, mapper.getTypeFactory().constructCollectionType(List.class, PurchaseRecord.class));

        return records;
    }

    public static class PurchaseRecord {
        private int purchaseDate;
        private String customerId;
        private String productId;

        // Getters, setters, and conversion methods (optional)

        public int getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) throws ParseException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  // Assuming input format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");  // Desired output format
            String formattedDate = outputFormat.format(format.parse(purchaseDate));
            this.purchaseDate = Integer.parseInt(formattedDate);  // Parse String to int
          }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @Override
        public String toString() {
            return "PurchaseRecord{" +
                    "purchaseDate=" + purchaseDate +  
                    ", customerId='" + customerId + '\'' +
                    ", productId='" + productId + '\'' +
                    '}';
        }
    }

    // Merge Sort Implementation

    public static void mergeSort(List<PurchaseRecord> records) {
        if (records.size() <= 1) {
            return;  // Base case: list with 0 or 1 element is already sorted
        }

        // Divide the list into two halves
        List<PurchaseRecord> leftHalf = new ArrayList<>(records.subList(0, records.size() / 2));
        List<PurchaseRecord> rightHalf = new ArrayList<>(records.subList(records.size() / 2, records.size()));

        // Conquer (sort sublists recursively)
        mergeSort(leftHalf);
        mergeSort(rightHalf);

        // Merge the sorted sublists
        merge(leftHalf, rightHalf, records);
    }

    private static void merge(List<PurchaseRecord> left, List<PurchaseRecord> right, List<PurchaseRecord> mergedList) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        // Compare elements from both halves and add the smaller one to the merged list
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getPurchaseDate() <= right.get(rightIndex).getPurchaseDate()) {
            mergedList.set(mergedIndex, left.get(leftIndex));
            leftIndex++;
            } else {
            mergedList.set(mergedIndex, right.get(rightIndex));
            rightIndex++;
            }
            mergedIndex++;
        }

        // Add remaining elements from the leftover half (if any)
        while (leftIndex < left.size()) {
            mergedList.set(mergedIndex, left.get(leftIndex));
            leftIndex++;
            mergedIndex++;
        }

        while (rightIndex < right.size()) {
            mergedList.set(mergedIndex, right.get(rightIndex));
            rightIndex++;
            mergedIndex++;
        }
    }
}
