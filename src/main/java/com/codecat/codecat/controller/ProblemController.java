package com.codecat.codecat.controller;

import com.codecat.codecat.model.Problem;
import com.codecat.codecat.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @PostMapping("/create")
    private ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
        Problem createProblem = problemService.createProblem(problem);
        return ResponseEntity.ok(createProblem);
    }

    @GetMapping("/list")
    private ResponseEntity<List<Problem>> getAllProblems() {
        List<Problem> problemList = problemService.getAllProblems();
        return ResponseEntity.ok(problemList);
    }
}
