package com.savan.ruleengine.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class OpenSearchConfig {

    @Bean
    public RestHighLevelClient openSearchClient() {
        try {
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    new AuthScope("localhost", 9200),
                    new UsernamePasswordCredentials("admin", "@qazxsW$345#")
            );

            // Disable SSL validation
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(null, (certificate, authType) -> true)
                    .build();

            return new RestHighLevelClient(
                    RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "https"))
                            .setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) ->
                                    httpClientBuilder
                                            .setDefaultCredentialsProvider(credentialsProvider)
                                            .setSSLContext(sslContext)
                                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                            )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error creating OpenSearch client", e);
        }
    }
}
