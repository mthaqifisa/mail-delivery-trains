package com.thaqif.bigpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Package {
    private String name;
    private int weightInKg;
    private String startingNode;
    private String destinationNode;
}