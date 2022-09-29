package com.bidpoint.backend;

import com.bidpoint.backend.recommendation.repository.RecommendationRepository;
import com.bidpoint.backend.role.entity.Role;
import com.bidpoint.backend.role.service.RoleService;
import com.bidpoint.backend.user.dto.UserInputDto;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.TimeZone;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
public class BackendApplication {
    private final ConversionService conversionService;
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Transactional
    CommandLineRunner run(UserService userService, RoleService roleService,RecommendationRepository recommendationRepository) {
        return args -> {
//            Double[][] a = new Double[2][2];
//            a[0][0] = 0.5d;
//            a[0][1] = 0.0d;
//            a[1][0] = 1.235434d;
//            a[1][1] = 0.0d;
//            Recommendation rec = new Recommendation(null, a);
//            recommendationRepository.save(rec);

            String role_admin = "admin";
            String role_seller = "seller";
            String role_bidder = "bidder";

            roleService.createRole(new Role(null, role_admin,new LinkedHashSet<>()));
            roleService.createRole(new Role(null, role_seller,new LinkedHashSet<>()));
            roleService.createRole(new Role(null, role_bidder,new LinkedHashSet<>()));

            User nikolasil = userService.createUser(conversionService.convert(new UserInputDto(
                    "Nikolas",
                    "Iliopoulos",
                    "nikolasil",
                    "1234",
                    "",
                    "",
                    "",
                    "",
                    ""),User.class),Arrays.asList("admin", "seller", "bidder"));

            User nassosanagn = userService.createUser(conversionService.convert(new UserInputDto(
                    "Nassos",
                    "Anagnostopoulos",
                    "nassosanagn",
                    "1234",
                    "",
                    "",
                    "",
                    "",
                    ""),User.class),Arrays.asList("seller", "bidder"));
        };
    }
}
