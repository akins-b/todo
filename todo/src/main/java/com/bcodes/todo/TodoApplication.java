package com.bcodes.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class TodoApplication {

	private static final Logger log = LoggerFactory.getLogger(TodoApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(TodoApplication.class, args);
	}
}

