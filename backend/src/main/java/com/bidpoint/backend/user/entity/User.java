package com.bidpoint.backend.user.entity;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="application_user")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(unique=true)
    private String username;
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean isApproved;

    private String address;
    private String phone;
    private String mail;
    private String afm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

}
