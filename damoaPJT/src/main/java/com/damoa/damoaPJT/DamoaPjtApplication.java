package com.damoa.damoaPJT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DamoaPjtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DamoaPjtApplication.class, args);
	}

}
