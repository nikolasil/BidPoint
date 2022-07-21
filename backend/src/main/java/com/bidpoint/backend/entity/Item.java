package com.bidpoint.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Item {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private BigDecimal currently;
    private BigDecimal buy_price;
    private BigDecimal first_bid;
    private Integer number_of_bids;
}
