package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;

import java.util.HashMap;
import java.util.Map;

public interface RecommendationService {
    Map<User, HashMap<Item, Double>> makeRecommendation(String username) throws Exception;

    boolean createRecommendations();

    double computeWeight(Integer countVisited, Boolean hasBid);
}