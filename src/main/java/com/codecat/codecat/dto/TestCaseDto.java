package com.codecat.codecat.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {

    private Long id;
    private String expectedOutput;
    private Integer points;
    private Long problemId;
}
