package com.codecat.codecat.service;

import com.codecat.codecat.model.Assessment;
import com.codecat.codecat.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    AssessmentRepository assessmentRepository;

    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAllAssessment() {
        return assessmentRepository.findAll();
    }

    public List<Assessment> getAllAssessmentByClassId(Long classId){
        return assessmentRepository.findByClassesId(classId);
    }

    public Assessment getAssessmentById(Long id) {
        return assessmentRepository.findById(id).orElse(null);
    }
}
