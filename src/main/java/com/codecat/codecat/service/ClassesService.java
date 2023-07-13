package com.codecat.codecat.service;

import com.codecat.codecat.model.Classes;
import com.codecat.codecat.model.User;
import com.codecat.codecat.repository.ClassesRepository;
import com.codecat.codecat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClassesService {

    @Autowired
    ClassesRepository classesRepository;

    @Autowired
    UserRepository userRepository;

    public Classes createClass(Classes classes) {
        return classesRepository.save(classes);
    }

    public List<Classes> getAllClass() {
        return classesRepository.findAll();
    }

    public void deleteClassById(Long id) {
        classesRepository.deleteById(id);
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

        // Associate the user with the class
        classes.getUsers().add(user);
        user.getClasses().add(classes);

        classesRepository.save(classes);
        userRepository.save(user);
    }


    public List<Classes> getAllClassesByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            log.info("there is no email: {}", userEmail);
        }

        assert user != null;
        return user.getClasses();
    }
}
