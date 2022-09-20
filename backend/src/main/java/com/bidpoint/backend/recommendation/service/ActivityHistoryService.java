package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.recommendation.entity.ActivityHistory;
import com.bidpoint.backend.user.entity.User;

import java.util.List;

public interface ActivityHistoryService {
    void addVisit(User user, Item item);
    void addBid(User user, Item item);
    List<ActivityHistory> getAll();

}
