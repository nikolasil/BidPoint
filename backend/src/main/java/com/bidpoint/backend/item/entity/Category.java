package com.bidpoint.backend.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "category_sequence")
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany
    @JoinTable(name = "category_items",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "items_id"))
    private Set<Item> items = new LinkedHashSet<>();
    public void addItem(Item item) {
        this.items.add(item);
    }
    public void removeItem(Item item) { this.items.remove(item); }
}
