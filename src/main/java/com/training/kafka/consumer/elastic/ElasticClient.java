package com.training.kafka.consumer.elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticClient {

	public static RestHighLevelClient createClient() {
		
		String hostname = "nareshtraining-3232373452.eu-west-1.bonsaisearch.net";
		String username = "fjr9z7ixmp";
		String password = "z7lje5jwcb";
		
		//don't do if you run a local ES
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, 
				               new UsernamePasswordCredentials(username,password));
		
		RestClientBuilder builder = RestClient.builder(
				              new HttpHost(hostname, 443, "https"))
				              .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
								
								public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
									return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)	;
								}
							});
		
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
}
