package org.sunbird.contentvalidation.model;

import java.util.List;

public class Payload {
    private String classification;
    private ImageProfanity image_profanity;
    private List<IndiaClassification> india_classification;
    private TextProfanity text_profanity;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ImageProfanity getImage_profanity() {
        return image_profanity;
    }

    public void setImage_profanity(ImageProfanity image_profanity) {
        this.image_profanity = image_profanity;
    }

    public List<IndiaClassification> getIndia_classification() {
        return india_classification;
    }

    public void setIndia_classification(List<IndiaClassification> india_classification) {
        this.india_classification = india_classification;
    }

    public TextProfanity getText_profanity() {
        return text_profanity;
    }

    public void setText_profanity(TextProfanity text_profanity) {
        this.text_profanity = text_profanity;
    }
}
