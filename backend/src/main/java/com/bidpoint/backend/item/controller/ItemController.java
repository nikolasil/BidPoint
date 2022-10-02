package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.auth.exception.AuthorizationException;
import com.bidpoint.backend.auth.exception.TokenIsMissingException;
import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.item.dto.*;
import com.bidpoint.backend.item.dto.xml.ItemXmlDto;
import com.bidpoint.backend.item.dto.xml.ItemXmlListDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController @CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/api/item")
@AllArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ConversionService conversionService;
    private final AuthService authService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemOutputDto> createItem(@RequestPart(value = "images", required = false) MultipartFile[] images,
                                                    @RequestPart("item") ItemInputDto item,
                                                    HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            throw new TokenIsMissingException();
        try {
            String username = authService.decodeAuthorizationHeader(authorizationHeader).getSubject();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    conversionService.convert(
                            images != null ?
                            itemService.createItemWithCategoryAndImages(
                                    username,
                                    conversionService.convert(
                                            item,
                                            Item.class
                                    ),
                                    item.getCategories(),
                                    images
                            ):itemService.createItemWithCategory(
                                    username,
                                    conversionService.convert(
                                            item,
                                            Item.class
                                    ),
                                    item.getCategories()
                            ),
                            ItemOutputDto.class
                    )
            );
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
    }

    @GetMapping
    public ResponseEntity<ItemOutputDto> getItem(@RequestParam(name = "itemId",required = true) Long itemId,
                                                 HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            return ResponseEntity.status(HttpStatus.OK).body(
                    conversionService.convert(
                            itemService.getItem(itemId),
                            ItemOutputDto.class
                    )
            );
        try {
            String username = authService.decodeAuthorizationHeader(authorizationHeader).getSubject();

            return ResponseEntity.status(HttpStatus.OK).body(
                    conversionService.convert(
                            itemService.getItemAndStoreVisitor(itemId,username),
                            ItemOutputDto.class
                    )
            );
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<PageItemsOutputDto> getItems(
                                                        @RequestParam(name = "searchTerm") String searchTerm,
                                                        @RequestParam(name = "pageNumber") int pageNumber,
                                                        @RequestParam(name = "itemCount") int itemCount,
                                                        @RequestParam(name = "sortField") String sortField,
                                                        @RequestParam(name = "sortDirection") String sortDirection,
                                                        @RequestParam(name = "active") FilterMode active,
                                                        @RequestParam(name = "isEnded") FilterMode isEnded,
                                                        @RequestParam(name = "username", defaultValue = "") String username,
                                                        @RequestParam(name = "categories") String[] categories) {
        if(Arrays.asList(categories).size() == 1 && Objects.equals(Arrays.asList(categories).get(0), ""))
            categories = new String[0];

        SearchItemQueryOutputDto results = itemService.getItemsSearchPageableSortingFiltering(Arrays.asList(categories), searchTerm, active, username, isEnded, pageNumber, itemCount, sortField, Sort.Direction.fromString(sortDirection));

        List<ItemOutputDto> itemsList = results.getItems().stream().map(i -> conversionService.convert(i, ItemOutputDto.class)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageItemsOutputDto(
                        results.getTotalItems(),
                        itemsList,
                        new SearchItemStateOutputDto(pageNumber, itemCount, sortField, sortDirection, searchTerm, active.toString(), isEnded.toString(), username,  Arrays.stream(categories).toList())
                )
        );
    }

    @GetMapping(value = "/export/xml", produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ItemXmlListDto> exportItemsXml() {
        ItemXmlListDto ret = new ItemXmlListDto();
        ret.setItems(itemService.getAll().stream().map(i->conversionService.convert(i,ItemXmlDto.class)).toList());
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping(value = "/export/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ItemXmlListDto> exportItemsJson() {
        ItemXmlListDto ret = new ItemXmlListDto();
        ret.setItems(itemService.getAll().stream().map(i->conversionService.convert(i,ItemXmlDto.class)).toList());
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @PostMapping(value = "/import", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, "application/xml;charset=UTF-8","text/xml"}, produces = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, "application/xml;charset=UTF-8"})
    public ResponseEntity<ItemXmlListDto> importItems(@RequestBody ItemXmlListDto items) {

        itemService.createAll(
                items.getItems().stream().map(i->i.getSeller().getUserID()).toList(),
                items.getItems().stream().map(i->conversionService.convert(i, Item.class)).toList(),
                items.getItems().stream().map(ItemXmlDto::getCategories).toList(),
                items.getItems().stream().map(i -> i.getBids().getBids().stream().map(b-> conversionService.convert(b, Bid.class)).toList()).toList()
        );

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
