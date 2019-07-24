package com.jdjhaha.findbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component("com.jdjhaha.findbook")
@SpringBootApplication
public class FindBookServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindBookServerApplication.class, args);
	}

}
