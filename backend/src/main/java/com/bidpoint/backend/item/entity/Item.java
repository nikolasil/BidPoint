package com.bidpoint.backend.item.entity;

import com.bidpoint.backend.user.entity.User;
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
import java.util.*;

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
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = EAGER)
    private Set<Bid> bids = new LinkedHashSet<>();



    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = EAGER)
    private Set<Image> images = new LinkedHashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean active;

    private LocalDateTime dateEnds;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;


    public boolean isEnded() { return LocalDateTime.now().isAfter(this.dateEnds);}

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public List<Bid> getSortedBids(){
        return this.bids.stream().sorted(Comparator.comparing(Bid::getDateCreated).reversed()).toList();
    }
}
