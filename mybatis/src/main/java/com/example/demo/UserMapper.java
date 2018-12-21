package com.example.demo;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from rbac_user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from rbac_user")
    List<User> findAll();

    @Insert("insert into rbac_user (name, password, email) values (#{name}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Insert("insert into rbac_user (name, password, email) values (#{name}, #{password}, #{email})")
    int insert1(User user);

    @Insert("insert into rbac_user (name, password, email) values (#{name}, #{password}, #{email})")
    @SelectKey(statement = "select LAST_INSERT_ID()",
            keyProperty = "id",
            resultType = Long.class,
            before = false)
    int insert2(User user);

    @Update("update rbac_user set name=#{name}, password=#{password}, email=#{email} where id=#{id}")
    int update(User user);

    @Update("update rbac_user set password=#{password} where id=#{id}")
    int updatePassword(String password, Long id);

    @Update("update rbac_user set account=#{account}, version=#{version}+1 where id=#{id} and version=#{version}")
    int updateAccount(User user);

    @Update("delete from rbac_user where id=#{id}")
    int delete(Long id);
}
