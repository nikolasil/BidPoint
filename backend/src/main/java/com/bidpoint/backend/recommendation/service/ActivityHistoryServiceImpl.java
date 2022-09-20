package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.recommendation.entity.ActivityHistory;
import com.bidpoint.backend.recommendation.repository.ActivityHistoryRepository;
import com.bidpoint.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ActivityHistoryServiceImpl implements ActivityHistoryService {

    private ActivityHistoryRepository activityHistoryRepository;

    @Override
    public void addVisit(User user, Item item) {
        ActivityHistory userActivity = activityHistoryRepository.findByUserAndItem(user, item);
        if(userActivity == null) {
            userActivity = activityHistoryRepository.save(new ActivityHistory(null, 1, false, user, item));
            return;
        }
        userActivity.increaseCountVisited();
    }

    @Override
    public void addBid(User user, Item item) {
        ActivityHistory userActivity = activityHistoryRepository.findByUserAndItem(user, item);
        if(userActivity == null) {
            userActivity = activityHistoryRepository.save(new ActivityHistory(null, 1, true, user, item));
            return;
        }
        userActivity.setHadBid(true);
    }

    @Override
    public List<ActivityHistory> getAll() {
        return activityHistoryRepository.findAll();
    }
}
