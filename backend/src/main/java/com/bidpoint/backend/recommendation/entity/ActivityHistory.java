package com.bidpoint.backend.recommendation.entity;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="activity_history")
public class ActivityHistory {
    @Id
    @GeneratedValue
    private UUID id;

    private Integer countVisited;
    public void increaseCountVisited(){
        this.countVisited = this.countVisited + 1;
    }
    private Boolean hadBid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
