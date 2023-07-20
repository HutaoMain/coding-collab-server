package com.codecat.codecat.service;

import com.codecat.codecat.model.Assessment;
import com.codecat.codecat.model.AssessmentTracker;
//import com.codecat.codecat.model.Classes;
import com.codecat.codecat.model.User;
import com.codecat.codecat.model.UserAssessment;
import com.codecat.codecat.repository.AssessmentTrackerRepository;
//import com.codecat.codecat.repository.ClassesRepository;
import com.codecat.codecat.repository.AssessmentRepository;
import com.codecat.codecat.repository.UserClassesRepository;
import com.codecat.codecat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserAssessmentService {

    @Autowired
    UserClassesRepository userClassesRepository;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    ClassesRepository classesRepository; /* change into assessment*/

    @Autowired
    AssessmentRepository assessmentRepository;

    @Autowired
    AssessmentTrackerRepository assessmentTrackerRepository;

    public List<UserAssessment> getAllUserBasedOnUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            log.error("There is no user email: {}", userEmail);
        }

        return userClassesRepository.findByUser(user);
    }

    /* AssessmentAccess method here */

    private void creatingAssessmentAccess(String userEmail, Long assessmentId) {
        AssessmentTracker assessmentTracker = new AssessmentTracker();

        assessmentTracker.setEmail(userEmail);
        assessmentTracker.setAssessmentId(assessmentId);

        assessmentTrackerRepository.save(assessmentTracker);
    }

    public void joinAssessment(String assessmentCode, String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new Exception("User not found");
        }
/* change into assessment
//        Classes classes = classesRepository.findByClassCode(classCode);
        if (classes == null) {
            throw new Exception("Class not found");
        } */

        Assessment assessment = assessmentRepository.findByAssessmentCode(assessmentCode);

        UserAssessment userAssessment = new UserAssessment();
        userAssessment.setUser(user);
        userAssessment.setAssessment(assessment);

        creatingAssessmentAccess(userEmail, assessment.getId());

        userClassesRepository.save(userAssessment);
    }


    public List<UserAssessment> getAllClassesByClasses(String assessmentCode) {
//        change into assessment
        Assessment assessment = assessmentRepository.findByAssessmentCode(assessmentCode);

        List<UserAssessment> userAssessmentList;

        if (assessment != null) {
//            change into assessment
            userAssessmentList = userClassesRepository.findByAssessment(assessment);
        } else {
            userAssessmentList = userClassesRepository.findAll();
        }

        return userAssessmentList;
    }

    public UserAssessment updateAssessmentMode(Long id, UserAssessment userAssessment) {
        UserAssessment userAssessmentObject = userClassesRepository.findById(id).orElse(null);
        assert userAssessmentObject != null;
        log.info(userAssessmentObject.getUser().getEmail());
        userAssessmentObject.setAssessmentMode(userAssessment.getAssessmentMode());
        return userClassesRepository.save(userAssessmentObject);
    }

}
