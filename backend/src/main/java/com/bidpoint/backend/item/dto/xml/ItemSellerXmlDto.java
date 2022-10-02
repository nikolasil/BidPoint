package com.bidpoint.backend.item.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemSellerXmlDto {
    @JacksonXmlProperty(isAttribute = true, localName = "Rating")
    private String rating;
    @JacksonXmlProperty(isAttribute = true, localName = "UserID")
    private String userID;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
