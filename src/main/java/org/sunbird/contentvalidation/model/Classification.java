
package org.sunbird.contentvalidation.model;

public class Classification {
    private int correct_percentage;
    private int incorrect_percentage;

    public int getCorrect_percentage() {
        return correct_percentage;
    }

    public void setCorrect_percentage(int correct_percentage) {
        this.correct_percentage = correct_percentage;
    }

    public int getIncorrect_percentage() {
        return incorrect_percentage;
    }

    public void setIncorrect_percentage(int incorrect_percentage) {
        this.incorrect_percentage = incorrect_percentage;
    }
}