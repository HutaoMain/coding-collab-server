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
    private ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment createClass = assessmentService.createAssessment(assessment);
        return ResponseEntity.ok(createClass);
    }

    @GetMapping("/list")
    private ResponseEntity<List<Assessment>> getAllAssessment() {
        List<Assessment> assessmentList = assessmentService.getAllAssessment();
        return ResponseEntity.ok(assessmentList);
    }

//    @GetMapping("/list/class-id/{classId}")
//    private ResponseEntity<List<Assessment>> getAllAssessmentByClassId(@PathVariable Long classId) {
//        List<Assessment> assessmentListByClassId = assessmentService.getAllAssessmentByClassId(classId);
//        return ResponseEntity.ok(assessmentListByClassId);
//    }

    @GetMapping("/{id}")
    private ResponseEntity<Assessment> getAssessmentById(@PathVariable Long id) {
        Assessment assessment = assessmentService.getAssessmentById(id);
        return ResponseEntity.ok(assessment);
    }

//    @PutMapping("/isTake/{id}")
//    private ResponseEntity<Assessment> updateIsTake(@PathVariable Long id, @RequestBody Assessment assessment) {
//        Assessment updatedAssessment = assessmentService.updateIsTake(id, assessment);
//        return ResponseEntity.ok(updatedAssessment);
//    }
}
