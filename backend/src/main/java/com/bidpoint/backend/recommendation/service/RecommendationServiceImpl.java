package com.bidpoint.backend.recommendation.service;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.ItemService;
import com.bidpoint.backend.recommendation.algorithm.MatrixFactorization;
import com.bidpoint.backend.recommendation.entity.Recommendation;
import com.bidpoint.backend.recommendation.repository.RecommendationRepository;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RecommendationServiceImpl implements  RecommendationService{
    private final UserService userService;
    private final ItemService itemService;
    private final RecommendationRepository recommendationRepository;

    @Override
    public void createRecommendations() {
        log.info("createRecommendations");
        List<User> users = userService.getAllOrdered();
        List<Item> items = itemService.getAllOrdered();
        if(items.size() > 0 && users.size() > 0)  {
            Double[][] R = new Double[users.size()][items.size()];
            for(Double[] i : R){
                Arrays.fill(i,0.0d);
            }
            for (User user : users) {
                Set<Bid> userBids = user.getBids();
                if(userBids == null)
                    userBids = new LinkedHashSet<Bid>();
                Set<Item> userVisitedItems = user.getVisitedItems();
                if(userVisitedItems == null)
                    userVisitedItems = new LinkedHashSet<Item>();
                userVisitedItems.forEach(item -> {
                    R[(int) (user.getId()- 1)][(int) (item.getId() - 1)] = 2.5d;
                });
                userBids.forEach(bid -> {
                    R[(int) (user.getId() - 1)][(int) (bid.getItem().getId() - 1)] = 5.0d;
                });
            }
            MatrixFactorization matrixFactorization = new MatrixFactorization(R, R.length, R[0].length, 2,0.002,0.02,50);

            ArrayList<Double> training_process = matrixFactorization.train();
            Double[][] res = matrixFactorization.fullMatrix();
            recommendationRepository.save(new Recommendation(null,res,null));
        }
    }

    @Override
    public List<Item> recommend(String username) {
        Recommendation recommendation = recommendationRepository.findFirstByOrderByIdDesc();
        int maxNumberOfRecommendations = 3;
        Set<Item> items = new LinkedHashSet<>();
        if(recommendation != null) {
            User user = userService.getUser(username);
            if(recommendation.getPredictions().length > user.getId() - 1) {
                Double[] itemsPredictions = recommendation.getPredictions()[(int) (user.getId() - 1)];
                int numberOfRecommends = Math.min(recommendation.getPredictions().length, maxNumberOfRecommendations);

                for(int j = 0; j < numberOfRecommends; j++) {
                    int i;
                    Double max = itemsPredictions[0];
                    int maxIndex = 0;
                    for (i = 1; i < itemsPredictions.length; i++){
                        if (itemsPredictions[i] > max) {
                            max = itemsPredictions[i];
                            maxIndex = i;
                        }
                    }
                    Item item = itemService.getItem(Long.valueOf(maxIndex + 1));
                    if(item != null && !user.getBids().contains(item) && item.isActive())
                        items.add(item);
                    else
                        j--;

                    itemsPredictions[maxIndex] = 0.0d;
                }
            }
        }
        if(items.size() != 0)  return items.stream().toList();

        Long count = itemService.countAll();
        int numberOfRecommends = (int) Math.min(count, maxNumberOfRecommendations);
        User user = userService.getUser(username);
        while(items.size() < numberOfRecommends) {
            Long number = new Random().nextLong(count);
            Item item = itemService.getItem(number);
            if(item != null && !user.getBids().contains(item))
                items.add(item);
        }

        return items.stream().toList();
    }
}
