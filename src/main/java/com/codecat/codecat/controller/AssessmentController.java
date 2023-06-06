package com.codecat.codecat.controller;

import com.codecat.codecat.model.Assessment;
import com.codecat.codecat.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment")
@CrossOrigin("*")
public class AssessmentController {

    @Autowired
    AssessmentService assessmentService;

    @PostMapping("/create")
    private ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment){
       Assessment createClass =  assessmentService.createAssessment(assessment);
        return ResponseEntity.ok(createClass);
    }

    @GetMapping("/list")
    private ResponseEntity<List<Assessment>> getAllAssessment(){
        List<Assessment> assessmentList =  assessmentService.getAllAssessment();
        return ResponseEntity.ok(assessmentList);
    }
}