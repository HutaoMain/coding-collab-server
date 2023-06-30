package com.codecat.codecat.controller;

import com.codecat.codecat.model.Problem;
import com.codecat.codecat.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/problem")
@CrossOrigin("*")
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

    @GetMapping("/list/{assessmentId}")
    private ResponseEntity<List<Problem>> getAllAssessmentByClassId(@PathVariable Long assessmentId){
        List<Problem> problemListByAssessmentId =  problemService.getAllProblemByAssessmentId(assessmentId);
        return ResponseEntity.ok(problemListByAssessmentId);
    }

    @GetMapping("/{problemId}")
    private ResponseEntity<Optional<Problem>> getProblemById(@PathVariable Long problemId){
        Optional<Problem> problemById =  problemService.getProblemById(problemId);
        return ResponseEntity.ok(problemById);
    }
}
