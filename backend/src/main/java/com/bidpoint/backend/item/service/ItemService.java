package com.bidpoint.backend.item.service;

import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.item.dto.SearchItemQueryOutputDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ItemService {
    Item createItemWithCategoryAndImages(String username, Item item, Set<String> categories, MultipartFile[] images);
    Item createItemWithCategory(String username, Item item, Set<String> categories);
    Item getItem(Long itemId);
    Item getItemAndStoreVisitor(Long itemId, String username);
    List<Item> getAll();
    Long countAll();
    List<Item> createAll(List<String> usernames, List<Item> items, List<List<String>> categories, List<List<Bid>> bids);
    Item importItemWithCategoriesAndBids(String username, Item item, List<String> categories, List<Bid> bids);
    Item importItemWithCategoryAndBidsAndImages(Item item, List<String> categories, List<Bid> bids, MultipartFile[] images);

    SearchItemQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);

    List<Item> getAllOrdered();
}
