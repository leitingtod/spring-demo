package com.example.demo.rbac;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserRepository {

    @Autowired
    private SqlSession sqlSession;

    private UserMapper getMapper() {
        return this.sqlSession.getMapper(UserMapper.class);
    }

    public void updateAccount(BigDecimal account, long id, String commit, int delay) {
        double delta = getMapper().findById(id).getAccount().doubleValue() + account.doubleValue();
        if (delta >= 0.00) {
            getMapper().updateAccount(BigDecimal.valueOf(delta), id);
        } else {
            throw new RuntimeException("余额不足...");
        }
    }
}
