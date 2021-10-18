package com.epam.brest.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

import static com.epam.brest.model.StatusType.CALC;

public class Calc implements Status {

    Scanner scanner;

    public Calc(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Status handle() {
        try {
            BigDecimal weight = userData.get(0);
            BigDecimal distance = userData.get(1);
            System.out.println(weight.multiply(priceFromFile(weight, "pricesKg.json")).add(distance.multiply(priceFromFile(distance, "pricesKm.json"))));
        } finally {
            userData.clear();
        }
        return new ReadData(scanner);
    }

    @Override
    public StatusType getType() {
        return CALC;
    }

    private static BigDecimal priceFromFile(BigDecimal measure,String fileName) {
        Map<BigDecimal, BigDecimal> priceMap = new HashMap<>();
        try {
            File file = new File(Objects.requireNonNull(Calc.class.getResource("/" + fileName)).getPath());
            Type type = new TypeToken<Map<BigDecimal, BigDecimal>>(){}.getType();
            priceMap = new Gson().fromJson(new FileReader(file), type);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        BigDecimal result = BigDecimal.valueOf(1);
        SortedSet<BigDecimal> prices = new TreeSet<>(priceMap.keySet());
        for (BigDecimal fileMeasure: prices){
            if(measure.compareTo(fileMeasure) >= 0) {
                result = priceMap.get(fileMeasure);
            } else break;
        }
        return result;
    }
}
