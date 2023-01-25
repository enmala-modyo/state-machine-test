package com.modyo.test.statemachine.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.statemachine.data.jpa.JpaStateRepository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackageClasses = JpaStateRepository.class,
    transactionManagerRef = "stateMachineTransactionManager",
    entityManagerFactoryRef = "stateMachineEntityManagerFactory"
)
@RequiredArgsConstructor
public class StateMachineDbConfig {

  private static final String PERSISTENCE_ADAPTER_PACKAGE = "org.springframework.statemachine.data.jpa";
  private static final String PROPERTIES_PREFIX = "spring.datasource.statemachine";
  private final Environment env;

  @Bean
  public DataSource statemachineDataSource() {
    HikariDataSource dataSource
        = new HikariDataSource();
    Optional.ofNullable(env.getProperty(PROPERTIES_PREFIX.concat(".driverClassName")))
        .ifPresent(dataSource::setDriverClassName);
    dataSource.setJdbcUrl(env.getProperty(PROPERTIES_PREFIX.concat(".url")));
    dataSource.setUsername(env.getProperty(PROPERTIES_PREFIX.concat(".username")));
    dataSource.setPassword(env.getProperty(PROPERTIES_PREFIX.concat(".password")));

    Optional.ofNullable(env.getProperty(PROPERTIES_PREFIX.concat(".hikari.connectionTimeout")))
        .map(Long::parseLong)
        .ifPresent(dataSource::setConnectionTimeout);
    Optional.ofNullable(env.getProperty(PROPERTIES_PREFIX.concat(".hikari.idleTimeout")))
        .map(Long::parseLong)
        .ifPresent(dataSource::setIdleTimeout);
    Optional.ofNullable(env.getProperty(PROPERTIES_PREFIX.concat(".hikari.maxLifetime")))
        .map(Long::parseLong)
        .ifPresent(dataSource::setMaxLifetime);
    Optional.ofNullable(env.getProperty(PROPERTIES_PREFIX.concat(".hikari.maximumPoolSize")))
        .map(Integer::parseInt)
        .ifPresent(dataSource::setMaximumPoolSize);

    return dataSource;
  }

  @Bean(name = "stateMachineEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean
  entityManagerFactory(
      EntityManagerFactoryBuilder builder
  ) {
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty(PROPERTIES_PREFIX.concat(".ddl-auto")));
    return builder
        .dataSource(statemachineDataSource())
        .packages(PERSISTENCE_ADAPTER_PACKAGE)
        .persistenceUnit("modyo")
        .properties(properties)
        .build();
  }

  @Bean(name = "stateMachineTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("stateMachineEntityManagerFactory") EntityManagerFactory
          entityManagerFactory
  ) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
