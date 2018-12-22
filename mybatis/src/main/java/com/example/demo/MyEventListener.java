package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public class MyEventListener {
    private static final Logger logger = LoggerFactory.getLogger(MyEventListener.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void processAfterRollback(MyEvent event) {
        logger.info("update failed, rollback");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processCommit(MyEvent event) {
        logger.info("update success, committed");
    }
}
