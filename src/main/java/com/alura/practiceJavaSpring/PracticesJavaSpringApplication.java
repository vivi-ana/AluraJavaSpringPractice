package com.alura.practiceJavaSpring;

import com.alura.practiceJavaSpring.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticesJavaSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PracticesJavaSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.showMenu();
	}
}
