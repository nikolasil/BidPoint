package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.dto.SearchItemQueryOutputDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.item.repository.BidRepository;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.item.repository.ImageRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import com.bidpoint.backend.role.repository.RoleRepository;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.exception.UserNotFoundException;
import com.bidpoint.backend.user.repository.UserRepository;
import com.bidpoint.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final RoleRepository roleRepository;
    private final BidRepository bidRepository;

    @Override
    public Item createItemWithCategoryAndImages(String username, Item item, Set<String> categories, MultipartFile[] images) {
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
    public Item createItemWithCategory(String username, Item item, Set<String> categories) {
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

        return itemRepository.save(item);
    }

    @Override
    public Item getItem(UUID itemId) {
        return itemRepository.findItemById(itemId);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> createAll(List<String> usernames, List<Item> items, List<List<String>> categories, List<List<Bid>> bids) {
        List<Item> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            log.info(usernames.get(i));
            log.info(items.get(i).toString());
            log.info(categories.get(i).toString());
            log.info(bids.get(i).toString());
            result.add(this.importItemWithCategoriesAndBids(usernames.get(i), items.get(i), categories.get(i), bids.get(i)));
        }
        return result;
    }

    @Override
    public Item importItemWithCategoriesAndBids(String username, Item item, List<String> categories, List<Bid> bids) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            user = userService.createUser(new User(
                    null,
                    "",
                    "",
                    username,
                    "1234",
                    true,
                    "",
                    "",
                    "",
                    "",
                    new LinkedHashSet<>(),
                    new LinkedHashSet<>(),
                    new LinkedHashSet<>()
            ), Arrays.asList("seller", "bidder"));
        }
        User finalUser = user;

        itemRepository.save(item);
        item.setUser(finalUser);

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

        bids.forEach(bid->{
            bid.setItem(item);
            User bidderUser = userRepository.findByUsername(bid.getUser().getUsername());
            if(bidderUser == null){
                bidderUser = userService.createUser(new User(
                        null,
                        "",
                        "",
                        bid.getUser().getUsername(),
                        "1234",
                        true,
                        "",
                        "",
                        "",
                        "",
                        new LinkedHashSet<>(),
                        new LinkedHashSet<>(),
                        new LinkedHashSet<>()
                ), Arrays.asList("seller", "bidder"));
            }
            bid.setUser(bidderUser);
            item.addBid(bidRepository.save(bid));
        });

        return itemRepository.save(item);
    }

    @Override
    public Item importItemWithCategoryAndBidsAndImages(Item item, List<String> categories, List<Bid> bids, MultipartFile[] images) {
        return null;
    }

    @Override
    public SearchItemQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return itemRepository.getItemsSearchPageableSortingFiltering(categories, searchTerm, active, username, isEnded, PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField)));
    }
}
