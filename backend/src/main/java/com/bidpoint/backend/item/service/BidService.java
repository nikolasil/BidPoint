package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Bid;

import java.util.List;

public interface BidService {
    void createBid(Long itemId, String username, Bid bid);
    Bid getBid(Long bidId);
    List<Bid> getBidsOfItem(Long itemId);

}
