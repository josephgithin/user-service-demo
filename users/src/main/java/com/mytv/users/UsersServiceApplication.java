package com.mytv.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class UsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

}
