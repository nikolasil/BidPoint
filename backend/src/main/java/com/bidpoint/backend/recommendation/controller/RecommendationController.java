package com.bidpoint.backend.recommendation.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bidpoint.backend.auth.exception.AuthorizationException;
import com.bidpoint.backend.auth.exception.TokenIsMissingException;
import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.recommendation.service.RecommendationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController @CrossOrigin(origins = {"https://localhost:3000", "http://localhost:3000"})
@RequestMapping("/api/recommendation")
@AllArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final AuthService authService;
    private final ConversionService conversionService;

    @GetMapping()
    public ResponseEntity<List<ItemOutputDto>> getRecommendations(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authService.hasAuthorizationHeader(authorizationHeader))
            throw new TokenIsMissingException();

        try {
            DecodedJWT decodedJWT = authService.decodeAuthorizationHeader(authorizationHeader);

            String username = decodedJWT.getSubject();

            return ResponseEntity.status(HttpStatus.OK).body(recommendationService.recommend(username).stream().map(i->conversionService.convert(i,ItemOutputDto.class)).toList());

        } catch (Exception e) {
            throw new AuthorizationException(e);
        }

    }

    @PostMapping()
    public void createRecommendations() {
        recommendationService.createRecommendations();
    }
}