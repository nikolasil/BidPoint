package com.bidpoint.backend.item.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String description;

    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal buyPrice;

    private Integer numberOfBids;

    @JsonManagedReference
    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = EAGER)
    private Set<Image> images = new LinkedHashSet<>();

    @Column(nullable = false)
    private boolean active;
    private LocalDateTime dateEnds;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;



}
