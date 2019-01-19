package com.eitan.msexpression;

import com.eitan.msexpression.project.ProjectClient;
import com.eitan.msexpression.project.ProjectService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MsExpressionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExpressionApplication.class, args);
	}

	@Bean
	public ProjectClient runtimeProjectClient(
			@Value("${allocations.standalone}") boolean allocationsStandalone,
			ProjectService projectService,
			@Qualifier("feignProjectsClient") ProjectClient feignProjectsClient) {
		if (allocationsStandalone) {
			return feignProjectsClient;
		} else {
			return projectService;
		}
	}

}
