package com.thaqif.bigpay.controller;

import com.thaqif.bigpay.model.*;
import com.thaqif.bigpay.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/solve")
    public List<Move> solve(@RequestBody Request req) {
        return trainService.solve(req.getNodes(), req.getEdges(), req.getTrains(), req.getPackages());
    }
}