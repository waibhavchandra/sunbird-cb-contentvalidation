package org.sunbird.contentvalidation.model;

import java.util.List;

public class TextProfanity {
    private OverallTextClassification overall_text_classification;
    private List<Object> possible_profanity;

    public OverallTextClassification getOverall_text_classification() {
        return overall_text_classification;
    }

    public void setOverall_text_classification(OverallTextClassification overall_text_classification) {
        this.overall_text_classification = overall_text_classification;
    }

    public List<Object> getPossible_profanity() {
        return possible_profanity;
    }

    public void setPossible_profanity(List<Object> possible_profanity) {
        this.possible_profanity = possible_profanity;
    }
}
