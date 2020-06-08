package com.example.demo.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class ElasticClientConfig {
	@Value(value="${elas.server.hostName}")
	private String serverHost;
	
	@Value(value="${elas.server.port}")
	private int serverPort;
	@Value(value="${elas.cluster.name}")
	private String clusterName;

	@Bean
	public RestHighLevelClient getClient() throws UnknownHostException{
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost (serverHost, serverPort, "http"))
				.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
		            @Override
		            public Builder customizeRequestConfig(Builder requestConfigBuilder) {
		                requestConfigBuilder.setConnectTimeout(5000);
		                requestConfigBuilder.setSocketTimeout(40000);
		                requestConfigBuilder.setConnectionRequestTimeout(1000);
		                return requestConfigBuilder;
		            }
		        }));
		return client;
	}

	@Bean 
	public RestClientBuilder getHttpHost(){
		return RestClient.builder(new HttpHost (serverHost, serverPort, "http"));
	}
}
