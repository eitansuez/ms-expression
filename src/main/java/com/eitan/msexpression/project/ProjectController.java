package com.eitan.msexpression.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("#{ @servicesConfiguration.standalone ? '/' : '/projects' }")
public class ProjectController {
  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Project> get(@PathVariable Long id) {
    Optional<Project> maybeProject = projectService.getProject(id);
    return maybeProject.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Project> create(@RequestBody Project project) {
    Project savedProject = projectService.create(project);
    return new ResponseEntity<>(savedProject, CREATED);
  }

}
