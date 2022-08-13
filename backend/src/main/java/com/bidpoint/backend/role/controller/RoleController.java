package com.bidpoint.backend.role.controller;

import com.bidpoint.backend.user.entity.Role;
import com.bidpoint.backend.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
        return ResponseEntity.created(uri).body(roleService.createRole(role));
    }

    @GetMapping
    public ResponseEntity<Role> getRole(@RequestParam(name = "roleName",required = true) String roleName) {
        return ResponseEntity.ok().body(roleService.getRole(roleName));
    }
}
