package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.exception.ItemHasEndedException;
import com.bidpoint.backend.item.exception.ItemNotActiveException;
import com.bidpoint.backend.item.exception.ItemNotFoundException;
import com.bidpoint.backend.item.repository.BidRepository;
import com.bidpoint.backend.item.repository.ItemRepository;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BidServiceImpl implements BidService {
   private final BidRepository bidRepository;
   private final ItemRepository itemRepository;
   private final UserRepository userRepository;

    @Override
    public void createBid(Long itemId, String username, Bid bid) {
        Item item = itemRepository.findItemById(itemId);
        if(item == null)
            throw new ItemNotFoundException(itemId.toString());
        if(item.isEnded())
            throw new ItemHasEndedException(itemId.toString());
        if(!item.isActive())
            throw new ItemNotActiveException(itemId.toString());

        User user = userRepository.findByUsername(username);

        item.setNumberOfBids(item.getNumberOfBids() + 1);
        item.setCurrentPrice(bid.getAmount());

        bid.setItem(item);
        bid.setUser(user);

        bidRepository.save(bid);
        item.addBid(bid);
        itemRepository.save(item);
    }

    @Override
    public Bid getBid(Long bidId) {
        return bidRepository.findBidById(bidId);
    }

    @Override
    public List<Bid> getBidsOfItem(Long itemId) {
        Item item = itemRepository.findItemById(itemId);
        if(item == null)
            throw new ItemNotFoundException(itemId.toString());

        return item.getSortedBids();
    }

}
