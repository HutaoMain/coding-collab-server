package com.codecat.codecat.repository;

import com.codecat.codecat.model.Problem;
import com.codecat.codecat.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    TestCase findByProblem(Problem problem);
}
