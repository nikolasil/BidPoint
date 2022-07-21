package com.bidpoint.backend;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.UserService;
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
            String role_admin = "admin";
            String role_seller = "seller";
            String role_bidder = "bidder";
            String role_visitor = "visitor";

            userService.saveRole(new Role(null, role_admin));
            userService.saveRole(new Role(null, role_seller));
            userService.saveRole(new Role(null, role_bidder));
            userService.saveRole(new Role(null, role_visitor));

            userService.saveUser(new User(null, "Nikolas Iliopoulos", "nikolasil", "1234", new ArrayList<>()));
            userService.addRoleToUser("nikolasil", role_admin);
//            userService.addRoleToUser("nikolasil", role_seller);
//            userService.addRoleToUser("nikolasil", role_bidder);
//            userService.addRoleToUser("nikolasil", role_visitor);

            userService.saveUser(new User(null, "Nassos Anagnostopoulos", "nassosanagn", "1234", new ArrayList<>()));
//            userService.addRoleToUser("nassosanagn", role_admin);
//            userService.addRoleToUser("nassosanagn", role_seller);
//            userService.addRoleToUser("nassosanagn", role_bidder);
            userService.addRoleToUser("nassosanagn", role_visitor);

            userService.saveUser(new User(null, "Michael Volakis", "Michael-Vol", "1234", new ArrayList<>()));
            userService.addRoleToUser("Michael-Vol", role_seller);

            userService.saveUser(new User(null, "Ilias Piotopoulos", "ilias000", "1234", new ArrayList<>()));
            userService.addRoleToUser("ilias000", role_bidder);

            userService.saveUser(new User(null, "Charis Piotopoulos", "piotcharis", "1234", new ArrayList<>()));
            userService.addRoleToUser("piotcharis", role_seller);

            userService.saveUser(new User(null, "Apostolos Karvelas", "TollisK", "1234", new ArrayList<>()));
            userService.addRoleToUser("TollisK", role_bidder);

            userService.saveUser(new User(null, "Miltos Kartalas", "miltoskartalas", "1234", new ArrayList<>()));
            userService.addRoleToUser("miltoskartalas", role_seller);

            userService.saveUser(new User(null, "Ioannis Papadimitriou", "Giannis-Papadimitriou", "1234", new ArrayList<>()));
            userService.addRoleToUser("Giannis-Papadimitriou", role_bidder);
        };
    }
}
