package com.bidpoint.backend.item.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private boolean isActive;
    private LocalDateTime dateEnds;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;


}
