package com.auction.platform.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bid_generator_id")
    @SequenceGenerator(name = "bid_generator_id", sequenceName = "bid_sequence_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }
}
