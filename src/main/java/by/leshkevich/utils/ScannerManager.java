package by.leshkevich.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ScannerManager {

    public static String nextLine(Scanner scanner) {
        while(scanner.hasNext("\n")) {
            scanner.next();
        }
        String str = scanner.nextLine();

        return str;

//        String str = "";
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            while(reader.equals("\n")) {
//                reader.read();
//            }
//
//            str = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
    }

    public static Double nextDouble(Scanner scanner) {

        return Double.parseDouble(nextLine(scanner));
    }
}
