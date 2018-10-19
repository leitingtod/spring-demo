package com.example.demo.mysql;

import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getOne(Long id);

    @Insert("INSERT INTO user(id, name,email) VALUES(#{id}, #{name}, #{email})")
    //@Options(useGeneratedKeys=true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE user SET name=#{name},email=#{email} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);
}
