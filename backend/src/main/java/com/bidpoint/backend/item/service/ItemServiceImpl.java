package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.exception.CategoryNotFoundException;
import com.bidpoint.backend.item.repository.BidRepository;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
   private final ItemRepository itemRepository;
   private final CategoryRepository categoryRepository;

    @Override
    public Item createItemWithCategory(Item item, List<String> categories) {
        itemRepository.save(item);
        item.setCategories(categories.stream().map(categoryName->{
            Category category = categoryRepository.findCategoryByName(categoryName);
            if(category != null){
                category.getItems().add(item);
                return category;
            }
            Set<Item> items = new LinkedHashSet<>();
            items.add(item);
            return categoryRepository.save(new Category(null,categoryName,"",items));
        }).collect(Collectors.toSet()));

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
