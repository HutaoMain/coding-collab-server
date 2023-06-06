package com.codecat.codecat.service;

import com.codecat.codecat.model.Problem;
import com.codecat.codecat.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    ProblemRepository problemRepository;

    public Problem createProblem(Problem problem){
        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems(){
        return problemRepository.findAll();
    }
}
