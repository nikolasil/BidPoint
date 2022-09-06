package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.enums.FilterMode;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static java.time.ZoneOffset.UTC;

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
    public Page<Item> getItemsPageable(FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField));

        if(Objects.equals(username, "")) {
            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.findAll(pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.findAllByDateEndsGreaterThan(ZonedDateTime.now(UTC), pageRequest) :
                            itemRepository.findAllByDateEndsLessThan(ZonedDateTime.now(UTC), pageRequest);
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.findAllByActive(active == FilterMode.TRUE, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                        itemRepository.findAllByActiveAndDateEndsGreaterThan(active == FilterMode.TRUE, ZonedDateTime.now(UTC), pageRequest) :
                        itemRepository.findAllByActiveAndDateEndsLessThan(active == FilterMode.TRUE, ZonedDateTime.now(UTC), pageRequest);
            }
        } else {
            User user = userRepository.findByUsername(username);
            if(user== null)
                throw new UserNotFoundException(username);
            
            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.findAllByUser(user, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.findAllByUserAndDateEndsGreaterThan(user, ZonedDateTime.now(UTC), pageRequest) :
                            itemRepository.findAllByUserAndDateEndsLessThan(user, ZonedDateTime.now(UTC), pageRequest);
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.findAllByActiveAndUser(active == FilterMode.TRUE, user, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.findAllByActiveAndDateEndsGreaterThanAndUser(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user, pageRequest) :
                            itemRepository.findAllByActiveAndDateEndsLessThanAndUser(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user, pageRequest);
            }
        }
    }

    @Override
    public Long getItemsCount(FilterMode active, String username, FilterMode isEnded) {
        if(Objects.equals(username, "")) {
            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.count();
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countAllByDateEndsGreaterThan(ZonedDateTime.now(UTC)) :
                            itemRepository.countAllByDateEndsLessThan(ZonedDateTime.now(UTC));
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countAllByActive(active == FilterMode.TRUE);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countAllByActiveAndDateEndsGreaterThan(active == FilterMode.TRUE, ZonedDateTime.now(UTC)) :
                            itemRepository.countAllByActiveAndDateEndsLessThan(active == FilterMode.TRUE, ZonedDateTime.now(UTC));
            }
        } else {
            User user = userRepository.findByUsername(username);
            if(user== null)
                throw new UserNotFoundException(username);

            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countAllByUser(user);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countAllByUserAndDateEndsGreaterThan(user, ZonedDateTime.now(UTC)) :
                            itemRepository.countAllByUserAndDateEndsLessThan(user, ZonedDateTime.now(UTC));
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countAllByActiveAndUser(active == FilterMode.TRUE, user);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countAllByActiveAndDateEndsGreaterThanAndUser(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user) :
                            itemRepository.countAllByActiveAndDateEndsLessThanAndUser(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user);
            }
        }
    }

    @Override
    public Page<Item> getItemsSearchPageable(String searchTerm, FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField));

        if(Objects.equals(username, "")) {
            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.searchItems(searchTerm, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.searchItemsByDateEndsGreaterThan(searchTerm, ZonedDateTime.now(UTC), pageRequest) :
                            itemRepository.searchItemsByDateEndsLessThan(searchTerm, ZonedDateTime.now(UTC), pageRequest);
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.searchItemsByActive(searchTerm, active == FilterMode.TRUE, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.searchItemsByActiveAndDateEndsGreaterThan(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), pageRequest) :
                            itemRepository.searchItemsByActiveAndDateEndsLessThan(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), pageRequest);
            }
        } else {
            User user = userRepository.findByUsername(username);
            if(user== null)
                throw new UserNotFoundException(username);

            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.searchItemsByUser(searchTerm, user, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.searchItemsByUserAndDateEndsGreaterThan(searchTerm, user, ZonedDateTime.now(UTC), pageRequest) :
                            itemRepository.searchItemsByUserAndDateEndsLessThan(searchTerm, user, ZonedDateTime.now(UTC), pageRequest);
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.searchItemsByActiveAndUser(searchTerm, active == FilterMode.TRUE, user, pageRequest);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.searchItemsByActiveAndDateEndsGreaterThanAndUser(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), user, pageRequest) :
                            itemRepository.searchItemsByActiveAndDateEndsLessThanAndUser(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), user, pageRequest);
            }
        }
    }

    @Override
    public Long getItemsSearchCount(String searchTerm, FilterMode active, String username, FilterMode isEnded) {
        log.info(String.valueOf(String.valueOf(ZonedDateTime.now(UTC))));
        if (Objects.equals(username, "")) {
            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countSearchItems(searchTerm);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countSearchItemsByDateEndsGreaterThan(searchTerm, ZonedDateTime.now(UTC)) :
                            itemRepository.countSearchItemsByDateEndsLessThan(searchTerm, ZonedDateTime.now(UTC));
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countSearchItemsByActive(searchTerm, active == FilterMode.TRUE);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countSearchItemsByActiveAndDateEndsGreaterThan(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC)) :
                            itemRepository.countSearchItemsByActiveAndDateEndsLessThan(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC));
            }
        } else {
            User user = userRepository.findByUsername(username);
            if (user == null)
                throw new UserNotFoundException(username);

            if (active == FilterMode.NONE) {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countSearchItemsByUser(searchTerm, user);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countSearchItemsByUserAndDateEndsGreaterThan(searchTerm, user, ZonedDateTime.now(UTC)) :
                            itemRepository.countSearchItemsByUserAndDateEndsLessThan(searchTerm, user, ZonedDateTime.now(UTC));
            } else {
                if (isEnded == FilterMode.NONE)
                    return itemRepository.countSearchItemsByActiveAndUser(searchTerm, active == FilterMode.TRUE, user);
                else
                    return isEnded == FilterMode.FALSE ?
                            itemRepository.countSearchItemsByActiveAndDateEndsGreaterThanAndUser(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), user) :
                            itemRepository.countSearchItemsByActiveAndDateEndsLessThanAndUser(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), user);
            }
        }
    }
}
