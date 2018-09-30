package com.example.demo.mongo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;

/**
 * Simple configuration that registers a {@link LoggingEventListener} to demonstrate mapping behavior when streaming
 * data.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
class ApplicationConfiguration {

//    @Bean
//    public LoggingEventListener mongoEventListener() {
//        return new LoggingEventListener();
//    }
}