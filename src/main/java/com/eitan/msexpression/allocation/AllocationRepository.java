package com.eitan.msexpression.allocation;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("allocations")
@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
}
