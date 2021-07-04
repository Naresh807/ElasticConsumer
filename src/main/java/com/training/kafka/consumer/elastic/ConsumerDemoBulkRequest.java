package com.training.kafka.consumer.elastic;

import java.io.IOException;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;

public class ConsumerDemoBulkRequest {
	
	private final static String topic = "twitter_tweets";

	public static void main(String[] args) throws IOException {
		Logger logger = LoggerFactory.getLogger(ConsumerDemoBulkRequest.class);
		
		RestHighLevelClient elasticClient = ElasticClient.createClient();
		
		KafkaConsumer<String, String> consumer = KafkaElasticConsumer.createConsumer(topic);
		
		 while(true) {
			ConsumerRecords<String,String> records =  consumer.poll(Duration.ofMillis(100));
			
			if(records.count() > 0) {
			    logger.info("Received : {} records",records.count());
			
				BulkRequest bulkRequest = new BulkRequest();
				for (ConsumerRecord<String, String> record : records) {
					
					
					String id = ExtractIdFromTweets(record.value());
					
					IndexRequest indexRequest = new IndexRequest
			                  ("twitter", "tweets",id)
			                  .source(record.value(), XContentType.JSON);
	                 bulkRequest.add(indexRequest);  
	                 // IndexResponse indexResponse = elasticClient.index(indexRequest, RequestOptions.DEFAULT);
	                 // logger.info(id);
	                  try { 
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				BulkResponse bulkResponse = elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT); 
				//logger.info("Commiting offset");
				consumer.commitSync();
			}
		 }
		
		
		
		//elasticClient.close();

	}

	private static String ExtractIdFromTweets(String tweets) {
		
	        JsonParser jsonParser = new JsonParser();
	        String id = jsonParser.parse(tweets)
			            .getAsJsonObject()
			            .get("id_str")
			            .getAsString();
			return id;
		
	}

}
