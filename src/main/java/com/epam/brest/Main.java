package com.epam.brest;

import com.epam.brest.calc.CalcImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {
        BigDecimal weight, pricePerKg, length, pricePerKm;
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                weight = getValueFromCon(scanner, "Enter weight:");
                pricePerKg = priceFromFile(weight, "pricesKg.json");
                length = getValueFromCon(scanner, "Enter length:");
                pricePerKm = priceFromFile(length, "pricesKm.json");
                System.out.println("Result:" + new CalcImpl().handle(weight, pricePerKg, length, pricePerKm));
                System.out.println("Enter 'q' for exit or other to continue:");
            } while (!scanner.next().equalsIgnoreCase("q"));
        }
    }

    private static BigDecimal getValueFromCon(Scanner scanner, String outputMessage) {
        BigDecimal enteredValue;
        System.out.print(outputMessage);
        while(!scanner.hasNextBigDecimal()) {
            System.out.println("Incorrect data entered! Try again.");
            System.out.print(outputMessage);
            scanner.next();
        }
        enteredValue = scanner.nextBigDecimal();
        return enteredValue;
    }

    private static BigDecimal priceFromFile(BigDecimal measure,String fileName) {
        Map<BigDecimal, BigDecimal> priceMap = new HashMap<>();
        try {
            File file = new File(Objects.requireNonNull(Main.class.getResource("/" + fileName)).getPath());
            Type type = new TypeToken<Map<BigDecimal, BigDecimal>>(){}.getType();
            priceMap = new Gson().fromJson(new FileReader(file), type);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        BigDecimal result = new BigDecimal(1);
        for (BigDecimal fileMeasure: priceMap.keySet()){
            if(measure.compareTo(fileMeasure) >= 0) {
                result = priceMap.get(fileMeasure);
            }
        }
        return result;
    }
}
