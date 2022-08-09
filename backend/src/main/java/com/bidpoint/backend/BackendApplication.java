package com.bidpoint.backend;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.role.RoleService;
import com.bidpoint.backend.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

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
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            String role_admin = "admin";
            String role_seller = "seller";
            String role_bidder = "bidder";

            roleService.createRole(new Role(null, role_admin));
            roleService.createRole(new Role(null, role_seller));
            roleService.createRole(new Role(null, role_bidder));

            User nikolasil = userService.createUser(
                new User(
                    null,
                    "Nikolas",
                    "Iliopoulos",
                    "nikolasil",
                    "1234",
                    false,
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<>()
                ), Arrays.asList("admin", "seller", "bidder")
            );

            User nassosanagn = userService.createUser(
                new User(
                    null,
                    "Nassos",
                    "Anagnostopoulos",
                    "nassosanagn",
                    "1234",
                    true,
                    "",
                    "",
                    "",
                    "",
                    new ArrayList<>()
                ), Arrays.asList("seller", "bidder")
            );

        };
    }
}
