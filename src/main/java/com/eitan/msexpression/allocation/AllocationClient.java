package com.eitan.msexpression.allocation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(value = "allocations", qualifier = "feignAllocationsClient")
public interface AllocationClient {

  @PostMapping
  Optional<Allocation> create(@RequestBody Allocation allocation);
}
