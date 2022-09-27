package com.bidpoint.backend.role.entity;

import com.bidpoint.backend.user.entity.User;
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
@Table(name="role")
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "role_sequence")
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new LinkedHashSet<>();
    public void addUser(User user){ this.users.add(user);}
    public void removeUser(User user){
        this.users.remove(user);
    }
}
