package com.bidpoint.backend.item.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique=true)
    private String name;
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private Set<Item> items = new LinkedHashSet<>();

}
