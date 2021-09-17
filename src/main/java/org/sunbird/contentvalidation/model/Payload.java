package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
    private String classification;
    private ImageProfanity image_profanity;
    private List<IndiaMapClassification> india_classification;
//    private Profanity text_profanity;

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

    public List<IndiaMapClassification> getIndia_classification() {
        return india_classification;
    }

    public void setIndia_classification(List<IndiaMapClassification> india_classification) {
        this.india_classification = india_classification;
    }

//    public Profanity getText_profanity() {
//        return text_profanity;
//    }
//
//    public void setText_profanity(Profanity text_profanity) {
//        this.text_profanity = text_profanity;
//    }
}
