package com.codecat.codecat.service;

import com.codecat.codecat.model.Classes;
import com.codecat.codecat.model.User;
import com.codecat.codecat.model.UserClasses;
import com.codecat.codecat.repository.ClassesRepository;
import com.codecat.codecat.repository.UserClassesRepository;
import com.codecat.codecat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class UserClassesService {

    @Autowired
    UserClassesRepository userClassesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassesRepository classesRepository;

    public List<UserClasses> getAllUserBasedOnUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            log.error("There is no user email: {}", userEmail);
        }

        return userClassesRepository.findByUser(user);
    }

    public void joinClass(String classCode, String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new Exception("User not found");
        }

        Classes classes = classesRepository.findByClassCode(classCode);
        if (classes == null) {
            throw new Exception("Class not found");
        }

        UserClasses userClasses = new UserClasses();
        userClasses.setUser(user);
        userClasses.setClasses(classes);

        userClassesRepository.save(userClasses);
    }


    public List<UserClasses> getAllClassesByClasses(String classCode) {
        Classes classes = classesRepository.findByClassCode(classCode);

        List<UserClasses> userClassesList;

        if (classes != null) {
            userClassesList = userClassesRepository.findByClasses(classes);
        } else {
            userClassesList = userClassesRepository.findAll();
        }

        return userClassesList;
    }

    public UserClasses updateAssessmentMode(Long id, UserClasses userClasses) {
        UserClasses userClassesObject = userClassesRepository.findById(id).orElse(null);
        assert userClassesObject != null;
        log.info(userClassesObject.getUser().getEmail());
        userClassesObject.setAssessmentMode(userClasses.getAssessmentMode());
        return userClassesRepository.save(userClassesObject);
    }

}
