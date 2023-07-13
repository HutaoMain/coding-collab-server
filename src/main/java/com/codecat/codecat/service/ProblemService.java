package com.codecat.codecat.service;

import com.codecat.codecat.dto.AssessmentIdDto;
import com.codecat.codecat.model.Problem;
import com.codecat.codecat.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProblemService {

    @Autowired
    ProblemRepository problemRepository;

    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

//    public List<Problem> getAllProblemByAssessmentId(Long assessmentId){
//        return problemRepository.findByAssessmentId(assessmentId);
//    }

    public List<Problem> getAllProblemByClassesId(Long classesId) {
        return problemRepository.findByClassesId(classesId);
    }

    public List<Problem> getAllProblemsByAssessmentId(Long assessmentId) {
        return problemRepository.findByAssessmentId(assessmentId);
    }

    public Optional<Problem> getProblemById(Long problemId) {
        return problemRepository.findById(problemId);
    }

    public Problem updateProblem(Long problemId, AssessmentIdDto assessmentIdDto) {
        Problem updatedProblem = problemRepository.findById(problemId).orElse(null);
        log.info("eto problem : {}", updatedProblem);
        assert updatedProblem != null;
        updatedProblem.setAssessmentId(assessmentIdDto.getAssessmentId());
        problemRepository.save(updatedProblem);
        return updatedProblem;
    }

}
