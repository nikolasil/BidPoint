package com.bidpoint.backend.service.item;

import com.bidpoint.backend.entity.Item;

public interface ItemService {
    Item saveItem(Item item);
    Item getItem(Long itemId);
}
