package com.thaqif.bigpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    private String name;
    private int capacityInKg;
    private String startingNode;
}