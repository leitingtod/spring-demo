package com.example.demo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/*
* https://leetcode-cn.com/problems/rank-scores/
*/

@Data
@AllArgsConstructor
public class Score {
    private long id;
    private BigDecimal score;
}
