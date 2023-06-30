package com.codecat.codecat.repository;

import com.codecat.codecat.model.ActivityTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTrackerRepository extends JpaRepository<ActivityTracker, Long> {

    ActivityTracker findByEmailAndTestCaseId(String email, Long testCaseId);
}
