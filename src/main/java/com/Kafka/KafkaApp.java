package com.Kafka;

import com.Kafka.producer.TwitterProducer;


public class KafkaApp {
    public static void main(String[] args) {
        try {
            new TwitterProducer().run();
        }catch (Exception e){
            System.out.println(e.getStackTrace().getClass());
        }
    }
}


