package com.codecat.codecat.service;

import com.codecat.codecat.model.Problem;
import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Problem> getAllProblemByAssessmentId(Long assessmentId){
        return problemRepository.findByAssessmentId(assessmentId);
    }

    public Optional<Problem> getProblemById(Long problemId){
        return problemRepository.findById(problemId);
    }

}
