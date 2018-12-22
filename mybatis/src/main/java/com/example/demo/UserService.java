package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    public UserService(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }

    public User getUser(long id) {
        return userMapper.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateAccount(double amount, long id, int delay) throws Exception {
//        applicationEventPublisher.publishEvent(new MyEvent("MY"));
        try {
            User user = userMapper.findById(id);
            user.setAccount(BigDecimal.valueOf(user.getAccount().doubleValue()+amount));
            if(userMapper.updateAccount(user) == 0) {
                throw new UpdateException("update account failed");
            }
            Thread.sleep(delay); // 延时提交
        } catch (Exception e) {
            throw new UpdateException("update account failed");
        }
    }
}
