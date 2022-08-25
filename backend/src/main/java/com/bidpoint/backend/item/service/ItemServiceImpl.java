package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.exception.CategoryNotFoundException;
import com.bidpoint.backend.item.repository.BidRepository;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ImageRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Override
    public Item createItemWithCategoryAndImages(Item item, List<String> categories, MultipartFile[] images) {
        itemRepository.save(item);

        item.setCategories(categories.stream().map(categoryName->{
            Category category = categoryRepository.findCategoryByName(categoryName);
            if(category != null){
                category.getItems().add(item);
                return category;
            }
            Set<Item> items = new LinkedHashSet<>();
            items.add(item);
            return categoryRepository.save(new Category(null, categoryName, items));
        }).collect(Collectors.toSet()));

        item.setImages(Arrays.stream(images).map(image->{
            try {
                return imageRepository.save(new Image(null,image.getOriginalFilename(),image.getContentType(),image.getBytes(),"",item));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toSet()));

        return itemRepository.save(item);
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findItemById(itemId);
    }

    @Override
    public Long getItemsCount() {
        return itemRepository.count();
    }

    @Override
    public List<Item> searchItems(String searchTerm) {
        return itemRepository.searchItems(searchTerm);
    }

    @Override
    public Page<Item> getItemsPaginationAndSort(int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return itemRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        itemCount
                ).withSort(
                        Sort.by(
                                sortDirection,
                                sortField
                        )
                )
        );
    }
}
