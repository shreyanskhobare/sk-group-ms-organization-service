package com.sk.group.ms.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.sk.group.shared.*")
@ComponentScan("com.sk.group.*")
@EnableFeignClients(basePackages = "com.sk.group.*")
public class SkGroupMsOrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkGroupMsOrganizationServiceApplication.class, args);
	}

}
