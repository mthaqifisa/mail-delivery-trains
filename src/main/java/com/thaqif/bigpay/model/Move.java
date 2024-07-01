package com.thaqif.bigpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move {
    private int timeInSeconds;
    private String trainName;
    private String startNode;
    private List<String> pickedUpPackages;
    private String endNode;
    private List<String> droppedOffPackages;
}