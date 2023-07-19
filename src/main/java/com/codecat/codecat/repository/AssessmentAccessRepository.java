package com.codecat.codecat.repository;

import com.codecat.codecat.model.AssessmentAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentAccessRepository extends JpaRepository<AssessmentAccess, Long> {
}
