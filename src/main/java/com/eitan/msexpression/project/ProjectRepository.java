package com.eitan.msexpression.project;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("projects")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
