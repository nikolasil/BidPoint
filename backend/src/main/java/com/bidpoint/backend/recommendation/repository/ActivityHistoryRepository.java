package com.bidpoint.backend.recommendation.repository;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.recommendation.entity.ActivityHistory;
import com.bidpoint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, UUID> {
    ActivityHistory findByUserAndItem(User user, Item item);
}
