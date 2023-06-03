package com.codecat.codecat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@SpringBootApplication
public class CodecatApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodecatApplication.class, args);
	}

	@Bean
	public HttpFirewall httpFirewall() {
		return new DefaultHttpFirewall();
	}
}
