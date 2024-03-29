package com.bidpoint.backend.user.entity;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="application_user")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence")
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique=true)
    private String username;

    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean approved;

    private String address;

    private String phone;

    private String mail;

    private String afm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new LinkedHashSet<>();
    public void addRole(Role role){ this.roles.add(role); }
    public void removeRole(Role role){ this.roles.remove(role); }

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Item> items = new LinkedHashSet<>();
    public void addItem(Item item){ this.items.add(item); }
    public void removeItem(Item item){ this.items.remove(item); }

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();
    public void addBid(Bid bid){
        this.bids.add(bid);
    }
    public void removeBid(Bid bid){
        this.bids.remove(bid);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Item> visitedItems = new LinkedHashSet<>();
    public void addVisitedItems(Item item){
        this.visitedItems.add(item);
    }
    public void removeVisitedItems(Item item){
        this.visitedItems.remove(item);
    }

    private Long sellerRating;
    private Long bidderRating;
}
