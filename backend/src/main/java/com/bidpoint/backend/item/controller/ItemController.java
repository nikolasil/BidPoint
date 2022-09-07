package com.bidpoint.backend.item.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bidpoint.backend.auth.exception.AuthorizationException;
import com.bidpoint.backend.auth.exception.TokenIsMissingException;
import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.dto.PageItemsOutputDto;
import com.bidpoint.backend.item.dto.SearchStateOutputDto;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.enums.FilterMode;
import com.bidpoint.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ConversionService conversionService;
    private final AuthService authService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemOutputDto> createItem(@RequestPart("images") MultipartFile[] images,
                                                    @RequestPart("item") ItemInputDto item,
                                                    HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            throw new TokenIsMissingException();
        try {
            DecodedJWT decodedJWT = authService.decodeAuthorizationHeader(authorizationHeader);

            Collection<GrantedAuthority> authorities = authService.mapRolesToSimpleGrantedAuthority(decodedJWT.getClaim("roles").asArray(String.class));
            String username = decodedJWT.getSubject();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    conversionService.convert(
                            itemService.createItemWithCategoryAndImages(
                                    username,
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
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
    }

    @GetMapping
    public ResponseEntity<ItemOutputDto> getItem(@RequestParam(name = "itemId",required = true) UUID itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                conversionService.convert(
                        itemService.getItem(itemId),
                        ItemOutputDto.class
                )
        );
    }

    @GetMapping("/all")
    public ResponseEntity<PageItemsOutputDto> getItemsPaginationAndSorting(
                                                                      @RequestParam(name = "pageNumber") int pageNumber,
                                                                      @RequestParam(name = "itemCount") int itemCount,
                                                                      @RequestParam(name = "sortField") String sortField,
                                                                      @RequestParam(name = "sortDirection") String sortDirection,
                                                                      @RequestParam(name = "active") FilterMode active,
                                                                      @RequestParam(name = "isEnded") FilterMode isEnded,
                                                                      @RequestParam(name = "username", defaultValue = "") String username) {

        Page<Item> items = itemService.getItemsPageable(active, username, isEnded, pageNumber, itemCount, sortField, Sort.Direction.fromString(sortDirection));
        Long totalCount = itemService.getItemsCount(active, username, isEnded);

        List<ItemOutputDto> itemsList = items.stream().map(i -> conversionService.convert(i, ItemOutputDto.class)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageItemsOutputDto(
                        totalCount,
                        new SearchStateOutputDto(pageNumber, itemCount, sortField, sortDirection, ""),
                        itemsList
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<PageItemsOutputDto> getItems(
                                                        @RequestParam(name = "searchTerm") String searchTerm,
                                                        @RequestParam(name = "pageNumber") int pageNumber,
                                                        @RequestParam(name = "itemCount") int itemCount,
                                                        @RequestParam(name = "sortField") String sortField,
                                                        @RequestParam(name = "sortDirection") String sortDirection,
                                                        @RequestParam(name = "active") FilterMode active,
                                                        @RequestParam(name = "isEnded") FilterMode isEnded,
                                                        @RequestParam(name = "username", defaultValue = "") String username) {

        Page<Item> items = itemService.getItemsSearchPageable(searchTerm, active, username, isEnded, pageNumber, itemCount, sortField, Sort.Direction.fromString(sortDirection));
        Long totalCount = itemService.getItemsSearchCount(searchTerm, active, username, isEnded);

        List<ItemOutputDto> itemsList = items.stream().map(i -> conversionService.convert(i, ItemOutputDto.class)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageItemsOutputDto(
                        totalCount,
                        new SearchStateOutputDto(pageNumber, itemCount, sortField, sortDirection, searchTerm),
                        itemsList
                )
        );
    }
}
