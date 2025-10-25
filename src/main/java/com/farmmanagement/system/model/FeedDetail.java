package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedDetail {
    private String feedItemId; // Refers to an inventory item
    private double quantityPerDay;
    private String unit;

    public String getFeedItemId() {
        return this.feedItemId;
    }

    public void setFeedItemId(String feedItemId) {
        this.feedItemId = feedItemId;
    }

    public FeedDetail feedItemId(String feedItemId) {
        setFeedItemId(feedItemId);
        return this;
    }

    public double getQuantityPerDay() {
        return this.quantityPerDay;
    }

    public void setQuantityPerDay(double quantityPerDay) {
        this.quantityPerDay = quantityPerDay;
    }

    public FeedDetail quantityPerDay(double quantityPerDay) {
        setQuantityPerDay(quantityPerDay);
        return this;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public FeedDetail unit(String unit) {
        setUnit(unit);
        return this;
    }

}
