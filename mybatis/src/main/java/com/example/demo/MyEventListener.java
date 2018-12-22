package com.example.demo;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public class MyEventListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void processAfterRollback(MyEvent event) {
        System.out.println("AFTER_ROLLBACK event");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processCommit(MyEvent event) {
        System.out.println("AFTER_COMMIT event");
    }
}
