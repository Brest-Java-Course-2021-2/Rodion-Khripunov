package com.epam.brest;

import com.epam.brest.model.ReadData;
import com.epam.brest.model.Status;
import com.epam.brest.model.StatusType;

import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {
        try (Scanner scanner = new Scanner(System.in)) {
            Status currentStatus = new ReadData(scanner);
            while(currentStatus.getType() != StatusType.EXIT) {
                System.out.println("Current type: " + currentStatus.getType());
                currentStatus = currentStatus.handle();
            }
        }
    }
}
