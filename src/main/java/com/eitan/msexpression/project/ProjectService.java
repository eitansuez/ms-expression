package com.eitan.msexpression.project;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService implements ProjectClient {
  private final ProjectRepository repository;

  public ProjectService(ProjectRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Project> getProject(Long id) {
    return repository.findById(id);
  }
}
