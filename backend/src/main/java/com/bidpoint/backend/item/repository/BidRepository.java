package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
    Bid findBidById(Long id);
}
