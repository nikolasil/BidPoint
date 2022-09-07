package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BidOutputDto {
    private UUID id;
    private BigDecimal amount;
    private String username;

    private ZonedDateTime dateCreated;
}
