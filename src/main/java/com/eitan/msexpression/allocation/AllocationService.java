package com.eitan.msexpression.allocation;

import com.eitan.msexpression.project.Project;
import com.eitan.msexpression.project.ProjectClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllocationService implements AllocationClient {
  private final AllocationRepository repository;
  private final ProjectClient projectClient;

  public AllocationService(AllocationRepository repository,
                           ProjectClient runtimeProjectClient) {
    this.repository = repository;
    this.projectClient = runtimeProjectClient;
  }

  @Override
  public Optional<Allocation> create(Allocation allocation) {
    Optional<Project> project = projectClient.getProject(allocation.getProjectId());
    if (project.isPresent() && project.get().isActive()) {
      Allocation savedAllocation = repository.save(allocation);
      return Optional.of(savedAllocation);
    }
    return Optional.empty();
  }

}
