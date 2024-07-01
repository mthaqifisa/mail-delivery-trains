package com.thaqif.bigpay.model;

import lombok.Data;

import java.util.List;

@Data
public class Request {
    List<Node> nodes;
    List<Edge> edges;
    List<Train> trains;
    List<Package> packages;
}
