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

    @ManyToMany
    @JoinTable(name = "category_items",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "items_id"))
    private Set<Item> items = new LinkedHashSet<>();

}
