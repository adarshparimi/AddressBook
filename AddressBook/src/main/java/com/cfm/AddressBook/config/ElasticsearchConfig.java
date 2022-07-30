package com.cfm.AddressBook.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;

@Slf4j
@EnableElasticsearchRepositories(basePackages = "com.cfm.AddressBook.repository")
@ComponentScan({"com.test","com.bean"})
@Configuration
public class ElasticsearchConfig {
    @Value("${elasticsearch:host}:${elasticsearch.port}")
    private String elasticsearchHostWithPort;

    @Value("${elasticsearch.ssl}")
    private boolean elasticsearchSsl;

    @Value("${elasticsearch.username:null}")
    private String elasticsearchUserName;

    @Value("${elasticsearch.password:null}")
    private String elasticsearchPassword;

    @Value("${elasticsearch.connection-timeout-seconds}")
    private int connectionTimeout;

    @Value("${elasticsearch.socket-timeout-seconds}")
    private int socketTimeout;

    @Bean
    public RestHighLevelClient elasticsearchClient(){
        log.info("Elasticserach Client Connection - hostWithPort = {}", elasticsearchHostWithPort);
        log.info("Elasticsearch Client Connection - withSsl = {}", elasticsearchSsl);

        final ClientConfiguration.MaybeSecureClientConfigurationBuilder builder =
                ClientConfiguration.builder().connectedTo(elasticsearchHostWithPort);

        if(elasticsearchSsl){
            builder.usingSsl();
            builder.withBasicAuth(elasticsearchUserName,elasticsearchPassword);
        }

        builder.withConnectTimeout(Duration.ofSeconds(connectionTimeout));
        builder.withSocketTimeout(Duration.ofSeconds(socketTimeout));

        return RestClients.create(builder.build()).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(){
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

}