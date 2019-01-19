package com.eitan.msexpression.allocation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("#{${allocations.standalone} ? '/' : '/allocations'}")
public class AllocationController {
  private final AllocationService service;

  public AllocationController(AllocationService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Allocation> create(@RequestBody Allocation allocation) {
    Optional<Allocation> maybePersistedAllocation = service.create(allocation);
    if (maybePersistedAllocation.isPresent()) {
      return new ResponseEntity(maybePersistedAllocation.get(), HttpStatus.CREATED);
    }
    return ResponseEntity.badRequest().build();
  }
}
