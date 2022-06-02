package com.auction.platform.backend;

import com.auction.platform.backend.entity.Role;
import com.auction.platform.backend.entity.User;
import com.auction.platform.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SELLER"));
            userService.saveRole(new Role(null, "ROLE_BIDDER"));
            userService.saveRole(new Role(null, "ROLE_VISITOR"));

            userService.saveUser(new User(null, "Nikolas Iliopoulos", "nikolasil", "1234", new ArrayList<>()));
            userService.addRoleToUser("nikolasil", "ROLE_ADMIN");
            userService.addRoleToUser("nikolasil", "ROLE_SELLER");
            userService.addRoleToUser("nikolasil", "ROLE_BIDDER");
            userService.addRoleToUser("nikolasil", "ROLE_VISITOR");

            userService.saveUser(new User(null, "Nassos Anagnostopoulos", "nassosanagn", "1234", new ArrayList<>()));
            userService.addRoleToUser("nassosanagn", "ROLE_ADMIN");
            userService.addRoleToUser("nassosanagn", "ROLE_SELLER");
            userService.addRoleToUser("nassosanagn", "ROLE_BIDDER");
            userService.addRoleToUser("nassosanagn", "ROLE_VISITOR");

            userService.saveUser(new User(null, "Michael Volakis", "Michael-Vol", "1234", new ArrayList<>()));
            userService.addRoleToUser("Michael-Vol", "ROLE_SELLER");

            userService.saveUser(new User(null, "Ilias Piotopoulos", "ilias000", "1234", new ArrayList<>()));
            userService.addRoleToUser("ilias000", "ROLE_BIDDER");

            userService.saveUser(new User(null, "Charis Piotopoulos", "piotcharis", "1234", new ArrayList<>()));
            userService.addRoleToUser("piotcharis", "ROLE_SELLER");

            userService.saveUser(new User(null, "Apostolos Karvelas", "TollisK", "1234", new ArrayList<>()));
            userService.addRoleToUser("TollisK", "ROLE_BIDDER");

            userService.saveUser(new User(null, "Miltos Kartalas", "miltoskartalas", "1234", new ArrayList<>()));
            userService.addRoleToUser("miltoskartalas", "ROLE_SELLER");

            userService.saveUser(new User(null, "Ioannis Papadimitriou", "Giannis-Papadimitriou", "1234", new ArrayList<>()));
            userService.addRoleToUser("Giannis-Papadimitriou", "ROLE_BIDDER");
        };
    }
}
