package com.codecat.codecat.controller;

import com.codecat.codecat.dto.CodeRequest;
import com.codecat.codecat.model.ActivityTracker;
import com.codecat.codecat.model.Problem;
import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.repository.ActivityTrackerRepository;
import com.codecat.codecat.repository.ProblemRepository;
import com.codecat.codecat.repository.TestCaseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.*;

@RestController
@CrossOrigin("*")
@Slf4j
public class CompilerController {

    @Value("${rapid.api.key}")
    String rapidApiKey;

    @Value("${rapid.api.host}")
    String rapidApiHost;

    @Value("${rapid.api.url}")
    String rapidApiUrl;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    ActivityTrackerRepository activityTrackerRepository;

    @PostMapping("/{testCaseId}/{problemId}/{userEmail}/compile")
    public ResponseEntity<CodeRequest> compileCode(@PathVariable Long problemId, @PathVariable Long testCaseId, @PathVariable String userEmail, @RequestBody CodeRequest codeRequest) throws JsonProcessingException {
        Problem problem = problemRepository.findById(problemId).orElse(null);
        if (problem == null) {
            return ResponseEntity.notFound().build();
        }

        TestCase testCase = testCaseRepository.findById(testCaseId).orElse(null);
        if (testCase == null) {
            return ResponseEntity.notFound().build();
        }

        log.info("testCase Ito: {}", testCase.getId().toString());

        String expectedOutput = testCase.getExpectedOutput();
        String constraints = problem.getProblemConstraint();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<CodeRequest> requestEntity = new RequestEntity<>(codeRequest, headers, HttpMethod.POST, URI.create(rapidApiUrl));

        ResponseEntity<String> compileResponse = restTemplate.exchange(requestEntity, String.class);

        String responseBody = compileResponse.getBody();
        log.info("responseBody: {}", responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseBody);

        String output = responseJson.get("output").asText();
        log.info("output: {}", output);

        /* handles the checking of the constraints */
        switch (constraints) {
            case "integer":
                handleIntegerConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
            case "string":
                handleStringConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
            case "userDefinedFunction":
                handleUserDefinedFunctionConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
            case "forLoop":
                handleForLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
            case "whileLoop":
                handleWhileLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
            case "forWhileLoop":
                handleForWhileLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode());
                break;
        }

        return restTemplate.exchange(requestEntity, CodeRequest.class);
    }

    /* handles the saving of the activity */
    private void saveActivityTracker(String userEmail, Long testCaseId, Integer points, String code, String expectedOutput, String problemConstraint) {
        ActivityTracker activityTracker = new ActivityTracker();

        activityTracker.setEmail(userEmail);
        activityTracker.setTestCaseId(testCaseId);
        activityTracker.setPoints(points);
        activityTracker.setCompiledCode(code);
        activityTracker.setExpectedOutput(expectedOutput);
        activityTracker.setProblemConstraint(problemConstraint);

        activityTrackerRepository.save(activityTracker);
    }

    /* handles the Integer Constraint */
    private void handleIntegerConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        try {
            int integerValue = Integer.parseInt(output.trim());
            log.info("integer value ito: {}", integerValue);

            if (problem.getPattern().equals("exactMatch")) {
                if (Integer.parseInt(expectedOutput) == integerValue) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String integerValueString = String.valueOf(integerValue);

                if (integerValueString.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } catch (Exception e) {
            log.info("output is not integer: {}", output);
        }
    }

    /* handles the String Constraint */
    private void handleStringConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        try {
            log.info("String value ito: {}", output);

            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("String value ito: {} string pattern matching {} ", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } catch (Exception e) {
            log.info("output is not a string: {}", output);
        }
    }

    /* handles the User Defined Constraint */
    private void handleUserDefinedFunctionConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        String patternRegex = "def [a-zA-Z_][a-zA-Z0-9_]*\\((?:[^),]*,?\\s*)*\\)";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("User-defined function value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } else {
            log.info("User-defined function code does not match the expected pattern.");
        }
    }

    /* handles the for loop Constraint */
    private void handleForLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        String patternRegex = "for\\s+\\w+\\s+in\\s+\\w+:";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("For loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } else {
            log.info("For loop code does not match the expected pattern.");
        }
    }

    /* handles the while loop Constraint */
    private void handleWhileLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        String patternRegex = "while\\s+.+:";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("While loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } else {
            log.info("While loop code does not match the expected pattern.");
        }
    }

    /* handles the combination of for while loop Constraint */
    private void handleForWhileLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code) {
        String patternRegex = "(for\\s+\\w+\\s+in\\s+\\w+:)|(while\\s+.+:)";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("For-While loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint());
                }
            }
        } else {
            log.info("For-While loop code does not match the expected pattern.");
        }
    }


}
