/******************************************************************************
 * @file: Proj2.java
 * @description: This program reads housing price data from a file, processes it,
 *               and performs insertion and search operations on BST and AVL trees.
 *               It measures and records the time taken for these operations.
 * @author: Katherine Demetris
 * @date: October 21, 2024
 ******************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        ArrayList<HousingPricesData> housingPriceList = new ArrayList<>();

        // Read and parse data from the file
        int lineCount = 0;
        while (inputFileNameScanner.hasNextLine() && lineCount < numLines) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 13) {
                try {
                    // Create HousingPricesData object and add to list
                    HousingPricesData data = new HousingPricesData(
                            parts[0], parts[1],  // suburb, address
                            parts[2].isEmpty() ? 0 : Integer.parseInt(parts[2]),  // rooms
                            parts[3].isEmpty() ? ' ' : parts[3].charAt(0),  // type
                            parts[4].isEmpty() ? 0 : Integer.parseInt(parts[4]),  // price
                            parts[5].isEmpty() ? ' ' : parts[5].charAt(0),  // method
                            parts[6], parts[7],  // sellerG, date
                            parts[8].isEmpty() ? 0 : Integer.parseInt(parts[8]),  // postcode
                            parts[9],  // regionName
                            parts[10].isEmpty() ? 0 : Integer.parseInt(parts[10]),  // propertyCount
                            parts[11].isEmpty() ? 0.0 : Double.parseDouble(parts[11]),  // distance
                            parts[12]  // councilArea
                    );
                    housingPriceList.add(data);
                    lineCount++;
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing line: " + line);
                }
            } else {
                System.err.println("Invalid data format in line: " + line);
            }
        }

        // Create sorted and randomized copies of the data
        ArrayList<HousingPricesData> sortedData = new ArrayList<>(housingPriceList);
        ArrayList<HousingPricesData> randomizedData = new ArrayList<>(housingPriceList);
        Collections.sort(sortedData);
        Collections.shuffle(randomizedData);

        // Initialize BST and AVL trees
        BST<HousingPricesData> sortedBST = new BST<>();
        BST<HousingPricesData> randomBST = new BST<>();
        AvlTree<HousingPricesData> sortedAVL = new AvlTree<>();
        AvlTree<HousingPricesData> randomAVL = new AvlTree<>();

        long startTime, endTime;
        double insertSortedBST, insertRandomBST, insertSortedAVL, insertRandomAVL;
        double searchSortedBST, searchRandomBST, searchSortedAVL, searchRandomAVL;

        // Measure insertion time for sorted BST
        startTime = System.nanoTime();
        for (HousingPricesData house : sortedData) {
            sortedBST.insert(house);
        }
        endTime = System.nanoTime();
        insertSortedBST = (endTime - startTime) / 1e6;

        // Measure insertion time for randomized BST
        startTime = System.nanoTime();
        for (HousingPricesData house : randomizedData) {
            randomBST.insert(house);
        }
        endTime = System.nanoTime();
        insertRandomBST = (endTime - startTime) / 1e6;

        // Measure insertion time for sorted AVL
        startTime = System.nanoTime();
        for (HousingPricesData house : sortedData) {
            sortedAVL.insert(house);
        }
        endTime = System.nanoTime();
        insertSortedAVL = (endTime - startTime) / 1e6;

        // Measure insertion time for randomized AVL
        startTime = System.nanoTime();
        for (HousingPricesData house : randomizedData) {
            randomAVL.insert(house);
        }
        endTime = System.nanoTime();
        insertRandomAVL = (endTime - startTime) / 1e6;

        // Measure search time for sorted BST
        startTime = System.nanoTime();
        for (HousingPricesData house : housingPriceList) {
            sortedBST.find(house);
        }
        endTime = System.nanoTime();
        searchSortedBST = (endTime - startTime) / 1e6;

        // Measure search time for randomized BST
        startTime = System.nanoTime();
        for (HousingPricesData house : housingPriceList) {
            randomBST.find(house);
        }
        endTime = System.nanoTime();
        searchRandomBST = (endTime - startTime) / 1e6;

        // Measure search time for sorted AVL
        startTime = System.nanoTime();
        for (HousingPricesData house : housingPriceList) {
            sortedAVL.contains(house);
        }
        endTime = System.nanoTime();
        searchSortedAVL = (endTime - startTime) / 1e6;

        // Measure search time for randomized AVL
        startTime = System.nanoTime();
        for (HousingPricesData house : housingPriceList) {
            randomAVL.contains(house);
        }
        endTime = System.nanoTime();
        searchRandomAVL = (endTime - startTime) / 1e6;

        // Write results to output.txt
        FileWriter fw = new FileWriter("output.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("Number of lines processed: " + lineCount + "\n\n");
        bw.write(String.format("Time to insert into sorted BST: %.6f ms\n", insertSortedBST));
        bw.write(String.format("Time to insert into randomized BST: %.6f ms\n", insertRandomBST));
        bw.write(String.format("Time to insert into sorted AVL: %.6f ms\n", insertSortedAVL));
        bw.write(String.format("Time to insert into randomized AVL: %.6f ms\n\n", insertRandomAVL));
        bw.write(String.format("Time to search in sorted BST: %.6f ms\n", searchSortedBST));
        bw.write(String.format("Time to search in randomized BST: %.6f ms\n", searchRandomBST));
        bw.write(String.format("Time to search in sorted AVL: %.6f ms\n", searchSortedAVL));
        bw.write(String.format("Time to search in randomized AVL: %.6f ms\n", searchRandomAVL));
        bw.write("\n");
        bw.close();

        System.out.println(sortedData); // check if sorted
        System.out.println(randomizedData); // check if randomized
    }
}
