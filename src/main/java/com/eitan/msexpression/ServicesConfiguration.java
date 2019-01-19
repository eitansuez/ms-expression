package com.eitan.msexpression;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="services")
public class ServicesConfiguration {
  private final List<String> active = new ArrayList<>();

  public List<String> getActive() {
    return active;
  }

  public boolean isServiceActive(String serviceName) {
    return active.contains(serviceName);
  }

  public boolean isStandalone() {
    return active.size() == 1;
  }
}
