package com.thaqif.bigpay.util;

import com.thaqif.bigpay.model.Edge;
import com.thaqif.bigpay.model.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<Node, Set<Edge>> adjacencyList = new HashMap<>();

    public void addNode(Node node) {
        adjacencyList.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(Node node1, Node node2, Edge edge) {
        adjacencyList.get(node1).add(edge);
        adjacencyList.get(node2).add(edge);
    }

    public Set<Edge> getEdges(Node node) {
        return adjacencyList.get(node);
    }
}