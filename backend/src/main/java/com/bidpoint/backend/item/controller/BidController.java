package com.bidpoint.backend.item.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.item.dto.CategoryInputDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.service.BidService;
import com.bidpoint.backend.item.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/bid")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final ConversionService conversionService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<Bid> createCategory(HttpServletRequest request,
                                              @RequestParam(name = "itemId",required = true) Long itemId,
                                              @RequestParam(name = "amount",required = true) BigDecimal amount) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                bidService.createBid(
                        itemId,
                        authService.decodeAuthorizationHeader(request.getHeader(AUTHORIZATION)).getSubject(),
                        conversionService.convert(
                                amount,
                                Bid.class
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<Bid> getCategory(@RequestParam(name = "bidId",required = true) Long bidId) {
        return ResponseEntity.status(HttpStatus.OK).body(bidService.getBid(bidId));
    }
}
