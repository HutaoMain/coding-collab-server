package com.codecat.codecat.controller;

import com.codecat.codecat.model.AssessmentTracker;
import com.codecat.codecat.service.AssessmentTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessment-tracker")
@CrossOrigin("*")
public class AssessmentTrackerController {

    @Autowired
    AssessmentTrackerService assessmentTrackerService;

    @PostMapping("/create")
    private ResponseEntity<AssessmentTracker> createAssessmentTracker(@RequestBody AssessmentTracker assessmentTracker){
        AssessmentTracker createdAssessmentTracker = assessmentTrackerService.createAssessmentTracker(assessmentTracker);
        return ResponseEntity.ok(createdAssessmentTracker);
    }

    @GetMapping("/{assessmentId}/{email}")
    private ResponseEntity<AssessmentTracker> getAssessmentTrackerByAssessmentIdAndEmail(@PathVariable Long assessmentId, @PathVariable String email){
        AssessmentTracker assessmentTracker = assessmentTrackerService.getAssessmentTrackerByAssessmentIdAndEmail(assessmentId, email);
        return ResponseEntity.ok(assessmentTracker);
    }

    @PutMapping("/update/{assessmentId}/{email}")
    private ResponseEntity<AssessmentTracker> updateAssessmentTrackerByAssessmentIdAndEmail(@PathVariable Long assessmentId, @PathVariable String email, @RequestBody AssessmentTracker assessmentTracker){
        AssessmentTracker updatedAssessmentTracker = assessmentTrackerService.updateAssessmentTrackerByAssessmentIdAndEmail(assessmentId, email, assessmentTracker);
        return ResponseEntity.ok(updatedAssessmentTracker);
    }
}
