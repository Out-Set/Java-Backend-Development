package com.workflow.camunda.config;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GroovyConfig {

    @Value("${groovy.allowed-star-imports}")
    private List<String> allowedStarImports;

    @Bean
    public CompilerConfiguration groovyCompilerConfiguration() {

        CompilerConfiguration config = new CompilerConfiguration();
        // List<String> allowedStarImports =
        // Arrays.asList("java.lang","java.time","java.util","org.springframework.util","org.apache.commons.collections4");
        // Create SecureASTCustomizer
        SecureASTCustomizer secure = new SecureASTCustomizer();
        // secure.setDisallowedImports(Collections.singletonList("java.util.Date"));
        // secure.setDisallowedStarImports(Collections.singletonList("java.util"));
        // secure.setAllowedImports(Arrays.asList("java.lang.Math"));
        secure.setAllowedStarImports(allowedStarImports);
        secure.setIndirectImportCheckEnabled(true);

        // Add the SecureASTCustomizer to the configuration
        config.addCompilationCustomizers(secure);
        return config;
    }
}