package com.eitan.msexpression.allocation;

import com.eitan.msexpression.project.Project;
import com.eitan.msexpression.project.ProjectClient;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("allocations")
@Service
@Primary
public class AllocationService implements AllocationClient {
  private final AllocationRepository repository;
  private final ProjectClient projectClient;

  public AllocationService(AllocationRepository repository,
                           ProjectClient projectClient) {
    this.repository = repository;
    this.projectClient = projectClient;
  }

  public List<Allocation> getAllocations() {
    return repository.findAll();
  }

  public Optional<Allocation> getAllocation(Long id) {
    return repository.findById(id);
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
