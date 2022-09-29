package com.bidpoint.backend.recommendation.scheduler;

import com.bidpoint.backend.recommendation.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecommendationScheduler {
    private final RecommendationService recommendationService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleRecommendations() {
        recommendationService.createRecommendations();
    }
}
