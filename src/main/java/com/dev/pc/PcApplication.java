package com.dev.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PcApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(PcApplication.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		String password = "123456";

		for (int i = 0; i < 4; i++){
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(PcApplication.class, args);
		logger.info("SERVER ONLINE");
	}
}
