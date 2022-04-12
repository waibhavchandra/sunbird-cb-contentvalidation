package org.sunbird.contentvalidation.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextProfanity {
	@JsonProperty("overall_text_classification")
    private OverallTextClassification overallTextClassification;
	@JsonProperty("possible_profanity")
    private List<Object> possibleProfanity;

    public OverallTextClassification getOverallTextClassification() {
        return overallTextClassification;
    }

    public void setOverallTextClassification(OverallTextClassification overallTextClassification) {
        this.overallTextClassification = overallTextClassification;
    }

    public List<Object> getPossibleProfanity() {
        return possibleProfanity;
    }

    public void setPossibleProfanity(List<Object> possibleProfanity) {
        this.possibleProfanity = possibleProfanity;
    }
}