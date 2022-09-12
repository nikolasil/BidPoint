package com.bidpoint.backend.item.entity;

import com.bidpoint.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.StringType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static java.time.ZoneOffset.UTC;
import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    private BigDecimal startingPrice;

    private BigDecimal currentPrice;

    private BigDecimal buyPrice;

    private Integer numberOfBids;

    @Column(nullable = false)
    private boolean active;

    private ZonedDateTime dateEnds;
    public boolean isEnded() { return ZonedDateTime.now(UTC).isAfter(this.dateEnds);}

    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime dateCreated = ZonedDateTime.now(UTC);

    @UpdateTimestamp
    private ZonedDateTime dateUpdated;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = EAGER)
    private Set<Bid> bids = new LinkedHashSet<>();
    public void addBid(Bid bid) { this.bids.add(bid); }
    public void removeBid(Bid bid) { this.bids.remove(bid); }
    public List<Bid> getSortedBids(){ return this.bids.stream().sorted(Comparator.comparing(Bid::getDateCreated).reversed()).toList(); }

    @JsonIgnoreProperties(value = "items")
    @ManyToMany(mappedBy = "items", fetch = EAGER)
    private Set<Category> categories = new LinkedHashSet<>();
    public void addCategory(Category category) { this.categories.add(category); }
    public void removeCategory(Category category) { this.categories.remove(category); }

    @JsonManagedReference
    @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = EAGER)
    private Set<Image> images = new LinkedHashSet<>();
    public void addImage(Image image) { this.images.add(image); }
    public void removeImage(Image image) { this.images.remove(image); }
}
