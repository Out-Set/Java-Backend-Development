package com.tcl.messageService.pankajSir.email.service.configurations;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

@Configuration
public class GraphMailConfig {

    @Value("${integration.graph.microsoft.azure.client-id}")
    private String clientId;

    @Value("${integration.graph.microsoft.azure.tenant-id}")
    private String tenantId;

    @Value("${integration.graph.microsoft.azure.client-secret}")
    private String clientSecret;

    private GraphServiceClient<Request> initializeGraphForAppOnlyAuth() {

        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder().clientId(clientId)
                .tenantId(tenantId)
                .clientSecret(clientSecret).build();

        // Use the .default scope when using app-only auth
        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                List.of("https://graph.microsoft.com/.default"), clientSecretCredential);

        return GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
    }

    @Bean
    public GraphServiceClient<Request> graphClient() {
        return initializeGraphForAppOnlyAuth();
    }

}