package com.bidpoint.backend.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Category {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
