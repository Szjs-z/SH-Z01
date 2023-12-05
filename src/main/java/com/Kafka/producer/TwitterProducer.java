package com.Kafka.producer;

import com.Kafka.config.*;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {
    final Logger logger = LoggerFactory.getLogger(TwitterProducer.class);
    private Client client;
    private KafkaProducer<String,String> producer;
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(30);
    private List<String> trackTerms = Lists.newArrayList("coronavirus");
    public TwitterProducer() { run(); }



    // Twitter Client
    public Client createTwitterClient(BlockingQueue<String> msgQueue){
        // Set up a commection:
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hbEndpoint = new StatusesFilterEndpoint();

        // Term that i want to search on Twitter:
        hbEndpoint.trackTerms(trackTerms);
        // Twitter API and tokens
        Authentication hosebirdAuth = new OAuth1(TwitterConfig.CONSUMER_KEYS, TwitterConfig.CONSUMER_SECRETS, TwitterConfig.TOKEN, TwitterConfig.SECRET);

        // Create a client:
        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client")
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hbEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        Client hbClient = builder.build();
        return hbClient;
    }

    private KafkaProducer<String,String> createKafkaProducer() {
        // Create produver properties
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BOOTSTRAPSERVERS);
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // Create safe Producer
        prop.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        prop.setProperty(ProducerConfig.ACKS_CONFIG,KafkaConfig.ACKS_CONFIG);
        prop.setProperty(ProducerConfig.RETRIES_CONFIG,KafkaConfig.RETRIES_CONFIG);
        prop.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,KafkaConfig.MAX_IN_FLIGHT_CONN);

        // Additinal settings for hight throughput producer
        prop.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG,KafkaConfig.COMPRESSION_TYPE);
        prop.setProperty(ProducerConfig.LINGER_MS_CONFIG,KafkaConfig.LINGER_CONFIG);
        prop.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,KafkaConfig.BATCH_SIZE);

        // Create producer object:
        return new KafkaProducer<String,String>(prop);

    }

    private void run() {
        logger.info("Setting up");

        // Call the Twitter Client:
        client = createTwitterClient(msgQueue);
        client.connect();

        // Create kafka Producer
        producer = createKafkaProducer();

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    logger.info("Application is not stopping");
                    client.stop();
                    logger.info("Closing Producer");
                    producer.close();
                    logger.info("Finished closing");
                }
        ));

        // Send Tweets to kafka
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if (msg != null) {
                logger.info(msg);
                producer.send(new ProducerRecord<String, String>(KafkaConfig.TOPIC, null, msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e !=null) {
                            logger.error("Some error happend",e);
                        }
                    }
                });
            }
        }
        logger.info("\n Application END");
    }

}