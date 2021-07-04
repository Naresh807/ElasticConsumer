package com.training.kafka.consumer.elastic;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaElasticConsumer {
	final Logger logger = LoggerFactory.getLogger(KafkaElasticConsumer.class);
	private final static String bootStrapServer = "127.0.0.1:9092";
	private final static String groupId = "kafka-demo-elasticsearch"; 
	
	
	public static KafkaConsumer<String, String> createConsumer(String topic) {
		 
		 // create Producer properties
		 Properties properties = new Properties();
		 
		 properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		 properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		 properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		 properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		 properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		 properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		 /*properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest/latest/none");
					 //earliest = would read data from start
					 //latest = would read from latest
					 //none= will throw error if no offset is saved */
		 
		 //Create consumer 
		 KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		 consumer.subscribe(Arrays.asList(topic));
		 return consumer;

	}

}
