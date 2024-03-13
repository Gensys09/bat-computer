package com.batman.batcomputer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
public class BatComputerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatComputerApplication.class, args);
	}

}
