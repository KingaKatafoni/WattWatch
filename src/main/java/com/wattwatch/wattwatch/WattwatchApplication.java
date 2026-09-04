package com.wattwatch.wattwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WattwatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(WattwatchApplication.class, args);
	}

}
