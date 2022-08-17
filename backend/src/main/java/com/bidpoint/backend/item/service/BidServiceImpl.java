package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BidServiceImpl implements BidService {
   private final BidRepository bidRepository;

    @Override
    public Bid createBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public Bid getBid(Long bidId) {
        return bidRepository.findBidById(bidId);
    }
}
