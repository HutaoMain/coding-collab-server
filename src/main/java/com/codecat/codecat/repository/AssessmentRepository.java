package com.codecat.codecat.repository;

import com.codecat.codecat.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Assessment findByAssessmentCode(String assessmentCode);
}
