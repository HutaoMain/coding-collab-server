package com.codecat.codecat.dto;

import lombok.Data;

@Data
public class CodeRequest {
    private String language;
    private String version;
    private String code;
    private String input;
}
