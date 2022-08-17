package com.bidpoint.backend.role.controller;

import com.bidpoint.backend.role.entity.Role;
import com.bidpoint.backend.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(role));
    }

    @GetMapping
    public ResponseEntity<Role> getRole(@RequestParam(name = "roleName",required = true) String roleName) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRole(roleName));
    }
}
