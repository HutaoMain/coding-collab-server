package com.codecat.codecat.repository;

import com.codecat.codecat.model.Assessment;
import com.codecat.codecat.model.User;
import com.codecat.codecat.model.UserAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClassesRepository extends JpaRepository<UserAssessment, Long> {

    List<UserAssessment> findByUser(User user);

    List<UserAssessment> findByAssessment(Assessment assessment);
}
