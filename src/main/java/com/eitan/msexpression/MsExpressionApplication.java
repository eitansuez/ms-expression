package com.eitan.msexpression;

import com.eitan.msexpression.project.ProjectClient;
import com.eitan.msexpression.project.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties(ServicesConfiguration.class)
@SpringBootApplication
public class MsExpressionApplication {

  private final Logger log = LoggerFactory.getLogger(MsExpressionApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MsExpressionApplication.class, args);
  }

  @Bean
  public ProjectClient runtimeProjectClient(
      ServicesConfiguration servicesConfiguration,
      ProjectService projectService,
      @Qualifier("feignProjectsClient") ProjectClient feignProjectsClient) {
    return servicesConfiguration.isServiceActive("projects") ? projectService : feignProjectsClient;
  }

}
