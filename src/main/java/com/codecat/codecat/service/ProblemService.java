package com.codecat.codecat.service;

import com.codecat.codecat.dto.ProblemDto;
import com.codecat.codecat.dto.TestCaseDto;
import com.codecat.codecat.model.Problem;
import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    public List<Problem> getAllProblemByClassesId(Long classesId) {
//        return problemRepository.findByClassesId(classesId);
//    }

    public List<Problem> getAllProblemsByAssessmentId(Long assessmentId) {
        return problemRepository.findByAssessmentId(assessmentId);
    }

    public Optional<Problem> getProblemById(Long problemId) {
        return problemRepository.findById(problemId);
    }

    public ProblemDto getProblemDtoById(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElse(null);

        if (problem == null) {
            return null;
        }

        ProblemDto problemDto = new ProblemDto();
        problemDto.setId(problem.getId());
        problemDto.setProblemCode(problem.getProblemCode());
        problemDto.setProblemName(problem.getProblemName());
        problemDto.setProblemDescription(problem.getProblemDescription());
        problemDto.setProgrammingLanguage(problem.getProgrammingLanguage());
        problemDto.setProblemConstraint(problem.getProblemConstraint());
        problemDto.setPattern(problem.getPattern());
        problemDto.setAssessmentId(problem.getAssessment().getId());

        List<TestCaseDto> testCaseDtoS = new ArrayList<>();
        for (TestCase testCase : problem.getTestCase()) {
            TestCaseDto testCaseDto = new TestCaseDto();
            testCaseDto.setId(testCase.getId());
            testCaseDto.setExpectedOutput(testCase.getExpectedOutput());
            testCaseDto.setPoints(testCase.getPoints());
            testCaseDto.setProblemId(testCase.getProblem().getId());
            testCaseDtoS.add(testCaseDto);
        }

        problemDto.setTestCase(testCaseDtoS);

        return problemDto;
    }

//    public Problem updateProblem(Long problemId, AssessmentIdDto assessmentIdDto) {
//        Problem updatedProblem = problemRepository.findById(problemId).orElse(null);
//        log.info("eto problem : {}", updatedProblem);
//        assert updatedProblem != null;
//        updatedProblem.setAssessmentId(assessmentIdDto.getAssessmentId());
//        problemRepository.save(updatedProblem);
//        return updatedProblem;
//    }

}
