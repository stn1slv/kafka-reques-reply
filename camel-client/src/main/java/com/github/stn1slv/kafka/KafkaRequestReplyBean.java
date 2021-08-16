package com.github.stn1slv.kafka;

import org.apache.camel.Handler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

@Component
public class KafkaRequestReplyBean {
    // @Value("${kafka.request.topic}")
    // private String requestTopic;

    // @Autowired
    // public KafkaRequestReplyBean(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate) {
    //     this.replyingKafkaTemplate = replyingKafkaTemplate;
    // }

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
    // private final ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate; 

    // @Handler
    public String call() throws Exception {
        String inputValue = "test";
        ProducerRecord<String, String> record = new ProducerRecord<>("kRequests", null, "STD001", inputValue);
        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> response = future.get();
        return response.value();
    }
}
