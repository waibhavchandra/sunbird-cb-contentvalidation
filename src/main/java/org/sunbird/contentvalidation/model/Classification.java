
package org.sunbird.contentvalidation.model;

public class Classification {
    private int correctPercentage;
    private int incorrectPercentage;

    public int getCorrectPercentage() {
        return correctPercentage;
    }

    public void setCorrectPercentage(int correct_percentage) {
        this.correctPercentage = correct_percentage;
    }

    public int getIncorrectPercentage() {
        return incorrectPercentage;
    }

    public void setIncorrectPercentage(int incorrect_percentage) {
        this.incorrectPercentage = incorrect_percentage;
    }
}