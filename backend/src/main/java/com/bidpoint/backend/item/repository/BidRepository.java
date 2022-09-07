package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid,Long> {
    Bid findBidById(UUID id);
}
