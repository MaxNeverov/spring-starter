package com.mndev.spring.database.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component(value = "pool1")
public class ConnectionPool {
    private final String username;
    private final Integer poolSize;
    public ConnectionPool(@Value("${db.username}") String username,
                          @Value("${db.pool.size}") Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        log.info("Init...");
    }

    @PreDestroy
    private void destroy() {
        log.info("Clean...");
    }
}


