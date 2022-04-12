
package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Classification {
	@JsonProperty("correct_percentage")
    private int correctPercentage;
	@JsonProperty("incorrect_percentage")
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