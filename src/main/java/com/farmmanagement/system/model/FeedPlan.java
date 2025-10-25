package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "feedPlans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedPlan {
    @Id
    private String id;
    private String farmId;
    private String name;
    private FeedPlanStage stage;
    private String description;
    private List<FeedDetail> feedDetails;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FeedPlan id(String id) {
        setId(id);
        return this;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public FeedPlan farmId(String farmId) {
        setFarmId(farmId);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeedPlan name(String name) {
        setName(name);
        return this;
    }

    public FeedPlanStage getStage() {
        return this.stage;
    }

    public void setStage(FeedPlanStage stage) {
        this.stage = stage;
    }

    public FeedPlan stage(FeedPlanStage stage) {
        setStage(stage);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeedPlan description(String description) {
        setDescription(description);
        return this;
    }

    public List<FeedDetail> getFeedDetails() {
        return this.feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }

    public FeedPlan feedDetails(List<FeedDetail> feedDetails) {
        setFeedDetails(feedDetails);
        return this;
    }

    public enum FeedPlanStage {
        GESTATION_EARLY,
        GESTATION_LATE,
        LACTATION,
        STARTER,
        DEVELOPMENT,
        FATTENING
    }
}
