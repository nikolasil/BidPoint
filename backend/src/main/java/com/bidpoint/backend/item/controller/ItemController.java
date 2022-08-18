package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<ItemOutputDto> createItem(@RequestBody ItemInputDto item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                conversionService.convert(
                        itemService.createItemWithCategory(
                                conversionService.convert(
                                        item,
                                        Item.class
                                ),
                                item.getCategoryName()
                        ),
                        ItemOutputDto.class
                )
        );
    }

    @GetMapping
    public ResponseEntity<ItemOutputDto> getItem(@RequestParam(name = "itemId",required = true) Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                conversionService.convert(
                        itemService.getItem(itemId),
                        ItemOutputDto.class
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemOutputDto>> getItems(@RequestParam(name = "searchTerm",required = true) String searchTerm) {
        return ResponseEntity.status(HttpStatus.OK).body(
                itemService.searchItems(searchTerm).stream().map(
                        i -> conversionService.convert(
                                i,
                                ItemOutputDto.class
                        )
                ).collect(Collectors.toList())
        );
    }
}
