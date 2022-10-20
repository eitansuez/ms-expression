package com.eitan.msexpression.allocation;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Profile("allocations")
@RestController
@RequestMapping("/allocations")
public class AllocationController {
  private final AllocationService service;

  public AllocationController(AllocationService service) {
    this.service = service;
  }

  @GetMapping
  public List<Allocation> list() {
    return service.getAllocations();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Allocation> get(@PathVariable Long id) {
    Optional<Allocation> maybeAllocation = service.getAllocation(id);
    return maybeAllocation.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
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
