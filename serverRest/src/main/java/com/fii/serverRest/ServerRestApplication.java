package com.fii.serverRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaLab11Application {

	public static void main(String[] args) {
		new Server().start();
		SpringApplication.run(JavaLab11Application.class, args);
	}

}
