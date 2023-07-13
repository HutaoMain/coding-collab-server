package com.codecat.codecat.controller;

import com.codecat.codecat.dto.CodeRequest;
import com.codecat.codecat.model.ActivityTracker;
import com.codecat.codecat.model.Problem;
import com.codecat.codecat.model.TestCase;
import com.codecat.codecat.model.User;
import com.codecat.codecat.repository.ActivityTrackerRepository;
import com.codecat.codecat.repository.ProblemRepository;
import com.codecat.codecat.repository.TestCaseRepository;
import com.codecat.codecat.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.*;

@RestController
@CrossOrigin("*")
@Slf4j
public class CompilerController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

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

    @Autowired
    UserRepository userRepository;

    @PostMapping("/{testCaseId}/{problemId}/{userEmail}/compile")
    public ResponseEntity<String> compileCode(@PathVariable Long problemId, @PathVariable Long testCaseId, @PathVariable String userEmail, @RequestBody CodeRequest codeRequest) throws JsonProcessingException {
        try {
            User user = userRepository.findByEmail(userEmail);

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

            String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();

            Long assessmentId = problem.getAssessmentId();

            /* handles the checking of the constraints */
            switch (constraints) {
                case "integer":
                    handleIntegerConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
                case "string":
                    handleStringConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
                case "userDefinedFunction":
                    handleUserDefinedFunctionConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
                case "forLoop":
                    handleForLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
                case "whileLoop":
                    handleWhileLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
                case "forWhileLoop":
                    handleForWhileLoopConstraint(output, expectedOutput, problem, userEmail, testCaseId, testCase.getPoints(), codeRequest.getCode(), fullName, assessmentId);
                    break;
            }

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            log.error("Error occurred during compilation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* handles the saving of the activity */
    private void saveActivityTracker(String userEmail, Long testCaseId, Integer points, String code, String expectedOutput, String problemConstraint, String fullName, Long assessmentId) {
        ActivityTracker activityTracker = new ActivityTracker();

        activityTracker.setEmail(userEmail);
        activityTracker.setTestCaseId(testCaseId);
        activityTracker.setPoints(points);
        activityTracker.setCompiledCode(code);
        activityTracker.setExpectedOutput(expectedOutput);
        activityTracker.setProblemConstraint(problemConstraint);
        activityTracker.setFullName(fullName);
        activityTracker.setAssessmentId(assessmentId);

        activityTrackerRepository.save(activityTracker);
    }

    /* handles the Integer Constraint */
    private void handleIntegerConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        try {
            int integerValue = Integer.parseInt(output.trim());
            log.info("integer value ito: {}", integerValue);

            if (problem.getPattern().equals("exactMatch")) {
                if (Integer.parseInt(expectedOutput) == integerValue) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String integerValueString = String.valueOf(integerValue);

                if (integerValueString.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } catch (Exception e) {
            log.info("output is not integer: {}", output);
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

    /* handles the String Constraint */
    private void handleStringConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        try {
            if (problem.getPattern().trim().equals("exactMatch".trim())) {
                if (expectedOutput.trim().equals(output.trim())) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().trim().equals("patternMatching".trim())) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("String value ito: {} string pattern matching {} ", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } catch (Exception e) {
            log.info("output is not a string: {}", output);
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

    /* handles the User Defined Constraint */
    private void handleUserDefinedFunctionConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        String patternRegex = "def [a-zA-Z_][a-zA-Z0-9_]*\\((?:[^),]*,?\\s*)*\\)";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("User-defined function value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } else {
            log.info("User-defined function code does not match the expected pattern.");
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

    /* handles the for loop Constraint */
    private void handleForLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        String patternRegex = "for\\s+\\w+\\s+in\\s+\\w+:";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("For loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } else {
            log.info("For loop code does not match the expected pattern.");
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

    /* handles the while loop Constraint */
    private void handleWhileLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        String patternRegex = "while\\s+.+:";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("While loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } else {
            log.info("While loop code does not match the expected pattern.");
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

    /* handles the combination of for while loop Constraint */
    private void handleForWhileLoopConstraint(String output, String expectedOutput, Problem problem, String userEmail, Long testCaseId, Integer points, String code, String fullName, Long assessmentId) {
        String patternRegex = "(for\\s+\\w+\\s+in\\s+\\w+:)|(while\\s+.+:)";

        if (code.matches(patternRegex)) {
            if (problem.getPattern().equals("exactMatch")) {
                if (expectedOutput.equals(output)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            } else if (problem.getPattern().equals("patternMatching")) {
                String expectedOutputString = String.valueOf(expectedOutput);
                String stringValue = String.valueOf(output);
                log.info("For-While loop value ito: {} pattern matching {}", stringValue, output);
                if (stringValue.contains(expectedOutputString)) {
                    saveActivityTracker(userEmail, testCaseId, points, code, expectedOutput, problem.getProblemConstraint(), fullName, assessmentId);
                } else {
                    throw new ResponseStatusException(HttpStatus.RESET_CONTENT); // throw 205 Incorrect Answer
                }
            }
        } else {
            log.info("For-While loop code does not match the expected pattern.");
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT); // throw 206 Output doesn't follow the constraint
        }
    }

}
