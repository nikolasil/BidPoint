package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.exception.CategoryNotFoundException;
import com.bidpoint.backend.item.repository.BidRepository;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ImageRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.exception.UserNotFoundException;
import com.bidpoint.backend.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Override
    public Item createItemWithCategoryAndImages(String username, Item item, List<String> categories, MultipartFile[] images) {
        User user = userRepository.findByUsername(username);
        if(user== null)
            throw new UserNotFoundException(username);

        itemRepository.save(item);
        item.setUser(user);

        categories.forEach(categoryName->{
            Category category = categoryRepository.findCategoryByName(categoryName);

            if(category != null){
                category.addItem(item);
                item.addCategory(category);
                return;
            }

            category = new Category(
                    null,
                    categoryName,
                    new LinkedHashSet<>()
            );
            category.addItem(item);
            item.addCategory(categoryRepository.save(category));

        });

        Arrays.stream(images).forEach(image->{
            Image imageEntity = null;
            try {
                imageEntity = imageRepository.save(
                        new Image(
                                null,
                                image.getOriginalFilename(),
                                image.getContentType(),
                                image.getBytes(),
                                "",
                                item
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            item.addImage(imageEntity);
        });

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
    public Long getItemsCountByActive(boolean active) {
        return itemRepository.countAllByActive(active);
    }

    @Override
    public Page<Item> getItemsPaginationAndSortByUser(String username, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        User user = userRepository.findByUsername(username);
        if(user== null)
            throw new UserNotFoundException(username);
        return itemRepository.findAllByUser(
                user,
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

    @Override
    public Long getItemsCountByUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user== null)
            throw new UserNotFoundException(username);
        return itemRepository.countAllByUser(user);
    }

    @Override
    public Page<Item> searchItems(String searchTerm,int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return itemRepository.searchItems(searchTerm,PageRequest.of(
                pageNumber,
                itemCount
        ).withSort(
                Sort.by(
                        sortDirection,
                        sortField
                )
        ));
    }

    @Override
    public Long countSearchItems(String query) {
        return itemRepository.countSearchItems(query);
    }

    @Override
    public Page<Item> searchItemsByActive(boolean active, String searchTerm,int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return itemRepository.searchItemsByActive(searchTerm, active, PageRequest.of(
                pageNumber,
                itemCount
        ).withSort(
                Sort.by(
                        sortDirection,
                        sortField
                )
        ));
    }

    @Override
    public Long countSearchItemsByActive(String query, boolean active) {
        return itemRepository.countSearchItemsByActive(query, active);
    }

    @Override
    public Page<Item> searchItemsByUser(String username, String query, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        User user = userRepository.findByUsername(username);
        if(user== null)
            throw new UserNotFoundException(username);
        return itemRepository.searchItemsByUser(query, user, PageRequest.of(
                pageNumber,
                itemCount
        ).withSort(
                Sort.by(
                        sortDirection,
                        sortField
                )
        ));
    }

    @Override
    public Long countSearchItemsByUser(String query, String username) {
        User user = userRepository.findByUsername(username);
        if(user== null)
            throw new UserNotFoundException(username);
        return itemRepository.countSearchItemsByUser(query, user);
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

    @Override
    public Page<Item> getItemsPaginationAndSortByActive(boolean active, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return itemRepository.findAllByActive(
                active,
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
