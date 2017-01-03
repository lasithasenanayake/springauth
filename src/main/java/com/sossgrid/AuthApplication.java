package com.sossgrid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sossgrid.configuration.ConfigurationManager;

@SpringBootApplication
public class AuthApplication {
	public static void main(String[] args) {
		ConfigurationManager.Load();
		SpringApplication.run(AuthApplication.class, args);
	}
}
