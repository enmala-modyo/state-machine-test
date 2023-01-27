package com.modyo.test.statemachine;

import com.modyo.test.statemachine.archunit.HexagonalArchitecture;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

class DependencyRuleTests {

  private static final String rootPackageName = "com.modyo.test.statemachine";
  private static final String domainPackage = "domain";
  private static final String applicationPackage = "application";
  private static final String adaptersPackage = "adapters";

  @Test
  void validateRegistrationContextArchitecture() {
    HexagonalArchitecture.boundedContext(rootPackageName)
        .withDomainLayer(domainPackage)
        .withAdaptersLayer(adaptersPackage)
        .incoming("web")
        .outgoing("persistence")
        .and()
        .withApplicationLayer(applicationPackage)
        .services("service")
        .incomingPorts("port.in")
        .outgoingPorts("port.out")
        .and()
        .withConfiguration("configuration")
        .check(new ClassFileImporter()
            .importPackages(rootPackageName));
  }

}
