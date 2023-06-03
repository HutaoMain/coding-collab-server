package com.codecat.codecat.controller;

import com.codecat.codecat.dto.CodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/compile")
    public ResponseEntity<String> compileCode(@RequestBody CodeRequest codeRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<CodeRequest> requestEntity = new RequestEntity<>(codeRequest, headers, HttpMethod.POST, URI.create(rapidApiUrl));

        log.info("code : {}", restTemplate.exchange(requestEntity, String.class));
        return restTemplate.exchange(requestEntity, String.class);
    }

}