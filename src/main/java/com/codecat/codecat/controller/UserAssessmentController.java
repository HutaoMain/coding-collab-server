package com.codecat.codecat.controller;

import com.codecat.codecat.dto.JoinRequestDto;
import com.codecat.codecat.model.UserAssessment;
import com.codecat.codecat.service.UserAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-assessment")
@CrossOrigin("*")
public class UserAssessmentController {

    @Autowired
    UserAssessmentService userAssessmentService;

    @PostMapping("/join/{email}")
    public ResponseEntity<?> joinClass(@RequestBody JoinRequestDto joinClassRequest, @PathVariable String email) {
        try {
            String assessmentCode = joinClassRequest.getAssessmentCode();

            userAssessmentService.joinAssessment(assessmentCode, email);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list/{userEmail}")
    private ResponseEntity<List<UserAssessment>> getAllUserClassesBasedOnEmail(@PathVariable String userEmail) {
        List<UserAssessment> userAssessmentList = userAssessmentService.getAllUserBasedOnUserEmail(userEmail);
        return ResponseEntity.ok(userAssessmentList);
    }

    @GetMapping("/list")
    private ResponseEntity<List<UserAssessment>> getAllUserClassesByClass(@RequestParam(required = false) String assessmentCode) {
        List<UserAssessment> userAssessmentList = userAssessmentService.getAllClassesByClasses(assessmentCode);
        return ResponseEntity.ok(userAssessmentList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserAssessment> updateAssessmentMode(@PathVariable Long id, @RequestBody UserAssessment userAssessment) {
        UserAssessment userAssessmentObject = userAssessmentService.updateAssessmentMode(id, userAssessment);
        return ResponseEntity.ok(userAssessmentObject);
    }

}
