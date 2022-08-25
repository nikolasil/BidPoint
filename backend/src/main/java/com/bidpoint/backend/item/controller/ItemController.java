package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ConversionService conversionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemOutputDto> createItem(@RequestPart("images") MultipartFile[] images, @RequestPart("item") ItemInputDto item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                conversionService.convert(
                        itemService.createItemWithCategoryAndImages(
                                conversionService.convert(
                                        item,
                                        Item.class
                                ),
                                item.getCategories().stream().toList(),
                                images
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

    @GetMapping("/all")
    public ResponseEntity<List<ItemOutputDto>> getItemsPaginationAndSorting(@RequestParam(name = "pageNumber",required = true) int pageNumber,
                                                                      @RequestParam(name = "itemCount",required = true) int itemCount,
                                                                      @RequestParam(name = "sortField",required = true) String sortField,
                                                                      @RequestParam(name = "sortDirection",required = true) String sortDirection) {
        Page<Item> items = itemService.getItemsPaginationAndSort(
                pageNumber,
                itemCount,
                sortField,
                Sort.Direction.fromString(sortDirection));

        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(i -> conversionService.convert(i,ItemOutputDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getItemsCount() {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemsCount());
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
