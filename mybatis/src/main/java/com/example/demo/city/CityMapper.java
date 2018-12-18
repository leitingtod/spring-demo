package com.example.demo.mapper;

import com.example.demo.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {
    @Select("select * from city where state = #{state}")
    List<City> findByState(@Param("state") String state);

    @Select("select * from city where country = #{country}")
    List<City> findByCountry(@Param("country") String country);

    @Select("select * from city")
    List<City> findAll();
}
