package com.github.stn1slv.kafka;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {
    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    @Value("${kafka.request.topic}")
    private String requestTopic;

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

    @PostMapping("/api/uppercase")
    public ResponseEntity<String> getObject(@RequestBody String inputValue)
            throws InterruptedException, ExecutionException {

        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, null, "STD001", inputValue);

        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, String> response = future.get();

        logger.info("Client received: " + response.value());
        return new ResponseEntity<>(response.value(), HttpStatus.OK);
    }
}
