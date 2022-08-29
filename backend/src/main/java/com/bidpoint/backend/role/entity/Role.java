package com.bidpoint.backend.role.entity;

import com.bidpoint.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }
}
