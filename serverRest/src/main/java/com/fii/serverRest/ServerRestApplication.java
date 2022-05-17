package com.fii.serverRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerRestApplication {

	public static void main(String[] args) {
		new Server().start();
		SpringApplication.run(ServerRestApplication.class, args);
	}

}
