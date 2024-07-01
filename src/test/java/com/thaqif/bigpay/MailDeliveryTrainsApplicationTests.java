package com.thaqif.bigpay;

import com.thaqif.bigpay.model.*;
import com.thaqif.bigpay.model.Package;
import com.thaqif.bigpay.service.TrainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MailDeliveryTrainsApplicationTests {

    @Autowired
    private TrainService trainService;

    @Test
    void testSolve() {
        // Define nodes
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        // Define edges
        Edge edge1 = new Edge("E1", "A", "B", 1800); // 30 minutes in seconds
        Edge edge2 = new Edge("E2", "B", "C", 600);  // 10 minutes in seconds

        // Define train
        Train train = new Train("Q1", 6, "B");

        // Define package
        Package pkg = new Package("K1", 5, "A", "C");

        // Define expected moves
        List<Move> expectedMoves = Arrays.asList(
                new Move(0, "Q1", "B", List.of(), "A", List.of()),
                new Move(1800, "Q1", "A", List.of("K1"), "B", List.of()),
                new Move(3600, "Q1", "B", List.of("K1"), "C", List.of("K1"))
        );

        // Call the service method
        List<Move> actualMoves = trainService.solve(
                Arrays.asList(nodeA, nodeB, nodeC),
                Arrays.asList(edge1, edge2),
                Arrays.asList(train),
                Arrays.asList(pkg)
        );

        // Validate the moves
        assertEquals(expectedMoves.size(), actualMoves.size());
        for (int i = 0; i < expectedMoves.size(); i++) {
            assertEquals(expectedMoves.get(i).getTimeInSeconds(), actualMoves.get(i).getTimeInSeconds());
            assertEquals(expectedMoves.get(i).getTrainName(), actualMoves.get(i).getTrainName());
            assertEquals(expectedMoves.get(i).getStartNode(), actualMoves.get(i).getStartNode());
            assertEquals(expectedMoves.get(i).getPickedUpPackages(), actualMoves.get(i).getPickedUpPackages());
            assertEquals(expectedMoves.get(i).getEndNode(), actualMoves.get(i).getEndNode());
            assertEquals(expectedMoves.get(i).getDroppedOffPackages(), actualMoves.get(i).getDroppedOffPackages());
        }
    }
}