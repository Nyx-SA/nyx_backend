package com.github.grupo_s.nyx_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NyxApplication {

	public static void main(String[] args) {
		SpringApplication.run(NyxApplication.class, args);
	}

}
