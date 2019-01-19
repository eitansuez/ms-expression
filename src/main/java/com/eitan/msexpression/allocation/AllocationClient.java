package com.eitan.msexpression.allocation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Profile("!allocations")
@FeignClient(
    value = "allocations",
    qualifier = "feignAllocationsClient",
    decode404 = true,
    primary = false
)
public interface AllocationClient {

  @PostMapping
  Optional<Allocation> create(@RequestBody Allocation allocation);
}
