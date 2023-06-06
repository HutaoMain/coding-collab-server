package com.codecat.codecat.controller;

import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-case")
@CrossOrigin("*")
public class TestCaseController {

    @Autowired
    TestCaseService testCaseService;

    @PostMapping("/create")
    private ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase){
        TestCase createdTestCase = testCaseService.createTestCase(testCase);
        return ResponseEntity.ok(createdTestCase);
    }

    @GetMapping("/list")
    private ResponseEntity<List<TestCase>> getAllTestCase(){
        List<TestCase> testCaseList = testCaseService.getAllTestCase();
        return ResponseEntity.ok(testCaseList);
    }
}
