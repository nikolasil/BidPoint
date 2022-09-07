package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Bid;

import java.util.List;
import java.util.UUID;

public interface BidService {
    void createBid(UUID itemId, String username, Bid bid);
    Bid getBid(UUID bidId);
    List<Bid> getBidsOfItem(UUID itemId);

}
