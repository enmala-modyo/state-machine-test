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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"com.modyo.test.statemachine.adapters.persistence"},
    transactionManagerRef = "transactionManager",
    entityManagerFactoryRef = "entityManagerFactory"
)
@RequiredArgsConstructor
public class ModyoDbConfig {

  private static final String PERSISTENCE_ADAPTER_PACKAGE = "com.modyo.test.statemachine.adapters.persistence";
  private static final String PROPERTIES_PREFIX = "spring.datasource.modyo";
  private final Environment env;

  @Bean
  @Primary
  public DataSource modyoDataSource() {
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

  @Bean(name = "entityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean
  entityManagerFactory(
      EntityManagerFactoryBuilder builder
  ) {
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty(PROPERTIES_PREFIX.concat(".ddl-auto")));
    return builder
        .dataSource(modyoDataSource())
        .packages(PERSISTENCE_ADAPTER_PACKAGE)
        .persistenceUnit("modyo")
        .properties(properties)
        .build();
  }

  @Bean(name = "transactionManager")
  @Primary
  public PlatformTransactionManager transactionManager(
      @Qualifier("entityManagerFactory") EntityManagerFactory
          entityManagerFactory
  ) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
