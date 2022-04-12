package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
    private String classification;
	@JsonProperty("image_profanity")
    private ImageProfanity imageProfanity;
	@JsonProperty("india_classification")
    private List<IndiaMapClassification> indiaClassification;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ImageProfanity getImageProfanity() {
        return imageProfanity;
    }

    public void setImageProfanity(ImageProfanity imageProfanity) {
        this.imageProfanity = imageProfanity;
    }

    public List<IndiaMapClassification> getIndia_classification() {
        return indiaClassification;
    }

    public void setIndia_classification(List<IndiaMapClassification> indiaClassification) {
        this.indiaClassification = indiaClassification;
    }

}
