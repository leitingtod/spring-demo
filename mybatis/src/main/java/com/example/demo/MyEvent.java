package com.example.demo;

import org.springframework.context.ApplicationEvent;

class MyEvent extends ApplicationEvent {

    MyEvent(Object o) {
        super(o);
    }
}