package com.codecat.codecat.repository;

import com.codecat.codecat.model.AssessmentTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentTrackerRepository extends JpaRepository<AssessmentTracker, Long> {
    boolean existsByAssessmentIdAndEmail(Long assessmentId, String email);

    AssessmentTracker findByAssessmentIdAndEmail(Long assessmentId, String email);
}
