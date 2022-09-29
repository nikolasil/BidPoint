package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Item;

import java.util.List;

public interface RecommendationService {
    void createRecommendations();
    List<Item> recommend(String username);
}
