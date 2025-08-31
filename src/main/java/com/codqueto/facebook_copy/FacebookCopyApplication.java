package com.codqueto.facebook_copy;

import com.codqueto.facebook_copy.dtos.request.PageRequest;
import com.codqueto.facebook_copy.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class FacebookCopyApplication implements CommandLineRunner {

	@Autowired
	PageService pageService;

	public static void main(String[] args) {
		SpringApplication.run(FacebookCopyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		pageService.delete("User3 Page");

	}
}
