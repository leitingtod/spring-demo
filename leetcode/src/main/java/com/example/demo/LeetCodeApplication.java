package com.example.demo;

import com.example.demo.mysql.Score;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class LeetCodeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LeetCodeApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating table scores");

        String sql = "DROP TABLE IF EXISTS scores";
        jdbcTemplate.execute(sql);

        sql = "CREATE TABLE scores(id INT PRIMARY KEY AUTO_INCREMENT, score DECIMAL(10,2))";

        jdbcTemplate.execute(sql);

        // Split up the array of whole names into an array of first/last names
        List<Object[]> scores = Stream.of(3.50, 3.65, 4.00, 3.85, 4.00, 3.65)
                .map(e -> new BigDecimal[]{BigDecimal.valueOf(e)}).collect(Collectors.toList());


        // Use a Java 8 stream to print out each tuple of the list
        scores.forEach(name -> log.info(String.format("Inserting scores record for %s", name)));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO scores(score) VALUES (?)", scores);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, score FROM scores",
                (rs, rowNum) -> new Score(rs.getInt("id"), rs.getBigDecimal("score"))
        ).forEach(score -> log.info(score.toString()));
    }
}
