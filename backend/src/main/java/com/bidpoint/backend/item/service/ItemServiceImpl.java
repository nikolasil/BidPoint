package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.exception.CategoryNotFoundException;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
   private final ItemRepository itemRepository;
   private final CategoryRepository categoryRepository;

    @Override
    public Item createItemWithCategory(Item item, String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName);
        if(category == null)
            category = categoryRepository.save(new Category(null,categoryName,"",new LinkedHashSet<>()));
        item.setCategory(category);
        return itemRepository.save(item);
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findItemById(itemId);
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
