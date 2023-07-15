package com.codecat.codecat.repository;

import com.codecat.codecat.model.ActivityTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityTrackerRepository extends JpaRepository<ActivityTracker, Long> {

    ActivityTracker findByEmailAndTestCaseId(String email, Long testCaseId);

    List<ActivityTracker> findByAssessmentId(Long assessmentId);

    List<ActivityTracker> findByEmail(String email);
}
