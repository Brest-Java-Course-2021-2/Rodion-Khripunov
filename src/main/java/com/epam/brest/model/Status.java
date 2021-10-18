package com.epam.brest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Status {

    List<String> messages = new ArrayList<>(Arrays.asList("Please enter weight or 'q' to exit: ", "Please enter distance or 'q' to exit: "));
    List<BigDecimal> userData = new ArrayList<>();

    Status handle();

    StatusType getType();
}
