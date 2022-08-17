package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BidService {
    Bid createBid(Bid bid);
    Bid getBid(Long bidId);
   }
