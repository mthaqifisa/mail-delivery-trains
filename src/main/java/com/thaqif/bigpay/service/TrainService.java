package com.thaqif.bigpay.service;

import com.thaqif.bigpay.model.*;
import com.thaqif.bigpay.model.Package;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainService {

    public List<Move> solve(List<Node> nodes, List<Edge> edges, List<Train> trains, List<Package> packages) {
        List<Move> moves = new ArrayList<>();
        Map<String, String> packageLocation = new HashMap<>();
        Map<String, String> trainLocation = new HashMap<>();
        Map<String, List<Edge>> adjacencyList = new HashMap<>();

        for (Node node : nodes) {
            adjacencyList.put(node.getName(), new ArrayList<>());
        }

        for (Edge edge : edges) {
            adjacencyList.get(edge.getNode1()).add(edge);
            adjacencyList.get(edge.getNode2()).add(edge);
        }

        for (Package pkg : packages) {
            packageLocation.put(pkg.getName(), pkg.getStartingNode());
        }

        for (Train train : trains) {
            trainLocation.put(train.getName(), train.getStartingNode());
        }

        int currentTime = 0;

        for (Train train : trains) {
            for (Package pkg : packages) {
                if (train.getCapacityInKg() >= pkg.getWeightInKg()) {
                    String startNode = trainLocation.get(train.getName());
                    String endNode = pkg.getDestinationNode();
                    String currentLocation = packageLocation.get(pkg.getName());

                    if (!currentLocation.equals(endNode)) {
                        // Move train to the package location if not already there
                        if (!startNode.equals(currentLocation)) {
                            List<String> pathToPackage = findShortestPath(adjacencyList, startNode, currentLocation);
                            currentTime = moveTrainAlongPath(train, pathToPackage, moves, currentTime, new ArrayList<>(), adjacencyList);
                            startNode = currentLocation;
                            trainLocation.put(train.getName(), currentLocation);
                        }

                        // Pick up the package and move to the destination
                        List<String> pathToDestination = findShortestPath(adjacencyList, currentLocation, endNode);
                        List<String> pickedUpPackages = new ArrayList<>();
                        pickedUpPackages.add(pkg.getName());
                        currentTime = moveTrainAlongPath(train, pathToDestination, moves, currentTime, pickedUpPackages, adjacencyList);
                        packageLocation.put(pkg.getName(), endNode);
                        trainLocation.put(train.getName(), endNode);
                    }
                }
            }
        }

        return moves;
    }

    private List<String> findShortestPath(Map<String, List<Edge>> adjacencyList, String startNode, String endNode) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<String> nodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        nodes.add(startNode);

        while (!nodes.isEmpty()) {
            String currentNode = nodes.poll();

            if (currentNode.equals(endNode)) {
                break;
            }

            for (Edge edge : adjacencyList.get(currentNode)) {
                String neighbor = edge.getNode1().equals(currentNode) ? edge.getNode2() : edge.getNode1();
                int newDist = distances.get(currentNode) + edge.getJourneyTimeInSeconds();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    nodes.add(neighbor);
                }
            }
        }

        List<String> path = new ArrayList<>();
        for (String at = endNode; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return path;
    }

    private int findTravelTime(String startNode, String endNode, Map<String, List<Edge>> adjacencyList) {
        for (Edge edge : adjacencyList.get(startNode)) {
            if ((edge.getNode1().equals(startNode) && edge.getNode2().equals(endNode)) ||
                    (edge.getNode1().equals(endNode) && edge.getNode2().equals(startNode))) {
                System.out.println("Found edge: " + edge.getName() + " from " + startNode + " to " + endNode);
                System.out.println("Journey time: " + edge.getJourneyTimeInSeconds() + " seconds");
                return edge.getJourneyTimeInSeconds();
            }
        }
        throw new IllegalArgumentException("No edge between " + startNode + " and " + endNode);
    }

    private int moveTrainAlongPath(Train train, List<String> path, List<Move> moves, int currentTime, List<String> pickedUpPackages, Map<String, List<Edge>> adjacencyList) {
        String currentLocation = path.get(0); // Initial location of the train

        for (int i = 1; i < path.size(); i++) {
            String nextLocation = path.get(i);
            int travelTime = findTravelTime(currentLocation, nextLocation, adjacencyList);

            List<String> droppedOffPackages = new ArrayList<>();
            if (i == path.size() - 1) { // Last move in the path
                droppedOffPackages.addAll(pickedUpPackages);
            }

            moves.add(new Move(currentTime, train.getName(), currentLocation, new ArrayList<>(pickedUpPackages), nextLocation, droppedOffPackages));
            currentTime += travelTime;

            // Update current location of the train
            currentLocation = nextLocation;

            System.out.println("Move added: Time=" + currentTime + ", Train=" + train.getName() + ", Start=" + currentLocation + ", PickedUp=" + pickedUpPackages + ", End=" + nextLocation + ", DroppedOff=" + droppedOffPackages);
        }

        return currentTime;
    }
}