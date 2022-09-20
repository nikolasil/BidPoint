package com.bidpoint.backend.recommendation.controller;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.recommendation.service.RecommendationService;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendation")
@AllArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;
    @GetMapping()
    public void getRecommendations(@RequestParam(name = "username") String username) throws Exception {
        Map<User, HashMap<Item, Double>> results = recommendationService.makeRecommendation(username);
        HashMap<Item, Double> resultsForUser = results.get(userService.getUser(username));
        for (Map.Entry<Item, Double> UserItemEntry : resultsForUser.entrySet())
            log.info(UserItemEntry.getKey().getName() + " " + UserItemEntry.getValue().toString());
    }
}
