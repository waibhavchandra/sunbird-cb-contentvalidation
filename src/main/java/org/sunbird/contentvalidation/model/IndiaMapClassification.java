package org.sunbird.contentvalidation.model;

public class IndiaMapClassification {
    private Classification classification;
    private double percentageProbability;
    private String present;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public double getPercentage_probability() {
        return percentageProbability;
    }

    public void setPercentage_probability(double percentageProbability) {
        this.percentageProbability = percentageProbability;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

}
