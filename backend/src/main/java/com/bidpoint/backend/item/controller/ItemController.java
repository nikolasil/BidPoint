package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.user.entity.Item;
import com.bidpoint.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveRole(@RequestBody Item item) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/item").toUriString());
        return ResponseEntity.created(uri).body(itemService.saveItem(item));
    }

    @GetMapping
    public ResponseEntity<Item> getRole(@RequestParam(name = "itemId",required = true) Long itemId) {
        return ResponseEntity.ok().body(itemService.getItem(itemId));
    }
}
