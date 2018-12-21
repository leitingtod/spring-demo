package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserRepository {
    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    public UserService(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public User getUser(long id) {
        return userMapper.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateAccount(double amount, long id, int delay) throws Exception {
//        applicationEventPublisher.publishEvent(new MyEvent("TL", delay));
        try {
            System.out.printf("--->>  ------- %s ---------------------\n", delay);
            User user = userMapper.findById(id);
            user.setAccount(BigDecimal.valueOf(user.getAccount().doubleValue()+amount));
            System.out.printf("===>>  ======= %s =====================\n", user);
            userMapper.updateAccount(user);
            Thread.sleep(delay); // 延时提交
            System.out.printf("===>>  ======= %s =====================\n", delay);
        } catch (Exception e) {
            throw new RuntimeException("update failed: " + delay);
        }

        /*
         * 1. 设置 MySQL 隔离级别为
         *    set @@session.tx_isolation = 'read-uncommitted'; set @@global.tx_isolation = 'read-uncommitted';
         * 2. select @@tx_isolation;select @@global.tx_isolation;
         * 3. 启动一个事务更新用户的account -900，延时 8s 提交
         * 4. 在提交前查看用户account 值为 1500 - 900 = 600
         * 5. 设置 MySQL 隔离级别为
         *    set @@session.tx_isolation = 'read-committed'; set @@global.tx_isolation = 'read-committed';
         */
    }
}
