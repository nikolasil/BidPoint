package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
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
    public Page<Item> getItemsPageable(Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField));

        if(username.isEmpty()) {
            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.findAll(pageRequest);
                else
                    return itemRepository.findAllByDateEndsGreaterThan(ZonedDateTime.now(UTC), pageRequest);
            else if (dateEnds.isEmpty())
                return itemRepository.findAllByActive(active.get(), pageRequest);
            else
                return itemRepository.findAllByActiveAndDateEndsGreaterThan(active.get(), ZonedDateTime.now(UTC), pageRequest);
        } else {
            User user = userRepository.findByUsername(username.get());
            if(user== null)
                throw new UserNotFoundException(username.get());
            
            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.findAllByUser(user, pageRequest);
                else
                    return itemRepository.findAllByUserAndDateEndsGreaterThan(user, ZonedDateTime.now(UTC), pageRequest);
            else if (dateEnds.isEmpty())
                return itemRepository.findAllByActiveAndUser(active.get(), user, pageRequest);
            else
                return itemRepository.findAllByActiveAndDateEndsGreaterThanAndUser(active.get(), ZonedDateTime.now(UTC), user, pageRequest);
        }
    }

    @Override
    public Long getItemsCount(Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds) {
        if(username.isEmpty()) {
            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.count();
                else
                    return itemRepository.countAllByDateEndsGreaterThan(ZonedDateTime.now(UTC));
            else if (dateEnds.isEmpty())
                return itemRepository.countAllByActive(active.get());
            else
                return itemRepository.countAllByActiveAndDateEndsGreaterThan(active.get(), ZonedDateTime.now(UTC));
        } else {
            User user = userRepository.findByUsername(username.get());
            if(user== null)
                throw new UserNotFoundException(username.get());

            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.countAllByUser(user);
                else
                    return itemRepository.countAllByUserAndDateEndsGreaterThan(user, ZonedDateTime.now(UTC));
            else if (dateEnds.isEmpty())
                return itemRepository.countAllByActiveAndUser(active.get(), user);
            else
                return itemRepository.countAllByActiveAndDateEndsGreaterThanAndUser(active.get(), ZonedDateTime.now(UTC), user);
        }
    }

    @Override
    public Page<Item> getItemsSearchPageable(String searchTerm, Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField));
        if(username.isEmpty()) {
            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.searchItems(searchTerm, pageRequest);
                else
                    return itemRepository.searchItemsByDateEndsGreaterThan(searchTerm, ZonedDateTime.now(UTC), pageRequest);
            else if (dateEnds.isEmpty())
                return itemRepository.searchItemsByActive(searchTerm, active.get(), pageRequest);
            else
                return itemRepository.searchItemsByActiveAndDateEndsGreaterThan(searchTerm, active.get(), ZonedDateTime.now(UTC), pageRequest);
        } else {
            User user = userRepository.findByUsername(username.get());
            if(user== null)
                throw new UserNotFoundException(username.get());

            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.searchItemsByUser(searchTerm, user, pageRequest);
                else
                    return itemRepository.searchItemsByUserAndDateEndsGreaterThan(searchTerm, user, ZonedDateTime.now(UTC), pageRequest);
            else if (dateEnds.isEmpty())
                return itemRepository.searchItemsByActiveAndUser(searchTerm, active.get(), user, pageRequest);
            else
                return itemRepository.searchItemsByActiveAndDateEndsGreaterThanAndUser(searchTerm, active.get(), ZonedDateTime.now(UTC), user, pageRequest);
        }
    }

    @Override
    public Long getItemsSearchCount(String searchTerm, Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds) {
        if(username.isEmpty()) {
            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.countSearchItems(searchTerm);
                else
                    return itemRepository.countSearchItemsByDateEndsGreaterThan(searchTerm, ZonedDateTime.now(UTC));
            else if (dateEnds.isEmpty())
                return itemRepository.countSearchItemsByActive(searchTerm, active.get());
            else
                return itemRepository.countSearchItemsByActiveAndDateEndsGreaterThan(searchTerm, active.get(), ZonedDateTime.now(UTC));
        } else {
            User user = userRepository.findByUsername(username.get());
            if(user== null)
                throw new UserNotFoundException(username.get());

            if (active.isEmpty())
                if (dateEnds.isEmpty())
                    return itemRepository.countSearchItemsByUser(searchTerm, user);
                else
                    return itemRepository.countSearchItemsByUserAndDateEndsGreaterThan(searchTerm, user, ZonedDateTime.now(UTC));
            else if (dateEnds.isEmpty())
                return itemRepository.countSearchItemsByActiveAndUser(searchTerm, active.get(), user);
            else
                return itemRepository.countSearchItemsByActiveAndDateEndsGreaterThanAndUser(searchTerm, active.get(), ZonedDateTime.now(UTC), user);
        }
    }
}
