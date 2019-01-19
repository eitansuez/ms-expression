package com.eitan.msexpression.project;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Profile("projects")
@Service
@Primary
public class ProjectService implements ProjectClient {
  private final ProjectRepository repository;

  public ProjectService(ProjectRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Project> getProject(Long id) {
    return repository.findById(id);
  }

  public Project create(Project project) {
    return repository.save(project);
  }
}
