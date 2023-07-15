package com.codecat.codecat.controller;

import com.codecat.codecat.dto.JoinRequestDto;
import com.codecat.codecat.model.UserClasses;
import com.codecat.codecat.service.UserClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-classes")
@CrossOrigin("*")
public class UserClassesController {

    @Autowired
    UserClassesService userClassesService;

    @PostMapping("/join/{email}")
    public ResponseEntity<?> joinClass(@RequestBody JoinRequestDto joinClassRequest, @PathVariable String email) {
        try {
            String classCode = joinClassRequest.getClassCode();

            userClassesService.joinClass(classCode, email);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list/{userEmail}")
    private ResponseEntity<List<UserClasses>> getAllUserClassesBasedOnEmail(@PathVariable String userEmail) {
        List<UserClasses> userClassesList = userClassesService.getAllUserBasedOnUserEmail(userEmail);
        return ResponseEntity.ok(userClassesList);
    }

    @GetMapping("/list")
    private ResponseEntity<List<UserClasses>> getAllUserClassesByClass(@RequestParam(required = false) String classCode) {
        List<UserClasses> userClassesList = userClassesService.getAllClassesByClasses(classCode);
        return ResponseEntity.ok(userClassesList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserClasses> updateAssessmentMode(@PathVariable Long id, @RequestBody UserClasses userClasses) {
        UserClasses userClassesObject = userClassesService.updateAssessmentMode(id, userClasses);
        return ResponseEntity.ok(userClassesObject);
    }

}
