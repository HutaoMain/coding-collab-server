package com.codecat.codecat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDto {

    private Long id;
    private String problemCode;
    private String problemName;
    private String problemDescription;
    private String programmingLanguage;
    private String problemConstraint;
    private String pattern;
    private Long assessmentId;
    private List<TestCaseDto> testCase;

}
