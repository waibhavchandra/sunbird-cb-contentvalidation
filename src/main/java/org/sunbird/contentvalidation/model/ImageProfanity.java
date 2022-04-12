package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageProfanity {
	@JsonProperty("is_safe")
    private boolean isSafe;
    @JsonProperty("nsfw-nude")
    private int nsfwNude;
    @JsonProperty("nsfw-risque")
    private int nsfwRisque;
    @JsonProperty("nsfw-sex")
    private int nsfwSex;
    @JsonProperty("nsfw-violence")
    private int nsfwViolence;
    private int sfw;

    public boolean isSafe() {
        return isSafe;
    }

    public void setIsSafe(boolean is_safe) {
        this.isSafe = is_safe;
    }

    public int getNsfwNude() {
        return nsfwNude;
    }

    public void setNsfwNude(int nsfwNude) {
        this.nsfwNude = nsfwNude;
    }

    public int getNsfwRisque() {
        return nsfwRisque;
    }

    public void setNsfwRisque(int nsfwRisque) {
        this.nsfwRisque = nsfwRisque;
    }

    public int getNsfwSex() {
        return nsfwSex;
    }

    public void setNsfwSex(int nsfwSex) {
        this.nsfwSex = nsfwSex;
    }

    public int getNsfwViolence() {
        return nsfwViolence;
    }

    public void setNsfwViolence(int nsfwViolence) {
        this.nsfwViolence = nsfwViolence;
    }

    public int getSfw() {
        return sfw;
    }

    public void setSfw(int sfw) {
        this.sfw = sfw;
    }
}
