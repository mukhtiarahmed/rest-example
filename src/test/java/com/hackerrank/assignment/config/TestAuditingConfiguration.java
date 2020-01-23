package com.hackerrank.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class TestAuditingConfiguration {

    @Bean("auditorAware")
    @Primary
    public AuditorAware<String> testAuditorProvider() {
        return () -> Optional.of("Test  Auditor");
    }

}
