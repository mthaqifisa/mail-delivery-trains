package com.thaqif.bigpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    private String name;
    private String node1;
    private String node2;
    private int journeyTimeInSeconds;
}