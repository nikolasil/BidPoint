package com.bidpoint.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Collection<Role> roles = new ArrayList<>();
}
