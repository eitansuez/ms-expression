package com.eitan.msexpression;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ServicesConfiguration {
  private final List<String> activeServices;

  public ServicesConfiguration(Environment environment) {
    this.activeServices = Arrays.asList(environment.getActiveProfiles());
  }

  public boolean isServiceActive(String serviceName) {
    return activeServices.contains(serviceName);
  }

  public boolean isStandalone() {
    return activeServices.size() == 1;
  }
}
