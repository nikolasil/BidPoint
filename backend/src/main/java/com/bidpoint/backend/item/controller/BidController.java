package com.bidpoint.backend.item.controller;

import com.bidpoint.backend.auth.service.AuthService;
import com.bidpoint.backend.item.dto.BidOutputDto;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/bid")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final ConversionService conversionService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<List<BidOutputDto>> createBid(HttpServletRequest request,
                                                             @RequestParam(name = "itemId",required = true) UUID itemId,
                                                             @RequestParam(name = "amount",required = true) BigDecimal amount) {
        bidService.createBid(
                itemId,
                authService.decodeAuthorizationHeader(request.getHeader(AUTHORIZATION)).getSubject(),
                conversionService.convert(
                        amount,
                        Bid.class
                )
        );

        List<Bid> bids = bidService.getBidsOfItem(itemId);

        return ResponseEntity.status(HttpStatus.CREATED).body(bids.stream().map(bid -> conversionService.convert(bid, BidOutputDto.class)).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<Bid> getBid(@RequestParam(name = "bidId",required = true) UUID bidId) {
        return ResponseEntity.status(HttpStatus.OK).body(bidService.getBid(bidId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BidOutputDto>> getBidsOfItem(@RequestParam(name = "itemId",required = true) UUID itemId) {
        List<Bid> bids = bidService.getBidsOfItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(bids.stream().map(bid -> conversionService.convert(bid, BidOutputDto.class)).collect(Collectors.toList()));
    }
}
