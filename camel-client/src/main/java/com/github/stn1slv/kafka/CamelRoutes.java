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
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest().post("/api/uppercase").to("direct:uppercase");

        from("direct:uppercase")
            .log(LoggingLevel.INFO, "New request: ${body}")//;
            .bean(new KafkaRequestReplyBean(),"call");

    }
}