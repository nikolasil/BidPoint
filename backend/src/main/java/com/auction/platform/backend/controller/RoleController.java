package com.auction.platform.backend.controller;

import com.auction.platform.backend.entity.Role;
import com.auction.platform.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @GetMapping
    public ResponseEntity<Role> getRole(@RequestParam(name = "roleName",required = true) String roleName) {
        return ResponseEntity.ok().body(userService.getRole(roleName));
    }
}
