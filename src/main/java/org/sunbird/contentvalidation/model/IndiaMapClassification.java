package org.sunbird.contentvalidation.model;

public class IndiaMapClassification {
    private Classification classification;
    private double percentage_probability;
    private String present;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public double getPercentage_probability() {
        return percentage_probability;
    }

    public void setPercentage_probability(double percentage_probability) {
        this.percentage_probability = percentage_probability;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

}
