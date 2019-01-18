package com.eitan.msexpression.project;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "projects", qualifier = "feignProjectsClient")
public interface ProjectClient {

  @GetMapping("/{id}")
  Optional<Project> getProject(@PathVariable Long id);
}
