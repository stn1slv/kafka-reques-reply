package com.github.stn1slv.kafka;

import com.github.stn1slv.kafka.KafkaRequestReplyBean;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("netty-http:http://0.0.0.0:{{listener.port}}/api/uppercase").streamCaching()
            .log(LoggingLevel.INFO, "Request: ${body}")
            .bean(KafkaRequestReplyBean.class)
            .log(LoggingLevel.INFO, "Response: ${body}");
    }
}