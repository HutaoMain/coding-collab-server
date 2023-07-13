package com.codecat.codecat.controller;

import com.codecat.codecat.dto.JoinRequestDto;
import com.codecat.codecat.model.Classes;
import com.codecat.codecat.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("*")
public class ClassesController {

    @Autowired
    ClassesService classesService;

    @PostMapping("/create")
    private ResponseEntity<Classes> createClass(@RequestBody Classes classes) {
        Classes createdClass = classesService.createClass(classes);
        return ResponseEntity.ok(createdClass);
    }

    @GetMapping("/list")
    private ResponseEntity<List<Classes>> getAllClasses() {
        List<Classes> classesList = classesService.getAllClass();
        return ResponseEntity.ok(classesList);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<String> deleteClassById(@PathVariable Long id) {
        classesService.deleteClassById(id);
        return ResponseEntity.ok("deleted ID: " + id);
    }

    @PostMapping("/join/{email}")
    public ResponseEntity<?> joinClass(@RequestBody JoinRequestDto joinClassRequest, @PathVariable String email) {
        try {
            String classCode = joinClassRequest.getClassCode();

            classesService.joinClass(classCode, email);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list/join-class/{email}")
    public ResponseEntity<List<Classes>> getAllClasses(@PathVariable String email) {
        List<Classes> classes = classesService.getAllClassesByUser(email);
        return ResponseEntity.ok(classes);
    }
}
