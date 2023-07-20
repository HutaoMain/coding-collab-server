package com.codecat.codecat.service;

import com.codecat.codecat.model.AssessmentTracker;
import com.codecat.codecat.repository.AssessmentTrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssessmentTrackerService {

    @Autowired
    AssessmentTrackerRepository assessmentTrackerRepository;

    public AssessmentTracker createAssessmentTracker(AssessmentTracker assessmentTracker) {
        boolean isAssessmentTrackerAlreadyExist = assessmentTrackerRepository.existsByAssessmentIdAndEmail(assessmentTracker.getAssessmentId(), assessmentTracker.getEmail());
        if (isAssessmentTrackerAlreadyExist) {
            log.info("AssessmentTracker already exist");
            return assessmentTracker;
        }
        return assessmentTrackerRepository.save(assessmentTracker);
    }

    public AssessmentTracker getAssessmentTrackerByAssessmentIdAndEmail(Long assessmentId, String email){
        return assessmentTrackerRepository.findByAssessmentIdAndEmail(assessmentId, email);
    }
}
