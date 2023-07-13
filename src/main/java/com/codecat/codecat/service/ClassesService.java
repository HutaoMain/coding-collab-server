package com.codecat.codecat.service;

import com.codecat.codecat.model.Classes;
import com.codecat.codecat.repository.ClassesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClassesService {

    @Autowired
    ClassesRepository classesRepository;

    public Classes createClass(Classes classes) {
        return classesRepository.save(classes);
    }

    public List<Classes> getAllClass() {
        return classesRepository.findAll();
    }

    public void deleteClassById(Long id) {
        classesRepository.deleteById(id);
    }
}
