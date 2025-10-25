package com.farmmanagement.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReproductionLog {
    private LocalDate breedingDate;
    private String sireId;
    private LocalDate expectedFarrowDate;
    private LocalDate farrowDate;
    private int pigletsBorn;
    private int pigletsWeaned;
    private String qualityRating;
    private String farrowingNotes;
    private boolean difficultBirth;
    private String milkSupply;
    private String pigletHealthStatus;

    public LocalDate getBreedingDate() {
        return this.breedingDate;
    }

    public void setBreedingDate(LocalDate breedingDate) {
        this.breedingDate = breedingDate;
    }

    public ReproductionLog breedingDate(LocalDate breedingDate) {
        setBreedingDate(breedingDate);
        return this;
    }

    public String getSireId() {
        return this.sireId;
    }

    public void setSireId(String sireId) {
        this.sireId = sireId;
    }

    public ReproductionLog sireId(String sireId) {
        setSireId(sireId);
        return this;
    }

    public LocalDate getExpectedFarrowDate() {
        return this.expectedFarrowDate;
    }

    public void setExpectedFarrowDate(LocalDate expectedFarrowDate) {
        this.expectedFarrowDate = expectedFarrowDate;
    }

    public ReproductionLog expectedFarrowDate(LocalDate expectedFarrowDate) {
        setExpectedFarrowDate(expectedFarrowDate);
        return this;
    }

    public LocalDate getFarrowDate() {
        return this.farrowDate;
    }

    public void setFarrowDate(LocalDate farrowDate) {
        this.farrowDate = farrowDate;
    }

    public ReproductionLog farrowDate(LocalDate farrowDate) {
        setFarrowDate(farrowDate);
        return this;
    }

    public int getPigletsBorn() {
        return this.pigletsBorn;
    }

    public void setPigletsBorn(int pigletsBorn) {
        this.pigletsBorn = pigletsBorn;
    }

    public ReproductionLog pigletsBorn(int pigletsBorn) {
        setPigletsBorn(pigletsBorn);
        return this;
    }

    public int getPigletsWeaned() {
        return this.pigletsWeaned;
    }

    public void setPigletsWeaned(int pigletsWeaned) {
        this.pigletsWeaned = pigletsWeaned;
    }

    public ReproductionLog pigletsWeaned(int pigletsWeaned) {
        setPigletsWeaned(pigletsWeaned);
        return this;
    }

    public String getQualityRating() {
        return this.qualityRating;
    }

    public void setQualityRating(String qualityRating) {
        this.qualityRating = qualityRating;
    }

    public ReproductionLog qualityRating(String qualityRating) {
        setQualityRating(qualityRating);
        return this;
    }

    public String getFarrowingNotes() {
        return this.farrowingNotes;
    }

    public void setFarrowingNotes(String farrowingNotes) {
        this.farrowingNotes = farrowingNotes;
    }

    public ReproductionLog farrowingNotes(String farrowingNotes) {
        setFarrowingNotes(farrowingNotes);
        return this;
    }

    public boolean isDifficultBirth() {
        return this.difficultBirth;
    }

    public boolean getDifficultBirth() {
        return this.difficultBirth;
    }

    public void setDifficultBirth(boolean difficultBirth) {
        this.difficultBirth = difficultBirth;
    }

    public ReproductionLog difficultBirth(boolean difficultBirth) {
        setDifficultBirth(difficultBirth);
        return this;
    }

    public String getMilkSupply() {
        return this.milkSupply;
    }

    public void setMilkSupply(String milkSupply) {
        this.milkSupply = milkSupply;
    }

    public ReproductionLog milkSupply(String milkSupply) {
        setMilkSupply(milkSupply);
        return this;
    }

    public String getPigletHealthStatus() {
        return this.pigletHealthStatus;
    }

    public void setPigletHealthStatus(String pigletHealthStatus) {
        this.pigletHealthStatus = pigletHealthStatus;
    }

    public ReproductionLog pigletHealthStatus(String pigletHealthStatus) {
        setPigletHealthStatus(pigletHealthStatus);
        return this;
    }

}
