package com.bidpoint.backend.item.service;

import com.bidpoint.backend.user.entity.Item;

public interface ItemService {
    Item saveItem(Item item);
    Item getItem(Long itemId);
}
