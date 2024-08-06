package com.security.implementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.security.implementation"})
public class ImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImplementationApplication.class, args);
	}
}
