package com.codecat.codecat.service;

import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    TestCaseRepository testCaseRepository;

    public TestCase createTestCase(TestCase testCase){
        return testCaseRepository.save(testCase);
    }

    public List<TestCase> getAllTestCase(){
        return testCaseRepository.findAll();
    }
}
