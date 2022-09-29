package com.bidpoint.backend.recommendation.repository;

import com.bidpoint.backend.recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository  extends JpaRepository<Recommendation, Long> {
    Recommendation findFirstByOrderByIdDesc();
}
