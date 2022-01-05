package org.sunbird.contentvalidation.model;
import java.util.List;

public class TextProfanity {
    private OverallTextClassification overallTextClassification;
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