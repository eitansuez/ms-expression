package com.eitan.msexpression.project;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Profile("!projects")
@FeignClient(
    value = "projects",
    qualifiers = {"feignProjectsClient"},
    decode404 = true,
    primary = false
)
public interface ProjectClient {
  @GetMapping("/projects/{id}")
  Optional<Project> getProject(@PathVariable Long id);
}
