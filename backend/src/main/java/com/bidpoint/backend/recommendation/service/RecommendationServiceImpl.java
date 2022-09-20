package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.repository.ItemRepository;
import com.bidpoint.backend.recommendation.entity.ActivityHistory;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.repository.UserRepository;
import com.bidpoint.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class RecommendationServiceImpl implements RecommendationService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ActivityHistoryService activityHistoryService;

    @Override
    public Map<User, HashMap<Item, Double>> makeRecommendation(String username) throws Exception {
        User user = userService.getUser(username);
        List<ActivityHistory> activityHistory = activityHistoryService.getAll();

        if (activityHistory == null || activityHistory.size() == 0) {
            throw new Exception("activityHistory is empty. Cannot make Recommendations!");
        }

        Map<User, HashMap<Item, Double>> data = new HashMap<>();

        for (ActivityHistory visited : activityHistory) {
            if (data.containsKey(visited.getUser())) {
                data.get(visited.getUser()).put(visited.getItem(), computeWeight(visited.getCountVisited(),visited.getHadBid()));
            } else {
                HashMap<Item, Double> UserRating = new HashMap<>();
                UserRating.put(visited.getItem(), computeWeight(visited.getCountVisited(),visited.getHadBid()));
                data.put(visited.getUser(), UserRating);
            }
        }

        log.info("data={}", data);
//
//        Map<User, HashMap<Item, Double>> projectedData = RecommendationAlgorithm.recommend(data, itemService.getAll());
//
//        log.info("projectedData={}", projectedData);

        return data;
    }

    @Override
    public boolean createRecommendations() {
//        List<ActivityHistory> activityHistories = activityHistoryService.getAll();
//
//        Double[][] R = new Double[(int) userRepository.count()][(int) itemRepository.count()];

//        MatrixFactorization matrixFactorization = new MatrixFactorization();
        return true;
    }

    @Override
    public double computeWeight(Integer countVisited, Boolean hasBid) {
        return hasBid ?
                1.0d :
                countVisited >= 5 ?
                        0.8d :
                        countVisited == 4 ?
                                0.7d :
                                countVisited == 3 ?
                                        0.6d :
                                        countVisited == 2 ?
                                                0.5d :
                                                countVisited == 1 ?
                                                        0.25d :
                                                        0.0d;
    }
}

