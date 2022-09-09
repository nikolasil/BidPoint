package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.dto.SearchQueryOutputDto;
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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public Item getItem(UUID itemId) {
        return itemRepository.findItemById(itemId);
    }

    @Override
    public SearchQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField));
        return itemRepository.getItemsSearchPageableSortingFiltering(categories, searchTerm, active, username, isEnded, pageRequest);
    }

//    @Override
//    public Long countItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded) {
//        return new Long(5);
//        if(Objects.equals(searchTerm, "")) { // WITHOUT SEARCH TERM
//            if(categories.size() == 0){
//                if(Objects.equals(username, "")) {
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.count();
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByDateEndsGreaterThan( ZonedDateTime.now(UTC)) :
//                                    itemRepository.countAllByDateEndsLessThan( ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByActive( active == FilterMode.TRUE);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByActiveAndDateEndsGreaterThan( active == FilterMode.TRUE, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countAllByActiveAndDateEndsLessThan( active == FilterMode.TRUE, ZonedDateTime.now(UTC));
//                    }
//                } else {
//                    User user = userRepository.findByUsername(username);
//                    if(user== null)
//                        throw new UserNotFoundException(username);
//
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByUser( user);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByUserAndDateEndsGreaterThan( user, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countAllByUserAndDateEndsLessThan( user, ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByActiveAndUser( active == FilterMode.TRUE, user);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByActiveAndDateEndsGreaterThanAndUser( active == FilterMode.TRUE, ZonedDateTime.now(UTC), user) :
//                                    itemRepository.countAllByActiveAndDateEndsLessThanAndUser(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user);
//                    }
//                }
//            } else {
//                if(Objects.equals(username, "")) {
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByCategories(categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByCategoriesAndDateEndsGreaterThan(categories, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countAllByCategoriesAndDateEndsLessThan(categories, ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByActiveAndCategories( active == FilterMode.TRUE,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByActiveAndDateEndsGreaterThanAndCategories( active == FilterMode.TRUE, ZonedDateTime.now(UTC),categories) :
//                                    itemRepository.countAllByActiveAndDateEndsLessThanAndCategories( active == FilterMode.TRUE, ZonedDateTime.now(UTC),categories);
//                    }
//                } else {
//                    User user = userRepository.findByUsername(username);
//                    if(user== null)
//                        throw new UserNotFoundException(username);
//
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByUserAndCategories( user,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByUserAndDateEndsGreaterThanAndCategories( user, ZonedDateTime.now(UTC),categories) :
//                                    itemRepository.countAllByUserAndDateEndsLessThanAndCategories( user, ZonedDateTime.now(UTC),categories);
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countAllByActiveAndUserAndCategories( active == FilterMode.TRUE, user,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countAllByActiveAndDateEndsGreaterThanAndUserAndCategories( active == FilterMode.TRUE, ZonedDateTime.now(UTC), user,categories) :
//                                    itemRepository.countAllByActiveAndDateEndsLessThanAndUserAndCategories(active == FilterMode.TRUE, ZonedDateTime.now(UTC), user,categories);
//                    }
//                }
//            }
//        } else {  // WITH SEARCH TERM
//            if(categories.size() == 0){
//                if(Objects.equals(username, "")) {
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItems( searchTerm);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByDateEndsGreaterThan( searchTerm,ZonedDateTime.now(UTC)) :
//                                    itemRepository.countSearchItemsByDateEndsLessThan( searchTerm,ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByActive( searchTerm,active == FilterMode.TRUE);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByActiveAndDateEndsGreaterThan(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countSearchItemsByActiveAndDateEndsLessThan( searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC));
//                    }
//                } else {
//                    User user = userRepository.findByUsername(username);
//                    if(user== null)
//                        throw new UserNotFoundException(username);
//
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByUser(searchTerm, user);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByUserAndDateEndsGreaterThan( searchTerm,user, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countSearchItemsByUserAndDateEndsLessThan( searchTerm,user, ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByActiveAndUser( searchTerm,active == FilterMode.TRUE, user);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByActiveAndDateEndsGreaterThanAndUser(searchTerm, active == FilterMode.TRUE, ZonedDateTime.now(UTC), user) :
//                                    itemRepository.countSearchItemsByActiveAndDateEndsLessThanAndUser(searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC), user);
//                    }
//                }
//            } else {
//                if(Objects.equals(username, "")) {
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByCategories(searchTerm,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByCategoriesAndDateEndsGreaterThan(searchTerm,categories, ZonedDateTime.now(UTC)) :
//                                    itemRepository.countSearchItemsByCategoriesAndDateEndsLessThan(searchTerm,categories, ZonedDateTime.now(UTC));
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByActiveAndCategories( searchTerm,active == FilterMode.TRUE,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByActiveAndDateEndsGreaterThanAndCategories( searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC),categories) :
//                                    itemRepository.countSearchItemsByActiveAndDateEndsLessThanAndCategories( searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC),categories);
//                    }
//                } else {
//                    User user = userRepository.findByUsername(username);
//                    if(user== null)
//                        throw new UserNotFoundException(username);
//
//                    if (active == FilterMode.NONE) {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByUserAndCategories( searchTerm,user,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByUserAndDateEndsGreaterThanAndCategories(searchTerm, user, ZonedDateTime.now(UTC),categories) :
//                                    itemRepository.countSearchItemsByUserAndDateEndsLessThanAndCategories(searchTerm, user, ZonedDateTime.now(UTC),categories);
//                    } else {
//                        if (isEnded == FilterMode.NONE)
//                            return itemRepository.countSearchItemsByActiveAndUserAndCategories( searchTerm,active == FilterMode.TRUE, user,categories);
//                        else
//                            return isEnded == FilterMode.FALSE ?
//                                    itemRepository.countSearchItemsByActiveAndDateEndsGreaterThanAndUserAndCategories( searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC), user,categories) :
//                                    itemRepository.countSearchItemsByActiveAndDateEndsLessThanAndUserAndCategories(searchTerm,active == FilterMode.TRUE, ZonedDateTime.now(UTC), user,categories);
//                    }
//                }
//            }
//        }
//    }
}
