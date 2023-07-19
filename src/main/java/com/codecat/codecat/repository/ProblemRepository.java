package com.codecat.codecat.repository;

import com.codecat.codecat.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

//    List<Problem> findByAssessmentId(Long assessmentId);
//
//    List<Problem> findByClassesId(Long classesId);

    List<Problem> findByAssessmentId(Long assessmentId);
}
